
package mx.gob.segob.nsjp.ws.cliente.recibiracusederecibodesolicituddedefensor;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for solicitudWSDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="solicitudWSDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.service.nsjp.segob.gob.mx/}documentoWSDTO">
 *       &lt;sequence>
 *         &lt;element name="areaDestino" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="areaOrigen" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="asuntoSolicitud" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="casoWSDTO" type="{http://ws.service.nsjp.segob.gob.mx/}casoWSDTO" minOccurs="0"/>
 *         &lt;element name="destinatario" type="{http://ws.service.nsjp.segob.gob.mx/}funcionarioWSDTO" minOccurs="0"/>
 *         &lt;element name="destinatarioInstExterna" type="{http://ws.service.nsjp.segob.gob.mx/}funcionarioExternoWSDTO" minOccurs="0"/>
 *         &lt;element name="esUrgente" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="fechaCierre" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="fechaLimite" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="fechaModificacion" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="folioSolicitud" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="idEstatus" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="idInstitucion" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="idTipoSolicitud" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="lstDocumentosAdjuntos" type="{http://ws.service.nsjp.segob.gob.mx/}documentoWSDTO" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="motivo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nombreDeLaUnidadDeInvestigacionDelSolicitante" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nombreInstitucionSolicitante" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nombreSolicitante" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nombreSolicitanteExternoInterno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numCarpetaEjecucion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numCausa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroCasoAsociado" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroCausaSentencia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroExpedienteAsociado" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroExpedienteId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="observaciones" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="solicitanteExterno" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="solicitanteInstExterna" type="{http://ws.service.nsjp.segob.gob.mx/}funcionarioExternoWSDTO" minOccurs="0"/>
 *         &lt;element name="solicitudDefensorWSDTO" type="{http://ws.service.nsjp.segob.gob.mx/}solicitudDefensorWSDTO" minOccurs="0"/>
 *         &lt;element name="usuarioSolicitante" type="{http://ws.service.nsjp.segob.gob.mx/}funcionarioWSDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "solicitudWSDTO", propOrder = {
    "areaDestino",
    "areaOrigen",
    "asuntoSolicitud",
    "casoWSDTO",
    "destinatario",
    "destinatarioInstExterna",
    "esUrgente",
    "fechaCierre",
    "fechaLimite",
    "fechaModificacion",
    "folioSolicitud",
    "idEstatus",
    "idInstitucion",
    "idTipoSolicitud",
    "lstDocumentosAdjuntos",
    "motivo",
    "nombreDeLaUnidadDeInvestigacionDelSolicitante",
    "nombreInstitucionSolicitante",
    "nombreSolicitante",
    "nombreSolicitanteExternoInterno",
    "numCarpetaEjecucion",
    "numCausa",
    "numeroCasoAsociado",
    "numeroCausaSentencia",
    "numeroExpedienteAsociado",
    "numeroExpedienteId",
    "observaciones",
    "solicitanteExterno",
    "solicitanteInstExterna",
    "solicitudDefensorWSDTO",
    "usuarioSolicitante"
})
@XmlSeeAlso({
    SolicitudDefensorWSDTO.class
})
public class SolicitudWSDTO
    extends DocumentoWSDTO
{

    protected Long areaDestino;
    protected Long areaOrigen;
    protected String asuntoSolicitud;
    protected CasoWSDTO casoWSDTO;
    protected FuncionarioWSDTO destinatario;
    protected FuncionarioExternoWSDTO destinatarioInstExterna;
    protected Boolean esUrgente;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaCierre;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaLimite;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaModificacion;
    protected String folioSolicitud;
    protected Long idEstatus;
    protected Long idInstitucion;
    protected Long idTipoSolicitud;
    @XmlElement(nillable = true)
    protected List<DocumentoWSDTO> lstDocumentosAdjuntos;
    protected String motivo;
    protected String nombreDeLaUnidadDeInvestigacionDelSolicitante;
    protected String nombreInstitucionSolicitante;
    protected String nombreSolicitante;
    protected String nombreSolicitanteExternoInterno;
    protected String numCarpetaEjecucion;
    protected String numCausa;
    protected String numeroCasoAsociado;
    protected String numeroCausaSentencia;
    protected String numeroExpedienteAsociado;
    protected Long numeroExpedienteId;
    protected String observaciones;
    protected Long solicitanteExterno;
    protected FuncionarioExternoWSDTO solicitanteInstExterna;
    protected SolicitudDefensorWSDTO solicitudDefensorWSDTO;
    protected FuncionarioWSDTO usuarioSolicitante;

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
     * Gets the value of the areaOrigen property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getAreaOrigen() {
        return areaOrigen;
    }

    /**
     * Sets the value of the areaOrigen property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setAreaOrigen(Long value) {
        this.areaOrigen = value;
    }

    /**
     * Gets the value of the asuntoSolicitud property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAsuntoSolicitud() {
        return asuntoSolicitud;
    }

    /**
     * Sets the value of the asuntoSolicitud property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAsuntoSolicitud(String value) {
        this.asuntoSolicitud = value;
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
     * Gets the value of the destinatario property.
     * 
     * @return
     *     possible object is
     *     {@link FuncionarioWSDTO }
     *     
     */
    public FuncionarioWSDTO getDestinatario() {
        return destinatario;
    }

    /**
     * Sets the value of the destinatario property.
     * 
     * @param value
     *     allowed object is
     *     {@link FuncionarioWSDTO }
     *     
     */
    public void setDestinatario(FuncionarioWSDTO value) {
        this.destinatario = value;
    }

    /**
     * Gets the value of the destinatarioInstExterna property.
     * 
     * @return
     *     possible object is
     *     {@link FuncionarioExternoWSDTO }
     *     
     */
    public FuncionarioExternoWSDTO getDestinatarioInstExterna() {
        return destinatarioInstExterna;
    }

    /**
     * Sets the value of the destinatarioInstExterna property.
     * 
     * @param value
     *     allowed object is
     *     {@link FuncionarioExternoWSDTO }
     *     
     */
    public void setDestinatarioInstExterna(FuncionarioExternoWSDTO value) {
        this.destinatarioInstExterna = value;
    }

    /**
     * Gets the value of the esUrgente property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEsUrgente() {
        return esUrgente;
    }

    /**
     * Sets the value of the esUrgente property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEsUrgente(Boolean value) {
        this.esUrgente = value;
    }

    /**
     * Gets the value of the fechaCierre property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaCierre() {
        return fechaCierre;
    }

    /**
     * Sets the value of the fechaCierre property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaCierre(XMLGregorianCalendar value) {
        this.fechaCierre = value;
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
     * Gets the value of the fechaModificacion property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaModificacion() {
        return fechaModificacion;
    }

    /**
     * Sets the value of the fechaModificacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaModificacion(XMLGregorianCalendar value) {
        this.fechaModificacion = value;
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
     * Gets the value of the idEstatus property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getIdEstatus() {
        return idEstatus;
    }

    /**
     * Sets the value of the idEstatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setIdEstatus(Long value) {
        this.idEstatus = value;
    }

    /**
     * Gets the value of the idInstitucion property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getIdInstitucion() {
        return idInstitucion;
    }

    /**
     * Sets the value of the idInstitucion property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setIdInstitucion(Long value) {
        this.idInstitucion = value;
    }

    /**
     * Gets the value of the idTipoSolicitud property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getIdTipoSolicitud() {
        return idTipoSolicitud;
    }

    /**
     * Sets the value of the idTipoSolicitud property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setIdTipoSolicitud(Long value) {
        this.idTipoSolicitud = value;
    }

    /**
     * Gets the value of the lstDocumentosAdjuntos property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the lstDocumentosAdjuntos property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLstDocumentosAdjuntos().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DocumentoWSDTO }
     * 
     * 
     */
    public List<DocumentoWSDTO> getLstDocumentosAdjuntos() {
        if (lstDocumentosAdjuntos == null) {
            lstDocumentosAdjuntos = new ArrayList<DocumentoWSDTO>();
        }
        return this.lstDocumentosAdjuntos;
    }

    /**
     * Gets the value of the motivo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMotivo() {
        return motivo;
    }

    /**
     * Sets the value of the motivo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMotivo(String value) {
        this.motivo = value;
    }

    /**
     * Gets the value of the nombreDeLaUnidadDeInvestigacionDelSolicitante property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreDeLaUnidadDeInvestigacionDelSolicitante() {
        return nombreDeLaUnidadDeInvestigacionDelSolicitante;
    }

    /**
     * Sets the value of the nombreDeLaUnidadDeInvestigacionDelSolicitante property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreDeLaUnidadDeInvestigacionDelSolicitante(String value) {
        this.nombreDeLaUnidadDeInvestigacionDelSolicitante = value;
    }

    /**
     * Gets the value of the nombreInstitucionSolicitante property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreInstitucionSolicitante() {
        return nombreInstitucionSolicitante;
    }

    /**
     * Sets the value of the nombreInstitucionSolicitante property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreInstitucionSolicitante(String value) {
        this.nombreInstitucionSolicitante = value;
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
     * Gets the value of the nombreSolicitanteExternoInterno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreSolicitanteExternoInterno() {
        return nombreSolicitanteExternoInterno;
    }

    /**
     * Sets the value of the nombreSolicitanteExternoInterno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreSolicitanteExternoInterno(String value) {
        this.nombreSolicitanteExternoInterno = value;
    }

    /**
     * Gets the value of the numCarpetaEjecucion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumCarpetaEjecucion() {
        return numCarpetaEjecucion;
    }

    /**
     * Sets the value of the numCarpetaEjecucion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumCarpetaEjecucion(String value) {
        this.numCarpetaEjecucion = value;
    }

    /**
     * Gets the value of the numCausa property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumCausa() {
        return numCausa;
    }

    /**
     * Sets the value of the numCausa property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumCausa(String value) {
        this.numCausa = value;
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
     * Gets the value of the numeroCausaSentencia property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroCausaSentencia() {
        return numeroCausaSentencia;
    }

    /**
     * Sets the value of the numeroCausaSentencia property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroCausaSentencia(String value) {
        this.numeroCausaSentencia = value;
    }

    /**
     * Gets the value of the numeroExpedienteAsociado property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroExpedienteAsociado() {
        return numeroExpedienteAsociado;
    }

    /**
     * Sets the value of the numeroExpedienteAsociado property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroExpedienteAsociado(String value) {
        this.numeroExpedienteAsociado = value;
    }

    /**
     * Gets the value of the numeroExpedienteId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getNumeroExpedienteId() {
        return numeroExpedienteId;
    }

    /**
     * Sets the value of the numeroExpedienteId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setNumeroExpedienteId(Long value) {
        this.numeroExpedienteId = value;
    }

    /**
     * Gets the value of the observaciones property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * Sets the value of the observaciones property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObservaciones(String value) {
        this.observaciones = value;
    }

    /**
     * Gets the value of the solicitanteExterno property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getSolicitanteExterno() {
        return solicitanteExterno;
    }

    /**
     * Sets the value of the solicitanteExterno property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setSolicitanteExterno(Long value) {
        this.solicitanteExterno = value;
    }

    /**
     * Gets the value of the solicitanteInstExterna property.
     * 
     * @return
     *     possible object is
     *     {@link FuncionarioExternoWSDTO }
     *     
     */
    public FuncionarioExternoWSDTO getSolicitanteInstExterna() {
        return solicitanteInstExterna;
    }

    /**
     * Sets the value of the solicitanteInstExterna property.
     * 
     * @param value
     *     allowed object is
     *     {@link FuncionarioExternoWSDTO }
     *     
     */
    public void setSolicitanteInstExterna(FuncionarioExternoWSDTO value) {
        this.solicitanteInstExterna = value;
    }

    /**
     * Gets the value of the solicitudDefensorWSDTO property.
     * 
     * @return
     *     possible object is
     *     {@link SolicitudDefensorWSDTO }
     *     
     */
    public SolicitudDefensorWSDTO getSolicitudDefensorWSDTO() {
        return solicitudDefensorWSDTO;
    }

    /**
     * Sets the value of the solicitudDefensorWSDTO property.
     * 
     * @param value
     *     allowed object is
     *     {@link SolicitudDefensorWSDTO }
     *     
     */
    public void setSolicitudDefensorWSDTO(SolicitudDefensorWSDTO value) {
        this.solicitudDefensorWSDTO = value;
    }

    /**
     * Gets the value of the usuarioSolicitante property.
     * 
     * @return
     *     possible object is
     *     {@link FuncionarioWSDTO }
     *     
     */
    public FuncionarioWSDTO getUsuarioSolicitante() {
        return usuarioSolicitante;
    }

    /**
     * Sets the value of the usuarioSolicitante property.
     * 
     * @param value
     *     allowed object is
     *     {@link FuncionarioWSDTO }
     *     
     */
    public void setUsuarioSolicitante(FuncionarioWSDTO value) {
        this.usuarioSolicitante = value;
    }

}
