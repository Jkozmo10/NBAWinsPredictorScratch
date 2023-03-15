public class team {
    private final String name;
    private final String season;
    private final String abbreviation;
    private final Integer playoffs;
    private final Integer wins;
    private final Integer losses;
    private final double avgFgPercent;
    private final double avgX3pPercent;
    private final double avgFtPercent;
    private final double avgTrbPerGame;
    private final double avgAstPerGame;
    private final double avgStlPerGame;
    private final double avgBlkPerGame;
    private final double avgTovPerGame;
    private final double avgPtsPerGame;

    // constructor
    public team(String name, String season, String abbreviation, Integer playoffs, Integer wins, Integer losses, double avgFgPercent, double avgX3pPercent, double avgFtPercent, double avgTrbPerGame, double avgAstPerGame, double avgStlPerGame, double avgBlkPerGame, double avgTovPerGame, double avgPtsPerGame) {
        this.name = name;
        this.season = season;
        this.abbreviation = abbreviation;
        this.playoffs = playoffs;
        this.wins = wins;
        this.losses = losses;
        this.avgFgPercent = avgFgPercent;
        this.avgX3pPercent = avgX3pPercent;
        this.avgFtPercent = avgFtPercent;
        this.avgTrbPerGame = avgTrbPerGame;
        this.avgAstPerGame = avgAstPerGame;
        this.avgStlPerGame = avgStlPerGame;
        this.avgBlkPerGame = avgBlkPerGame;
        this.avgTovPerGame = avgTovPerGame;
        this.avgPtsPerGame = avgPtsPerGame;
    }

    public double calculateL2Distance(team otherTeam) {
        double distance = Math.sqrt(
                Math.pow(this.playoffs - otherTeam.getPlayoffs(), 2) +
                        Math.pow(this.wins - otherTeam.getWins(), 2) +
                        Math.pow(this.losses - otherTeam.getLosses(), 2) +
                        Math.pow(this.avgFgPercent - otherTeam.getAvgFgPercent(), 2) +
                        Math.pow(this.avgX3pPercent - otherTeam.getAvgX3pPercent(), 2) +
                        Math.pow(this.avgFtPercent - otherTeam.getAvgFtPercent(), 2) +
                        Math.pow(this.avgTrbPerGame - otherTeam.getAvgTrbPerGame(), 2) +
                        Math.pow(this.avgAstPerGame - otherTeam.getAvgAstPerGame(), 2) +
                        Math.pow(this.avgStlPerGame - otherTeam.getAvgStlPerGame(), 2) +
                        Math.pow(this.avgBlkPerGame - otherTeam.getAvgBlkPerGame(), 2) +
                        Math.pow(this.avgTovPerGame - otherTeam.getAvgTovPerGame(), 2) +
                        Math.pow(this.avgPtsPerGame - otherTeam.getAvgPtsPerGame(), 2)
        );
        return distance;
    }


    // getters and setters for all attributes

    public String getName() {
        return name;
    }

    public String getSeason() {
        return season;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public Integer getPlayoffs() {
        return playoffs;
    }

    public Integer getWins() {
        return wins;
    }

    public Integer getLosses() {
        return losses;
    }

    public double getAvgFgPercent() {
        return avgFgPercent;
    }

    public double getAvgX3pPercent() {
        return avgX3pPercent;
    }

    public double getAvgFtPercent() {
        return avgFtPercent;
    }

    public double getAvgTrbPerGame() {
        return avgTrbPerGame;
    }

    public double getAvgAstPerGame() {
        return avgAstPerGame;
    }

    public double getAvgStlPerGame() {
        return avgStlPerGame;
    }

    public double getAvgBlkPerGame() {
        return avgBlkPerGame;
    }

    public double getAvgTovPerGame() {
        return avgTovPerGame;
    }

    public double getAvgPtsPerGame() {
        return avgPtsPerGame;
    }
}