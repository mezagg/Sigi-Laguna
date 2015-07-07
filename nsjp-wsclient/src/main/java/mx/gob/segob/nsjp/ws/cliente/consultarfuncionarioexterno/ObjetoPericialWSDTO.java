
package mx.gob.segob.nsjp.ws.cliente.consultarfuncionarioexterno;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for objetoPericialWSDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="objetoPericialWSDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.service.nsjp.segob.gob.mx/}objetoWSDTO">
 *       &lt;sequence>
 *         &lt;element name="categoriaIndicioId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="indicioId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "objetoPericialWSDTO", propOrder = {
    "categoriaIndicioId",
    "indicioId"
})
public class ObjetoPericialWSDTO
    extends ObjetoWSDTO
{

    protected Long categoriaIndicioId;
    protected Long indicioId;

    /**
     * Gets the value of the categoriaIndicioId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCategoriaIndicioId() {
        return categoriaIndicioId;
    }

    /**
     * Sets the value of the categoriaIndicioId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCategoriaIndicioId(Long value) {
        this.categoriaIndicioId = value;
    }

    /**
     * Gets the value of the indicioId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getIndicioId() {
        return indicioId;
    }

    /**
     * Sets the value of the indicioId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setIndicioId(Long value) {
        this.indicioId = value;
    }

}
