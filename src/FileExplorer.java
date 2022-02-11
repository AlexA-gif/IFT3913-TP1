import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Classe permettant de parcourir les dossiers et de traiter les informations dans 
 * chaque dossier:
 * - Identifie les classes et recueille les données nécessaires pour chaque classe
 * - Identifie les paquets et recueille les données nécessaires pour chaque paquet
 * - Ajoute les informations dans les CSV appropriés.
 */
public class FileExplorer {
    
    public DonneesPaquet traitePath (File path, String paquet){
 
        File[] listOfFiles = path.listFiles();
        ParserDocs result = new ParserDocs();
        int nbrJavaFiles = this.checkForJava(path);

        /**Ensemble des files java et paquets */
        File[] javaFiles = isolateJavaFiles(listOfFiles, nbrJavaFiles);
        File[] folderList = isolateFolders(listOfFiles);

        /**Initialisation du tableau de stockage des informations des classes */
        DonneesClasse[] javaFilesData = new DonneesClasse[javaFiles.length];

        /**Initialisation du tableau de stockage des informations des paquets */
        DonneesPaquet[] repoData = new DonneesPaquet[folderList.length];

        /**Traitement des fichiers .java */
        if(nbrJavaFiles>0){
            int i =0;
            for(File file : javaFiles){
                /**Mettre à jour les données du fichier CSV des classes
                 * selon les données du fichier courant. 
                */
                javaFilesData[i]=result.verifsLignes(file);
                String nomClasse = javaFiles[i].getName();
                this.writeInCSV(javaFilesData[i], path.getPath(), nomClasse);
                i++;
            }
        }
        
        /**Boucle d'itération pour les sous-dossiers */
        for(int j =0; j < folderList.length; j++){
            /**Met à jour les données du paquet */
            FileExplorer fe2 = new FileExplorer();
            repoData[j] = fe2.traitePath(folderList[j], paquet+"/"+folderList[j].getName());
        }

        DonneesPaquet retour = new DonneesPaquet(0, 0);

        for(DonneesClasse data : javaFilesData){
            retour.addCodes(data.getCodes());
            retour.addComment(data.getComments());
            retour.addWCP(data.getWMC());
        }

        for(DonneesPaquet data : repoData){
            retour.addCodes(data.getCodes());
            retour.addComment(data.getComments());
            retour.addWCP(data.getWCP());
        }
        
        /**S'il y a présence de fichiers .java, met à jour les informations
         * dans le fichier CSV des paquets selon le
         * paquet en input.
         */
        if(nbrJavaFiles>0){
            this.writeInCSV(retour, path.getPath(), paquet);
        }
        
        return retour;
    }
   
    /**
     * Méthode permettant de compter le nombre de fichiers .java  
     * se trouvant dans le chemin donné en paramètre
     * 
     * @param pathTocheck Chemin pour vérifications des fichiers .java
     * 
     * @return Le nombre de fichiers .java trouvés dans le chemin en input
     */
    private int checkForJava(File pathTocheck){

        File[] filesToCheck = pathTocheck.listFiles();
        String path = "";
        int compteur = 0;

        for(int i =0; i < filesToCheck.length; i++ ){
            path = filesToCheck[i].getPath();
            if(path.contains(".java")){
                compteur += 1;
            }
        }
        return compteur;
    }

    /**
     * Méthode qui retourne un tableau de fichiers .java se trouvant
     * dans le chemin en input
     * 
     * @param input Chemin pour l'identification des fichiers .java
     * 
     * @param nbrFiles Nombre de fichiers .java extraits plus tôt de la 
     *                  méthode checkForJava (pour la création du tableau)
     * 
     * @return Le tableau de fichiers .java trouvés dans le chemin en input
     */
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

    /**
     * Méthode permettant de compter les dossiers présents dans le cheminn en input 
     * et retourne un tableau des dossiers 
     * 
     * @param input Chemin pour l'identification des dossiers
     * 
     * @return Le tableau de dossiers trouvés dans le chemin en input
     */
    private File[] isolateFolders(File[] input){
        
        /**Identification et comptage des dossiers se trouvant dans le chemin
         * en input 
         */
        int nbrDossier = 0;
        for(File file : input){
            if(file.isDirectory()){
                nbrDossier+=1;
            }
        }

        File[] retour = new File[nbrDossier];

        int i =0;

        /**Création du tableau de stockage des dossiers se trouvant dans le 
         * chemin en input.
         */
        for(File file : input){
            if(file.isDirectory()){
                retour[i]=file;
                i++;
            }
        }

        return retour;
    }

    /**
     * Méthode qui ajoute au fichier CSV des classes, les informations recueillies
     * de la classe en input.
     * 
     * @param input Données associées à chaque classe
     * 
     * @param path Chemin de la classe
     * 
     * @param classe Nom de la classe
     */
    private void writeInCSV(DonneesClasse input, String path, String classe){
        try {

            String strToWrite = ""+path+","+classe+","+input.getCodes()+","+
                input.getComments()+","+input.getDensite()+","+input.getWMC()+
                ","+input.getDegre()+"\n";

            FileWriter writer = new FileWriter("./resultat/classes.csv", true);
            writer.append(strToWrite);
            writer.flush();
            writer.close();

        } catch (IOException e) {
            System.out.println("Une erreur est survennue lorsqu'on mettait à jour le fichier CSV des classes.");
            e.printStackTrace();
        }
    }

    /**
     * Méthode qui ajoute au fichier CSV des paquets, les informations recueillies 
     * du paquet en input.
     * 
     * @param input Données associées au paquet
     * 
     * @param path Chemin du paquet
     * 
     * @param paquet Nom du paquet
     */
    private void writeInCSV(DonneesPaquet input, String path, String paquet){
        try {

            String strToWrite = ""+path+","+paquet+","+input.getCodes()+","+
                input.getComments()+","+input.getDensite()+","+
                input.getWCP()+","+input.getDegre()+"\n";

            FileWriter writer = new FileWriter("./resultat/paquets.csv", true);
            writer.append(strToWrite);
            writer.flush();
            writer.close();

        } catch (IOException e) {
            System.out.println("Une erreur est survennue lorsqu'on mettait à jour le fichier CSV des paquets.");
            e.printStackTrace();
        }
    }
}
