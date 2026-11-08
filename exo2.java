public abstract class Colis implements Comparable<Colis> {
    private final String code; private final int poids; private final boolean horsDakar;
    public Colis(String code, int poids, boolean horsDakar) {
        if (poids <= 0) throw new IllegalArgumentException("poids <= 0");
        this.code = code; this.poids = poids; this.horsDakar = horsDakar;
    }
    public String getCode() { return code; }
    public int getPoids() { return poids; }
    public boolean isHorsDakar() { return horsDakar; }
    public long tarif() {
        return (long) poids * Parametres.TARIF_KG
             + (horsDakar ? Parametres.SURCHARGE_HORS_DAKAR : 0);
    }
    public abstract String sigle();
    public abstract int priorite();
    @Override public String toString() {
        return "[" + sigle() + "] " + code + " " + poids + "kg "
             + (horsDakar ? "hors-Dakar" : "Dakar") + " " + tarif() + " FCFA";
    }
    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return code.equals(((Colis) o).code);
    }
    @Override public int hashCode() { return code.hashCode(); }
    @Override public int compareTo(Colis o) {
        int c = Integer.compare(o.priorite(), priorite());
        if (c != 0) return c;
        c = Long.compare(o.tarif(), tarif());
        if (c != 0) return c;
        return code.compareTo(o.code);
    }
}

public class ColisStandard extends Colis {
    public ColisStandard(String c, int p, boolean h) { super(c, p, h); }
    @Override public String sigle() { return "STD"; }
    @Override public int priorite() { return 1; }
}

public class ColisExpress extends Colis {
    public ColisExpress(String c, int p, boolean h) { super(c, p, h); }
    @Override public long tarif() { return super.tarif() * 3 / 2; }
    @Override public String sigle() { return "EXP"; }
    @Override public int priorite() { return 3; }
}

public class ColisFragile extends ColisStandard {
    private final long valeurDeclaree;
    public ColisFragile(String c, int p, boolean h, long v) { super(c, p, h); valeurDeclaree = v; }
    public long getValeurDeclaree() { return valeurDeclaree; }
    @Override public long tarif() { return super.tarif() + valeurDeclaree / 50; }
    @Override public String sigle() { return "FRG"; }
    @Override public int priorite() { return 2; }
    @Override public String toString() { return super.toString() + " (valeur " + valeurDeclaree + ")"; }
}

public class Inventaire {
    public static long valeurAssuree(Object[] objets) {
        long s = 0;
        for (Object o : objets)
            if (o instanceof ColisFragile) s += ((ColisFragile) o).getValeurDeclaree();
        return s;
    }
    public static int[] compterParType(Object[] objets) {
        int[] r = new int[4];
        for (Object o : objets) {
            if (o == null) continue;
            if (o instanceof ColisFragile) r[2]++;       // avant ColisStandard !
            else if (o instanceof ColisStandard) r[0]++;
            else if (o instanceof ColisExpress) r[1]++;
            else r[3]++;
        }
        return r;
    }
}