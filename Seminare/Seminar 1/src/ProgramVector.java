public class ProgramVector {
    public static void main(String[] args) {
        if(args.length > 0) {
            System.out.println("Parametru: " + args[0]);
        }
        else {
            System.err.println("Fara parametri.");
            return;
        }

        String[] elemente = args[0].split(",");
        int[] vector = new int[elemente.length];

        for(int i=0; i< elemente.length; i++)
        {
            vector[i] = Integer.parseInt(elemente[i]);
        }

        for(var element : elemente)
        {
            System.out.println(element);
        }

        incrementare(vector);
        afisare("Acesta este vectorul: ", vector);
    }

    static void afisare(String mesaj, int[] vector)
    {
        System.out.print(mesaj + ":");
        for (var element : vector) {
            System.out.print(" " + element);
        }
        System.out.println();
    }

    static void incrementare(int[] vector) {
        for(int i=0; i< vector.length; i++)
        {
            vector[i]++;
        }
    }

    static int[] inserare(int[] vector, int valoare) {
        int[] temp = new int[vector.length + 1];
        temp[0] = valoare;
        for(int i=0; i< vector.length; i++)
        {
            temp[i+1] = vector[i];
        }
        return temp;
    }

    static int[] eliminareImpare(int[] vector) {
        int contorPare = 0;
        for(int element : vector)
        {
            if(element%2==0) contorPare++;
        }
        int[] temp = new int[contorPare];
        for(int i=0; i< vector.length; i++)
        {
            if(vector[i]%2==0) temp[i] = vector[i];
        }
        return temp;
    }
    }

