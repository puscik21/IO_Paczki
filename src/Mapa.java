import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class Mapa {

    public static void generujmape() throws FileNotFoundException {

        Random generator = new Random();
        int[][] mapa = new int[50][99];
        for (int j = 0; j < 99; j++) {
            for (int i = 0; i < 50; i++) {
                if (j%2 == 0 && i == 49)
                    mapa[i][j] = 0;
                else
                    mapa[i][j] = generator.nextInt(10) + 1;
            }
        }


        PrintWriter zapis = new PrintWriter("mapa.txt");
        for (int j = 0; j < 99; j++) {
            for (int i = 0; i < 50; i++) {
                zapis.print(mapa[i][j]);
                zapis.print(" ");
            }
            zapis.println();
        }
        zapis.close();


    }
}
