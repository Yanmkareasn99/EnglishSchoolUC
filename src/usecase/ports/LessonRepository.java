package usecase.ports;

import domain.Lesson;
import java.util.List;

public interface LessonRepository {
    List<Lesson> findAll();
    Lesson findById(int id);
    void add(Lesson lesson);
    int nextId();
    void saveAll();
}
