package app;

import adapter.cli.CoursePrompter;
import adapter.cli.StaffMenu;
import adapter.cli.StudentMenu;
import adapter.cli.TeacherMenu;
import adapter.persistence.CsvLessonCostRepository;
import adapter.persistence.CsvLessonRepository;
import adapter.persistence.CsvStudentRepository;
import adapter.persistence.CsvTeacherRepository;
import usecase.*;
import usecase.ports.LessonCostRepository;
import usecase.ports.LessonRepository;
import usecase.ports.StudentRepository;
import usecase.ports.TeacherRepository;
import util.Design;

import java.util.Scanner;

public class EnglishSchoolMain {
    public static void main(String[] args) {
        StudentRepository studentRepository = new CsvStudentRepository("students.csv");
        TeacherRepository teacherRepository = new CsvTeacherRepository("teachers.csv");
        LessonRepository lessonRepository = new CsvLessonRepository("lessons.csv");
        LessonCostRepository lessonCostRepository = new CsvLessonCostRepository("lesson_cost.csv");

        AddStudentUseCase addStudentUseCase = new AddStudentUseCase(studentRepository);
        ViewStudentsUseCase viewStudentsUseCase = new ViewStudentsUseCase(studentRepository);
        ViewStudentUseCase viewStudentUseCase = new ViewStudentUseCase(studentRepository);
        ChangeStudentCourseUseCase changeStudentCourseUseCase = new ChangeStudentCourseUseCase(studentRepository);
        RemoveStudentUseCase removeStudentUseCase = new RemoveStudentUseCase(studentRepository);
        AddPointsUseCase addPointsUseCase = new AddPointsUseCase(studentRepository);

        AddTeacherUseCase addTeacherUseCase = new AddTeacherUseCase(teacherRepository);
        ViewTeachersUseCase viewTeachersUseCase = new ViewTeachersUseCase(teacherRepository);

        ReserveLessonUseCase reserveLessonUseCase = new ReserveLessonUseCase(
                studentRepository, teacherRepository, lessonRepository, lessonCostRepository
        );
        ViewLessonsUseCase viewLessonsUseCase = new ViewLessonsUseCase(lessonRepository);
        ViewStudentLessonsUseCase viewStudentLessonsUseCase = new ViewStudentLessonsUseCase(lessonRepository);
        CancelLessonUseCase cancelLessonUseCase = new CancelLessonUseCase(lessonRepository);
        RecordAttendanceUseCase recordAttendanceUseCase = new RecordAttendanceUseCase(lessonRepository, studentRepository);

        ViewLessonCostUseCase viewLessonCostUseCase = new ViewLessonCostUseCase(lessonCostRepository);
        ChangeLessonCostUseCase changeLessonCostUseCase = new ChangeLessonCostUseCase(lessonCostRepository);
        ViewProfitUseCase viewProfitUseCase = new ViewProfitUseCase(lessonRepository, lessonCostRepository);

        Scanner scanner = new Scanner(System.in);
        CoursePrompter coursePrompter = new CoursePrompter();

        StudentMenu studentMenu = new StudentMenu(
                scanner,
                viewStudentUseCase,
                reserveLessonUseCase,
                viewStudentLessonsUseCase,
                viewTeachersUseCase,
                coursePrompter
        );

        TeacherMenu teacherMenu = new TeacherMenu(
                scanner,
                viewTeachersUseCase,
                viewLessonsUseCase,
                recordAttendanceUseCase
        );

        StaffMenu staffMenu = new StaffMenu(
                scanner,
                addStudentUseCase,
                viewStudentsUseCase,
                changeStudentCourseUseCase,
                addPointsUseCase,
                reserveLessonUseCase,
                viewLessonsUseCase,
                cancelLessonUseCase,
                viewLessonCostUseCase,
                changeLessonCostUseCase,
                addTeacherUseCase,
                viewTeachersUseCase,
                removeStudentUseCase,
                viewProfitUseCase,
                viewStudentUseCase,
                coursePrompter
        );

        while (true) {
            System.out.println(Design.LINE);
            System.out.print("""
              
                        ***** 英会話スクールシステム *****
                        
                                  Main Menu
                                  
                          1. 生徒
                          2. 講師
                          3. 受付
                          0. 終了してデータをCSVに書く
                          
                        """);
            while (true) {
                System.out.print("番号を選択してください >>> ");
                try {
                    int choice = Integer.parseInt(scanner.nextLine());
                    switch (choice) {
                        case 1 -> studentMenu.showMenu();
                        case 2 -> teacherMenu.showMenu();
                        case 3 -> staffMenu.showMenu();
                        case 0 -> {
                            studentRepository.saveAll();
                            teacherRepository.saveAll();
                            lessonRepository.saveAll();
                            lessonCostRepository.save(lessonCostRepository.load());
                            System.out.println("保存が終了しました。");
                            return;
                        }
                        default -> {
                            System.out.println("無効な入力！！！");
                            continue;
                        }
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("数字を入力してください");
                }
            }
        }
    }
}
