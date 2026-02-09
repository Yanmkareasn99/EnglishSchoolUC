package usecase.ports;

import domain.Student;
import java.util.List;

public interface StudentRepository {
    List<Student> findAll();
    Student findById(int id);
    void add(Student student);
    void update(Student student);
    int nextId();
    void saveAll();
}
