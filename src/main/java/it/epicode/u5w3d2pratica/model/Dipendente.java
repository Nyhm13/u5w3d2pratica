package it.epicode.u5w3d2pratica.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Dipendente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String nome;
    private String cognome;
    private String email;
    private String avatar;

    @OneToMany(mappedBy = "dipendente")
    @JsonIgnore
    private List<Prenotazione> prenotazioni;

}
