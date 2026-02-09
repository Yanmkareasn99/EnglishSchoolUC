package adapter.persistence;

import domain.LessonCost;
import usecase.ports.LessonCostRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

public class CsvLessonCostRepository implements LessonCostRepository {
    private final String path;
    private LessonCost cached;

    public CsvLessonCostRepository(String path) {
        this.path = path;
        this.cached = loadInternal();
    }

    @Override
    public LessonCost load() {
        return cached;
    }

    @Override
    public void save(LessonCost cost) {
        cached = cost;
        try (FileWriter fw = new FileWriter(path)) {
            fw.write("pointValue,lessonCost\n");
            fw.write(cost.getPointValue() + "," + cost.getLessonCost() + "\n");
        } catch (Exception e) {
            System.out.println("単価CSV保存エラー");
        }
    }

    private LessonCost loadInternal() {
        try (FileReader fr = new FileReader(path);
             BufferedReader br = new BufferedReader(fr)) {
            br.readLine();
            String line = br.readLine();
            if (line == null || line.isEmpty()) {
                return new LessonCost(1000, 50);
            }
            String[] d = line.split(",");
            if (d.length < 2) {
                return new LessonCost(1000, 50);
            }
            return new LessonCost(Integer.parseInt(d[0]), Integer.parseInt(d[1]));
        } catch (Exception e) {
            return new LessonCost(1000, 50);
        }
    }
}
