#!/bin/sh

#Uso l'opzione -n per non nadare a capo
echo -n "Come ti chiami? "

#leggo dallo standard input il valore digitato 
#dall'utente e lo assegno alla variabile TUO_NOME
read TUO_NOME

echo "Quanti anni hai? "
read YEAR


#Stampo a video il saluto personalizzato
echo "Ciao "${TUO_NOME} " int: " ${YEAR}

#svuotare una variabile del suo contenuto useremo:
unset TUO_NOME

echo "Dopo unset nome: "${TUO_NOME}" int: " 
