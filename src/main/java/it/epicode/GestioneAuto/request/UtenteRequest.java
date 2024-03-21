package it.epicode.GestioneAuto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UtenteRequest {

    @NotBlank(message = "Campo obbligatorio")
    private String nome;
    @NotBlank(message = "Campo obbligatorio")
    private String cognome;
    @Email(message = "Inserisci una email valida")
    private String email;
    @NotBlank(message = "Campo obbligatorio")
    private String username;
//    @NotBlank(message = "Campo obbligatorio")
    private String password;
}
