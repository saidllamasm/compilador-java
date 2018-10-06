
package analizador.sintactico;

public class Automatas {
    
    private Automata INSTRUCCION_CREA[] = new Automata[8];
    private Automata INSTRUCCION_IMPRIME[] = new Automata[3];
    private Automata INSTRUCCION_ASIGNA[] = new Automata[5];
    private Automata INSTRUCCION_CICLO[] = new Automata[7];
    private Automata INSTRUCCION_CONDICION[] = new Automata[7];
    
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
        
        // ASIGNA
        INSTRUCCION_ASIGNA = crearArrayAutomata(INSTRUCCION_ASIGNA);
        
        INSTRUCCION_ASIGNA[0].setId(new String[]{"asigna"});
        INSTRUCCION_ASIGNA[0].setAddress(new Automata[]{
            INSTRUCCION_ASIGNA[1],
        });
        
        INSTRUCCION_ASIGNA[1].setId(new String[]{"<var>"});
        INSTRUCCION_ASIGNA[1].setAddress(new Automata[]{
            INSTRUCCION_ASIGNA[2],
        });
        
        INSTRUCCION_ASIGNA[2].setId(new String[]{"="});
        INSTRUCCION_ASIGNA[2].setAddress(new Automata[]{
            INSTRUCCION_ASIGNA[3],
        });
        
        INSTRUCCION_ASIGNA[3].setId(new String[]{"<var>", "<val>"});
        INSTRUCCION_ASIGNA[3].setIsAccepted(true);
        INSTRUCCION_ASIGNA[3].setAddress(new Automata[]{
            INSTRUCCION_ASIGNA[4],
        });
        INSTRUCCION_ASIGNA[4].setId(new String[]{"+","-"});
        INSTRUCCION_ASIGNA[4].setAddress(new Automata[]{
            INSTRUCCION_ASIGNA[3],
        });
        
        // ciclo
        INSTRUCCION_CICLO = crearArrayAutomata(INSTRUCCION_CICLO);
        INSTRUCCION_CICLO[0].setId(new String[]{"ciclo"});
        INSTRUCCION_CICLO[0].setAddress(new Automata[]{
            INSTRUCCION_CICLO[1],
        });
        
        INSTRUCCION_CICLO[1].setId(new String[]{"("});
        INSTRUCCION_CICLO[1].setAddress(new Automata[]{
            INSTRUCCION_CICLO[2],
        });
        
        INSTRUCCION_CICLO[2].setId(new String[]{"<var>"});
        INSTRUCCION_CICLO[2].setAddress(new Automata[]{
            INSTRUCCION_CICLO[3],
            INSTRUCCION_CICLO[5],
            INSTRUCCION_CICLO[6],
            
        });
        
        INSTRUCCION_CICLO[3].setId(new String[]{"<", ">","==","!="});
        INSTRUCCION_CICLO[3].setAddress(new Automata[]{
            INSTRUCCION_CICLO[4],
        });
        
        INSTRUCCION_CICLO[4].setId(new String[]{"<var>","<val>"});
        INSTRUCCION_CICLO[4].setAddress(new Automata[]{
            INSTRUCCION_CICLO[5],
            INSTRUCCION_CICLO[6],
        });
        
        INSTRUCCION_CICLO[5].setId(new String[]{")"});
        INSTRUCCION_CICLO[5].setIsAccepted(true);
        INSTRUCCION_CICLO[5].setAddress(new Automata[]{
            //duda
            
        });
        
        INSTRUCCION_CICLO[6].setId(new String[]{"||","&&"});
        INSTRUCCION_CICLO[6].setAddress(new Automata[]{
            INSTRUCCION_CICLO[2],
        });
        
        // condicion
        INSTRUCCION_CONDICION = crearArrayAutomata(INSTRUCCION_CONDICION);
        INSTRUCCION_CONDICION[0].setId(new String[]{"compara_si"});
        INSTRUCCION_CONDICION[0].setAddress(new Automata[]{
            INSTRUCCION_CONDICION[1],
        });
        
        INSTRUCCION_CONDICION[1].setId(new String[]{"("});
        INSTRUCCION_CONDICION[1].setAddress(new Automata[]{
            INSTRUCCION_CONDICION[2],
        });
        
        INSTRUCCION_CONDICION[2].setId(new String[]{"<var>"});
        INSTRUCCION_CONDICION[2].setAddress(new Automata[]{
            INSTRUCCION_CONDICION[3],
            INSTRUCCION_CONDICION[5],
            INSTRUCCION_CONDICION[6],
            
        });
        
        INSTRUCCION_CONDICION[3].setId(new String[]{"<", ">","==","!="});
        INSTRUCCION_CONDICION[3].setAddress(new Automata[]{
            INSTRUCCION_CONDICION[4],
        });
        
        INSTRUCCION_CONDICION[4].setId(new String[]{"<var>","<val>"});
        INSTRUCCION_CONDICION[4].setAddress(new Automata[]{
            INSTRUCCION_CONDICION[5],
            INSTRUCCION_CONDICION[6],
        });
        
        INSTRUCCION_CONDICION[5].setId(new String[]{")"});
        INSTRUCCION_CONDICION[5].setIsAccepted(true);
        INSTRUCCION_CONDICION[5].setAddress(new Automata[]{
            //duda
            
        });
        
        INSTRUCCION_CONDICION[6].setId(new String[]{"||","&&"});
        INSTRUCCION_CONDICION[6].setAddress(new Automata[]{
            INSTRUCCION_CONDICION[2],
        });
        
    }
    
    protected Automata[] getPatronAsignacion(){
        return this.INSTRUCCION_ASIGNA;
    }
    
    protected Automata[] getPatronCondicion(){
        return this.INSTRUCCION_CONDICION;
    }
    protected Automata[] getPatronCiclo(){
        return this.INSTRUCCION_CICLO;
    }
    protected Automata[] getPatronInstancia(){
        return this.INSTRUCCION_CREA;
    }
    
    protected Automata[] getPatronImprime(){
        return this.INSTRUCCION_IMPRIME;
    }
    
    
    public static Automata[] crearArrayAutomata(Automata[] automata){
        for(int i = 0; i < automata.length; i++ )
            automata[i] = new Automata();
        return automata;
   }
}
