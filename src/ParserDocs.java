
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Classe permettant de traverser un fichier .java donné comme paramètre. 
 * Permet de :
 * - compter le nombre de lignes de commentaires
 * - compter le nombre de lignes de code
 * - faire le calcul du WMC
 */
public class ParserDocs {

    /**Paramètres pour le compte des lignes*/
    private int nbrCodes = 0;
    private int nbrCommentaires = 0;

    /**Paramètres d'identification de l'état d'une ligne */
    private boolean isComment = false;
    private boolean commentFound = false;
    private boolean codeFound = false;

    /**Point de décisions pour le calcul du WMC */
    private String[] listeCommandes = new String[] {"for(", "for (", "if(", "if (", "case", "while( ", "while ("};

    /**
     * Cree l'ensemble de données nécessaires pour la définition de la classe 
     * (chemin, class, classe_LOC, classe_CLOC, classe_DC, WMC, classe_BC)
     * 
     * @param dossier Chemin du fichier .java en traitement
     * 
     * @return Les données contenues dans la classe 
     */
    public DonneesClasse verifsLignes (File dossier)
    {   
        nbrCodes = 0;
        nbrCommentaires = 0;
        isComment = false;
        commentFound = false;
        codeFound = false;
        DonneesClasse lignes = new DonneesClasse(0,0);

        try{
            BufferedReader reader = new BufferedReader(new FileReader(dossier));
            String ligneEnCours = reader.readLine();

            while(ligneEnCours != null) {
                analyserLigne(ligneEnCours);
                ligneEnCours = reader.readLine();
            }
            reader.close();

            lignes.addCodes(nbrCodes);
            lignes.addComment(nbrCommentaires);
            lignes.setWMC(calculerWMC(dossier));

        }catch(Exception ex) {
            System.out.println("Une erreur s'est produite #1: " + ex);
        }

        return lignes;
    }

    /**
     * Identifie si la ligne possède des commentaires/code
     * 
     * @param ligne Ligne en cours de lecture dans le BufferedReader
     */
    private void analyserLigne (String ligne){
        String traitement = ligne;

        /**Ligne vide => Aucun traitement */
        if (ligne.length() == 0){
            return;
        }

        /**Traitement de commentaire multiligne */
        if(isComment){

            /**Ajout de début de commentaire au début de la ligne */
            traitement="/*"+traitement;

            /**Si le commentaire finit => élimination du commentaire
             * Sinon on passe à la prochaine ligne
             */
            if(traitement.contains("*/")){ 
                commentFound=true;
                traitement=traitement.replaceAll(".*[*]/", "");
                isComment=false;
            }else{
                nbrCommentaires++;
                return;
            }
        }

        /**Identification des guillemets dans la ligne et élimination 
         * de la partie entre les guillets. 
        */
        if(traitement.contains("\"")){
            traitement=traitement.replaceAll("\".*\"", "");
        }

        /**Début d'un comentaire multiple */
        if(traitement.contains("/*")){
            commentFound=true;

            /**Si le commentaire finit => élimination du commentaire 
             * Sinon on passe à la prochaine ligne.
            */
            if(traitement.contains("*/")){
                traitement=traitement.replaceAll("/[*].*[*]/", ""); 
            }else{
                isComment=true;
                nbrCommentaires++;
                return;
            }
        }

        /**Identification de commentaire simple et élimination du commentaire */
        if(traitement.contains("//")){
            traitement=traitement.replaceAll("//.*","");
            commentFound=true;
        }

        /**Identification des espaces libres et élimination */
        if(traitement.contains(" ")){
            traitement=traitement.replaceAll(" ","");
        }

        /**Identifications des tabs et élimination */
        if(traitement.contains("\t")){
            traitement=traitement.replaceAll("\t","");
        }

        /**Si la ligne possède encore des charactères après toutes les 
         * modifications => ligne de code 
        */
        if(traitement.length()>0){
            codeFound=true;
        }

        /**Ligne de code trouvée => incrémentation */
        if(codeFound){
            nbrCodes++;
        }
                
        /**Ligne de commentaire trouvée => incrémentation */
        if(commentFound){
            nbrCommentaires++;
        }

        /**Réinitialisation des paramètres */
        codeFound = false;
        commentFound = false;
    }

    /**
     * Méthode permettant d'identifier le nombre de méthodes dans un fichier .java
     * et les points de décisions pour calculer le WMC
     * 
     * @param dossier chemin du fichier .java
     * 
     * @return WMC
     */
    public int calculerWMC(File dossier) {

        int WMC = 0;

        try{

            BufferedReader reader = new BufferedReader(new FileReader(dossier));
            String ligneEnCours = reader.readLine();
            boolean isComment = false;

            while(ligneEnCours != null) {

                /**Traitement de commentaires multilignes  */
                if(isComment){

                    /**Si le commentaire finit => élimination du commentaire 
                     * Sinon on passe à la prochaine ligne.
                    */
                    if (ligneEnCours.indexOf("*/") != -1){
                        isComment = false;
                        StringBuffer ligneTraitement = new StringBuffer(ligneEnCours);
                        ligneTraitement.delete(0,ligneEnCours.indexOf("*/") + 1);
                        ligneEnCours = ligneTraitement.toString();
                    }else{
                        ligneEnCours = reader.readLine();
                        continue;
                    }
                }
                
                /**Si la ligne est une description possible d'une méthode => incrémentation WMC
                */
                if(ligneEnCours.matches("(.*\\bpublic \\b.*|.*\\bprivate \\b.*|.*\\bprotected \\b.*|.*\\bstatic \\b.*)[(].*")){
                    WMC++;
                }

                /**Début d'un commentaire multiple */
                if (ligneEnCours.indexOf("/*") != -1){

                    /**Si le commentaire finit => élimination du commentaire 
                     * Sinon on élimine la partie du commentaire et on continue
                    */
                    if (ligneEnCours.indexOf("*/") != -1){
                        StringBuffer ligneTraitement = new StringBuffer(ligneEnCours);
                        ligneTraitement.delete(ligneEnCours.indexOf("/*"),ligneEnCours.indexOf("*/") + 1);
                        ligneEnCours = ligneTraitement.toString();
                    } else {
                        isComment = true;
                        ligneEnCours = ligneEnCours.substring(0, ligneEnCours.indexOf("/*"));
                    }
                }

                /**Identification des guillemets dans la ligne et élimination 
                 * de la partie entre les guillets. 
                */
                while (ligneEnCours.indexOf("\"") != -1 ){
                    int index = ligneEnCours.indexOf("\"");
                    int nextindex = ligneEnCours.indexOf("\"", index +1);

                    if(nextindex == -1){
                        break;
                    }

                    StringBuffer ligneTraitement = new StringBuffer (ligneEnCours);
                    ligneTraitement.delete(index, nextindex+1);
                    ligneEnCours = ligneTraitement.toString();
                }

                /**Identification de commentaire simple et élimination du commentaire */
                while(ligneEnCours.indexOf("//") != -1 ){
                    int index = ligneEnCours.indexOf("//");
                    ligneEnCours = ligneEnCours.substring(0, index);
                }

                /**Identification des points de décisions et incrémentation du WMC */
                for(int i = 0; i < listeCommandes.length; i++){
                    if(ligneEnCours.indexOf(listeCommandes[i]) != -1){
                        WMC += 1;
                    }
                }
                
                ligneEnCours = reader.readLine();
            }

            reader.close();

        }catch(Exception ex) {
            System.out.println("Unde erreur s'est produite #2: "+ ex);
        }

        return WMC;
    }
}
