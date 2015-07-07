
package mx.gob.segob.nsjp.ws.cliente.estatusmandamiento;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for mandamientoWSDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="mandamientoWSDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.service.nsjp.segob.gob.mx/}documentoWSDTO">
 *       &lt;sequence>
 *         &lt;element name="delitosPersona" type="{http://ws.service.nsjp.segob.gob.mx/}delitoPersonaWSDTO" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="estatus" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="idTipoMandamiento" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="mandamientosPersona" type="{http://ws.service.nsjp.segob.gob.mx/}mandamientoPersonaWSDTO" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="numeroCaso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroCausa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "mandamientoWSDTO", propOrder = {
    "delitosPersona",
    "estatus",
    "idTipoMandamiento",
    "mandamientosPersona",
    "numeroCaso",
    "numeroCausa"
})
public class MandamientoWSDTO
    extends DocumentoWSDTO
{

    @XmlElement(nillable = true)
    protected List<DelitoPersonaWSDTO> delitosPersona;
    protected Long estatus;
    protected Long idTipoMandamiento;
    @XmlElement(nillable = true)
    protected List<MandamientoPersonaWSDTO> mandamientosPersona;
    protected String numeroCaso;
    protected String numeroCausa;

    /**
     * Gets the value of the delitosPersona property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the delitosPersona property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDelitosPersona().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DelitoPersonaWSDTO }
     * 
     * 
     */
    public List<DelitoPersonaWSDTO> getDelitosPersona() {
        if (delitosPersona == null) {
            delitosPersona = new ArrayList<DelitoPersonaWSDTO>();
        }
        return this.delitosPersona;
    }

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
     * Gets the value of the idTipoMandamiento property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getIdTipoMandamiento() {
        return idTipoMandamiento;
    }

    /**
     * Sets the value of the idTipoMandamiento property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setIdTipoMandamiento(Long value) {
        this.idTipoMandamiento = value;
    }

    /**
     * Gets the value of the mandamientosPersona property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the mandamientosPersona property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMandamientosPersona().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MandamientoPersonaWSDTO }
     * 
     * 
     */
    public List<MandamientoPersonaWSDTO> getMandamientosPersona() {
        if (mandamientosPersona == null) {
            mandamientosPersona = new ArrayList<MandamientoPersonaWSDTO>();
        }
        return this.mandamientosPersona;
    }

    /**
     * Gets the value of the numeroCaso property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroCaso() {
        return numeroCaso;
    }

    /**
     * Sets the value of the numeroCaso property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroCaso(String value) {
        this.numeroCaso = value;
    }

    /**
     * Gets the value of the numeroCausa property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroCausa() {
        return numeroCausa;
    }

    /**
     * Sets the value of the numeroCausa property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroCausa(String value) {
        this.numeroCausa = value;
    }

}
