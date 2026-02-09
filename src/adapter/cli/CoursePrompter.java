package adapter.cli;

import java.util.Scanner;

public class CoursePrompter {
    private static final String[] COURSES = {"日常会話", "留学準備", "ビジネス", "キッズ"};
    private static final String[] LEVELS = {"初級", "上級"};

    public String selectCourse(Scanner scanner) {
        int courseIndex = selectCourseIndex(scanner);
        int levelIndex = selectLevelIndex(scanner);
        boolean toeic = selectToeicOption(scanner);

        String course = COURSES[courseIndex] + " " + LEVELS[levelIndex];
        if (toeic) {
            course += " + TOEIC";
        }
        return course;
    }

    public String selectLessonType(Scanner scanner) {
        while (true) {
            System.out.println("""

                    レッスンタイプ
                    1. グループレッスン
                    2. マンツーマン
                    番号を入力してください>>> """);
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> { return "グループレッスン"; }
                    case 2 -> { return "マンツーマン"; }
                    default -> System.out.println("無効な入力です。");
                }
            } catch (NumberFormatException ignored) {
                System.out.println("無効な入力です。");
            }
        }
    }

    private int selectCourseIndex(Scanner scanner) {
        while (true) {
            System.out.println("""

                    コース
                    1. 日常会話
                    2. 留学準備
                    3. ビジネス
                    4. キッズ
                    """);
            System.out.print("番号を入力してください>>> ");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> { return 0; }
                    case 2 -> { return 1; }
                    case 3 -> { return 2; }
                    case 4 -> { return 3; }
                    default -> System.out.println("無効な入力です。");
                }
            } catch (NumberFormatException ignored) {
                System.out.println("無効な入力です。");
            }
        }
    }

    private int selectLevelIndex(Scanner scanner) {
        while (true) {
            System.out.println("""

                    レベル
                    1. 初級
                    2. 上級
                    """);
            System.out.print("番号を入力してください>>> ");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> { return 0; }
                    case 2 -> { return 1; }
                    default -> System.out.println("無効な入力です。");
                }
            } catch (NumberFormatException ignored) {
                System.out.println("無効な入力です。");
            }
        }
    }

    private boolean selectToeicOption(Scanner scanner) {
        while (true) {
            System.out.println("""

                    TOEIC
                    1. あり
                    2. なし
                    """);
            System.out.print("番号を入力してください>>> ");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> { return true; }
                    case 2 -> { return false; }
                    default -> System.out.println("無効な入力です。");
                }
            } catch (NumberFormatException ignored) {
                System.out.println("無効な入力です。");
            }
        }
    }
}
