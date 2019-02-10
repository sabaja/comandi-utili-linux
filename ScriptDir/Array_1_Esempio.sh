#!/bin/bash
messaggio[0]="Ciao"
messaggio[1]="Mondo"
echo -n "messaggio[0] = "
echo ${messaggio[0]}
echo -n "messaggio[1] = "
echo ${messaggio[1]}
messaggio[1]="Mondo!"
messaggio[2]="${messaggio[0]} ${messaggio[1]}"
echo ${messaggio[2]}
