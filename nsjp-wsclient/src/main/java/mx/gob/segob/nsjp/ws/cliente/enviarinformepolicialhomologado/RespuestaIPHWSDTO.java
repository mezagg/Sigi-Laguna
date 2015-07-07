
package mx.gob.segob.nsjp.ws.cliente.enviarinformepolicialhomologado;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for respuestaIPHWSDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="respuestaIPHWSDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.service.nsjp.segob.gob.mx/}genericWSDTO">
 *       &lt;sequence>
 *         &lt;element name="idDocumentoIPH" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="idNuevoExpedienteIPH" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="mensajeDeError" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "respuestaIPHWSDTO", propOrder = {
    "idDocumentoIPH",
    "idNuevoExpedienteIPH",
    "mensajeDeError"
})
public class RespuestaIPHWSDTO
    extends GenericWSDTO
{

    protected Long idDocumentoIPH;
    protected Long idNuevoExpedienteIPH;
    protected String mensajeDeError;

    /**
     * Gets the value of the idDocumentoIPH property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getIdDocumentoIPH() {
        return idDocumentoIPH;
    }

    /**
     * Sets the value of the idDocumentoIPH property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setIdDocumentoIPH(Long value) {
        this.idDocumentoIPH = value;
    }

    /**
     * Gets the value of the idNuevoExpedienteIPH property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getIdNuevoExpedienteIPH() {
        return idNuevoExpedienteIPH;
    }

    /**
     * Sets the value of the idNuevoExpedienteIPH property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setIdNuevoExpedienteIPH(Long value) {
        this.idNuevoExpedienteIPH = value;
    }

    /**
     * Gets the value of the mensajeDeError property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMensajeDeError() {
        return mensajeDeError;
    }

    /**
     * Sets the value of the mensajeDeError property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMensajeDeError(String value) {
        this.mensajeDeError = value;
    }

}
