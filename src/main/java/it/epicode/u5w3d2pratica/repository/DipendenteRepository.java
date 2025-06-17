package it.epicode.u5w3d2pratica.repository;

import it.epicode.u5w3d2pratica.model.Dipendente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DipendenteRepository extends JpaRepository<Dipendente,Integer> {
}
