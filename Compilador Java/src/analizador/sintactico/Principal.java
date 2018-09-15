/*
 * Copyright (C) 2018 saidllamas
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of theº GNU General Public License as published by
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

import java.io.IOException;
import java.util.Vector;

/**
 *
 * De aquí ejecture mi programa
 */
public class Principal {
    
    static LecturaCF archivo = new LecturaCF();
    static Depurador depurador = new Depurador();
    static CompiladorAnalizador analizador = new CompiladorAnalizador();
    
    
    public static void main(String[] args)  throws IOException {
        /*Tools t = new Tools();
        System.out.println(t.isCorrectNameVariable(""));*/
        //System.out.println(t.isCorrectFormatString("\"bo\""));
        //System.out.println(t.isCorrectFormatString("\"b___SS__So\""));
        
        correr();
        
    }    
    
    private static void correr() throws IOException{
        String rutaABS = "/users/saidllamas/desktop/";
        String extIN = ".txt", extOut = ".sllm";
        String content = archivo.leerArchivo(rutaABS+"fuente"+extIN);
        //depurador.depurar(content);
        content = depurador.depurar(content);   
        System.out.println(content);
        System.out.println("---");
        System.out.println();
        /*
        System.out.print(archivo.leerArchivo(rutaABS+"fuente"+extIN));
        System.out.println("---");*/
        String renglon = "";
        int n_renglon = 1;
        for(int i = 0; i < content.length(); i++){
            while(content.charAt(i) != '\n'){
                renglon += content.charAt(i) ;
                i++;
            }
            //System.out.println("Renglon construido: "+renglon);
            if(!analizador.analizar(renglon)){
                System.out.print(CustomColors.RED+"in row "+n_renglon);
                System.out.println();
            }
            System.out.println();
            
            
            renglon = "";
            n_renglon++;
                
        }
        
        System.out.println("---");
        System.out.println("");
        
        Vector <Simbolo> vector = analizador.getVars();
        System.out.println("Nombre | ID  | Tipo | Valor | Lectura");
        for(int i = 0; i < vector.size(); i++){
            Simbolo simbolo = vector.elementAt(i);
            System.out.print(simbolo.getNombre()+"      " + simbolo.getId() + ""+i+"   " + simbolo.getTipo() + "  " + simbolo.getValor() + "  "+simbolo.isLectura());
            System.out.println("");
            
        }
        
        for(int i = 0; i < 20; i++)
            System.out.println("");
    }
}
