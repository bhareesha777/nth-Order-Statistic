import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class OS_Finding {

    /**
     * Reads a txt file of integers seperated by line (without empty lines) and returns them in an array
     * @param fileName is a string representing the filename to be read
     * @return int[] representing the contents of the file as an array
     */
    private int[] readArray(String fileName) {
        int lineCount = 0;
        int index = 0;
        int[] inputArray;

        //Check for valid input
        if (fileName == null || fileName.isEmpty())
            throw new IllegalArgumentException("fileName passed in is either empty or null");
        File inputFile = new File(fileName);
        if (!inputFile.exists()) throw new IllegalArgumentException("fileName:" + fileName + " does not exist");

        try {

            //Create scanner to find line count
            Scanner lineScanner = new Scanner(inputFile);
            while (lineScanner.hasNextLine()) {
                lineCount++;
                lineScanner.nextLine();

            }
            lineScanner.close();

            //Initialize array and scan ints to it
            inputArray = new int[lineCount];
            Scanner inputScanner = new Scanner(inputFile);
            while (inputScanner.hasNextLine()) {
                inputArray[index] = inputScanner.nextInt();
                index++;
            }
            inputScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error in reading in input. " + e.getMessage());
            throw new RuntimeException(e);
        }
        //returns the array
        return inputArray;
    }

    /**
     * Partition's subarray of passed in array around the rightIndex as pivot
     *
     * @param array      int array representing the array to be sorted
     * @param leftIndex  int representing leftmost index of array to be included
     * @param rightIndex int representing rightmost index of array to be included
     * @return int representing the index of the final location of the pivot
     */
    private int partition(int[] array, int leftIndex, int rightIndex) {

        //Initialize the index to be used as a pivot and the index for going through the array
        int pivot = array[rightIndex];
        int index = leftIndex;
        int temp;

        //Will go from i = leftIndex.... i = rightIndex - 1
        for (int i = leftIndex; i < rightIndex; i++) {

            //if the current index value of the array is less than the pivot value
            if (array[i] <= pivot) {

                //Swaps the integer at index with the one at i, then increments index
                temp = array[i];
                array[i] = array[index];
                array[index] = temp;

                index++;
            }
        }
        //Swaps the pivot into it's final place in array by swapping rightIndex and index of the array
        temp = array[index];
        array[index] = array[rightIndex];
        array[rightIndex] = temp;

        //Returns the index of the pivot
        return index;
    }

    /**
     * Partitions from left index  to right index using a random element as the pivot
     *
     * @param array      array to be partitioned
     * @param leftIndex  left most index to be partitioned of the subarray
     * @param rightIndex right most index to be partitioned of the subarray
     * @return int representing the index of the final location of the pivot
     */
    private int randomized_partition(int[] array, int leftIndex, int rightIndex) {
        int temp;
        Random random = new Random();
        //Generates a random number from leftIndex (inclusive) to rightIndex (inclusive)
        int i = random.nextInt(leftIndex, rightIndex + 1);

        //Swaps the index chosen with the rightmost index
        temp = array[i];
        array[i] = array[rightIndex];
        array[rightIndex] = temp;

        //Calls regular partition method
        return partition(array, leftIndex, rightIndex);
    }

    /**
     * Finds the nth order statistic in a subarray of the passed in array
     *
     * @param array array from which to find ith order statistic in a subarray from p to r
     * @param p     int representing the left most index of the subarray
     * @param r     int representing the right most index of the subarray
     * @param i     int representing what ith order statistic wanted
     * @return int equal to the ith order statistic within the subarray of array[p....r]
     */
    private int RandomizedSelect(int[] array, int p, int r, int i) {

        int q, k;

        //Base case where array is 1 element
        if (p == r) return array[p];

        //Set q to equal the index of the pivot after a partition
        q = randomized_partition(array, p, r);
        //Set k to equal what nth order statistic the pivot is of the subarray
        k = q - p + 1;


        if (i == k) { //When requested order is equal to current pivot index
            return array[q];
        } else if (i < k) { //When order is less than current pivot index
            return RandomizedSelect(array, p, q - 1, i);
        } else { //When order is more than the pivot index
            return RandomizedSelect(array, q + 1, r, i - k);
        }
    }


    public static void main(String[] args) {
        //Check for given 2 command line parameters and that the second one is a natural number
        if (!(args.length >= 2 && (Integer.parseInt(args[1]) > 0))) {
            System.out.println("Insufficient command line parameters. Either less than two given or second one is not an integer greater than 0");
        } else {
            OS_Finding osFinding = new OS_Finding();

            //Reads in the input array from args given parameter
            int[] inputArray = osFinding.readArray(args[0]);

            //Finds nth specific order statistic using args given parameter for n
            //Checks that second command line parameter is valid
            if (Integer.parseInt(args[1]) > inputArray.length ) {
                System.out.println("null");
            } else {
                //If valid, find result
                int orderStatistic = osFinding.RandomizedSelect(inputArray, 0, inputArray.length - 1, Integer.parseInt(args[1]));
                //Print out result
                System.out.println(orderStatistic);
            }
        }


    }
}


