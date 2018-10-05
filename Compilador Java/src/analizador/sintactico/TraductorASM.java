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

import java.util.Vector;

/**
 *
 * @author saidllamas
 */
public class TraductorASM {
    
    String[][] vars ;
    
    final String stack = "pila segment para stack 'stack'\n" +
                        "pila ends\n";
    final String i_data = "datos segment para public 'data'\n";
    final String f_data = "datos ends\n";
    final String extra = "extra segment para public 'data'\n" +
                        "\n" +
                        "extra ends"+
                        "\n";
    final String i_code = "assume cs:codigo, ds:datos, ss:pila, es:extra   ; segmentos\n" +
                        "\n" +
                        "\n" +
                        "public p0                                       ; funcion principal\n" +
                        "\n" +
                        "codigo segment para public 'code'\n" +
                        "\n" +
                        "\n" +
                        "  p0 proc far"+
                        "\n"+
                        "\n";
    final String obligatorias = "    push ds             ; obligatoria\n" +
                                "    mov ax,0            ; obligatoria\n" +
                                "    push ax             ; obligatoria\n" +
                                "\n" +
                                "    mov ax,datos        ; obligatoria\n" +
                                "    mov ds,ax           ; obligatoria\n" +
                                "\n" +
                                "    mov ax,extra        ; obligatoria\n" +
                                "    mov es,ax           ; obligatoria\n";
    final String f_code = " ret\n"+
                            "  p0 endp\n" +
                            "\n" +
                            "  codigo ends\n" +
                            "\n" +
                            "end p0\n";
    
    public String convertirASM(String content, Vector<Simbolo> simbolos){
        return stack + i_data 
                + obtainVars(simbolos) // mio
                + f_data + extra + i_code + obligatorias 
                + translateToASM(content)  // mio
                + f_code;
    }
    
    public String translateToASM (String content){
        return "\n;CODE_TRANS_HERE\n" + filter(content) + "\n;END_CODE_TRANS\n";
    }
    
    public String obtainVars(Vector<Simbolo> simbolos){
        Tools tools = new Tools(); 
        String txtVars = "";
        vars = new String[simbolos.size()][3];
        for(int i = 0; i < simbolos.size(); i++){
            Simbolo simbolo = simbolos.elementAt(i);
            //System.out.print(simbolo.getNombre()+"      " + simbolo.getId() + ""+i+"   " + simbolo.getTipo() + "  " + simbolo.getValor() + "  "+simbolo.isLectura());
            vars[i][0] = simbolo.getNombre();
            vars[i][1] = simbolo.getId()+""+i+"";
            vars[i][2] = simbolo.getValor();
            if(simbolo.getTipo().equals("entero")){
                if(simbolo.getValor() != null)
                    txtVars += "    " +vars[i][1]+" DB " +  simbolo.getValor(); //nombre de variable
                else 
                    txtVars += "    " +vars[i][1]+" DB 0"; //nombre de variable
            } else if(simbolo.getTipo().equals("cadena")){
                if(simbolo.getValor() != null)
                    txtVars += "    " +vars[i][1]+" DB " +  tools.cleanVarString(simbolo.getValor())+"$"; //nombre de variable
                else 
                    txtVars += "    " +vars[i][1]+" DB \"$\""; //nombre de variable
            } else if(simbolo.getTipo().equals("booleano")){
                if(simbolo.getValor() != null)
                    if ( simbolo.getValor().equals("verdadero"))
                        txtVars += "    " +vars[i][1]+" DB 1 " ; // VERDADERO
                    else 
                         txtVars += "    " +vars[i][1]+" DB 0 " ; // FALSO    
                else
                    txtVars += "    " +vars[i][1]+" DB 0 " ; // FALSO    
            }
            txtVars += "\n";
            
        }
        return txtVars;
    }
    public String filter(String content){
        
        for(int i = 0; i < content.length(); i++){
            String renglon = "";
            while(content.charAt(i) != '\n'){
                renglon += content.charAt(i) ;
                i++;
            }
        }
        return "";
    }
    
    public String[][] getDiccionarioASM(){
        return this.vars;
    }
    
}
