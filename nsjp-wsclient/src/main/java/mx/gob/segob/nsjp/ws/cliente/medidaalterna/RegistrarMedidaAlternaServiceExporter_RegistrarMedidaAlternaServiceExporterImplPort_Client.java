
package mx.gob.segob.nsjp.ws.cliente.medidaalterna;

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
 * 2013-08-16T13:45:46.031-05:00
 * Generated source version: 2.4.2
 * 
 */
public final class RegistrarMedidaAlternaServiceExporter_RegistrarMedidaAlternaServiceExporterImplPort_Client {

    private static final QName SERVICE_NAME = new QName("http://impl.ws.service.nsjp.segob.gob.mx/", "RegistrarMedidaAlternaServiceExporterImplService");

    private RegistrarMedidaAlternaServiceExporter_RegistrarMedidaAlternaServiceExporterImplPort_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = RegistrarMedidaAlternaServiceExporterImplService.WSDL_LOCATION;
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
      
        RegistrarMedidaAlternaServiceExporterImplService ss = new RegistrarMedidaAlternaServiceExporterImplService(wsdlURL, SERVICE_NAME);
        RegistrarMedidaAlternaServiceExporter port = ss.getRegistrarMedidaAlternaServiceExporterImplPort();  
        
        {
        System.out.println("Invoking registrarMedidaAlterna...");
        mx.gob.segob.nsjp.ws.cliente.medidaalterna.MedidaAlternaWSDTO _registrarMedidaAlterna_arg0 = null;
        try {
            port.registrarMedidaAlterna(_registrarMedidaAlterna_arg0);

        } catch (NSJPNegocioException_Exception e) { 
            System.out.println("Expected exception: NSJPNegocioException has occurred.");
            System.out.println(e.toString());
        }
            }

        System.exit(0);
    }

}
