package nacho.tareaopenrouter;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST que expone los puntos de entrada de la API.
 */
@RestController
@RequestMapping("/api/arquitectura")
public class Controller {

    private final ServiceClass arquitecturaService;

    /**
     * Constructor para inyección de dependencias.
     * Spring inyectará automáticamente la instancia de Service aquí.
     * * @param arquitecturaService Lógica de negocio para recomendaciones.
     */
    public Controller(ServiceClass arquitecturaService) {
        this.arquitecturaService = arquitecturaService;
    }

    /**
     * Endpoint para generar una recomendación de arquitectura.
     * Recibe un JSON con los requisitos y devuelve la respuesta de la IA.
     * * URL: POST http://localhost:8080/api/arquitectura/generar
     *
     * @param requestDto Objeto con los requisitos funcionales y no funcionales.
     * @return ResponseEntity con la recomendación en formato texto/JSON.
     */
    @PostMapping("/generar")
    public ResponseEntity<String> generarRecomendacion(@RequestBody RequestDto requestDto) {
        String respuesta = arquitecturaService.obtenerRecomendacion(requestDto);

        return ResponseEntity.ok(respuesta);
    }
}