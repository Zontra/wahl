import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

class Kandidat {

    private final String name;
    private int punkte;
    private int platz1;
    private int id;

    public Kandidat(String name, int id) {
        this.name = name;
        this.id = id;
    }


    public void addPoints(int p) {
        this.punkte += p;
        if (p == 2)
            this.platz1++;
        if (p == -2)
            this.platz1--;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String toString() {
        DecimalFormat dc = new DecimalFormat("###0");
        return dc.format(punkte) + " / " + dc.format(platz1) + "   " + this.name;
    }

    public char firstChar() {
        return this.name.toLowerCase().charAt(0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Kandidat kandidat = (Kandidat) o;
        return punkte == kandidat.punkte && platz1 == kandidat.platz1 && Objects.equals(name, kandidat.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, punkte, platz1);
    }
}

public class Wahl {
    static List<Kandidat> k = new ArrayList<>();
    static List<String> names = new ArrayList<>();

    static {
        names.add("Dominik Hofmann");
        names.add("Kilian Prager");
        names.add("Niklas Hochstöger");
        names.add("Paul Pfiel");
        names.add("Raid Alarkhanov");

        IntStream.range(0, names.size()).forEach(i -> k.add(new Kandidat(names.get(i), i + 1)));
    }
    static Kandidat oldKandidat1 = null;
    static Kandidat oldKandidat2 = null;


    public static void main(String[] args) {
        checkKandidateNumber();
        islog(args);
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int i = 1;
        DecimalFormat dc = new DecimalFormat("000");

        try {
            PrintWriter out = new PrintWriter(new FileWriter(args[0]));
            printformat(dc, out, i);
            showlist(in, out, i, dc);

            out.close();
        } catch (IOException ioe) {
            System.err.println("Error: " + ioe.getMessage());
        }
    }

    private static void checkKandidateNumber() {
        if (k.size() < 2) {
            System.out.println("Es muessen mindestens 2 Kandidaten vorhanden sein!");
            System.exit(1);
        } else if (k.size() > 9) {
            System.out.println("Es duerfen maximal 9 Kandidaten vorhanden sein!");
            System.exit(1);
        }
    }



    static void showlist(BufferedReader in, PrintWriter out, int i, DecimalFormat dc) throws IOException {
        String ein;
        while ((ein = in.readLine()) != null && !ein.equals("quit")) {
            if(testifeingabeistreturn(out, i, dc, ein)) {
                continue;
            }
            int ok = 0;
            int ul = 0;
            out.println(ein);
            if (isValid(ein)) {
                ul += addifchardash(ein);
                ok += addifchardash(ein);
                if (ul > 1) ok = 0;
                ok += addifcharK(ein);
            }
            i = testifeingabeKorrekt(out, i, dc, ok);
        }
    }

    static Boolean testifeingabeistreturn(PrintWriter out, int i, DecimalFormat dc, String ein) {
        if (ein.equals("return")) {
            if (oldKandidat1 != null) {
                oldKandidat1.addPoints(-2);
                oldKandidat1 = null;
            }
            if (oldKandidat2 != null) {
                oldKandidat2.addPoints(-1);
                oldKandidat2 = null;
            }
            testifeingabeKorrekt(out, i, dc, 2);
            return true;
        }
        return false;
    }

    static int testifeingabeKorrekt(PrintWriter out, int i, DecimalFormat dc, int ok) {
        if (ok != 2) {
            System.out.println("     Falsche Eingabe!");
            out.println("     Falsche Eingabe!");
        } else {
            i++;
            for (Kandidat kandidat : k) {
                System.out.println(kandidat.getId() + ".    " + kandidat);
                out.println(kandidat.getId() + ".     " + kandidat);
            }
        }
        System.out.println("-----------------------------------------------------------");
        out.println("-----------------------------------------------------------");
        out.flush();
        printformat(dc, out, i);
        out.close();
        return i;
    }

    static int addifcharK(String ein) {
        int ok = 0;
        for (Kandidat kandidat : k) {
            if (ein.charAt(0) == kandidat.firstChar()) {
                kandidat.addPoints(2);
                oldKandidat1 = kandidat;
                ok += 1;
            }
            if (ein.charAt(1) == kandidat.firstChar()) {
                kandidat.addPoints(1);
                oldKandidat2 = kandidat;
                ok += 1;
            }
        }
        return ok;
    }

    static int addifchardash(String ein) {
        int ulOk = 0;
        if (ein.charAt(0) == '-') {
            ulOk++;
        }
        if (ein.charAt(1) == '-') {
            ulOk++;
        }
        return ulOk;
    }

    static void printformat(DecimalFormat dc, PrintWriter out, int i) {
        System.out.print(dc.format(i) + " >");
        out.print(dc.format(i) + " >");
        out.close();
    }

    static void islog(String[] args) {
        	if (args.length != 1) {
                System.out.println("Keine Logdatei");
                System.out.println("Usage: java Wahl pathToLog");
                System.exit(1);
            }
    }

    static boolean isValid(String s) {
        if (s.length() != 2) return false;
        char ch1 = s.charAt(0);
        char ch2 = s.charAt(1);
        int test1 = 0, test2 = 0;
        if (ch1 == ch2) return false;
        return checkcharcount(ch1, ch2, test1, test2);
    }

    static boolean checkcharcount(char ch1, char ch2, int test1, int test2) {
        test1 = berechnecharcount(ch1, test1);
        test2 = berechnecharcount(ch2, test2);
        return test1 == 1 && test2 == 1;
    }

    static int ifchardash(char ch, int test) {
        if (ch == '-') test++;
        return test;
    }

    static int berechnecharcount(char ch, int test) {
        for (Kandidat kandidat : k) {
            if (ch == kandidat.firstChar()) test++;
        }
        test = ifchardash(ch, test);
        return test;
    }
}
