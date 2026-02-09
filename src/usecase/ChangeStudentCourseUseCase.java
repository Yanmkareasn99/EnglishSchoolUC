package usecase;

import domain.Student;
import usecase.ports.StudentRepository;

public class ChangeStudentCourseUseCase {
    private final StudentRepository studentRepository;

    public ChangeStudentCourseUseCase(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public UseCaseResult execute(int studentId, String newCourse) {
        Student student = studentRepository.findById(studentId);
        if (student == null || !"在籍".equals(student.getStatus())) {
            return UseCaseResult.fail("在籍中の生徒が見つかりません。");
        }
        String before = student.getCourse();
        student.setCourse(newCourse);
        studentRepository.update(student);
        return UseCaseResult.ok("****コース変更****\n変更前: " + before + "\n変更後: " + newCourse);
    }
}
