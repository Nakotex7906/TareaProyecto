package nacho.tareaopenrouter;

import lombok.Data;

@Data
/**
 * Clase de Transferencia de Datos (DTO).
 * Representa la estructura de la información que recibimos del cliente.
 */
public class RequestDto {

    private String requisitosFuncionales;
    private String requisitosNoFuncionales;

    public RequestDto() {}

    public RequestDto(String requisitosFuncionales, String requisitosNoFuncionales) {
        this.requisitosFuncionales = requisitosFuncionales;
        this.requisitosNoFuncionales = requisitosNoFuncionales;
    }

}