package usecase;

import domain.Lesson;
import domain.LessonCost;
import domain.Student;
import domain.Teacher;
import usecase.ports.LessonCostRepository;
import usecase.ports.LessonRepository;
import usecase.ports.StudentRepository;
import usecase.ports.TeacherRepository;
import util.DateTimeUtil;

import java.time.LocalDateTime;

public class ReserveLessonUseCase {
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final LessonRepository lessonRepository;
    private final LessonCostRepository lessonCostRepository;

    public ReserveLessonUseCase(StudentRepository studentRepository,
                                TeacherRepository teacherRepository,
                                LessonRepository lessonRepository,
                                LessonCostRepository lessonCostRepository) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.lessonRepository = lessonRepository;
        this.lessonCostRepository = lessonCostRepository;
    }

    public ReserveLessonResult execute(int studentId, int teacherId,
                                       String lessonType, LocalDateTime dateTime) {
        Student student = studentRepository.findById(studentId);
        if (student == null || !"在籍".equals(student.getStatus())) {
            return ReserveLessonResult.fail("在籍中の生徒が見つかりません。");
        }
        Teacher teacher = teacherRepository.findById(teacherId);
        if (teacher == null) {
            return ReserveLessonResult.fail("先生が見つかりません");
        }
        if (dateTime.isBefore(LocalDateTime.now())) {
            return ReserveLessonResult.fail("過去の日時は予約できません。");
        }

        String formattedDateTime = DateTimeUtil.format(dateTime);
        for (Lesson lesson : lessonRepository.findAll()) {
            if (!"取消".equals(lesson.getStatus()) && formattedDateTime.equals(lesson.getDateTime())) {
                if (lesson.getTeacherId() == teacherId) {
                    return ReserveLessonResult.fail("講師はその時間に予約があります。");
                }
                if (lesson.getStudentId() == studentId) {
                    return ReserveLessonResult.fail("生徒はその時間に予約があります。");
                }
            }
        }

        LessonCost lessonCost = lessonCostRepository.load();
        if (!student.consumePoints(lessonCost.getLessonCost())) {
            return ReserveLessonResult.needsPoints("ポイントが不足しています。");
        }

        int lessonId = lessonRepository.nextId();
        Lesson lesson = new Lesson(lessonId, studentId, teacherId, lessonType, formattedDateTime);
        lessonRepository.add(lesson);
        studentRepository.update(student);

        return ReserveLessonResult.ok("レッスンを予約しました。", lesson);
    }
}
