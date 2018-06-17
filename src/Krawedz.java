public class Krawedz {
    private final int zrodlo;
    private final int cel;
    private final int waga;

    public Krawedz(int zrodlo, int cel, int waga){
        this.zrodlo = zrodlo;
        this.cel = cel;
        this.waga = waga;
    }

    public int getZrodlo(){ return zrodlo; }

    public int getCel() { return cel; }

    public int getWaga() { return waga; }

    public String toString(){
        int zrodlo_wys = zrodlo+1;
        int cel_wys = cel+1;
        return String.format(zrodlo_wys + " -> " + cel_wys + " (" + waga + ") ");
    }
}
