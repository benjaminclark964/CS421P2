import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class KnapsackDP {

    private static int tableReferences = 0;
    private static LinkedList<Integer> optimalItems = new LinkedList<>();
    private static int[][] table;
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
            int optimalValue = knapsackDP(numItems, knapSackWeight);
            printResults(knapSackWeight, optimalValue);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * finds the optimal solution for a knapsack with a certain weight amount of items
     * @param N num items
     * @param W MAX_WEIGHT
     * @return the optimal value
     */
    private static int knapsackDP(int N, int W) {
        int xSize = N+1;
        int ySize = W+1;
        table = new int[xSize][ySize];

        for(int i = 1; i < xSize; i++) {
            for(int j = 1; j < ySize; j++) {
                if(i==0 || j==0) {
                    table[i][j] = 0;
                }
                else if(itemWeights[i-1] > j) {
                    table[i][j] = table[i-1][j];
                } else {
                    int op1 = table[i-1][j];
                    int op2 = table[i-1][j-itemWeights[i-1]] + itemValues[i-1];
                    table[i][j] = Math.max(op1, op2);
                    tableReferences++;
                }
                tableReferences++;
            }
        }

        optimalItems = getOptimalItems(N, W);
        return table[xSize-1][ySize-1];
    }

    /**
     * gets the optimal items based on the dp table generated
     * @param numItems number of items
     * @param maxWeight max weight of knap sack
     * @return the optimal items
     */
    private static LinkedList<Integer> getOptimalItems(int numItems, int maxWeight) {
        LinkedList<Integer> optValues = new LinkedList<>();
        for(int i = numItems; i > 0; i--) {
            if((maxWeight - itemWeights[i-1] >= 0) &&
                    (table[i][maxWeight] - table[i-1][maxWeight-itemWeights[i-1]] == itemValues[i-1]))
            {
                optValues.add(i);
                maxWeight -= itemWeights[i-1];
            }
        }
        return optValues;
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
        System.out.println("Number of table references " + tableReferences);
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
