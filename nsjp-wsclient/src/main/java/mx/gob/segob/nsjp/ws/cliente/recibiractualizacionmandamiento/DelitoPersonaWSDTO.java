
package mx.gob.segob.nsjp.ws.cliente.recibiractualizacionmandamiento;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for delitoPersonaWSDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="delitoPersonaWSDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.service.nsjp.segob.gob.mx/}genericWSDTO">
 *       &lt;sequence>
 *         &lt;element name="bienTutelado" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="catDelitoCausaId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="catDelitoClasificacionId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="catDelitoLugarId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="catDelitoModalidadId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="catDelitoModusId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="claveInterIntitucionalDelito" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="esActivo" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="esPincipal" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="esProbable" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="folioInterInstitucionalDelitoPersona" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="folioProbableResponsable" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="folioVictima" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="formaParticipacion" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "delitoPersonaWSDTO", propOrder = {
    "bienTutelado",
    "catDelitoCausaId",
    "catDelitoClasificacionId",
    "catDelitoLugarId",
    "catDelitoModalidadId",
    "catDelitoModusId",
    "claveInterIntitucionalDelito",
    "esActivo",
    "esPincipal",
    "esProbable",
    "folioInterInstitucionalDelitoPersona",
    "folioProbableResponsable",
    "folioVictima",
    "formaParticipacion"
})
public class DelitoPersonaWSDTO
    extends GenericWSDTO
{

    protected Long bienTutelado;
    protected Long catDelitoCausaId;
    protected Long catDelitoClasificacionId;
    protected Long catDelitoLugarId;
    protected Long catDelitoModalidadId;
    protected Long catDelitoModusId;
    protected String claveInterIntitucionalDelito;
    protected Boolean esActivo;
    protected Boolean esPincipal;
    protected Boolean esProbable;
    protected String folioInterInstitucionalDelitoPersona;
    protected String folioProbableResponsable;
    protected String folioVictima;
    protected Long formaParticipacion;

    /**
     * Gets the value of the bienTutelado property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getBienTutelado() {
        return bienTutelado;
    }

    /**
     * Sets the value of the bienTutelado property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setBienTutelado(Long value) {
        this.bienTutelado = value;
    }

    /**
     * Gets the value of the catDelitoCausaId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCatDelitoCausaId() {
        return catDelitoCausaId;
    }

    /**
     * Sets the value of the catDelitoCausaId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCatDelitoCausaId(Long value) {
        this.catDelitoCausaId = value;
    }

    /**
     * Gets the value of the catDelitoClasificacionId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCatDelitoClasificacionId() {
        return catDelitoClasificacionId;
    }

    /**
     * Sets the value of the catDelitoClasificacionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCatDelitoClasificacionId(Long value) {
        this.catDelitoClasificacionId = value;
    }

    /**
     * Gets the value of the catDelitoLugarId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCatDelitoLugarId() {
        return catDelitoLugarId;
    }

    /**
     * Sets the value of the catDelitoLugarId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCatDelitoLugarId(Long value) {
        this.catDelitoLugarId = value;
    }

    /**
     * Gets the value of the catDelitoModalidadId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCatDelitoModalidadId() {
        return catDelitoModalidadId;
    }

    /**
     * Sets the value of the catDelitoModalidadId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCatDelitoModalidadId(Long value) {
        this.catDelitoModalidadId = value;
    }

    /**
     * Gets the value of the catDelitoModusId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCatDelitoModusId() {
        return catDelitoModusId;
    }

    /**
     * Sets the value of the catDelitoModusId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCatDelitoModusId(Long value) {
        this.catDelitoModusId = value;
    }

    /**
     * Gets the value of the claveInterIntitucionalDelito property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClaveInterIntitucionalDelito() {
        return claveInterIntitucionalDelito;
    }

    /**
     * Sets the value of the claveInterIntitucionalDelito property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClaveInterIntitucionalDelito(String value) {
        this.claveInterIntitucionalDelito = value;
    }

    /**
     * Gets the value of the esActivo property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEsActivo() {
        return esActivo;
    }

    /**
     * Sets the value of the esActivo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEsActivo(Boolean value) {
        this.esActivo = value;
    }

    /**
     * Gets the value of the esPincipal property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEsPincipal() {
        return esPincipal;
    }

    /**
     * Sets the value of the esPincipal property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEsPincipal(Boolean value) {
        this.esPincipal = value;
    }

    /**
     * Gets the value of the esProbable property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEsProbable() {
        return esProbable;
    }

    /**
     * Sets the value of the esProbable property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEsProbable(Boolean value) {
        this.esProbable = value;
    }

    /**
     * Gets the value of the folioInterInstitucionalDelitoPersona property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFolioInterInstitucionalDelitoPersona() {
        return folioInterInstitucionalDelitoPersona;
    }

    /**
     * Sets the value of the folioInterInstitucionalDelitoPersona property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFolioInterInstitucionalDelitoPersona(String value) {
        this.folioInterInstitucionalDelitoPersona = value;
    }

    /**
     * Gets the value of the folioProbableResponsable property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFolioProbableResponsable() {
        return folioProbableResponsable;
    }

    /**
     * Sets the value of the folioProbableResponsable property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFolioProbableResponsable(String value) {
        this.folioProbableResponsable = value;
    }

    /**
     * Gets the value of the folioVictima property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFolioVictima() {
        return folioVictima;
    }

    /**
     * Sets the value of the folioVictima property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFolioVictima(String value) {
        this.folioVictima = value;
    }

    /**
     * Gets the value of the formaParticipacion property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getFormaParticipacion() {
        return formaParticipacion;
    }

    /**
     * Sets the value of the formaParticipacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setFormaParticipacion(Long value) {
        this.formaParticipacion = value;
    }

}
