import java.io.File;

public class Test {
    
    public void rechercheChemin (File chemin){
        File cheminRecherche = chemin; 
        File[] listOfFiles = cheminRecherche.listFiles();
        
        for (File file : listOfFiles) {
            if (file.isFile()) {
            System.out.println(file+"\n"+file.getName());
            }else {
                rechercheChemin(file);
            }
        }
    }

}
