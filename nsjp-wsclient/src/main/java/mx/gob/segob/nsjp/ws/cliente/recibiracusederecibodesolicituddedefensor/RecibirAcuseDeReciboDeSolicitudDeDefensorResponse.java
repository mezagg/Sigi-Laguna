
package mx.gob.segob.nsjp.ws.cliente.recibiracusederecibodesolicituddedefensor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for recibirAcuseDeReciboDeSolicitudDeDefensorResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="recibirAcuseDeReciboDeSolicitudDeDefensorResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://ws.service.nsjp.segob.gob.mx/}documentoWSDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "recibirAcuseDeReciboDeSolicitudDeDefensorResponse", propOrder = {
    "_return"
})
public class RecibirAcuseDeReciboDeSolicitudDeDefensorResponse {

    @XmlElement(name = "return")
    protected DocumentoWSDTO _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link DocumentoWSDTO }
     *     
     */
    public DocumentoWSDTO getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentoWSDTO }
     *     
     */
    public void setReturn(DocumentoWSDTO value) {
        this._return = value;
    }

}
