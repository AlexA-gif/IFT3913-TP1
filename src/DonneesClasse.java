public class DonneesClasse {
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

    public int getComments() {
        return this.nbrCommentaires;
    }

    public int getCodes(){
        return this.nbrCodes;
    }
    
    public float getDensite(){
        return this.densiteCommentaire;
    }

    public int getWMC() {
        return this.WMC;
    }

    public float getDegre() {
        return this.degre;
    }

    public void addCodes(int nbrLignesAjouter){
        this.nbrCodes+=nbrLignesAjouter;
        this.updateDensite();
        return;
    }

    public void addComment(int nbrLignesAjouter){
        this.nbrCommentaires+=nbrLignesAjouter;
        this.updateDensite();
        return;
    }

    public void setWMC(int input){
        this.WMC=input;
        this.updateDegre();
        return;
    }

    

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

    private void updateDegre(){
        if(this.densiteCommentaire == 0 || this.WMC == 0){
            this.degre=0;
            return;
        }
        this.degre = this.densiteCommentaire/(float)this.WMC;
        return;
    }

}