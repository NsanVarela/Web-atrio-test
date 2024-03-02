package fr.webatriotest.webatriotest;

import fr.webatriotest.webatriotest.model.Emploi;
import fr.webatriotest.webatriotest.model.Personne;
import fr.webatriotest.webatriotest.model.PersonneDTO;
import fr.webatriotest.webatriotest.repository.PersonneRepository;
import fr.webatriotest.webatriotest.service.PersonneServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonneServiceImplTest {

    @Mock
    private PersonneRepository personneRepository;

    @InjectMocks
    private PersonneServiceImpl personneService;

    @Test
    public void testSavePersonne() {
        // Given
        Personne personne = new Personne();
        personne.setNom("John");
        personne.setPrenom("Doe");
        personne.setDateNaissance(LocalDate.of(1990, 1, 1));

        when(personneRepository.save(personne)).thenReturn(personne);

        // When
        Personne savedPersonne = personneService.savePersonne(personne);

        // Then
        assertEquals(personne, savedPersonne);
    }

    @Test
    public void testAddEmploi() {
        // Given
        Long personneId = 1L;
        Personne personne = new Personne();
        Emploi emploi = new Emploi();
        emploi.setEntreprise("Company");
        emploi.setPoste("Developer");
        personne.setId(personneId);

        when(personneRepository.findById(personneId)).thenReturn(Optional.of(personne));

        // When
        Emploi addedEmploi = personneService.addEmploi(personneId, emploi);

        // Then
        assertTrue(personne.getEmplois().contains(emploi));
        assertEquals(emploi, addedEmploi);
    }

    @Test
    public void testGetAllPersonnesOrderedByName() {
        // Given
        Personne personne1 = new Personne();
        personne1.setNom("Doe");
        Personne personne2 = new Personne();
        personne2.setNom("Smith");

        List<Personne> personnes = Arrays.asList(personne1, personne2);

        when(personneRepository.findAllByOrderByNomAsc()).thenReturn(personnes);

        // When
        List<PersonneDTO> result = personneService.getAllPersonnesOrderedByName();

        // Then
        assertEquals(2, result.size());
        assertEquals("Doe", result.get(0).getNom());
        assertEquals("Smith", result.get(1).getNom());
    }

    @Test
    public void testGetPersonnesByEntreprise() {
        // Given
        String nomEntreprise = "Company";
        Personne personne = new Personne();
        personne.setNom("John");
        Emploi emploi = new Emploi();
        emploi.setEntreprise(nomEntreprise);
        personne.setEmplois(Collections.singletonList(emploi));

        when(personneRepository.findByEmploisEntreprise(nomEntreprise)).thenReturn(Collections.singletonList(personne));

        // When
        List<PersonneDTO> result = personneService.getPersonnesByEntreprise(nomEntreprise);

        // Then
        assertEquals(1, result.size());
        assertEquals(nomEntreprise, result.get(0).getEmplois().get(0).getEntreprise());
    }

}
