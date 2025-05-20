# arrayCount.asm
  .data 
arrayA: .word 1, 0, 2, 0, 3, 6, 7, 8   # arrayA has 8 values
count:  .word 999             # dummy value

  .text
main:
    # code to setup the variable mappings
    la   $t0, arrayA	# map arrayA to $t0
    la   $t8, count	# load address of count to $t8
    lw   $t8, 0($t8)	# map value of count to $t8 
    add  $t1, $0, $0	# initialise counter value = 0
    addi $t2, $0, 8	# initialise size of array = 8
    add  $t3, $0, $0	# initialise i = 0

l1:
    beq  $t3, $t2, cont # exits loop if i == size of arrayA
    sll  $t5, $t3, 2	# $t5 = i * 4
    add  $t6, $t5, $t0	# $t6 = $t5 + base addr of arrayA    
    li   $v0, 5    	# system call code for read_int
    syscall	   	# read user input
    sw   $v0, 0($t6)	# store user input into arrayA[I]
    add  $t3, $t3, 1	# increment i by 1
    j    l1		
    
cont:
    # code for reading in the user value X
    li   $v0, 5    	# system call code for read_int
    syscall	   	# read user input
    addi $t4, $v0, -1   # store user input (mask) into $t4
    add  $t3, $0, $0	# initialise i = 0

    # code for counting multiples of X in arrayA
loop:
    beq  $t3, $t2, exit # exits loop if i == size of arrayA
    sll  $t5, $t3, 2	# $t5 = i * 4
    add  $t6, $t5, $t0	# $t6 = $t5 + base addr of arrayA
    lw	 $t7, 0($t6)	# load element i of arrayA into $t7
    
    and  $t9, $t7, $t4	# calculates if element i is a multiple of X
    beq  $t9, $0, multiple	# branch if element i is a multiple of X
    addi $t3, $t3, 1	# increment i by 1 
    j loop 

multiple:
    addi $t1, $t1, 1 	# increment count by 1
    addi $t3, $t3, 1	# increment i by 1
    j loop   

exit:  
    # code for printing result
    la $t8, count	# load address of count to $t8
    sw $t1, 0($t8)	# store value of count into address of count
    li   $v0, 1    	# system call code for print_int
    add  $a0, $t1, $0   # move count from $t1 to $a0 to be printed
    syscall        	# print the integer

    # code for terminating program
    li  $v0, 10
    syscall
