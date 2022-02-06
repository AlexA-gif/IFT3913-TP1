import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class App {

    //test 2
    public static void main(String[] args) throws Exception {

        //File chemin = new File ("C:/Users/Alexandra/Documents/Universite/HIVER2022"); 
        File chemin = new File ("C:/Users/Alexandra/Documents/Universite/HIVER2022/IFT3913-QualiteLogiciel/TestTP1"); 
        
        
        FileExplorer fe = new FileExplorer();
        fe.rechercheChemin(chemin);
       

    }

}


