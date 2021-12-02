import java.io.*;

public class Day01 {
    private static int calcDepthProgression(int blockSize, int dataSize) {
        BufferedReader input;
        //open input data
        try {
            input = new BufferedReader(new FileReader("resources/day01_input.txt"));
        } catch (FileNotFoundException e) {
            System.err.println("file not found");
            return -1;
        }

        String line = "";
        int currentSum, prevSum = Integer.MAX_VALUE;

        int increase = 0;
        int[] depths = new int[dataSize];
        int i = 0;
        // store input file as int array
        while (true) {
            try {
                line = input.readLine();
            } catch (IOException e) {
                e.printStackTrace();
                return -1;
            }

            if (line == null) break;

            depths[i++] = Integer.parseInt(line);
        }

        //compare sliding blocks
        for (i = 0; i < depths.length; i++) {
            currentSum = 0;
            for (int j = 0; j < blockSize; j++) {
                if (i+j < dataSize) currentSum += depths[i+j];
            }

            if (currentSum > prevSum) increase++;
            prevSum = currentSum;
        }

        return increase;
    }

    public static void main(String[] args) {
        System.out.printf("[Task 1] num of increases: %2d%n", calcDepthProgression(1, 2000));
        System.out.printf("[Task 2] num of increases: %2d%n", calcDepthProgression(3, 2000));

    }
}
