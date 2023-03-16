import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class main {
    private static HashMap<Integer, HashMap<String, team>> data = new HashMap<>();

    public static void main(String[] args) {
        process("data.csv");
    }
    public static void process(String filename) {
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);

            // Skip the first line (header row)
            scanner.nextLine();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.split(",");

                String name = values[0];
                int season = Integer.parseInt(values[1]);
                String abbreviation = values[2];
                Integer playoffs = values[3].equals("TRUE") ? 1 : 0;
                int wins = Integer.parseInt(values[4]);
                int losses = Integer.parseInt(values[5]);
                double avgFgPercent = Double.parseDouble(values[6]);
                double avgX3pPercent = Double.parseDouble(values[7]);
                double avgFtPercent = Double.parseDouble(values[8]);
                double avgTrbPerGame = Double.parseDouble(values[9]);
                double avgAstPerGame = Double.parseDouble(values[10]);
                double avgStlPerGame = Double.parseDouble(values[11]);
                double avgBlkPerGame = Double.parseDouble(values[12]);
                double avgTovPerGame = Double.parseDouble(values[13]);
                double avgPtsPerGame = Double.parseDouble(values[14]);

                team newTeam = new team(season, abbreviation, playoffs, wins, losses, avgFgPercent, avgX3pPercent, avgFtPercent, avgTrbPerGame, avgAstPerGame, avgStlPerGame, avgBlkPerGame, avgTovPerGame, avgPtsPerGame);
                System.out.println(newTeam);
                // If the season key doesn't exist, add it
                if (!data.containsKey(season)) {
                    data.put(season, new HashMap<String, team>());
                }

                // Add the team to the season
                data.get(season).put(abbreviation, newTeam);
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
            e.printStackTrace();
        }
    }


}
