#! /bin/bash

#for test in /home/mario/Dropbox/Faks/UTR/Zadaće\ i\ labosi/lab1/testniPrimjer/*
#do
 #   echo "$test"
  #  java -jar "/home/mario/Dropbox/Faks/UTR/Zadaće i labosi/lab1/UTRlab1.jar" < "$test"
   # diff rjesenja.txt ./testniRezultati/rez.txt
#done

for dire in ./testovi/*
do
	echo  $dire
	java -jar "/home/mario/Dropbox/Faks/UTR/Zadaće i labosi/lab1/UTRlab1.jar" < "$dire/test.a"
	diff rjesenja.txt  "$dire/test.b"

done

