package ma.khairy.dij;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Créer un graphe
        Graphe graphe = new Graphe();

        // Saisie des arêtes
        System.out.println("Entrez les arêtes au format 'source-destination:poids' (séparées par des espaces ou des virgules) :");
        String input = scanner.nextLine();

        // Traitement des arêtes
        List<String> arretesInput = Arrays.stream(input.split("[ ,]+")) // Séparer par espaces ou virgules
                .collect(Collectors.toList());

        for (String arreteStr : arretesInput) {
            try {
                String[] parts = arreteStr.split("-|:"); // Séparer par '-' ou ':'
                String source = parts[0].trim(); // Supprimer les espaces
                String destination = parts[1].trim();
                int poids = Integer.parseInt(parts[2].trim());

                // Ajouter l'arête au graphe
                graphe.ajouterArrete(source, destination, poids);
            } catch (Exception e) {
                System.out.println("Format d'arête invalide : " + arreteStr);
            }
        }

        // Saisie du sommet de départ
        System.out.println("Entrez le sommet de départ :");
        String startNode = scanner.nextLine().trim(); // Lire le sommet de départ

        // Vérifier si le sommet de départ existe
        if (graphe.getIndiceSommet(startNode) == -1) {
            System.out.println("Sommet de départ invalide.");
            return;
        }

        // Demander si l'utilisateur souhaite afficher les détails
        System.out.println("Voulez-vous afficher les détails des calculs ? (oui/non)");
        String afficherDetails = scanner.nextLine().toLowerCase();
        boolean details = afficherDetails.equals("oui");

        // Exécuter l'algorithme de Dijkstra
        Dijkstra dijkstra = new Dijkstra(graphe);
        Map<String, Integer> distances = dijkstra.calculerCheminMinimal(startNode, details);

        // Afficher les distances finales
        System.out.println("\nDistances minimales depuis " + startNode + " :");
        for (Map.Entry<String, Integer> entry : distances.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}
