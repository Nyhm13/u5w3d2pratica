package it.epicode.u5w3d2pratica.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PrenotazioneDto {

    @NotNull(message = "il campo data è obbligatorio")
    private LocalDate dataRichiesta;
    private String note;

    @NotNull(message = "devi mettere l`id del dipendente interessato è obligatorio")
    private int dipendenteId;
    @NotNull(message = "devi mettere l`id del viaggio che riguarda il dipendente")
    private  int viaggioId;

}
