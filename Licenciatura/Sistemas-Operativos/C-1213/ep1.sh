#!/bin/bash
if [ $# -eq 0 ]
then 
    echo "Introduza os 3 argumentos"

elif [ $# -lt 3 ]
then
    echo "Argumentos em falta"

elif [[ $1 != "hist" &&  $1 != "contar" ]]
then
    echo "Primeiro argumento invalido"
   
elif [[ $2 != "palavras" &&  $2 != "bytes" ]]
then
    echo "Segundo argumento invalido"
fi


if [[ $1 = "contar"  &&   $2 = "palavras" ]]
then
    cd $3
    for i in `ls`
    do
	if [ -f $i ]
	then
	    echo "$i" : `cat $i | wc -w` "palavras"
	fi
    done
fi

if [[ $1 = "contar"  &&   $2 = "bytes" ]]
then
    cd $3
    for i in `ls`
    do
	if [ -f $i ]
	then
	    echo "$i" : ` cat $i | wc -c` "bytes"
	fi
    done
fi
 #| grep -o [0-9]


if [[ $1 = "hist"  &&   $2 = "palavras" ]]
then
    cd $3
    for i in `ls`
    do
	if [ -f "$i" ]
	then
		palavras=`cat $i | wc -w`
		echo -e "$i :\c"
		for (( x=0 ; x<palavras ; x++ ))
		do
		    	echo -e "X\c"
		done
		echo
	fi
    done
fi

if [[ $1 = "hist"  &&   $2 = "bytes" ]]
then
    cd $3
    for i in `ls`
    do
	if [ -f "$i" ]
	then
		bytes=`cat $i | wc -c`
		echo -e "$i :\c"
		for (( x=0 ; x<bytes ; x++ ))
		do
	 	   	echo -e "X\c"
		done
		echo
	fi
    done
fi
