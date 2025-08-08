package controller;

import domain.model.Etat;
import domain.repository.IEtatRepository;
import domain.repository.IFactureRepository;
import utils.Console;
import view.IMainView;

public class EtatController {
	private final IFactureRepository repoFacture;
    private final IEtatRepository repoEtat;
	private final IMainView view;
	private static final String REGEX_COLOR = "^#[0-9a-fA-F]{6}$";
	
	public EtatController(IEtatRepository repoEtat, IFactureRepository repoFacture, IMainView view) {
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
		String couleur;		
		do {
		    couleur = Console.inputString("Couleur de l'état (#xxxxxx) : ");
		} while (!validateColor(couleur));
		
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
	                String nouvelleCouleur;
	                do {
	                	nouvelleCouleur = Console.inputString("Nouvelle couleur (#xxxxxx) : ");
	                }while (!validateColor(nouvelleCouleur));
	                
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
	
	private boolean validateColor(String couleur) {
	    return (couleur != null && couleur.matches(REGEX_COLOR));
	}
	
	private void resultMessage(boolean result, String success, String fail) {
		if (result) {
			Console.afficherMessage("Etat enregistré");
		}else {
			Console.afficherMessage("Impossible d'enregistrer l'état");
		}
	}
}
