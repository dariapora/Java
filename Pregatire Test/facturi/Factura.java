package facturi;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Factura {
    private final String denumireClient;
    private final LocalDate dataEmitere;
    private final List<Linie> linii;

    static class Linie {
        private final String produs;
        private final double pret;
        private final int cantitate;

        public Linie(String produs, double pret, int cantitate) {
            this.produs = produs;
            this.pret = pret;
            this.cantitate = cantitate;
        }

        public String getProdus() {
            return produs;
        }

        public double getPret() {
            return pret;
        }

        public int getCantitate() {
            return cantitate;
        }

        public double getValoare() {
            return pret * cantitate;
        }

        @Override
        public String toString() {
            return String.format("%-25s %3d x %5.2f RON = %6.2f RON",
                    produs, cantitate, pret, getValoare());
        }
    }

    public Factura(String denumireClient, LocalDate dataEmitere) {
        this.denumireClient = denumireClient;
        this.dataEmitere = dataEmitere;
        this.linii = new ArrayList<Linie>();
    }

    public String getDenumireClient() {
        return denumireClient;
    }

    public LocalDate getDataEmitere() {
        return dataEmitere;
    }

    public List<Linie> getLinii() {
        return linii;
    }

    public void adaugaLinie(Linie linie)
    {
        linii.add(linie);
    }

    public void adaugaLinie(String produs, double pret, int cantitate)
    {
        linii.add(new Linie(produs, pret, cantitate));
    }
}
