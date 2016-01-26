/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.segob.nsjp.comun.excepcion;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;

/**
 *
 * @author gerardo
 */
public class NSJPCommunicationException extends Exception {

    
    private static final long serialVersionUID = 1L;
    
    CodigoError codigo;
     
    public NSJPCommunicationException(CodigoError codigoError, Throwable cause) {
        super(cause);
        codigo = codigoError;
    }

     public NSJPCommunicationException(CodigoError codigoError, String msg, Throwable cause) {
        super(msg, cause);
        codigo = codigoError;
    }

    public CodigoError getCodigo() {
        return codigo;
    }

    public void setCodigo(CodigoError codigo) {
        this.codigo = codigo;
    }
}
