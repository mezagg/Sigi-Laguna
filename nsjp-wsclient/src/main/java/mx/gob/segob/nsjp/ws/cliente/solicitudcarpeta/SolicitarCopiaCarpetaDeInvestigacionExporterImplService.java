package mx.gob.segob.nsjp.ws.cliente.solicitudcarpeta;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.4.2
 * 2013-08-16T13:46:05.281-05:00
 * Generated source version: 2.4.2
 * 
 */
@WebServiceClient(name = "SolicitarCopiaCarpetaDeInvestigacionExporterImplService", 
                  wsdlLocation = "http://localhost:49801/nsjp-web/SolicitarCopiaCarpetaDeInvestigacionExporterImplService?wsdl",
                  targetNamespace = "http://impl.ws.service.nsjp.segob.gob.mx/") 
public class SolicitarCopiaCarpetaDeInvestigacionExporterImplService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://impl.ws.service.nsjp.segob.gob.mx/", "SolicitarCopiaCarpetaDeInvestigacionExporterImplService");
    public final static QName SolicitarCopiaCarpetaDeInvestigacionExporterImplPort = new QName("http://impl.ws.service.nsjp.segob.gob.mx/", "SolicitarCopiaCarpetaDeInvestigacionExporterImplPort");
    static {
        URL url = null;
        try {
            url = new URL("http://localhost:49801/nsjp-web/SolicitarCopiaCarpetaDeInvestigacionExporterImplService?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(SolicitarCopiaCarpetaDeInvestigacionExporterImplService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://localhost:49801/nsjp-web/SolicitarCopiaCarpetaDeInvestigacionExporterImplService?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public SolicitarCopiaCarpetaDeInvestigacionExporterImplService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public SolicitarCopiaCarpetaDeInvestigacionExporterImplService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public SolicitarCopiaCarpetaDeInvestigacionExporterImplService() {
        super(WSDL_LOCATION, SERVICE);
    }
    

    /**
     *
     * @return
     *     returns SolicitarCopiaCarpetaDeInvestigacionExporter
     */
    @WebEndpoint(name = "SolicitarCopiaCarpetaDeInvestigacionExporterImplPort")
    public SolicitarCopiaCarpetaDeInvestigacionExporter getSolicitarCopiaCarpetaDeInvestigacionExporterImplPort() {
        return super.getPort(SolicitarCopiaCarpetaDeInvestigacionExporterImplPort, SolicitarCopiaCarpetaDeInvestigacionExporter.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns SolicitarCopiaCarpetaDeInvestigacionExporter
     */
    @WebEndpoint(name = "SolicitarCopiaCarpetaDeInvestigacionExporterImplPort")
    public SolicitarCopiaCarpetaDeInvestigacionExporter getSolicitarCopiaCarpetaDeInvestigacionExporterImplPort(WebServiceFeature... features) {
        return super.getPort(SolicitarCopiaCarpetaDeInvestigacionExporterImplPort, SolicitarCopiaCarpetaDeInvestigacionExporter.class, features);
    }

}
