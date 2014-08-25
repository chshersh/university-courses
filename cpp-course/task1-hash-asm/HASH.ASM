	org 100h

	call precalc	;предпосчёт хэшей

	call input	;ввод пар

	call output	;вывод результата

	mov ax, 4C00h	;exit
	int 21h

precalc: ;предпосчёт хэшей
  mov al, 1
  mov bl, 29
  mov di, hash	      ;адрес начала массива хэшей
  mov cx, 63
  mov [di], al

  lp_pr:
    inc di
    mul bl
    mov [di], al
    loop lp_pr

  ret

;ввод пар key-value
input:
  part1:
    mov si, cur_str	  ;считываем ключ
    xor bx, bx		  ;длина строки
    mov ah, 1		  ;дос команда считывание

  read_key:
    int 21h
    cmp al, 0Dh 	   ;если конец строки,
    je end_of_reading	  ;то переходим к выводу
    cmp al, 32		  ;если пробел,
    je get_hash 		;то считаем хэш
    mov [si], al
    inc si
    inc bx
    jmp read_key

  part2:
    mov si, cur_str	;теперь здесь будет значение
    xor bx, bx
    mov ah, 1

  read_value:
    int 21h
    cmp al, 0Dh 	  ;если конец строки,
    je add_to_map	  ;то добавляем значение
    mov [si], al
    inc si
    inc bx
    jmp read_value

  end_of_reading:
    mov ax, 1		;идём к print
    mov [met], ax
    ret

;получить хеш строки
get_hash:
  mov cx, bx		;длина строки в cx
  mov si, cur_str
  mov bx, hash		;в bx адрес хеша
			;и в цикле значение его
  xor di, di
  ;xor ax, ax
  ;bl += (str[i] - 'a' + 1) * hash[i]
  calc:
    mov al, [si]
    sub al, 'a'
    inc al

    push bx
    mov bx, [bx]
    mul bl
    pop bx

    inc si
    inc bx

    add di, ax
    and di, 0x00FF	;di %= 256
    loop calc

  push di		;хеш в di

  mov ax, [met]       ;если пришли из print'a
  cmp ax, 1
  je back_to_print

  jmp part2

;добавить значение в hashmap по ключу
add_to_map:
  mov cx, bx
  mov si, cur_str
  mov bx, map
  mov al, [max_str_size]
  pop dx
  mul dl		;в ax начало места строки в хеше
  add bx, ax

  mov di, mas_str_size	;m_s_s[dx] = cl
  add di, dx		;длина строки с хешем dx
  mov [di], cl

  write:
    mov al, [si]
    mov [bx], al
    inc bx
    inc si
    loop write

  jmp part1

;вывод значений на экран
output:
  mov ah, 1
  mov si, cur_str
  xor bx, bx
  int 21h
  cmp al, 0Dh		 ;если конец строки, то завершаем
  je the_end
  mov [si], al
  inc si
  inc bx

  read_key_1:
    int 21h
    cmp al, 0Dh
    je print
    mov [si], al
    inc si
    inc bx
    jmp read_key_1

  the_end:
    ret

;вывести значение из map'a по ключу
print:
  jmp get_hash
back_to_print:
  pop dx		;хеш в dx(точнее dl)
  mov bx, map
  mov al, [max_str_size]
  mul dl
  add bx, ax

  mov di, mas_str_size
  add di, dx
  xor cx, cx
  mov cl, [di]
  cmp cx, 0
  jz print_err

  mov ah, 02h
  print_char:
    mov dl, [bx]
    int 21h
    inc bx
    loop print_char

  mov ah, 09h
  mov dx, crlf
  int 21h

  jmp output

print_err:
  mov ah, 09h
  mov dx, err_msg
  int 21h
  jmp output

;#
hash: rb 64	     ;хэши
cur_str: rb 64	     ;текущая строка
mas_str_size: rb 256 ;массив длин строк в map'е
map: rb 16384	     ;массив ключей, 64 * 256
met: dw 0	     ;сигнал о том, что сейчас читаем только ключи
max_str_size: db 64  ;сдвиг на одну строку
err_msg: db 'No value with such key', 13, 10, '$'
crlf: db 13, 10, '$'



