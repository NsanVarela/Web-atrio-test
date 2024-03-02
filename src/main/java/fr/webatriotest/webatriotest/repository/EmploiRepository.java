package fr.webatriotest.webatriotest.repository;

import fr.webatriotest.webatriotest.model.Emploi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmploiRepository extends JpaRepository<Emploi, Long> {
    List<Emploi> findByPersonneIdAndDateDebutBetween(Long personneId, LocalDate startDate, LocalDate endDate);
}