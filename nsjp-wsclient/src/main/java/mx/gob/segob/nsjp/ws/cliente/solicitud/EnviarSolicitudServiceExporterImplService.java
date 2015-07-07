package mx.gob.segob.nsjp.ws.cliente.solicitud;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.4.2
 * 2013-08-16T13:45:31.507-05:00
 * Generated source version: 2.4.2
 * 
 */
@WebServiceClient(name = "EnviarSolicitudServiceExporterImplService", 
                  wsdlLocation = "http://localhost:49801/nsjp-web/EnviarSolicitudServiceExporterImplService?wsdl",
                  targetNamespace = "http://impl.ws.service.nsjp.segob.gob.mx/") 
public class EnviarSolicitudServiceExporterImplService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://impl.ws.service.nsjp.segob.gob.mx/", "EnviarSolicitudServiceExporterImplService");
    public final static QName EnviarSolicitudServiceExporterImplPort = new QName("http://impl.ws.service.nsjp.segob.gob.mx/", "EnviarSolicitudServiceExporterImplPort");
    static {
        URL url = null;
        try {
            url = new URL("http://localhost:49801/nsjp-web/EnviarSolicitudServiceExporterImplService?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(EnviarSolicitudServiceExporterImplService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://localhost:49801/nsjp-web/EnviarSolicitudServiceExporterImplService?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public EnviarSolicitudServiceExporterImplService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public EnviarSolicitudServiceExporterImplService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public EnviarSolicitudServiceExporterImplService() {
        super(WSDL_LOCATION, SERVICE);
    }
    

    /**
     *
     * @return
     *     returns EnviarSolicitudServiceExporterImpl
     */
    @WebEndpoint(name = "EnviarSolicitudServiceExporterImplPort")
    public EnviarSolicitudServiceExporterImpl getEnviarSolicitudServiceExporterImplPort() {
        return super.getPort(EnviarSolicitudServiceExporterImplPort, EnviarSolicitudServiceExporterImpl.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns EnviarSolicitudServiceExporterImpl
     */
    @WebEndpoint(name = "EnviarSolicitudServiceExporterImplPort")
    public EnviarSolicitudServiceExporterImpl getEnviarSolicitudServiceExporterImplPort(WebServiceFeature... features) {
        return super.getPort(EnviarSolicitudServiceExporterImplPort, EnviarSolicitudServiceExporterImpl.class, features);
    }

}
