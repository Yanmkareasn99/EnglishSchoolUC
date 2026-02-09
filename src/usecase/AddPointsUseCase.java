package usecase;

import domain.Student;
import usecase.ports.StudentRepository;

public class AddPointsUseCase {
    private final StudentRepository studentRepository;

    public AddPointsUseCase(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public UseCaseResult execute(int studentId, int points) {
        Student student = studentRepository.findById(studentId);
        if (student == null || !"在籍".equals(student.getStatus())) {
            return UseCaseResult.fail("在籍中の生徒が見つかりません。");
        }
        if (student.addPoints(points)) {
            studentRepository.update(student);
            return UseCaseResult.ok("ポイント追加完了");
        }
        return UseCaseResult.fail("200単位で入力してください");
    }
}
