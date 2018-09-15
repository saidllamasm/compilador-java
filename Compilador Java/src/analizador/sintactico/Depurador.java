
package analizador.sintactico;

public class Depurador {
    
    
    public String depurar(String file){
        return filterSpaces(file).replaceAll("\\t", "").replaceAll("^\\s+", "").replaceAll("(\\s\\n)+", "\n");
    }
    
    // retira espacios doble o mas de dos y los convierte a un solo espacio
    private String filterSpaces(String file){
        boolean error = false;
        String error_message = "";
        String val_file = "";
        
        for(int i = 0; i < file.length(); i++){
            if(file.charAt(i) == '"'){
                i++;
                String tmp = "\"";
                while(file.charAt(i)!='"'){
                    i++;
                    tmp += ""+file.charAt(i)+"";
                    if((i+1) == file.length()){
                        tmp = "";
                        error = true;
                        error_message = "No cerraste comillas";
                        System.out.println(error_message);
                        break;
                    }
                }
                val_file += tmp;
            }else{
                if(file.charAt(i) == ' '){
                    if(file.charAt(i+1) != ' '){
                        val_file += file.charAt(i);
                    }
                }else{
                    val_file += file.charAt(i);
                }
            }
        }
        return val_file;
    }
    
}
