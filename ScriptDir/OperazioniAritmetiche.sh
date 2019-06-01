#! /bin/bash

#si utilizza la cosiddetta espansione aritmetica, denotata da $(())
echo $((3 * 4 + (6 / 3)))

#esponenziazione (2 ** 4 risulta 16)
echo $((2**4))

#operazione con numero negativo
echo $(( -(2**3) ))

#aritmetica è possibile riferirsi a variabili dichiarate senza l'utilizzo di $
i = 2+3
echo $(( 7 * i ))

#mentre scrivendo $i verrebbe effettuata una sostituzione di stringa, producendo 7 * 2 + 3
echo $(( 7 * $i ))
