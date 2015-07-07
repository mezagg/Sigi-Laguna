
package mx.gob.segob.nsjp.ws.cliente.solicitud;

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
 * 2013-08-16T13:45:31.390-05:00
 * Generated source version: 2.4.2
 * 
 */
public final class EnviarSolicitudServiceExporterImpl_EnviarSolicitudServiceExporterImplPort_Client {

    private static final QName SERVICE_NAME = new QName("http://impl.ws.service.nsjp.segob.gob.mx/", "EnviarSolicitudServiceExporterImplService");

    private EnviarSolicitudServiceExporterImpl_EnviarSolicitudServiceExporterImplPort_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = EnviarSolicitudServiceExporterImplService.WSDL_LOCATION;
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
      
        EnviarSolicitudServiceExporterImplService ss = new EnviarSolicitudServiceExporterImplService(wsdlURL, SERVICE_NAME);
        EnviarSolicitudServiceExporterImpl port = ss.getEnviarSolicitudServiceExporterImplPort();  
        
        {
        System.out.println("Invoking recibirSolicitud...");
        mx.gob.segob.nsjp.ws.cliente.solicitud.SolicitudWSDTO _recibirSolicitud_arg0 = null;
        try {
            mx.gob.segob.nsjp.ws.cliente.solicitud.SolicitudWSDTO _recibirSolicitud__return = port.recibirSolicitud(_recibirSolicitud_arg0);
            System.out.println("recibirSolicitud.result=" + _recibirSolicitud__return);

        } catch (NSJPNegocioException_Exception e) { 
            System.out.println("Expected exception: NSJPNegocioException has occurred.");
            System.out.println(e.toString());
        }
            }

        System.exit(0);
    }

}
