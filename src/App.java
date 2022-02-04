import java.io.File;

public class App {

    
    public static void main(String[] args) throws Exception {

        File chemin = new File ("C:/Users/Alexandra/Documents/Universite/HIVER2022"); 
        Test test = new Test ();
        test.rechercheChemin(chemin);
       

        /*
        parser parse = new parser();
        paireReads[] test = parse.verificationReads();
        System.out.println(test[0].getNom());
        System.out.println(test[0].getSequence());
        System.out.println(test[1].getNom());
        System.out.println(test[1].getSequence());
        */
    }

}


