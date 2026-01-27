import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class StudentMenu {
    public static void showMenu() {
        while (true) {
            try {
                System.out.println("""
                        
                        1: 生徒情報確認
                        2: レッスン予約
                        0: 戻る
                        """);
                int choice = Integer.parseInt(Main.sc.nextLine());
                switch (choice) {
                    case 1 -> viewStudent();
                    case 2 -> reserveLesson();
                    case 0 -> { return; }
                    default -> System.out.println("無効な入力です。");
                }
            } catch (NumberFormatException e) {
                System.out.println("数字を入力してください。");
            }
        }
    }

    private static void viewStudent() {
        System.out.print("生徒ID: ");
        int id = Integer.parseInt(Main.sc.nextLine());

        for (Student s : Main.students) {
            if (s.getId() == id) {
                System.out.println(
                        "ID=" + s.getId() +
                                " 名前=" + s.getName() +
                                " コース=" + s.getCourse() +
                                " ポイント=" + s.getPoints() +
                                " 最新レッスン=" + s.getLesson()
                );
                return;
            }
        }
        System.out.println("生徒が見つかりません。");
    }

    private static void reserveLesson() {
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

    private static Student findStudent(int studentId) {
        for (Student s : Main.students) {
            if (s.getId() == studentId) {
                return s;
            }
        }
        return null;
    }
}
