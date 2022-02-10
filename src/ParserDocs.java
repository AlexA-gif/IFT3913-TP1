
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Classe permettant de traverser un fichier .java donné comme paramètre et 
 * d'identifier le nombre de lignes de commentaires et le nombre de lignes de
 * code dans le fichier.
 */
public class ParserDocs {

    /**Paramètres d'identification de ligne */
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
            System.out.println(ex);
        }

        return lignes;
    }

    /**
     * Identifie si la ligne possède de commentaire de de code 
     * 
     * @param ligne
     */
    private void analyserLigne (String ligne){
        String traitement = ligne;

        /**Ligne vide => Aucun traitement */
        if (ligne.length() == 0){
            return;
        }

        /**Ligne est un commentaire */
        if(isComment){
            traitement="/*"+traitement;

            /**Si le commentaire finit */
            if(traitement.contains("*/")){ 
                commentFound=true;
                traitement=traitement.replaceAll(".*[*]/", ""); //enlève le commentaire
                isComment=false;
            }else{
                nbrCommentaires++;
                return;
            }
        }

        if(traitement.contains("\"")){
            traitement=traitement.replaceAll("\".*\"", "");
        }

        if(traitement.contains("/*")){
            commentFound=true;
            if(traitement.contains("*/")){
                traitement=traitement.replaceAll("/[*].*[*]/", "");
            }else{
                isComment=true;
                nbrCommentaires++;
                return;
            }
            
        }

        if(traitement.contains("//")){
            traitement=traitement.replaceAll("//.*","");
            commentFound=true;
        }

        if(traitement.contains(" ")){
            traitement=traitement.replaceAll(" ","");
        }

        if(traitement.contains("\t")){
            traitement=traitement.replaceAll("\t","");
        }

        if(traitement.length()>0){
            codeFound=true;
        }

        if(codeFound){
            nbrCodes++;
        }
        if(commentFound){
            nbrCommentaires++;
        }
        codeFound = false;
        commentFound = false;

    }

    public int calculerWMC(File dossier) {

        int WMC = 0;

        try{

            BufferedReader reader = new BufferedReader(new FileReader(dossier));
            String ligneEnCours = reader.readLine();

            boolean isComment = false;

            while(ligneEnCours != null) {

                //traitement des commentaires multilignes
                if(isComment){
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
                
                //calcul nombre méthodes dans une classe
                if(ligneEnCours.matches("(.*\\bpublic \\b.*|.*\\bprivate \\b.*|.*\\bprotected \\b.*|.*\\bstatic \\b.*)[(].*")){
                    WMC++;
                }

                if (ligneEnCours.indexOf("/*") != -1){
                    if (ligneEnCours.indexOf("*/") != -1){
                    
                        StringBuffer ligneTraitement = new StringBuffer(ligneEnCours);
                        ligneTraitement.delete(ligneEnCours.indexOf("/*"),ligneEnCours.indexOf("*/") + 1);
                        ligneEnCours = ligneTraitement.toString();
                    } else {
                        isComment = true;
                        ligneEnCours = ligneEnCours.substring(0, ligneEnCours.indexOf("/*"));
                        
                    }
                }

                // enlever les guillemets et les mots se trouvant entre 2 positions de guillemets
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

                //enlever les commentaires simples
                while(ligneEnCours.indexOf("//") != -1 ){
                    int index = ligneEnCours.indexOf("//");
                    ligneEnCours = ligneEnCours.substring(0, index);
                }

                //analyser pour des for, if, while, case 
                for(int i = 0; i < listeCommandes.length; i++){
                    if(ligneEnCours.indexOf(listeCommandes[i]) != -1){
                        WMC += 1;
                    }
                }
                
                ligneEnCours = reader.readLine();
            }
            reader.close();

        }catch(Exception ex) {
            System.out.println(ex);
        }
        return WMC;
    }

}
