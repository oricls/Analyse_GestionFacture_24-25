package view;

import java.util.List;

import domain.model.Etat;
import domain.model.Facture;

public interface IMainView {
	 void afficherMenuEtat();
	 void afficherEtats(List<Etat> etats);
	 void optionModificationEtat() ;
	 void afficherMenuFacture();
	 void afficherFactures(List<Facture> factures);
	 void afficherMenuModificationFacture();
	 void affichageMenu();

}
