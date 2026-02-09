package usecase.ports;

import domain.Teacher;
import java.util.List;

public interface TeacherRepository {
    List<Teacher> findAll();
    Teacher findById(int id);
    void add(Teacher teacher);
    int nextId();
    void saveAll();
}
