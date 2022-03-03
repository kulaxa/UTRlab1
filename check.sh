#! /bin/bash


for dire in ./test/*
do
#	echo  $dire
  java -classpath "C:\Users\filip\IdeaProjects\UTRlab1\out\production\labos1" lab < "$dire/test.a"

  var="$(diff output.txt  "$dire/test.b" )"

  if [ "$var" = "" ]
    then echo "$dire OK"
  else
    echo "$dire"
    echo $var
  fi
done


