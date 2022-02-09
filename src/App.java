import java.io.File;
import java.io.FileWriter;
import java.io.IOException;



public class App {

    //test 2
    public static void main(String[] args) throws Exception {

        File chemin = new File ("./test/");
        FileExplorer fe = new FileExplorer();
        String[] listeCommandes = new String[] {"for", "if", "case", "while"};

        //creation des fichiers csv
        try {
            File dir = new File("./resultat");
            dir.mkdir();

            File toCreate1 = new File("./resultat/classes.csv");
            File toCreate2 = new File("./resultat/paquets.csv");
            toCreate1.createNewFile();
            toCreate2.createNewFile();

            FileWriter writer = new FileWriter("./resultat/classes.csv");
            writer.append("chemin,class,classe_LOC,classe_CLOC,classe_DC");
            writer.append("\n");
            writer.flush();
            writer.close();

            writer = new FileWriter("./resultat/paquets.csv");
            writer.append("chemin,paquet,paquet_LOC,paquet_CLOC,paquet_DC");
            writer.append("\n");
            writer.flush();
            writer.close();

            
          } catch (IOException e) {
                System.out.println("An error occurred while creating the csv files.");
                e.printStackTrace();
          }

        fe.traitePath(chemin, "");

    }

}


/*



*/