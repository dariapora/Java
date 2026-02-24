import java.util.Arrays;

class Nota {
    String numeDisciplina;
    int nota;

    public Nota(String numeDisciplina, int nota) {
        this.numeDisciplina = numeDisciplina;
        this.nota = nota;
    }

    public String getNumeDisciplina()
    {
        return numeDisciplina;
    }

    public void setNota(int nota) {
        this.nota=nota;
    }

    public int getNota()
    {
        return nota;
    }

    public Nota()
    {
        this("-", 1);
    }

    @Override
    public String toString()
    {
        return String.format("%-18s %2d%n", numeDisciplina, nota);
    }
}

class Student {
    private int idStudent;
    private String nume;
    private int grupa;
    private int anul;
    private Nota[] note;

    public Student() {
        this(0, "-", 0, 0);
    }

    public Student(int idStudent, String nume, int grupa, int anul) {
        this.idStudent = idStudent;
        this.nume = nume;
        this.grupa = grupa;
        this.anul = anul;
        this.note = new Nota[0];
    }

    public int getAnul() {
        return anul;
    }

    public void setAnul(int anul) {
        this.anul = anul;
    }

    public int getGrupa() {
        return grupa;
    }

    public void setGrupa(int grupa) {
        this.grupa = grupa;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(int idStudent) {
        this.idStudent = idStudent;
    }

    @Override
    public String toString() {
        var builder = new StringBuilder();
        builder.append(String.format("#2d - %-15s gr %d, an %d", getIdStudent(), getNume(), getGrupa(), getAnul()));
        for (var nota : note) {
            builder.append("    " + nota.toString() + "\n");
        }
        return builder.toString();
    }
    public Nota[] getNote()
    {
        return note;
    }

    public void add(Nota nota)
    {
        for (var notaExistenta : getNote()) {
            if(notaExistenta.getNumeDisciplina().equals(nota.getNumeDisciplina()))
            {
                notaExistenta.setNota(nota.getNota());
                return;
            }
        }
        note = Arrays.copyOf(note, note.length + 1);
        note[note.length + 1] = nota;
    }
}

public class Program {
    public static void main(String[] args)
    {
        System.out.println(new Nota());
        var nota = new Nota("Java", 10);
        var ion = new Student(13, "Ion", 1234, 2);
        ion.add(nota);
        ion.add(new Nota("PAW", 4));
        ion.add(new Nota("SDD", 6));
        ion.add(new Nota("PAW", 8));
        System.out.println(ion);
    }
}