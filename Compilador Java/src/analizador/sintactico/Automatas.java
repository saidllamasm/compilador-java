
package analizador.sintactico;

public class Automatas {
    
    private Automata INSTRUCCION_CREA[] = new Automata[8];
    private Automata INSTRUCCION_IMPRIME[] = new Automata[3];
    
    public Automatas(){
        
        // crea
        INSTRUCCION_CREA = crearArrayAutomata(INSTRUCCION_CREA);
       
        INSTRUCCION_CREA[0].setId(new String[]{"crea"});
        INSTRUCCION_CREA[0].setAddress(new Automata[]{
            INSTRUCCION_CREA[1],
            INSTRUCCION_CREA[2],
            INSTRUCCION_CREA[3]
        });
       
        INSTRUCCION_CREA[1].setId(new String[]{"entero"});
        INSTRUCCION_CREA[1].setAddress(new Automata[]{
            INSTRUCCION_CREA[4]
        });

        INSTRUCCION_CREA[2].setId(new String[]{"cadena"});
        INSTRUCCION_CREA[2].setAddress(new Automata[]{
            INSTRUCCION_CREA[4]
        });

        INSTRUCCION_CREA[3].setId(new String[]{"booleano"});
        INSTRUCCION_CREA[3].setAddress(new Automata[]{
            INSTRUCCION_CREA[4]
        });

        INSTRUCCION_CREA[4].setId(new String[]{"<var>"});
        INSTRUCCION_CREA[4].setIsAccepted(true);
        INSTRUCCION_CREA[4].setAddress(new Automata[]{
            INSTRUCCION_CREA[5]
        });
        
        INSTRUCCION_CREA[5].setId(new String[]{"="});
        INSTRUCCION_CREA[5].setAddress(new Automata[]{
            INSTRUCCION_CREA[6]
        });
        
        INSTRUCCION_CREA[6].setId(new String[]{"<var>","<val>"});
        INSTRUCCION_CREA[6].setIsAccepted(true);
        INSTRUCCION_CREA[6].setAddress(new Automata[]{
            INSTRUCCION_CREA[7]
        });
        
        INSTRUCCION_CREA[7].setId(new String[]{"+","-"});
        INSTRUCCION_CREA[7].setAddress(new Automata[]{
            INSTRUCCION_CREA[6]
        });
        
        // imprime
        INSTRUCCION_IMPRIME = crearArrayAutomata(INSTRUCCION_IMPRIME);
        
        INSTRUCCION_IMPRIME[0].setId(new String[]{"imprime"});
        INSTRUCCION_IMPRIME[0].setAddress(new Automata[]{
            INSTRUCCION_IMPRIME[1],
        });
        
        INSTRUCCION_IMPRIME[1].setId(new String[]{"<var>","<val>"});
        INSTRUCCION_IMPRIME[1].setIsAccepted(true);
        INSTRUCCION_IMPRIME[1].setAddress(new Automata[]{
            INSTRUCCION_IMPRIME[2]
        });
        
        INSTRUCCION_IMPRIME[2].setId(new String[]{"."});
        INSTRUCCION_IMPRIME[2].setAddress(new Automata[]{
            INSTRUCCION_IMPRIME[1]
        });
        
        
        
    }
    
    protected Automata[] getPatronInstancia(){
        return this.INSTRUCCION_CREA;
    }
    
    
    
    public static Automata[] crearArrayAutomata(Automata[] automata){
        for(int i = 0; i < automata.length; i++ )
            automata[i] = new Automata();
        return automata;
   }
}
