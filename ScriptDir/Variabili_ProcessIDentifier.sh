#!/bin/bash
# Altre due variabili principali che vengono impostate automaticamente dall’ambiente Bash sono $$ e $!.
# Entrambe rappresentano numeri di processi. Nella variabile $$ viene memorizzato il PID (Process IDentifier) della shell in esecuzione.
# Questo può essere utile per creare file temporanei, come /tmp/my-script.$$,
# un’operazione piuttosto comune se più istanze dello script possono essere eseguite allo stesso tempo, ciascuna con i propri file temporanei. 
# La variabile $!, invece, corrisponde al PID dell’ultimo processo eseguito in background.
# Essa è utile per tener traccia del processo mentre questo continua a svolgere il proprio lavoro.

echo attuale processo: $$
