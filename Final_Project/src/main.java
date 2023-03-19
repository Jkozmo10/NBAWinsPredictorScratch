import javax.print.DocFlavor;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class main {
    private static HashMap<Integer, team> data = new HashMap<>();
    private static HashMap<String, ArrayList<Double>>  toCalculate = new HashMap<>();
    private static HashMap<String, HashMap<Integer, ArrayList<Double>>>  nameLater = new HashMap<>();
    private static ArrayList<Double> TTavgFgPercent = new ArrayList<>();
    private static ArrayList<Double> TTavgX3pPercent = new ArrayList<>();
    private static ArrayList<Double> TTavgFtPercent = new ArrayList<>();
    private static ArrayList<Double> TTavgTrbPerGame = new ArrayList<>();
    private static ArrayList<Double> TTavgAstPerGame = new ArrayList<>();
    private static ArrayList<Double> TTavgStlPerGame = new ArrayList<>();
    private static ArrayList<Double> TTavgBlkPerGame = new ArrayList<>();
    private static ArrayList<Double> TTavgTovPerGame = new ArrayList<>();
    private static ArrayList<Double> TTavgPtsPerGame = new ArrayList<>();
    public static void main(String[] args) {
        process("data.csv");
    }
    public static void process(String filename) {
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);

            // Skip the first line (header row)
            scanner.nextLine();
            // Create key for the toCalculate
            toCalculate.put("avgFgPercent", new ArrayList<>());
            toCalculate.put("avgX3pPercent", new ArrayList<>());
            toCalculate.put("avgFtPercent", new ArrayList<>());
            toCalculate.put("avgTrbPerGame", new ArrayList<>());
            toCalculate.put("avgAstPerGame", new ArrayList<>());
            toCalculate.put("avgStlPerGame", new ArrayList<>());
            toCalculate.put("avgBlkPerGame", new ArrayList<>());
            toCalculate.put("avgTovPerGame", new ArrayList<>());
            toCalculate.put("avgPtsPerGame", new ArrayList<>());

            nameLater.put("avgFgPercent", new HashMap<>());
            nameLater.put("avgX3pPercent", new HashMap<>());
            nameLater.put("avgFtPercent", new HashMap<>());
            nameLater.put("avgTrbPerGame", new HashMap<>());
            nameLater.put("avgAstPerGame", new HashMap<>());
            nameLater.put("avgStlPerGame", new HashMap<>());
            nameLater.put("avgBlkPerGame", new HashMap<>());
            nameLater.put("avgTovPerGame", new HashMap<>());
            nameLater.put("avgPtsPerGame", new HashMap<>());

            Integer counter = 0;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.split(",");

                int season = Integer.parseInt(values[0]);
                if(!nameLater.get("avgFgPercent").containsKey(season)){
                    nameLater.get("avgFgPercent").put(season, new ArrayList<>());
                    nameLater.get("avgX3pPercent").put(season, new ArrayList<>());
                    nameLater.get("avgFtPercent").put(season, new ArrayList<>());
                    nameLater.get("avgTrbPerGame").put(season, new ArrayList<>());
                    nameLater.get("avgAstPerGame").put(season, new ArrayList<>());
                    nameLater.get("avgStlPerGame").put(season, new ArrayList<>());
                    nameLater.get("avgBlkPerGame").put(season, new ArrayList<>());
                    nameLater.get("avgTovPerGame").put(season, new ArrayList<>());
                    nameLater.get("avgPtsPerGame").put(season, new ArrayList<>());
                }
                String abbreviation = values[1];
                Double playoffs = values[2].equals("TRUE") ? 1.0 : 0.0;
                int wins = Integer.parseInt(values[3]);
                int losses = Integer.parseInt(values[4]);
                double avgFgPercent = Double.parseDouble(values[5]);
                nameLater.get("avgFgPercent").get(season).add(avgFgPercent);
                double avgX3pPercent = Double.parseDouble(values[6]);
                nameLater.get("avgX3pPercent").get(season).add(avgX3pPercent);
                double avgFtPercent = Double.parseDouble(values[7]);
                nameLater.get("avgFtPercent").get(season).add(avgFtPercent);
                double avgTrbPerGame = Double.parseDouble(values[8]);
                nameLater.get("avgTrbPerGame").get(season).add(avgTrbPerGame);
                double avgAstPerGame = Double.parseDouble(values[9]);
                nameLater.get("avgAstPerGame").get(season).add(avgAstPerGame);
                double avgStlPerGame = Double.parseDouble(values[10]);
                nameLater.get("avgStlPerGame").get(season).add(avgStlPerGame);
                double avgBlkPerGame = Double.parseDouble(values[11]);
                nameLater.get("avgBlkPerGame").get(season).add(avgBlkPerGame);
                double avgTovPerGame = Double.parseDouble(values[12]);
                nameLater.get("avgTovPerGame").get(season).add(avgTovPerGame);
                double avgPtsPerGame = Double.parseDouble(values[13]);
                nameLater.get("avgPtsPerGame").get(season).add(avgPtsPerGame);

                team newTeam = new team(season, abbreviation, playoffs, wins, losses, avgFgPercent, avgX3pPercent, avgFtPercent, avgTrbPerGame, avgAstPerGame, avgStlPerGame, avgBlkPerGame, avgTovPerGame, avgPtsPerGame);
                // If the season key doesn't exist, add it
                data.put(counter, newTeam);
                counter++;
                // Add the team to the season

            }
            scanner.close();
            normalizeAll(); // normalize the data
            standardizeAll();
            for (Integer key : data.keySet()) {
                System.out.println("Key: " + key);
                team value = data.get(key);
                System.out.println(value.toString());
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
            e.printStackTrace();
        }
    }

    public static void standardizeAll(){
            for(Map.Entry<Integer, team> t : data.entrySet()){
                t.getValue().standardize("avgFgPercent", calculateMean(TTavgFgPercent), calculateStdDev(TTavgFgPercent));
                t.getValue().standardize("avgX3pPercent", calculateMean(TTavgX3pPercent), calculateStdDev(TTavgX3pPercent));
                t.getValue().standardize("avgFtPercent", calculateMean(TTavgFtPercent), calculateStdDev(TTavgFtPercent));
                t.getValue().standardize("avgTrbPerGame", calculateMean(TTavgTrbPerGame), calculateStdDev(TTavgTrbPerGame));
                t.getValue().standardize("avgAstPerGame", calculateMean(TTavgAstPerGame), calculateStdDev(TTavgAstPerGame));
                t.getValue().standardize("avgStlPerGame", calculateMean(TTavgStlPerGame), calculateStdDev(TTavgStlPerGame));
                t.getValue().standardize("avgBlkPerGame", calculateMean(TTavgBlkPerGame), calculateStdDev(TTavgBlkPerGame));
                t.getValue().standardize("avgTovPerGame", calculateMean(TTavgTovPerGame), calculateStdDev(TTavgTovPerGame));
                t.getValue().standardize("avgPtsPerGame", calculateMean(TTavgPtsPerGame), calculateStdDev(TTavgPtsPerGame));
        }
    }

    public static double calculateStdDev(ArrayList<Double> values) {
        int n = values.size();
        if (n < 2) {
            throw new IllegalArgumentException("List must contain at least two values.");
        }

        // Calculate the mean of the values
        double sum = 0.0;
        for (double val : values) {
            sum += val;
        }
        double mean = sum / n;

        // Calculate the sum of the squared differences from the mean
        double squaredDiffsSum = 0.0;
        for (double val : values) {
            double diff = val - mean;
            squaredDiffsSum += diff * diff;
        }
        double variance = squaredDiffsSum / (n - 1);
        double stdDev = Math.sqrt(variance);

        return stdDev;
    }
    public static double calculateMean(ArrayList<Double> list) {
        double sum = 0.0;
        for (double num : list) {
            sum += num;
        }
        return sum / list.size();
    }

    public static void normalize(HashMap<String, HashMap<Integer, ArrayList<Double>>> nameLater, team Team){
        double meanavgFgPercent = calculateMean(nameLater.get("avgFgPercent").get(Team.getSeason()));
        Team.setAvgFgPercent(Team.getAvgFgPercent()/meanavgFgPercent);
        TTavgFgPercent.add(Team.getAvgFgPercent());
        double meanavgX3pPercent = calculateMean(nameLater.get("avgFgPercent").get(Team.getSeason()));
        Team.setAvgX3pPercent(Team.getAvgX3pPercent()/meanavgX3pPercent);
        TTavgX3pPercent.add(Team.getAvgX3pPercent());
        Team.setAvgFtPercent(Team.getAvgFtPercent()/calculateMean(nameLater.get("avgFtPercent").get(Team.getSeason())));
        TTavgFtPercent.add(Team.getAvgFtPercent());
        Team.setAvgTrbPerGame(Team.getAvgTrbPerGame()/calculateMean(nameLater.get("avgTrbPerGame").get(Team.getSeason())));
        TTavgTrbPerGame.add(Team.getAvgTrbPerGame());
        Team.setAvgAstPerGame(Team.getAvgAstPerGame()/calculateMean(nameLater.get("avgAstPerGame").get(Team.getSeason())));
        TTavgAstPerGame.add(Team.getAvgAstPerGame());
        Team.setAvgStlPerGame(Team.getAvgStlPerGame()/calculateMean(nameLater.get("avgStlPerGame").get(Team.getSeason())));
        TTavgStlPerGame.add(Team.getAvgStlPerGame());
        Team.setAvgBlkPerGame(Team.getAvgBlkPerGame()/calculateMean(nameLater.get("avgBlkPerGame").get(Team.getSeason())));
        TTavgBlkPerGame.add(Team.getAvgBlkPerGame());
        Team.setAvgTovPerGame(Team.getAvgTovPerGame()/calculateMean(nameLater.get("avgTovPerGame").get(Team.getSeason())));
        TTavgTovPerGame.add(Team.getAvgTovPerGame());
        Team.setAvgPtsPerGame(Team.getAvgPtsPerGame()/calculateMean(nameLater.get("avgPtsPerGame").get(Team.getSeason())));
        TTavgPtsPerGame.add(Team.getAvgPtsPerGame());
    }
    public static void normalizeAll(){
            for(Map.Entry<Integer, team> t : data.entrySet()){
                normalize(nameLater, t.getValue());

        }
    }
}
