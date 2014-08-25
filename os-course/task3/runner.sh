gcc -std=c99 -o checkmod checkmod.c
echo -ne '1000 r test.txt\0' > input
sudo ./checkmod < input
#rm checkmod
