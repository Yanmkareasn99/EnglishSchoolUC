package usecase;

import domain.Teacher;
import usecase.ports.TeacherRepository;

import java.util.List;

public class ViewTeachersUseCase {
    private final TeacherRepository teacherRepository;

    public ViewTeachersUseCase(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public List<Teacher> execute() {
        return teacherRepository.findAll();
    }
}
