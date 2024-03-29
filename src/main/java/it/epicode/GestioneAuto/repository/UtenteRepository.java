package it.epicode.GestioneAuto.repository;


import it.epicode.GestioneAuto.model.Auto;
import it.epicode.GestioneAuto.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Integer>, PagingAndSortingRepository<Utente, Integer> {

    public Optional<Utente> findByUsername(String username);

}
