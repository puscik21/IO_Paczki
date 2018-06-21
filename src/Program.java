import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.util.Scanner;

public class Program {

    private static int liczba_linii;
    private static int dlugosc_linii;
    private static int liczba_wierzcholkow;
    private final int rozmiarDanych = znajdzRozmiarDanych();
    private int[] timestampy = new int[rozmiarDanych];
    private String[] kierowcy = new String[rozmiarDanych];
    int[][] mapa;                                                       //TODO meczyc to na private?
    private String dane = "";

    int getRozmiarDanych() { return rozmiarDanych; }

    int[] getTimestampy() { return timestampy; }

    String[] getKierowcy() { return kierowcy; }

    static int getLiczba_linii() {
        liczba_linii =0;
        dlugosc_linii =0;
        Program.znajdzWielkoscMapy();
        return liczba_linii;
    }

    static int getDlugosc_linii() {
        liczba_linii =0;
        dlugosc_linii =0;
        Program.znajdzWielkoscMapy();
        return dlugosc_linii;
    }

    static int getLiczba_wierzcholkow() {
        liczba_linii =0;
        dlugosc_linii =0;
        Program.znajdzWielkoscMapy();
        liczba_wierzcholkow = dlugosc_linii * (liczba_linii+1) /2;
        return liczba_wierzcholkow;
    }

    public Program() {
        liczba_linii =0;
        dlugosc_linii =0;
        Program.znajdzWielkoscMapy();
        mapa = new int[dlugosc_linii][liczba_linii];
    }


    void wczytajMape() {
        String linia;
        try {
            Scanner odczyt = new Scanner(new File("mapa_mati.txt"));
            while (odczyt.hasNext()) {
                for (int j = 0; j < liczba_linii; j++) {
                    linia = odczyt.nextLine();
                    for (int i = 0; i < dlugosc_linii; i++) {
                        mapa[i][j] = Integer.parseInt(linia.substring(i, i + 1));
                    }
                }
            }
        } catch (FileNotFoundException f) {
            System.out.println("No nie udalo sie, kurczaki!");
        }
    }

    static void znajdzWielkoscMapy(){
        String linia;
        try {
            Scanner odczyt = new Scanner(new File("mapa_mati.txt"));
            while (odczyt.hasNext()){
                linia = odczyt.nextLine();
                liczba_linii++;
                dlugosc_linii = linia.length();
            }

        } catch (FileNotFoundException f) {
            System.out.println("No nie udalo sie, kurczaki!");
        }
    }

    int znajdzRozmiarDanych(){           //zwraca liczbe linii danych
        int licznik =0;
        try{
            Scanner odczyt = new Scanner(new File("dane_mati.txt"));
            while (odczyt.hasNext()){
                odczyt.nextLine();
                licznik++;
            }
        } catch ( FileNotFoundException e){
            System.out.println("Nie udalo sie. Na co drazyc temat");
        }
        return licznik;
    }

    int[][] wczytajDane() {
        int liczba_linii = rozmiarDanych;

        int[][] liczby = new int[liczba_linii][0];
        try {
            Scanner odczyt = new Scanner(new File("dane_mati.txt"));
            for (int j = 0; j < liczba_linii; j++) {


                String linia = odczyt.nextLine();
                linia = linia.replace(".", ",");
                String[] podzielona_linia = linia.split(",", 3);
                String miesko = podzielona_linia[2];
                timestampy[j] = Integer.valueOf(podzielona_linia[0]);
                kierowcy[j] = podzielona_linia[1];
                miesko = miesko.replaceAll("[^-?0-9]+", " ");     //stackoverflow to wspaniale miejsce <3
                miesko = miesko.trim();           //przycina spacje na poczatku i koncu
                String[] dane = miesko.split(" ");      //podzielenie na Stringo-cyfry

                liczby[j] = new int[dane.length];
                for (int i = 0; i < dane.length; i++) {
                    liczby[j][i] = Integer.parseInt(dane[i])-1;      //zamiana ze Stringa na inta //-1 bo my opisujemy punkty od 0
                }
            }

        } catch(FileNotFoundException e) {
            System.out.println("Nie znaleziono pliku z danymi");
        }

        return liczby;
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
