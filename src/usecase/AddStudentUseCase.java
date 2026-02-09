package usecase;

import domain.Student;
import usecase.ports.StudentRepository;

import java.time.LocalDate;

public class AddStudentUseCase {
    private final StudentRepository studentRepository;

    public AddStudentUseCase(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public UseCaseResult execute(String name, int age, String sex,
                                 long phone, String email, String course,
                                 LocalDate registerDate) {
        int id = studentRepository.nextId();
        Student student = new Student(
                id, name, age, sex,
                phone, email, course,
                0, "在籍", "", ""
        );
        student.setRegisterDate(registerDate);
        studentRepository.add(student);
        return UseCaseResult.ok("生徒を登録しました。");
    }
}
