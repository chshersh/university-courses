	extern printf
	
	section .text
	global main

set_space:
	or bl, 1000b
	jmp for_done
set_zero:
	or bl, 0100b
	jmp for_done
set_plus:
	or bl, 0010b
	jmp for_done
set_minus:
	or bl, 0001b
	jmp for_done
; al, bl -- not empty
; pre: al contains highest digit of length,
;	   esi points to first digit of length
get_length:
	sub al, '0'
	mov bh, 10
	simple_loop:
		inc esi
		mov cl, [esi]
		test cl, cl
		jz end_loop
		
		mul bh	; ah:al *= bh
		sub cl, '0'
		add al, cl
		jmp simple_loop
	end_loop:
		mov [flag_length], al
		jmp exit_sets
set_flags:
	mov bl, [flags]
	for_each_flag:
		mov al, [esi]
		test al, al
		jz exit_sets
		
		cmp al, ' '
			jz set_space
		cmp al, '0'
			jz set_zero
			ja get_length
		cmp al, '+'
			jz set_plus
		cmp al, '-'
			jz set_minus
			
		for_done:
			inc esi
			jmp for_each_flag
	exit_sets:
		mov [flags], bl
		ret
		
inc_esi:
	inc esi
	mov al, [esi]
	jmp sign_gotten
set_sign:
	mov bl, [flags]
	xor bl, 10000b
	mov [flags], bl
	ret
set_and_move_esi:
	call set_sign
	jmp inc_esi
; al got 0..9a..f
hexchar2int:
	sub al, '0'
	cmp al, 9
	jbe universal_ret
	add al, '0' - 'a' + 10
universal_ret:
	ret
; -a = ~a + 1
from_dop_code:
	mov ebx, hex_input
	mov al, [ebx]
	and al, 1000b
	test al, al
	jz end_without_sign
	
	invert_hex: ; do tilda first
		mov al, [ebx]
		not al
		and al, 1111b
		mov [ebx], al
		inc ebx
		dec cl
		jnz invert_hex
	
	xor ecx, ecx	
	mov cl, [string_size]
	mov ebx, hex_input
	add ebx, ecx
	dec ebx ; ebx now points to last char
	
	; now adding 1
	mov al, [ebx]
	add al, 1
	add_1:
		mov ah, al
		and ah, 10000b
		test ah, ah
		jz end_carry ; nothing to add
		
		and al, 1111b
		mov [ebx], al
		dec ebx
		mov al, [ebx]
		add al, 1
		jmp add_1
	end_carry:
		mov [ebx], al
		call set_sign
	end_without_sign:
		jmp dividing
equal_zero:
	xor ecx, ecx
	mov cl, [string_size]
	mov ebx, hex_input
	mov ah, 0
	for_loop:
		mov al, [ebx]
		inc ebx
		or ah, al ; will be zero if all digits are zero
		dec cl
		jnz for_loop
	ret
supress_sign:
	mov bl, [flags]
	and bl, 01111b
	mov [flags], bl
	jmp dividing
; esi now points to highest hex-char		
hex_to_int:
	mov al, [esi]
	cmp al, '+'
		jz inc_esi
	cmp al, '-'
		jz set_and_move_esi
; al has highest digit now
sign_gotten:
	xor ecx, ecx
	mov cl, [string_size]
	mov ebx, hex_input
	loop1: ; input string to hex
		or al, 0x20 ; A -> a
		call hexchar2int ; a -> 10
		mov [ebx], al

		inc ebx ; next write
		inc esi ; next input
		inc cl  ; size++
		
		mov al, [esi]
		test al, al
		jnz loop1 ; reading to the end of string
	mov [string_size], cl
	; get modulo
	cmp cl, 32
	jz from_dop_code
	
	call equal_zero
	test ah, ah
	jz supress_sign ; delete minus-sign if zero
	; now big_integer division by 10
	; hex to int 
dividing:
	xor eax, eax ; current symbol of hex_input
	xor ebx, ebx ; address of hex
	xor ecx, ecx ; current symbol decimal
	xor edx, edx ; additional calculations
	mov edi, dec_number
	while_hex_jaz:
		mov ebx, hex_input
		mov ch, [string_size]
		mov dh, 0 ; carry
		for_hex_loop: ; divide hex by 10
			mov al, dh ; al = carry
			shl al, 4 ; al *= base
			mov dl, [ebx] ; dl = hex_input[i]
			add al, dl ; al += dl
			xor ah, ah
			mov dh, 10
			div dh ; ah:al /= 10
			mov dh, ah ; dh = carry = ah:al % 10
			mov [ebx], al ; hex_input[i] = cur / 10
			inc ebx
			dec ch
			jnz for_hex_loop
		mov [edi], dh; write carry to dec_number
		inc edi ; inc addres
		inc cl ; decimal-size++
		push ecx
		call equal_zero ; returns result in ah
		pop ecx
		test ah, ah
		jnz while_hex_jaz
	mov [decimal_size], cl
	ret
	
; pre: for zero sign is correct, 
;      number stored from left to right	
apply_flag:
	mov bl, [flags]
	mov edi, dec_number
	xor eax, eax
	xor ecx, ecx
	mov al, [flag_length]
	mov cl, [decimal_size]
	cmp cl, al
	jae plus_minus_space ; if ah >= al then no align is needed
	
	xor dh, dh
	mov dl, al ; dl now is flag_length
	sub dl, cl ; how many spaces or zeros need to add
	
	mov bh, 0001b ; minus flag; suppress zero flag
	and bh, bl
	jnz left_space_align
	
	mov bh, 0100b ; zero flag
	and bh, bl
	jnz right_zero_align	
right_space_align: ; no zero or minus flags, but flag_len > dec_len
	mov dh, ' '
right_zero_align:
	add edi, ecx
	add_one_zero:
		mov [edi], dh ; dh = 0 or ' '
		inc edi
		dec dl
		jnz add_one_zero
	mov edi, dec_number ; return pointer to start of decimal
	jmp plus_minus_space
left_space_align:
	mov [space_size], dl
	jmp skip_max_reassign
plus_minus_space:
	cmp cl, al
	jae skip_max_reassign
	mov [decimal_size], al
skip_max_reassign:
	mov bh, 11010b
	and bh, bl
	jz universal_ret ; no signs to add
	
	add edi, ecx
	
	mov bh, 0100b ; zero flag
	and bh, bl
	jz set_after_num
	
	cmp cl, al
	jae set_after_num ; flag = zero and flag_len <= dec_len
	
	xor edx, edx
	mov dl, al
	sub dl, cl
	add edi, edx
	dec edi
	
set_after_num:
	mov bh, 1000b ; space flag
	and bh, bl
	jnz set_space_sign
supress_zero_flag_by_plus:	
	mov bh, 0010b ; plus flag
	and bh, bl
	jnz set_plus_sign
set_minus_sign:
	mov bh, 10000b ; sign flag
	and bh, bl
	jz correct_length ; if no sign
	
	mov dh, '-'
	mov [edi], dh
correct_length:
	mov dl, [space_size]
	
	mov bh, 0001b ; minus flag
	and bh, bl
	jnz correct_space_len
	
	cmp cl, al
	jae correct_size_len
	mov cl, al ; cl = max(cl, al)
	
	jmp finally_end ; no correcting is needed
correct_space_len
    test dl, dl
    jz correct_size_len
	dec dl
correct_size_len:
	inc cl
finally_end:
	mov [space_size], dl
	mov [decimal_size], cl
	ret
set_space_sign:
	mov bh, 10000b ; sign
	and bh, bl
	jnz set_minus_sign
	mov dh, ' '
	mov [edi], dh
	jmp supress_zero_flag_by_plus
set_plus_sign:
	mov bh, 10000b ; sign
	and bh, bl
	jnz set_minus_sign
	mov dh, '+'
	mov [edi], dh
	jmp correct_length
	
myprint:
 	xor ebx, ebx
	mov edi, dec_number
	mov bl, [decimal_size]
	
	add edi, ebx
	dec edi
	print_char:
		xor eax, eax
		mov al, [edi]
		dec edi
		mov dh, 9
		cmp al, dh
		ja skip_correcting_char
		add al, '0'
	skip_correcting_char:
		push eax
		push format_char
		call printf
		add esp, 8
		dec bl
		jnz print_char
	
	mov bl, [space_size]
	print_space:
		test bl, bl
		jz universal_ret
		
		xor eax, eax
		mov al, ' '
		push eax
		push format_char
		call printf
		add esp, 8
		dec bl
		jmp print_space
	
flag_empty:
	mov esi, [edi + 4]
	jmp goto_hex_to_int
	
main:
	mov edi, [esp + 8]; edi = argv
	
	mov esi, [edi + 8]
	test esi, esi
	jz flag_empty ; will go to number immediately if no flags
	
	mov esi, [edi + 4]; esi = flags as argv[1]
	call set_flags
	
	mov esi, [edi + 8]; esi = hex number as argv[2]
	goto_hex_to_int: call hex_to_int
	
	call apply_flag
	
	call myprint
	ret
; your path ends here

	section .rdata
format_char: db '%c', 0
format_int_line: db '%d', 10, 0

	section .data
flag_length: db 0 ; lenght in the flag string
flags: dd 00000b ; sign-space-zero-plus-minus
hex_input: resb 32 ; number from string to digit converted; stored left-to-right from high to low
dec_number: resb 64 ; answer string in decimal; stored left-to-right from low to high
space_size: db 0 ; how many spaces to add
string_size: db 0 ; size of hex string
decimal_size: db 0 ; size of decimal number
