#!/bin/bash
echo "Benvenuti $1 e $2!"

#https://www.html.it/pag/52039/variabili-built-in/
#È possibile anche riferirsi a tutti gli argomenti in una volta utilizzando $@
#che espande tutti i parametri posizionali rispettandone l’ordine. 
#Quando viene posizionato fra virgolette ("$@") ogni argomento diventa una parola separata; 
#questo può rivelarsi utile in combinazione con il comando built-in shift
#che rimuove il primo parametro posizionale
#in modo che $2 diventi $1
#$3 $2 e così via. Ad esempio
#possiamo modificare lo script precedente in questo modo:


echo "Benvenuto $1!"
shift
echo "Benvenuti anche a voi $@!"
echo "il file in esecuzione si chiama $0"
echo "il processo in esecuzione è $$"
echo "ultimo processo eseguito in background $!"


#IFS = Internal Field Separator
old_IFS="$IFS"
IFS=:
echo "Inserisci tre dati separati da due punti ..."
read x y z
IFS=$old_IFS
echo "x è $x y è $y z è $z"
