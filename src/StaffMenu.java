//Staff have all the authorities except recordAttandance();
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;

public class StaffMenu {

    public static void showMenu() {
        while (true) {
            try {
                System.out.println("""
                        
                        1: 登録
                        2: 照会
                        3: 変更
                        4: ポイント
                        5: 予約代行
                        6: 取消
                        7: 講師登録
                        8: 講師一覧
                        9: 生徒退会
                        0: 戻る
                        """);

                int choice = Integer.parseInt(Main.sc.nextLine());

                switch (choice) {
                    case 1 -> addStudent();
                    case 2 -> viewStudents();
                    case 3 -> changeStudent();
                    case 4 -> addPoints();
                    case 5 -> reserveLesson();
                    case 6 -> cancelLesson();
                    case 7 -> addTeacher();
                    case 8 -> viewTeachers();
                    case 9 -> removeStudent();
                    case 0 -> { return; }
                    default -> System.out.println("無効な入力です。");
                }

            } catch (Exception e) {
                System.out.println("数字を入力してください。");
            }
        }
    }

    public static void addStudent() {
        int id = Main.students.size()+1;

        System.out.print("名前: ");
        String name = Main.sc.nextLine();

        System.out.print("年齢: ");
        int age = Integer.parseInt(Main.sc.nextLine());

        System.out.print("性別: ");
        String sex = Main.sc.nextLine();

        System.out.print("電話番号: ");
        int phone = Integer.parseInt(Main.sc.nextLine());

        System.out.print("Email: ");
        String email = Main.sc.nextLine();

        String course = CourseUtil.selectCourse();

        Student s = new Student(
                id, name, age, sex,
                phone, email, course,
                0, "在籍", "", ""
        );

        Main.students.add(s);
        System.out.println("生徒を登録しました。");
    }

    public static void viewStudents() {
        System.out.println("\n------ 生徒一覧 ------");
        for (Student s : Main.students) {
            if (!"在籍".equals(s.getStatus())) {
                continue;
            }
            System.out.println(
                    "ID=" + s.getId() +
                            " 名前=" + s.getName() +
                            " コース=" + s.getCourse() +
                            " ポイント=" + s.getPoints()
            );
        }
    }

    public static void changeStudent() {
        System.out.print("変更する生徒ID: ");
        int id = Integer.parseInt(Main.sc.nextLine());

        for (Student s : Main.students) {
            if (s.getId() == id) {
                if (!"在籍".equals(s.getStatus())) {
                    System.out.println("在籍中の生徒が見つかりません。");
                    return;
                }
                System.out.print("新しいコース名: ");
                String course = Main.sc.nextLine();
                System.out.println("※コース変更（簡易実装）");
                System.out.println("変更前: " + s.getCourse());
                s.setCourse(course);
                System.out.println("変更後: " + course);
                return;
            }
        }
        System.out.println("生徒が見つかりません。");
    }

    public static void removeStudent() {
        System.out.print("退会する生徒ID: ");
        int id = Integer.parseInt(Main.sc.nextLine());

        for (Student s : Main.students) {
            if (s.getId() == id) {
                if (!"在籍".equals(s.getStatus())) {
                    System.out.println("すでに退学済みです。");
                    return;
                }
                s.setStatus("退学");
                System.out.println("退学処理が完了しました。");
                return;
            }
        }
        System.out.println("生徒が見つかりません。");
    }

    public static Student findStudent(int studentId) {
        for (Student s : Main.students) {
            if (s.getId() == studentId) {
                return s;
            }
        }
        return null;
    }

    public static void addPoints() {
        System.out.print("生徒ID: ");
        int id = Integer.parseInt(Main.sc.nextLine());

        for (Student s : Main.students) {
            if (s.getId() == id) {
                if (!"在籍".equals(s.getStatus())) {
                    System.out.println("在籍中の生徒が見つかりません。");
                    return;
                }
                System.out.print("追加ポイント（200単位）: ");
                int p = Integer.parseInt(Main.sc.nextLine());

                if (s.addPoints(p)) {
                    System.out.println("ポイント追加完了");
                } else {
                    System.out.println("200単位で入力してください");
                }
                return;
            }
        }
        System.out.println("生徒が見つかりません。");
    }

    public static void reserveLesson() {
        System.out.print("レッスンID: ");
        int lessonId = Integer.parseInt(Main.sc.nextLine());

        System.out.print("生徒ID: ");
        int studentId = Integer.parseInt(Main.sc.nextLine());
        Student student = findStudent(studentId);
        if (student == null || !"在籍".equals(student.getStatus())) {
            System.out.println("在籍中の生徒が見つかりません。");
            return;
        }

        System.out.print("講師ID: ");
        int teacherId = Integer.parseInt(Main.sc.nextLine());

        String course = CourseUtil.selectCourse();

        if (!student.consumePoints(50)) {
            System.out.println("ポイントが不足しています（必要: 50）。");
            return;
        }

        System.out.print("日時 (例: 2026-02-01 18:00): ");
        String input = Main.sc.nextLine();
        LocalDateTime dateTime;
        try {
            dateTime = DateTimeUtil.parse(input);
        } catch (DateTimeParseException e) {
            System.out.println("日時の形式が正しくありません。");
            return;
        }
        if (dateTime.isBefore(LocalDateTime.now())) {
            System.out.println("過去の日時は予約できません。");
            return;
        }

        Lesson l = new Lesson(lessonId, studentId, teacherId, course, DateTimeUtil.format(dateTime));
        Main.lessons.add(l);

        System.out.println("レッスンを予約しました。");
    }

    public static void cancelLesson() {
        System.out.print("取消するレッスンID: ");
        int lessonId = Integer.parseInt(Main.sc.nextLine());

        for (Lesson l : Main.lessons) {
            if (l.getLessonId() == lessonId) {
                if ("取消".equals(l.getStatus())) {
                    System.out.println("すでに取消済みです。");
                    return;
                }
                l.setStatus("取消");
                System.out.println("レッスンを取消しました。");
                return;
            }
        }
        System.out.println("レッスンが見つかりません。");
    }

    public static void addTeacher() {
        int id = Main.teachers.size() + 1;

        System.out.print("講師名: ");
        String name = Main.sc.nextLine();

        Teacher t = new Teacher(id, name);
        Main.teachers.add(t);
        System.out.println("講師を登録しました。");
    }

    public static void viewTeachers() {
        System.out.println("\n------ 講師一覧 ------");
        for (Teacher t : Main.teachers) {
            System.out.println(
                    "ID=" + t.getId() +
                            " 名前=" + t.getName()
            );
        }
    }




}
