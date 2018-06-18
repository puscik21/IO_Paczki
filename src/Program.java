import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.util.Scanner;

public class Program {

    private int liczba_linii;
    private int dlugosc_linii;
    private final int rozmiarDanych = znajdzRozmiarDanych();
    private int[] timestampy = new int[rozmiarDanych];
    private String[] kierowcy = new String[rozmiarDanych];
    int[][] mapa;
    String dane = "";

    public int getRozmiarDanych() { return rozmiarDanych; }

    public int[] getTimestampy() { return timestampy; }

    public String[] getKierowcy() { return kierowcy; }


    public Program(int liczba_linii, int dlugosc_linii) {
        this.liczba_linii = liczba_linii;
        this.dlugosc_linii = dlugosc_linii;
        mapa = new int[dlugosc_linii][liczba_linii];
    }


    public void wczytajMape() {
        String linia;
        try {
            Scanner odczyt = new Scanner(new File("mapa_mati.txt"));            //zmieniajac plik trzeba zmienic jeszcze w Dijkstrze w "znajdzWielkoscMapy"
            while (odczyt.hasNext()) {
                for (int j = 0; j < liczba_linii; j++) {
                    linia = odczyt.nextLine();
                    for (int i = 0; i < dlugosc_linii; i++) {
                        mapa[i][j] = Integer.parseInt(linia.substring(i, i + 1));
                    }
                }
            }
//            for (int j=0; j<liczba_linii; j++){           //wyswietla mape, ale jak patrzylem tak zgrubsza, to to dziala :)
//                for (int i=0; i<dlugosc_linii; i++){
//                    System.out.print(mapa[i][j] + " ");
//                }
//                System.out.println();
//            }
        } catch (FileNotFoundException f) {
            System.out.println("No nie udalo sie, kurczaki!");
        }
    }

    public int znajdzRozmiarDanych(){
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

    public int[][] wczytajDane() {                          //TODO jeszcze dorobic uzycie pobranego timestampa i nazwy kierowcy
        int liczba_linii = rozmiarDanych;

        int[][] liczby = new int[liczba_linii][0];
        try {
            Scanner odczyt = new Scanner(new File("dane_mati.txt"));
            for (int j = 0; j < liczba_linii; j++) {


                String linia = odczyt.nextLine();
                linia = linia.replace(".", ",");
                String[] podzielona_linia = linia.split(",", 3);
                //String[] podzielona_linia = linia.split("\\p{Punct}", 3);   //dziele linie na 3 czesci, trzecia to miesko, czyli cyferki
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













                                                                        //stara wersja
//    int[][] mapa = new int[50][99];
//
//    void wczytajmape() {
//        try {
//            Scanner odczyt = new Scanner(new File("mapa.txt"));
//            for (int j = 0; j < 99; j++) {
//                for (int i = 0; i < 50; i++) {
//                    mapa[i][j] = odczyt.nextInt();
//                }
//            }
//        } catch (FileNotFoundException f) {}
//    }

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
