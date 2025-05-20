# arrayFunction.asm
       .data 
array: .word 8, 2, 1, 6, 9, 7, 3, 5, 0, 4
newl:  .asciiz "\n"

       .text
main:
	# Print the original content of array
	# setup the parameter(s)
	la   $a0, array
	addi $a1, $zero, 10

	# call the printArray function
	jal  printArray

	# Ask the user for two indices
	li   $v0, 5         	# System call code for read_int
	syscall           
	addi $t0, $v0, 0    	# first user input in $t0
 
	li   $v0, 5         	# System call code for read_int
	syscall           
	addi $t1, $v0, 0    	# second user input in $t1

	# Call the findMin function
	# setup the parameter(s)
	la   $a0, array
	sll  $t1, $t1, 2	# multiply the user input Y by 4
	add  $a1, $a0, $t1	# a1 = address of A[Y]
	sll  $t8, $t0, 2	# multiply the user input X by 4
	add  $a0, $a0, $t8	# $a0 = address of A[X]

	# call the function
	jal  findMin

	# Print the min item
	# place the min item in $t3 for printing
	add  $t9, $v0, $zero
	lw $t3, 0($t9)

	# Print an integer followed by a newline
	li   $v0, 1   		# system call code for print_int
    	addi $a0, $t3, 0    	# print $t3
    	syscall       		# make system call

	li   $v0, 4   		# system call code for print_string
    	la   $a0, newl    	# 
    	syscall       		# print newline

	#Calculate and print the index of min item
	la   $a0, array		# address of A
	sub  $t4, $t9, $a0	# index of min element (in word)
	
	# Place the min index in $t3 for printing
	srl  $t3, $t4, 2	# index of min element	

	# Print the min index
	# Print an integer followed by a newline
	li   $v0, 1   		# system call code for print_int
    	addi $a0, $t3, 0    	# print $t3
    	syscall       		# make system call
	
	li   $v0, 4   		# system call code for print_string
    	la   $a0, newl    	# 
    	syscall       		# print newline
	
	# End of main, make a syscall to "exit"
	li   $v0, 10   		# system call code for exit
	syscall	       		# terminate program
	

#######################################################################
###   Function printArray   ### 
#Input: Array Address in $a0, Number of elements in $a1
#Output: None
#Purpose: Print array elements
#Registers used: $t0, $t1, $t2, $t3
#Assumption: Array element is word size (4-byte)
printArray:
	addi $t1, $a0, 0	# $t1 is the pointer to the item
	sll  $t2, $a1, 2	# $t2 is the offset beyond the last item
	add  $t2, $a0, $t2 	# $t2 is pointing beyond the last item
l1:	
	beq  $t1, $t2, e1
	lw   $t3, 0($t1)	# $t3 is the current item
	li   $v0, 1   		# system call code for print_int
    	addi $a0, $t3, 0   	# integer to print
    	syscall       		# print it
	addi $t1, $t1, 4
	j    l1				# Another iteration
e1:
	li   $v0, 4   		# system call code for print_string
    	la   $a0, newl    	# 
    	syscall       		# print newline
	jr   $ra		# return from this function


#######################################################################
###   Student Function findMin   ### 
#Input: Lower Array Pointer in $a0, Higher Array Pointer in $a1
#Output: $v0 contains the address of min item 
#Purpose: Find and return the minimum item 
#              between $a0 and $a1 (inclusive)
#Registers used: $t0, $t1, $t2, $t3, $v0
#Assumption: Array element is word size (4-byte), $a0 <= $a1
findMin:
	addi  $t1, $a0, 0	# set $t1 to be lower array pointer
	addi  $t2, $a1, 4	# set $t2 to be beyond high array pointer
	lw    $t4, 0($t1)	# set min to be first element
	add   $v0, $zero, $t1	# set address of min
loop:
	beq   $t1, $t2, exit
	lw    $t3, 0($t1)	# t3 = current value of $t1	
	slt   $t0, $t4, $t3	# if min < $t3 (current element), set $t0 = 1
	bne   $t0, $zero, skip	# if min < $t3 (current element), branch to skip
	add   $v0, $zero, $t1	# min = $t1 (address of current element)
	add   $t4, $zero, $t3

skip:
	addi  $t1, $t1, 4	# increment current lower array pointer
	j loop			# re-loop
exit:
	jr $ra			# return from this function
