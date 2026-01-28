public class LessonCost {
    private static int[] costs = new int[]{1000,50};

    public static void changeCost(){
        System.out.println(Main.LINE);
        System.out.println("一ポイントの単価を入力してください>>> ");
        costs[0] = Integer.parseInt(Main.sc.nextLine());
        System.out.println("レッスン1コマの単価を入力してください>>> ");
        costs[1] = Integer.parseInt(Main.sc.nextLine());
        System.out.println("単価を変更しました。");
    }

    public static int getPointValue() {
        return costs[0];
    }

    public static int getLessonCost() {
        return costs[1];
    }
}
