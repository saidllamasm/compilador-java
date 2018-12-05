; JASCI  ; 
pila segment para stack 'stack'
pila ends
datos segment para public 'data'
id0 DB 61, ?, 61 DUP( ? )  ; variable de lectura
id1 DB 61, ?, 61 DUP( ? )  ; variable de lectura
tmp0 DB "a es menor que b" , 10, 13, 24H
datos ends
extra segment para public 'data'
extra ends
assume cs:codigo, ds:datos, ss:pila, es:extra   ; segmentos
public p0                                       ; funcion principal
codigo segment para public 'code'
  p0 proc far


    push ds             ; obligatoria
    mov ax,0            ; obligatoria
    push ax             ; obligatoria
    mov ax,datos        ; obligatoria
    mov ds,ax           ; obligatoria
    mov ax,extra        ; obligatoria
    mov es,ax           ; obligatoria
    ; Inicia limpiar pantalla
 MOV AH, 6
 MOV AL, 0
 MOV BH, 7
 MOV CH, 0
 MOV CL, 0
 MOV DH, 24
 MOV DL, 79
 INT 10H
 ; Termina limpiar pantalla
; inicia lectura de cadena
       lea  bx, id1
	push 	bx 
	call	leercad 
	add 	sp, 2
       call	slinea
       ; fin lectura
; inicia lectura de cadena
       lea  bx, id0
	push 	bx 
	call	leercad 
	add 	sp, 2
       call	slinea
       ; fin lectura
;;;;;;;;;;;;;;;;
	lea bx,id1
	inc bx
	mov cl,[bx]
	mov CH,0
	PUSH CX
   
	CDR0:
		INC BX
		SUB[BX],BYTE PTR 30H
	LOOP CDR0	

	LEA BX,id1+2;AQUI SE GUARDA EL 2
	MOV AL,[BX]
	MOV DI,0AH ;NO PUEDE SER DX
	MOV AH,0; PARA QUITAR EL 09 DE AH, PUESTO EN LA FUNCION 9 DE LA INT 21 
	POP CX
	DEC CX

	MYS0:
		INC BX
		MUL DI
		MOV DL,[BX]
		MOV DH,0
		ADD AX,DX	
	LOOP MYS0
	;; AX tiene el decimal leido
	;;;;;;;;;;;;;;;;

	MOV BX, AX ; en bx primer numero
	PUSH BX
	;;;;;;;;;;;;;;;;
	lea bx,id0
	inc bx
	mov cl,[bx]
	mov CH,0
	PUSH CX
   
	CDR1:
		INC BX
		SUB[BX],BYTE PTR 30H
	LOOP CDR1

	LEA BX,id0+2;AQUI SE GUARDA EL 2
	MOV AL,[BX]
	MOV DI,0AH ;NO PUEDE SER DX
	MOV AH,0; PARA QUITAR EL 09 DE AH, PUESTO EN LA FUNCION 9 DE LA INT 21 
	POP CX
	DEC CX

	MYS1:
		INC BX
		MUL DI
		MOV DL,[BX]
		MOV DH,0
		ADD AX,DX	
	LOOP MYS1
	;; AX tiene el decimal leido
	;;;;;;;;;;;;;;;;

	POP BX
	;;; ejemplo de uso del numero ingresado como comparacion para un if ;;;
	CMP BX,AX ;
JL MENOR0
JMP FIN0
MENOR0: 
		; code ;
; Impresion de cadena
LEA DX, tmp0
MOV AH,09
INT 21H
		JMP FIN0
FIN0:
    extern implec:far ; imprimir cadena leida de teclado
    extern impnumde:far ; imprime numero decimal
    extern slinea:far ; salto de linea
    extern impcad:far  ; imprime cadena
    extern leercad:far ; lee cadena desde teclado
 ret
  p0 endp
  codigo ends
end p0

;   END JASCI ;