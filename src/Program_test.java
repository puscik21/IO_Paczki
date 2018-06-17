import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.util.Scanner;

public class Program_test {

    int[][] mapa = new int[5][9];
    String dane = "";

    void wczytajmape() {
        try {
            Scanner odczyt = new Scanner(new File("mapa_test.txt"));
            for (int j = 0; j < 9; j++) {
                for (int i = 0; i < 5; i++) {
                    mapa[i][j] = odczyt.nextInt();
                }
            }
        } catch (FileNotFoundException f) {}
    }



    void wczytajdane() {
        try {
            Scanner odczytdane = new Scanner(new File("dane.txt"));
            dane = odczytdane.nextLine();
            //System.out.println(dane);
            //1527352952988 Adam (13 9)(12 40)(46 27)(38 8)(8 21); <---Przykłdowe dane taki schemat

            dane = dane.replace("(","");        //1527352952988 Adam 13 9)12 40)46 27)38 8)8 21);

            dane = dane.replace(")"," ");       //1527352952988 Adam 13 9 12 40 46 27 38 8 8 21 ;

            dane = dane.replace(" ;","");       //1527352952988 Adam 13 9 12 40 46 27 38 8 8 21

            String [] parts = dane.split(" ");
            int length = parts.length;
            long time = Long.parseLong(parts[0]);   //1527352952988
            Timestamp tsmp = new Timestamp(time);   //do Timestampa
            String driver = parts[1];               //Adam
            int [] coords = new int [length-2];     //tablica do przechowanie współrzędnych
            //System.out.println(coords.length);
            for (int i=0; i<length-2; i++) {
                coords [i] = Integer.parseInt(parts[i+2]);
            }
            int packsnumber = coords.length/2;      //liczba paczek (liczba współrzędnych/2)
            //System.out.println(packsnumber);

        } catch (FileNotFoundException f) { }
    }
}
