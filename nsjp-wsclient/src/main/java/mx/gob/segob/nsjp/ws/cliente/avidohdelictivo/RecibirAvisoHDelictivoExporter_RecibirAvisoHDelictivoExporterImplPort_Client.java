
package mx.gob.segob.nsjp.ws.cliente.avidohdelictivo;

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
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.FaultAction;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.4.2
 * 2013-08-16T13:45:37.993-05:00
 * Generated source version: 2.4.2
 * 
 */
public final class RecibirAvisoHDelictivoExporter_RecibirAvisoHDelictivoExporterImplPort_Client {

    private static final QName SERVICE_NAME = new QName("http://impl.ws.service.nsjp.segob.gob.mx/", "RecibirAvisoHDelictivoExporterImplService");

    private RecibirAvisoHDelictivoExporter_RecibirAvisoHDelictivoExporterImplPort_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = RecibirAvisoHDelictivoExporterImplService.WSDL_LOCATION;
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
      
        RecibirAvisoHDelictivoExporterImplService ss = new RecibirAvisoHDelictivoExporterImplService(wsdlURL, SERVICE_NAME);
        RecibirAvisoHDelictivoExporter port = ss.getRecibirAvisoHDelictivoExporterImplPort();  
        
        {
        System.out.println("Invoking recibirAvisoHDelictivoService...");
        mx.gob.segob.nsjp.ws.cliente.avidohdelictivo.AvisoHechoDelictivoWSDTO _recibirAvisoHDelictivoService_arg0 = null;
        try {
            port.recibirAvisoHDelictivoService(_recibirAvisoHDelictivoService_arg0);

        } catch (NSJPNegocioException_Exception e) { 
            System.out.println("Expected exception: NSJPNegocioException has occurred.");
            System.out.println(e.toString());
        }
            }

        System.exit(0);
    }

}
