package usecase;

import domain.Lesson;
import domain.LessonCost;
import usecase.ports.LessonCostRepository;
import usecase.ports.LessonRepository;

public class ViewProfitUseCase {
    private final LessonRepository lessonRepository;
    private final LessonCostRepository lessonCostRepository;

    public ViewProfitUseCase(LessonRepository lessonRepository, LessonCostRepository lessonCostRepository) {
        this.lessonRepository = lessonRepository;
        this.lessonCostRepository = lessonCostRepository;
    }

    public ProfitSummary execute() {
        int lessonCount = 0;
        for (Lesson lesson : lessonRepository.findAll()) {
            lessonCount++;
        }
        LessonCost cost = lessonCostRepository.load();
        int totalPointsUsed = lessonCount * cost.getLessonCost();
        int totalProfit = totalPointsUsed * cost.getPointValue();
        return new ProfitSummary(totalPointsUsed, totalProfit);
    }
}
