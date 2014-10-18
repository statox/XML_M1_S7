Adrien Fabre - TP2 XML

exercice 1

1) Tous les nœuds descendants du deuxième nœud "livre"

expression longue
/child::liste/descendant::livre[position() = 2]

expression courte
/liste/livre[position() = 2]

2) Tous les titres des nœuds "livre" frères suivant le premier nœud "livre"

//liste/livre[1]/following-sibling::*


3) Tous les titres des nœuds "livre" frères suivant le deuxième nœud "livre" et chacun de ses descendants respectifs

//liste/livre[2]/following-sibling::*/descendant::*

4) Sélectionner le dernier nœud "livre" du genre "jeu"

/liste/livre[titre[@genre='jeu']][last()]

5) Sélectionner le titre du 2eme livre paru en 2006

/liste/livre[parution=2006][2]
