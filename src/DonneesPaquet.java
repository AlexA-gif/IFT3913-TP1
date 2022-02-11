/**
 * Classe detenant toutes les informations liées aux paquets nécessaires 
 * pour la mise à jour du fichier CSV des paquets
 */
public class DonneesPaquet {

    /**Déclaration des attributs */
    private int nbrCommentaires;
    private int nbrCodes;
    private float densiteCommentaire;
    private int WCP;
    private float degre;

    public DonneesPaquet (int comments, int codes) {
        this.nbrCodes = codes;
        this.nbrCommentaires = comments;
        this.densiteCommentaire = 0;
        this.WCP = 0;
        this.degre=0;
    }

    /**
     * Retourne le nombre de lignes de commentaires dans le paquet
     * 
     * @return Nombre de lignes de commentaire
     */
    public int getComments() {
        return this.nbrCommentaires;
    }

    /**
     * Retroune le nombre de lignes de code dans le paquet
     * 
     * @return Nombre de lignes de code
     */
    public int getCodes(){
        return this.nbrCodes;
    }

    /**
     * Retourne la densité de commentaire d'un paquet, calculée par 
     * updateDensite()
     * 
     * @return Résultat du calcul de la densité de commentaire d'un paquet
     */
    public float getDensite(){
        return this.densiteCommentaire;
    }

    /**
     * Retourne le "Weighted Methods per Package" 
     * 
     * @return Valeur du WCP
     */
    public int getWCP(){
        return this.WCP;
    }

    /**
     * Retourne le degré selon lequel un paquet est bien commenté
     * 
     * @return Valuer du degré
     */
    public float getDegre(){
        return this.degre;
    }

    /**
     * Mise à jour du nombre de lignes de code trouvés dans le paquet
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
     * Mise à jour du nombre de lignes de commentaires trouvés dans le paquet
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
     * Mise à jour du WCP du paquet et mise à jour du degré
     * 
     * @param input Résultat suite au calcul du WCP
     */
    public void addWCP(int input){
        this.WCP+=input;
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
        if(this.densiteCommentaire == 0 || this.WCP == 0){
            this.degre=0;
            return;
        }
        this.degre = this.densiteCommentaire/(float)this.WCP;
        return;
    }

}