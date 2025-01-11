package ma.khairy.dij;

import java.util.*;
import java.util.stream.Collectors;

public class Dijkstra {
    private final Map<Sommet, Integer> distances;
    private final Map<Sommet, Sommet> predecesseurs;
    private final Set<Sommet> sommetsNonVisites;
    private final Set<Sommet> sommetsVisites;
    private final Graphe graphe;

    public Dijkstra(Graphe graphe) {
        this.distances = new HashMap<>();
        this.predecesseurs = new HashMap<>();
        this.sommetsNonVisites = new HashSet<>();
        this.sommetsVisites = new HashSet<>();
        this.graphe = graphe;
    }

    public Map<String, Integer> calculerCheminMinimal(String sommetDepartNom, boolean details) {
        Sommet sommetDepart = graphe.getSommet(sommetDepartNom);
        distances.put(sommetDepart, 0);
        sommetsNonVisites.addAll(graphe.getSommets());

        // Affichage initial
        if (details) {
            afficherEtatInitial();
        }

        int iteration = 0;
        while (!sommetsNonVisites.isEmpty()) {
            Sommet sommetCourant = obtenirSommetDistanceMinimale();
            if (sommetCourant == null) break; // Aucun chemin restant
            sommetsNonVisites.remove(sommetCourant);
            sommetsVisites.add(sommetCourant);

            // Afficher les détails après chaque itération
            if (details) {
                afficherEtatIteration(sommetCourant, iteration++);
            }

            for (Arrete arrete : sommetCourant.getArretes()) {
                Sommet voisin = arrete.getDestination();
                if (sommetsNonVisites.contains(voisin)) {
                    int nouvelleDistance = distances.getOrDefault(sommetCourant, Integer.MAX_VALUE) + arrete.getPoids();
                    if (nouvelleDistance < distances.getOrDefault(voisin, Integer.MAX_VALUE)) {
                        distances.put(voisin, nouvelleDistance);
                        predecesseurs.put(voisin, sommetCourant);
                    }
                }
            }
        }

        // Convertir les résultats pour retourner des noms de sommets avec leurs distances
        Map<String, Integer> result = new HashMap<>();
        for (Sommet sommet : distances.keySet()) {
            result.put(sommet.getNom(), distances.get(sommet));
        }
        return result;
    }

    private Sommet obtenirSommetDistanceMinimale() {
        Sommet sommetMin = null;
        int distanceMin = Integer.MAX_VALUE;

        for (Sommet sommet : sommetsNonVisites) {
            int distance = distances.getOrDefault(sommet, Integer.MAX_VALUE);
            if (distance < distanceMin) {
                distanceMin = distance;
                sommetMin = sommet;
            }
        }

        return sommetMin;
    }

    private void afficherEtatInitial() {
        // Afficher l'initialisation
        Set<Sommet> sommetsVisitesInitiaux = new HashSet<>();
        Set<Sommet> sommetsNonVisitesInitiaux = new HashSet<>(graphe.getSommets());

        // Ajouter le sommet de départ à S
        Sommet sommetDepart = graphe.getSommets().stream()
                .filter(s -> distances.getOrDefault(s, Integer.MAX_VALUE) == 0)
                .findFirst()
                .orElse(null);

        if (sommetDepart != null) {
            sommetsVisitesInitiaux.add(sommetDepart);
            sommetsNonVisitesInitiaux.remove(sommetDepart);
        }

        String S = "{" + (sommetDepart != null ? sommetDepart.getNom() : "") + "}";
        String S_ = sommetsNonVisitesInitiaux.stream()
                .map(Sommet::getNom)
                .collect(Collectors.joining(", ", "[", "]"));

        String pi = calculerVecteurPi(); // Utiliser la méthode calculerVecteurPi pour afficher les distances

        System.out.println("Initialisation");
        System.out.println("S=" + S + " ; 𝑆−=" + S_ + " ; π=" + pi);
    }

    private void afficherEtatIteration(Sommet sommetCourant, int iteration) {
        // Affichage de l'étape i, des successeurs et de la mise à jour des distances
        System.out.println(iteration + "ère Itération :");
        String pi = calculerVecteurPi();
        System.out.println("Les successeurs de " + sommetCourant.getNom() + " dans 𝑆−");

        for (Arrete arrete : sommetCourant.getArretes()) {
            Sommet voisin = arrete.getDestination();
            int nouvelleDistance = distances.getOrDefault(sommetCourant, Integer.MAX_VALUE) + arrete.getPoids();
            if (nouvelleDistance < distances.getOrDefault(voisin, Integer.MAX_VALUE)) {
                System.out.println("π(" + voisin.getNom() + ")=min(" +
                        (distances.containsKey(voisin) ?
                                (distances.get(voisin) == Integer.MAX_VALUE ? "∞" : distances.get(voisin)) : "∞") +
                        "," + distances.get(sommetCourant) + "+" + arrete.getPoids() + ")=" + nouvelleDistance);
                distances.put(voisin, nouvelleDistance);
                pi = calculerVecteurPi();
            }
        }

        System.out.println("Le nouveau vecteur π=" + pi);
        System.out.println("-----------------------------------------------------");
    }

    private String calculerVecteurPi() {
        // Calculer le vecteur π sous la forme (nom_sommet:valeur)
        StringBuilder pi = new StringBuilder("(");
        for (Sommet sommet : graphe.getSommets()) {
            int distance = distances.getOrDefault(sommet, Integer.MAX_VALUE);
            pi.append(sommet.getNom()).append(":"); // Ajouter le nom du sommet et ":"
            if (distance == Integer.MAX_VALUE) {
                pi.append("∞"); // Utiliser le symbole infini
            } else {
                pi.append(distance); // Ajouter la distance
            }
            pi.append(",");
        }
        // Supprimer la dernière virgule et ajouter ")"
        if (pi.length() > 1) {
            pi.setLength(pi.length() - 1);
        }
        pi.append(")");
        return pi.toString();
    }
}