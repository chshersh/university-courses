#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <wait.h>
#include <sys/types.h>

const int DEFAULT_FD_BUFFER_SIZE = 10; // max digits of fd
const int MIN_REQUIRED_ARGUMENTS = 4;
const int EXEC_PROGRAM_POS = 2;

void crash(const char* msg) {
    printf("%s\n", msg);
    exit(EXIT_FAILURE);
}

int main(int argc, char* argv[]) {
    if (argc < MIN_REQUIRED_ARGUMENTS) {
        crash("Need more arguments");
    }
    
    int FILE_NUMBERS = argc - MIN_REQUIRED_ARGUMENTS + 1;
    int fds[FILE_NUMBERS];
    char* fdscat_argv[argc];
    
    fdscat_argv[0] = argv[EXEC_PROGRAM_POS];
    fdscat_argv[1] = argv[EXEC_PROGRAM_POS - 1];
    for (int i = EXEC_PROGRAM_POS; i < argc - 1; ++i) {
        int fd = open(argv[i + 1], O_RDONLY);
        if (fd == -1) {
            crash("Can't open file");
        }
        
        char* fd_buf = malloc(DEFAULT_FD_BUFFER_SIZE * sizeof(char));
        if (!fd_buf) {
            crash("Can't allocate memory");
        }
        
        int sres = sprintf(fd_buf, "%d", fd);
        if (sres < 0) {
            crash("Number wasn't sprinted");
        }

        fdscat_argv[i] = fd_buf;
        fds[i - EXEC_PROGRAM_POS] = fd;
    }
    fdscat_argv[argc - 1] = NULL;
    
    int child_pid = fork();
    if (child_pid == -1) {
        crash("Fork error");
    }
    
    if (child_pid) {
        int status;
        int wres = waitpid(child_pid, &status, 0);
        if (wres == -1) {
            crash("Can't wait no more");   
        } else if (WEXITSTATUS(status) == EXIT_FAILURE) {
            exit(EXIT_FAILURE);
        }

        for (int i = 0; i < FILE_NUMBERS; ++i) {
            int cres = close(fds[i]);
            if (cres == -1) {
                crash("Error while closing file");
            }
            free(fdscat_argv[i + EXEC_PROGRAM_POS]);
        }
    } else {
        int eres = execv(argv[EXEC_PROGRAM_POS], fdscat_argv);
        if (eres == -1) {
            crash("fdscat wasn't executed");
        }
    }
    return EXIT_SUCCESS;
}
