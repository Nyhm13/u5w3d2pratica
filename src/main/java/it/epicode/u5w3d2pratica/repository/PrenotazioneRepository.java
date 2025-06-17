package it.epicode.u5w3d2pratica.repository;

import it.epicode.u5w3d2pratica.model.Dipendente;
import it.epicode.u5w3d2pratica.model.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;
import java.util.List;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione,Integer>, PagingAndSortingRepository<Prenotazione,Integer> {

    boolean existsByDipendenteAndDataRichiesta(Dipendente dipendente, LocalDate dataRichiesta);
    List<Prenotazione> findByDipendente(Dipendente dipendente);
}
