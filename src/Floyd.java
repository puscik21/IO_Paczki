import java.util.ArrayList;
/**
 * for v1 in V[G]
 *      for v2 w V[G]
 *          d[v1][v2] = inf
 *          poprzednik[v1][v2] = null
 *          d[v1][v1] = 0
 *  for edge(v1,v2) in E[G]
 *      d[v1][v2] = w(v1,v2)
 *      poprzednik[v1][v2] = v1
 *  for u in V[G]
 *      for v1 in V[G]
 *          for v2 in V[G]
 *              if d[v1][v2] > d[v1][u] + d[u][v2]
 *                  d[v1][v2] = d[v1][u] + d[u][v2]
 *                  poprzednik[v1][v2] = poprzednik [u][v2]
 */

public class Floyd {

    private static int liczba_wierzcholkow ;

    private static int[][] d;
    private static int inf = 10000;
    private static int licznik_permutacji =0;   //uzywany tylko przy metodzie permutacje

    public static int getLiczba_wierzcholkow(){ return liczba_wierzcholkow; }



    public static boolean czyMaSciezkeDo(int zrodlo, int cel){
        return d[zrodlo][cel] < inf;
    }

    private static int zwrocKombinacje(int liczba_paczek){      //do zwrocenia liczby kombinacji tras miedzy paczkami
        if (liczba_paczek == 1)
            return 1;
        else if (liczba_paczek == 0)
            return 0;
        return liczba_paczek * zwrocKombinacje(liczba_paczek-1);
    }

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


    //      ################################################             MAIN            ################################################       //
    public static void main(String[] args) {

        int dlugosc_linii = Program.getDlugosc_linii();
        int liczba_linii = Program.getLiczba_linii();
        liczba_wierzcholkow = Program.getLiczba_wierzcholkow();
        Program program = new Program();
        program.wczytajMape();
        int[] timestampy = program.getTimestampy();
        String[] kierowcy = program.getKierowcy();
        int[][] dane= program.wczytajDane();        //nieparzyste liczby - pozycja w wierszu, parzyste - pozycja w kolumnie


        d = new int[liczba_wierzcholkow][liczba_wierzcholkow];
        Wierzcholek[] wierzcholki = new Wierzcholek[liczba_wierzcholkow];
        for (int i = 0; i < liczba_wierzcholkow; i++)
            wierzcholki[i] = new Wierzcholek(i);

        //ta wypasiona ArrayLista przechowuje sciezki do danych wierzcholkow
        //cos jakby: [z tego wierzcholka][do tego wierzcholka][zbior wierzcholkow na drodze]
        ArrayList<ArrayList<ArrayList<Integer>>> poprzednik = new ArrayList<>(liczba_wierzcholkow);             //cos jakby tab[][][]
        for (int i = 0; i < liczba_wierzcholkow; i++)
            poprzednik.add(new ArrayList<ArrayList<Integer>>(liczba_wierzcholkow));
        for (int j=0; j< liczba_wierzcholkow; j++) {
            for (int i = 0; i < liczba_wierzcholkow; i++)
                poprzednik.get(j).add(new ArrayList<Integer>((int) Math.sqrt(liczba_wierzcholkow) * 2));
        }

        ArrayList<ArrayList<Integer>> paczki_punkty = new ArrayList<>(dane.length);     //dosyc dlugo glowkowalem jak zrobic to na zwyklej podwojnej tablicy, ale tak w sumie tez jest spoko TODO rozmiar program.getrozmiardanych
        for (int i=0; i<dane.length; i++){
            paczki_punkty.add(new ArrayList<>(4));
        }
        for (int j=0; j<dane.length; j++) {
            for (int i = 0; i < dane[j].length-1; i += 2) {
                paczki_punkty.get(j).add(dane[j][i] + dane[j][i +1] * ((liczba_linii + 1) / 2));
                //(liczba_linii+1)/2 wyznaczy liczbe wierszy
                //powinno to przerobic podany zapis punktow na ten uzywany przez nas
            }
        }


        int punkt = 0;                                          //WYPELNIENIE grafu krawedziami od 1 do ostatniego wierzcholku
        for (int j = 0; j <= liczba_linii - 1; j += 2) {
            for (int i = 0; i < dlugosc_linii; i++) {

                if ((punkt + 1) % dlugosc_linii != 0 || punkt == 0)
                    wierzcholki[punkt].dodajKrawedz(punkt + 1, program.mapa[i][j]);

                if (j != liczba_linii - 1)
                    wierzcholki[punkt].dodajKrawedz(punkt + dlugosc_linii, program.mapa[i][j + 1]);
                punkt++;
            }
        }
        punkt = liczba_wierzcholkow - 1;                          //to samo tylko w druga strone bo krawedzie sa skierowane w jedna strone
        for (int j = liczba_linii - 1; j >= 0; j -= 2) {
            for (int i = dlugosc_linii - 1; i >= 0; i--) {

                if (punkt % dlugosc_linii != 0 && i != 0)
                    wierzcholki[punkt].dodajKrawedz(punkt - 1, program.mapa[i - 1][j]);

                if (j != 0)
                    wierzcholki[punkt].dodajKrawedz(punkt - dlugosc_linii, program.mapa[i][j - 1]);
                punkt--;
            }
        }

//algorytmicznosc algorytmu Floyda
        for (int v1 = 0; v1 < liczba_wierzcholkow; v1++) {
            for (int v2 = 0; v2 < liczba_wierzcholkow; v2++) {
                d[v1][v2] = inf;                                        //inf oznacza brak okreslonej sciezki do danego punktu
            }
        }

        for (int j = 0; j < liczba_wierzcholkow; j++) {
            for (int i = 0; i < wierzcholki[j].krawedzie.size(); i++) {
                d[j][wierzcholki[j].krawedzie.get(i).getCel()] = wierzcholki[j].krawedzie.get(i).getWaga();
            }
        }
        for (int i=0; i<liczba_wierzcholkow; i++){
            d[i][i] = 0;
        }

        for (int j=0; j<liczba_wierzcholkow; j++){
            for (int i=0; i<liczba_wierzcholkow; i++){        // wstepne poprzedniki
                if (Floyd.czyMaSciezkeDo(j, i))
                    poprzednik.get(j).get(i).add(j);
            }
        }
                                                            //glowna czesc algorytmu Floyda
        for (int u = 0; u < liczba_wierzcholkow; u++) {
            for (int v1 = 0; v1 < liczba_wierzcholkow; v1++) {
                for (int v2 = 0; v2 < liczba_wierzcholkow; v2++) {

                    if (d[v1][v2] > d[v1][u] + d[u][v2]) {
                        d[v1][v2] = d[v1][u] + d[u][v2];    //przypisanie wag
                                                                                        //przypisywanie poprzednikow
                        //bez usuwania robily sie wiksy nadpisywania typu 2 razy przez ten sam punkt itp
                        for (int i=poprzednik.get(v1).get(v2).size()-1; i>=0; i--)
                            poprzednik.get(v1).get(v2).remove(i);
                        //droga z v1 do wierzcholka u
                        for (int i=0; i<poprzednik.get(v1).get(u).size(); i++)
                            poprzednik.get(v1).get(v2).add(poprzednik.get(v1).get(u).get(i));
                        //droga z u do wierzcholka v2
                        for (int i=0; i<poprzednik.get(u).get(v2).size(); i++)
                            poprzednik.get(v1).get(v2).add(poprzednik.get(u).get(v2).get(i));

                    }
                }
            }
        }
        for (int j=0; j<liczba_wierzcholkow; j++){
            for (int i=0; i<liczba_wierzcholkow; i++){
                if (Floyd.czyMaSciezkeDo(j, i))
                    poprzednik.get(j).get(i).add(i);        //to tak dla formalnosci, ze na koncu sciezki jest szukany wierzcholek
            }
        }
        for (int j=0; j<liczba_wierzcholkow; j++) {         //usuniecie pierwszego punktu
            for (int i = 0; i < liczba_wierzcholkow; i++)
                poprzednik.get(j).get(i).remove(0);
        }
//koniec algorytmicznosci algorytmu

        Najkrocej[] najkrocej = new Najkrocej[liczba_wierzcholkow];             //tablica wszystkich wierzcholkow, z ktorych kazdy ma w "najkrocej" tablice najkrotszych sciezek
        for (int zrodlo=0; zrodlo<liczba_wierzcholkow; zrodlo++){
            najkrocej[zrodlo] = new Najkrocej();                          //serdeczna inicjalizacja

            for (int cel=0; cel<liczba_wierzcholkow; cel++){
                if (czyMaSciezkeDo(zrodlo, cel)){
                    najkrocej[zrodlo].odleglosci[cel] = d[zrodlo][cel];         //wartosci sciezek do kazdego wierzcholka

                    for (int i=0; i<poprzednik.get(zrodlo).get(cel).size(); i++)
                        najkrocej[zrodlo].punkty.get(cel).add(poprzednik.get(zrodlo).get(cel).get(i));

                }
                else
                    najkrocej[zrodlo].odleglosci[cel] = Integer.MAX_VALUE;
            }
        }


//---------------------------------------------TESTY--------------------------------------------------//
        ArrayList<String> bledy = new ArrayList<>(10);
        int[] kombinacje = new int[8];
        int[] najlepsza_waga = new int[program.getRozmiarDanych()];
        for (int i = 0; i < kombinacje.length; i++) {
            kombinacje[i] = Floyd.zwrocKombinacje(i);        //zeby troche przyspieszyc, od razu szuka kombinacje dla wartosci 0-6 a pozniej tylko wczytuje
        }

        outerloop:
        for (int k=0; k<program.getRozmiarDanych(); k++) {
            int[] przykladowe = new int[paczki_punkty.get(k).size()];                                     //do przykladowych przejda te ktore beda spelnialy warunki
            int liczba_paczek = przykladowe.length;
            int liczba_kombinacji = kombinacje[przykladowe.length];         //przechowuje aktualna liczbe kombinacji dla danego wykonania petli, zeby kod byl bardziej czytelny

            if (k == 0) {
                for (int i = 0; i < liczba_paczek; i++) {
                    if (paczki_punkty.get(k).get(i) < 230 && paczki_punkty.get(k).get(i) > 0)
                        przykladowe[i] = paczki_punkty.get(k).get(i);
                    else
                        bledy.add("Blad w " + (k + 1) + " linii danych wejsciowych");
                }
            }
            else{
                for (int i = 0; i < liczba_paczek; i++) {
                    if (paczki_punkty.get(k).get(i) < 230 && paczki_punkty.get(k).get(i) >0 && (timestampy[k] > timestampy[k-1]))
                        przykladowe[i] = paczki_punkty.get(k).get(i);
                    else {
                        bledy.add("Blad w " + (k+1) + " linii danych wejsciowych");
                        continue outerloop;
                    }
                }
            }

            int[][] cele = new int[liczba_kombinacji][liczba_paczek + 1];            //+1 bo jeszcze zostawie '0' na baze
            Floyd.permutacje(przykladowe, 0, cele);                    //wszystkie kombinacje wpisuje do 'cele'
            Floyd.licznik_permutacji =0;                                        //trzeba dziada wyzerowac bo inaczej bylby wiekszy niz tablica w permutacje

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
            int[] indeks = new int[program.getRozmiarDanych()];     //rozmiarDanych to liczba linii danych
            for (int i = 1; i < liczba_kombinacji; i++) {
                if (najlepsza_waga[k] > wagi[i]) {
                    najlepsza_waga[k] = wagi[i];
                    indeks[k] = i;
                }
            }


            int[] tab = usunPowtorzenia(cele[indeks[k]]); //z najlepszej kombinacji usuwa sie powtorzenia
            int[] punkty = new int[tab.length + 1];              //przechowuje ta jedna, najlepsza sciezke :O, +1 bo na koncu jeszcze baza
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
                System.out.print((droga.get(i)+1) + " ");               //+1 bo wczesniej wierzcholki byly zapisywane od 0
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

        System.out.println("\nBLEDY:");
        for (int i=0; i< bledy.size(); i++){
            System.out.println(bledy.get(i));
        }




//                                                                         //troche wyswietlania:
//        punkt =0;
//            for (int i=0; i<liczba_wierzcholkow; i++)                       //drogi
//                System.out.println("Dla d["+punkt+"]["+i+"] idzie sie przez " + poprzednik.get(punkt).get(i));
    }
}
