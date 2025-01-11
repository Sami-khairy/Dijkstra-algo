package ma.khairy.dij;

import java.util.ArrayList;
import java.util.List;

class Sommet {
    private final String nom;
    private final List<Arrete> arretes;

    public Sommet(String nom) {
        this.nom = nom;
        this.arretes = new ArrayList<>();
    }

    public String getNom() {
        return nom;
    }

    public List<Arrete> getArretes() {
        return arretes;
    }

    public void ajouterArrete(Arrete arrete) {
        this.arretes.add(arrete);
    }

    @Override
    public String toString() {
        return nom;
    }
}