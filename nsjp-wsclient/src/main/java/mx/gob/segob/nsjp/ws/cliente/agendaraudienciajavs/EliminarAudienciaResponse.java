
package mx.gob.segob.nsjp.ws.cliente.agendaraudienciajavs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="eliminar_AudienciaResult" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "eliminarAudienciaResult"
})
@XmlRootElement(name = "eliminar_AudienciaResponse")
public class EliminarAudienciaResponse {

    @XmlElement(name = "eliminar_AudienciaResult")
    protected int eliminarAudienciaResult;

    /**
     * Gets the value of the eliminarAudienciaResult property.
     * 
     */
    public int getEliminarAudienciaResult() {
        return eliminarAudienciaResult;
    }

    /**
     * Sets the value of the eliminarAudienciaResult property.
     * 
     */
    public void setEliminarAudienciaResult(int value) {
        this.eliminarAudienciaResult = value;
    }

}
