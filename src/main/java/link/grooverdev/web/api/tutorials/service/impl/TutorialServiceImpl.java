package link.grooverdev.web.api.tutorials.service.impl;

import link.grooverdev.web.api.tutorials.entity.Tutorial;
import link.grooverdev.web.api.tutorials.repository.TutorialRepository;
import link.grooverdev.web.api.tutorials.service.ITutorialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TutorialServiceImpl implements ITutorialService {

    private final TutorialRepository tutorialRepository;
    @Override
    @Transactional(readOnly = true)
    public List<Tutorial> findAll() {
        return tutorialRepository.findAll();
    }
    @Override
    @Transactional(readOnly = true)
    public Optional<Tutorial> findByID(Long id) {
        return tutorialRepository.findById(id);
    }

    @Override
    @Transactional
    public Tutorial save(Tutorial tutorial) {
        return tutorialRepository.save(tutorial);
    }

    @Override
    @Transactional
    public Tutorial update(Long id, Tutorial updTutorial) {

        var updatedTutorial = tutorialRepository.findById(id);

        if (updatedTutorial.isPresent()) {
            updatedTutorial.get().setTitle(updTutorial.getTitle());
            updatedTutorial.get().setDescription(updTutorial.getDescription());
            updatedTutorial.get().setPublished(updTutorial.isPublished());
        }
        return tutorialRepository.save(updatedTutorial.get());
    }
    @Override
    @Transactional
    public Tutorial delete(Long id) {

        var deletedTutorial = tutorialRepository.findById(id);
        deletedTutorial.ifPresent(delTutorial->deletedTutorial.get().setEnabled(false));
        return tutorialRepository.save(deletedTutorial.get());
    }
}
