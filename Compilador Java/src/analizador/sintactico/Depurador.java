
package analizador.sintactico;

public class Depurador {
    
    
    //Metodo llamado para depurar, regresa el contenido depurado
    public String depurar(String file){
        String new_file = filtroComentariosSimples(file);
        new_file = filtroComentariosDobles(new_file);
        new_file = filtroDobleEspacios(new_file);
        new_file = filtroCaracteresVacios(new_file);
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
            /*if(file.charAt(i) == '/'){
                try{
                    i++;
                    if(file.charAt(i) == '/'){
                        i++;
                        while(file.charAt(i) != '\n'){
                            System.out.print("limpiando");
                            i++;
                        }
                    }
                }catch(Exception e){
                    System.out.println(CustomColors.RED+"Error al depurar el codigo fuente. Problema en los comentarios.");
                }
            }else{
                new_file += file.charAt(i);
            }*/
        }
        return new_file;    
    }
    
    // retira espacios doble o mas de dos y los convierte a un solo espacio
    private String filtroDobleEspacios(String file){
        return file;
    }
    
    // retira tabs
    private String filtroCaracteresVacios(String file){
        return file;
    }
    
}
