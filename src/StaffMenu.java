import java.util.*;
public class StaffMenu {

    public static void showMenu(){
        Scanner sc = new Scanner(System.in);
        while (true){
            try{
                System.out.println("""
                
                1:登録 
                2:照会 
                3:変更 
                4:ポイント 
                5:予約代行 
                6:取消 
                0:戻る""");

                int choice = Integer.parseInt(sc.nextLine());
                switch (choice){
                    case 1 -> {
                        addStudent();
                    }
                    case 2 -> {
                        viewStudents();
                    }
                    case 3 -> {
                        changeStudent();
                    }
                    case 4 -> {
                        addPoints();
                    }
                    case 5 -> {
                        resreveLesson();
                    }
                    case 6 -> {
                        cancelLesson();
                    }
                    case 0-> {
                        return;
                    }
                    default -> System.out.println("無効な入力！！！");
                }
            } catch (Exception e) {
                System.out.println("数字を入力してください");
            }
        }

    }
    public static void viewStudents() {
        System.out.println("\n------------生徒一覧------------------");

        for (Student s : Main.students) {
            System.out.println(
                            "名前=" + s.getName() + "  " +
                            "ID=" + s.getId() + "  " +
                            "コース=" + s.getCourse() + "  " +
                            "ポイント=" + s.getPoints()
            );
        }

        System.out.println("----------------------------------------------------");
    }
}
