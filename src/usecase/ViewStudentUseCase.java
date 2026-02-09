package usecase;

import domain.Student;
import usecase.ports.StudentRepository;

public class ViewStudentUseCase {
    private final StudentRepository studentRepository;

    public ViewStudentUseCase(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student execute(int studentId) {
        Student student = studentRepository.findById(studentId);
        if (student == null || !"在籍".equals(student.getStatus())) {
            return null;
        }
        return student;
    }
}
