#!/bin/bash

cd /home/sabaja/Scrivania/Nuova_cartella
ls -t | sed -e '1,10d' | xargs -d '\n' | rm -f

echo DONE

exit 0

