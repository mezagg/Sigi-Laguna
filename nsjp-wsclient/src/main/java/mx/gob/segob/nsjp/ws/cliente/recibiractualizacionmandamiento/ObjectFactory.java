
package mx.gob.segob.nsjp.ws.cliente.recibiractualizacionmandamiento;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the mx.gob.segob.nsjp.ws.cliente.recibiractualizacionmandamiento package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _RecibirActualizacionMandamientoInterInstitucional_QNAME = new QName("http://ws.service.nsjp.segob.gob.mx/", "recibirActualizacionMandamientoInterInstitucional");
    private final static QName _RecibirActualizacionMandamientoInterInstitucionalResponse_QNAME = new QName("http://ws.service.nsjp.segob.gob.mx/", "recibirActualizacionMandamientoInterInstitucionalResponse");
    private final static QName _NSJPNegocioException_QNAME = new QName("http://ws.service.nsjp.segob.gob.mx/", "NSJPNegocioException");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: mx.gob.segob.nsjp.ws.cliente.recibiractualizacionmandamiento
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RecibirActualizacionMandamientoInterInstitucional }
     * 
     */
    public RecibirActualizacionMandamientoInterInstitucional createRecibirActualizacionMandamientoInterInstitucional() {
        return new RecibirActualizacionMandamientoInterInstitucional();
    }

    /**
     * Create an instance of {@link RecibirActualizacionMandamientoInterInstitucionalResponse }
     * 
     */
    public RecibirActualizacionMandamientoInterInstitucionalResponse createRecibirActualizacionMandamientoInterInstitucionalResponse() {
        return new RecibirActualizacionMandamientoInterInstitucionalResponse();
    }

    /**
     * Create an instance of {@link NSJPNegocioException }
     * 
     */
    public NSJPNegocioException createNSJPNegocioException() {
        return new NSJPNegocioException();
    }

    /**
     * Create an instance of {@link PersonaWSDTO }
     * 
     */
    public PersonaWSDTO createPersonaWSDTO() {
        return new PersonaWSDTO();
    }

    /**
     * Create an instance of {@link CalidadWSDTO }
     * 
     */
    public CalidadWSDTO createCalidadWSDTO() {
        return new CalidadWSDTO();
    }

    /**
     * Create an instance of {@link ArchivoDigitalWSDTO }
     * 
     */
    public ArchivoDigitalWSDTO createArchivoDigitalWSDTO() {
        return new ArchivoDigitalWSDTO();
    }

    /**
     * Create an instance of {@link NombreDemograficoWSDTO }
     * 
     */
    public NombreDemograficoWSDTO createNombreDemograficoWSDTO() {
        return new NombreDemograficoWSDTO();
    }

    /**
     * Create an instance of {@link CorreoElectronicoWSDTO }
     * 
     */
    public CorreoElectronicoWSDTO createCorreoElectronicoWSDTO() {
        return new CorreoElectronicoWSDTO();
    }

    /**
     * Create an instance of {@link ElementoWSDTO }
     * 
     */
    public ElementoWSDTO createElementoWSDTO() {
        return new ElementoWSDTO();
    }

    /**
     * Create an instance of {@link GenericWSDTO }
     * 
     */
    public GenericWSDTO createGenericWSDTO() {
        return new GenericWSDTO();
    }

    /**
     * Create an instance of {@link LugarWSDTO }
     * 
     */
    public LugarWSDTO createLugarWSDTO() {
        return new LugarWSDTO();
    }

    /**
     * Create an instance of {@link MandamientoWSDTO }
     * 
     */
    public MandamientoWSDTO createMandamientoWSDTO() {
        return new MandamientoWSDTO();
    }

    /**
     * Create an instance of {@link TelefonoWSDTO }
     * 
     */
    public TelefonoWSDTO createTelefonoWSDTO() {
        return new TelefonoWSDTO();
    }

    /**
     * Create an instance of {@link DocumentoWSDTO }
     * 
     */
    public DocumentoWSDTO createDocumentoWSDTO() {
        return new DocumentoWSDTO();
    }

    /**
     * Create an instance of {@link DelitoPersonaWSDTO }
     * 
     */
    public DelitoPersonaWSDTO createDelitoPersonaWSDTO() {
        return new DelitoPersonaWSDTO();
    }

    /**
     * Create an instance of {@link ActividadWSDTO }
     * 
     */
    public ActividadWSDTO createActividadWSDTO() {
        return new ActividadWSDTO();
    }

    /**
     * Create an instance of {@link MandamientoPersonaWSDTO }
     * 
     */
    public MandamientoPersonaWSDTO createMandamientoPersonaWSDTO() {
        return new MandamientoPersonaWSDTO();
    }

    /**
     * Create an instance of {@link DomicilioWSDTO }
     * 
     */
    public DomicilioWSDTO createDomicilioWSDTO() {
        return new DomicilioWSDTO();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RecibirActualizacionMandamientoInterInstitucional }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.service.nsjp.segob.gob.mx/", name = "recibirActualizacionMandamientoInterInstitucional")
    public JAXBElement<RecibirActualizacionMandamientoInterInstitucional> createRecibirActualizacionMandamientoInterInstitucional(RecibirActualizacionMandamientoInterInstitucional value) {
        return new JAXBElement<RecibirActualizacionMandamientoInterInstitucional>(_RecibirActualizacionMandamientoInterInstitucional_QNAME, RecibirActualizacionMandamientoInterInstitucional.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RecibirActualizacionMandamientoInterInstitucionalResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.service.nsjp.segob.gob.mx/", name = "recibirActualizacionMandamientoInterInstitucionalResponse")
    public JAXBElement<RecibirActualizacionMandamientoInterInstitucionalResponse> createRecibirActualizacionMandamientoInterInstitucionalResponse(RecibirActualizacionMandamientoInterInstitucionalResponse value) {
        return new JAXBElement<RecibirActualizacionMandamientoInterInstitucionalResponse>(_RecibirActualizacionMandamientoInterInstitucionalResponse_QNAME, RecibirActualizacionMandamientoInterInstitucionalResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NSJPNegocioException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.service.nsjp.segob.gob.mx/", name = "NSJPNegocioException")
    public JAXBElement<NSJPNegocioException> createNSJPNegocioException(NSJPNegocioException value) {
        return new JAXBElement<NSJPNegocioException>(_NSJPNegocioException_QNAME, NSJPNegocioException.class, null, value);
    }

}
