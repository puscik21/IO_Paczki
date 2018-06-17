import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Random;
import java.lang.System;

public class Dane extends Thread{

    Timestamp data = new Timestamp(System.currentTimeMillis());
    String [] kierowcy = {"Marek", "Adam", "Marcin", "Radek", "Krzysiek"};
    int [] wspolrzedne = new int [10];
    Random rand = new Random();

    @Override
    public void run() {
        //super.run();
        try {
            PrintWriter zapis = new PrintWriter("dane.txt");
            zapis.print(System.currentTimeMillis()+" ");
            zapis.print(kierowcy[rand.nextInt(5)]+" ");
            for (int i=0; i<5; i++) {
                zapis.print("(");
                zapis.print(Integer.toString(rand.nextInt(50)));
                zapis.print(" ");
                zapis.print(Integer.toString(rand.nextInt(50)));
                zapis.print(")");
            }
            zapis.print(";");
            zapis.close();
        } catch (FileNotFoundException e) {
        }
    }
}
