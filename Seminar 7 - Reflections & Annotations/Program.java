import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Modifier;
import java.util.Scanner;

@Retention(RetentionPolicy.RUNTIME)
@interface IgnoraCamp {}

@Retention(RetentionPolicy.RUNTIME)
@interface Mesaj { String value(); }

@Retention(RetentionPolicy.RUNTIME)
@interface Dimensiune { int minim(); int maxim(); }


public class Program {

    static Object citireObiect(Class<?> clasa) throws Exception {
        var constructor = clasa.getConstructor();
        var obiect = constructor.newInstance();
        var scanner = new Scanner(System.in);

        for (var camp: clasa.getDeclaredFields()) {
            if (Modifier.isStatic(camp.getModifiers()) || camp.isAnnotationPresent(IgnoraCamp.class)) continue;
            String mesaj = camp.isAnnotationPresent(Mesaj.class) ? camp.getAnnotationsByType(Mesaj.class)[0].value() : "Introduceti valoarea pentru `" +
                    camp.getName() + "` (" +
                    camp.getType() + "):";
            System.out.println(mesaj);

            var valoareString = scanner.nextLine();
            Object valoare = null;

            if(camp.getType() == String.class) {
                valoare = valoareString;
                if(camp.isAnnotationPresent(Dimensiune.class)) {
                    var dim = camp.getAnnotationsByType(Dimensiune.class)[0];
                    if(valoareString.length() < dim.minim() || valoareString.length() > dim.maxim()) {
                        throw new Exception("Dimensiune invalida pentru campul: " + camp.getName());
                    }
                }
            }
            else if(camp.getType() == int.class) {
                valoare = Integer.parseInt(valoareString);
            }
            else if(camp.getType() == double.class) {
                valoare = Double.parseDouble(valoareString);
            }
            else {
                throw new Exception("Tipul nu este valid.");
            }

            var denumireSet = "set" + camp.getName().substring(0, 1).toUpperCase() + camp.getName().substring(1);
            System.out.println(denumireSet);

            var metodaSet = clasa.getDeclaredMethod(denumireSet, camp.getType());
            System.out.println(metodaSet);
            metodaSet.invoke(obiect, valoare);
        }

        return obiect;
    }

    static void afisareObiect(Object obiect) throws Exception
    {
        var clasa = obiect.getClass();
        System.out.println(clasa.getName() + ":");

        for (var camp : clasa.getDeclaredFields()) {
            System.out.println("     " +
                    camp.getName() +
                    "(" + camp.getType().getName() + ") = "
                    + camp.get(obiect));
        }
    }

    public static void main(String[] args) throws Exception {
        var produs = citireObiect(Produs.class);
        // System.out.println(produs);
        afisareObiect(produs);
    }
}

class Produs {
    @Dimensiune(minim=3, maxim=20)
    String denumire;
    @IgnoraCamp
    int cantitate;
    @Mesaj("Pretul produsului este: ")
    int pret;

    public Produs() {
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    public int getPret() {
        return pret;
    }

    public void setPret(int pret) {
        this.pret = pret;
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