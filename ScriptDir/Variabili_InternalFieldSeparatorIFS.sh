#! /bin/bash

# Un’altra variabile interessante è IFS, ovvero l’Internal Field Separator, 
# che rappresenta il carattere (o i caratteri) che separa i diversi parametri fra di loro. 
# Il valore di default è SPACE TAB NEWLINE, ma se questo viene modificato è facile mantenerne una copia, 
# così come mostrato nello script che segue:

#!/bin/bash

# newline e altri caratteri "incontrollabili", perciò una buona pratica quella di usare le virgolette nella loro gestione, cioè usare old_IFS="$IFS" invece che old_IFS=$IFS.
old_IFS="$IFS"
IFS=:
echo -e "Inserisci tre dati separati da due punti\n"
#ese: Pippo:Pluto:Topolino
read x y z
IFS=$old_IFS
echo "x è $x y è $y z è $z"
