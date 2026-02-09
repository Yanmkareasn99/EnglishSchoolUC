package usecase;

import domain.Lesson;
import usecase.ports.LessonRepository;

import java.util.ArrayList;
import java.util.List;

public class ViewStudentLessonsUseCase {
    private final LessonRepository lessonRepository;

    public ViewStudentLessonsUseCase(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    public List<Lesson> execute(int studentId) {
        List<Lesson> lessons = new ArrayList<>();
        for (Lesson lesson : lessonRepository.findAll()) {
            if (lesson.getStudentId() == studentId && !"取消".equals(lesson.getStatus())) {
                lessons.add(lesson);
            }
        }
        return lessons;
    }
}
