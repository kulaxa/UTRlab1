#! /bin/bash
for dire in ./testovi/*
do
	
difs="$(java --class-path '/home/mario/Dropbox/Faks/UTR/Zadaće i labosi/UTRlab1/UTRlab1/src' lab1 < $dire/test.a | diff $dire/test.b -)"

		
	if [ "$difs" = "" ];
	then
		echo "$dire : [OK]"
	else
		
		echo "$dire : "
		echo $difs
	fi	

done

