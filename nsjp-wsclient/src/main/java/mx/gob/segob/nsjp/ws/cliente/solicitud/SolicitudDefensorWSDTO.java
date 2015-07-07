
package mx.gob.segob.nsjp.ws.cliente.solicitud;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for solicitudDefensorWSDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="solicitudDefensorWSDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://impl.ws.service.nsjp.segob.gob.mx/}solicitudWSDTO">
 *       &lt;sequence>
 *         &lt;element name="apellidoMaternoImputado" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="apellidoPaternoImputado" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="audiencia" type="{http://impl.ws.service.nsjp.segob.gob.mx/}audienciaWSDTO" minOccurs="0"/>
 *         &lt;element name="defensorAsignado" type="{http://impl.ws.service.nsjp.segob.gob.mx/}funcionarioExternoWSDTO" minOccurs="0"/>
 *         &lt;element name="delitos" type="{http://impl.ws.service.nsjp.segob.gob.mx/}delitoWSDTO" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="esDetenido" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="folioElemento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="involucrados" type="{http://impl.ws.service.nsjp.segob.gob.mx/}involucradoWSDTO" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="nombreImputado" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipoAsesoria" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "solicitudDefensorWSDTO", propOrder = {
    "apellidoMaternoImputado",
    "apellidoPaternoImputado",
    "audiencia",
    "defensorAsignado",
    "delitos",
    "esDetenido",
    "folioElemento",
    "involucrados",
    "nombreImputado",
    "tipoAsesoria"
})
public class SolicitudDefensorWSDTO
    extends SolicitudWSDTO
{

    protected String apellidoMaternoImputado;
    protected String apellidoPaternoImputado;
    protected AudienciaWSDTO audiencia;
    protected FuncionarioExternoWSDTO defensorAsignado;
    @XmlElement(nillable = true)
    protected List<DelitoWSDTO> delitos;
    protected Boolean esDetenido;
    protected String folioElemento;
    @XmlElement(nillable = true)
    protected List<InvolucradoWSDTO> involucrados;
    protected String nombreImputado;
    protected Long tipoAsesoria;

    /**
     * Gets the value of the apellidoMaternoImputado property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApellidoMaternoImputado() {
        return apellidoMaternoImputado;
    }

    /**
     * Sets the value of the apellidoMaternoImputado property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApellidoMaternoImputado(String value) {
        this.apellidoMaternoImputado = value;
    }

    /**
     * Gets the value of the apellidoPaternoImputado property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApellidoPaternoImputado() {
        return apellidoPaternoImputado;
    }

    /**
     * Sets the value of the apellidoPaternoImputado property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApellidoPaternoImputado(String value) {
        this.apellidoPaternoImputado = value;
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
     * Gets the value of the defensorAsignado property.
     * 
     * @return
     *     possible object is
     *     {@link FuncionarioExternoWSDTO }
     *     
     */
    public FuncionarioExternoWSDTO getDefensorAsignado() {
        return defensorAsignado;
    }

    /**
     * Sets the value of the defensorAsignado property.
     * 
     * @param value
     *     allowed object is
     *     {@link FuncionarioExternoWSDTO }
     *     
     */
    public void setDefensorAsignado(FuncionarioExternoWSDTO value) {
        this.defensorAsignado = value;
    }

    /**
     * Gets the value of the delitos property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the delitos property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDelitos().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DelitoWSDTO }
     * 
     * 
     */
    public List<DelitoWSDTO> getDelitos() {
        if (delitos == null) {
            delitos = new ArrayList<DelitoWSDTO>();
        }
        return this.delitos;
    }

    /**
     * Gets the value of the esDetenido property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEsDetenido() {
        return esDetenido;
    }

    /**
     * Sets the value of the esDetenido property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEsDetenido(Boolean value) {
        this.esDetenido = value;
    }

    /**
     * Gets the value of the folioElemento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFolioElemento() {
        return folioElemento;
    }

    /**
     * Sets the value of the folioElemento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFolioElemento(String value) {
        this.folioElemento = value;
    }

    /**
     * Gets the value of the involucrados property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the involucrados property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInvolucrados().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InvolucradoWSDTO }
     * 
     * 
     */
    public List<InvolucradoWSDTO> getInvolucrados() {
        if (involucrados == null) {
            involucrados = new ArrayList<InvolucradoWSDTO>();
        }
        return this.involucrados;
    }

    /**
     * Gets the value of the nombreImputado property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreImputado() {
        return nombreImputado;
    }

    /**
     * Sets the value of the nombreImputado property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreImputado(String value) {
        this.nombreImputado = value;
    }

    /**
     * Gets the value of the tipoAsesoria property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getTipoAsesoria() {
        return tipoAsesoria;
    }

    /**
     * Sets the value of the tipoAsesoria property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setTipoAsesoria(Long value) {
        this.tipoAsesoria = value;
    }

}
