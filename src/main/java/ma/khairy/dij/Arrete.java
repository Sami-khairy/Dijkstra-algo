package ma.khairy.dij;

class Arrete {
    private final Sommet source;
    private final Sommet destination;
    private final int poids;

    public Arrete(Sommet source, Sommet destination, int poids) {
        this.source = source;
        this.destination = destination;
        this.poids = poids;
    }

    public Sommet getSource() {
        return source;
    }

    public Sommet getDestination() {
        return destination;
    }

    public int getPoids() {
        return poids;
    }

    @Override
    public String toString() {
        return "(" + source.getNom() + " -> " + destination.getNom() + ", poids: " + poids + ")";
    }
}

