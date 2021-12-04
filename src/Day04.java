import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Day04 {
    private static BingoField createBingos(BufferedReader input) {
        int[][] bingo = new int[5][5];

        String line;
        for (int i = 0; i < 5; i++) {
            try {
                line = input.readLine();
            } catch (IOException e) {
                return null;
            }

            if (line == null) return null;

            /*

22 13 17 11  0
 8  2 23  4 24
21  9 14 16  7
 6 10  3 18  5
 1 12 20 15 19
             */

            int start = 0;
            for (int j = 0; j < 5; j++) {
                bingo[i][j] = Integer.parseInt(line.substring(start, start+2).replace(" ", ""));
                start += 3;
            }
        }

        return new BingoField(bingo);
    }

    private static int task01(String source) {
        ArrayList<BingoField> bingos = new ArrayList<>();

        BufferedReader input;

        try {
            input = new BufferedReader(new FileReader(source));
        } catch (FileNotFoundException e) {
            System.err.println("file not found");
            return -1;
        }

        String line;
        try {
            line = input.readLine();
        } catch (IOException e) {
            return -1;
        }
        String[] numbers = line.split(",");
        int[] calledNumbers = new int[numbers.length];

        for (int i = 0; i < calledNumbers.length; i++) {
            calledNumbers[i] = Integer.parseInt(numbers[i]);
        }

        while (true) {
            try {
                line = input.readLine();
            } catch (IOException e) {
                return -1;
            }
            if (line == null) break;
            if (line.equals("")) {
                bingos.add(createBingos(input));
            }
        }

        for (int number : calledNumbers) {
            for (BingoField bingo : bingos) {
                if (bingo.eliminateNumber(number)) {
                    return bingo.calculateScore(number);
                }
            }
        }
        return -1;
    }

    private static int task02(String source) {
        ArrayList<BingoField> bingos = new ArrayList<>();

        BufferedReader input;

        try {
            input = new BufferedReader(new FileReader(source));
        } catch (FileNotFoundException e) {
            System.err.println("file not found");
            return -1;
        }

        String line;
        try {
            line = input.readLine();
        } catch (IOException e) {
            return -1;
        }
        String[] numbers = line.split(",");
        int[] calledNumbers = new int[numbers.length];

        for (int i = 0; i < calledNumbers.length; i++) {
            calledNumbers[i] = Integer.parseInt(numbers[i]);
        }

        while (true) {
            try {
                line = input.readLine();
            } catch (IOException e) {
                return -1;
            }
            if (line == null) break;
            if (line.equals("")) {
                bingos.add(createBingos(input));
            }
        }

        for (int number : calledNumbers) {
            ArrayList<BingoField> trash = new ArrayList<>();
            for (BingoField bingo : bingos) {
                if (bingo.eliminateNumber(number)) {
                    trash.add(bingo);
                }
            }

            bingos.removeAll(trash);
            if (bingos.size() < 1) return trash.get(trash.size()-1).calculateScore(number);
            // if (bingos.size() == 1) return bingos.get(0).calculateScore(number);
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println(task01("resources/day04_input.txt"));
        System.out.println(task02("resources/day04_input.txt"));
    }
}


class BingoField {
    int[][] field;

    public BingoField(int[][] field) {
        this.field = field;
    }

    public boolean eliminateNumber(int n) {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                if (field[i][j] == n) {
                    field[i][j] = -1;

                    return checkWinCondition();
                }
            }
        }

        return false;
    }

    public int calculateScore(int number) {
        int sum = 0;
        for (int[] row : field) {
            for (int value : row) {
                if (value >= 0) sum += value;
            }
        }

        return sum*number;
    }

    private boolean checkWinCondition() {
        for (int i = 0; i < field.length; i++) {
            if ((field[i][0] + field[i][1] + field[i][2] + field[i][3] + field[i][4]) == -5) {
                return true;
            }
            if ((field[0][i] + field[1][i] + field[2][i] + field[3][i] + field[4][i]) == -5) {
                return true;
            }
        }
        return false;
    }
}
