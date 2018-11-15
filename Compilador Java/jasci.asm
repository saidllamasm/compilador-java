; JASCI  ; 
pila segment para stack 'stack'
pila ends
datos segment para public 'data'
    id0 DB 0
tmp0 DB "hola" , 10, 13, 24H
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
 ; inicia ciclo 
 MOV CX,4 
 C0: 
; Impresion de cadena
LEA DX, tmp0
MOV AH,09
INT 21H
 loop C0 
 ; fin ciclo 
    extern impnumde:far ; imprime numero decimal
    ; extern impcar:far ; imprime caracter
    extern slinea:far ; salto de linea
    extern impcad:far  ; imprime cadena
    extern leercad:far ; lee cadena desde teclado
 ret
  p0 endp
  codigo ends
end p0

;   END JASCI ;