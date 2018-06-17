import java.util.*;

public class Graf {
    private final int liczba_wierzcholkow;
    private int liczba_krawedzi;
    private List<Krawedz>[] lista_sasiedztwa;       //bedzie wyswietlac krawedzie
                                                    //dla danego wierzcholka
//konstruktor
    public Graf(int liczba_wierzcholkow){
        this.liczba_wierzcholkow = liczba_wierzcholkow;
        this.liczba_krawedzi = 0;
        this.lista_sasiedztwa = (List<Krawedz>[]) new List[liczba_wierzcholkow];

        for (int i=0; i<liczba_wierzcholkow; i++){
            lista_sasiedztwa[i] = new ArrayList<Krawedz>(); //arraylist bo bedziemy
                                                            //dodawac nieznana ilosc krawedzi
        }
    }

    public int getLiczba_wierzcholkow() { return liczba_wierzcholkow; }

    public int getLiczba_krawedzi() { return liczba_krawedzi; }

    //zwraca liste sasiedztwa konkretnego wierzcholka
    public Iterable<Krawedz> getLista_sasiedztwa(int wierzcholek) {
        return lista_sasiedztwa[wierzcholek];
    }

    //funkcja dodaje krawedz skierowana
    public void dodajKrawedz(Krawedz k){
        lista_sasiedztwa[k.getZrodlo()].add(k);
        liczba_krawedzi++;
    }


}
