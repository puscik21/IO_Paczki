import java.io.FileNotFoundException;
import java.lang.System;

public class Main {
    /**
     * Mala rozpiska - mozemy tu wpisywac jak przemyslenia lub rzeczy do zrobienia i je wykreslac
     *
     *
     * 1) zastanawiam sie czy nie poprzerabiac tego programu tak zeby wszystkie algorytmy razem z testami itp
     * odpalac wlasnia stad - z Maina ale moze byc z tym troche zabawy
     *
     * 2) Klasy Mala_mapa, Maly_program, Program_test itp wyrzucam, jakby mialy byc potrzebne ze niby generatory czy cos
     * to mozna przywrocic, ale watpie
     * usunalem tez "System", bo raczej to co on mial zawierac znalazlo sie juz w "Program"
     * zostawilem Mapa_test bo jest calkiem useful rozmiar do testow
     *
     * 3) Co z klasa Generator? robimy jakies GUI? Mogloby sie przydac a chyba nie ma duzo przy tym duzo
     *
     * 4) Klasa "Dane" jak rozumiem bedzie naszym pewnego rodzaju generatorem? Wiec chyba zostaje?
     *
     * 5) Tak co do twojego algorytmu to mysle, ze spokojnie mozesz sie sugerowac Floydem,
     * zwlaszcza jesli chodzi o zabawe typu odczyt danych, operacje na nich, wypisanie tego wszystkiego i bledow
     *
     * 6) Sa zmienne ktore zostawilem bez private bo uzytkowanie byloby dosyc mocno utrudnione, no chyba ze masz jakis
     * fajny pomysl zeby to gralo z private
     * takie zmienne to np. mapa[][] w Program.java, odleglosci[] i punkty[][] w Najkrocej.java
     *
     * 7) To chyba tyle :D jakby wyszlo tego duzo to pisz :)
     */

    public static void main(String[] args) {
        //Mapa map = new Mapa();
        try {
            Mapa_test.generujmape();
//            Mala_mapa.generujmape();
//            Mapa.generujmape();
        } catch (FileNotFoundException f) {

            Dane dane = new Dane();
            dane.run();
            Program program = new Program();

            program.wczytajMape();
            program.wczytajdane();
        }
    }
}
