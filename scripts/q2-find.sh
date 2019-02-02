#!/bin/bash
if [ -z "$1" ];
then
	echo 'Error: provide a directory path '
	exit 1
fi

echo "Plain files modified more than 72 hours ago in $1"
for i in $(find "$1" -maxdepth 1 -type f -mmin +4320)
do
	echo "$i"
done

echo "Directories in sub-directories of $1 starting with \"dir\":"
for i in $(find "$1" -type d -name "dir*")
do
	echo "$i"
done








