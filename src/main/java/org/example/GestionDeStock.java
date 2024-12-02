package org.example;

import java.util.Scanner;

public class GestionDeStock {
    private static final int MAX_PRODUITS = 100;
    private static int[] codesProduits = new int[MAX_PRODUITS];
    private static String[] nomsProduits = new String[MAX_PRODUITS];
    private static int[] quantites = new int[MAX_PRODUITS];
    private static double[] prix = new double[MAX_PRODUITS];
    private static int nombreProduits = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choix;

        do {
            printMenu(); // Affiche le menu principal
            choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    ajouterProduit(scanner); // Ajoute un nouveau produit
                    break;
                case 2:
                    modifierProduit(scanner); // Modifie un produit existant
                    break;
                case 3:
                    supprimerProduit(scanner); // Supprime un produit
                    break;
                case 4:
                    afficherProduits(); // Affiche la liste des produits
                    break;
                case 5:
                    rechercherProduit(scanner); // Recherche un produit par son nom
                    break;
                case 6:
                    calculerValeurStock(); // Calcule la valeur totale du stock
                    break;
                case 0:
                    System.out.println("Merci d'avoir utilisé l'application de gestion de stock.");
                    break;
                default:
                    System.out.println("Option invalide. Veuillez réessayer.");
            }
        } while (choix != 0);

        scanner.close();
    }

    // Affiche le menu principal
    private static void printMenu() {
        System.out.println("---- Gestion de Stock ----");
        System.out.println("1. Ajouter un produit");
        System.out.println("2. Modifier un produit");
        System.out.println("3. Supprimer un produit");
        System.out.println("4. Afficher la liste des produits");
        System.out.println("5. Rechercher un produit");
        System.out.println("6. Calculer la valeur totale du stock");
        System.out.println("0. Quitter");
        System.out.print("Choisissez une option : ");
    }

    // Ajoute un nouveau produit
    private static void ajouterProduit(Scanner scanner) {
        if (nombreProduits >= MAX_PRODUITS) {
            System.out.println("Le stock est plein. Impossible d'ajouter plus de produits.");
            return;
        }

        System.out.print("Entrez le code du produit : ");
        int code = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Entrez le nom du produit : ");
        String nom = scanner.nextLine();

        System.out.print("Entrez la quantité : ");
        int quantite = scanner.nextInt();

        System.out.print("Entrez le prix unitaire : ");
        double prixUnitaire = scanner.nextDouble();

        int index = trouverIndexVide(); // Trouve une case vide dans les tableaux
        if (index != -1) {
            // Ajoute le produit dans la case vide
            codesProduits[index] = code;
            nomsProduits[index] = nom;
            quantites[index] = quantite;
            prix[index] = prixUnitaire;
        } else {
            // Ajoute le produit à la fin des tableaux
            codesProduits[nombreProduits] = code;
            nomsProduits[nombreProduits] = nom;
            quantites[nombreProduits] = quantite;
            prix[nombreProduits] = prixUnitaire;
            nombreProduits++;
        }

        System.out.println("Produit ajouté avec succès.");
    }

    // Modifie un produit existant
    private static void modifierProduit(Scanner scanner) {
        System.out.print("Entrez le code du produit à modifier : ");
        int code = scanner.nextInt();
        scanner.nextLine();

        int index = trouverIndexParCode(code); // Trouve l'index du produit par son code
        if (index == -1) {
            System.out.println("Produit non trouvé.");
            return;
        }

        System.out.print("Entrez le nouveau nom du produit : ");
        String nouveauNom = scanner.nextLine();

        System.out.print("Entrez la nouvelle quantité : ");
        int nouvelleQuantite = scanner.nextInt();

        System.out.print("Entrez le nouveau prix unitaire : ");
        double nouveauPrix = scanner.nextDouble();

        // Met à jour les informations du produit
        nomsProduits[index] = nouveauNom;
        quantites[index] = nouvelleQuantite;
        prix[index] = nouveauPrix;

        System.out.println("Produit modifié avec succès.");
    }

    // Supprime un produit
    private static void supprimerProduit(Scanner scanner) {
        System.out.print("Entrez le code du produit à supprimer : ");
        int code = scanner.nextInt();
        scanner.nextLine();

        int index = trouverIndexParCode(code); // Trouve l'index du produit par son code
        if (index == -1) {
            System.out.println("Produit non trouvé.");
            return;
        }

        // Remplit les cases du produit supprimé avec des valeurs nulles ou zéro
        codesProduits[index] = 0;
        nomsProduits[index] = null;
        quantites[index] = 0;
        prix[index] = 0.0;

        System.out.println("Produit supprimé avec succès.");
    }

    // Affiche la liste des produits
    private static void afficherProduits() {
        boolean produitTrouve = false;
        System.out.println("Liste des produits en stock :");
        for (int i = 0; i < MAX_PRODUITS; i++) {
            if (codesProduits[i] != 0) {
                System.out.println("Code : " + codesProduits[i] +
                        ", Nom : " + nomsProduits[i] +
                        ", Quantité : " + quantites[i] +
                        ", Prix unitaire : " + prix[i]);
                produitTrouve = true;
            }
        }
        if (!produitTrouve) {
            System.out.println("Aucun produit en stock.");
        }
    }

    // Recherche un produit par son nom
    private static void rechercherProduit(Scanner scanner) {
        System.out.print("Entrez le nom du produit à rechercher : ");
        String nom = scanner.nextLine();

        boolean trouvé = false;
        for (int i = 0; i < MAX_PRODUITS; i++) {
            if (nomsProduits[i] != null && nomsProduits[i].equalsIgnoreCase(nom)) {
                System.out.println("Produit trouvé :");
                System.out.println("Code : " + codesProduits[i] +
                        ", Nom : " + nomsProduits[i] +
                        ", Quantité : " + quantites[i] +
                        ", Prix unitaire : " + prix[i]);
                trouvé = true;
                break;
            }
        }

        if (!trouvé) {
            System.out.println("Produit non trouvé.");
        }
    }

    // Calcule la valeur totale du stock
    private static void calculerValeurStock() {
        double valeurTotale = 0;
        for (int i = 0; i < MAX_PRODUITS; i++) {
            if (codesProduits[i] != 0) {
                valeurTotale += quantites[i] * prix[i];
            }
        }
        System.out.println("La valeur totale du stock est : " + valeurTotale);
    }

    // Trouve l'index d'un produit par son code
    private static int trouverIndexParCode(int code) {
        for (int i = 0; i < MAX_PRODUITS; i++) {
            if (codesProduits[i] == code) {
                return i;
            }
        }
        return -1;
    }

    // Trouve une case vide dans les tableaux
    private static int trouverIndexVide() {
        for (int i = 0; i < MAX_PRODUITS; i++) {
            if (codesProduits[i] == 0) {
                return i;
            }
        }
        return -1;
    }
}