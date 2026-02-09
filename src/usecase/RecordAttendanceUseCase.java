package usecase;

import domain.Lesson;
import domain.Student;
import usecase.ports.LessonRepository;
import usecase.ports.StudentRepository;

public class RecordAttendanceUseCase {
    private final LessonRepository lessonRepository;
    private final StudentRepository studentRepository;

    public RecordAttendanceUseCase(LessonRepository lessonRepository, StudentRepository studentRepository) {
        this.lessonRepository = lessonRepository;
        this.studentRepository = studentRepository;
    }

    public UseCaseResult execute(int lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId);
        if (lesson == null) {
            return UseCaseResult.fail("レッスンが見つかりません。");
        }
        Student student = studentRepository.findById(lesson.getStudentId());
        if (student == null) {
            return UseCaseResult.fail("生徒が見つかりません。");
        }
        String summary = lesson.getDateTime() + " " + lesson.getLessonType();
        student.setLesson(summary);
        studentRepository.update(student);
        return UseCaseResult.ok("出席を登録しました。");
    }
}
