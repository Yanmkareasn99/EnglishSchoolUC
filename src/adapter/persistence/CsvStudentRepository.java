package adapter.persistence;

import domain.Student;
import usecase.ports.StudentRepository;
import util.DateTimeUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CsvStudentRepository implements StudentRepository {
    private final List<Student> students = new ArrayList<>();
    private final String path;

    public CsvStudentRepository(String path) {
        this.path = path;
        load();
    }

    private void load() {
        try (FileReader fr = new FileReader(path);
             BufferedReader br = new BufferedReader(fr)) {

            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] d = line.split(",");

                LocalDateTime pointExpireAt = null;
                if (d.length > 11 && !d[11].isEmpty()) {
                    pointExpireAt = DateTimeUtil.parse(d[11]);
                }
                LocalDate registerDate = null;
                if (d.length > 12 && !d[12].isEmpty()) {
                    registerDate = LocalDate.parse(d[12]);
                }

                Student student = new Student(
                        Integer.parseInt(d[0]),         // id
                        d[1],                           // name
                        Integer.parseInt(d[2]),         // age
                        d[3],                           // sex
                        Long.parseLong(d[4]),           // phone
                        d[5],                           // email
                        d[7],                           // course
                        Integer.parseInt(d[6]),         // point
                        d[9],                           // status
                        d[8],                           // lesson
                        d.length > 10 ? d[10] : "",     // address
                        pointExpireAt,
                        registerDate
                );

                students.add(student);
            }

        } catch (Exception e) {
            System.out.println("CSVがないです。");
        }
    }

    @Override
    public List<Student> findAll() {
        return students;
    }

    @Override
    public Student findById(int id) {
        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }

    @Override
    public void add(Student student) {
        students.add(student);
    }

    @Override
    public void update(Student student) {
        // In-memory list already updated by reference.
    }

    @Override
    public int nextId() {
        return students.size() + 1;
    }

    @Override
    public void saveAll() {
        try (FileWriter fw = new FileWriter(path)) {
            fw.write("id,name,age,sex,phone,email,point,course,lesson,status,address,pointExpireAt,registerDate\n");
            for (Student student : students) {
                fw.write(student.toCsv() + "\n");
            }
        } catch (Exception e) {
            System.out.println("生徒のCSV保存エラー");
        }
    }
}
