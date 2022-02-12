import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


/**
 * Classe principale permettant de lancer le processus selon le chemin donné
 * et de créer les fichiers CSV dans lesquels les données vont se trouver
 */
public class App {
    public static void main(String[] args) throws Exception {

        File chemin = new File ("./input/");
        FileExplorer fe = new FileExplorer();

        try {
            File dir = new File("./resultat");
            dir.mkdir();

            /**Initialisation des fichiers CSV ppour les classes et les paquets */
            File toCreate1 = new File("./resultat/classes.csv");
            File toCreate2 = new File("./resultat/paquets.csv");
            toCreate1.createNewFile();
            toCreate2.createNewFile();

            /**Ajouter les premières informations dans le fichier CSV des classes*/
            FileWriter writer = new FileWriter("./resultat/classes.csv");
            writer.append("chemin,class,classe_LOC,classe_CLOC,classe_DC,WMC,classe_BC");
            writer.append("\n");
            writer.flush();
            writer.close();

            /**Ajouter les premières informations dans le fichier CSV des paquets */
            writer = new FileWriter("./resultat/paquets.csv");
            writer.append("chemin,paquet,paquet_LOC,paquet_CLOC,paquet_DC,WCP,paquet_BC");
            writer.append("\n");
            writer.flush();
            writer.close();
            
        } catch (IOException e) {
            System.out.println("Une erreur s'est produite lors de la création des fichiers CSV.");
            e.printStackTrace();
        }

        fe.traitePath(chemin, "");

    }
}