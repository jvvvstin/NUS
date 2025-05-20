#include <stdio.h>
#include <ctype.h>
#include <string.h>

#define BUFLEN 1024

int isPalindrome(char *, int);

int isPalindrome(char *str, int len) {
  int start = 0;
  int end = len - 1;

  while (start < end) {
    if (tolower(str[start]) != tolower(str[end])) {
      return 0;
    }
    start++;
    end--;
  }
  return 1;
}

int main() {
  char str[BUFLEN];
  int len;

  printf("Enter a string: ");
  fgets(str, BUFLEN,stdin);

  len = strlen(str);
  
  if (str[len - 1] == '\n') {
    str[len - 1] = '\0';
    len--;
  }

  if (isPalindrome(str, len)) {
    printf("YES it is a palindrome\n");
  } else {
    printf("NO it is not a palindrome\n");
  }
}
