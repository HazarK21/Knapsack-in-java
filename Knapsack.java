import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

public class Knapsack {

    public static void main(String[] args) throws IOException {
        int[] degerler;
        int[] agirlik;
        int kapasite;
        int numItems;

        String[] dosyaAdlari = {"ks_4.txt", "ks_19.txt", "ks_200.txt", "ks_10000.txt"};
        for (String dosyaAdi : dosyaAdlari) {
            BufferedReader reader = new BufferedReader(new FileReader(dosyaAdi));

            String[] ilkSatir = reader.readLine().split(" ");
            numItems = Integer.parseInt(ilkSatir[0]);
            kapasite = Integer.parseInt(ilkSatir[1]);

            degerler = new int[numItems];
            agirlik = new int[numItems];

            for (int i = 0; i < numItems; i++) {
                String[] line = reader.readLine().split(" ");
                degerler[i] = Integer.parseInt(line[0]);
                agirlik[i] = Integer.parseInt(line[1]);
            }

            int[] result = knapsack(kapasite, degerler, agirlik);

            long basla = System.currentTimeMillis();
            System.out.println("" + dosyaAdi +" "+ "Dosyasi isleniyor...");
            System.out.println(dosyaAdi + " " + "optimal value degeri" + " = " + result[0]);
            for (int i = 0; i < numItems; i++) {
                System.out.print(result[1] % 2 + " ");
                result[1] /= 2;
            }
            long bitir = System.currentTimeMillis();
            long timeElapsed = bitir - basla;
            System.out.println("\nHarcanan sure(ms)"+" "+ timeElapsed);
            System.out.println();
        }
    }

    public static int[] knapsack(int kapasite, int[] degerler, int[] agirlik) {
        int n = degerler.length;
        int[] dp = new int[kapasite + 1];
        for (int i = 0; i < n; i++) {
            for (int w = kapasite; w >= agirlik[i]; w--) {
                dp[w] = Math.max(dp[w], dp[w - agirlik[i]] + degerler[i]);
            }
        }
        int secilenler = 0;
        for (int i = n - 1, w = kapasite; i >= 0 && w > 0; i--) {
            if (w - agirlik[i] >= 0 && dp[w] == dp[w - agirlik[i]] + degerler[i]) {
                secilenler |= 1 << i;
                w -= agirlik[i];
            }
        }
        return new int[]{dp[kapasite], secilenler};
    }
}