#!/bin/sh

cd ~/Scrivania/Nuova_cartella
find . -mtime +0 -type f -delete
ls -l

echo DONE

exit 0

