package mx.gob.segob.nsjp.ws.cliente.enviardocumentoainstitucion;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.4.2
 * 2013-08-16T13:45:24.412-05:00
 * Generated source version: 2.4.2
 * 
 */
@WebServiceClient(name = "EnviarDocumentoAInstitucionExporterImplService", 
                  wsdlLocation = "http://localhost:49801/nsjp-web/EnviarDocumentoAInstitucionExporterImplService?wsdl",
                  targetNamespace = "http://impl.ws.service.nsjp.segob.gob.mx/") 
public class EnviarDocumentoAInstitucionExporterImplService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://impl.ws.service.nsjp.segob.gob.mx/", "EnviarDocumentoAInstitucionExporterImplService");
    public final static QName EnviarDocumentoAInstitucionExporterImplPort = new QName("http://impl.ws.service.nsjp.segob.gob.mx/", "EnviarDocumentoAInstitucionExporterImplPort");
    static {
        URL url = null;
        try {
            url = new URL("http://localhost:49801/nsjp-web/EnviarDocumentoAInstitucionExporterImplService?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(EnviarDocumentoAInstitucionExporterImplService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://localhost:49801/nsjp-web/EnviarDocumentoAInstitucionExporterImplService?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public EnviarDocumentoAInstitucionExporterImplService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public EnviarDocumentoAInstitucionExporterImplService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public EnviarDocumentoAInstitucionExporterImplService() {
        super(WSDL_LOCATION, SERVICE);
    }
    

    /**
     *
     * @return
     *     returns EnviarDocumentoAInstitucionExporter
     */
    @WebEndpoint(name = "EnviarDocumentoAInstitucionExporterImplPort")
    public EnviarDocumentoAInstitucionExporter getEnviarDocumentoAInstitucionExporterImplPort() {
        return super.getPort(EnviarDocumentoAInstitucionExporterImplPort, EnviarDocumentoAInstitucionExporter.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns EnviarDocumentoAInstitucionExporter
     */
    @WebEndpoint(name = "EnviarDocumentoAInstitucionExporterImplPort")
    public EnviarDocumentoAInstitucionExporter getEnviarDocumentoAInstitucionExporterImplPort(WebServiceFeature... features) {
        return super.getPort(EnviarDocumentoAInstitucionExporterImplPort, EnviarDocumentoAInstitucionExporter.class, features);
    }

}
