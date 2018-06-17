import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Testowanko {

    static int liczba_linii = 0;
    static int dlugosc_linii = 0;

    private static void znajdzWielkoscMapy(){
        String linia;
        try {
            Scanner odczyt = new Scanner(new File("mapa_test_razem.txt"));
            while (odczyt.hasNext()){
                linia = odczyt.nextLine();
                liczba_linii++;
                dlugosc_linii = linia.length();
            }

        } catch (FileNotFoundException f) {
            System.out.println("No nie udalo sie, kurczaki!");
        }
    }


    static void wczytajMape() {
        String linia;
        int liczba;
        int[][] mapa = new int[dlugosc_linii][liczba_linii];
        System.out.println(liczba_linii);
        System.out.println(dlugosc_linii);
        try {
            Scanner odczyt = new Scanner(new File("mapa_test_razem.txt"));
            while (odczyt.hasNext()) {
                for (int j=0; j<liczba_linii; j++){
                    linia = odczyt.nextLine();
                    for (int i=0; i<dlugosc_linii; i++){
                        mapa[i][j] = Integer.parseInt(linia.substring(i,i+1));
                    }
                }
            }
            for (int j=0; j<liczba_linii; j++){
                for (int i=0; i<dlugosc_linii; i++){
                    System.out.print(mapa[i][j] + " ");
                }
                System.out.println();
            }


        } catch (FileNotFoundException f) {
            System.out.println("No nie udalo sie, kurczaki!");
        }
    }

    public static void main(String[] args){

        znajdzWielkoscMapy();
        wczytajMape();
    }
}
