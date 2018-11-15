/*
 * Copyright (C) 2018 saidllamas
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package analizador.sintactico;

import static analizador.sintactico.CompiladorAnalizador.simbolos;
import java.util.Vector;

/**
 *
 * @author saidllamas
 */
public class TraductorASM { 
    
    Vector<Simbolo> tablaSimbolos;
    final String i_stack = "pila segment para stack 'stack'\n";
    final String f_stack = "pila ends\n";
    
    final String i_data = "datos segment para public 'data'\n";
    final String f_data = "datos ends\n";
    final String i_extra = "extra segment para public 'data'\n";
    final String f_extra = "extra ends\n";
    final String i_code = "assume cs:codigo, ds:datos, ss:pila, es:extra   ; segmentos\n" +
                        "public p0                                       ; funcion principal\n" +
                        "codigo segment para public 'code'\n" +
                        "  p0 proc far\n"+
                        "\n"+
                        "\n";
    final String obligatorias = "    push ds             ; obligatoria\n" +
                                "    mov ax,0            ; obligatoria\n" +
                                "    push ax             ; obligatoria\n" +
                                "    mov ax,datos        ; obligatoria\n" +
                                "    mov ds,ax           ; obligatoria\n" +
                                "    mov ax,extra        ; obligatoria\n" +
                                "    mov es,ax           ; obligatoria\n";
    
    final String libs = "    extern impnumde:far ; imprime numero decimal\n" +
                        "    ; extern impcar:far ; imprime caracter\n" +
                        "    extern slinea:far ; salto de linea\n" +
                        "    extern impcad:far  ; imprime cadena\n" +
                        "    extern leercad:far ; lee cadena desde teclado\n";
            
    final String f_code = " ret\n"+
                            "  p0 endp\n" +
                            "  codigo ends\n" +
                            "end p0\n";
    
    
    // code generate
    String code = "", varsTmps = "";
    int idAux = 0;
    int idcSalto = 0; // controla el id del salto para ciclos
    
    public String clearScreen(){
        
        return "    ; Inicia limpiar pantalla\n"
                + " MOV AH, 6\n"
                + " MOV AL, 0\n"
                + " MOV BH, 7\n"
                + " MOV CH, 0\n"
                + " MOV CL, 0\n"
                + " MOV DH, 24\n"
                + " MOV DL, 79\n"
                + " INT 10H\n"
                + " ; Termina limpiar pantalla\n";
    }
    
    
    public String convertirASM (String c, Vector simbolos){
        Tools tools = new Tools(); 
        String content = "";
        tablaSimbolos = simbolos;
        String vars = "";
        for(int i = 0; i < tablaSimbolos.size(); i++){
           Simbolo simbolo = tablaSimbolos.elementAt(i);
           if(simbolo.isLectura()){
               vars += ""+simbolo.getId()+" DB 61, ?, 61 DUP( ? )  ; variable de lectura";
           }else{
               if(simbolo.getTipo().equals("entero")){
                    if(simbolo.getValor() != null)
                        vars += "    " +simbolo.getId()+" DB " +  simbolo.getValor(); //nombre de variable
                    else 
                        vars += "    " +simbolo.getId()+" DB 0"; //nombre de variable
                } else if(simbolo.getTipo().equals("cadena")){
                    if(simbolo.getValor() != null){
                        String var_tmp = tools.cleanVarString(simbolo.getValor());
                        String res = "";
                        int indice = var_tmp.length()-1;
                        while(var_tmp.charAt(indice) != '"'){
                            indice--;
                        }
                        for(int tm = 0; tm < indice; tm++){
                            res += ""+var_tmp.charAt(tm);
                        } 
                        //res+="$\"";
                        res+="\", 10, 13, 24H";
                        //txtVars += "    " +vars[i][1]+" DB " +  tools.cleanVarString(simbolo.getValor())+"$"; //nombre de variable   
                        vars += "    " +simbolo.getId()+" DB " +  res; //nombre de variable   
                        //txtVars +=res;
                    }
                    else 
                        vars += "    " +simbolo.getId()+" DB \"$\""; //nombre de variable
                } else if(simbolo.getTipo().equals("booleano")){
                    if(simbolo.getValor() != null)
                        if ( simbolo.getValor().equals("verdadero"))
                            vars += "    " +simbolo.getId()+" DB 1 " ; // VERDADERO
                        else 
                             vars += "    " +simbolo.getId()+" DB 0 " ; // FALSO    
                    else
                        vars += "    " +simbolo.getId()+" DB 0 " ; // FALSO    
                }
           }
           vars += "\n";
        }
        filterCode(c);
        content =   i_stack +
                    // agregar en pila
                    f_stack + 
                    i_data +
                    // agregar en seg de datos
                    vars + // variables de la tabla de simbolo
                    varsTmps + //variables para runtime
                    f_data +
                    i_extra +
                    // agregar en seg extr datos
                    f_extra +
                    i_code +
                    // codigo asm
                    obligatorias + //instrucciones obligatorias
                    clearScreen() + // limpia pantalla
                    code + // codigo filtrado
                    libs + // referencia a metodos
                    //end codigo asm
                    f_code
                ;
        return content;
    }
    
    public void filterCode(String content){
        boolean ciclo = false;
        for(int i = 0; i < content.length(); i++){
            String renglon = "";
            while(content.charAt(i) != '\n'){
                renglon += content.charAt(i) ;
                i++;
            }
            if(renglon.split(" ")[0].equals("crea") && !ciclo){
                
            } else if(renglon.split(" ")[0].equals("asigna")  && !ciclo){
                //new_content += ""+translateAsigna(renglon);
            } else if(renglon.split(" ")[0].equals("imprime")  && !ciclo){
                translateImpresion(renglon);
            } else if(renglon.split(" ")[0].equals("lea")  && !ciclo){
               translateLea(renglon);
            } else if(renglon.split(" ")[0].equals("ciclo")  && !ciclo){
                //new_content += ""+translateAsigna(renglon);
                translateCiclo(i, content, renglon);
                ciclo = true;
            } else if(renglon.split(" ")[0].equals("compara_si")  && !ciclo){
                //new_content += ""+translateAsigna(renglon);
            } else{
                if(renglon.split(" ")[0].equals("fin_compara") || renglon.split(" ")[0].equals("fin_ciclo")){
                    //new_content += "";//salto en ensamblador
                     ciclo = false;
                }else{
                 //System.out.println(CustomColors.RED+"ERROR 500 ::"+renglon.split(" ")[0]+"::");   
                }
            }
        }
    }
    
    public void translateAsigna(String content){
    }
    
    public void translateCiclo(int indice, String content, String condicion){
        System.out.println(condicion);
        String next_content = "";
        for(int i = indice; i < content.length(); i++){
            next_content += content.charAt(i);
        }
        String instrucciones = "";
        String rowsNext[] = next_content.split("\n");
        
        
        for(int i = 0; i < rowsNext.length; i++){
            String partsNext[] = rowsNext[i].split(" ");   
            if(partsNext[0].equals("fin_ciclo") ){
                //System.out.println("PARA!");
                break;
            }else{
                instrucciones += rowsNext[i] +"\n" ;
            }
        }
        code += " ; inicia ciclo \n"+
                " MOV CX,4 \n"+
                " C"+idcSalto+": \n";
        filterCode(instrucciones);
        code += " loop C"+idcSalto+" \n"+
                " ; fin ciclo \n";
        //System.out.println(instrucciones);
        
        idcSalto++;
        
        //System.out.println(CustomColors.PURPLE+instrucciones);
        //System.out.println(indice+" "+content.charAt(indice+2)+" "+content.charAt(indice+1));
    }
    
    public void translateImpresion(String content){
        String new_content = "";
        //System.out.println(CustomColors.YELLOW+content.split(" ")[1].toString());
        if(isDataReading(content.split(" ")[1])){
            new_content += "mov 	cx, 0 ;limpia contador\n" +
            "	lea 	bx, "+getIDVar(content.split(" ")[1]) +" + 1 \n"+
            "	mov 	cl, [bx] ;almacena en cl el numero de caracteres ingresados\n" +
            "	\n" +
            "impEnt:\n" +
           "MOV DL,[bx+1]\n" +
"      	MOV AH,2\n" +
"	  	INT 21H\n" +
"		INC bx\n"+
            "loop 		impEnt\n";
        }else{
            if(getIDVar(content.split(" ")[1]).equals("")){
                //System.out.println(CustomColors.YELLOW+ getIDVar( content.split(" ")[1]));
                Tools herramient = new Tools();
                //System.out.println(CustomColors.YELLOW+ );
                varsTmps += "tmp"+idAux + " DB " + herramient.cleanVarString(content.split(" ")[1])  +" , 10, 13, 24H\n";
                Simbolo simbolo = new Simbolo(); 
                simbolo.setNombre("tmp"+idAux);
                simbolo.setId("tmp"+idAux);
                simbolo.setLectura(false);
                //simbolo.setValor(herramient.retireQuotes( herramient.cleanVarString(content.split(" ")[1])));
                simbolo.setValor( herramient.cleanVarString(content.split(" ")[1]) );
                simbolo.setTipo("cadena");
                simbolos.add((simbolo));
                tablaSimbolos.add(simbolo);
                new_content = "; Impresion de cadena\n" +
                    "LEA DX, tmp"+ idAux + "\n"+
                    "MOV AH,09\n" +
                    "INT 21H";
                idAux++;
            }else{
                new_content = "; Impresion de cadena\n" +
                    "LEA DX, "+ getIDVar(content.split(" ")[1]) + "\n"+
                    "MOV AH,09\n" +
                    "INT 21H";
            }
        }
        
        code += new_content+"\n";
    }
    
    public void translateLea(String content){
        String new_content = "";
        new_content = "; inicia lectura de cadena\n"+
                    "       lea  bx, " + getIDVar(content.split(" ")[1])+"\n" +
                    "	push 	bx \n" +
                    "	call	leercad \n" +
                    "	add 	sp, 2\n" +
                    "       call	slinea\n"+
                    "       ; fin lectura";
        code += new_content+"\n";
    }
    
    
    public String getIDVar(String name){
        // recorrer vector de todas las variables
        String id ="";
        for(int i = 0; i < tablaSimbolos.size(); i++){
            Simbolo simbolo = tablaSimbolos.elementAt(i);
            if(simbolo.getNombre().equals(name)){
                id =  ""+simbolo.getId();
                break;
            }
        }
        //System.out.println(CustomColors.YELLOW+id);
        return id; // retornar id de la variable declarada en la tabla de simbolos
    }
    
    public boolean isDataReading(String name){
        // recorrer vector de todas las variables
        boolean read = false;
        for(int i = 0; i < tablaSimbolos.size(); i++){
            Simbolo simbolo = tablaSimbolos.elementAt(i);
            if(simbolo.getNombre().equals(name)){
                read = simbolo.isLectura();
                break;
            }
        }
        //System.out.println(CustomColors.YELLOW+id);
        return read; // retornar id de la variable declarada en la tabla de simbolos
        
    }
    
    public String getTypeVar(String name){
        // recorrer vector de todas las variables
        String type ="";
        for(int i = 0; i < tablaSimbolos.size(); i++){
            Simbolo simbolo = tablaSimbolos.elementAt(i);
            if(simbolo.getNombre().equals(name)){
                type =  ""+simbolo.getTipo();
                break;
            }
        }
        //System.out.println(CustomColors.YELLOW+id);
        return type; // retornar id de la variable declarada en la tabla de simbolos
        
    }
     
}
