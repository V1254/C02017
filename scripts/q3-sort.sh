#!/bin/bash

## checks to see if only one argument is passed in.
if [ -z "$1" ] || [ $# != 1 ];
then
	echo "Usage: q3-sort.sh <file>"
	exit 1
fi

# checks for read permissions.
if [ ! -f $1 ];
then
	echo "$1 does not exist"
	exit 1
fi

# gets filename from path/ fileName passed in.
fileName=${1##*/}

# lexicographically reverse sorts in first column.
sort -r -t ';' $1 > "alpha-$fileName"

# reverse sorts based on date in the 3rd column.
sort $1 -r -t ';' -M -k3  > "date-$fileName"
