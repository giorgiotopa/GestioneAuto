package it.epicode.GestioneAuto.repository;

import it.epicode.GestioneAuto.model.Auto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutoRepository extends JpaRepository<Auto,Integer>, PagingAndSortingRepository<Auto,Integer> {

}
