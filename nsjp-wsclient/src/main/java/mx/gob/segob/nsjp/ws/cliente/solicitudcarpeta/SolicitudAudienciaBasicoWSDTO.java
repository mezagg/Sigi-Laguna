
package mx.gob.segob.nsjp.ws.cliente.solicitudcarpeta;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for solicitudAudienciaBasicoWSDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="solicitudAudienciaBasicoWSDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.service.nsjp.segob.gob.mx/}genericWSDTO">
 *       &lt;sequence>
 *         &lt;element name="areaDestino" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="areaSolicitanteId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="audiencia" type="{http://ws.service.nsjp.segob.gob.mx/}audienciaWSDTO" minOccurs="0"/>
 *         &lt;element name="casoWSDTO" type="{http://ws.service.nsjp.segob.gob.mx/}casoWSDTO" minOccurs="0"/>
 *         &lt;element name="claveFuncionarioId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="distritoId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="fechaLimite" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="folioSolicitud" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="funcionarioExternoDestinatario" type="{http://ws.service.nsjp.segob.gob.mx/}funcionarioExternoWSDTO" minOccurs="0"/>
 *         &lt;element name="funcionarioExternoSolicitante" type="{http://ws.service.nsjp.segob.gob.mx/}funcionarioExternoWSDTO" minOccurs="0"/>
 *         &lt;element name="idFuncionarioSolicitante" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="involucradosDTO" type="{http://ws.service.nsjp.segob.gob.mx/}involucradoWSDTO" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="nombreInvolucrado" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nombreSolicitante" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroCasoAsociado" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="objetosDTO" type="{http://ws.service.nsjp.segob.gob.mx/}objetoWSDTO" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="relacionesDelitoPersona" type="{http://ws.service.nsjp.segob.gob.mx/}delitoPersonaWSDTO" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="solicitante" type="{http://ws.service.nsjp.segob.gob.mx/}funcionarioWSDTO" minOccurs="0"/>
 *         &lt;element name="solicitanteExternoId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="solicitudId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="tribunalId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "solicitudAudienciaBasicoWSDTO", propOrder = {
    "areaDestino",
    "areaSolicitanteId",
    "audiencia",
    "casoWSDTO",
    "claveFuncionarioId",
    "distritoId",
    "fechaLimite",
    "folioSolicitud",
    "funcionarioExternoDestinatario",
    "funcionarioExternoSolicitante",
    "idFuncionarioSolicitante",
    "involucradosDTO",
    "nombreInvolucrado",
    "nombreSolicitante",
    "numeroCasoAsociado",
    "objetosDTO",
    "relacionesDelitoPersona",
    "solicitante",
    "solicitanteExternoId",
    "solicitudId",
    "tribunalId"
})
public class SolicitudAudienciaBasicoWSDTO
    extends GenericWSDTO
{

    protected Long areaDestino;
    protected Long areaSolicitanteId;
    protected AudienciaWSDTO audiencia;
    protected CasoWSDTO casoWSDTO;
    protected Long claveFuncionarioId;
    protected Long distritoId;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaLimite;
    protected String folioSolicitud;
    protected FuncionarioExternoWSDTO funcionarioExternoDestinatario;
    protected FuncionarioExternoWSDTO funcionarioExternoSolicitante;
    protected Long idFuncionarioSolicitante;
    @XmlElement(nillable = true)
    protected List<InvolucradoWSDTO> involucradosDTO;
    protected String nombreInvolucrado;
    protected String nombreSolicitante;
    protected String numeroCasoAsociado;
    @XmlElement(nillable = true)
    protected List<ObjetoWSDTO> objetosDTO;
    @XmlElement(nillable = true)
    protected List<DelitoPersonaWSDTO> relacionesDelitoPersona;
    protected FuncionarioWSDTO solicitante;
    protected Long solicitanteExternoId;
    protected Long solicitudId;
    protected Long tribunalId;

    /**
     * Gets the value of the areaDestino property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getAreaDestino() {
        return areaDestino;
    }

    /**
     * Sets the value of the areaDestino property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setAreaDestino(Long value) {
        this.areaDestino = value;
    }

    /**
     * Gets the value of the areaSolicitanteId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getAreaSolicitanteId() {
        return areaSolicitanteId;
    }

    /**
     * Sets the value of the areaSolicitanteId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setAreaSolicitanteId(Long value) {
        this.areaSolicitanteId = value;
    }

    /**
     * Gets the value of the audiencia property.
     * 
     * @return
     *     possible object is
     *     {@link AudienciaWSDTO }
     *     
     */
    public AudienciaWSDTO getAudiencia() {
        return audiencia;
    }

    /**
     * Sets the value of the audiencia property.
     * 
     * @param value
     *     allowed object is
     *     {@link AudienciaWSDTO }
     *     
     */
    public void setAudiencia(AudienciaWSDTO value) {
        this.audiencia = value;
    }

    /**
     * Gets the value of the casoWSDTO property.
     * 
     * @return
     *     possible object is
     *     {@link CasoWSDTO }
     *     
     */
    public CasoWSDTO getCasoWSDTO() {
        return casoWSDTO;
    }

    /**
     * Sets the value of the casoWSDTO property.
     * 
     * @param value
     *     allowed object is
     *     {@link CasoWSDTO }
     *     
     */
    public void setCasoWSDTO(CasoWSDTO value) {
        this.casoWSDTO = value;
    }

    /**
     * Gets the value of the claveFuncionarioId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getClaveFuncionarioId() {
        return claveFuncionarioId;
    }

    /**
     * Sets the value of the claveFuncionarioId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setClaveFuncionarioId(Long value) {
        this.claveFuncionarioId = value;
    }

    /**
     * Gets the value of the distritoId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getDistritoId() {
        return distritoId;
    }

    /**
     * Sets the value of the distritoId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setDistritoId(Long value) {
        this.distritoId = value;
    }

    /**
     * Gets the value of the fechaLimite property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaLimite() {
        return fechaLimite;
    }

    /**
     * Sets the value of the fechaLimite property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaLimite(XMLGregorianCalendar value) {
        this.fechaLimite = value;
    }

    /**
     * Gets the value of the folioSolicitud property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFolioSolicitud() {
        return folioSolicitud;
    }

    /**
     * Sets the value of the folioSolicitud property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFolioSolicitud(String value) {
        this.folioSolicitud = value;
    }

    /**
     * Gets the value of the funcionarioExternoDestinatario property.
     * 
     * @return
     *     possible object is
     *     {@link FuncionarioExternoWSDTO }
     *     
     */
    public FuncionarioExternoWSDTO getFuncionarioExternoDestinatario() {
        return funcionarioExternoDestinatario;
    }

    /**
     * Sets the value of the funcionarioExternoDestinatario property.
     * 
     * @param value
     *     allowed object is
     *     {@link FuncionarioExternoWSDTO }
     *     
     */
    public void setFuncionarioExternoDestinatario(FuncionarioExternoWSDTO value) {
        this.funcionarioExternoDestinatario = value;
    }

    /**
     * Gets the value of the funcionarioExternoSolicitante property.
     * 
     * @return
     *     possible object is
     *     {@link FuncionarioExternoWSDTO }
     *     
     */
    public FuncionarioExternoWSDTO getFuncionarioExternoSolicitante() {
        return funcionarioExternoSolicitante;
    }

    /**
     * Sets the value of the funcionarioExternoSolicitante property.
     * 
     * @param value
     *     allowed object is
     *     {@link FuncionarioExternoWSDTO }
     *     
     */
    public void setFuncionarioExternoSolicitante(FuncionarioExternoWSDTO value) {
        this.funcionarioExternoSolicitante = value;
    }

    /**
     * Gets the value of the idFuncionarioSolicitante property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getIdFuncionarioSolicitante() {
        return idFuncionarioSolicitante;
    }

    /**
     * Sets the value of the idFuncionarioSolicitante property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setIdFuncionarioSolicitante(Long value) {
        this.idFuncionarioSolicitante = value;
    }

    /**
     * Gets the value of the involucradosDTO property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the involucradosDTO property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInvolucradosDTO().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InvolucradoWSDTO }
     * 
     * 
     */
    public List<InvolucradoWSDTO> getInvolucradosDTO() {
        if (involucradosDTO == null) {
            involucradosDTO = new ArrayList<InvolucradoWSDTO>();
        }
        return this.involucradosDTO;
    }

    /**
     * Gets the value of the nombreInvolucrado property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreInvolucrado() {
        return nombreInvolucrado;
    }

    /**
     * Sets the value of the nombreInvolucrado property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreInvolucrado(String value) {
        this.nombreInvolucrado = value;
    }

    /**
     * Gets the value of the nombreSolicitante property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreSolicitante() {
        return nombreSolicitante;
    }

    /**
     * Sets the value of the nombreSolicitante property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreSolicitante(String value) {
        this.nombreSolicitante = value;
    }

    /**
     * Gets the value of the numeroCasoAsociado property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroCasoAsociado() {
        return numeroCasoAsociado;
    }

    /**
     * Sets the value of the numeroCasoAsociado property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroCasoAsociado(String value) {
        this.numeroCasoAsociado = value;
    }

    /**
     * Gets the value of the objetosDTO property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the objetosDTO property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getObjetosDTO().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ObjetoWSDTO }
     * 
     * 
     */
    public List<ObjetoWSDTO> getObjetosDTO() {
        if (objetosDTO == null) {
            objetosDTO = new ArrayList<ObjetoWSDTO>();
        }
        return this.objetosDTO;
    }

    /**
     * Gets the value of the relacionesDelitoPersona property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the relacionesDelitoPersona property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRelacionesDelitoPersona().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DelitoPersonaWSDTO }
     * 
     * 
     */
    public List<DelitoPersonaWSDTO> getRelacionesDelitoPersona() {
        if (relacionesDelitoPersona == null) {
            relacionesDelitoPersona = new ArrayList<DelitoPersonaWSDTO>();
        }
        return this.relacionesDelitoPersona;
    }

    /**
     * Gets the value of the solicitante property.
     * 
     * @return
     *     possible object is
     *     {@link FuncionarioWSDTO }
     *     
     */
    public FuncionarioWSDTO getSolicitante() {
        return solicitante;
    }

    /**
     * Sets the value of the solicitante property.
     * 
     * @param value
     *     allowed object is
     *     {@link FuncionarioWSDTO }
     *     
     */
    public void setSolicitante(FuncionarioWSDTO value) {
        this.solicitante = value;
    }

    /**
     * Gets the value of the solicitanteExternoId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getSolicitanteExternoId() {
        return solicitanteExternoId;
    }

    /**
     * Sets the value of the solicitanteExternoId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setSolicitanteExternoId(Long value) {
        this.solicitanteExternoId = value;
    }

    /**
     * Gets the value of the solicitudId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getSolicitudId() {
        return solicitudId;
    }

    /**
     * Sets the value of the solicitudId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setSolicitudId(Long value) {
        this.solicitudId = value;
    }

    /**
     * Gets the value of the tribunalId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getTribunalId() {
        return tribunalId;
    }

    /**
     * Sets the value of the tribunalId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setTribunalId(Long value) {
        this.tribunalId = value;
    }

}
