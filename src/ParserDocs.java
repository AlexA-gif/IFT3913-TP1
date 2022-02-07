
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class ParserDocs {

    private int nbrCodes = 0;
    private int nbrCommentaires = 0;
    private boolean isString = false;
    private boolean isComment = false;
    private boolean commentFound = false;
    private boolean codeFound = false;

    public DonneesClasse verifsLignes (File dossier)
    {
        DonneesClasse lignes = new DonneesClasse(0,0);

        try{
            BufferedReader reader = new BufferedReader(new FileReader(dossier));
            String ligneEnCours = reader.readLine();

            while(ligneEnCours != null) {
                analyserLigne(ligneEnCours);
                ligneEnCours = reader.readLine();
            }
            reader.close();
            System.out.println(nbrCommentaires);
            System.out.println(nbrCodes);

            lignes.addCodes(nbrCodes);
            lignes.addComment(nbrCommentaires);

            //fonction qui compte les whiles, if, etc... 
            //calcule WMC
            //insertion du WMC

        }catch(Exception ex) {
            System.out.println(ex);
        }
        return lignes;
    }


    private void analyserLigne (String ligne){
        
        if (ligne.length() == 0){
            return;
        }

        for (int i = 0; i < ligne.length(); i++){

            if(isComment){
                commentFound=true;
                if(ligne.charAt(i) =='*' && i+1<ligne.length() && ligne.charAt(i+1)=='/' ){
                    i++;
                    isComment=false;
                }
                if(i==ligne.length()-1){
                    break;
                }
                continue;
            }
            if(isString){
                if(ligne.charAt(i)=='"'){
                    isString=false;
                }
                continue;
            }

            if(ligne.charAt(i) == '/'  && !isString){
                if(i+1<ligne.length() && ligne.charAt(i+1) == '/' && !isComment){
                    commentFound=true;
                    break;
                } else if(i+1<ligne.length() && ligne.charAt(i+1) == '*'){
                    i++;
                    isComment = true;
                    commentFound=true;
                    continue;
                }else if(!isComment){
                    codeFound = true;
                    continue;
                }
            }

            if(ligne.charAt(i) == '"' && !isString && !isComment){
                codeFound= true;
                isString = true;
                continue;
            }
            
            codeFound = true;
            
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


    //TO_DO : méthode analyser fichier pour WMC
}



