/**
 * Nombre del Programa		: FiltroMandamientoDTO.java
 * Autor                    : AlejandroGA
 * Compania                 : Ultrasist
 * Proyecto                 : NSJP						Fecha:14/06/2013 
 * Marca de cambio        	: N/A
 * Descripcion General    	: DTO para portar los filtros por los cuales de puede realizar
 * 							  la b&uacute;squeda de mandamientos judiciales
 * Programa Dependiente  	: N/A
 * Programa Subsecuente 	: N/A
 * Cond. de ejecucion		: N/A
 * Dias de ejecucion		: N/A                             Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                    : N/A
 * Compania					: N/A
 * Proyecto                 : N/A						Fecha: N/A
 * Modificacion				: N/A
 *------------------------------------------------------------------------------
 */

package mx.gob.segob.nsjp.dto.mandamiento;

import java.util.Date;
import java.util.List;

/**
 * @author AlejandroGA
 * @version 1.0
 */
public class FiltroMandamientoDTO {
	
	// Campos
	private String numeroExpediente;
	private Date fechaInicioBusqueda;
	private Date fechaFinBusqueda;
	private Long idEstatus;
	private List<Long> idsTipoMandamiento;
	private Long claveFuncionario;
	private Long idInstitucion;
	
	
	/**
	 * M&eacute;todo de acceso al campo numeroExpediente.
	 * @return El valor del campo numeroExpediente
	 */
	public String getNumeroExpediente() {
		return numeroExpediente;
	}
	
	/**
	 * Asigna el valor al campo numeroExpediente.
	 * @param numeroExpediente a asignar
	 */
	public void setNumeroExpediente(String numeroExpediente) {
		this.numeroExpediente = numeroExpediente;
	}
	
	/**
	 * M&eacute;todo de acceso al campo fechaInicioBusqueda.
	 * @return El valor del campo fechaInicioBusqueda
	 */
	public Date getFechaInicioBusqueda() {
		return fechaInicioBusqueda;
	}
	
	/**
	 * Asigna el valor al campo fechaInicioBusqueda.
	 * @param fechaInicioBusqueda a asignar
	 */
	public void setFechaInicioBusqueda(Date fechaInicioBusqueda) {
		this.fechaInicioBusqueda = fechaInicioBusqueda;
	}
	
	/**
	 * M&eacute;todo de acceso al campo fechaFinBusqueda.
	 * @return El valor del campo fechaFinBusqueda
	 */
	public Date getFechaFinBusqueda() {
		return fechaFinBusqueda;
	}
	
	/**
	 * Asigna el valor al campo fechaFinBusqueda.
	 * @param fechaFinBusqueda a asignar
	 */
	public void setFechaFinBusqueda(Date fechaFinBusqueda) {
		this.fechaFinBusqueda = fechaFinBusqueda;
	}
	
	/**
	 * M&eacute;todo de acceso al campo idEstatus.
	 * @return El valor del campo idEstatus
	 */
	public Long getIdEstatus() {
		return idEstatus;
	}
	
	/**
	 * Asigna el valor al campo idEstatus.
	 * @param idEstatus a asignar
	 */
	public void setIdEstatus(Long idEstatus) {
		this.idEstatus = idEstatus;
	}
	
	/**
	 * M&eacute;todo de acceso al campo idsTipoMandamiento.
	 * @return El valor del campo idsTipoMandamiento
	 */
	public List<Long> getIdsTipoMandamiento() {
		return idsTipoMandamiento;
	}
	
	/**
	 * Asigna el valor al campo idsTipoMandamiento.
	 * @param idsTipoMandamiento a asignar
	 */
	public void setIdsTipoMandamiento(List<Long> idsTipoMandamiento) {
		this.idsTipoMandamiento = idsTipoMandamiento;
	}
	
	/**
	 * M&eacute;todo de acceso al campo claveFuncionario.
	 * @return El valor del campo claveFuncionario
	 */
	public Long getClaveFuncionario() {
		return claveFuncionario;
	}
	
	/**
	 * Asigna el valor al campo claveFuncionario.
	 * @param claveFuncionario a asignar
	 */
	public void setClaveFuncionario(Long claveFuncionario) {
		this.claveFuncionario = claveFuncionario;
	}
	
	/**
	 * M&eacute;todo de acceso al campo idInstitucion.
	 * @return El valor del campo idInstitucion
	 */
	public Long getIdInstitucion() {
		return idInstitucion;
	}
	
	/**
	 * Asigna el valor al campo idInstitucion.
	 * @param idInstitucion a asignar
	 */
	public void setIdInstitucion(Long idInstitucion) {
		this.idInstitucion = idInstitucion;
	}
}
