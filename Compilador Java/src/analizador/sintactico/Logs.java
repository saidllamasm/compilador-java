
package analizador.sintactico;

/**
 *
 * Contiene los mensajes de error que se mandan a imprimir asi como los de exito
 */
public class Logs {
    
    // errores
    protected final String ERROR_VARIABLE_NOT_DECLARE = "cannot find symbol";
    protected final String ERROR_VARIABLE_TYPE_NOT_COMPATIBLE = "incompatible types";
    protected final String ERROR_VARIABLE_TYPE_NOT_DATATYPE = "it is not a data type";
    protected final String ERROR_VARIABLE_ALREADY_DEFINE = "variable is already defined";
    protected final String ERROR_STATEMENT = "not a statement";
    
    
    // exitos
    protected final String SUCCESS_BUILD = "BUILD SUCCESSFUL";
    
}
