# walk through parameters and grep them if they exist in the current working directory
while [ "$1" != "" ];
do 
	result=$(grep --with-filename -C 1 "$USER" "$1") 
	echo "$result" && shift
	## after shifting check if next element is empty, print dash only if it isn't.
	if [ "$1" != "" ];
	then
		echo "--"
	fi
done

