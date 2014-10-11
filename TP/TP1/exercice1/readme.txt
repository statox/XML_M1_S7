Adrien Fabre M1 - IL

Rapport TP1 XML - 11/10/2014

Exercice 1 - Au cinéma

1) cf. film_brigades_de_tigre.xml

2) Pour décrire l'ensemble du programme d'un cinéma il faudrait ajouter au document un élément racine qui pourrait etre <films></films> et qui contiendrait plusieurs arbres du même genre que celui présent dans le fichier précédent:
<films>
  <film titre="Les Brigades du Tigre" duree="125" genre="Policier" annee= 2005" langue="VF">
    ...
  </film>

  <film titre="The Shinning" duree="120" genre="Horreur" annee="1980" langue="VO">
    ...
  </film>
</films>

3) cf. film.dtd

4) La dtd d'un arbre représentant un programme de cinéma contenant plusieurs films serait la suivante

<!ELEMENT films (film*)>  Ce qui indique que la racine de l'arbre contient 0 ou plusieurs repertoires films

Afin que la dtd accepte les elements decrivant les films dans n'importe quel ordre on a modifié la ligne dans film.dtd:

<!ELEMENT film  (notes, genres, realisateur , acteurs , description , horaires )> 

par la ligne:

<!ELEMENT film  (notes|genres|realisateur|acteurs|description|horaires)+> 

Pour signifier que chaque information peut apparaitre dans n'importe quel ordre

