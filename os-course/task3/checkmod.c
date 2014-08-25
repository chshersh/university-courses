#define _GNU_SOURCE
#include <unistd.h>
#include <sys/types.h>
#include <stdlib.h>
#include <fcntl.h>
#include <stdio.h>
#include <string.h>

const int BLOCK_SIZE = 1024;
const int UID_LEN = 10;
const int FLAGS_LEN = 2;

void crash(const char* msg) {
    printf("%s\n", msg);
    exit(EXIT_FAILURE);
}

int main(int argc, char *argv[]) {
    char buf[BLOCK_SIZE];
    char uid_buf[UID_LEN];
    int uid_len = 0;
    int uid;

    char flags_buf[FLAGS_LEN + 1];
    int flags_len = 0;
    int flags;

    char file_buf[BLOCK_SIZE];
    int file_len = 0;

    int chars_read, pos = 0;
    int parse_state = 0;

    while ((chars_read = read(0, buf + pos, BLOCK_SIZE - pos)) >= 0) {
        for (int i = pos; i < pos + chars_read; ++i) {
            int end_parse = 0;
            if (buf[i] == '\0') {
                if (parse_state != 2) {
                    crash("Invalid arguments");
                }
                end_parse = 1;
            }
            if (parse_state == 0) {
                if (buf[i] == ' ') {
                    parse_state = 1;
                    uid_buf[uid_len] = '\0';

                    int sres = sscanf(uid_buf, "%d", &uid);
                    if (sres == 0 || sres == EOF) {
                        crash("Cannot parse uid");
                    }

                    sres = seteuid(uid);
                    if (sres == -1) {
                        crash("Cannot set given uid");
                    }
                    uid_len = 0;
                } else {
                    if (uid_len == UID_LEN) {
                        crash("Too big uid");
                    }
                    uid_buf[uid_len++] = buf[i];
                }
            } else if (parse_state == 1) {
                if (buf[i] == ' ') {
                    parse_state = 2;
                    flags_buf[flags_len] = '\0';
                    if (!strcmp(flags_buf, "r")) {
                        flags = O_RDONLY;
                    } else if (!strcmp(flags_buf, "w")) {
                        flags = O_WRONLY;
                    } else if (!strcmp(flags_buf, "rw")) {
                        flags = O_RDWR;
                    } else {
                        crash("Unrecognized flag");
                    }
                    flags_len = 0;
                } else {
                    if (flags_len == FLAGS_LEN) {
                        crash("flag is too big");
                    }
                    flags_buf[flags_len++] = buf[i];
                }
            } else { // parse_state == 2
                if (buf[i] == ' ' || buf[i] == '\0') {
                    parse_state = 1;
                    file_buf[file_len] = '\0';
                    int fd = open(file_buf, flags);
                    if (fd == -1) {
                        crash("Cannot open file");
                    }

                    int cres = close(fd);
                    if (cres == -1) {
                        crash("Cannot close file");
                    }

                    file_len = 0;
                } else {
                    if (file_len == BLOCK_SIZE) {
                        crash("File is too big");
                    }
                    file_buf[file_len++] = buf[i];
                }
            }
            if (end_parse) {
                write(1, "OK\n", 3);
                parse_state = 0;
            }
        }

        pos += chars_read;
        if (pos == BLOCK_SIZE) {
            pos = 0;
        }
    }
    if (chars_read == -1) {
        crash("Cannot read from fd");
    }
    return EXIT_SUCCESS;
}
