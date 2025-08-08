package controller;

import java.time.LocalDate;
import java.util.List;
import domain.model.ClientFinal;
import domain.model.Etat;
import domain.model.Facture;
import domain.model.Fournisseur;
import domain.model.Partenaire;
import domain.model.Particulier;
import domain.model.Type;
import domain.repository.IEtatRepository;
import domain.repository.IFactureRepository;
import utils.Console;
import view.IMainView;

public class FactureController {    
    private final IFactureRepository repoFacture;
    private final IEtatRepository repoEtat;
    private Particulier clientDirect;

    private final IMainView view;
    
    public FactureController(IEtatRepository repoEtat, IFactureRepository repoFacture,  Particulier clientDirect, IMainView view) {
    	this.repoFacture = repoFacture;
        this.repoEtat = repoEtat;
        this.clientDirect = clientDirect;
        this.view = view;
    }

    public void gererFactures() {
        boolean continuer = true;
        while (continuer) {
            view.afficherMenuFacture();
            int choix = Console.inputInteger("Choix : ");
            
            switch (choix) {
                case 1 -> ajouterFacture();
                case 2 -> modifierFacture();
                case 3 -> archiverFacture();
                case 4 -> afficherFactures();
                case 5 -> rechercherFacture();
                case 0 -> continuer = false;
            }
        }
    }
    
    private void afficherEtats() {
		view.afficherEtats(repoEtat.getEtats());
    }
    
    private void afficherFactures() {
    	view.afficherFactures(repoFacture.getFactureOfClient(clientDirect));
    }
    
    
    public void genererEcheancier() {
    	String dateStr = Console.inputString("Inclure les factures avec échéance avant (AAAA-MM-JJ, vide pour toutes) : ");
    	final LocalDate dateMax = dateStr.isBlank() ? null : LocalDate.parse(dateStr);


        int numType = Console.inputInteger("Type (1. Rentrée / 2. Dépense / 0 pour ignorer) : ");
        Type type = (numType > 0) ? getType(numType) : null;

        Console.afficherMessage("État des factures (0 pour ignorer) :");
        afficherEtats();
        int numEtat = Console.inputInteger("Choix : ");
        Etat etat = (numEtat > 0) ? repoEtat.getEtatByid(numEtat-1) : null;

        List<Facture> factures = repoFacture.rechercherFactures(null, null, type, etat);

        if (dateMax != null) {
            factures = factures.stream()
                    .filter(f -> f.getDateEcheance() != null && (f.getDateEcheance().isBefore(dateMax) || f.getDateEcheance().isEqual(dateMax)))
                    .toList();
        }

        if (factures.isEmpty()) {
            Console.afficherMessage("Aucune facture trouvée");
        } else {
            Console.afficherMessage("-- ECHEANCIER --");
            view.afficherFactures(factures);
            afficherFacturesAvecTotaux(factures);

        }
    }
    
    public void afficherFacturesAvecTotaux(List<Facture> factures) {
        double totalHtva = 0;
        double totalTva = 0;
        double totalTtc = 0;

        for (Facture f : factures) {
            totalHtva += f.getMontantHtva();
            totalTva += f.calculerMontantTva();
            totalTtc += f.calculerMontantTtc();
        }

        Console.afficherMessage("\n-- TOTAUX --");
        Console.afficherMessage(String.format("Total HTVA : %.2f", totalHtva));
        Console.afficherMessage(String.format("Total TVA  : %.2f", totalTva));
        Console.afficherMessage(String.format("Total TTC  : %.2f", totalTtc));
    }

    
    
    
    private void ajouterFacture() {
    	String nom = Console.inputString("Nom facture : ");

        double montant = Console.inputDouble("Montant HTVA : ");

        double taux = Console.inputDouble("Taux TVA (en %): ");

        int type = 0;
        do {
        	type = Console.inputInteger("Type : \n(1. Rentrée / 2. Dépense) : ");
        }while(type != 1 && type != 2);

       
        String partenaireNom = Console.inputString("Nom du partenaire : ");

        String numTva = null;
        if (type == 2) {
            numTva = Console.inputString("Num TVA du partenaire : ");
        }

        Console.afficherMessage("État :");
        afficherEtats();
        int etat = 0;
        do {
        	etat = Console.inputInteger("Choix : ");
        }while (etat < 1 || etat > repoEtat.getEtats().size());

        ajouterFacture(nom, montant, taux, type, etat, partenaireNom, numTva);
    }
    
    private void ajouterFacture(String nom, double montant, double taux, int numType, int numEtat, String partenaireNom, String numTva) {
    	var type = getType(numType);
    	var etat = repoEtat.getEtatByid(numEtat-1);

    	var partenaire = construirePartenaire(numType, partenaireNom, numTva);

        var facture = new Facture (nom, LocalDate.now(), LocalDate.now(), montant, taux, nom, type, etat, partenaire, clientDirect);
        
        var result = repoFacture.addFacture(facture);
        if (result) Console.afficherMessage("Facture enregistrée");
    }

    private Partenaire construirePartenaire(int numType, String partenaireNom, String numTva) {
        return switch (numType) {
            case 1 -> new ClientFinal(partenaireNom);
            case 2 -> new Fournisseur(partenaireNom, numTva);
            default -> throw new IllegalArgumentException("Type de partenaire invalide");
        };
    }

    private Type getType(int num) {
        return switch (num) {
            case 1 -> new Type("Rentrée");
            case 2 -> new Type("Dépense");
            default -> throw new IllegalArgumentException("Type de facture invalide");
        };
    }
   
    private void archiverFacture() {
        afficherFactures();
        int index = Console.inputInteger("Numéro de la facture à archiver : ");
        var result = repoFacture.archiverFacture(index);
        
        if (result) Console.afficherMessage("Facture archivée");
    }
    
    
    
    private void rechercherFacture() {
        String nom = Console.inputString("Nom facture (vide si pas de critère) : ");
        if (nom.isBlank()) nom = null;

        String dateStr = Console.inputString("Date (AAAA-MM-JJ, vide si pas de critère) : ");
        LocalDate date = null;
        if (!dateStr.isBlank()) date = LocalDate.parse(dateStr);

        int type = Console.inputInteger("Type :\n(1. Rentrée / 2. Dépense / 0 pour ignorer)\nChoix : ");
        
        int etat = -1;
        do {
        	 Console.afficherMessage("État :\n(0 pour ignorer)");
             afficherEtats();
             etat = Console.inputInteger("Choix : ");
        }while(etat < 0 || etat > repoEtat.getEtats().size());
       

        var resultats = rechercherFactures(nom, date, type, etat);

        if (resultats.isEmpty()) {
            Console.afficherMessage("Aucune facture trouvée.");
        } else {
            view.afficherFactures(resultats);
        }
    }
    

    private void modifierFacture() {
    	afficherFactures();
    	int index = Console.inputInteger("Numéro de la facture à modifier : ");
    	Facture facture = repoFacture.getFactureOfClient(clientDirect).get(index);

    	boolean modifier = true;
    	while (modifier) {
    		view.afficherMenuModificationFacture();
    		int choix = Console.inputInteger("Choix : ");

    		switch (choix) {
    			case 1 -> {
    				String nom = Console.inputString("Nouveau nom de facture : ");
    				facture.setNumFacture(nom);
    			}
    			case 2 -> {
    				double montant = Console.inputDouble("Nouveau montant HTVA : ");
    				facture.setMontantHtva(montant);
    			}
    			case 3 -> {
    				double taux = Console.inputDouble("Nouveau taux TVA : ");
    				facture.setTauxTva(taux);
    			}
    			case 4 -> {
    				int numType = Console.inputInteger("Type (1. Rentrée / 2. Dépense) : ");
    				facture.setType(getType(numType));
    			}
    			case 5 -> {
    				afficherEtats();
    				int numEtat = Console.inputInteger("État : ");
    				facture.setEtat(repoEtat.getEtatByid(numEtat-1));
    			}
    			case 0 -> {
    				modifier = false;
    				Console.afficherMessage("Modification terminée.");
    			}
    		}
    	}
    }
    
    public List<Facture> rechercherFactures(String nom, LocalDate date, int numType, int numEtat) {
    	var type = (numType > 0) ? getType(numType) : null;
    	var etat = (numEtat > 0) ? repoEtat.getEtatByid(numEtat-1) : null;
        return repoFacture.rechercherFactures(nom, date, type, etat);
    }
}
