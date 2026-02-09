package usecase;

import domain.LessonCost;
import usecase.ports.LessonCostRepository;

public class ChangeLessonCostUseCase {
    private final LessonCostRepository lessonCostRepository;

    public ChangeLessonCostUseCase(LessonCostRepository lessonCostRepository) {
        this.lessonCostRepository = lessonCostRepository;
    }

    public UseCaseResult execute(int pointValue, int lessonCost) {
        LessonCost cost = new LessonCost(pointValue, lessonCost);
        lessonCostRepository.save(cost);
        return UseCaseResult.ok("単価を変更しました。");
    }
}
