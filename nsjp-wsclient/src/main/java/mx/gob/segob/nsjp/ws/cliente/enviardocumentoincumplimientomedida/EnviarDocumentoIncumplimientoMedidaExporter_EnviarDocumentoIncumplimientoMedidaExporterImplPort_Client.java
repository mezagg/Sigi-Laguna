
package mx.gob.segob.nsjp.ws.cliente.enviardocumentoincumplimientomedida;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.FaultAction;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.4.2
 * 2013-08-16T13:45:26.152-05:00
 * Generated source version: 2.4.2
 * 
 */
public final class EnviarDocumentoIncumplimientoMedidaExporter_EnviarDocumentoIncumplimientoMedidaExporterImplPort_Client {

    private static final QName SERVICE_NAME = new QName("http://impl.ws.service.nsjp.segob.gob.mx/", "EnviarDocumentoIncumplimientoMedidaExporterImplService");

    private EnviarDocumentoIncumplimientoMedidaExporter_EnviarDocumentoIncumplimientoMedidaExporterImplPort_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = EnviarDocumentoIncumplimientoMedidaExporterImplService.WSDL_LOCATION;
        if (args.length > 0 && args[0] != null && !"".equals(args[0])) { 
            File wsdlFile = new File(args[0]);
            try {
                if (wsdlFile.exists()) {
                    wsdlURL = wsdlFile.toURI().toURL();
                } else {
                    wsdlURL = new URL(args[0]);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
      
        EnviarDocumentoIncumplimientoMedidaExporterImplService ss = new EnviarDocumentoIncumplimientoMedidaExporterImplService(wsdlURL, SERVICE_NAME);
        EnviarDocumentoIncumplimientoMedidaExporter port = ss.getEnviarDocumentoIncumplimientoMedidaExporterImplPort();  
        
        {
        System.out.println("Invoking enviarDocumentoDeIncumplimientoDeMedida...");
        mx.gob.segob.nsjp.ws.cliente.enviardocumentoincumplimientomedida.DocumentoWSDTO _enviarDocumentoDeIncumplimientoDeMedida_arg0 = null;
        java.lang.String _enviarDocumentoDeIncumplimientoDeMedida_arg1 = "";
        java.lang.String _enviarDocumentoDeIncumplimientoDeMedida_arg2 = "";
        try {
            java.lang.Long _enviarDocumentoDeIncumplimientoDeMedida__return = port.enviarDocumentoDeIncumplimientoDeMedida(_enviarDocumentoDeIncumplimientoDeMedida_arg0, _enviarDocumentoDeIncumplimientoDeMedida_arg1, _enviarDocumentoDeIncumplimientoDeMedida_arg2);
            System.out.println("enviarDocumentoDeIncumplimientoDeMedida.result=" + _enviarDocumentoDeIncumplimientoDeMedida__return);

        } catch (NSJPNegocioException_Exception e) { 
            System.out.println("Expected exception: NSJPNegocioException has occurred.");
            System.out.println(e.toString());
        }
            }

        System.exit(0);
    }

}
