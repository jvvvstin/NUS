#include <stdio.h>
#define KMS_PER_MILE 1.609

int main(void) {
  float miles, kms;

  printf("Enter distance in miles: ");
  scanf("%f", &miles);

  kms = miles * KMS_PER_MILE;

  printf("That equals %9.2f km.\n", kms);

  return 0;
}
