import java.time.LocalDateTime;

public class Student {

    private int id, age, points, phone;
    private String name, sex, email, course, status, address, lesson;
    private LocalDateTime pointExpireAt;

    public Student(int id, String name, int age, String sex,
                   int phone, String email, String course,
                   int points, String status, String lesson, String address) {
        this(id, name, age, sex, phone, email, course, points, status, lesson, address, null);
    }

    public Student(int id, String name, int age, String sex,
                   int phone, String email, String course,
                   int points, String status, String lesson, String address,
                   LocalDateTime pointExpireAt) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.phone = phone;
        this.email = email;
        this.course = course;
        this.points = points;
        this.status = status;
        this.lesson = lesson;
        this.address = address;
        this.pointExpireAt = pointExpireAt;
    }

    public int getId() { return id; }
    public int getAge() { return age; }
    public int getPoints() {
        normalizePoints(LocalDateTime.now());
        return points;
    }
    public int getPhone() { return phone; }
    public String getName() { return name; }
    public String getSex() { return sex; }
    public String getEmail() { return email; }
    public String getCourse() { return course; }
    public String getStatus() { return status; }
    public String getAddress() { return address; }
    public String getLesson() { return lesson; }

    public void setCourse(String course) { this.course = course; }
    public void setLesson(String lesson) { this.lesson = lesson; }
    public void setStatus(String status) { this.status = status; }

    //+200 increments only
    public boolean addPoints(int p) {
        if (p % 200 != 0) {
            return false;
        }
        LocalDateTime now = LocalDateTime.now();
        normalizePoints(now);
        points += p;
        pointExpireAt = now.plusMonths(12);
        return true;
    }

    public boolean consumePoints(int cost) {
        LocalDateTime now = LocalDateTime.now();
        normalizePoints(now);
        if (points < cost) {
            return false;
        }
        points -= cost;
        return true;
    }

    public String toCsv() {
        return id + "," + name + "," + age + "," + sex + "," +
                phone + "," + email + "," + points + "," +
                course + "," + lesson + "," + status + "," + address + "," +
                formatPointExpireAt();
    }

    private boolean isPointsExpired(LocalDateTime now) {
        if (pointExpireAt == null) {
            return false;
        }
        return now.isAfter(pointExpireAt);
    }

    private String formatPointExpireAt() {
        if (pointExpireAt == null) {
            return "";
        }
        return DateTimeUtil.format(pointExpireAt);
    }

    private void normalizePoints(LocalDateTime now) {
        if (isPointsExpired(now)) {
            points = 0;
            pointExpireAt = null;
        }
    }
}
