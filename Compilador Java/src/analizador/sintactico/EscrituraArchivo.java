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

import java.io.*;
import java.util.Vector;
/**
 *
 * @author saidllamas
 */
public class EscrituraArchivo {
    final String nombre_Archivo = "jasci.asm";
    
    public void crearArchivoASM(String contenido){
        File f = new File(nombre_Archivo);

        try{
            FileWriter w = new FileWriter(f);
            BufferedWriter bw = new BufferedWriter(w);
            PrintWriter wr = new PrintWriter(bw);	
            wr.write("; JASCI  ; \n");//escribimos en el archivo 
            wr.append(contenido); // CF
            wr.append("\n;   END JASCI ;"); //concatenamos en el archivo sin borrar lo existente
            wr.close();
            bw.close();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}
