#!/bin/bash
#https://stackoverflow.com/questions/9011233/for-files-in-directory-only-echo-filename-no-path
for filename in ~/Scrivania/COMANDI\ UTILI/ScriptDir/*.sh; do
	echo "nomefile: " `basename "$filename"`
        cat "$filename"
	echo -ne "********************************************\n"
    done
