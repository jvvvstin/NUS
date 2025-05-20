#include <stdio.h>
#define MAX 10

int readArray(int [], int);
void printArray(int [], int);
void reverseArray(int [], int);
void reverseArrayRec(int [], int, int);
void reverseArrayAlt(int [], int);

int main(void) {
	int array[MAX], numElements;

	numElements = readArray(array, MAX);
	//reverseArray(array, numElements);
	reverseArrayAlt(array, numElements);
  printArray(array, numElements);

	return 0;
}

int readArray(int arr[], int limit) {
	// ...
	printf("Enter up to %d integers, terminating with a negative integer.\n", limit);
	// ...
  int i, num;
  i = 0;
  scanf("%d", &num);
  while (num >= 0 && i < MAX) {
    arr[i] = num;
    i++;
    if (i == MAX) {
      break;
    }
    scanf("%d", &num);
  }
  return i;
}

void reverseArray(int arr[], int size) {
	// ...
  int tempArr[size];
  for (int i = 0; i < size; i++) {
    tempArr[i] = arr[size - i - 1];
  }

  for (int i = 0; i < size; i++) {
    arr[i] = tempArr[i];
  }
}

void reverseArrayRec(int arr[], int left, int right) {
  int temp;

  if (left < right) {
    temp = arr[left];
    arr[left] = arr[right];
    arr[right] = temp;

    reverseArrayRec(arr, left + 1, right - 1);
  }
}

void reverseArrayAlt(int arr[], int size) {
  reverseArrayRec(arr, 0, size - 1);
}

void printArray(int arr[], int size) {
	int i;

	for (i=0; i<size; i++) {
		printf("%d ", arr[i]);
	}
	printf("\n");
}
