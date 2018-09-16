# Bienvenido a JASCI!

¡Hola! Llegaste a mi compilador realizado en Java y ASM para la materia de Lenguajes y Autómatas 1 y 2. El presente documento corresponde a la documentación del lenguaje JASCI, lenguaje compilado basándome en el lenguaje de programación Java.
> **Nota:** Este repositorio es para uso educativo y no pretende remplazar algún lenguaje actual en la industria.

# Reglas para nombrar variables
Al momento de declarar variables se deberá tener en cuenta lo siguiente:
-  El nombre debe contener únicamente caracteres del tipo letra ASCII (en mayúsculas y/o minúsculas)
-  No se aceptan números, caracteres de subrayado ni caracteres especiales.
-  No debe contener espacios.
-  El nombre de una variable no puede ser una palabra reservada.

# Palabras reservadas
Entiéndase como `<variable>` un nombre de variable, `<val>` como un valor, que bien puede ser numérico, booleano o de cadena y `<tipo>` como el tipo de dato que se va a definir esa variable.

|Palabra|Descripción| Ejemplo
|-|-|-|
|crea|Su función es reservar espacios de memoria, dicho de otra manera, definir variables.| `crea <tipo> <variable>`
|||`crea entero <variable>`|
|||`crea booleano <variable>`|
|||`crea cadena <variable>`|
|||`crea entero <variable> = <variable> + <val> + <val> + <variable>`|
|||`crea entero vara = varb + 2 + 34 + varc`|
|lea|Su función leer de teclado un valor y asignarlo a una variable ya declarada| `lea <variable>`
|||`lea var` 
|imprime|Su función es imprimir en consola algún valor o variable. **Nota**: Para concatenar se utiliza el símbolo . | `imprime <variable>`
|||`imprime <val>`|
|||`imprime "Hola_este_lenguaje_es_genial!"`|
|||`imprime "Hola_este_lenguaje_es_genial!" . "_estoy_concatenando"`|
|||`imprime vara . varb`|
|asigna|Su función es darle valor a las variables ya declaradas| `asigna <variable> = 20`
|||`asigna <variable> = 20 + 234 -343`|
|||`asigna <variable> = "Hola_JASCI"`|
|||`asigna <variable> = verdadero`|

# Tipos de datos validos
Entiéndase como `<variable>` un nombre de variable y `<val>` un valor, que bien puede ser numérico, booleano o de cadena.

|      Tipo          |Descripción          |Ejemplo                         |
|-|-|-|
|entero|Recibe números enteros que van desde el-32768 al 32767.|`crea entero <variable>`
||| `crea entero var = 2`
||| `crea entero var = 2 + 200 `|
||| `crea entero <var> = <var> + <val> `|
||| `crea entero var = varb + 33 `|
|booleano|Recibe dos valores: verdadero ó falso|`crea booleano <variable>`
||| `crea booleano var = verdadero`
||| `crea booleano var = falso`
|cadena|Recibe cualquier carácter o caracteres. **Importante**: Los espacios en blanco deben ser sustituidos por guion bajo '**_**'|`crea cadena <variable>`
|||`crea cadena var = “Hola_JASCI”`
||| `crea cadena var = “Hola_esta_es_cadena”`




# Ejemplos de código fuente

El siguiente fragmento de código es una muestra de como se pueden definir variables, definir y asignar un valor en un mismo renglón, muestra también las dos formas de agregar comentarios, impresiones de variables asi como de concatenación con alguna otra variable o un valor directo, por ultimo muestra como se puede leer desde teclado un valor y almacenarlo en una variable. 

    `crea entero varA
    crea cadena saludo = "Hola_mundo_JASCI!"
    crea booleano cond = verdadero
    
    /*
	    Comentario de 
	    más de una linea
    /
    
    imprime saludo . 2332 . "puedo_concatenar_cadenas"
    imprime 223 . "_es_tu_resultado"
    imprime "Hola_JASCI"
    imprime varA
    
    // comentario de una sola linea

    lea varA
    
    crea cadena despedida = "Gracias_por_usarme"`


# Referencias

 - [Compilers: Principles, Techniques, and Tools](https://github.com/germanoa/compiladores/blob/master/doc/ebook/Compiladores%20Principios%2C%20Tecnicas%20e%20Ferramentas%20-%20Alfred%20V.%20Aho.pdf)
