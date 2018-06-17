import java.util.ArrayList;
import java.util.List;

public class Najkrocej {                   //TODO zmienic wielkosci tablic


    private int zrodlo;
    private int liczba_wierzcholkow = Dijkstra.getLiczba_wierzcholkow();
    public int[] odleglosci = new int[liczba_wierzcholkow];
    //tablica tablic - dla kazdego punktu[] droga do kazdego punktu[]
    public ArrayList<ArrayList<Integer>> punkty = new ArrayList<ArrayList<Integer>>( liczba_wierzcholkow);


    Najkrocej(int zrodlo){
        this.zrodlo = zrodlo;
        for (int i=0; i<liczba_wierzcholkow; i++){
            punkty.add(new ArrayList<Integer>((int)Math.sqrt(liczba_wierzcholkow)*2));
        }
    }
}
