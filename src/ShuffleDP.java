/**
 * Author: Benjamin Clark
 * 07/27/2020
 * CS421
 * P2 ShuffelDP
 *
 * This program determines if to decks can create the z deck from a shuffle of
 * of the other two decks using dynammic programming
 */
public class ShuffleDP {

    private static int[][] table = {};
    private static int tableReferences = 0;

    public static void main(String[] args) {
        if(args.length != 3) { printErrorMessage(); return; }
        if(!argsIsLetters(args)) { return; }

        char[] x = args[0].toCharArray();
        char[] y = args[1].toCharArray();
        char[] z = args[2].toCharArray();
        if(x.length != y.length) { return; }
        table = new int[x.length+1][y.length+1];
        shuffleDP(x, y, z);
        if(table[table.length-1][table.length-1] == 1) { printResults(); }
        else { printBadResults(); }
    }

    /**
     * Checks if deck z is a shuffle of x and y using dynamic programming
     *
     * @param x x deck
     * @param y y deck
     * @param z z deck
     * @return the table
     */
    private static int[][] shuffleDP(char[] x, char[] y, char[] z) {
        for(int i = 0; i <= x.length; i++) {
            for(int j = 0; j <= y.length; j++) {
                if(i==0 && j==0) {
                    table[i][j] = 1;
                }

                else if(i==0) {
                    if(y[j-1] == z[j-1]) {
                        table[i][j] = table[i][j-1];
                        tableReferences++;
                    }
                }

                else if(j==0) {
                    if(x[i-1] == z[i-1]) {
                        table[i][j] = table[i-1][j];
                        tableReferences++;
                    }
                }

                else if(x[i-1] == z[i+j-1] &&
                        y[j-1] != z[i+j-1]) {
                    table[i][j] = table[i-1][j];
                    tableReferences++;
                }

                else if(x[i-1] != z[i+j-1] &&
                        y[j-1] == z[i+j-1]) {
                    table[i][j] = table[i][j-1];
                    tableReferences++;
                }

                else if(x[i-1] == z[i+j-1] &&
                        y[j-1] == z[i+j-1]) {
                    table[i][j] = table[i-1][j] | table[i][j-1];
                    tableReferences++;
                }
            }
        }
        return table;
    }


    /**
     * Checks if i and j are in bounds
     * @param i index i
     * @param j index j
     * @return true if i and j are in bounds
     */
    private static boolean checkIfNotOutOfBounds(int i, int j) {
        boolean notOutOfBounds = true;
        if(i < 0 || j < 0) { notOutOfBounds = false; }
        if(i == table.length || j == table.length) { notOutOfBounds = false; }
        return notOutOfBounds;
    }

    /**
     * checks if args are letters
     * @param args the arguments
     * @return boolean
     */
    private static boolean argsIsLetters(String[] args) {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < args[i].length(); j++) {
                if(!Character.isLetter(args[i].charAt(j))) { printErrorMessage(); return false; }
            }
        }
        return true;
    }

    /**
     * prints successful results
     */
    private static void printResults() {
        generateTable();
        System.out.println("yes");
        System.out.println("Number of table references: " + tableReferences);
    }

    private static void printBadResults() {
        generateTable();
        System.out.println("no");
        System.out.println("Number of table references: " + tableReferences);
    }

    private static void generateTable() {
        for(int i = 0; i < table.length; i++) {
            for(int j = 0; j < table.length; j++) {
                System.out.printf("%-4s", table[i][j]);
            }
            //generate new line
            System.out.println();
        }
    }

    /**
     * prints error message for invalid args
     */
    private static void printErrorMessage() {
        System.err.println("Error");
        System.out.println("Usage: X Y Z");
        System.out.println("The total amount of X and Y should be equal to Z");
        System.out.println("X, Y, and Z should be letters");
    }
}
