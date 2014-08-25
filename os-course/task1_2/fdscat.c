#include <string.h>
#include <unistd.h>
#include <string.h>
#include <stdlib.h>
#include <stdio.h>
#include <sys/types.h>

#define min(a,b) ((a) < (b) ? (a) : (b))

const int BLOCK_SIZE = 1024;
const int FDS_POS = 2;
const char* LINE_SEPARATOR = "\n";

void crash(const char* msg) {
    printf("%s\n", msg);
    exit(EXIT_FAILURE);
}

void write_chars(int chars_rest, const char* buf) {
    int total_chars = chars_rest;
    while (chars_rest > 0) {
        int chars_write = write(1, buf + total_chars - chars_rest, chars_rest);
        if (chars_write == -1) {
            crash("An I\\O error occured while writing");    
        }
        chars_rest -= chars_write;
    }
}

// find next "\n" starting from <code>beg</code> : add some javadoc in c :D
int find_end(const char* buf, int beg, int len) {
    for (int i = beg; i < len; ++i) {
        if (buf[i] == LINE_SEPARATOR[0]) {
            return i;
        }
    }
    return -1;
}

// reverse buf[left, right)
void reverse_buffer(char* buf, int left, int right) {
    for (int i = 0; i < (right - left) / 2; ++i) {
        char c = buf[left + i];
        buf[left + i] = buf[right - i - 1];
        buf[right - i - 1] = c;
    }
}

int main(int argc, char* argv[]) {
    char buf[BLOCK_SIZE], line1[BLOCK_SIZE];
    
    int line1_len = 0;
    int len = strlen(argv[FDS_POS - 1]);
    char* delimiter = argv[FDS_POS - 1];
    
    for (int k = FDS_POS; k < argc; k++) {
        int fd, sres = sscanf(argv[k], "%d", &fd);
        if (sres == EOF || sres == 0) {
            crash("Can't sscanf fd argument");
        }
        
        int buf_off = 0, prev_line_end = 0, line_num = 0;
        while (1) {
            int chars_read = read(fd, buf + buf_off, BLOCK_SIZE - buf_off);
            if (chars_read == -1) {
                crash("An I\\O error occured while reading");
            }
            if (chars_read == 0) {
                break;
            }

            int end;
            while ((end = find_end(buf, prev_line_end, buf_off + chars_read)) != -1) {
                if (!line_num) {
                    line1_len = end - prev_line_end;
                    memcpy(line1, buf + prev_line_end, line1_len);
                    reverse_buffer(line1, 0, line1_len);
                    line_num = 1;
                } else {
                    line_num = 0;
                    reverse_buffer(buf, prev_line_end, end);
                    
                    write_chars(end - prev_line_end, buf + prev_line_end);
                    write_chars(strlen(LINE_SEPARATOR), LINE_SEPARATOR);
                    write_chars(line1_len, line1);
                    write_chars(strlen(LINE_SEPARATOR), LINE_SEPARATOR);
                }
                prev_line_end = end + 1;
            }

            if (prev_line_end == 0 && buf_off + chars_read >= BLOCK_SIZE) {
                crash("String too big");
            }
            
            if (buf_off + chars_read < BLOCK_SIZE) {
                buf_off = buf_off + chars_read;
            } else {
                buf_off = BLOCK_SIZE - prev_line_end;
                memmove(buf, buf + prev_line_end, buf_off);
                prev_line_end = 0;
            }
        }
        if (line_num) {
            line_num = 0;
            write_chars(line1_len, line1);
            write_chars(strlen(LINE_SEPARATOR), LINE_SEPARATOR);
        }

        if (k < argc - 1) {
            write_chars(len, delimiter);
        }

        int cres = close(fd);
        if (cres == -1) {
            crash("Cannot close fd");
        }
    }
    
    return EXIT_SUCCESS;
}
