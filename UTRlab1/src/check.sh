#! /bin/bash
for dire in ./testovi/*
do
	
difs="$(java lab1 < $dire/test.a | diff $dire/test.b -)"

		
	if [ "$difs" = "" ];
	then
		echo "$dire : [OK]"
	else
		
		echo "$dire : "
		echo $difs
	fi	

done

