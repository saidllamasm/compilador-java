package analizador.sintactico;

public class AnalizadorAutomatas {
    
    //Automatas automatas = new Automatas();
    Automata nodo_base = new Automata();
    
    public boolean analizarRenglon(String[] lexemas, Automata[] pattron){
        boolean state = false;
        nodo_base = pattron[0];
        boolean resp = false;
        for(int i = 0; i < lexemas.length; i++){
            String s = lexemas[i];
            if((i+1) == lexemas.length){
               resp = evaluarNodo(nodo_base,new String[]{lexemas[i], null});
            }else{
               resp = evaluarNodo(nodo_base, new String[]{lexemas[i], lexemas[i+1]});
            }
            if(!resp){ 
                //System.out.println(CustomColors.RED+lexemas[i+1]);
                break;
            }
            if((i+1) == lexemas.length){
                
                if(resp)
                    if(nodo_base.IsAccepted())
                        state = true;
                    //else
                        //System.out.println(CustomColors.RED+"Instrucción no aceptada");
                //else 
                    //System.out.println(CustomColors.RED+"Instrucción no aceptada");
            }
            resp = false;

        }
        return state;
    }
   
   // lexema[0] actual, lexema[1] siguiente o null si es fin de cadena
   private boolean evaluarNodo(Automata nodo, String[] lexema){
       boolean idFind = false;
       for(String id : nodo.getId()){
           if(id.equals(lexema[0])){
               idFind = true;
               break;
           }
       }
       boolean isCorrect = false;
       if(lexema[1]!=null){
            if(idFind){
                if(evaluarSiguienteNodo(nodo.getAddress(),lexema[1])){
                    isCorrect = true;
                }
            }
       } else if (lexema[1] == null)isCorrect = idFind;
       
       return isCorrect;
   }
   
   private boolean evaluarSiguienteNodo(Automata[] nodos_siguientes, String lexema){
       boolean find = false;
       for(Automata automata : nodos_siguientes){
           for(String id : automata.getId()){
               if(id.equals(lexema)){
                    find = true;
                    nodo_base = automata;
               }
           }
       }
       return find;
   }

   // No realiza operacion ni es importante, lo uso para mostrar el automata que voy a validar
   private static void recorrerAutomata(Automata[] arrayID){
       for(Automata automata : arrayID){
           for(String id : automata.getId()){
                System.out.print(" "+id);   
           }
       }
   }
   
}
