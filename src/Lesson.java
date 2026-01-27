public class Lesson {

    private int lessonId;
    private int studentId;
    private int teacherId;
    private String course;
    private String dateTime;
    private String status;

    public Lesson(int lessonId, int studentId, int teacherId,
                  String course, String dateTime) {
        this(lessonId, studentId, teacherId, course, dateTime, "予約");
    }

    public Lesson(int lessonId, int studentId, int teacherId,
                  String course, String dateTime, String status) {
        this.lessonId = lessonId;
        this.studentId = studentId;
        this.teacherId = teacherId;
        this.course = course;
        this.dateTime = dateTime;
        this.status = status;
    }

    public int getLessonId() { return lessonId; }
    public int getStudentId() { return studentId; }
    public int getTeacherId() { return teacherId; }
    public String getCourse() { return course; }
    public String getDateTime() { return dateTime; }
    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public String toCsv() {
        return lessonId + "," + studentId + "," + teacherId + "," +
                course + "," + dateTime + "," + status;
    }
}
