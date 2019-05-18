#!/bin/bash

#unset toglie un elemento
array=(2 3 4 5)
echo "prima di unset: ${array[@]} lunghezza: ${#array[*]}"
unset array[1]
echo "dopo unset ${array[@]} lunghezza: ${#array[@]}"
echo "Ecco il secondo e terzo elemento di array ${array[1]} ${array[2]}"
