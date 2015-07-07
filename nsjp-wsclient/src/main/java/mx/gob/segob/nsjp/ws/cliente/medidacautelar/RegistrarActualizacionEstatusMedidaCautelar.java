
package mx.gob.segob.nsjp.ws.cliente.medidacautelar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for registrarActualizacionEstatusMedidaCautelar complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="registrarActualizacionEstatusMedidaCautelar">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="arg0" type="{http://ws.service.nsjp.segob.gob.mx/}medidaCautelarWSDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "registrarActualizacionEstatusMedidaCautelar", propOrder = {
    "arg0"
})
public class RegistrarActualizacionEstatusMedidaCautelar {

    protected MedidaCautelarWSDTO arg0;

    /**
     * Gets the value of the arg0 property.
     * 
     * @return
     *     possible object is
     *     {@link MedidaCautelarWSDTO }
     *     
     */
    public MedidaCautelarWSDTO getArg0() {
        return arg0;
    }

    /**
     * Sets the value of the arg0 property.
     * 
     * @param value
     *     allowed object is
     *     {@link MedidaCautelarWSDTO }
     *     
     */
    public void setArg0(MedidaCautelarWSDTO value) {
        this.arg0 = value;
    }

}
