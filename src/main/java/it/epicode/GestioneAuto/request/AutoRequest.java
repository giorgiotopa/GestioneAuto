package it.epicode.GestioneAuto.request;

import it.epicode.GestioneAuto.enums.TipoAlimentazione;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AutoRequest {
    @NotBlank(message = "Marca required")
    private String marca;
    @NotBlank(message = "Modello required")
    private String modello;
    @NotNull(message = "Anno di immatricolazione required")
    private Integer annoDiImmatricolazione;
    @NotNull(message = "Cilindrata required")
    private Integer cilindrata;
    @NotNull(message = "Tipo alimentazione required")
    private TipoAlimentazione tipoAlimentazione;
    @NotBlank(message = "Modello required")
    private String descrizione;
    @NotNull(message = "Prezzo required")
    private Double prezzo;
    @NotNull(message = "Cliente required")
    private Integer utenteId;
}
