package fr.webatriotest.webatriotest.service;

import fr.webatriotest.webatriotest.repository.PersonneRepository;
import fr.webatriotest.webatriotest.model.Emploi;
import fr.webatriotest.webatriotest.model.EmploiDTO;
import fr.webatriotest.webatriotest.model.Personne;
import fr.webatriotest.webatriotest.model.PersonneDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonneServiceImpl implements PersonneService {

    @Autowired
    private PersonneRepository personneRepository;

    /**
     * @param personne
     * @return
     */
    @Override
    public Personne savePersonne(Personne personne) {
        int age = calculateAge(personne.getDateNaissance());
        if (age >= 150) {
            throw new IllegalArgumentException("La personne ne peut pas être enregistrée car elle a plus de 150 ans.");
        }
        return personneRepository.save(personne);
    }

    /**
     * @param personneId
     * @param emploi
     * @return
     */
    @Override
    public Emploi addEmploi(Long personneId, Emploi emploi) {
        Personne personne = personneRepository.findById(personneId)
                .orElseThrow(() -> new IllegalArgumentException("Personne non trouvée avec l'ID : " + personneId));

        personne.getEmplois().add(emploi);
        return emploi;
    }

    /**
     * @return
     */
    @Override
    public List<PersonneDTO> getAllPersonnesOrderedByName() {
        List<Personne> personnes = personneRepository.findAllByOrderByNomAsc();
        return personnes.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * @param nomEntreprise
     * @return
     */
    @Override
    public List<PersonneDTO> getPersonnesByEntreprise(String nomEntreprise) {
        List<Personne> personnes = personneRepository.findByEmploisEntreprise(nomEntreprise);
        return personnes.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * @param personneId
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public List<Emploi> getEmploisByPersonneAndDates(Long personneId, LocalDate startDate, LocalDate endDate) {
        Personne personne = personneRepository.findById(personneId)
                .orElseThrow(() -> new IllegalArgumentException("Personne non trouvée avec l'ID : " + personneId));

        return personne.getEmplois().stream()
                .filter(emploi -> emploi.getDateDebut().isAfter(startDate) && emploi.getDateDebut().isBefore(endDate))
                .collect(Collectors.toList());
    }

    private int calculateAge(LocalDate dateNaissance) {
        return LocalDate.now().getYear() - dateNaissance.getYear();
    }

    private PersonneDTO convertToDTO(Personne personne) {
        PersonneDTO personneDTO = new PersonneDTO();
        personneDTO.setId(personne.getId());
        personneDTO.setNom(personne.getNom());
        personneDTO.setPrenom(personne.getPrenom());
        personneDTO.setDateNaissance(personne.getDateNaissance());
        personneDTO.setAge(calculateAge(personne.getDateNaissance()));

        List<EmploiDTO> emploisDTO = personne.getEmplois().stream()
                .map(this::convertEmploiToDTO)
                .collect(Collectors.toList());
        personneDTO.setEmplois(emploisDTO);

        return personneDTO;
    }

    private EmploiDTO convertEmploiToDTO(Emploi emploi) {
        EmploiDTO emploiDTO = new EmploiDTO();
        emploiDTO.setEntreprise(emploi.getEntreprise());
        emploiDTO.setPoste(emploi.getPoste());
        emploiDTO.setDateDebut(emploi.getDateDebut());
        emploiDTO.setDateFin(emploi.getDateFin());
        return emploiDTO;
    }

}
