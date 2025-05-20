#include <stdio.h>

int main(void) {
  int number;
  printf("Enter a number: ");
  scanf("%d", &number);
  if (number < 0) {
    number *= -1;
  }
  printf("Absolute value: %d\n", number);
  return 0;
}
