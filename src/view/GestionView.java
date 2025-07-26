package view;

import java.util.List;
import model.Etat;
import model.Facture;
import utils.Console;

public class GestionView {
	
	public void afficherMenuEtat() {
		Console.afficherMessage("\n-- GÉRER LES ÉTATS --"
				+ "\n1. Ajouter un état"
				+ "\n2. Modifier un état"
				+ "\n3. Supprimer un état"
				+ "\n4. Voir les états"
				+ "\n0. Retour au menu");
    }
	
	
    public void afficherEtats(List<Etat> etats) {
        for (int i = 0; i < etats.size(); i++) {
        	Console.afficherMessage(i+1 + ". " + etats.get(i));
        }
    }
    
    public void optionModificationEtat() {
    	 Console.afficherMessage("\n-- MODIFIER UN ÉTAT --"
	                + "\n1. Modifier le nom"
	                + "\n2. Modifier la couleur"
	                + "\n0. Terminer modification");
    }
    
    
	public void afficherMenuFacture() {
		Console.afficherMessage("\n-- GÉRER LES FACTURES D'UN CLIENT --"
		            + "\n1. Ajouter une facture"
		            + "\n2. Modifier une facture"
		            + "\n3. Archiver une facture"
		            + "\n4. Voir les factures du client"
		            + "\n5. Chercher facture avec filtres"
		            + "\n0. Retour au menu");
	}
	

	public void afficherFactures(List<Facture> factures) {
	    Console.afficherMessage(
	            String.format("%-4s %-10s %-12s %-12s %-10s %-7s %-10s %-10s %-15s %-15s",
	                    "N°", "Nom", "Date", "Échéance", "HTVA", "TVA %", "TTC", "Type", "État", "Partenaire")
	    );

	    int index = 0;
	    for (Facture f : factures) {
	        afficherFacture(f, index++);
	    }
	}

	private void afficherFacture(Facture f, int index) {
	    Console.afficherMessage(
	            String.format("%-4d %-10s %-12s %-12s %-10.2f %-7.2f %-10.2f %-10s %-15s %-15s",
	                    index,
	                    f.getNumFacture(),
	                    f.getDateFacture(),
	                    f.getDateEcheance(),
	                    f.getMontantHtva(),
	                    f.getTauxTva(),
	                    f.calculerMontantTtc(),
	                    f.getType() != null ? f.getType().getNomType() : "X",
	                    f.getEtat() != null ? f.getEtat().getNom() : "X",
	                    f.getPartenaire() != null ? f.getPartenaire().getNom() : "X"
	            )
	    );
	}

	
	public void afficherMenuModificationFacture() {
		Console.afficherMessage("\n-- MODIFIER UNE FACTURE --"
			+ "\n1. Nom facture"
			+ "\n2. Montant HTVA"
			+ "\n3. Taux TVA"
			+ "\n4. Type"
			+ "\n5. État"
			+ "\n0. Terminer modification");
	}

	
	public void affichageMenu() {
		Console.afficherMessage( "\n-- MENU PRINCIPAL --"
	                + "\n1. Gérer les états"
	                //+ "\n2. Ajouter client"
	                + "\n2. Gérer factures"
	                + "\n3. Créer échéancier"
	                + "\n0. Fermer le programme");
	}
}
