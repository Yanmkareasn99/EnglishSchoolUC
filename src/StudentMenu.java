import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class StudentMenu {
    public static void showMenu() {
        while (true) {
            try {
                System.out.println(Main.LINE);
                System.out.print("""
                        
                        1: 生徒情報確認
                        2: レッスン予約
                        0: 戻る
                        
                        番号を入力してください>>> """);
                int choice = Integer.parseInt(Main.sc.nextLine());
                switch (choice) {
                    case 1 -> viewStudent();
                    case 2 -> StaffMenu.reserveLesson();
                    case 0 -> { return; }
                    default -> System.out.println("無効な入力です。");
                }
            } catch (NumberFormatException e) {
                System.out.println("数字を入力してください。");
            }
        }
    }

    private static void viewStudent() {
        System.out.println(Main.LINE);
        System.out.print("生徒ID: ");
        int id = Integer.parseInt(Main.sc.nextLine());

        for (Student s : Main.students) {
            if (s.getId() == id) {
                System.out.println(
                        "ID=" + s.getId() +
                                " 名前=" + s.getName() +
                                " コース=" + s.getCourse() +
                                " ポイント=" + s.getPoints() +
                                " レッスン=" + s.getLesson() +
                                " 登録日=" + s.getRegisterDate()
                );
                return;
            }
        }
        System.out.println("生徒が見つかりません。");
    }

    private static Student findStudent(int studentId) {
        return StaffMenu.findStudent(studentId);
    }
}
