#include <stdio.h>

int main(void) {
  int celcius; 
  double fahrenheit;
  scanf("%d", &celcius);
  fahrenheit = celcius * 9.0 / 5.0 + 32;
  printf("%.2f\n", fahrenheit);
  return 0;
}
