import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileExplorer {
    
    public DonneesPaquet traitePath (File path, String paquet){
 
        File[] listOfFiles = path.listFiles();
        ParserDocs result = new ParserDocs();
        int nbrJavaFiles = this.checkForJava(path);
        
        File[] javaFiles = isolateJavaFiles(listOfFiles, nbrJavaFiles);
        File[] folderList = isolateFolders(listOfFiles);

        //tableau de stockage d'information des fichiers
        DonneesClasse[] javaFilesData = new DonneesClasse[javaFiles.length];
        //tableau de stockage d'information des paquets
        DonneesPaquet[] repoData = new DonneesPaquet[folderList.length];

        
        //for de traitement de dossiers
            //doit updater nom du paquet avant de traiter les autres dossiers.

        if(nbrJavaFiles>0){
            //var paquet contient le nom du paquet
            //Doit traiter seulement les .java
            int i =0;
            for(File file : javaFiles){
                javaFilesData[i]=result.verifsLignes(file);
                //updater csv file avec les données du file courant
                String nomClasse = javaFiles[i].getName();
                this.writeInCSV(javaFilesData[i], path.getPath(), nomClasse);
                i++;
            }
        }
        
        //boucle d'itération des sous-dossiers
        
        for(int j =0; j < folderList.length; j++){
            //updater la var paquet lors de l'appel
            FileExplorer fe2 = new FileExplorer();
            repoData[j] = fe2.traitePath(folderList[j], paquet+"/"+folderList[j].getName());
        }
        

        DonneesPaquet retour = new DonneesPaquet(0, 0);

        for(DonneesClasse data : javaFilesData){
            retour.addCodes(data.getCodes());
            retour.addComment(data.getComments());
        }
        for(DonneesPaquet data : repoData){
            retour.addCodes(data.getCodes());
            retour.addComment(data.getComments());
        }
        //setter le WCP du paquet
        
        //updater le paquet csv avec les données du paquet courant si presence de .java
        if(nbrJavaFiles>0){
            this.writeInCSV(retour, path.getPath(), paquet);
        }
        

        return retour;
    }
            
            




        
  
        

        //traitement des variables qui demandent toutes les infos
        
    

    private int checkForJava(File pathTocheck){

        File[] filesToCheck = pathTocheck.listFiles();
        String path = "";
        int compteur = 0;

        for(int i =0; i<filesToCheck.length; i++ ){
            path = filesToCheck[i].getPath();
            if(path.contains(".java")){
                compteur+=1;
            }
        }
        return compteur;
    }

    private File[] isolateJavaFiles(File[] input, int nbrFiles){
        File[] retour = new File[nbrFiles];
        String path = "";

        int i =0;
        for(File file : input){
            path = file.getPath();
            if(path.contains(".java")){
                retour[i]=file;
                i++;
            }
        }
        return retour;
    }

    private File[] isolateFolders(File[] input){
        
        int nbrDossier = 0;
        for(File file : input){
            if(file.isDirectory()){
                nbrDossier+=1;
            }
        }

        File[] retour = new File[nbrDossier];

        int i =0;
        for(File file : input){
            if(file.isDirectory()){
                retour[i]=file;
                i++;
            }
        }
        return retour;
    }

    //TO_DO ajouter le segment concernant le WCS
    private void writeInCSV(DonneesClasse input, String path, String classe){
        try {
            String strToWrite = ""+path+","+classe+","+input.getCodes()+","+
                input.getComments()+","+input.getDensite()+"\n";

            FileWriter writer = new FileWriter("./resultat/classes.csv", true);
            writer.append(strToWrite);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred while writing a java file in the csv file.");
            e.printStackTrace();
        }
    }

    private void writeInCSV(DonneesPaquet input, String path, String paquet){
        try {
            String strToWrite = ""+path+","+paquet+","+input.getCodes()+","+
                input.getComments()+","+input.getDensite()+"\n";

            FileWriter writer = new FileWriter("./resultat/paquets.csv", true);
            writer.append(strToWrite);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred while writing a java file in the csv file.");
            e.printStackTrace();
        }
    }

    //}

}
