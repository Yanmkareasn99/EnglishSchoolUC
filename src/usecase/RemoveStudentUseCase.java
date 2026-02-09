package usecase;

import domain.Student;
import usecase.ports.StudentRepository;

public class RemoveStudentUseCase {
    private final StudentRepository studentRepository;

    public RemoveStudentUseCase(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public UseCaseResult execute(int studentId) {
        Student student = studentRepository.findById(studentId);
        if (student == null) {
            return UseCaseResult.fail("生徒が見つかりません。");
        }
        if (!"在籍".equals(student.getStatus())) {
            return UseCaseResult.fail("すでに退学済みです。");
        }
        student.setStatus("退学");
        studentRepository.update(student);
        return UseCaseResult.ok("退学処理が完了しました。");
    }
}
