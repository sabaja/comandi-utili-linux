#!/bin/sh
#https://www.html.it/pag/51808/utilizzare-le-variabili-2/

echo -en "Come ti chiami? "
read NOME
echo "Il tuo nome è : ${NOME:=`whoami`}"
echo "Tutto ok $NOME?"
