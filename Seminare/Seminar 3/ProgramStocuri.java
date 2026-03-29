// să se definească un câmp static stocuri de tip dicționar care să asocieze fiecărui produs lista de tranzacții aferente.
//să se definească o metodă statică AdaugaProdus(int cod, String denumire) care să adauge un produs nou în lista de stocuri. Produsul NU trebuie să existe în stocuri.
//să se definească o metodă statică AdaugaTranzactie(TipTranzactie tip,LocalDate data,int codProdus,int cantitate) care să adauge o tranzacție nouă. Produsul trebuie să existe în stocuri.
//să se definească o metodă statică AfisareStocuri() care să afișeze lista de produse cu: cod,
// denumire, stocCurent, data ultimei tranzacții


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import stocuri.TipTranzactie;
import stocuri.Produs;
import stocuri.Tranzactie;

public class ProgramStocuri {

    static Map<Produs, ArrayList<Tranzactie>> stocuri = new HashMap<>();

    static void AdaugaProdus(int cod, String denumire) {
        Produs produs = new Produs(cod, denumire);
        if(stocuri.containsKey(produs)) throw new RuntimeException("Produsul exista deja.");
        stocuri.put(produs, new ArrayList<>(0));
    }

    static void AdaugaTranzactie(TipTranzactie tip, LocalDate data, int codProdus, int cantitate)
    {
        Produs produsGasit = new Produs(codProdus);
        if(stocuri.containsKey(produsGasit)) {
            stocuri.get(produsGasit).add(new Tranzactie(tip, data, codProdus, cantitate));
        }
        else throw new IllegalArgumentException("Produsul nu a fost gasit.");
    }

    static void AfisareStocuri()
    {
        for (var produs : stocuri.entrySet())
        {
            if(!produs.getValue().isEmpty())
            {
                int stocCurent = 0;
            LocalDate date = LocalDate.MIN;
            for (var tranzactie : produs.getValue())
            {
                stocCurent = stocCurent + tranzactie.getCantitate() * tranzactie.getTip().semn();
                if(tranzactie.getData().isAfter(date)) {
                    date = tranzactie.getData();
                }
            }
                System.out.println(produs.getKey() + "\n\tStoc: " + stocCurent + "\n\tUltima tranzactie: " + date);
            }
            else {
                System.out.println("Nu exista tranzactii pentru produsul cu codul: " + produs.getKey().getCod());
            }
        }
    }

    public static void main(String[] args) {
        stocuri = new HashMap<>();
        AdaugaProdus(1, "A");
        AdaugaProdus(0, "C");
        AdaugaProdus(2, "B");

        AdaugaTranzactie(TipTranzactie.INTRARE, LocalDate.of(2020,1,3), 1, 10);
        AdaugaTranzactie(TipTranzactie.INTRARE, LocalDate.of(2020,1,6), 2, 10);
        AdaugaTranzactie(TipTranzactie.IESIRE, LocalDate.of(2020,2,6), 1, 7);
        AdaugaTranzactie(TipTranzactie.INTRARE, LocalDate.of(2019,2,6), 1, 7);

        AfisareStocuri();
    }
}
