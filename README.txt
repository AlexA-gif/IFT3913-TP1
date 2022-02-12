Travail réalisé par:
Alexis Rivest (20117680)
Alexandra Odagiu (20117272)

Lien GitHub: 
https://github.com/AlexA-gif/IFT3913-TP1

Le dossier src contient les fichiers .java qui doivent être compilés dans un JAR.

Le dossier "test" et le dossier "test2" contiennent des exemples qui ont été utilisés 
durant le développement pour tester les fonctionnalités du programme. VOUS NE DEVEZ 
PAS LES COMPILER AVEC LES FICHIERS DU DOSSIER "src"!!!

Le projet a été réalisé avec JAVA 8, sous Windows en utilisant VSCode comme IDE.
Afin de minimiser les chances d'une erreur de compilation, je vous encourage à 
compiler le projet dans les mêmes conditions qu'énumérées ci-haut.

Afin d'utiliser le programme java compilé, vous devrez le placer dans un dossier 
vide de votre choix, dans lequel le programme pourra Créer/Modifier/Lire des 
fichiers, de préférence dans un environnement Windows. Le programme a principalement
été développé pour utilisation sur Windows.

Vous devrez également créer un dossier nommé "input" DANS LE MÊME DOSSIER OÙ 
SE TROUVE LE FICHIER JAVA EXÉCUTABLE. 

SANS CE DOSSIER, LE PROGRAMME NE S'EXÉCUTERA PAS!!!

Lorsque vous voulez exécuter l'application, vous devrez fournir la librairie à analyser
dans le dossier "input" créé précédement.

Ex:
C:/Desktop/Test (dossier d'exécution, emplacement à votre discrétion)
            |
            ->   IFT3913-TP1.JAR (.JAR exécutable)
            ->   input (dossier d'input)
                   |
                   ->   jfreechart-master (librairie à analyser)
                                |
                                -> ...

Lors de l'exécution, le programme créera un nouveau dossier nommé "resultat", au même
emplacement que le fichier exécutable.

À la fin de l'exécution, les résultats seront écris dans 2 fichiers .csv, qui seront 
déposés dans le dossier "resultat".
