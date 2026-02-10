package adapter.cli;

import domain.Lesson;
import domain.Student;
import domain.Teacher;
import usecase.ReserveLessonResult;
import usecase.ReserveLessonUseCase;
import usecase.ViewStudentLessonsUseCase;
import usecase.ViewStudentUseCase;
import usecase.ViewTeachersUseCase;
import util.DateTimeUtil;
import util.Design;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class StudentMenu {
    private final Scanner scanner;
    private final ViewStudentUseCase viewStudentUseCase;
    private final ReserveLessonUseCase reserveLessonUseCase;
    private final ViewStudentLessonsUseCase viewStudentLessonsUseCase;
    private final ViewTeachersUseCase viewTeachersUseCase;
    private final CoursePrompter coursePrompter;

    public StudentMenu(Scanner scanner,
                       ViewStudentUseCase viewStudentUseCase,
                       ReserveLessonUseCase reserveLessonUseCase,
                       ViewStudentLessonsUseCase viewStudentLessonsUseCase,
                       ViewTeachersUseCase viewTeachersUseCase,
                       CoursePrompter coursePrompter) {
        this.scanner = scanner;
        this.viewStudentUseCase = viewStudentUseCase;
        this.reserveLessonUseCase = reserveLessonUseCase;
        this.viewStudentLessonsUseCase = viewStudentLessonsUseCase;
        this.viewTeachersUseCase = viewTeachersUseCase;
        this.coursePrompter = coursePrompter;
    }

    public void showMenu() {
        System.out.println(Design.LINE);
        while(true){
            System.out.print("生徒ID: ");
            int studentId;
            try {
                studentId = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("数字を入力してください！！！");
                continue;
            }
            Student currentStudent = viewStudentUseCase.execute(studentId);
            if (currentStudent == null) {
                System.out.println("在籍中の生徒が見つかりません");
                continue;
            }

            while (true) {
                Design.clearScreen();
                System.out.println(Design.LINE);
                System.out.println("生徒: " + currentStudent.getName() + " (ID: " + studentId + ")");
                System.out.print("""

                        1: 生徒情報確認
                        2: レッスン予約
                        3: レッスン確認
                        0: 戻る

                        """);
                while (true) {
                    System.out.print("番号を入力してください>>> ");
                    try {
                        int choice = Integer.parseInt(scanner.nextLine());
                        switch (choice) {
                            case 1 -> viewStudent(studentId);
                            case 2 -> reserveLesson(studentId);
                            case 3 -> viewLessons(studentId);
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

    private void viewStudent(int studentId) {
        System.out.println(Design.LINE);
        Student student = viewStudentUseCase.execute(studentId);
        if (student == null) {
            System.out.println("生徒が見つかりません");
            return;
        }
        System.out.println(
                "ID=" + student.getId() +
                        " 名前=" + student.getName() +
                        " コース=" + student.getCourse() +
                        " ポイント=" + student.getPoints() +
                        " レッスン=" + student.getLesson() +
                        " 登録日=" + student.getRegisterDate()
        );
    }

    private void reserveLesson(int studentId) {
        System.out.println(Design.LINE);

        System.out.print("講師ID: ");
        int teacherId;
        try {
            teacherId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("数字を入力してください！！！");
            return;
        }

        String lessonType = coursePrompter.selectLessonType(scanner);

        System.out.print("日時 (例: 2026-02-01 18): ");
        String input = scanner.nextLine();
        LocalDateTime dateTime;
        try {
            dateTime = DateTimeUtil.parse(input);
        } catch (Exception e) {
            System.out.println("日時の形式が正しくありません。");
            return;
        }

        ReserveLessonResult result = reserveLessonUseCase.execute(studentId, teacherId, lessonType, dateTime);
        System.out.println(result.getMessage());
    }

    private void viewLessons(int studentId) {
        System.out.println(Design.LINE);
        List<Lesson> lessons = viewStudentLessonsUseCase.execute(studentId);
        if (lessons.isEmpty()) {
            System.out.println("レッスンがありません。");
            return;
        }
        for (Lesson lesson : lessons) {
            System.out.println(
                    "レッスンID=" + lesson.getLessonId() +
                            " 講師=" + getTeacherName(lesson.getTeacherId()) +
                            " レッスンタイプ=" + lesson.getLessonType() +
                            " 日時=" + lesson.getDateTime() + "時"
            );
        }
    }

    private String getTeacherName(int teacherId) {
        List<Teacher> teachers = viewTeachersUseCase.execute();
        for (Teacher teacher : teachers) {
            if (teacher.getId() == teacherId) {
                return teacher.getName();
            }
        }
        return "";
    }
}
