#!/bin/sh
#!/bin/bash

#Operazioni matematiche con numeri interi

#Definiamo due variabili numeriche
X=10
Y=4

#Uso di expr tra apici gravi
R1=`expr $X - $Y`

#Uso le doppie parentesi
R2=$((X-Y))

#Uso di let
#let "R3 = X - Y"

#Uso di let
R3=$((X-Y))

#Stampiamo a video i risultati
echo "risultato di expr: "$R1
echo "risultato delle dopie parentesi: "$R2
echo "risultato di let: "$R3
