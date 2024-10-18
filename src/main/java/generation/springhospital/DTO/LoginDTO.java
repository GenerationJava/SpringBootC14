package generation.springhospital.DTO;

import lombok.Data;

/**LOS DTO (Data Transfer Object) son objetos cuya finalidad es la transferencia de datos entre capas, en este
 * caso, el LoginDTO viene a representar los datos necesarios para realizar el login**/

@Data
public class LoginDTO {


    private String email;
    private String password;
}
