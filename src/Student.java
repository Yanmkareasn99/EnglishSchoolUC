public class Student {
    private int id, age, points, phone;
    private String name, sex, email, course, status, address;

    public Student(int id, int age, int points, int phone, String name, String sex, String email, String course, String status){
        this.id = id; this.name = name; this.age = age; this.sex = sex;
        this.phone = phone; this.email = email; this.course = course;
        this.points = points; this.status = status; this.address = address;
    }

    public int getId() {
        return id;
    }

    public int getAge() {
        return age;
    }

    public int getPoints() {
        return points;
    }

    public int getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public String getEmail() {
        return email;
    }

    public String getCourse() {
        return course;
    }

    public String getStatus() {
        return status;
    }

    public String getAddress() {
        return address;
    }

    public String toCsv() {
        return id + "," + name + "," + age + "," + sex + "," + phone + "," + email + "," + points + "," + course + "," + status + "\n";
        }
}

