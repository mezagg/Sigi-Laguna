
package mx.gob.segob.nsjp.ws.cliente.avidohdelictivo;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.4.2
 * 2013-08-16T13:45:38.047-05:00
 * Generated source version: 2.4.2
 */

@WebFault(name = "NSJPNegocioException", targetNamespace = "http://ws.service.nsjp.segob.gob.mx/")
public class NSJPNegocioException_Exception extends Exception {
    
    private mx.gob.segob.nsjp.ws.cliente.avidohdelictivo.NSJPNegocioException nsjpNegocioException;

    public NSJPNegocioException_Exception() {
        super();
    }
    
    public NSJPNegocioException_Exception(String message) {
        super(message);
    }
    
    public NSJPNegocioException_Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public NSJPNegocioException_Exception(String message, mx.gob.segob.nsjp.ws.cliente.avidohdelictivo.NSJPNegocioException nsjpNegocioException) {
        super(message);
        this.nsjpNegocioException = nsjpNegocioException;
    }

    public NSJPNegocioException_Exception(String message, mx.gob.segob.nsjp.ws.cliente.avidohdelictivo.NSJPNegocioException nsjpNegocioException, Throwable cause) {
        super(message, cause);
        this.nsjpNegocioException = nsjpNegocioException;
    }

    public mx.gob.segob.nsjp.ws.cliente.avidohdelictivo.NSJPNegocioException getFaultInfo() {
        return this.nsjpNegocioException;
    }
}
