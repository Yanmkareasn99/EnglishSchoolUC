package adapter.persistence;

import domain.Lesson;
import usecase.ports.LessonRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class CsvLessonRepository implements LessonRepository {
    private final List<Lesson> lessons = new ArrayList<>();
    private final String path;

    public CsvLessonRepository(String path) {
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
                String status = d.length > 5 ? d[5] : "予約";

                Lesson lesson = new Lesson(
                        Integer.parseInt(d[0]),
                        Integer.parseInt(d[1]),
                        Integer.parseInt(d[2]),
                        d[3],
                        d[4],
                        status
                );
                lessons.add(lesson);
            }

        } catch (Exception e) {
            System.out.println("CSVがないです。");
        }
    }

    @Override
    public List<Lesson> findAll() {
        return lessons;
    }

    @Override
    public Lesson findById(int id) {
        for (Lesson lesson : lessons) {
            if (lesson.getLessonId() == id) {
                return lesson;
            }
        }
        return null;
    }

    @Override
    public void add(Lesson lesson) {
        lessons.add(lesson);
    }

    @Override
    public int nextId() {
        return lessons.size() + 1;
    }

    @Override
    public void saveAll() {
        try (FileWriter fw = new FileWriter(path)) {
            fw.write("lessonId,studentId,teacherId,course,dateTime,status\n");
            for (Lesson lesson : lessons) {
                fw.write(lesson.toCsv() + "\n");
            }
        } catch (Exception e) {
            System.out.println("レッスンCSV保存エラー");
        }
    }
}
