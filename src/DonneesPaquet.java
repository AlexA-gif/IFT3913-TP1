public class DonneesPaquet {
    private int nbrCommentaires;
    private int nbrCodes;

    public DonneesPaquet (int comments, int codes) {
        this.nbrCodes = codes;
        this.nbrCommentaires = comments;
    }

    public int getComments() {
        return this.nbrCommentaires;
    }

    public int getCodes(){
        return this.nbrCodes;
    }

    public void addCodes(int nbrLignesAjouter){
        this.nbrCodes+=nbrLignesAjouter;
        return;
    }

    public void addComment(int nbrLignesAjouter){
        this.nbrCommentaires+=nbrLignesAjouter;
        return;
    }
}