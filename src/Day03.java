import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Day03 {
    private static int[] diagnostics(String source) {
        BufferedReader input;
        //open input data
        try {
            input = new BufferedReader(new FileReader(source));
        } catch (FileNotFoundException e) {
            System.err.println("file not found");
            return null;
        }

        String line;
        int[] occ = new int[12];
        // store input file as int array
        while (true) {
            try {
                line = input.readLine();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

            if (line == null) break;

            char[] vals = line.toCharArray();
            for (int i = 0; i < vals.length; i++) {
                if (vals[i] == '0') {
                    occ[i] -= 1;
                    continue;
                }
                occ[i] += 1;
            }
        }

        return new int[]{distroArrayParse(occ, false), distroArrayParse(occ, true)};
    }

    public static int liveSupportScan(String source) {
        ArrayList<char[]> oxygen = new ArrayList<>();
        ArrayList<char[]> co2 = new ArrayList<>();
        BufferedReader input;

        try {
            input = new BufferedReader(new FileReader(source));
        } catch (FileNotFoundException e) {
            System.err.println("file not found");
            return -1;
        }

        String line;
        while (true) {
            try {
                line = input.readLine();
            } catch (IOException e) {
                e.printStackTrace();
                return -1;
            }

            if (line == null) break;

            char[] lineAsChars = line.toCharArray();
            oxygen.add(lineAsChars);
            co2.add(lineAsChars);
        }

        for (int index = 0; index < oxygen.get(0).length; index++) {
            int occO2 = 0;
            int occCO2 = 0;

            for (char[] dia : co2) {
                if (dia[index] == '0') {
                    occCO2 -= 1;
                    continue;
                }
                occCO2 += 1;
            }

            for (char[] dia : oxygen) {
                if (dia[index] == '0') {
                    occO2 -= 1;
                    continue;
                }
                occO2 += 1;
            }
            System.out.println(occO2);

            ArrayList<char[]> trash = new ArrayList<>();
            if (oxygen.size() > 1) {
                for (char[] dia : oxygen) {
                    if ((occO2<0? dia[index] == '1' : dia[index] == '0')  || (occO2 == 0 && dia[index] == '0')) {
                        trash.add(dia);
                    }
                }
            }

            oxygen.removeAll(trash);
            if (oxygen.size() < 1) {
                oxygen.add(trash.get(trash.size()-1));
            }
            trash = new ArrayList<>();

            if (co2.size() > 1) {
                for (char[] dia : co2) {
                    if ((occCO2>0? dia[index] == '1' : dia[index] == '0') || (occCO2 == 0 && dia[index] == '1')) {
                        trash.add(dia);
                    }
                }
            }

            co2.removeAll(trash);
            if (co2.size() < 1) {
                co2.add(trash.get(trash.size()-1));
            }
        }
        int[] co2Ints = new int[co2.get(0).length];
        int[] o2Ints = new int[co2.get(0).length];
        for (int i = 0; i < co2.get(0).length; i++) {
            co2Ints[i] = co2.get(0)[i]-48;
            o2Ints[i] = oxygen.get(0)[i]-48;
        }

        System.out.printf("co2: %2s,o2: %2s%n", Arrays.toString(co2Ints), Arrays.toString(o2Ints));

        return distroArrayParse(co2Ints, false) * distroArrayParse(o2Ints, false);
    }

    private static int distroArrayParse(int[] binaryDistribution, boolean inverse) {
        int dezNum = 0;
        for (int i = 0; i < binaryDistribution.length; i++) {
            if (inverse? binaryDistribution[i]<0: binaryDistribution[i]>0) {
                dezNum += Math.pow(2,binaryDistribution.length-1-i);
            }
        }
        System.out.println(dezNum);

        return dezNum;
    }

    public static void main(String[] args) {
        int[] diagnostics = diagnostics("resources/day03_input.txt");
        if (diagnostics == null) return;
        System.out.printf("power consumption: %2d%n", diagnostics[0]*diagnostics[1]);

        int lifeSupport = liveSupportScan("resources/day03_input.txt");
        if (lifeSupport < 0) return;
        System.out.printf("life support: %2d%n", lifeSupport);
    }
}
