import facturi.*;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProgramFacturi {

    public static List<Factura> generareListaFacturi(LocalDate dataMin, int numarFacturi)
    {
        String[] denumiriClienti = new String[]{

                "ALCOR CONSTRUCT SRL",
                "SC DOMINO COSTI SRL",
                "SC TRANSCRIPT SRL",
                "SIBLANY SRL",
                "INTERFLOOR SYSTEM SRL",
                "MERCURY  IMPEX  2000  SRL",
                "ALEXANDER SRL",
                "METAL INOX IMPORT EXPOSRT SRL",
                "EURIAL BROKER DE ASIGURARE SRL"
        };


        String[] denumiriProduse = new String[]{
                "Stafide 200g",
                "Seminte de pin 300g",
                "Bulion Topoloveana 190g",
                "Paine neagra Frontera",
                "Ceai verde Lipton"

        };

        double[] preturiProduse = new double[]{
                5.20,
                12.99,
                6.29,
                4.08,
                8.99
        };

        List<Factura> listaFacturi = new ArrayList<>(0);
        Random r = new Random();

        while(numarFacturi > 0) {
            int indexClient = r.nextInt(denumiriClienti.length);
            int numarMaximZile = (int)ChronoUnit.DAYS.between(dataMin, LocalDate.now());
            int numarLinii = r.nextInt(1,11);
            LocalDate data = numarMaximZile > 0 ? dataMin.plusDays(r.nextInt(numarMaximZile)) : LocalDate.now();
            Factura factura = new Factura(denumiriClienti[indexClient], data);
            while(numarLinii > 0)
            {
                int indexProdus = r.nextInt(denumiriProduse.length);
                int cantitate = r.nextInt(1, 101);
                factura.adaugaLinie(denumiriProduse[indexProdus], preturiProduse[indexProdus], cantitate);
                numarLinii--;
            }
            listaFacturi.add(factura);
            numarFacturi--;
        }
        return listaFacturi;
    }

    //Să se definească o funcție salvareFacturi pentru salvarea unei liste de facturi într-un fișier binar.
    //Fișierul va fi compus din înregistrări de forma:

    //denumire client - string
    //an / luna / zi - întregi
    //număr linii - întreg
            //(produs - string, preț - double, cantitate - întreg) x număr linii
    

    public static void main(String[] args)
    {

    }
}
