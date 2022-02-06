public class CompteurLignes {
    private int nbrCommentaires;
    private int nbrCodes;

    public CompteurLignes (int comments, int codes) {
        this.nbrCodes = codes;
        this.nbrCommentaires = comments;
    }

    public int getComments() {
        return this.nbrCommentaires;
    }

    public int getCodes(){
        return this.nbrCodes;
    }
}
