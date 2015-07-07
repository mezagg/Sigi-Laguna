/**
* Nombre del Programa 		: BitacoraElementoDTO.java
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
package mx.gob.segob.nsjp.dto.elemento;

import java.util.Date;

import mx.gob.segob.nsjp.dto.base.GenericDTO;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author EdgarAT
 *
 */
public class BitacoraElementoDTO extends GenericDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5166852132360330080L;

	private Long bitacoraElementoId;
	private Date fechaModificacion;
	private ElementoDTO elemento;
	
	/**
	 * M&eacute;todo de acceso al campo bitacoraElementoId.
	 * @return El valor del campo bitacoraElementoId
	 */
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
	public ElementoDTO getElemento() {
		return elemento;
	}
	
	/**
	 * Asigna el valor al campo elemento.
	 * @param elemento el valor elemento a asignar
	 */
	public void setElemento(ElementoDTO elemento) {
		this.elemento = elemento;
	}
	
}
