
package mx.gob.segob.nsjp.ws.cliente.sentencia;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for remisionWSDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="remisionWSDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://impl.ws.service.nsjp.segob.gob.mx/}genericWSDTO">
 *       &lt;sequence>
 *         &lt;element name="catTipoRemisionId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="cumplida" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="fechaAutorizacion" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="idiasAcreditados" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="imontoDanioReparado" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "remisionWSDTO", propOrder = {
    "catTipoRemisionId",
    "cumplida",
    "fechaAutorizacion",
    "idiasAcreditados",
    "imontoDanioReparado"
})
public class RemisionWSDTO
    extends GenericWSDTO
{

    protected Long catTipoRemisionId;
    protected Boolean cumplida;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaAutorizacion;
    protected Long idiasAcreditados;
    protected Float imontoDanioReparado;

    /**
     * Gets the value of the catTipoRemisionId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCatTipoRemisionId() {
        return catTipoRemisionId;
    }

    /**
     * Sets the value of the catTipoRemisionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCatTipoRemisionId(Long value) {
        this.catTipoRemisionId = value;
    }

    /**
     * Gets the value of the cumplida property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCumplida() {
        return cumplida;
    }

    /**
     * Sets the value of the cumplida property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCumplida(Boolean value) {
        this.cumplida = value;
    }

    /**
     * Gets the value of the fechaAutorizacion property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaAutorizacion() {
        return fechaAutorizacion;
    }

    /**
     * Sets the value of the fechaAutorizacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaAutorizacion(XMLGregorianCalendar value) {
        this.fechaAutorizacion = value;
    }

    /**
     * Gets the value of the idiasAcreditados property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getIdiasAcreditados() {
        return idiasAcreditados;
    }

    /**
     * Sets the value of the idiasAcreditados property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setIdiasAcreditados(Long value) {
        this.idiasAcreditados = value;
    }

    /**
     * Gets the value of the imontoDanioReparado property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getImontoDanioReparado() {
        return imontoDanioReparado;
    }

    /**
     * Sets the value of the imontoDanioReparado property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setImontoDanioReparado(Float value) {
        this.imontoDanioReparado = value;
    }

}
