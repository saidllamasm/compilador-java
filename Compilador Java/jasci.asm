; JASCI  ; 
pila segment para stack 'stack'
pila ends
datos segment para public 'data'
id0 DB 61, ?, 61 DUP( ? )  ; variable de lectura
    id1 DB 0 
tmp0 DB "Hola ingresa una cadena" , 10, 13, 24H
tmp1 DB "ingresaste la cadena" , 10, 13, 24H
tmp2 DB "inicia el ciclo" , 10, 13, 24H
tmp3 DB "." , 10, 13, 24H
tmp4 DB "otro ciclo" , 10, 13, 24H
tmp5 DB "a es menor que dos" , 10, 13, 24H
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
; Impresion de cadena
LEA DX, tmp0
MOV AH,09
INT 21H
; inicia lectura de cadena
       lea  bx, id0
	push 	bx 
	call	leercad 
	add 	sp, 2
       call	slinea
       ; fin lectura
; Impresion de cadena
LEA DX, tmp1
MOV AH,09
INT 21H
 ; inicia impresion de cadena leida desde teclado 
   lea bx, id0 + 1 
   call implec
   call slinea

; Impresion de cadena
LEA DX, tmp2
MOV AH,09
INT 21H
 ; inicia ciclo 
 MOV CX,10 
 C0: 
; Impresion de cadena
LEA DX, tmp3
MOV AH,09
INT 21H
 loop C0 
 ; fin ciclo 
 ; inicia ciclo 
 MOV CX,2 
 C1: 
; Impresion de cadena
LEA DX, tmp4
MOV AH,09
INT 21H
 loop C1 
 ; fin ciclo 
; Impresion de cadena
LEA DX, tmp5
MOV AH,09
INT 21H
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