package controller;

import model.Particulier;
import repository.EtatRepository;
import repository.FactureRepository;
import utils.Console;
import view.GestionView;

public class Main {	
    public static void main(String[] args) {    	
    	GestionView view = new GestionView();
    	
    	Particulier clientDirect = new Particulier("Roger Bernard", "000000000", "bernard.roger@mail.com");
    	
    	EtatRepository etatRepo = new EtatRepository();
    	FactureRepository factureRepo = new FactureRepository(clientDirect);
    	
    	EtatController etatController = new EtatController(etatRepo,factureRepo, view);
    	FactureController factureController = new FactureController(etatRepo, factureRepo, clientDirect, view);
    	
        int saisie;
        do {
        	view.affichageMenu();
        	saisie = Console.inputInteger("Choix : ");

            switch (saisie) {
            	case 0 -> Console.afficherMessage("Programme terminé.");
                case 1 -> etatController.gererEtats();
                case 2 -> factureController.gererFactures();
                case 3 -> factureController.genererEcheancier();
            }
        } while (saisie != 0);

    }       
}
