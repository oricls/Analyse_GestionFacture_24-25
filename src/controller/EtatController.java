package controller;

import model.Etat;
import repository.EtatRepository;
import repository.FactureRepository;
import utils.Console;
import view.GestionView;

public class EtatController {
	private final FactureRepository repoFacture;
    private final EtatRepository repoEtat;
	private final GestionView view;
	
	public EtatController(EtatRepository repoEtat, FactureRepository repoFacture,GestionView view) {
		this.repoEtat = repoEtat;
		this.repoFacture = repoFacture;
		this.view = view;
	}
	
	 public void gererEtats() {
	        int choix;
	        do {
	            view.afficherMenuEtat();
	            choix = Console.inputInteger("Choix : ");

	            switch (choix) {
	                case 1 -> addNewEtat();
	                case 2 -> updateEtat();
	                case 3 -> deleteEtat();
	                case 4 -> afficherEtats();
	                case 0 -> Console.afficherMessage("Retour au menu");
	            }
	        } while (choix != 0);
	    }
	 
	private void addNewEtat() {
		String nom = Console.inputString("Nom de l'état : ");
		String couleur = Console.inputString("Couleur de l'état (#xxxxxx) : ");
		
		Etat e = new Etat(nom, couleur);
		boolean result = repoEtat.addEtat(e);
		
		resultMessage(result, "Etat enregistré", "Impossible d'enregistrer l'état");
	}
	
	
	private void updateEtat() {
	    afficherEtats();

	    int index = Console.inputInteger("Numéro de l'état à modifier : ");
	    Etat etat = repoEtat.getEtatByid(index-1);

	    boolean modifier = true;

	    while (modifier) {
	    	view.optionModificationEtat();
	        int choix = Console.inputInteger("Choix : ");

	        switch (choix) {
	            case 1 -> {
	                String nouveauNom = Console.inputString("Nouveau nom de l'état : ");
	                etat.setEtat(nouveauNom, etat.getCouleurHex());
	                Console.afficherMessage("Nom mis à jour.");
	            }
	            case 2 -> {
	                String nouvelleCouleur = Console.inputString("Nouvelle couleur (#xxxxxx) : ");
	                etat.setEtat(etat.getNom(), nouvelleCouleur);
	                Console.afficherMessage("Couleur mise à jour.");
	            }
	            case 0 -> {
	                modifier = false;
	                Console.afficherMessage("Modification terminée.");
	            }
	        }
	    }
	}

	

	
	private void deleteEtat() {
		afficherEtats();
		
		int index = Console.inputInteger("Numero de l'état à supprimer : ");
		var e = repoEtat.getEtatByid(index-1);
		
		var result = repoEtat.deleteEtat(e.getNom());
		supprimerEtatDesFactures(e);
		resultMessage(result, "Etat supprimé", "Impossible de suppriemr l'état");
	}
	
	private void supprimerEtatDesFactures(Etat etatSupprime) {
	    var factures = repoFacture.getAllFactures();
	    factures.forEach(f -> {
	        if (f.getEtat() != null && f.getEtat().equals(etatSupprime)) {
	            f.setEtat(null);
	        }
	    });
	}
	
	private void afficherEtats() {
		view.afficherEtats(repoEtat.getEtats());
    }
	
	private void resultMessage(boolean result, String success, String fail) {
		if (result) {
			Console.afficherMessage("Etat enregistré");
		}else {
			Console.afficherMessage("Impossible d'enregistrer l'état");
		}
	}
}
