import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

public class Knapsack {

    public static void main(String[] args) throws IOException {
        int[] values;
        int[] weight;
        int capacity;
        int numItems;

        String[] fileNames = {"ks_4.txt", "ks_19.txt", "ks_200.txt", "ks_10000.txt"};
        for (String fileName : fileNames) {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));

            String[] firstLine = reader.readLine().split(" ");
            numItems = Integer.parseInt(firstLine[0]);
            capacity = Integer.parseInt(firstLine[1]);

            values = new int[numItems];
            weight = new int[numItems];

            for (int i = 0; i < numItems; i++) {
                String[] line = reader.readLine().split(" ");
                values[i] = Integer.parseInt(line[0]);
                weight[i] = Integer.parseInt(line[1]);
            }

            int[] result = knapsack(capacity, values, weight);

            long start = System.currentTimeMillis();
            System.out.println("" + fileName +" "+ "Reading File...");
            System.out.println(fileName + " " + "optimal value" + " = " + result[0]);
            for (int i = 0; i < numItems; i++) {
                System.out.print(result[1] % 2 + " ");
                result[1] /= 2;
            }
            long stop = System.currentTimeMillis();
            long timeElapsed = stop - start;
            System.out.println("\nTime Elapsed(ms)"+" "+ timeElapsed);
            System.out.println();
        }
    }

    public static int[] knapsack(int capacity, int[] values, int[] weight) {
        int n = values.length;
        int[] dp = new int[capacity + 1];
        for (int i = 0; i < n; i++) {
            for (int w = capacity; w >= weight[i]; w--) {
                dp[w] = Math.max(dp[w], dp[w - weight[i]] + values[i]);
            }
        }
        int chosen = 0;
        for (int i = n - 1, w = capacity; i >= 0 && w > 0; i--) {
            if (w - weight[i] >= 0 && dp[w] == dp[w - weight[i]] + values[i]) {
                chosen |= 1 << i;
                w -= weight[i];
            }
        }
        return new int[]{dp[capacity], chosen};
    }
}
