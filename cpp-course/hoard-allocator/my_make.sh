g++-4.8 -shared -std=c++11 -pedantic -pthread -fPIC -g -gdwarf-2 -o hoard.so myallocator.cpp heaps.cpp hoard.cpp tracing.cpp
#-g -gdwarf-2

#g++-4.8 -std=c++11 main.cpp -o main
#chmod +x main
#./main
#LD_PRELOAD=./hoard.so MALLOC_INTERCEPT_NO_TRACE=1 ./main
#LD_PRELOAD=./hoard.so ./main

LD_PRELOAD=./hoard.so MALLOC_INTERCEPT_NO_TRACE=1 ls
#+gcalctool
#+eog
#+gnome-terminal
#+software-center
#+-totem
#-gnome-system-monitor

#g++-4.8 -std=c++11 -pthread main2.cpp -o main2
#chmod +x main2
#LD_PRELOAD=./hoard.so MALLOC_INTERCEPT_NO_TRACE=1 ./main2

#g++-4.8 -std=c++11 -pthread main3.cpp -o main3
#chmod +x main3
#LD_PRELOAD=./hoard.so MALLOC_INTERCEPT_NO_TRACE=1 ./main3

#g++ -std=c++11 -pthread -pedantic -g -gdwarf-2 myallocator.cpp heaps.cpp hoard.cpp tracing.cpp -o myallocator
#chmod +x myallocator
#./myallocator
