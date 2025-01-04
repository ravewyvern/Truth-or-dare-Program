package org.example;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class Main {

    public static boolean continueProgram = true;
    public static final boolean debugMode = false; //set to true to enable debug mode for testing
    public static final int maxRunTimes = 1000;

    public static void main(String[] args) {
        //default settings

        //Values: pg, pg13, r
        String rating = "pg13";

        //Values: local, api, random
        String getQuestions = "random";

        Scanner input = new Scanner(System.in);
        String[] usedTruths = new String[1];
        String[] usedDares = new String[1];
        int totalLocalTruthes = 0, totalLocalDares = 0;
        int totalTruths = 0, totalDares = 0;

        System.out.println("Welcome to Truth or Dare!");
        System.out.print("To get started How many players are there?: ");
        int players = input.nextInt();
        PlayerStats[] playerStats = new PlayerStats[players];
        for (int i = 0; i < players; i++) {
            playerStats[i] = new PlayerStats();
        }
        System.out.print("And how many skips should each player have?: ");
        int skips = input.nextInt();
        System.out.println("Great! Let's get started!");

        while (continueProgram)
        {
            int mainLoop = 0;
            while (mainLoop < players) {
                System.out.println();
                System.out.println("It is player " + (mainLoop + 1) + "'s turn.");
                System.out.print("Select a Truth (1), Dare (2), Stats (3), Settings (4), or Exit (5) question: ");
                int choice = input.nextInt();

                if (choice == 1) {
                    if (getCSVLength("src/main/java/org/example/truths.csv") <= totalLocalTruthes) {
                        System.out.println("You have used all the local truths.");
                        if (yesNoPrompt("Would you like to reset the truths")) {
                            usedTruths = new String[1];
                        }
                        continue;
                    }
                    String randomTruth = getTruthOrDare(rating, "truth", getQuestions, usedTruths);
                    if (getQuestions.equals("local")) totalLocalTruthes++;

                    usedTruths = resizeArray(usedTruths, 1);
                    usedTruths[usedTruths.length - 2] = "placeholder";

                    boolean continueLoopforTruth = true;

                    while (continueLoopforTruth) {
                        System.out.println("Random Truth: " + randomTruth);
                        System.out.print("Would you like to continue (1), skip (2), or save (3)?: ");
                        switch (input.nextInt()) {
                            case 1:
                                continueLoopforTruth = false;
                                break;
                            case 2:
                                if (playerStats[mainLoop].getUsedSkips() < skips) {
                                    playerStats[mainLoop].setUsedSkips(playerStats[mainLoop].getUsedSkips() + 1);
                                    continueLoopforTruth = false;
                                    System.out.println("1 skip used, you now have " + (skips - playerStats[mainLoop].getUsedSkips()) + " skips left.");
                                } else {
                                    System.out.println("You have used all your skips, please complete the truth.");
                                }
                                break;
                            case 3:
                                playerStats[mainLoop].setFavoriteTruthOrDare(randomTruth);
                                System.out.println("Truth saved.");
                                break;
                        }
                    }
                    totalTruths++;
                    playerStats[mainLoop].setTotalTruths(playerStats[mainLoop].getTotalTruths() + 1);
                    mainLoop++;

                    usedTruths[usedTruths.length - 2] = randomTruth;
                    if (debugMode) printArray(usedTruths);
                }
                else if (choice == 2) {
                    if (getCSVLength("src/main/java/org/example/dares.csv") <= totalLocalDares) {
                        System.out.println("You have used all the local dares.");
                        if (yesNoPrompt("Would you like to reset the dares")) {
                            usedDares = new String[1];
                        }
                        continue;
                    }
                    String randomDare = getTruthOrDare(rating, "dare", getQuestions, usedDares);
                    if (getQuestions.equals("local")) totalLocalDares++;

                    usedDares = resizeArray(usedDares, 1);
                    usedDares[usedDares.length - 2] = "placeholder";

                    boolean continueLoopforDare = true;

                    while (continueLoopforDare) {
                        System.out.println("Random Dare: " + randomDare);
                        System.out.print("Would you like to continue (1), skip (2), or save (3)?: ");
                        switch (input.nextInt()) {
                            case 1:
                                continueLoopforDare = false;
                                break;
                            case 2:
                                if (playerStats[mainLoop].getUsedSkips() < skips) {
                                    playerStats[mainLoop].setUsedSkips(playerStats[mainLoop].getUsedSkips() + 1);
                                    continueLoopforDare = false;
                                    System.out.println("1 skip used, you now have " + (skips - playerStats[mainLoop].getUsedSkips()) + " skips left.");
                                } else {
                                    System.out.println("You have used all your skips, please complete the dare.");
                                }
                                break;
                            case 3:
                                playerStats[mainLoop].setFavoriteTruthOrDare(randomDare);
                                System.out.println("Dare saved.");
                                break;
                        }
                    }

                    totalDares++;
                    playerStats[mainLoop].setTotalDares(playerStats[mainLoop].getTotalDares() + 1);
                    mainLoop++;

                    usedDares[usedDares.length - 2] = randomDare;
                    if (debugMode) printArray(usedDares);
                }
                else if (choice == 3) {
                    System.out.println("Global Stats:");
                    System.out.println("You have used " + totalTruths + " truths.");
                    System.out.println("There are a total of " + getCSVLength("src/main/java/org/example/truths.csv") + " truths.");
                    System.out.println("You have used " + totalDares + " dares.");
                    System.out.println("There are a total of " + getCSVLength("src/main/java/org/example/dares.csv") + " dares.");
                    for (int i = 0; i < players; i++) {
                        System.out.println();
                        System.out.println("Player " + (i + 1) + " Stats:");
                        System.out.println("Has used " + playerStats[i].getUsedSkips() + "/" + skips +" skips.");
                        System.out.println("Has played " + playerStats[i].getTotalTruths() + " truths.");
                        System.out.println("Has played " + playerStats[i].getTotalDares() + " dares.");
                        System.out.println("Has a Favorite T/D of " + playerStats[i].getFavoriteTruthOrDare());
                    }

                } else if (choice == 4) {
                    System.out.println("Settings:");
                    System.out.println("1. Change the number of players.");
                    System.out.println("2. Change the number of skips.");
                    System.out.println("3. Reset the used truths.");
                    System.out.println("4. Reset the used dares.");
                    System.out.println("5. Change rating for truths and dares.");
                    System.out.println("6. Change method of getting truths and dares.");
                    System.out.println("7. Exit settings.");
                    System.out.print("Select an option: ");
                    int settingChoice = input.nextInt();
                    switch (settingChoice) {
                        case 1:
                            System.out.print("How many players are there?: ");
                            players = input.nextInt();
                            playerStats = new PlayerStats[players];
                            for (int i = 0; i < players; i++) {
                                playerStats[i] = new PlayerStats();
                            }
                            break;
                        case 2:
                            System.out.print("How many skips should each player have?: ");
                            skips = input.nextInt();
                            break;
                        case 3:
                            if (yesNoPrompt("Are you sure you want to reset the used truths?")) {
                                usedTruths = new String[1];
                            }
                            break;
                        case 4:
                            if (yesNoPrompt("Are you sure you want to reset the used dares?")) {
                                usedDares = new String[1];
                            }
                            break;
                        case 5:
                            System.out.print("What rating would you like to set the truths and dares to PG (1), PG-13 (2), or R (3): ");
                            switch (input.nextInt()) {
                                case 1:
                                    rating = "pg";
                                    break;
                                case 2:
                                    rating = "pg13";
                                    break;
                                case 3:
                                    rating = "r";
                                    break;
                                default:
                                    System.out.println("Invalid rating.");
                                    break;
                            }
                            System.out.println("Rating set to " + rating);
                            break;
                        case 6:
                            System.out.print("Would you like to Use local (1), API (2), or random (3) to get truths and dares?");
                            switch (input.nextInt()) {
                                case 1:
                                    System.out.println("Using local files.");
                                    getQuestions = "local";
                                    break;
                                case 2:
                                    System.out.println("Using an API.");
                                    getQuestions = "api";
                                    break;
                                case 3:
                                    System.out.println("Using random.");
                                    getQuestions = "random";
                                    break;
                                default:
                                    System.out.println("Invalid option.");
                                    break;
                            }
                            break;
                        case 7:
                            break;
                    }
                }
                else {
                    if (yesNoPrompt("Are you sure you want to exit?")) {
                        continueProgram = false;
                        break;
                    }
                }
            }

        }
    }

    public static String[] resizeArray(String[] array, int changeSize) {
        //Checks if the array is being resized by 0
        if (changeSize == 0) {
            return array;
        }
        //Creates a new array with the new size and copies the elements
        //from the old array to the new array
        String[] newArray = new String[array.length + changeSize];

        int elementsToCopy = Math.min(array.length, newArray.length);
        for (int i = 0; i < elementsToCopy; i++) {
            newArray[i] = array[i];
        }
        return newArray;
    }

    public static boolean arrayItemChecker(String[] array, String item) {
        for (int i = 0; i < array.length - 1; i++) {
            if (debugMode) System.out.println("checking: " + i + ". " + array[i] + " against: " + item);
            if (array[i].equals(item)) {
                if (debugMode) System.out.println("Match between: " + i + ". " + array[i] + " and " + item);
                return true;
            }
        }
        if (debugMode) System.out.println("No matches found");
        return false;
    }

    public static void printArray(String[] array) {
        System.out.print("{ ");
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]);
            if (i != array.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.print(" }");
        System.out.println();
    }

    public static String getRandomEntry(String filePath) {
        List<String> entries = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                for (String value : values) {
                    entries.add(value.trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (entries.isEmpty()) {
            return null;
        }

        Random rand = new Random();
        return entries.get(rand.nextInt(entries.size()));
    }

    public static int getCSVLength(String filePath) {
        List<String> entries = new ArrayList<>();
        String[] values = null;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                values = line.split(",");
                for (String value : values) {
                    entries.add(value.trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (entries.isEmpty()) {
            return 0;
        }

        return values.length;
    }

    public static boolean yesNoPrompt(String prompt) {
        Scanner input = new Scanner(System.in);
        System.out.print(prompt + " (Y/n): ");
        String choice = input.next();
        if (choice.equalsIgnoreCase("y") || choice.equalsIgnoreCase("yes")) {
            return true;
        }
        return false;
    }

    public static String getTruthOrDareFromAPI(String rating, String type) {
        String apiUrl = "https://api.truthordarebot.xyz/api/" + type + "?rating=" + rating;
        StringBuilder response = new StringBuilder();

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Read the response
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Parse the response as JSON
            JSONObject jsonResponse = new JSONObject(response.toString());
            return jsonResponse.getString("question");  // Return only the "question" field

        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    public static String getTruthOrDare(String rating, String type, String source, String[] usedQuestions) {
        if (source.equals("random")) {
            Random rand = new Random();
            String[] sourceOptions = {"local", "api"};
            source = sourceOptions[rand.nextInt(sourceOptions.length)];
            if (debugMode) System.out.println("Random source: " + source);
        }
        String selectedQuestion;
        int timesRun = 0;
        do {
            if (source.equals("local")) {
                selectedQuestion = getRandomEntry("src/main/java/org/example/" + type + "s.csv");
            } else {
                selectedQuestion = getTruthOrDareFromAPI(rating, type);
            }
            timesRun++;
            if (timesRun > maxRunTimes) return "Error: Had to stop process";
        } while (arrayItemChecker(usedQuestions, selectedQuestion));
        return selectedQuestion;
    }
}