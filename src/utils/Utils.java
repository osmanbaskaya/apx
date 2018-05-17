package utils;

import java.io.*;
import java.util.*;

public class Utils {

    final static String STOPWORDS_FILE = "misc/stopwords.txt";


    public static Map<String, Integer> sortByComparator(Map<String, Integer> unsortMap, final boolean isAscending) {

        List<Map.Entry<String, Integer>> list = new LinkedList<>(unsortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, (o1, o2) -> {
            if (isAscending) {
                return o1.getValue().compareTo(o2.getValue());
            } else {
                return o2.getValue().compareTo(o1.getValue());

            }
        });

        // Maintaining insertion order with the help of LinkedList
        Map<String, Integer> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

    public static void normalizeInPlace(double[][] matrix) {

        int rowLen = matrix.length;
        int colLen = matrix[0].length;

        for (int i = 0; i < rowLen; i++) {
            double total = 0;
            for (int j = 0; j < colLen; j++) {
                total += matrix[i][j];
            }
            for (int j = 0; j < colLen; j++) {
                matrix[i][j] /= total;
            }
        }
    }

    public static double calculateEuclideanDistance(double[] vector1, double[] vector2) {
        double total = 0;
        assert vector1.length == vector2.length;
        for (int i = 0; i < vector1.length; i++) {
            total += Math.pow(vector1[i] - vector2[i], 2);
        }
        return Math.sqrt(total);
    }


    public static double[] add(double[] total, double[] x) {
        assert total.length == x.length;
        double[] vector = new double[total.length];
        for (int i = 0; i < total.length; i++) {
            vector[i] = total[i] + x[i];
        }
        return vector;
    }

    public static double[] divideScalar(double[] other, int scalar) {
        double[] vector = new double[other.length];
        for (int i = 0; i < other.length; i++) {
            vector[i] = other[i] / scalar;
        }
        return vector;
    }

    public static int numUniqueElement(int[] array) {

        Set<Integer> s = new HashSet<>();
        for (int anArray : array) {
                s.add(anArray);
        }

        return s.size();
    }

    public static Map<Integer, Integer> getLabelMap(int[] array) {

        Map<Integer, Integer> labelMap = new HashMap<>();
        int index = 0;
        for (int labelIdx : array) {
            if (!labelMap.containsKey(labelIdx)){
                labelMap.put(labelIdx, index);
                index++;
            }
        }

        return labelMap;
    }

    public static int sum(int[][] m) {
        int total = 0;
        for (int[] aM : m) {
            for (int j = 0; j < m[0].length; j++) {
                total += aM[j];
            }
        }
        return total;
    }

    public static int sum(int[] array) {
        int total = 0;
        for (int e : array) {
            total += e;
        }
        return total;
    }

    public static void printArray(int [] array) {
        for (int anArray : array) System.out.print(anArray + " ");
        System.out.println();
    }

    public static void printMatrix(int [][] m) {
        for (int[] array : m) {
            for (int e : array)
                System.out.print(e + " ");
            System.out.println();
        }
        System.out.println();
    }

    public static Set<String> getStopWordSet(String filename) throws IOException {
        Set<String> stopWords = new HashSet<>();
        if (filename == null)
            filename = Utils.STOPWORDS_FILE;

        File file = new File(filename);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String s;
        while ((s = br.readLine()) != null) {
            Collections.addAll(stopWords, s.split("\\s+"));
        }

        return stopWords;
    }

    public static List<String> getUrlAddresses(String filename) throws IOException {
        File file = new File(filename);
        List<String> urls = new ArrayList<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String s;
        while ((s = br.readLine()) != null) {
            urls.add(s);
        }
        return urls;
    }

}
