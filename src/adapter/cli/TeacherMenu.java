package adapter.cli;

import domain.Lesson;
import domain.Teacher;
import usecase.RecordAttendanceUseCase;
import usecase.UseCaseResult;
import usecase.ViewLessonsUseCase;
import usecase.ViewTeachersUseCase;
import util.Design;

import java.util.List;
import java.util.Scanner;

public class TeacherMenu {
    private final Scanner scanner;
    private final ViewTeachersUseCase viewTeachersUseCase;
    private final ViewLessonsUseCase viewLessonsUseCase;
    private final RecordAttendanceUseCase recordAttendanceUseCase;

    public TeacherMenu(Scanner scanner,
                       ViewTeachersUseCase viewTeachersUseCase,
                       ViewLessonsUseCase viewLessonsUseCase,
                       RecordAttendanceUseCase recordAttendanceUseCase) {
        this.scanner = scanner;
        this.viewTeachersUseCase = viewTeachersUseCase;
        this.viewLessonsUseCase = viewLessonsUseCase;
        this.recordAttendanceUseCase = recordAttendanceUseCase;
    }

    public void showMenu() {
        int teacherId;
        Design.clearScreen();
        System.out.println(Design.LINE);
        while (true) {
            System.out.print("講師ID: ");
            try {
                teacherId = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("数字を入力してください！！！");
                continue;
            }
            Teacher currentTeacher = findTeacher(teacherId);
            if (currentTeacher == null) {
                System.out.println("先生が見つかりません");
                continue;
            }
            while (true) {
                Design.clearScreen();
                System.out.println(Design.LINE);
                System.out.print("""
                        
                        1: レッスン一覧
                        2: 出席登録
                        0: 戻る
                        
                        """);
                while (true) {
                    System.out.print("番号を入力してください>>> ");
                    try {
                        int choice = Integer.parseInt(scanner.nextLine());
                        switch (choice) {
                            case 1 -> viewLessons();
                            case 2 -> recordAttendance();
                            case 0 -> { return; }
                            default -> {
                                System.out.println("無効な入力です。");
                                continue;
                            }
                        }
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("数字を入力してください。");
                    }
                }
            }
        }
    }

    private Teacher findTeacher(int teacherId) {
        List<Teacher> teachers = viewTeachersUseCase.execute();
        for (Teacher teacher : teachers) {
            if (teacher.getId() == teacherId) {
                return teacher;
            }
        }
        return null;
    }

    private void viewLessons() {
        System.out.println(Design.LINE);

        List<Lesson> lessons = viewLessonsUseCase.execute();
        if (lessons.isEmpty()) {
            System.out.println("該当レッスンがありません。");
            return;
        }
        for (Lesson lesson : lessons) {
            System.out.println(
                    "レッスンID=" + lesson.getLessonId() +
                            " 生徒ID=" + lesson.getStudentId() +
                            " レッスンタイプ=" + lesson.getLessonType() +
                            " 日時=" + lesson.getDateTime()
            );
        }
    }

    private void recordAttendance() {
        System.out.println(Design.LINE);
        System.out.print("レッスンID: ");
        int lessonId = Integer.parseInt(scanner.nextLine());

        UseCaseResult result = recordAttendanceUseCase.execute(lessonId);
        System.out.println(result.getMessage());
    }
}
