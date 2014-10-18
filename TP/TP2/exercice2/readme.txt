Adrien Fabre M1 IL - TP2 XML

Exercice 2

Vous trouverez avec ces réponses le fichier .dtd fourni dans le sujet ainsi qu'un fichier .xml créé pour tester mes requetes.

a) Toutes les compositions
//composition

b) Toutes les compositions ayant un seul 'soloist'
//performance[count(soloist)=1]/composition

c) Toutes les performances avec un seul "orchestra" mais pas de "soloist"
//performance[count(soloist)=0][count(orchestra)=1]

d) Tous les solistes ayant joué avec le London Symphony Orchestra sur un CD publié par Deutsche Grammophon

//CD[publisher='Deutsche Grammophon']/performance[orchestra='London Symphony Orchestra']/soloist

e) Tous les CDs comportant des performances du London Symphony Orchestra
//CD[performance/orchestra='London Symphony Orchestra']




