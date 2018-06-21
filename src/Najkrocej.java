import java.util.ArrayList;
import java.util.List;

public class Najkrocej {

    private int liczba_wierzcholkow = Program.getLiczba_wierzcholkow();
    int[] odleglosci = new int[liczba_wierzcholkow];     //tablica tablic - dla kazdego punktu droga do kazdego punktu (jej waga)
    ArrayList<ArrayList<Integer>> punkty = new ArrayList<ArrayList<Integer>>( liczba_wierzcholkow);                         //TODO tez pytanie czy private?


    Najkrocej(){
        for (int i=0; i<liczba_wierzcholkow; i++){
            punkty.add(new ArrayList<Integer>((int)Math.sqrt(liczba_wierzcholkow)*2));
        }
    }
}
