#include <stdio.h>

int main(void) {
  int fahrenheit;
  double celcius;
  scanf("%d", &fahrenheit);
  celcius = (fahrenheit - 32) * 5.0 / 9.0;
  printf("%.2f\n", celcius);
  return 0;
}
