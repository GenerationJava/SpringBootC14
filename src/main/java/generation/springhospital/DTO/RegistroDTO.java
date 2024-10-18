package generation.springhospital.DTO;

import lombok.Data;


/**LOS DTO (Data Transfer Object) son objetos cuya finalidad es la transferencia de datos entre capas, en este
 * caso, el RegistroDTO viene a representar los datos necesarios para realizar el registro de usuario**/
@Data
public class RegistroDTO {

    private String nombre;

    private String email;

    private String password;


}




