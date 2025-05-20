#include <stdio.h>

void abs_array(int[], int);

int main(void) {
  int arr[4] = { 3, 5, -2, -9 };
  abs_array(arr, 4);
  for (int i = 0; i < 4; i++) {
    printf("%d\n", arr[i]);
  }
  return 0;
}

void abs_array(int arr[], int size) {
  for (int i = 0; i < size; i++) {
    if (arr[i] < 0) {
      arr[i] *= -1;
    }
  }
}
