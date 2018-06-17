import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class Mapa_test {

    public static void generujmape() throws FileNotFoundException {

        Random generator = new Random();
        int[][] mapa = new int[5][9];
        for (int j = 0; j < 9; j++) {
            for (int i = 0; i < 5; i++) {
                if (j%2 == 0 && i == 4)
                    mapa[i][j] = 0;
                else
                    mapa[i][j] = generator.nextInt(10) + 1;
            }
        }


        PrintWriter zapis = new PrintWriter("mapa_test.txt");
        for (int j = 0; j < 9; j++) {
            for (int i = 0; i < 5; i++) {
                zapis.print(mapa[i][j]);
                zapis.print(" ");
            }
            zapis.println();
        }
        zapis.close();


    }
}
