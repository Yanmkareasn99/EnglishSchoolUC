public class StaffMenu {

    public static void showMenu(){
        System.out.println("""
                
                1:登録 
                2:照会 
                3:変更 
                4:ポイント 
                5:予約代行 
                6:取消 
                0:戻る""");

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
