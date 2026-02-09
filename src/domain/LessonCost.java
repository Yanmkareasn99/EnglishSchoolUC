package domain;

public class LessonCost {
    private int pointValue;
    private int lessonCost;

    public LessonCost(int pointValue, int lessonCost) {
        this.pointValue = pointValue;
        this.lessonCost = lessonCost;
    }

    public int getPointValue() {
        return pointValue;
    }

    public void setPointValue(int pointValue) {
        this.pointValue = pointValue;
    }

    public int getLessonCost() {
        return lessonCost;
    }

    public void setLessonCost(int lessonCost) {
        this.lessonCost = lessonCost;
    }
}
