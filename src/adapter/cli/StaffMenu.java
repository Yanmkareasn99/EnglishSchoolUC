package adapter.cli;

import domain.Lesson;
import domain.LessonCost;
import domain.Student;
import domain.Teacher;
import usecase.*;
import util.DateTimeUtil;
import util.Design;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class StaffMenu {
    private final Scanner scanner;
    private final AddStudentUseCase addStudentUseCase;
    private final ViewStudentsUseCase viewStudentsUseCase;
    private final ChangeStudentCourseUseCase changeStudentCourseUseCase;
    private final AddPointsUseCase addPointsUseCase;
    private final ReserveLessonUseCase reserveLessonUseCase;
    private final ViewLessonsUseCase viewLessonsUseCase;
    private final CancelLessonUseCase cancelLessonUseCase;
    private final ViewLessonCostUseCase viewLessonCostUseCase;
    private final ChangeLessonCostUseCase changeLessonCostUseCase;
    private final AddTeacherUseCase addTeacherUseCase;
    private final ViewTeachersUseCase viewTeachersUseCase;
    private final RemoveStudentUseCase removeStudentUseCase;
    private final ViewProfitUseCase viewProfitUseCase;
    private final ViewStudentUseCase viewStudentUseCase;
    private final CoursePrompter coursePrompter;

    public StaffMenu(Scanner scanner,
                     AddStudentUseCase addStudentUseCase,
                     ViewStudentsUseCase viewStudentsUseCase,
                     ChangeStudentCourseUseCase changeStudentCourseUseCase,
                     AddPointsUseCase addPointsUseCase,
                     ReserveLessonUseCase reserveLessonUseCase,
                     ViewLessonsUseCase viewLessonsUseCase,
                     CancelLessonUseCase cancelLessonUseCase,
                     ViewLessonCostUseCase viewLessonCostUseCase,
                     ChangeLessonCostUseCase changeLessonCostUseCase,
                     AddTeacherUseCase addTeacherUseCase,
                     ViewTeachersUseCase viewTeachersUseCase,
                     RemoveStudentUseCase removeStudentUseCase,
                     ViewProfitUseCase viewProfitUseCase,
                     ViewStudentUseCase viewStudentUseCase,
                     CoursePrompter coursePrompter) {
        this.scanner = scanner;
        this.addStudentUseCase = addStudentUseCase;
        this.viewStudentsUseCase = viewStudentsUseCase;
        this.changeStudentCourseUseCase = changeStudentCourseUseCase;
        this.addPointsUseCase = addPointsUseCase;
        this.reserveLessonUseCase = reserveLessonUseCase;
        this.viewLessonsUseCase = viewLessonsUseCase;
        this.cancelLessonUseCase = cancelLessonUseCase;
        this.viewLessonCostUseCase = viewLessonCostUseCase;
        this.changeLessonCostUseCase = changeLessonCostUseCase;
        this.addTeacherUseCase = addTeacherUseCase;
        this.viewTeachersUseCase = viewTeachersUseCase;
        this.removeStudentUseCase = removeStudentUseCase;
        this.viewProfitUseCase = viewProfitUseCase;
        this.viewStudentUseCase = viewStudentUseCase;
        this.coursePrompter = coursePrompter;
    }

    public void showMenu() {
        while (true) {
            Design.clearScreen();
            System.out.println(Design.LINE);
            System.out.print("""
                       
                        1: 生徒登録
                        2: 生徒一覧
                        3: 生徒情報変更
                        4: ポイント購入
                        5: レッスン予約
                        6: レッスン確認
                        7: レッスンキャンセル
                        8: レッスン単価
                        9: 講師登録
                        10: 講師一覧
                        11: 生徒退会
                        12: 売上確認
                        0: 戻る
                       """);
            while (true) {
                System.out.print("番号を入力してください>>> ");
                try {
                    int choice = Integer.parseInt(scanner.nextLine());
                    switch (choice) {
                        case 1 -> addStudent();
                        case 2 -> viewStudents();
                        case 3 -> changeStudent();
                        case 4 -> addPoints();
                        case 5 -> reserveLesson();
                        case 6 -> viewLessons();
                        case 7 -> cancelLesson();
                        case 8 -> changeLessonCost();
                        case 9 -> addTeacher();
                        case 10 -> viewTeachers();
                        case 11 -> removeStudent();
                        case 12 -> viewProfit();
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

    private void addStudent() {
        System.out.println(Design.LINE);

        System.out.print("名前: ");
        String name = scanner.nextLine();

        System.out.print("年齢: ");
        int age = Integer.parseInt(scanner.nextLine());

        System.out.print("性別: ");
        String sex = scanner.nextLine();

        System.out.print("電話番号: ");
        long phone = Long.parseLong(scanner.nextLine());

        System.out.print("Email: ");
        String email = scanner.nextLine();

        String course = coursePrompter.selectCourse(scanner);

        UseCaseResult result = addStudentUseCase.execute(
                name, age, sex, phone, email, course, LocalDate.now()
        );
        System.out.println(result.getMessage());
    }

    private void viewStudents() {
        System.out.println(Design.LINE);
        System.out.println("\n------ 生徒一覧 ------");
        List<Student> students = viewStudentsUseCase.execute();
        for (Student student : students) {
            System.out.println(
                    "ID=" + student.getId() +
                            " 名前=" + student.getName() +
                            " コース=" + student.getCourse() +
                            " ポイント=" + student.getPoints() +
                            " 登録日=" + student.getRegisterDate()
            );
        }
    }

    private void changeStudent() {
        System.out.println(Design.LINE);
        System.out.print("変更する生徒ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.print("新しいコース名: ");
        String course = scanner.nextLine();

        UseCaseResult result = changeStudentCourseUseCase.execute(id, course);
        System.out.println(result.getMessage());
    }

    private void removeStudent() {
        System.out.println(Design.LINE);
        System.out.print("退会する生徒ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        UseCaseResult result = removeStudentUseCase.execute(id);
        System.out.println(result.getMessage());
    }

    private void reserveLesson() {
        System.out.println(Design.LINE);
        viewStudents();

        System.out.print("生徒ID: ");
        int studentId = Integer.parseInt(scanner.nextLine());
        Student student = viewStudentUseCase.execute(studentId);
        if (student == null) {
            System.out.println("在籍中の生徒が見つかりません。");
            return;
        }

        viewTeachers();

        System.out.print("講師ID: ");
        int teacherId = Integer.parseInt(scanner.nextLine());

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
        if (result.isNeedsPoints()) {
            System.out.println(result.getMessage());
            while (true) {
                try {
                    System.out.println("""
        ポイントを購入しますか？
       1. はい
       2. いいえ
           
    番号を入力してください>>> """);
                    switch (Integer.parseInt(scanner.nextLine())) {
                        case 1 -> {
                            addPoints();
                            return;
                        }
                        case 2 -> {
                            return;
                        }
                        default -> System.out.println("無効な入力！！！");
                    }
                } catch (Exception e) {
                    System.out.println("数字を入力してください！！！");
                }
            }
        }
        System.out.println(result.getMessage());
    }

    private void viewLessons() {
        System.out.println(Design.LINE);
        List<Lesson> lessons = viewLessonsUseCase.execute();
        for (Lesson lesson : lessons) {
            System.out.println(
                    "レッスンID=" + lesson.getLessonId() +
                            " 生徒ID=" + lesson.getStudentId() +
                            " 講師ID=" + lesson.getTeacherId() +
                            " レッスンタイプ=" + lesson.getLessonType() +
                            " 日時=" + lesson.getDateTime() + "時"
            );
        }
    }

    private void cancelLesson() {
        System.out.println(Design.LINE);
        System.out.print("取消するレッスンID: ");
        int lessonId = Integer.parseInt(scanner.nextLine());

        UseCaseResult result = cancelLessonUseCase.execute(lessonId);
        System.out.println(result.getMessage());
    }

    private void addTeacher() {
        System.out.println(Design.LINE);

        System.out.print("講師名: ");
        String name = scanner.nextLine();

        UseCaseResult result = addTeacherUseCase.execute(name);
        System.out.println(result.getMessage());
    }

    private void viewTeachers() {
        System.out.println(Design.LINE);
        System.out.println("\n------ 講師一覧 ------");
        List<Teacher> teachers = viewTeachersUseCase.execute();
        for (Teacher teacher : teachers) {
            System.out.println(
                    "ID=" + teacher.getId() +
                            " 名前=" + teacher.getName()
            );
        }
    }

    private void addPoints() {
        System.out.println(Design.LINE);
        viewStudents();
        System.out.print("生徒ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.print("追加ポイント（200単位）: ");
        int p = Integer.parseInt(scanner.nextLine());

        UseCaseResult result = addPointsUseCase.execute(id, p);
        System.out.println(result.getMessage());
    }

    private void viewLessonCost() {
        LessonCost cost = viewLessonCostUseCase.execute();
        System.out.println("\n------ 単価 ------");
        System.out.println("レッスン単価=" + cost.getLessonCost() + "ポイント");
        System.out.println("ポイント単価=" + cost.getPointValue() + "円");
    }

    private void changeLessonCost() {
        System.out.println(Design.LINE);
        viewLessonCost();
        while (true) {
            try {
                System.out.println("""
            単価を変える
       1. はい
       2. いいえ
           
    番号を入力してください>>> """);
                switch (Integer.parseInt(scanner.nextLine())) {
                    case 1 -> {
                        System.out.println(Design.LINE);
                        System.out.print("一ポイントの単価を入力してください>>> ");
                        int pointValue = Integer.parseInt(scanner.nextLine());
                        System.out.print("レッスン1コマの単価を入力してください>>> ");
                        int lessonCost = Integer.parseInt(scanner.nextLine());
                        UseCaseResult result = changeLessonCostUseCase.execute(pointValue, lessonCost);
                        System.out.println(result.getMessage());
                        return;
                    }
                    case 2 -> {
                        return;
                    }
                    default -> System.out.println("無効な入力！！！");
                }
            } catch (Exception e) {
                System.out.println("数字を入力してください！！！");
            }
        }
    }

    private void viewProfit() {
        System.out.println(Design.LINE);
        ProfitSummary summary = viewProfitUseCase.execute();
        System.out.println(Design.LINE);
        System.out.println("ポイント合計=" + summary.getTotalPointsUsed());
        System.out.println("売上=" + summary.getTotalProfit() + "円");
    }
}
