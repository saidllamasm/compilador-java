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
    static Logs logs = new Logs();
    
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
                        for(int it = 4; it < lexemas.length; it++ ){
                            if(isCorrectNameVariable(lexemas[it])){
                                lexemas[it]= "<var>";   
                            } else {
                                // es numero
                                if( isCorrectFormatNumber(lexemas[it])){
                                    lexemas[it]= "<val>";   
                                }else if ( isCorrectFormatString(lexemas[it])) {
                                    lexemas[it]= "<val>";   
                                }else{
                                    // retirar comentario para ver que es lo que evalua pero no identifica
                                    //System.out.println(lexemas[it]);
                                }
                                // es simbolo
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
        boolean correct = true;
        String error_desc = logs.ERROR_VARIABLE_NAME; //usarla posteriormente para mas detalle en el log
        for(String system : logs.PAL_RESERV){ //no coincidir con palabras reservadas
            if (var.equals(system)){
                correct = false;
                break;
            }
        }
        if (correct){
            for(String system : logs.DATA_TYPES){ //no coincidir con TIPOS DE DATOS
                if (var.equals(system)){
                    correct = false;
                    break;
                }
            }
        }
        if(correct){
            String pattern = "^[a-zA-Z]+$";
            Pattern pat = Pattern.compile(pattern, Pattern.DOTALL);
            Matcher mat = pat.matcher(var);
            correct = mat.find();
        }
        return correct;
    }
    
    // validacion de simbolos relacoionales
    public static boolean isSymbolRelation(String symbol){
        boolean flag = false;
        for(String single_symbol : logs.SYMBOLS_RELATION){
            if(single_symbol.equals(symbol)){
                flag = true;
                break;
            }
        }
        return flag;
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
    //ejmp: "hola_adios" ;
    public static boolean isCorrectFormatString(String value){
        boolean correct = true;
        for(int i = 0; i < value.length(); i++){
            if(value.charAt(i) == ' '){
                correct = false;
                break;
            }
        }
        if(value.charAt(0) == '"' && value.charAt(value.length()-1) == '"' && correct )
            return true;
        else 
            return false;
    }
    
    
    public static String printString(String value){
        return value.replace('"', ' ');
    }
    
    // cambia de cadena a valor booleano
    public static boolean convertStringToBoolean(String val){
        return val.equals("verdadero");
    }
    
    public String cleanVarString(String value){
        return value.replace('_', ' ');
    }
    
    public String retireQuotes(String value){
        return value.replace('"', ' ');
    }
}
