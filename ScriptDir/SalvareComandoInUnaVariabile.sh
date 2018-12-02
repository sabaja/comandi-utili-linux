#! /bin/bash

#Quest operazione è conosciuta come espasione di variable, ed è molto più flessibile di come ci si aspetti. Ad esempio, si può anche memorizzare il nome del comando da eseguire:
cmd_to_run=echo

${cmd_to_run} Hello World

$cmd_to_run "Questo è un errore:"

x="Hello"
expr $x + 1

$cmd_to_run "Questo no:"

y=1
expr $y + 2

MESSAGGIO="Hello World"
MESSAGGIO_BREVE=hi
NUMERO=1
PIGRECO=3.142
PIGRECOALT="3.142"
MIXED=123abc

echo "È possibile impostare dinamicamente il valore di una variabile utilizzando il comando read;"

echo Come ti chiami? Scrivi il tuo nome
read NOME
echo "Ciao $NOME - tutto ok?"
