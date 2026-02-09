package usecase;

public class ProfitSummary {
    private final int totalPointsUsed;
    private final int totalProfit;

    public ProfitSummary(int totalPointsUsed, int totalProfit) {
        this.totalPointsUsed = totalPointsUsed;
        this.totalProfit = totalProfit;
    }

    public int getTotalPointsUsed() {
        return totalPointsUsed;
    }

    public int getTotalProfit() {
        return totalProfit;
    }
}
