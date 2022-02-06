import java.io.File;

public class FileExplorer {
    
    public void rechercheChemin (File chemin){
        File cheminRecherche = chemin; 
        File[] listOfFiles = cheminRecherche.listFiles();
        ParserDocs result = new ParserDocs();

        for (File file : listOfFiles) {
            if (file.isFile()) {
            System.out.println(file);
            result.verifsLignes(file);
            }else {
                rechercheChemin(file);
            }
        }
    }

}
