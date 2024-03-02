package fr.webatriotest.webatriotest.controller;

import fr.webatriotest.webatriotest.model.Emploi;
import fr.webatriotest.webatriotest.model.Personne;
import fr.webatriotest.webatriotest.model.PersonneDTO;
import fr.webatriotest.webatriotest.service.PersonneService;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PersonneController {
    @Autowired
    private PersonneService personneService;

    @PostMapping("/personnes")
    public ResponseEntity<?> savePersonne(@RequestBody Personne personne) {
        int age = LocalDate.now().getYear() - personne.getDateNaissance().getYear();
        if (age >= 150) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("La personne ne peut pas être enregistrée car elle a plus de 150 ans.");
        }

        Personne savedPersonne = personneService.savePersonne(personne);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPersonne);
    }

    @PostMapping("/personnes/{personneId}/emplois")
    public ResponseEntity<?> addEmploi(@PathVariable Long personneId, @RequestBody Emploi emploi) {
        Emploi addedEmploi = personneService.addEmploi(personneId, emploi);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedEmploi);
    }

    @GetMapping("/personnes")
    public ResponseEntity<List<PersonneDTO>> getAllPersonnes() {
        List<PersonneDTO> personnes = personneService.getAllPersonnesOrderedByName();
        return ResponseEntity.ok(personnes);
    }

    @GetMapping("/personnes/entreprise/{nomEntreprise}")
    public ResponseEntity<List<PersonneDTO>> getPersonnesByEntreprise(@PathVariable String nomEntreprise) {
        List<PersonneDTO> personnes = personneService.getPersonnesByEntreprise(nomEntreprise);
        return ResponseEntity.ok(personnes);
    }

    @GetMapping("/personnes/{personneId}/emplois")
    public ResponseEntity<Object> getEmploisByPersonneAndDates(
            @PathVariable Long personneId,
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate) {
        List<Emploi> emplois = personneService.getEmploisByPersonneAndDates(personneId, startDate, endDate);
        return ResponseEntity.ok(emplois);
    }
}
