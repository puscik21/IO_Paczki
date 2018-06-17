import java.io.FileNotFoundException;
import java.lang.System;

public class Main {

    public static void main(String[] args) {
        //Mapa map = new Mapa();
        try {
            Mapa.generujmape();
            Mala_mapa.generujmape();
            Mapa_test.generujmape();
        } catch (FileNotFoundException f) {

        } finally {
            System.out.println("Hello");
        }
        Dane dane = new Dane();
        dane.run();
        Dijkstra.znajdzWielkoscMapy();
        Program program = new Program(Dijkstra.getLiczba_linii(), Dijkstra.getDlugosc_linii());
        // ^ Teraz zeby utworzyc Program trzeba podac od razu podac wielkosc mapy
        //Wygodniej mi bylo zaimplementowac szukanie wielkosci w Dijkstrze wiec mozna z tej metody po prostu w ten sposob korzytac
        //Wiem, ze to moze byc mniej przejrzyste teraz, wiec jesli masz lepszy pomysl to mozesz np. przeniesc szukanie w inne miejsce

        program.wczytajMape();
        program.wczytajdane();
    }
}
