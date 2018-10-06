; JASCI  ; 
pila segment para stack 'stack'
pila ends
datos segment para public 'data'
    id0 DB 0
    id1 DB 10 
    id2 DB "hola " $
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

;CODE_TRANS_HERE

;END_CODE_TRANS
 ret
  p0 endp

  codigo ends

end p0

;   END JASCI ;