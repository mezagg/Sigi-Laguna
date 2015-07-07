
package mx.gob.segob.nsjp.ws.cliente.recibiractualizacionmandamiento;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for mandamientoPersonaWSDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="mandamientoPersonaWSDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="estatus" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="fechaEjecucion" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="folioInterInstitucional" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="persona" type="{http://ws.service.nsjp.segob.gob.mx/}personaWSDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "mandamientoPersonaWSDTO", propOrder = {
    "estatus",
    "fechaEjecucion",
    "folioInterInstitucional",
    "persona"
})
public class MandamientoPersonaWSDTO {

    protected Long estatus;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaEjecucion;
    protected String folioInterInstitucional;
    protected PersonaWSDTO persona;

    /**
     * Gets the value of the estatus property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getEstatus() {
        return estatus;
    }

    /**
     * Sets the value of the estatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setEstatus(Long value) {
        this.estatus = value;
    }

    /**
     * Gets the value of the fechaEjecucion property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaEjecucion() {
        return fechaEjecucion;
    }

    /**
     * Sets the value of the fechaEjecucion property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaEjecucion(XMLGregorianCalendar value) {
        this.fechaEjecucion = value;
    }

    /**
     * Gets the value of the folioInterInstitucional property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFolioInterInstitucional() {
        return folioInterInstitucional;
    }

    /**
     * Sets the value of the folioInterInstitucional property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFolioInterInstitucional(String value) {
        this.folioInterInstitucional = value;
    }

    /**
     * Gets the value of the persona property.
     * 
     * @return
     *     possible object is
     *     {@link PersonaWSDTO }
     *     
     */
    public PersonaWSDTO getPersona() {
        return persona;
    }

    /**
     * Sets the value of the persona property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonaWSDTO }
     *     
     */
    public void setPersona(PersonaWSDTO value) {
        this.persona = value;
    }

}
