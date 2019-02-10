#Gli operatori validi sull’intero contenuto di un array funzionano anche su di un singolo elemento, sostituendo il simbolo @ (o *) con la sua posizione.
# i due punti : accedono alla determionata posiszione 

#!/bin/bash
messaggio=("CiaoCiao" "Mondo!Mondo!")
#da 1 avanti di 4 / e per il sencondo da 6 avanti di 6 
echo "${messaggio[0]:1:4} ${messaggio[1]:6:6}"
