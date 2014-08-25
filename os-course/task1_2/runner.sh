gcc -std=c99 -o asfds asfds.c
gcc -std=c99 -o fdscat fdscat.c
./asfds "###" ./fdscat  asfds.c fdscat.c
#yes yes | ./fdscat '###' 0
rm asfds fdscat
