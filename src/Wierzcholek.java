import java.util.ArrayList;

public class Wierzcholek {
    ArrayList<Krawedz> krawedzie;
    private int numer;

    public Wierzcholek(int numer) {
        this.numer = numer;
        krawedzie = new ArrayList<>(4);
    }

    public void dodajKrawedz(int cel, int waga){
        Krawedz krawedz = new Krawedz(numer, cel, waga);
        krawedzie.add(krawedz);
    }
}
