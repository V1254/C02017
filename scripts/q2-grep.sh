# walk through parameters and grep them if they exist in the current working directory
while [ "$1" != "" ];
do 
	result=$(grep --with-filename -A 1 -B 1 "$USER" "$1") 
	echo "$result" && shift
done

