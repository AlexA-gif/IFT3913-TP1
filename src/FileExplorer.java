import java.io.File;

public class FileExplorer {
    
//à enlever
    public void rechercheChemin (File chemin){

        File cheminRecherche = chemin; 
        File[] listOfFiles = cheminRecherche.listFiles();
        ParserDocs result = new ParserDocs();


        for (File file : listOfFiles) {

            if (file.isFile()) {
                System.out.println(file);
                result.verifsLignes(file);
                System.out.println("ici1: "+file);
            }else {
                rechercheChemin(file);
                System.out.println("ici2: "+file);
            }

        }
    }

    public void traitePath (File path, String paquet){
 
        File[] listOfFiles = path.listFiles();
        ParserDocs result = new ParserDocs();
        int nbrJavaFiles = this.checkForJava(path);
        
        //TODO MÉTHODE QUI TRIE LES FILES POUR LES .JAVA
        //TODO MÉTHODE QUI TRIE LES DOSSIERS
        File[] javaFiles = isolateJavaFiles(listOfFiles, nbrJavaFiles);
        File[] folderList = isolateFolders(listOfFiles, nbrJavaFiles);

        //tableau de stockage d'information des fichiers
        DonneesClasse[] javaFilesData = new DonneesClasse[javaFiles.length];
        //tableau de stockage d'information des paquets


        
        //for de traitement de dossiers
            //doit updater nom du paquet avant de traiter les autres dossiers.

        if(nbrJavaFiles>0){
            //var paquet contient le nom du paquet
            //Doit traiter seulement les .java
            for(File file : javaFiles){
                System.out.println(file);
                result.verifsLignes(file);
                System.out.println("ici1: "+file);
                //updater csv file avec les données du file courant
            }
        
        //boucle d'itération des sous-dossiers
        //updater la var paquet lors de l'appel

        //updater le paquet csv avec les données du paquet courant


/*
            for (File file : javaFiles) {

                if (file.isFile()) {
                    System.out.println(file);
                    result.verifsLignes(file);
                    System.out.println("ici1: "+file);
                    //updater csv file avec les données du file courant
                }else {
                    rechercheChemin(file);
                    System.out.println("ici2: "+file);
                }
            }
            
            //updater le paquet csv avec les données du paquet courant
*/



        }
  
        

        //traitement des variables qui demandent toutes les infos
        
    }

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

    private File[] isolateFolders(File[] input, int nbrFiles){
        File[] retour = new File[input.length-nbrFiles];

        int i =0;
        for(File file : input){
            if(file.isDirectory()){
                retour[i]=file;
                i++;
            }
        }
        return retour;
    }

}
