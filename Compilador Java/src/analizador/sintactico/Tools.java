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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author saidllamas
 */
public class Tools {
    
    static Vector variables_globales = new Vector(), variables_type_globales = new Vector();
    
    
    //
    //Error: ID10032
    public String conversionVariables(String renglon, String type, String dataType){
        String new_renglon = "";
        switch(type){
            case "crea":
                String[] lexemas = renglon.split(" ");
                if(isCorrectNameVariable(lexemas[2])){
                   lexemas[2] = "<var>";
                   if(lexemas.length > 3){
                        for(int it = 4; it < lexemas.length; it+=2 ){
                            if(isCorrectNameVariable(lexemas[it])){
                                lexemas[it]= "<var>";   
                            } else{
                                boolean isDataType = false;
                                switch(dataType){
                                    case "entero":
                                        isDataType = isCorrectFormatNumber( lexemas[it] );
                                        break;
                                    case "cadena":
                                        isDataType = isCorrectFormatString(lexemas[it] );
                                        break;
                                    case "booleano":
                                        isDataType = isCorrectFormatBoolean(lexemas[it] );
                                        break;
                                    default:
                                        break;
                                        
                                }
                                if(!isDataType){
                                    new_renglon = "errorSintaxis";
                                    break;
                                }else{
                                    lexemas[it]= "<val>";   
                                }
                            }
                        }
                   }
                } else{
                    new_renglon = "errorSintaxis";
                }
                if(!new_renglon.equals("errorSintaxis")){
                    for(String lex : lexemas){
                        new_renglon += lex+" ";
                    }
                }
                
                break;
            default:
                System.out.println(CustomColors.RED+" ID10032 fatal error");
                System.exit(1);
                break;
        }
        
            
        return new_renglon;
    }
    
    public static boolean isCorrectNameVariable(String var){
        String pattern = "[^\\D+]|[\\*\\.\\;\\+\\-\\_\\<\\>]";
        Pattern pat = Pattern.compile(pattern);
        Matcher mat = pat.matcher(var);
        return !mat.find();
    }
    
    // validacion de numeros, que no contenga caracteres
    public static boolean isCorrectFormatNumber(String number){
        boolean flag = true;
        try{
            int n = Integer.parseInt(number);
        }catch(Exception e){
            //si entra al catch significa que no es un numero
            flag = false;
        }
        return flag;
    }
    
    // validacion de los dos estados, verdadero o falso
    public static boolean isCorrectFormatBoolean(String value){
        return value.equals("verdadero") || value.equals("falso");
    }
    
    // validacion de una cadena de forma correcta
    //ejmp: "hola...adios" ;
    public static boolean isCorrectFormatString(String value){
        if(value.charAt(0) == '"' && value.charAt(value.length()-1) == '"' )
            return true;
        else 
            return false;
    }
    
    public static String cleanVarString(String value){
        return value.replace('_', ' ');
    }
    
    public static String printString(String value){
        return value.replace('"', ' ');
    }
    
    // cambia de cadena a valor booleano
    public static boolean convertStringToBoolean(String val){
        return val.equals("verdadero");
    }
}