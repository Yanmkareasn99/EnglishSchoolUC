package usecase;

import domain.LessonCost;
import usecase.ports.LessonCostRepository;

public class ViewLessonCostUseCase {
    private final LessonCostRepository lessonCostRepository;

    public ViewLessonCostUseCase(LessonCostRepository lessonCostRepository) {
        this.lessonCostRepository = lessonCostRepository;
    }

    public LessonCost execute() {
        return lessonCostRepository.load();
    }
}
