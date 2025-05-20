# messages.asm
  .data 
str: .asciiz "the answer = "
  .text

main: 
    li   $v0, 5    	# system call code for read_int
    syscall	   	# read user input
    add  $t0, $v0, $0   # move result from $v0 to $t0 to be printed

    li   $v0, 4    	# system call code for print_string
    la   $a0, str    	# address of string to print
    syscall        	# print the string

    li   $v0, 1    	# system call code for print_int
    add  $a0, $t0, $0   # move result from $t0 to $a0 to be printed
    syscall        	# print the integer
    
    li   $v0, 10   # system call code for exit
    syscall        # terminate program
