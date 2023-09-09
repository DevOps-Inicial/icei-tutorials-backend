package link.grooverdev.web.api.tutorials.service;

import link.grooverdev.web.api.tutorials.entity.Tutorial;

import java.util.List;
import java.util.Optional;

public interface ITutorialService {
    List<Tutorial> findAll();
    Optional<Tutorial> findByID(Long id);
    Tutorial save(Tutorial tutorial);
    Tutorial update(Long id, Tutorial updTutorial);
    Tutorial delete(Long id);
}
