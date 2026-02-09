package usecase;

import domain.Lesson;

public class ReserveLessonResult {
    private final boolean success;
    private final boolean needsPoints;
    private final String message;
    private final Lesson lesson;

    public ReserveLessonResult(boolean success, boolean needsPoints, String message, Lesson lesson) {
        this.success = success;
        this.needsPoints = needsPoints;
        this.message = message;
        this.lesson = lesson;
    }

    public static ReserveLessonResult ok(String message, Lesson lesson) {
        return new ReserveLessonResult(true, false, message, lesson);
    }

    public static ReserveLessonResult needsPoints(String message) {
        return new ReserveLessonResult(false, true, message, null);
    }

    public static ReserveLessonResult fail(String message) {
        return new ReserveLessonResult(false, false, message, null);
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean isNeedsPoints() {
        return needsPoints;
    }

    public String getMessage() {
        return message;
    }

    public Lesson getLesson() {
        return lesson;
    }
}
