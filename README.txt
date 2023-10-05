Lilou Blanchette matricule: 20188851
William D'Anjou matricule: 20188213

Lien vers le repositoire: https://github.com/Benliam12/TP1-IFT3913


Comment compiler et exécuter le code:

Nous avons assumé que tous les fichiers java donnés sont fonctionnels.
Pour exécuter les .jar vous devez avoir au minimum la version 17 de Java.


1-Pour exécuter les fichiers .jar vous devez entrer dans l'invite de commande:
java -jar <le chemin du fichier .jar exécuter> <argument>

Pour le .jar Tloc:
java -jar <le chemin du fichier .jar exécuter> <chemin d'un fichier .java>

Pour le .jar Tassert:
java -jar <le chemin du fichier .jar exécuter> <chemin d'un fichier .java>

Pour le .jar Tls:
java -jar <le chemin du fichier .jar exécuter> <chemin d'un fichier et/ou d'un dossier qui contient du code .java>
java -jar <le chemin du fichier .jar exécuter> -o <chemin d'un fichier d'exportation .csv> <chemin d'un fichier et/ou d'un dossier qui contient du code .java>

Pour le .jar Tropcomp:
Pour ce programme le seuil sera converti en pourcentage. Par exemple: 5 = 5%.
java -jar <le chemin du fichier .jar exécuter> <chemin d'un fichier et/ou d'un dossier qui contient du code .java> <seuil>
java -jar <le chemin du fichier .jar exécuter> -o <chemin d'un fichier d'exportation .csv> <chemin d'un fichier et/ou d'un dossier qui contient du code .java> <seuil>


2- Pour compiler notre code sur IntelliJ:
Vous ouvrez le dossier TP1 à partir d'IntelliJ.
Vous sélectionnez un SDK supérieur ou égal à 17.
Dans le sous-dossier src/main vous pouvez compiler et exécuter les fichiers Tassert, Tloc, Tls et Tropcomp. Pour ce faire, vous devez aussi ajouter les bons arguments dans
la partie Program arguments pour chacune des classes. 

