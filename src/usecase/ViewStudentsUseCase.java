package usecase;

import domain.Student;
import usecase.ports.StudentRepository;

import java.util.ArrayList;
import java.util.List;

public class ViewStudentsUseCase {
    private final StudentRepository studentRepository;

    public ViewStudentsUseCase(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> execute() {
        List<Student> active = new ArrayList<>();
        for (Student student : studentRepository.findAll()) {
            if ("在籍".equals(student.getStatus())) {
                active.add(student);
            }
        }
        return active;
    }
}
