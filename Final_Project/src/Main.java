import java.io.*;
import java.util.*;

public class Main {
    private static HashMap<Integer, Team> data = new HashMap<>();
    private static HashMap<String, ArrayList<Double>>  toCalculate = new HashMap<>();
    private static HashMap<Integer, Team> trainingData = new HashMap<>();
    private static HashMap<Integer, Team> testingData = new HashMap<>();
    private static HashMap<Integer, HashMap<Integer, Team>> crossValidationSets = new HashMap<>();

    public static void main(String[] args) {
        process("Final_Project/data.csv");
        trainingData = createTrainingData();
        testingData = createTestingData();
        crossValidationSets = createCrossValidationSets();
        HashMap<Integer, Double> k_error = performCrossValidation();
        System.out.println(k_error);
    }


    public static void process(String filename) {
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);

            // Skip the first line (header row)
            scanner.nextLine();
            // Create key for the toCalculate
            toCalculate.put("wins", new ArrayList<>());
            toCalculate.put("losses", new ArrayList<>());
            toCalculate.put("avgFgPercent", new ArrayList<>());
            toCalculate.put("avgX3pPercent", new ArrayList<>());
            toCalculate.put("avgFtPercent", new ArrayList<>());
            toCalculate.put("avgTrbPerGame", new ArrayList<>());
            toCalculate.put("avgAstPerGame", new ArrayList<>());
            toCalculate.put("avgStlPerGame", new ArrayList<>());
            toCalculate.put("avgBlkPerGame", new ArrayList<>());
            toCalculate.put("avgTovPerGame", new ArrayList<>());
            toCalculate.put("avgPtsPerGame", new ArrayList<>());

            Integer counter = 0;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.split(",");

                int season = Integer.parseInt(values[0]);
                String abbreviation = values[1].replace("\"", "");
                Integer playoffs = values[2].equals("TRUE") ? 1 : 0;
                int wins = Integer.parseInt(values[3]);
                toCalculate.get("wins").add((double) wins);
                int losses = Integer.parseInt(values[4]);
                toCalculate.get("losses").add((double) losses);
                double avgFgPercent = Double.parseDouble(values[5]);
                toCalculate.get("avgFgPercent").add(avgFgPercent);
                double avgX3pPercent = Double.parseDouble(values[6]);
                toCalculate.get("avgX3pPercent").add(avgX3pPercent);
                double avgFtPercent = Double.parseDouble(values[7]);
                toCalculate.get("avgFtPercent").add(avgFtPercent);
                double avgTrbPerGame = Double.parseDouble(values[8]);
                toCalculate.get("avgTrbPerGame").add(avgTrbPerGame);
                double avgAstPerGame = Double.parseDouble(values[9]);
                toCalculate.get("avgAstPerGame").add(avgAstPerGame);
                double avgStlPerGame = Double.parseDouble(values[10]);
                toCalculate.get("avgStlPerGame").add(avgStlPerGame);
                double avgBlkPerGame = Double.parseDouble(values[11]);
                toCalculate.get("avgBlkPerGame").add(avgBlkPerGame);
                double avgTovPerGame = Double.parseDouble(values[12]);
                toCalculate.get("avgTovPerGame").add(avgTovPerGame);
                double avgPtsPerGame = Double.parseDouble(values[13]);
                toCalculate.get("avgPtsPerGame").add(avgPtsPerGame);

                Team newTeam = new Team(season, abbreviation, playoffs, wins, losses, avgFgPercent, avgX3pPercent, avgFtPercent, avgTrbPerGame, avgAstPerGame, avgStlPerGame, avgBlkPerGame, avgTovPerGame, avgPtsPerGame);
                // If the season key doesn't exist, add it

                data.put(counter, newTeam);
                counter++;
                // Add the team to the season

            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
            e.printStackTrace();
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

    public static HashMap<Integer, Team> createTrainingData(){
        HashMap<Integer, Team> testingData = new HashMap<>();
        Random r = new Random();
        r.setSeed(1257);
        while(testingData.size() < data.size() * 0.8){
            int idx = r.nextInt(data.size());
            testingData.put(idx, data.get(idx));
        }
        return testingData;
    }

    public static HashMap<Integer, Team> createTestingData(){
        HashMap<Integer, Team> testingData = new HashMap<>();
        for(int i = 0; i < data.size(); i++){
            if(!trainingData.containsKey(i)){
                testingData.put(i, data.get(i));
            }
        }
        return testingData;
    }

    public static HashMap<Integer, HashMap<Integer, Team>> createCrossValidationSets(){
        HashMap<Integer, HashMap<Integer, Team>> crossValidation = new HashMap<>(); //key: which chunk of the data, NOT data point id
        Random r = new Random();
        r.setSeed(1257);
        for (int i = 0; i < 10; i++){
            HashMap<Integer, Team> chunk = new HashMap<>();
            for(int j = 0; j < trainingData.size() * 0.1; j++){
                int idx = r.nextInt(data.size());
                chunk.put(idx, trainingData.get(idx));
            }
            crossValidation.put(i, chunk);
        }
        return crossValidation;
    }

    public static HashMap<Integer, Double> performCrossValidation(){
        HashMap<Integer, Double> errors = new HashMap<>(); //integer = k, double = average error
        double singleChunkError;
        double avgChunkError;
        double allChunkErrors;
        for (int k = 2; k <= 20; k++){
            allChunkErrors = 0;
            for (Map.Entry<Integer, HashMap<Integer, Team>> chunk : crossValidationSets.entrySet()){
                singleChunkError = 0;
                for (Team team : chunk.getValue().values()){
                    Collection<Team> closestTeams = findKClosestTeams(chunk.getKey(), team, k);
                    double predictedNumWins = classifyTeam(closestTeams);
                    singleChunkError += Math.abs(predictedNumWins - team.getWins());
                }
                avgChunkError = singleChunkError / chunk.getValue().values().size();
                allChunkErrors += avgChunkError;

            }
            errors.put(k, allChunkErrors / 10);
            //average error of all chunks and add to Hashmap
        }
        return errors;
    }

    public static Collection<Team> findKClosestTeams(int chunkIndex, Team t, int k){
        HashMap<Double, Team> closestTeams = new HashMap<>(); //double = distance to t, team = otherteam to t
        HashMap<Integer, Team> currentChunk = crossValidationSets.get(chunkIndex);
        for(Team otherTeam : currentChunk.values()){
            double distance = t.calculateL1Distance(otherTeam);
            if(closestTeams.size() < k){
                closestTeams.put(distance, otherTeam);
            } else{
                double maximumDistance = Collections.max(closestTeams.keySet());
                if (distance < maximumDistance){
                    closestTeams.remove(maximumDistance);
                    closestTeams.put(distance, otherTeam);
                }
            }
        }
        return closestTeams.values();
    }

    public static double classifyTeam(Collection<Team> closestTeams){
        double totalWins = 0.0;
        for(Team t : closestTeams){
            totalWins += t.getWins();
        }
        return totalWins / closestTeams.size();
    }


}
