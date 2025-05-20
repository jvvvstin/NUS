#include <stdio.h>

int main() {
  int exit = 0;
  float num1, num2;
  while (!exit) {
    char opr;
    printf("Please enter an operation (+, -, *, / or q to quit): ");
    scanf("%c", &opr);
    if (opr == '+' || opr == '-' || opr == '*' || opr == '/' || opr == 'q') {
      if (opr == 'q') {
        printf("\nBye!\n");
        exit = 1;
        break;
      } else {
        printf("\nEnter the first number: ");
        scanf("%f", &num1);
        printf("\nEnter the second number: ");
        scanf("%f", &num2);
        float result;
        if (opr == '+') {
          result = num1 + num2;
        } else if (opr == '-') {
          result = num1 - num2;
        } else if (opr == '*') {
          result = num1 * num2;
        } else {
          result = num1 / num2;
        }
        printf("\n%.2f %c %.2f = %.2f\n\n", num1, opr, num2, result);
      }
    } else {
      printf("\nUnrecognized operation\n\n");
    }
    fflush(stdin);
  }
}
