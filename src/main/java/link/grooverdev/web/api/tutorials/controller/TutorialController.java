package link.grooverdev.web.api.tutorials.controller;

import jakarta.validation.Valid;
import link.grooverdev.web.api.tutorials.dto.TutorialDto;
import link.grooverdev.web.api.tutorials.entity.Tutorial;
import link.grooverdev.web.api.tutorials.service.ITutorialService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
@RestController
@Slf4j
public class TutorialController {

    private final ITutorialService tutorialService;
    private final ModelMapper modelMapper = new ModelMapper();

    @GetMapping("/tutorials")
    public ResponseEntity<List<TutorialDto>> getAllTutorials() {

        try{
            log.debug("Listado completo");
            return new ResponseEntity<>(tutorialService.findAll()
                    .stream().map(tutorials ->
                            modelMapper.map(tutorials, TutorialDto.class))
                    .collect(Collectors.toList()), HttpStatus.OK);
        }
        catch (DataAccessException daex) {
            log.error("Error al obtener el listado " + daex.getMostSpecificCause());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/tutorials/{id}")
    public ResponseEntity<TutorialDto> getTutorialByID(@Valid @PathVariable long id) {

        try{
            var tutorial = tutorialService.findByID(id);
            var tutorialResponse = modelMapper.map(tutorial, TutorialDto.class);

            if (tutorialResponse == null) {
                log.error("El objeto se encuentra vacío, favor verifique! ");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            else {
                log.debug("Un registro");
                return new ResponseEntity<>(tutorialResponse, HttpStatus.OK);
            }
        }
        catch (DataAccessException daex) {
            log.error("Error al obtener un registro " + daex.getMostSpecificCause());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/tutorials")
    public ResponseEntity<?> saveTutorial(@Valid @RequestBody TutorialDto newTutorialDto) {

        try {
            var tutorialRequest = modelMapper.map(newTutorialDto, Tutorial.class);
            var newTutorial = tutorialService.save(tutorialRequest);
            var tutorialResponse = modelMapper.map(newTutorial, TutorialDto.class);

            if (tutorialResponse == null) {
                log.error("El objeto se encuentra vacio, favor verifique! ");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            else {
                log.debug("Registro guardado! ");
                return new ResponseEntity<>(tutorialResponse, HttpStatus.CREATED);
            }
        }
        catch (DataAccessException daex) {
            log.error("Error al guardar el rgistro " + daex.getMostSpecificCause());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/civilians/{id}")
    public ResponseEntity<TutorialDto> updateTutorial(@Valid @PathVariable long id,
                                                            @Valid @RequestBody TutorialDto tutorialToUpdateDto) {

        try {
            var tutorialRequest = modelMapper.map(tutorialToUpdateDto, Tutorial.class);
            var tutorialUpdated = tutorialService.update(id, tutorialRequest);
            var tutorialResponse = modelMapper.map(tutorialUpdated, TutorialDto.class);

            if (tutorialResponse == null) {
                log.error("El objeto se encuentra vacio, favor verifique! ");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            else {
                log.debug("Registro actualizado!");
                return new ResponseEntity<>(tutorialResponse, HttpStatus.ACCEPTED);
            }
        }
        catch (DataAccessException daex) {
            log.error("Error al actualizar el registro " + daex.getMostSpecificCause());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PatchMapping("/civilians/{id}")
    public ResponseEntity<TutorialDto> deleteTutorial(@Valid @PathVariable long id) {

        try {
            var tutorialDeleted = tutorialService.delete(id);
            var tutorialResponse = modelMapper.map(tutorialDeleted, TutorialDto.class);

            if (tutorialResponse == null) {
                log.error("El objeto se encuentra vacio, favor verifique! ");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            else {
                log.debug("Registro eliminado! ");
                return new ResponseEntity<>(tutorialResponse, HttpStatus.NO_CONTENT);
            }
        }
        catch (DataAccessException daex) {
            log.error("Error al actualizar el registro " + daex.getMostSpecificCause());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
