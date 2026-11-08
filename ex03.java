public class Tournee {
    public static void trierFusion(Colis[] t) {
        if (t.length < 2) return;
        int m = t.length / 2;
        Colis[] g = new Colis[m], d = new Colis[t.length - m];
        for (int i = 0; i < g.length; i++) g[i] = t[i];
        for (int i = 0; i < d.length; i++) d[i] = t[m + i];
        trierFusion(g); trierFusion(d);
        int i = 0, j = 0, k = 0;
        while (i < g.length && j < d.length)
            t[k++] = (g[i].compareTo(d[j]) <= 0) ? g[i++] : d[j++];
        while (i < g.length) t[k++] = g[i++];
        while (j < d.length) t[k++] = d[j++];
    }

    public static Colis[] chargementOptimal(Colis[] t, int capacite) {
        int n = t.length;
        long[][] best = new long[n + 1][capacite + 1];
        for (int i = 1; i <= n; i++) {
            int p = t[i - 1].getPoids(); long v = t[i - 1].tarif();
            for (int cap = 0; cap <= capacite; cap++) {
                best[i][cap] = best[i - 1][cap];
                if (p <= cap)
                    best[i][cap] = Math.max(best[i][cap], best[i - 1][cap - p] + v);
            }
        }
        boolean[] pris = new boolean[n];
        int cap = capacite, nb = 0;
        for (int i = n; i >= 1; i--)
            if (best[i][cap] != best[i - 1][cap]) {
                pris[i - 1] = true; cap -= t[i - 1].getPoids(); nb++;
            }
        Colis[] res = new Colis[nb];
        int k = 0;
        for (int i = 0; i < n; i++) if (pris[i]) res[k++] = t[i];
        return res;
    }

    public static void main(String[] args) {
        Colis[] lot = { /* le tableau de l'énoncé */ };
        trierFusion(lot);
        for (Colis c : lot) System.out.println(c);
        Colis[] choix = chargementOptimal(lot, Parametres.CAPACITE);
        long somme = 0;
        for (Colis c : choix) somme += c.tarif();
        System.out.println("Recette optimale : " + somme + " FCFA (capacite "
                           + Parametres.CAPACITE + " kg)");
    }
}