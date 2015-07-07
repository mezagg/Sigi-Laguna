/**
* Nombre del Programa 		: RemisionWSDTO.java
* Autor 					: EdgarAT
* Compania 					: Ultrasist
* Proyecto 					: NSJP 								Fecha: 19/12/2012
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
package mx.gob.segob.nsjp.dto.programas;

import java.util.Date;

import mx.gob.segob.nsjp.dto.base.GenericWSDTO;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author EdgarAT
 *
 */
public class RemisionWSDTO extends GenericWSDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6175414884681971335L;
	
	private Long catTipoRemisionId;
	private Float imontoDanioReparado;
	private Long idiasAcreditados;
	private Date fechaAutorizacion;
	private Boolean cumplida;
	
	/**
	 * M&eacute;todo de acceso al campo catTipoRemisionId.
	 * @return El valor del campo catTipoRemisionId
	 */
	public Long getCatTipoRemisionId() {
		return catTipoRemisionId;
	}
	
	/**
	 * Asigna el valor al campo catTipoRemisionId.
	 * @param catTipoRemisionId el valor catTipoRemisionId a asignar
	 */
	public void setCatTipoRemisionId(Long catTipoRemisionId) {
		this.catTipoRemisionId = catTipoRemisionId;
	}
	
	/**
	 * M&eacute;todo de acceso al campo imontoDanioReparado.
	 * @return El valor del campo imontoDanioReparado
	 */
	public Float getImontoDanioReparado() {
		return imontoDanioReparado;
	}
	
	/**
	 * Asigna el valor al campo imontoDanioReparado.
	 * @param imontoDanioReparado el valor imontoDanioReparado a asignar
	 */
	public void setImontoDanioReparado(Float imontoDanioReparado) {
		this.imontoDanioReparado = imontoDanioReparado;
	}
	
	/**
	 * M&eacute;todo de acceso al campo idiasAcreditados.
	 * @return El valor del campo idiasAcreditados
	 */
	public Long getIdiasAcreditados() {
		return idiasAcreditados;
	}
	
	/**
	 * Asigna el valor al campo idiasAcreditados.
	 * @param idiasAcreditados el valor idiasAcreditados a asignar
	 */
	public void setIdiasAcreditados(Long idiasAcreditados) {
		this.idiasAcreditados = idiasAcreditados;
	}
	
	/**
	 * M&eacute;todo de acceso al campo fechaAutorizacion.
	 * @return El valor del campo fechaAutorizacion
	 */
	public Date getFechaAutorizacion() {
		return fechaAutorizacion;
	}
	
	/**
	 * Asigna el valor al campo fechaAutorizacion.
	 * @param fechaAutorizacion el valor fechaAutorizacion a asignar
	 */
	public void setFechaAutorizacion(Date fechaAutorizacion) {
		this.fechaAutorizacion = fechaAutorizacion;
	}
	
	/**
	 * M&eacute;todo de acceso al campo cumplida.
	 * @return El valor del campo cumplida
	 */
	public Boolean getCumplida() {
		return cumplida;
	}
	
	/**
	 * Asigna el valor al campo cumplida.
	 * @param cumplida el valor cumplida a asignar
	 */
	public void setCumplida(Boolean cumplida) {
		this.cumplida = cumplida;
	}
	
	

}
