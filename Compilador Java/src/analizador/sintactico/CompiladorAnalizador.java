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
public class CompiladorAnalizador {
    
    static Vector <Simbolo> simbolos = new Vector();
    static int nodes_ciclo = 0, nodes_condicion = 0;
    
    public static boolean analizar(String instruccion){
        boolean isCorrect = false;
        AnalizadorAutomatas automata = new AnalizadorAutomatas();
        Automatas automatas = new Automatas();
        Tools herramientas = new Tools();
        Logs msj = new Logs();
        AnalizadorVariablesDeclaradas variablesDeclaradas = new AnalizadorVariablesDeclaradas();
        
        String bk_instruccion = instruccion;
        
        if(instruccion.split(" ")[0].equals("crea")){
            String[] lexemas = instruccion.split(" ");
            if(lexemas.length == 3 || lexemas.length >= 4){
                instruccion = herramientas.conversionVariables(instruccion, "crea", lexemas[1]);
                //lexemas = instruccion.split(" "); // establezco la nueva instruccion con los valores y variables traducidos
                if(!instruccion.equals("errorSintaxis")){
                    if(automata.analizarRenglon(instruccion.split(" "), automatas.getPatronInstancia() ) ){
                        if(variablesDeclaradas.isNameRegistered(lexemas[2])){
                            //System.out.println(CustomColors.RED+"Problema con la declaración de variables");
                            System.out.println(CustomColors.RED+bk_instruccion+" "+msj.ERROR_VARIABLE_ALREADY_DEFINE+" CE10222");
                        } else{
                            variablesDeclaradas.registerNewVariable(lexemas[2], lexemas[1]);
                            if(lexemas.length == 3){
                            //variablesDeclaradas.registerNewVariable(lexemas[2], lexemas[1]);
                                  System.out.println(CustomColors.GREEN+bk_instruccion+" sucess");
                                  isCorrect = true;
                                  Simbolo simbolo = new Simbolo(); 
                                  simbolo.setNombre(lexemas[2]);
                                  simbolo.setId("id");
                                  simbolo.setLectura(false);
                                  simbolo.setTipo(lexemas[1]);
                                  simbolos.add((simbolo));
                              }
                            if(lexemas.length >= 4){
                                int vals = 0, success = 0;
                                String valores = "";
                                for(int i = 4; i <= lexemas.length; i+=2){
                                    vals++;
                                    String[] res =  variablesDeclaradas.isRegisterVariable(lexemas[i]);
                                    //recorre solo cada valor o variable despues del simbolo =
                                    if(res[0].equals("verdadero") && res[1].equals(lexemas[1])){ // es variable
                                        success++;
                                        valores += ""+lexemas[i]+" ";
                                    }else{ // es valor
                                        String type_Validate = lexemas[1];
                                        boolean isDataType = false;
                                        switch(type_Validate){
                                            case "entero":
                                                isDataType = herramientas.isCorrectFormatNumber( lexemas[i] );
                                                break;
                                            case "cadena":
                                                isDataType = herramientas.isCorrectFormatString( lexemas[i] );
                                                break;
                                            case "booleano":
                                                isDataType = herramientas.isCorrectFormatBoolean( lexemas[i] );
                                                break;
                                            default:
                                                break;

                                        }
                                        if(isDataType){
                                            success++;
                                            valores += ""+lexemas[i]+" ";
                                        }
                                        //System.out.println(CustomColors.RED+lexemas[i]+" "+msj.ERROR_VARIABLE_NOT_DECLARE);
                                        //break;
                                    }
                                }
                                if(vals==success){
                                    Simbolo simbolo = new Simbolo(); 
                                    simbolo.setNombre(lexemas[2]);
                                    simbolo.setId("id");
                                    simbolo.setValor(valores);
                                    simbolo.setLectura(false);
                                    simbolo.setTipo(lexemas[1]);
                                    simbolos.add((simbolo));
                                    System.out.println(CustomColors.GREEN+bk_instruccion+" sucess");
                                    isCorrect = true;
                                }
                                /*else{
                                    System.out.println(CustomColors.CYAN+bk_instruccion+" "+ (vals-success)+"=no_success");
                                }*/
                            }
                        }
                    } else {
                        System.out.println(CustomColors.RED+bk_instruccion+" "+msj.ERROR_STATEMENT+" CE1020");
                    }
                } else {
                    System.out.println(CustomColors.RED+bk_instruccion+" "+msj.ERROR_VARIABLE_ALREADY_DEFINE+" or "+msj.ERROR_VARIABLE_TYPE_NOT_COMPATIBLE +" CE10224");
                }
            } else{
                System.out.println(CustomColors.RED+bk_instruccion+" "+msj.ERROR_STATEMENT+" CE1020");
            }
            
        } else if(instruccion.split(" ")[0].equals("imprime")){
            String[] lexemas = instruccion.split(" ");
            if(lexemas.length >= 2){
                for(int i = 1; i < lexemas.length; i++){
                    if( i%2 == 0){
                        if(!lexemas[i].equals(".") || (i+1) == lexemas.length){
                            System.out.println(CustomColors.RED+lexemas[i]+" "+msj.ERROR_STATEMENT+" CE3320");
                            isCorrect = false;
                            break;
                        }
                    } else{
                        // debe ser valor o variable
                        if(herramientas.isCorrectFormatNumber(lexemas[i]) || herramientas.isCorrectFormatString(lexemas[i]) || herramientas.isCorrectNameVariable(lexemas[i])){
                            if(herramientas.isCorrectNameVariable(lexemas[i])){
                                String[] res =  variablesDeclaradas.isRegisterVariable(lexemas[i]);
                                if(res[0].equals("verdadero")){
                                    isCorrect = true;
                                }else{
                                    System.out.println(CustomColors.RED+lexemas[i]+" "+msj.ERROR_VARIABLE_NOT_DECLARE+" CE3304");
                                    isCorrect = false;
                                    break;
                                }
                            }
                            if(herramientas.isCorrectFormatNumber(lexemas[i]) || herramientas.isCorrectFormatString(lexemas[i])){
                                isCorrect = true;
                            }
                            
                        } else {
                            System.out.println(CustomColors.RED+lexemas[i]+" "+msj.ERROR_STATEMENT+" CE3320");
                            isCorrect = false;
                            break;
                        }
                    }
                }   
            }else{
                System.out.println(CustomColors.RED+bk_instruccion+" "+msj.ERROR_STATEMENT+" CE1049");
            }
            if(isCorrect){
                System.out.println(CustomColors.GREEN+bk_instruccion+" sucess");
            }
            
        } 
        else if(instruccion.split(" ")[0].equals("lea")){
            //System.out.println("detectado lea como inicio de instruccion. no HAGO NADA");
            String[] lexemas = instruccion.split(" ");
            if(lexemas.length == 2){
                String[] res =  variablesDeclaradas.isRegisterVariable(lexemas[1]);
                //System.out.println(CustomColors.GREEN+bk_instruccion+" success");
                if(res[0].equals("verdadero")){
                    // actualizo el estado de la variable para retirar el valor y  asignarle true en lectura
                    for(int i = 0; i < simbolos.size(); i++){
                        Simbolo simbolo = simbolos.elementAt(i);
                        if(simbolo.getNombre().equals(lexemas[1])){
                            //System.out.println("update");
                            //simbolo.setValor("");
                            simbolo.setLectura(true);
                            break;
                        }
                    }
                    //
                    System.out.println(CustomColors.GREEN+bk_instruccion+" success");
                    isCorrect = true;
                }else{
                    System.out.println(CustomColors.RED+bk_instruccion+" "+msj.ERROR_VARIABLE_NOT_DECLARE+" CE1432");
                }
            }else{
                System.out.println(CustomColors.RED+bk_instruccion+" "+msj.ERROR_STATEMENT+" CE1022");
            }
        } else if(instruccion.split(" ")[0].equals("ciclo")){
            //System.out.println("detectado lea como inicio de instruccion. no HAGO NADA");
            String[] lexemas = instruccion.split(" ");
            
            if(lexemas.length == 4){
                // forzado para variable de lectura
                System.out.println(lexemas[2]);
                nodes_ciclo++;
                isCorrect = true;
            }else{
                if(lexemas.length < 3){
                    isCorrect = false;
                    System.out.println(CustomColors.BLUE+"Error en numero de lexemas");   
                }else{
                    String log_tmp = "";
                    for(int i = 2; i < lexemas.length; i++){
                        if(!herramientas.isSymbolRelation(lexemas[i])){  
                            if(herramientas.isCorrectFormatNumber(lexemas[i]) || herramientas.isCorrectFormatBoolean(lexemas[i])){
                                lexemas[i]="<val>";   
                            } else if(herramientas.isCorrectNameVariable(lexemas[i])){
                                String[] res =  variablesDeclaradas.isRegisterVariable(lexemas[i]);
                                if(res[0].equals("verdadero")){ // es variable
                                    if(herramientas.isSymbolRelation(lexemas[i+1]) ){
                                        boolean isBool = false;
                                        boolean isNumber = false;
                                        if(herramientas.isCorrectFormatNumber(lexemas[i+2]))
                                            isNumber = true;
                                        else if(herramientas.isCorrectFormatBoolean(lexemas[i+2]))
                                            isBool = true;

                                        if(isBool){
                                            if(!res[1].equals("booleano")){
                                                log_tmp=lexemas[i]+" "+msj.ERROR_VARIABLE_TYPE_NOT_COMPATIBLE;
                                                break;
                                            } else{
                                                lexemas[i]="<var>";
                                            }
                                        }else if (isNumber){
                                            // MODIFICAR AQUI PARA ACEPTAR ( <var> )
                                            if(!res[1].equals("entero")){
                                                log_tmp=lexemas[i]+" "+msj.ERROR_VARIABLE_TYPE_NOT_COMPATIBLE;
                                                break;
                                            }else{
                                                lexemas[i]="<var>";
                                            }
                                        }

                                    }

                                } else{
                                    log_tmp = lexemas[i] +" "+msj.ERROR_VARIABLE_NOT_DECLARE;
                                    break;
                                }

                            } else {
                                if( lexemas[i].equals(")")){
                                    String[] res =  variablesDeclaradas.isRegisterVariable(lexemas[i-1]);
                                    if(res[0].equals("verdadero")){
                                        if(res[1].equals("booleano")){
                                            lexemas[i-1] = "<var>";
                                        }else{
                                            log_tmp=lexemas[i-1]+" "+msj.ERROR_VARIABLE_TYPE_NOT_COMPATIBLE;
                                        }

                                    } else{
                                        log_tmp = lexemas[i-1] +" "+msj.ERROR_VARIABLE_NOT_DECLARE;
                                    } 
                                    break;
                                }
                                log_tmp =  lexemas[i]+" "+msj.ERROR_VARIABLE_TYPE_NOT_DATATYPE;
                                break;
                            }

                        }
                    }
                    /*for(int i = 0; i < lexemas.length; i++){
                        System.out.print(CustomColors.BLUE+lexemas[i]+" ");   
                    }*/
                    isCorrect = automata.analizarRenglon(lexemas,  automatas.getPatronCiclo());

                    if ( isCorrect ){
                        // en teoria va bien, ahora hay que analizar que las variables esten registradas y que correspondan al tipo de dato con el que se compara
                        System.out.println(CustomColors.GREEN+bk_instruccion+" sucess");
                        nodes_ciclo++;
                    } else{
                        System.out.println(CustomColors.RED+bk_instruccion+" "+log_tmp);
                    }


                }
            }
            
        } else if(instruccion.split(" ")[0].equals("compara_si")){
            //System.out.println("detectado lea como inicio de instruccion. no HAGO NADA");
            String[] lexemas = instruccion.split(" ");
            if(lexemas.length < 3){
                isCorrect = false;
                System.out.println(CustomColors.BLUE+"Error en numero de lexemas");   
            }else{
                String log_tmp = "";
                for(int i = 2; i < lexemas.length; i++){
                    if(!herramientas.isSymbolRelation(lexemas[i])){  
                        if(herramientas.isCorrectFormatNumber(lexemas[i]) || herramientas.isCorrectFormatBoolean(lexemas[i])){
                            lexemas[i]="<val>";   
                        } else if(herramientas.isCorrectNameVariable(lexemas[i])){
                            String[] res =  variablesDeclaradas.isRegisterVariable(lexemas[i]);
                            if(res[0].equals("verdadero")){ // es variable
                                lexemas[i]="<var>";
                            } else{
                                log_tmp = lexemas[i] +" "+msj.ERROR_VARIABLE_NOT_DECLARE;
                                break;
                            }
                            
                        }
                    }
                }
                /*for(int i = 0; i < lexemas.length; i++){
                    System.out.print(CustomColors.BLUE+lexemas[i]+" ");   
                }*/
                isCorrect = automata.analizarRenglon(lexemas,  automatas.getPatronCondicion());
                
                if ( isCorrect ){
                    // en teoria va bien, ahora hay que analizar que las variables esten registradas y que correspondan al tipo de dato con el que se compara
                    System.out.println(CustomColors.GREEN+bk_instruccion+" sucess");
                    nodes_condicion++;
                } else{
                    System.out.println(CustomColors.RED+bk_instruccion+" "+log_tmp);
                }
                
            }
            
        } else if(instruccion.split(" ")[0].equals("asigna")){
            String tmp_log = "";
            String[] lexemas = instruccion.split(" ");
            if(lexemas.length > 3){
                if(herramientas.isCorrectNameVariable(lexemas[1])){
                    String[] res =  variablesDeclaradas.isRegisterVariable(lexemas[1]);
                    if(res[0].equals("verdadero")){
                        lexemas[1] = "<var>";
                        String data_type = res[1];
                        for(int i = 3; i < lexemas.length; i+=2){
                            // revisar si las variables estan registradas y su tipo de dato
                            String[] tmp_Res =  variablesDeclaradas.isRegisterVariable(lexemas[i]);
                            if(tmp_Res[0].equals("verdadero")){
                                if(data_type.equals(tmp_Res[1])){
                                    lexemas[i] = "<var>";
                                }else{
                                 tmp_log = msj.ERROR_VARIABLE_TYPE_NOT_COMPATIBLE;
                                break;   
                                }
                            }else{
                                if(data_type.equals("booleano")){
                                    if(herramientas.isCorrectFormatBoolean(lexemas[i])){
                                        lexemas[i] = "<val>";
                                    } else{
                                        tmp_log = msj.ERROR_VARIABLE_TYPE_NOT_COMPATIBLE;
                                        break;
                                    }
                                } else if(data_type.equals("cadena")){
                                    if(herramientas.isCorrectFormatString(lexemas[i])){
                                        lexemas[i] = "<val>";
                                    } else{
                                        tmp_log = msj.ERROR_VARIABLE_TYPE_NOT_COMPATIBLE;
                                        break;
                                    }
                                } else if(data_type.equals("entero")){
                                    if(herramientas.isCorrectFormatNumber(lexemas[i])){
                                        lexemas[i] = "<val>";
                                    } else{
                                        tmp_log = msj.ERROR_VARIABLE_TYPE_NOT_COMPATIBLE;
                                        break;
                                    }
                                }
                            }
                            //lexemas[i] = "<var>";
                        }
                        
                    } else{
                        tmp_log = msj.ERROR_VARIABLE_NOT_DECLARE;
                    }
                }else{
                    tmp_log = msj.ERROR_VARIABLE_NAME;
                }
            } else {
                tmp_log = "Error, not values pased";
            }
            //automata.recorrerAutomata(automatas.getPatronAsignacion());
            isCorrect = automata.analizarRenglon(lexemas, automatas.getPatronAsignacion());
            if ( isCorrect ){
                System.out.println(CustomColors.GREEN+bk_instruccion+" sucess");
            } else{
                System.out.println(CustomColors.RED+bk_instruccion+" "+tmp_log);
            }
        } else if(instruccion.split(" ")[0].equals("fin_ciclo")){
            if(nodes_ciclo < 1)
                System.out.println(CustomColors.RED+bk_instruccion+" error, ciclo no definido");
            else{
                nodes_ciclo--;
                isCorrect = true;
                System.out.println(CustomColors.GREEN+bk_instruccion+" sucess");
            }
        } else if(instruccion.split(" ")[0].equals("fin_compara")){
            if(nodes_condicion < 1)
                System.out.println(CustomColors.RED+bk_instruccion+" error, condicion no definido");
            else {
                nodes_condicion--;
                isCorrect = true;
                System.out.println(CustomColors.GREEN+bk_instruccion+" sucess");
            }
        } else {
            System.out.println(CustomColors.RED+bk_instruccion+" "+msj.ERROR_STATEMENT+" CE001");
        }
        
        return isCorrect;
           
    }
    
    public boolean isConditionsCorrects(){
        boolean flag = true;
        if(nodes_condicion > 0 || nodes_ciclo > 0 )
            flag = false;
        return flag;
    }
    public Vector getVars(){
        return this.simbolos;
    }
}
