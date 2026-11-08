class Vehicule {
    protected String nom = "vehicule";
    static int compteur = 0;

    Vehicule() {
        compteur++;
        System.out.println("A " + nom);
        demarrer();
    }

    void demarrer() { System.out.println("B " + nom); }
    String type() { return "Vehicule"; }
    String comparer(Vehicule v) { return "C1"; }
    String comparer(Moto m) { return "C2"; }
    static String categorie() { return "D-vehicule"; }
}

class Moto extends Vehicule {
    protected String nom = "moto";
    private int cylindree = 100 + Parametres.N;

    Moto() {
        super();
        System.out.println("E " + nom + " " + cylindree);
    }

    @Override void demarrer() { System.out.println("F " + nom + " " + cylindree); }
    @Override String type() { return "Moto" + cylindree / 50; }
    @Override String comparer(Vehicule v) { return "C3"; }
    static String categorie() { return "D-moto"; }
}

class Camion extends Vehicule { }

public class Ex1 {
    public static void main(String[] args) {
        Vehicule v = new Moto();
        System.out.println(v.type());
        System.out.println(v.nom);
        System.out.println(((Moto) v).nom);
        System.out.println(v.comparer(v));
        System.out.println(v.comparer((Moto) v));
        System.out.println(v.categorie());
        Object o = v;
        System.out.println(o.equals(v) + " " + (o == new Moto()));
        System.out.println(o.toString().startsWith("Moto@"));
        for (int i = 0; i < Parametres.N % 3; i++) {
            new Vehicule();
        }
        System.out.println(Vehicule.compteur);
    }
}