#include <stdio.h>

double sum_of_square(double[], int);

int main(void) {
  
  return 0;
}

double sum_of_square(double arr[], int size) {
  double sum = 0;

  for (int i = 0; i < size; i++) {
    sum += arr[i] * arr[i];
  }

  return sum;
}
