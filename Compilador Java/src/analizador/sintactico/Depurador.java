
package analizador.sintactico;

public class Depurador {
    
    
    //Metodo llamado para depurar, regresa el contenido depurado
    public String depurar(String file){
        String new_file = filtroComentariosSimples(file); // //
        new_file = filtroComentariosDobles(new_file); // /*../
        new_file = filtroDobleEspacios(new_file);
        new_file = filtroCaracteresVacios(new_file); // como tabs
        new_file = filtroEspacioFinal(new_file);
        new_file = filtroRenglonVacio(new_file); 
        return new_file;
    }
    
    // retira los comentarios /* ... /
    private String filtroComentariosDobles(String file){
        String new_file = "";
        for(int i = 0; i < file.length(); i++){
            //System.out.println(CustomColors.PURPLE+" "+i);
            if(file.charAt(i) == '/'){
                try{
                    i++;
                    if(file.charAt(i) == '*'){
                        i++;
                        while(file.charAt(i) != '/'){
                            i++;
                        }
                    }
                }catch(Exception e){
                    System.out.println(CustomColors.RED+"Error al depurar el codigo fuente. Problema en los comentarios.");
                }
            }else{
                //System.out.println(CustomColors.CYAN+" "+i);
                new_file += file.charAt(i);
            }
        }
        return new_file;    
    }
    
    // retira los comentarios 
    private String filtroComentariosSimples(String file){
        String new_file = "";
        for(int i = 0; i < file.length(); i++){
            try{
                if(file.charAt(i) == '/' &&  file.charAt(i+1) == '/'){
                    while(file.charAt(i) != '\n')
                        i++;
                }
            }catch(Exception e){
               System.out.println(CustomColors.RED+"Error al depurar el codigo fuente. Problema en los comentarios."); 
            }
            new_file += file.charAt(i);
        }
        return new_file;    
    }
    
    // retira espacios doble o mas de dos y los convierte a un solo espacio
    private String filtroDobleEspacios(String file){
        return file.replaceAll(" +"," ");
    }
    
    // retira tabs
    private String filtroCaracteresVacios(String file){
        return file.replaceAll("	", "");
    }
    
    // elimina todos los renglones vacios -no estable-
    private String filtroRenglonVacio(String file){
        //return file.replaceAll("\\n{2}", "");
        return file.replaceAll("\\n+", "\n");
    }
    
    // elimina el espacio que el usuario puede dejar al final de una instruccion/rnglon
    private String filtroEspacioFinal(String file){
        return file.replaceAll(" \\n", "");
    }
    
}
