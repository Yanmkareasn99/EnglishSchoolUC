package usecase.ports;

import domain.LessonCost;

public interface LessonCostRepository {
    LessonCost load();
    void save(LessonCost cost);
}
