import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class Mala_mapa {

    public static void generujmape() throws FileNotFoundException {

        Random generator = new Random();
        int[][] mapa = new int[10][19];
        for (int j = 0; j < 19; j++) {
            for (int i = 0; i < 10; i++) {
                if (j%2 == 0 && i == 9)
                    mapa[i][j] = 0;
                else
                    mapa[i][j] = generator.nextInt(10) + 1;
            }
        }


        PrintWriter zapis = new PrintWriter("mala_mapa.txt");
        for (int j = 0; j < 19; j++) {
            for (int i = 0; i < 10; i++) {
                zapis.print(mapa[i][j]);
                zapis.print(" ");
            }
            zapis.println();
        }
        zapis.close();


    }
}
