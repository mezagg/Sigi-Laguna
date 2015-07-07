
package mx.gob.segob.nsjp.ws.cliente.recibiracusederecibodesolicituddedefensor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for recibirAcuseDeReciboDeSolicitudDeDefensor complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="recibirAcuseDeReciboDeSolicitudDeDefensor">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="arg0" type="{http://ws.service.nsjp.segob.gob.mx/}solicitudWSDTO" minOccurs="0"/>
 *         &lt;element name="arg1" type="{http://ws.service.nsjp.segob.gob.mx/}documentoWSDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "recibirAcuseDeReciboDeSolicitudDeDefensor", propOrder = {
    "arg0",
    "arg1"
})
public class RecibirAcuseDeReciboDeSolicitudDeDefensor {

    protected SolicitudWSDTO arg0;
    protected DocumentoWSDTO arg1;

    /**
     * Gets the value of the arg0 property.
     * 
     * @return
     *     possible object is
     *     {@link SolicitudWSDTO }
     *     
     */
    public SolicitudWSDTO getArg0() {
        return arg0;
    }

    /**
     * Sets the value of the arg0 property.
     * 
     * @param value
     *     allowed object is
     *     {@link SolicitudWSDTO }
     *     
     */
    public void setArg0(SolicitudWSDTO value) {
        this.arg0 = value;
    }

    /**
     * Gets the value of the arg1 property.
     * 
     * @return
     *     possible object is
     *     {@link DocumentoWSDTO }
     *     
     */
    public DocumentoWSDTO getArg1() {
        return arg1;
    }

    /**
     * Sets the value of the arg1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentoWSDTO }
     *     
     */
    public void setArg1(DocumentoWSDTO value) {
        this.arg1 = value;
    }

}
