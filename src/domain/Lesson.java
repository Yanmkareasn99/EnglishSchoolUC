package domain;

public class Lesson {

    private int lessonId;
    private int studentId;
    private int teacherId;
    private String lessonType;
    private String dateTime;
    private String status;

    public Lesson(int lessonId, int studentId, int teacherId,
                  String lessonType, String dateTime) {
        this(lessonId, studentId, teacherId, lessonType, dateTime, "予約");
    }

    public Lesson(int lessonId, int studentId, int teacherId,
                  String lessonType, String dateTime, String status) {
        this.lessonId = lessonId;
        this.studentId = studentId;
        this.teacherId = teacherId;
        this.lessonType = lessonType;
        this.dateTime = dateTime;
        this.status = status;
    }

    public int getLessonId() { return lessonId; }
    public int getStudentId() { return studentId; }
    public int getTeacherId() { return teacherId; }
    public String getLessonType() { return lessonType; }
    public String getDateTime() { return dateTime; }
    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public String toCsv() {
        return lessonId + "," + studentId + "," + teacherId + "," +
                lessonType + "," + dateTime + "," + status;
    }
}
