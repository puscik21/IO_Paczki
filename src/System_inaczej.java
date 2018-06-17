import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class System_inaczej {

    int [][] czaspoz = new int [49][50];
    int [][] czaspion = new int [50][49];

    void czytaj(){
        try {
            Scanner odczyt = new Scanner(new File("mapa.txt"));
            for (int i=0; i<49; i++) {
                for (int j=0; j<50; j++) {
                    czaspoz[i][j]= odczyt.nextInt();
                    czaspion[j][i]= odczyt.nextInt();
                }
            }
        } catch (FileNotFoundException f) {}
        try {
            Scanner odczytdane = new Scanner(new File("dane.txt"));
            String dane = odczytdane.nextLine();
        } catch (FileNotFoundException f) {}

    };


}
