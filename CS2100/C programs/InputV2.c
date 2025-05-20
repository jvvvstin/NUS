#include <stdio.h>
int main(void) {
  int age;
  double cap;

  printf("What is your age and CAP? ");
  scanf("%d %lf", &age, &cap);
  printf("You are %d years old and your CAP is %f\n", age, cap);

  return 0;
}
