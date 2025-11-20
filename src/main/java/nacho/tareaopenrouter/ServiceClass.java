package nacho.tareaopenrouter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Servicio encargado de la lógica de negocio y comunicación con OpenRouter.
 * Sigue el principio de responsabilidad única.
 */
@Service
public class ServiceClass {

    /**
     * Inyectamos la API Key desde el archivo de configuración.
     * Esto evita tener credenciales escritas en el código (hardcoded).
     */
    @Value("${OPENROUTER_API_KEY}")
    private String openRouterApiKey;

    private static final String OPENROUTER_URL = "https://openrouter.ai/api/v1/chat/completions";
    private final RestTemplate restTemplate;

    /**
     * Constructor con inyección de dependencias.
     * Inicializamos RestTemplate aquí.
     */
    public ServiceClass() {
        this.restTemplate = new RestTemplate();
    }

    /**
     * @param requestDto Los datos enviados por el usuario.
     * @return La respuesta de la IA procesada (String).
     */
    public String obtenerRecomendacion(RequestDto requestDto) {
        validarEntrada(requestDto);

        HttpHeaders headers = crearCabeceras();

        Map<String, Object> body = construirCuerpoJson(requestDto);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(OPENROUTER_URL, entity, String.class);
            return sanitizarRespuesta(response.getBody());
        } catch (Exception e) {
            return "Error al conectar con OpenRouter: " + e.getMessage();
        }
    }


    /**
     * Valida que los campos necesarios estén presentes.
     */
    private void validarEntrada(RequestDto dto) {
        if (dto.getRequisitosFuncionales() == null || dto.getRequisitosFuncionales().isEmpty()) {
            throw new IllegalArgumentException("Los requisitos funcionales son obligatorios");
        }
    }

    /**
     * Crea los headers necesarios para la petición HTTP.
     */
    private HttpHeaders crearCabeceras() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + openRouterApiKey);
        headers.set("HTTP-Referer", "http://localhost:8080");
        headers.set("X-Title", "Tarea Arquitectura API");
        return headers;
    }

    /**
     * Construye el mapa que Jackson convertirá automáticamente a JSON.
     * Estructura requerida por la API de OpenRouter (compatible con OpenAI).
     */
    private Map<String, Object> construirCuerpoJson(RequestDto dto) {
        Map<String, Object> body = new HashMap<>();
        body.put("model", "openai/gpt-3.5-turbo"); // Puedes cambiar el modelo aquí

        String promptUsuario = "Requisitos Funcionales: " + dto.getRequisitosFuncionales() +
                ". No Funcionales: " + dto.getRequisitosNoFuncionales();

        Map<String, String> mensajeSistema = new HashMap<>();
        mensajeSistema.put("role", "system");
        mensajeSistema.put("content", "Eres un arquitecto de software. Responde solo con un JSON valido que contenga: stack, arquitectura y justificacion.");

        Map<String, String> mensajeUsuario = new HashMap<>();
        mensajeUsuario.put("role", "user");
        mensajeUsuario.put("content", promptUsuario);

        body.put("messages", List.of(mensajeSistema, mensajeUsuario));
        return body;
    }

    private String sanitizarRespuesta(String rawResponse) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(rawResponse);

            String contenido = root.path("choices")
                    .get(0)
                    .path("message")
                    .path("content")
                    .asText();

            if (contenido.contains("```json")) {
                contenido = contenido.replace("```json", "").replace("```", "");
            } else if (contenido.contains("```")) {
                contenido = contenido.replace("```", "");
            }

            return contenido.trim();

        } catch (Exception e) {
            return "{\"error\": \"Error al procesar respuesta: " + e.getMessage() + "\"}";
        }
    }
}