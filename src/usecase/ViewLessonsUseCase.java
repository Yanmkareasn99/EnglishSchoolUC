package usecase;

import domain.Lesson;
import usecase.ports.LessonRepository;

import java.util.ArrayList;
import java.util.List;

public class ViewLessonsUseCase {
    private final LessonRepository lessonRepository;

    public ViewLessonsUseCase(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    public List<Lesson> execute() {
        List<Lesson> active = new ArrayList<>();
        for (Lesson lesson : lessonRepository.findAll()) {
            if (!"取消".equals(lesson.getStatus())) {
                active.add(lesson);
            }
        }
        return active;
    }
}
