
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.*;
import java.lang.reflect.*;

public class ParserDocs {

    private int nbrCodes = 0;
    private int nbrCommentaires = 0;
    private boolean isString = false;
    private boolean isComment = false;
    private boolean commentFound = false;
    private boolean codeFound = false;
    private String[] listeCommandes = new String[] {"for", "if", "case", "while"};

    public DonneesClasse verifsLignes (File dossier)
    {   
        nbrCodes = 0;
        nbrCommentaires = 0;
        isString = false;
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
            //calcul WMC
            lignes.setWMC(calculerWMC(dossier));
            System.out.println(lignes.getWMC() + "   "+dossier.getName());
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

    public int calculerWMC(File dossier) {

        int WMC = 0;
        String nomClasse = dossier.getName();
        int posPoint = nomClasse.indexOf(".");
        nomClasse = nomClasse.substring(0, posPoint);

        try {
            Class classeEnCours = Class.forName(nomClasse);
            int nbrMethodes = classeEnCours.getDeclaredMethods().length;
            WMC+=nbrMethodes;
        } catch (ClassNotFoundException e) {
            WMC+=1;            
        }
        
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
                for(int i = 0; i<listeCommandes.length; i++){
                    if(ligneEnCours.indexOf(listeCommandes[i]) != -1){
                        WMC++;
                    }
                }
                
                ligneEnCours = reader.readLine();
            }

        }catch(Exception ex) {
            System.out.println(ex);
        }
        return WMC;
    }


}



