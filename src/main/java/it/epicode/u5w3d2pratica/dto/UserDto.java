package it.epicode.u5w3d2pratica.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserDto {
    @NotEmpty(message = "il campo nome non puo essere nullo ")
    private String nome;
    @NotEmpty(message = "il campo cognome non puo essere nullo ")
    private String cognome;
    @NotEmpty(message = "il campo username non puo essere nullo ")
    private String username;
    @NotEmpty(message = "il campo email non puo essere nullo ")
    @Email(message = "l`email deve essere valida")
    private String email;
    @NotEmpty(message = "il campo password  non puo essere nullo ")
    private String password;

}
