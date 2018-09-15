# Bienvenido a JASCI!

Hola! Llegaste a mi compilador realizado en Java y ASM para la materia de Lenguajes y Automatas 1 y 2. 
> **Nota:** Este repositorio es para uso educativo y no pretende remplazar algún lenguaje actual en la industria.

# Reglas para nombres de variables
Al momento de declarar variables se deberá tener en cuenta lo siguiente:
-  El nombre debe contener únicamente carácteres del tipo letra ASCII (en mayúsculas o minúsculas)
-  No se aceptan números, caracteres de subrayado ni caracteres especiales.
-  No debe contener espacios.
-  El nombre de una variable no puede ser una palabra reservada.

# Palabras reservadas
Entiendase como `<var>` un nombre de variable, `<val>` como un valor, que bien puede ser númerico, booleano o de cadena y `<tipo>` como el tipo de dato que se va a definir esa variable.

|Palabra|Descripción| Ejemplo
|-|-|-|
|crea|Su función es reservar espacios de memoria, dicho de otra manera, definir variables.| `crea <tipo> <variable>`
|||`crea entero <var>`|
|||`crea booleano <var>`|
|||`crea cadena <var>`|
|||`crea entero <var> = <var> + <val> + <val> + <var>`|
|||`crea entero vara = varb + 2 + 34 + varc`|
|lea|Su función leer de teclado un valor y asignarlo a una variable ya declarada| `lea <variable>`
|||`lea var` 
|imprime|Su función es imprimir en consola algún valor o variable. **Nota**: Para concatenar se utiliza el simbolo . | `imprime <var>`
|||`imprime "Hola_este_lenguaje_es_genial!"`|
|||`imprime "Hola_este_lenguaje_es_genial!" . "_estoy_concatenando"`|
|||`imprime vara . varb`|
|asigna|Su función es darle valor a las variables ya declaradas| `asigna <variable> = 20`
|||`asigna <variable> = 20 + 234 -343`|
|||`asigna <variable> = "Hola_JASCI"`|
|||`asigna <variable> = verdadero`|

# Tipos de datos validos
Entiendase como `<var>` un nombre de variable y `<val>` un valor, que bien puede ser númerico, booleano o de cadena.

|      Tipo          |Descripción          |Ejemplo                         |
|-|-|-|
|entero|Recibe números enteros que van desde el-32768 al 32767.|`crea entero <var>`
||| `crea entero var = 2`
||| `crea entero var = 2 + 200 `|
||| `crea entero <var> = <var> + <val> `|
||| `crea entero var = varb + 33 `|
|booleano|Recibe dos valores: verdadero ó falso|`crea booleano <var>`
||| `crea booleano var = verdadero`
||| `crea booleano var = falso`
|cadena|Recibe cualquier carácter o caracteres. **Importante**: Los espacios en blanco deben ser sustituidos por guion bajo '**_**'|`crea cadena <var>`
|||`crea cadena var = “Hola_JASCI”`
||| `crea cadena var = “Hola_esta_es_cadena”`

# Referencias

 - [Compilers: Principles, Techniques, and Tools](https://github.com/germanoa/compiladores/blob/master/doc/ebook/Compiladores%20Principios%2C%20Tecnicas%20e%20Ferramentas%20-%20Alfred%20V.%20Aho.pdf)
