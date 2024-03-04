package it.epicode.GestioneAuto.model;

import it.epicode.GestioneAuto.enums.TipoAlimentazione;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Auto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auto_seq")
    @SequenceGenerator(name = "auto_seq", sequenceName = "auto_sequence", allocationSize = 1)
    private int id;

    private String marca;
    private String modello;

    @Column(name = "anno_di_immatricolazione")
    private int annoDiImmatricolazione;

    private int cilindrata;

    @Column(name = "tipo_alimentazione")
    @Enumerated(EnumType.STRING)
    private TipoAlimentazione tipoAlimentazione;

    private String descrizione;
    private double prezzo;

    private String foto;

    @ManyToOne
    @JoinColumn(name = "utente_id")
    Utente utente;

    public Auto() {
    }

    public Auto(String marca, String modello, int annoDiImmatricolazione, int cilindrata, TipoAlimentazione tipoAlimentazione, String descrizione, double prezzo, Utente utente) {
        this.marca = marca;
        this.modello = modello;
        this.annoDiImmatricolazione = annoDiImmatricolazione;
        this.cilindrata = cilindrata;
        this.tipoAlimentazione = tipoAlimentazione;
        this.descrizione = descrizione;
        this.prezzo = prezzo;
        this.utente = utente;
    }


}
