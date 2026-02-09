package usecase;

import domain.Lesson;
import usecase.ports.LessonRepository;

public class CancelLessonUseCase {
    private final LessonRepository lessonRepository;

    public CancelLessonUseCase(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    public UseCaseResult execute(int lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId);
        if (lesson == null) {
            return UseCaseResult.fail("レッスンが見つかりません。");
        }
        if ("取消".equals(lesson.getStatus())) {
            return UseCaseResult.fail("すでに取消済みです。");
        }
        lesson.setStatus("取消");
        return UseCaseResult.ok("レッスンを取消しました。");
    }
}
