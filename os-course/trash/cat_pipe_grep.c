#include <unistd.h>

int main(int argc, char** argv) {
    int fds[2];
    pipe(fds);

    if (fork()) {
        dup2(fds[0], 0);
        close(fds[0]);
        close(fds[1]);
        execlp("grep", "grep", "mouse", NULL);
    } else {
        dup2(fds[1], 1);
        close(fds[0]);
        close(fds[1]);
        execlp("cat", "cat", "cat_pipe_grep.c", NULL);
    }
    write(2, "error\n", 5);
    return 0;
}

