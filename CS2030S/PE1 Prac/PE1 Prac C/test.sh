#!/usr/bin/env bash
set -o nounset
function control_c() {
  if [ -e "$out" ]
  then
    rm -f "$out"
  fi
}

trap control_c INT

PROG=PE1
if [ ! -e $PROG.class ]
then
  echo "$PROG.class does not exist.  Have you compiled it with make or javac?"
  exit 1
fi

num_failed=0
i=1
while true
do
  if [ -e inputs/$PROG.$i.in ]
  then
    if [ "$(uname)" == "Darwin" ]
    then
      out=$(mktemp -t $PROG)
      gtimeout 5s java "$PROG" < "inputs/$PROG.$i.in" | ghead -c 50MB > "$out"
    else
      out=$(mktemp --suffix=$PROG)
      timeout 5s java "$PROG" < "inputs/$PROG.$i.in" | head -c 50MB > "$out"
    fi
    status="$?"
    if [ "$status" -ne "0" ]
    then
      echo "$PROG: return non-zero status $status for test case $i"
      # cat inputs/$PROG.$i.in
      num_failed=$((num_failed + 1))
    else 
      if [ -e "$out" ] 
      then
        if [ "$(diff -bB "$out" outputs/$PROG.$i.out | wc -l)" -ne 0 ] 
        then
          echo "test $i: ${description[$i]}.. failed"
          #cat inputs/$PROG.$i.in
          num_failed=$((num_failed + 1))
        else
          echo "test $i: ${description[$i]}.. ok"
        fi
        rm -f "$out"
      else
        echo "$PROG: cannot find output file. Execution interrupted?"
        num_failed=$((num_failed + 1))
      fi
    fi
    i=$((i + 1))
  else
    break
  fi
done

if [ $i -eq 1 ] 
then
  echo "$PROG: no test cases found 🤷"
elif [ $num_failed -eq 0 ] 
then
  echo "$PROG: passed everything 🎉"
fi
