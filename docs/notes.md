## Every command line option has a option that summarises the basic usage of that command

	``<command> --help/-h to display this information``

## Every command also has a manual page.
 
	`` <command> find ``

## For a more detailed information for a command.
	`` info <command>``


# Simple Commands and what they do.

	`` **ls** - displays the current working directories file list ``
	`` **pwd** -- prints the working directory ``


# Options are of two types Short and Long:

+ short options are preceeded with a '-' and are a single Character in length.
+ They can be concatenated with other short options without the need of more '-'

Example:
	`` ls **-l** ``
	`` ls **-aR** ``

+ Long Options are preceeded with a '--' and are full words.
+ Concatenation of long options required using multiple '--'

Example:
	`` ls **--all**``
	`` ls **--color --dereference**``

+ Anything else is considered an argument.

Example:

	``ls -l **/etc**``
	``ls --color **/var/log/**``
