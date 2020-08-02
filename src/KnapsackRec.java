import java.io.*;
import java.util.*;

/**
 * Author: Benjamin Clark
 * Date: 07/31/2020
 * Project: CS421 P2
 *
 * This class finds the optimal items and value from a list of values and associated weights.
 * The task is accomplished by using pure recursion trying every possible variation of items
 * in the sack.
 */
public class KnapsackRec {

    private static LinkedList<Integer> optimalItems = new LinkedList();
    private static int numRecursiveCalls = 0;
    private static int[] itemWeights;
    private static int[] itemValues;

    /**
     * Driver function
     * @param args
     * args[0] number of items available
     * args[1] Max Weight of knapsack
     * args[2] w.txt File containing item weights
     * args[3] v.txt File containing item values
     */
    public static void main(String[] args) {
        if(args.length != 4) {
            printError();
            return;
        }

        try {
            final int numItems = Integer.parseInt(args[0]);
            final int knapSackWeight = Integer.parseInt(args[1]);
            if(numItems == 0 || knapSackWeight == 0) {
                printResults(knapSackWeight, 0);
                return;
            }
            File weightFile = new File(args[2]);
            itemWeights = parseWtxt(numItems, weightFile);
            File valueFile = new File(args[3]);
            itemValues = parseVtxt(numItems, valueFile);
            int optimalValue = knapSackRec(numItems, knapSackWeight);
            printResults(knapSackWeight, optimalValue);
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Recursive method for finding the optimal items and value for the
     * given knapsack weight
     * @param i num items
     * @param j max weight of knapsack
     * @return optimal value
     */
    private static int knapSackRec(int i, int j) {
        int optValue = 0;
        numRecursiveCalls++;
        if(i == 0 || j == 0) {
            optValue = 0;
        }
        else if(itemWeights[i-1] > j) {
            optValue = knapSackRec(i-1, j);
            if(optimalItems.contains(i)) {
                optimalItems.removeFirstOccurrence(i);
            }
        } else {
            int op1 = knapSackRec(i-1, j);
            LinkedList<Integer> tmpList = new LinkedList<>(optimalItems);
            int op2 = knapSackRec(i-1, j-itemWeights[i-1]) + itemValues[i-1];
            optValue = Math.max(op1, op2);

            if(op2 >= op1) {
                if(!optimalItems.contains(i)) {
                    optimalItems.add(i);
                }
            } else {
                optimalItems = tmpList;
                if(optimalItems.contains(i)) {
                    optimalItems.remove((Integer)i);
                }
            }
        }
        return optValue;
    }

    /**
     * parse the w.txt file and add values to a global array
     *
     * @param numItems number of item weights being added
     * @param w w.txt file
     * @return item weights
     * @throws IOException
     */
    private static int[] parseWtxt(int numItems, File w) throws IOException {
        int[] itemWeights = new int[numItems];
        FileReader fr = new FileReader(w);
        BufferedReader br = new BufferedReader(fr);
        int counter = 0;
        while(counter < numItems) {
            String line = br.readLine();
            int weightValue = Integer.parseInt(line);
            itemWeights[counter] = weightValue;
            counter++;
        }
        return itemWeights;
    }

    /**
     * parses the v.txt file for item values
     * @param numItems number of items to be parsed
     * @param v v.txt file
     * @return item values
     * @throws IOException
     */
    private static int[] parseVtxt(int numItems, File v) throws IOException {
        int[] itemValues = new int[numItems];
        FileReader fr = new FileReader(v);
        BufferedReader br = new BufferedReader(fr);
        int counter = 0;
        while(counter < numItems) {
            String line = br.readLine();
            int itemValue = Integer.parseInt(line);
            itemValues[counter] = itemValue;
            counter++;
        }

        return itemValues;
    }

    /**
     * prints results to the console
     *
     * @param W MAX_WEIGHT of knapsack
     * @param valueOfSack optimal value of knapsack
     */
    private static void printResults(int W, int valueOfSack) {
        System.out.println("Optimal Solution:");
        Collections.sort(optimalItems);
        System.out.println(optimalItems);
        System.out.println("Total Weight: " + W);
        System.out.println("Optimal Value: " + valueOfSack);
        System.out.println("Number of recursive calls " + numRecursiveCalls);
    }

    /**
     * prints to the command prompt usage for a user if the given input
     * is invalid
     */
    private static void printError() {
        System.err.println("Usage:");
        System.out.println("Must contain four arguments n, W, w.txt, and v.txt");
        System.out.println("n is the number of items");
        System.out.println("W is the maximum weight a knapsack can carry");
        System.out.println("w.txt contains each individual items weight");
        System.out.println("v.txt contains each individual items value");
    }
}
