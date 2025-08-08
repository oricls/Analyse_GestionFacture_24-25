package controller;

import domain.model.Particulier;
import domain.repository.IEtatRepository;
import domain.repository.IFactureRepository;
import infrastructure.EtatRepository;
import infrastructure.FactureRepository;
import utils.Console;
import view.IMainView;
import view.MainView;

public class Main {	
    public static void main(String[] args) {    	
    	IMainView view = new MainView();
    	
    	Particulier clientDirect = new Particulier("Roger Bernard", "000000000", "bernard.roger@mail.com");
    	
    	IEtatRepository etatRepo = new EtatRepository();
    	IFactureRepository factureRepo = new FactureRepository(clientDirect);
    	
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
