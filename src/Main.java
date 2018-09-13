import java.io.FileNotFoundException;
import java.lang.System;

public class Main {
    /**
     * Mala rozpiska - mozemy tu wpisywac jak przemyslenia lub rzeczy do zrobienia i je wykreslac
     * <p>
     * <p>
     * 1) zastanawiam sie czy nie poprzerabiac tego programu tak zeby wszystkie algorytmy razem z testami itp
     * odpalac wlasnia stad - z Maina ale moze byc z tym troche zabawy
     * <p>
     * 2) Klasy Mala_mapa, Maly_program, Program_test itp wyrzucam, jakby mialy byc potrzebne ze niby generatory czy cos
     * to mozna przywrocic, ale watpie
     * usunalem tez "System", bo raczej to co on mial zawierac znalazlo sie juz w "Program"
     * zostawilem Mapa_test bo jest calkiem useful rozmiar do testow
     * <p>
     * 3) Co z klasa Generator? robimy jakies GUI? Mogloby sie przydac a chyba nie ma duzo przy tym duzo
     * <p>
     * 4) Klasa "Dane" jak rozumiem bedzie naszym pewnego rodzaju generatorem? Wiec chyba zostaje?
     * <p>
     * 5) Tak co do twojego algorytmu to mysle, ze spokojnie mozesz sie sugerowac Floydem,
     * zwlaszcza jesli chodzi o zabawe typu odczyt danych, operacje na nich, wypisanie tego wszystkiego i bledow
     * <p>
     * 6) Sa zmienne ktore zostawilem bez private bo uzytkowanie byloby dosyc mocno utrudnione, no chyba ze masz jakis
     * fajny pomysl zeby to gralo z private
     * takie zmienne to np. mapa[][] w Program.java, odleglosci[] i punkty[][] w Najkrocej.java
     * <p>
     * 7) To chyba tyle :D jakby wyszlo tego duzo to pisz :)
     */

    private static void generuj(){          //  to bylo wczesniej normalnie w main(), ale nie wiem czy potrzebne, wiec wyrzucilem
        try {
            Mapa_test.generujmape();
        } catch (FileNotFoundException f) {

            Dane dane = new Dane();
            dane.run();
            Program program = new Program();

            program.wczytajMape();
            program.wczytajdane();
        }
    }


    public static void main(String[] args) {

        Floyd floyd = new Floyd();
        floyd.main();

        Dijkstra dijkstra = new Dijkstra();
        dijkstra.main();
    }
}

