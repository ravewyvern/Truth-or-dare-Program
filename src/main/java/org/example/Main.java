package org.example;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
/*
TO DO LIST:
- Make an option to have it pull from an api
 */

public class Main {

    public static boolean continueProgram = true;
    public static final boolean debugMode = false; //set to true to enable debug mode for testing

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String[] usedTruths = new String[1];
        String[] usedDares = new String[1];
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
                System.out.print("Select a Truth (1), Dare (2), Stats (3), or Exit (4) question: ");
                int choice = input.nextInt();

                if (choice == 1) {
                    if (getCSVLength("src/main/java/org/example/truthes.csv") <= totalTruths) {
                        System.out.println("You have used all the truths.");
                        if (yesNoPrompt("Would you like to reset the truths")) {
                            usedTruths = new String[1];
                        }
                        continue;
                    }
                    String randomTruth = getRandomEntry("src/main/java/org/example/truthes.csv");

                    usedTruths = resizeArray(usedTruths, 1);
                    usedTruths[usedTruths.length - 2] = "placeholder";

                    while (arrayItemChecker(usedTruths, randomTruth)) {
                        randomTruth = getRandomEntry("src/main/java/org/example/truthes.csv");
                    }
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
                    if (getCSVLength("src/main/java/org/example/dares.csv") <= totalDares) {
                        System.out.println("You have used all the dares.");
                        if (yesNoPrompt("Would you like to reset the dares")) {
                            usedDares = new String[1];
                        }
                        continue;
                    }
                    String randomDare = getRandomEntry("src/main/java/org/example/dares.csv");

                    usedDares = resizeArray(usedDares, 1);
                    usedDares[usedDares.length - 2] = "placeholder";

                    while (arrayItemChecker(usedDares, randomDare)) {
                        randomDare = getRandomEntry("src/main/java/org/example/dares.csv");
                    }
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
                    System.out.println("There are a total of " + getCSVLength("src/main/java/org/example/truthes.csv") + " truths.");
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
}