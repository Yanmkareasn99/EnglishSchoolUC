import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

public class CsvUtil {

    public static void saveStudents(List<Student> students) {
        try (FileWriter fw = new FileWriter("students.csv")) {

            fw.write("id,name,age,sex,phone,email,point,course,lesson,status,address,pointExpireAt\n");

            for (Student s : students) {
                fw.write(s.toCsv() + "\n");
            }

        } catch (IOException e) {
            System.out.println("学生CSV保存エラー");
        }
    }

    public static void saveTeachers(List<Teacher> teachers) {
        try (FileWriter fw = new FileWriter("teachers.csv")) {

            fw.write("id,name\n");

            for (Teacher t : teachers) {
                fw.write(t.toCsv() + "\n");
            }

        } catch (IOException e) {
            System.out.println("講師CSV保存エラー");
        }
    }

    public static void saveLessons(List<Lesson> lessons) {
        try (FileWriter fw = new FileWriter("lessons.csv")) {

            fw.write("lessonId,studentId,teacherId,course,dateTime,status\n");

            for (Lesson l : lessons) {
                fw.write(l.toCsv() + "\n");
            }

        } catch (IOException e) {
            System.out.println("レッスンCSV保存エラー");
        }
    }

    public static void loadStudents(List<Student> students) {
        try (FileReader fr = new FileReader("students.csv");
             BufferedReader br = new BufferedReader(fr)) {

            String line = br.readLine();

            while ((line = br.readLine()) != null) {
                String[] d = line.split(",");

                LocalDateTime pointExpireAt = null;
                if (d.length > 11 && !d[11].isEmpty()) {
                    pointExpireAt = DateTimeUtil.parse(d[11]);
                }

                Student s = new Student(
                        Integer.parseInt(d[0]),         // id
                        d[1],                           // name
                        Integer.parseInt(d[2]),         // age
                        d[3],                           // sex
                        Integer.parseInt(d[4]),         // phone
                        d[5],                           // email
                        d[7],                           // course
                        Integer.parseInt(d[6]),         // point
                        d[9],                           // status
                        d[8],                           // lesson
                        d.length > 10 ? d[10] : "",     // address
                        pointExpireAt
                );

                students.add(s);
            }

        } catch (IOException e) {
            System.out.println("学生CSV読込なし（初回起動）");
        }
    }

    public static void loadTeachers(List<Teacher> teachers) {
        try (FileReader fr = new FileReader("teachers.csv");
             BufferedReader br = new BufferedReader(fr)) {

            String line = br.readLine();

            while ((line = br.readLine()) != null) {
                String[] d = line.split(",");

                Teacher t = new Teacher(
                        Integer.parseInt(d[0]), // id
                        d[1]                     // name
                );

                teachers.add(t);
            }

        } catch (IOException e) {
            System.out.println("講師CSV読込なし（初回起動）");
        }
    }

    public static void loadLessons(List<Lesson> lessons) {
        try (FileReader fr = new FileReader("lessons.csv");
             BufferedReader br = new BufferedReader(fr)) {

            String line = br.readLine();

            while ((line = br.readLine()) != null) {
                String[] d = line.split(",");

                String status = d.length > 5 ? d[5] : "予約";

                Lesson l = new Lesson(
                        Integer.parseInt(d[0]), // lessonId
                        Integer.parseInt(d[1]), // studentId
                        Integer.parseInt(d[2]), // teacherId
                        d[3],                   // course
                        d[4],                   // dateTime
                        status
                );

                lessons.add(l);
            }

        } catch (IOException e) {
            System.out.println("レッスンCSV読込なし（初回起動）");
        }
    }
}
