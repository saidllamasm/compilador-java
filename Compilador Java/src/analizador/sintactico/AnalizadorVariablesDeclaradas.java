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
public class AnalizadorVariablesDeclaradas {
    
    static Vector variables_globales = new Vector(), variables_type_globales = new Vector();
    
    
    // Busca entre todas las variables para determinar si ya esta declarada
    // retorna [0] estado, [1] tipo de dato
    public static String[] isRegisterVariable(String nom_var){
        String result[] = new String[]{"false", "it is not a data type"};
        for(int i = 0; i < variables_globales.size(); i++){
           if(variables_globales.elementAt(i).equals(nom_var)){
               result[0] = "verdadero";
               result[1]= ""+variables_type_globales.elementAt(i);
           }
        }
        return result;
    }
    
    // cambia de cadena a valor booleano
    public static boolean convertStringToBoolean(String val){
        return val.equals("verdadero");
    }
    
    // comprueba que el nombre de variable no este en uso
    // regresa verdadero si ya esta en uso, falso si esta disponible
    public static boolean isNameRegistered(String name){
        boolean find = false;
        for(int i = 0; i < variables_globales.size(); i++){
           if(variables_globales.elementAt(i).equals(name)){
               find = true;
               break;
           }
        }
        return find;
    }
    
    
    public static void registerNewVariable(String idVar,  String dataType){
        variables_globales.addElement(idVar);
        variables_type_globales.addElement(dataType);
    }
    
    public static void viewAllVarsDefined(){
        for(int i=0; i<variables_globales.size(); i++){
            System.out.println(variables_globales.elementAt(i) +"  ["+ variables_type_globales.elementAt(i)+"]");
        }
    }
}
