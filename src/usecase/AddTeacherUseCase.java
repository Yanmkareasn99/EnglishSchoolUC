package usecase;

import domain.Teacher;
import usecase.ports.TeacherRepository;

public class AddTeacherUseCase {
    private final TeacherRepository teacherRepository;

    public AddTeacherUseCase(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public UseCaseResult execute(String name) {
        int id = teacherRepository.nextId();
        Teacher teacher = new Teacher(id, name);
        teacherRepository.add(teacher);
        return UseCaseResult.ok("講師を登録しました。");
    }
}
