// Clasa Produs să fie transformată în clasă imutabilă.
// Clasa va avea doar un constructor cu trei parametri (denumire, cantitate, pret).
package Tema;
import java.io.*;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Scanner;

@Retention(RetentionPolicy.RUNTIME)
@interface Mesaj { String value(); }

final class Produs {
    private final String denumire;
    private final int cantitate;
    @Mesaj("Pretul produsului este: ")
    private final double pret;

    public Produs(String denumire, int cantitate, double pret) {
        this.denumire = denumire;
        this.cantitate = cantitate;
        this.pret = pret;
    }

    public String getDenumire() {
        return denumire;
    }

    public int getCantitate() {
        return cantitate;
    }

    public double getPret() {
        return pret;
    }

    @Override
    public String toString() {
        return "Produs{" +
                "denumire='" + denumire + '\'' +
                ", cantitate=" + cantitate +
                ", pret=" + pret +
                '}';
    }
}

class Melodie {
    String titlu;
    String artist;
    float durata;

    public Melodie() {
    }

    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public float getDurata() {
        return durata;
    }

    public void setDurata(float durata) {
        this.durata = durata;
    }

    @Override
    public String toString() {
        return "Melodie{" +
                "titlu='" + titlu + '\'' +
                ", artist='" + artist + '\'' +
                ", durata=" + durata +
                '}';
    }
}

public class Tema {

    static void Serializare(PrintWriter fisier, Object obiect) throws Exception
    {
        var clasa = obiect.getClass();
        fisier.println(clasa.getSimpleName());
        var campuri = clasa.getDeclaredFields();
        fisier.println(campuri.length);
        for (var camp : campuri)
        {
            if(camp.getType()==Object.class)
                Serializare(fisier, camp);
            else {
                fisier.println(camp.getName());
                var denumireGet = "get" + camp.getName().substring(0, 1).toUpperCase() + camp.getName().substring(1);
                var metodaGet = clasa.getDeclaredMethod(denumireGet);
                fisier.println(metodaGet.invoke(obiect));
            }
        }
    }

    static void Serializare(String caleFisier, Object obiect) throws Exception
    {
        PrintWriter printWriter = new PrintWriter(new FileWriter(caleFisier));
        Serializare(printWriter, obiect);
        System.out.println("Serializare efectuata cu succes.");
        printWriter.close();
    }

    static Object Deserializare(Scanner scanner) throws Exception
    {
        var numeClasa = "Tema." + scanner.nextLine();
        Class<?> clasaTransformata = Class.forName(numeClasa);
        var constructor = clasaTransformata.getConstructor();
        Object object = constructor.newInstance();
        int numarCampuri = Integer.parseInt(scanner.nextLine());
        for(int i=0; i<numarCampuri; i++)
        {
            Field camp = clasaTransformata.getDeclaredField(scanner.nextLine());
            if(camp.getType()==Object.class) Deserializare(scanner);
            else {
                var denumireSet = "set" + camp.getName().substring(0, 1).toUpperCase() + camp.getName().substring(1);
                var metodaSet = clasaTransformata.getDeclaredMethod(denumireSet, camp.getType());
                String input = scanner.nextLine();
                Object valoare = null;
                if (camp.getType() == float.class) {
                    valoare = Float.parseFloat(input);
                } else valoare = input;
                metodaSet.invoke(object, valoare);
            }
        }
        return object;
    }

    static Object Deserializare(String caleFisier) throws Exception
    {
        Scanner scanner = new Scanner(new FileReader(caleFisier));
        Object object = Deserializare(scanner);
        System.out.println("Deserializare efectuata cu succes.");
        return object;
    }

    static Object citireObiectTema1(Class<?> clasa) throws Exception
    {
        var campuri = clasa.getDeclaredFields();
        var scanner = new Scanner(System.in);
        Class<?>[] tipuriClasa = new Class<?>[campuri.length];
        Object[] valoriClasa = new Object[campuri.length];

        for(int i=0; i< campuri.length; i++){
            tipuriClasa[i] = campuri[i].getType();
            if (Modifier.isStatic(campuri[i].getModifiers())) continue;
            String mesaj = clasa.isAnnotationPresent(Mesaj.class) ? clasa.getAnnotation(Mesaj.class).value() : "Introduceti valoarea pentru campul '" +
                    campuri[i].getName() + "':";
            System.out.println(mesaj);
            String input = scanner.nextLine();
            if(campuri[i].getType() == int.class)
            {
                valoriClasa[i] = Integer.parseInt(input);
            }
            else if (campuri[i].getType() == double.class)
            {
                valoriClasa[i] = Double.parseDouble(input);
            }
            else valoriClasa[i] = input;
        }
        var constructor = clasa.getConstructor(tipuriClasa);
        var object = constructor.newInstance(valoriClasa);
        return object;
    }

    static Object citireObiectTema2(Class<?> clasa) throws Exception
    {
        var campuri = clasa.getDeclaredFields();
        var scanner = new Scanner(System.in);
        var constructor = clasa.getConstructor();
        var object = constructor.newInstance();
        for(int i=0; i< campuri.length; i++){
            if (Modifier.isStatic(campuri[i].getModifiers())) continue;
            String mesaj = clasa.isAnnotationPresent(Mesaj.class) ? clasa.getAnnotation(Mesaj.class).value() : "Introduceti valoarea pentru campul '" +
                    campuri[i].getName() + "':";
            System.out.println(mesaj);
            String input = scanner.nextLine();
            var denumireSet = "set" + campuri[i].getName().substring(0, 1).toUpperCase() + campuri[i].getName().substring(1);
            Object valoare = null;
            if(campuri[i].getType() == float.class)
            {
                valoare = Float.parseFloat(input);
            }
            else valoare = input;
            var metodaSet = clasa.getDeclaredMethod(denumireSet, campuri[i].getType());
            metodaSet.invoke(object, valoare);
        }
        return object;
    }

    public static void main(String args[]) throws Exception
    {
        Object melodie = citireObiectTema2(Melodie.class);
        Serializare("./fisier.txt", melodie);
        Object melodieDeserializata = Deserializare("./fisier.txt");
        System.out.println(melodieDeserializata);
    }
}
