package fr.webatriotest.webatriotest.service;


import fr.webatriotest.webatriotest.model.Emploi;
import fr.webatriotest.webatriotest.model.Personne;
import fr.webatriotest.webatriotest.model.PersonneDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface PersonneService {

    Personne savePersonne(Personne personne);
    Emploi addEmploi(Long personneId, Emploi emploi);
    List<PersonneDTO> getAllPersonnesOrderedByName();
    List<PersonneDTO> getPersonnesByEntreprise(String nomEntreprise);
    List<Emploi> getEmploisByPersonneAndDates(Long personneId, LocalDate startDate, LocalDate endDate);

}
