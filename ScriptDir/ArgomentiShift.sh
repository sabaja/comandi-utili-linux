#!/bin/bash
echo "Benvenuto $1!"
echo "siete $@"
#https://www.computerhope.com/unix/bash/shift.htm
shift
echo "Benvenuti anche a voi $@!"

