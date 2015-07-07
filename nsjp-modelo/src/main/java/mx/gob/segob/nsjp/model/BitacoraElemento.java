/**
* Nombre del Programa 		: BitacoraElemento.java
* Autor 					: EdgarAT
* Compania 					: Ultrasist
* Proyecto 					: NSJP 								Fecha: 07/11/2012
* Marca de cambio 			: N/A
* Descripcion General 		: Describir el objetivo de la clase de manera breve
* Programa Dependiente 		: N/A
* Programa Subsecuente 		: N/A
* Cond. de ejecucion 		: N/A
* Dias de ejecucion 		: N/A 								Horario: N/A
*                              MODIFICACIONES
*------------------------------------------------------------------------------
* Autor 					: N/A
* Compania 					: N/A
* Proyecto 					: N/A 								Fecha: N/A
* Modificacion 				: N/A
*------------------------------------------------------------------------------
*/
package mx.gob.segob.nsjp.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author EdgarAT
 *
 */
@Entity
@Table(name = "BitacoraElemento")
public class BitacoraElemento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1784689023591681723L;
	
	private Long bitacoraElementoId;
	private Date fechaModificacion;
	private Elemento elemento;

	/**
	 * M&eacute;todo de acceso al campo bitacoraElementoId.
	 * @return El valor del campo bitacoraElementoId
	 */
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BitacoraElemento_id", unique = true, nullable = false, precision = 18, scale = 0)
	public Long getBitacoraElementoId() {
		return bitacoraElementoId;
	}
	
	/**
	 * Asigna el valor al campo bitacoraElementoId.
	 * @param bitacoraElementoId el valor bitacoraElementoId a asignar
	 */
	public void setBitacoraElementoId(Long bitacoraElementoId) {
		this.bitacoraElementoId = bitacoraElementoId;
	}
	
	/**
	 * M&eacute;todo de acceso al campo fechaModificacion.
	 * @return El valor del campo fechaModificacion
	 */
	@Column(name = "dFechaModificacion", nullable = false, length = 23)
	public Date getFechaModificacion() {
		return fechaModificacion;
	}
	
	/**
	 * Asigna el valor al campo fechaModificacion.
	 * @param fechaModificacion el valor fechaModificacion a asignar
	 */
	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
	
	/**
	 * M&eacute;todo de acceso al campo elemento.
	 * @return El valor del campo elemento
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Elemento_id", nullable = false)
	public Elemento getElemento() {
		return elemento;
	}
	
	/**
	 * Asigna el valor al campo elemento.
	 * @param elemento el valor elemento a asignar
	 */
	public void setElemento(Elemento elemento) {
		this.elemento = elemento;
	}
}
