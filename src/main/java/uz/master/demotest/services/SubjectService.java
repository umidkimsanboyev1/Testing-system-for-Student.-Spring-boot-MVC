package uz.master.demotest.services;

import org.springframework.stereotype.Service;
import uz.master.demotest.entity.test.Subject;
import uz.master.demotest.repositories.SubjectRepository;

import java.util.List;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;

    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }


    public List<Subject> getAllSubjects() {
        return subjectRepository.findAllByDeletedFalse();
    }
}
