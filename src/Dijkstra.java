import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.PriorityQueue;
import java.util.Queue;
import java.lang.*;
import java.util.*;
import static java.lang.System.out;
import static java.lang.System.setOut;

public class Dijkstra {

    //wewnetrzna klasa do reprezentowania dystansu do krawedzi
    //uzywana przy wyborze najkrotszej sciezki
    class OdlegloscDoKrawedzi implements Comparable<OdlegloscDoKrawedzi>{
        private final int krawedz;  // <------- tu ma byc wierzcholek?
        private int odleglosc; //odleglosc do tej krawedzi

        public OdlegloscDoKrawedzi(int krawedz, int odleglosc){
            this.krawedz = krawedz;
            this.odleglosc = odleglosc;
        }

        public int getKrawedz() { return krawedz; }

        public int getOdleglosc() { return odleglosc; }

        public void setOdleglosc(int odleglosc) { this.odleglosc = odleglosc; }


        public int compareTo(OdlegloscDoKrawedzi p){    //srednio lapie ta metode
            Integer integer_porownywacz = odleglosc;
            int cmp =  integer_porownywacz.compareTo(p.getOdleglosc());
            //compareTo zwraca 0 gdy inty sa sobie rowne

            if(cmp == 0) {
                integer_porownywacz = krawedz;
                return integer_porownywacz.compareTo(p.getKrawedz());
            }

            return 0;
        }


        @Override
        public boolean equals(Object obj){      //wykorzystywane przy PriorityQueue
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            OdlegloscDoKrawedzi inna = (OdlegloscDoKrawedzi) obj;
            if (!getOuterType().equals(inna.getOuterType()))        //sprawdzenie czy na pewno
                return false;                                       //pochodzi od klasy Dijkstra
            if (odleglosc != inna.odleglosc)
                return false;
            if (krawedz != inna.krawedz)        //porownanie zmiennych
                return false;
            return true;                        //jesli wszystko sie zgadza to 'true'
        }

        private Dijkstra getOuterType(){
            return Dijkstra.this;
        }


    }

    //----------------------Klasa glowna------------------------------//

    private Krawedz[] najblizsza_krawedz; //krawedz z ktorej jest najblizej
    private Integer[] najmniejsza_odleglosc;
    private Queue<OdlegloscDoKrawedzi> kolejka;
    private static int liczba_linii;
    private static int dlugosc_linii;
    private static int liczba_wierzcholkow;

    public static int getLiczba_linii() { return liczba_linii; }

    public static int getDlugosc_linii() { return dlugosc_linii; }

    public static int getLiczba_wierzcholkow() { return liczba_wierzcholkow; }

    public static void setLiczba_wierzcholkow(int liczba_wierzcholkow) {
        Dijkstra.liczba_wierzcholkow = liczba_wierzcholkow;
    }

    //konstruktor
    public Dijkstra(Graf graf, int zrodlo) {
        //dla kazdego wierzcholka jego krawedz, z ktorej jest najblizej,
        //najkrotsze znalezione odleglosci do danego wierzcholka
        //lista krawedzi po ktorych tworza najkrotsza sciezke

        this.najblizsza_krawedz = new Krawedz[graf.getLiczba_wierzcholkow()];
        this.najmniejsza_odleglosc = new Integer[graf.getLiczba_wierzcholkow()];
        this.kolejka = new PriorityQueue<OdlegloscDoKrawedzi>(graf.getLiczba_wierzcholkow());


        //westepne ustawienie max wartosci dla kazdego z wierzcholkow
        for (int v = 0; v < graf.getLiczba_wierzcholkow(); v++)
            najmniejsza_odleglosc[v] = Integer.MAX_VALUE;

        najmniejsza_odleglosc[zrodlo] = 0; //odleglosc dla zrodla to 0

        //wstawienie wierzcholka zrodlowego do kolejki
        kolejka.offer(new OdlegloscDoKrawedzi(zrodlo,0));

        //relaksacja grafu dopoki kolejka nie bedzie pusta
        while (!kolejka.isEmpty()){
            relax(graf,kolejka.poll().getKrawedz());
            //poll() pobiera i usuwa pierwszy element kolejki
        }
    }

    //funkcja do sprawdzania czy zmiana wierzcholka skroci dystans
    private void relax(Graf graf, int v){
        for(Krawedz k : graf.getLista_sasiedztwa(v) ){
            int w = k.getCel();

            if(najmniejsza_odleglosc[w] > najmniejsza_odleglosc[v] + k.getWaga() && k.getWaga() !=0){
                najmniejsza_odleglosc[w] = najmniejsza_odleglosc[v] + k.getWaga();
                najblizsza_krawedz[w] = k;  //do wierzcholka 'w' najblizej krawedzia 'k'
                OdlegloscDoKrawedzi odk = new OdlegloscDoKrawedzi(w, najmniejsza_odleglosc[w]);

                kolejka.remove(odk);    //jesli jest juz krawedz to usuwamy
                kolejka.offer(odk);     //wstawiamy nowa krawedz z aktualna odlegloscia
            }
        }
    }
    //jesli zwroci MAX_VALUE to znaczy ze wierzcholek jest nieosiagalny
    public int getNajmniejszaOdleglosc(int v){
        return najmniejsza_odleglosc[v];
    }

    public boolean czyMaSciezkeDo(int v){
        return najmniejsza_odleglosc[v] < Integer.MAX_VALUE;
    }

    //jesli nie ma sciezki do danego wierzcholka zwroci pusta kolekcje
    public Iterable<Krawedz> znajdzSciezkeDo(int v){
        Deque<Krawedz> sciezka = new ArrayDeque<Krawedz>();
        if(!czyMaSciezkeDo(v)){     //jesli nie ma sciezki zwroc pusta kolekcje
            return sciezka;
        }

        for(Krawedz k = najblizsza_krawedz[v]; k != null; k = najblizsza_krawedz[k.getZrodlo()]){
            sciezka.push(k);
        }
            //przechodzi przez wszystkie krawedzie w dol i spisuje cala sciezke
        return sciezka;

    }

    private static int zwrocKombinacje(int liczba_paczek){      //do zwrocenia liczby kombinacji tras miedzy paczkami
        if (liczba_paczek == 1)
            return 1;
        else if (liczba_paczek == 0)
            return 0;
        return liczba_paczek * zwrocKombinacje(liczba_paczek-1);
    }


    static int licznik_permutacji =0;
    private static void permutacje(int[] arr, int index, int[][] target){       //permutacje potrzebne sa zeby wygenerowac kazda mozliwosc kolejnosc paczek
                                                                        //aby pozniej je ze soba porownac i wybrac najkrotsza
        if(index >= arr.length - 1){ //If we are at the last element - nothing left to permute

            for(int i = 1; i < arr.length; i++){
                target[licznik_permutacji][i] = arr[i-1];
            }
            if(arr.length > 0){
                target[licznik_permutacji][arr.length] = arr[arr.length -1];
                licznik_permutacji++;
            }
            return;
        }

        for(int i = index; i < arr.length; i++){ //For each index in the sub array arr[index...end]

            //Swap the elements at indices index and i
            int t = arr[index];
            arr[index] = arr[i];
            arr[i] = t;

            //Recurse on the sub array arr[index+1...end]
            permutacje(arr, index+1, target);

            //Swap the elements back
            t = arr[index];
            arr[index] = arr[i];
            arr[i] = t;
        }
    }

    private static int[] usunPowtorzenia(int[] arr){
        int[] powtorzenia = new int[arr.length];
        int licznik = 1;
        powtorzenia[0] = arr[0];
        for (int j=1; j<arr.length; j++){
            for (int i=0; i<powtorzenia.length; i++){
                if (powtorzenia[i] == arr[j]) {
                    break;
                }
                if(i == powtorzenia.length-1){
                    powtorzenia[licznik] = arr[j];
                    licznik++;
                }
            }
        }
        int[] gotowa = new int[licznik];
        for (int i=0; i<licznik; i++){
            gotowa[i] = powtorzenia[i];
        }
        return gotowa;
    }


    public static void znajdzWielkoscMapy(){
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



    //      ################################################             MAIN            ################################################       //

    public static void main(String[] args){

        /** Spis info:
         * najwazniejsze zmienne:
         * ArrayList droga - przechowuje cala najlepsza droge zapisana w kolejnych punktach
         * int najlepsza_waga - waga tej drogi, czyli suma wag z wszystkich polaczen na niej
         * na koncu kodu jest wypisywanie wszystkich drog z punktu ktory sie poda do kazdego w grafie - bardzo useful do sprawdzania jak dziala algorytm
         *
         * jest tutaj jeszcze troche useful metod, jak np permutacje czy usuniecie powtorzen w tablicy
         *
         * Polecam uzyc klasy Najkrocej do spisania sobie odleglosci i punktow
         *
         * TODO na koniec jak zostanie czasu to mozna przesledzic caly kod i pozmieniac wszystkie zmienne na PRIVATE, np w "Najkrocej" wiem ze nie ma
         */


        Dijkstra.znajdzWielkoscMapy();              //statyczna metoda; zwraca liczbe linii i dlugosc linii w pliku z mapa
        Dijkstra.setLiczba_wierzcholkow(dlugosc_linii * (liczba_linii+1) /2);
        Program program = new Program(liczba_linii, dlugosc_linii);
        program.wczytajMape();
        int[] timestampy = program.getTimestampy();
        String[] kierowcy = program.getKierowcy();
        int[][] dane= program.wczytajDane();        //nieparzyste liczby - pozycja w wierszu, parzyste - pozycja w kolumnie

        ArrayList<ArrayList<Integer>> paczki_punkty = new ArrayList<>(dane.length);     //dosyc dlugo glowkowalem jak zrobic to na zwyklej podwojnej tablicy, ale tak w sumie tez jest spoko TODO rozmiar program.getrozmiardanych
        for (int i=0; i<dane.length; i++){
            paczki_punkty.add(new ArrayList<>(4));
        }

        for (int j=0; j<dane.length; j++) {
            for (int i = 0; i < dane[j].length-1; i += 2) {
                paczki_punkty.get(j).add(dane[j][i] + dane[j][i +1] * ((liczba_linii + 1) / 2));
                //jesli dobrze rozumiem (liczba_linii+1)/2 wyznaczy liczbe wierszy
                //powinno to przerobic podany zapis punktow na nasz
            }
        }




//        for (int j=0; j<paczki_punkty.size(); j++) {                      //wyswietlanie wszystkich celow
//            for (int i = 0; i < paczki_punkty.get(j).size(); i++) {
//                System.out.print(paczki_punkty.get(j).get(i) + " ");
//            }
//            System.out.println();
//        }

        int liczba_wierzcholkow = Dijkstra.getLiczba_wierzcholkow();


        Graf graf = new Graf(liczba_wierzcholkow);


        int punkt = 0;                                      //   ***     wypelnienie grafu       ***
        for (int j=0; j<=liczba_linii-1; j+=2){

            for (int i=0; i<dlugosc_linii; i++){
                if ( (punkt+1) % dlugosc_linii !=0 || punkt ==0)                                //dla wszystkich tylko nie dla ostatniego w linii
                    graf.dodajKrawedz(new Krawedz(punkt,punkt+1, program.mapa[i][j]));

                if (j != liczba_linii-1)                                           //dla ostatniej linijki nie dodawaj polaczen w pionie
                    graf.dodajKrawedz(new Krawedz(punkt,punkt+dlugosc_linii, program.mapa[i][j+1]));
                punkt++;
            }
        }


//                                           //   ***     wypelnienie grafu       ***  //
// int punkt = 0;                                      TODO zostawilem to bo jesli bys chcial to lepiej widac jak to dziala, ale do usuniecia jak sobie przejrzysz
//        for (int j=0; j<=8; j+=2){
//
//            for (int i=0; i<5; i++){
//                if ( (punkt+1) % 5 !=0 || punkt ==0)
//                    graf.dodajKrawedz(new Krawedz(punkt,punkt+1, program.mapa[i][j]));
//
//                if (j!=8)                                           //dla ostatniej linijki nie dodawaj polaczen w pionie
//                    graf.dodajKrawedz(new Krawedz(punkt,punkt+5, program.mapa[i][j+1]));
//                punkt++;
//            }
//        }
                                                                    //  ***     wypelnienie grafu w druga strone    ***
        punkt = liczba_wierzcholkow-1;                 //ostatni punkt w grafie
        for (int j=liczba_linii-1; j>=0; j-=2){

            for (int i=dlugosc_linii-1; i>=0; i--){
                if (punkt % dlugosc_linii !=0 && i!=0)
                    graf.dodajKrawedz(new Krawedz(punkt,punkt-1, program.mapa[i-1][j]));
                if (j != 0)
                    graf.dodajKrawedz(new Krawedz(punkt,punkt-dlugosc_linii, program.mapa[i][j-1]));

                punkt--;
            }
        }

//        punkt = 24;                                 //  ***     wypelnienie grafu w druga strone    ***
//        for (int j=8; j>=0; j-=2){                                TODO to samo co wyzej
//
//            for (int i=4; i>=0; i--){
//                if (punkt % 5 !=0 && i!=0)
//                    graf.dodajKrawedz(new Krawedz(punkt,punkt-1, program.mapa[i-1][j]));
//                if (j != 0)
//                    graf.dodajKrawedz(new Krawedz(punkt,punkt-5, program.mapa[i][j-1]));
//
//                punkt--;
//            }
//        }








                            //      ***     tworzymy tablice zawierajace wartosci najkrotszych sciezek i ich opisanie punktami         ***
        Najkrocej[] najkrocej = new Najkrocej[liczba_wierzcholkow];           //tablica zawierajaca tablice najkrotszych sciezek kazdego wierzcholka
        for (int zrodlo=0; zrodlo<liczba_wierzcholkow; zrodlo++) {
            najkrocej[zrodlo] = new Najkrocej(zrodlo);
            Dijkstra najkrotszaSciezka = new Dijkstra(graf, zrodlo);

            for (int cel = 0; cel < liczba_wierzcholkow; cel++) {
                if (najkrotszaSciezka.czyMaSciezkeDo(cel)) {
                    najkrocej[zrodlo].odleglosci[cel] = najkrotszaSciezka.getNajmniejszaOdleglosc(cel);     //wartosc takiej sciezki

                    if (String.valueOf(najkrotszaSciezka.znajdzSciezkeDo(cel)).equals("[]"))        //jesli nie ma punktow na trasie (bo celem jest ten sam punkt) przepisz ten punkt
                        najkrocej[zrodlo].punkty.get(cel).add(zrodlo+1);
                    else{
                        for (Krawedz krawedz : najkrotszaSciezka.znajdzSciezkeDo(cel)){
                            najkrocej[zrodlo].punkty.get(cel).add(krawedz.getCel()+1);                            //kolejne punkty na sciezce
                        }
                    }
                }
                else
                    najkrocej[zrodlo].odleglosci[cel] = Integer.MAX_VALUE;                      //jesli nie ma dojsc daj "nieskonczonosc"
            }
        }





//---------------------------------------------TESTY--------------------------------------------------//
        ArrayList<String> bledy = new ArrayList<>(10);
        int[] kombinacje = new int[8];
        int[] najlepsza_waga = new int[program.getRozmiarDanych()];
        for (int i = 0; i < kombinacje.length; i++) {
            kombinacje[i] = Dijkstra.zwrocKombinacje(i);        //zeby troche przyspieszyc, od razu szuka kombinacje dla wartosci 0-6 a pozniej tylko wczytuje
        }
        //przykladowe punkty 1 -> 12 -> 7 -> 24 -> 11 -> 1                  TODO po ogarnieciu pobierania paczek z pliku do usuniecia - ale zajme sie tym :)
        outerloop:
        for (int k=0; k<program.getRozmiarDanych(); k++) {
            int[] przykladowe = new int[paczki_punkty.get(k).size()];                                     //to sie dobrze sprawdzilo jako wstepne testy
            //        przykladowe[0] = 11;
            //        przykladowe[1] = 6;
            //        przykladowe[2] = 23;
            //        przykladowe[3] = 10;


            int liczba_kombinacji = kombinacje[przykladowe.length];         //przechowuje aktualna liczbe kombinacji dla danego wykonania petli, zeby kod byl bardziej czytelny
            int liczba_paczek = przykladowe.length;
            for (int i = 0; i < liczba_paczek; i++) {
                if (paczki_punkty.get(k).get(i) < 230 && paczki_punkty.get(k).get(i) >0)
                    przykladowe[i] = paczki_punkty.get(k).get(i);
                else {
                    bledy.add("Blad w " + (k+1) + " linii danych wejsciowych");
                    continue outerloop;
                }
            }

            int[][] cele = new int[liczba_kombinacji][liczba_paczek + 1];            //+1 bo jeszcze zostawie '0' na baze
            Dijkstra.permutacje(przykladowe, 0, cele);                    //wszystkie kombinacje wpisuje do 'cele'
            Dijkstra.licznik_permutacji =0;

            int[] wagi = new int[liczba_kombinacji];      //  wagi dla kazdej mozliwej kombinacji

            for (int j = 0; j < liczba_kombinacji; j++) {               //j - kombinacje z permutacji
                for (int i = 0; i < (cele[j].length - 1); i++) {         //i - kolejne punkty z danej kombinacji
                    wagi[j] += najkrocej[cele[j][i]].odleglosci[cele[j][i + 1]];
                }
                wagi[j] += najkrocej[cele[j][cele[j].length-1]].odleglosci[0];      //na koncu dodaj jeszcze wage na powrot
            }

            //przypisanie najlepszej wagi i indeksu dla ktorej kombinacji wystapila

            if (wagi.length >0)
                najlepsza_waga[k] = wagi[0];
            int[] indeks = new int[program.getRozmiarDanych()];
            for (int i = 1; i < liczba_kombinacji; i++) {
                if (najlepsza_waga[k] > wagi[i]) {
                    najlepsza_waga[k] = wagi[i];
                    indeks[k] = i;
                }
            }


            int[] tab = usunPowtorzenia(cele[indeks[k]]); //z najlepszej kombinacji usuwa sie powtorzenia
            int[] punkty = new int[tab.length + 1];              //przechowuje ta jedna, najlepsza sciezke :O
            ArrayList<Integer> droga = new ArrayList<Integer>((int) ((liczba_paczek + 1) * (Math.sqrt(liczba_paczek) - 1) * 2));         //mniej wiecej oszacowana typowa dlugosc drogi


            for (int i = 0; i < tab.length; i++) {
                punkty[i] = tab[i];             //punkty[] ma '0' jeszcze na koncu, czyli baze
            }


            // ***      Zapisuje najlepsza droge    ***
            for (int j = 0; j < punkty.length - 1; j++) {
                for (int i = 0; i < najkrocej[punkty[j]].punkty.get(punkty[j + 1]).size(); i++) {       //droga okreslona przez punkty
                    droga.add(najkrocej[punkty[j]].punkty.get(punkty[j + 1]).get(i));
                }
            }

                                                                                                                //Wypisywanie
            System.out.println((k+1) +". " + "Najlepsza droga dla " + timestampy[k] + ", " + kierowcy[k] + " to: (" + najlepsza_waga[k] + ")");
            System.out.println("A najlepsza droga dla " + timestampy[k] + ", " + kierowcy[k] + " to:");
            for (int i = 0; i < droga.size(); i++) {
                System.out.print(droga.get(i) + " ");
            }
            System.out.println();
            System.out.println("\n\n\n");

        }
        double srednia_waga =0;                                                           //srednia waga
        for (int i=0; i<program.getRozmiarDanych(); i++){
            srednia_waga += najlepsza_waga[i];
        }
        srednia_waga = srednia_waga / (program.getRozmiarDanych() - bledy.size());
        System.out.println("Srednia waga tras dla wszystkich danych wyjsciowych wynosi: " + srednia_waga);
























                                                                    //TODO zostawilem bo moze sie przydac takie sprawdzanie,ale raczej do usuniecia
//                                // ***      Do sprawdzania drog po punktach    ***
//        for (int k=0; k < liczba_wierzcholkow; k++) {                                 //dla kazdego wierzcholku
//            for (int j = 0; j < liczba_wierzcholkow; j++) {                         //droga do kazdego wierzcholka
//
//                for (int i = 0; i < najkrocej[k].punkty.get(j).size(); i++) {
//                    System.out.print(najkrocej[k].punkty.get(j).get(i) + " ");      //droga okreslona przez punkty
//                }
//                System.out.println();
//            }
//            System.out.println("\n\t Z punktu " + (k+2) + "\n");
//        }




//                                               //     ***     wyswietlanie sciezek      ***                 //TODO to jest tak useful ze moze zostac zakomentowane na przyszlosc
//        int zrodlo = 230;
//        Dijkstra najkrotszaSciezka = new Dijkstra(graf,zrodlo);
//
//        for (int cel = 0; cel<graf.getLiczba_wierzcholkow(); cel++){
//            int zrodlo_wys = zrodlo+1;              //zmienne do wyswietlani
//            int cel_wys = cel+1;
//            if(najkrotszaSciezka.czyMaSciezkeDo(cel)){
//                System.out.print("Z " + zrodlo_wys + " do " + cel_wys + " (" + najkrotszaSciezka.getNajmniejszaOdleglosc(cel) +")\t ");
//                if (najkrotszaSciezka.czyMaSciezkeDo(cel)){
//                    for (Krawedz krawedz : najkrotszaSciezka.znajdzSciezkeDo(cel)){
//                        System.out.print(krawedz);
//                    }
//                }
//            }
//            else
//                System.out.println(zrodlo_wys + " do " + cel_wys + " brak sciezki");
//
//            System.out.println();
//        }
        System.out.println("\nBLEDY:");
        for (int i=0; i< bledy.size(); i++){
            System.out.println(bledy.get(i));
        }
    }
}
