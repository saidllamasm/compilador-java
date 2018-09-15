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
            System.out.println("detectado imprime como inicio de instruccion. no HAGO NADA");
            isCorrect = true;
        } 
        else if(instruccion.split(" ")[0].equals("lea")){
            //System.out.println("detectado lea como inicio de instruccion. no HAGO NADA");
            String[] lexemas = instruccion.split(" ");
            if(lexemas.length == 2){
                String[] res =  variablesDeclaradas.isRegisterVariable(lexemas[1]);
                //System.out.println(CustomColors.GREEN+bk_instruccion+" success");
                if(res[0].equals("verdadero")){
                    System.out.println(CustomColors.GREEN+bk_instruccion+" success");
                    isCorrect = true;
                }else{
                    System.out.println(CustomColors.RED+bk_instruccion+" "+msj.ERROR_VARIABLE_NOT_DECLARE+" CE1432");
                }
            }else{
                System.out.println(CustomColors.RED+bk_instruccion+" "+msj.ERROR_STATEMENT+" CE1022");
            }
        } else {
             System.out.println(CustomColors.RED+bk_instruccion+" "+msj.ERROR_STATEMENT+" CE001");
        }
     
        /*System.out.println(CustomColors.YELLOW_BOLD+"Tabla de simbolos");
        variablesDeclaradas.viewAllVarsDefined();*/
        /*
        String test = "crea entero v = \"ssddsds_____sdsds\" ";
        
        for(String t : test.split(" ")){
            System.out.println(herramientas.isCorrectFormatString(t));    
        }
        
        String testt = "\"hola___mundooo_\"";
        System.out.println(herramientas.printString(herramientas.cleanVarString(testt)));
        */
        return isCorrect;
           
    }
    
    public Vector getVars(){
        return this.simbolos;
    }
}
