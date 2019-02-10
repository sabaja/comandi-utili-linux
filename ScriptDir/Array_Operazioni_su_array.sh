# A questo punto è bene passare in rassegna le operazioni più comuni da poter effettuare su un array. 
# Per fare riferimento al suo intero contenuto si utilizza @ o * come indice; 
# così facendo, negli ultimi due esempi presentati, è possibile sostituire 
# l’ultima riga con la notazione più semplice echo "${messaggio[@]}". 
# Il simbolo # viene anteposto a quest’ultima # notazione per conoscere la lunghezza dell’array 
# (cioè il numero di elementi dichiarati effettivamente contenuti in esso), in questo modo:

#!/bin/bash
array=(2 3 4 5)
echo "array contiene ${#array[@]} elementi."
echo "gli elementi dell'array sono ${array[@]}"
echo "Il secondo e terzo elemento di array sono ${array[@]:1:2}"
