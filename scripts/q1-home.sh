#!/bin/bash
if [ "${PWD}" = "${HOME}" ];
then
	echo "In HOME directory"
else 
	echo "Not in HOME directory"
fi
