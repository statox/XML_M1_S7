Adrien Fabre M1 - IL

Rapport TP1 XML - 11/10/2014

Exercice 3 - Snoopy


Afin de limiter à 40 caractères le titre, l'auteur et le nom du personnage nous avons créé le type limitedCharacterType.
C'est un xsd:simpleType car il ne contiendra qu'une chaine de caractère mais à laquelle la restriction "xsd:maxLength" est appliquée.
De plus on fait hériter ce type de xsd:normalizedString et non de xsd:string, ce n'est pas demandé par le sujet mais c'est pour
éviter de voir des caractères spéciaux dans ces champs.

La description des amis des personnages est également du type limitedCharacterType pour répondre au critère de 40 caractères.
Ce champs friend-of n'apparait pas toujours, ce qui explique la présence de minOccurs="0" dans la déclaration des elements
de la sequence de character_type (ligne 42) et le sujet précisant qu'un personnage peut avoir plusieurs amis sans préciser
de limite nous avons mis maxOccurs="unbounded" pour laisser ce champ être répété un nombre infini de fois si besoin.

L'élément since devant être une date, nous avons simplement utilisé le champ xsd:date qui correspond parfaitement aux critères
recherchés.

Enfin le numéro ISBN du livre doit être composé obligatoirement de 10 chiffres.
Pour celà nous avons créé le type limitedDigitsNumber. Ce type est basé sur une chaine de caractère à laquelle nous avons 
ajouté comme contrainte de correspondre à un pattern. (ligne 34)
Ce patern est le suivant: [0-9]{10}.
La partie entre crochets indique que les seuls caractères présents dans la chaine doivent être des chiffres et la partie
entre accolade indique qu'il doit absolument y avoir 10 caractères dans cette chaine.
