import com.sun.jdi.VMOutOfMemoryException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day02 {
    private static int[] calcPosition(String source) {
        int x = 0, y = 0;
        BufferedReader input;
        //open input data
        try {
            input = new BufferedReader(new FileReader(source));
        } catch (FileNotFoundException e) {
            System.err.println("file not found");
            return null;
        }

        String line;
        while (true) {
            try {
                line = input.readLine();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

            if (line == null) break;

            if (line.charAt(0) == 'f') {
                x +=(Integer.parseInt(line.split(" ")[1]));
                continue;
            }
            int yTemp = (Integer.parseInt(line.split(" ")[1]));
            if (line.charAt(0) == 'u') {
                yTemp *= -1;
            }
            y += yTemp;
        }

        return new int[]{x, y};
    }

    private static int[] calcPositionWithAim(String source) {
        int x = 0, y = 0, aim = 0;
        BufferedReader input;
        //open input data
        try {
            input = new BufferedReader(new FileReader(source));
        } catch (FileNotFoundException e) {
            System.err.println("file not found");
            return null;
        }

        String line;
        while (true) {
            try {
                line = input.readLine();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

            if (line == null) break;

            if (line.charAt(0) == 'f') {
                int xTemp = Integer.parseInt(line.split(" ")[1]);
                x += xTemp;
                y += (aim * xTemp);
                continue;
            }
            int yTemp = (Integer.parseInt(line.split(" ")[1]));
            if (line.charAt(0) == 'u') {
                yTemp *= -1;
            }
            aim += yTemp;
        }

        return new int[]{x, y};
    }

    public static void main (String[]args){
        int[] position = calcPosition("resources/day02_input.txt");
        if (position == null) return;
        System.out.printf("x: %2d y: %2d mult: %2d%n", position[0], position[1], position[0]*position[1]);
        position = calcPositionWithAim("resources/day02_input.txt");
        if (position == null) return;
        System.out.printf("x: %2d y: %2d mult: %2d%n", position[0], position[1], position[0]*position[1]);
    }
}
