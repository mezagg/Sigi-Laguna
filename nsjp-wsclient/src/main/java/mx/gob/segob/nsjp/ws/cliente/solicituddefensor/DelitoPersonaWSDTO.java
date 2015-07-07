
package mx.gob.segob.nsjp.ws.cliente.solicituddefensor;

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
 *         &lt;element name="claveInterIntitucionalDelito" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="esPincipal" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="folioProbableResponsable" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="folioVictima" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "claveInterIntitucionalDelito",
    "esPincipal",
    "folioProbableResponsable",
    "folioVictima"
})
public class DelitoPersonaWSDTO
    extends GenericWSDTO
{

    protected String claveInterIntitucionalDelito;
    protected Boolean esPincipal;
    protected String folioProbableResponsable;
    protected String folioVictima;

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

}
