package fr.webatriotest.webatriotest.repository;

import fr.webatriotest.webatriotest.model.Personne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonneRepository extends JpaRepository<Personne, Long> {
    List<Personne> findByEmploisEntreprise(String nomEntreprise);

    List<Personne> findAllByOrderByNomAsc();
}