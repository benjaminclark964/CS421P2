/**
 * Author: Benjamin Clark
 * 07/27/2020
 * CS421 P2 ShuffleRec
 *
 * This program uses pure recursion to solve the shuffledness problem
 */
public class ShuffleRec {
    private static int count = 0;
    private static int used = 0;
    private static char[] xGlobal = {};
    private static boolean solutionFound = false;

    public static void main(String[] args) {
        if(args.length != 3) { printErrorMessage(); return; }

        if(!argsIsLetters(args)) { return; }

        char[] x = args[0].toCharArray();
        char[] y = args[1].toCharArray();
        char[] z = args[2].toCharArray();
        xGlobal = x;
        if(x.length != y.length) { printBadResults(); return;}
        if(shuffleRec(x ,y ,z, x.length, y.length)) { printResults(); }
        else { printBadResults(); }
    }

    /**
     * returns true if z could be shuffled using x and y
     * @param x x deck
     * @param y y deck
     * @param z z deck
     * @param i x deck index
     * @param j y deck index
     * @return boolean
     */
    private static boolean shuffleRec(char[] x, char[] y, char[] z, int i, int j) {
        count++;
        used++;
        if(count == 5) {
            int cv = 0;
        }
        //base case
        if((i==0 && j==0)) {
            solutionFound = true;
            return true;
        }

        if(j>0 && i==0) {
            if(compare(z, y, 0, j-1)) { shuffleRec(x,y,z,i,j-1); }
        } else if(i>0 && j==0) {
            if(compare(z,x,i-1,0)) { shuffleRec(x,y,z,i-1,j); }
        } else if(i>0 && j>0) {
            if(compare(z,x,i-1,j-1)) { shuffleRec(x,y,z,i-1,j); }
            if(solutionFound) return true;
            if(compare(z, y, i-1, j-1)) { shuffleRec(x,y,z,i,j-1); }
        }

        if(solutionFound) return true;

        used--;
        return false;
    }

    /**
     * compare two characters from to two char arrays
     * @param z
     * @param chars
     * @param i
     * @return
     */
    private static boolean compare(char[] z, char[] chars,int i, int j) {
        if(chars.equals(xGlobal)) {
            if(z[z.length-used] == chars[i]) {
                return true;}

            return false;
        } else {
            if(z[z.length-used] == chars[j]) {
                return true;}

            return false;
        }
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
        System.out.println("yes");
        System.out.println("Number of recursive calls: " + count);
    }

    /**
     * prints bad results
     */
    private static void printBadResults() {
        System.out.println("no");
        System.out.println("Number of recursive calls: " + count);
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