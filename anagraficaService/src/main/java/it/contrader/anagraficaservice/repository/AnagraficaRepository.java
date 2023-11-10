package it.contrader.anagraficaservice.repository;

import it.contrader.anagraficaservice.model.Anagrafica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnagraficaRepository extends JpaRepository<Anagrafica, Long> {

}
