/**
 * Classe detenant toutes les informations liées aux classes nécessaires 
 * pour la mise à jour du fichier CSV des classes
 */
public class DonneesClasse {

    /**Déclaration des attributs */
    private int nbrCommentaires;
    private int nbrCodes;
    private float densiteCommentaire;
    private int WMC;
    private float degre; 

    public DonneesClasse (int comments, int codes) {
        this.nbrCodes = codes;
        this.nbrCommentaires = comments;
        this.densiteCommentaire=0;
        this.WMC = 0;
        this.degre=0;
    }

    /**
     * Retourne le nombre de lignes de commentaires dans la classe
     * 
     * @return Nombre de lignes de commentaire
     */
    public int getComments() {
        return this.nbrCommentaires;
    }

    /**
     * Retroune le nombre de lignes de code dans la classe
     * 
     * @return Nombre de lignes de code
     */
    public int getCodes(){
        return this.nbrCodes;
    }
    
    /**
     * Retourne la densité de commentaire d'une classe, calculée par 
     * updateDensite()
     * 
     * @return Résultat du calcul de la densité de commentaire d'une classe
     */
    public float getDensite(){
        return this.densiteCommentaire;
    }

    /**
     * Retourne le "Weighted Methods per Class" calculée dans 
     * ParserDocs.calculerWMC()
     * 
     * @return Valeur du WMC
     */
    public int getWMC() {
        return this.WMC;
    }

    /**
     * Retourne le degré selon lequel une classe est bien commentée 
     * 
     * @return Valuer du degré
     */
    public float getDegre() {
        return this.degre;
    }

    /**
     * Mise à jour du nombre de lignes de code trouvés dans la classe
     * et mise à jour de la densité de commentaires
     * 
     * @param nbrLignesAjouter Nombre de lignes de code à ajouter
     */
    public void addCodes(int nbrLignesAjouter){
        this.nbrCodes+=nbrLignesAjouter;
        this.updateDensite();
        return;
    }

    /**
     * Mise à jour du nombre de lignes de commentaires trouvés dans la classe
     * et mise à jour de la densité de commentaires
     * 
     * @param nbrLignesAjouter Nombre de lignes de commentaires à ajouter
     */
    public void addComment(int nbrLignesAjouter){
        this.nbrCommentaires+=nbrLignesAjouter;
        this.updateDensite();
        return;
    }

    /**
     * Mise à jour du WMC de la classe et mise à jour du degré
     * 
     * @param input Résultat suite au calcul du WMC
     */
    public void setWMC(int input){
        this.WMC=input;
        this.updateDegre();
        return;
    }

    /**Mise à jour de la densité de commentaire et
     * mise à jour du degré
     */
    private void updateDensite(){
        if(this.nbrCommentaires == 0 || this.nbrCodes == 0){
            this.densiteCommentaire=0;
            this.updateDegre();
            return;
        }
        this.densiteCommentaire = (float)this.nbrCommentaires/(float)this.nbrCodes;
        this.updateDegre();
        return;
    }

    /**Mise à jour du degré selon lequel la classe est bien commentée */
    private void updateDegre(){
        if(this.densiteCommentaire == 0 || this.WMC == 0){
            this.degre=0;
            return;
        }
        this.degre = this.densiteCommentaire/(float)this.WMC;
        return;
    }

}