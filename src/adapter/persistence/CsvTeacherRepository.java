package adapter.persistence;

import domain.Teacher;
import usecase.ports.TeacherRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class CsvTeacherRepository implements TeacherRepository {
    private final List<Teacher> teachers = new ArrayList<>();
    private final String path;

    public CsvTeacherRepository(String path) {
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
                Teacher teacher = new Teacher(
                        Integer.parseInt(d[0]),
                        d[1]
                );
                teachers.add(teacher);
            }

        } catch (Exception e) {
            System.out.println("CSVがないです。");
        }
    }

    @Override
    public List<Teacher> findAll() {
        return teachers;
    }

    @Override
    public Teacher findById(int id) {
        for (Teacher teacher : teachers) {
            if (teacher.getId() == id) {
                return teacher;
            }
        }
        return null;
    }

    @Override
    public void add(Teacher teacher) {
        teachers.add(teacher);
    }

    @Override
    public int nextId() {
        return teachers.size() + 1;
    }

    @Override
    public void saveAll() {
        try (FileWriter fw = new FileWriter(path)) {
            fw.write("id,name\n");
            for (Teacher teacher : teachers) {
                fw.write(teacher.toCsv() + "\n");
            }
        } catch (Exception e) {
            System.out.println("講師CSV保存エラー");
        }
    }
}
