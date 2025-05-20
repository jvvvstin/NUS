#include <stdio.h>

void display(int);

int main() {
	int ageArray[] = { 2, 15, 4, 3 };
	display(ageArray[0]);
  int size = sizeof(ageArray) / sizeof(ageArray[0]);
  printf("Size of the array is %d\n", size);
//	printf("\n%d\n", ageArray[0]);
//  printf("%d\n", *ageArray);
  
//  printf("%p\n", &ageArray);
//  printf("%p\n", &ageArray[0]);

  return 0;
}

void display(int age) {
	printf("%d\n", age);
}
