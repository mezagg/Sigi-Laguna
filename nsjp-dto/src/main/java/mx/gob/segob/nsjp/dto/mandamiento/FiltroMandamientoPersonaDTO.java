/**
 * Nombre del Programa		: FiltroMandamientoPersonaDTO.java
 * Autor                    : AlejandroGA
 * Compania                 : Ultrasist
 * Proyecto                 : NSJP						Fecha:19/06/2013 
 * Marca de cambio        	: N/A
 * Descripcion General    	: Clase DTO que sirve como filtro para la consulta de 
 * 							  mandamientos persona
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
public class FiltroMandamientoPersonaDTO {
	
	private Long mandamientoId;
	private Long personaId;
	private Long mandamientoPersonaId;
	private Long estatusId;
	private Date fechaEjecucion;
	private String folioInterInstitucional;
	private List<Long> tipoMandamiento;
	private Long audienciaId;
	
	
	/**
	 * M&eacute;todo de acceso al campo mandamientoId.
	 * @return El valor del campo mandamientoId
	 */
	public Long getMandamientoId() {
		return mandamientoId;
	}
	/**
	 * Asigna el valor al campo mandamientoId.
	 * @param mandamientoId a asignar
	 */
	public void setMandamientoId(Long mandamientoId) {
		this.mandamientoId = mandamientoId;
	}
	/**
	 * M&eacute;todo de acceso al campo personaId.
	 * @return El valor del campo personaId
	 */
	public Long getPersonaId() {
		return personaId;
	}
	/**
	 * Asigna el valor al campo personaId.
	 * @param personaId a asignar
	 */
	public void setPersonaId(Long personaId) {
		this.personaId = personaId;
	}
	/**
	 * M&eacute;todo de acceso al campo mandamientoPersonaId.
	 * @return El valor del campo mandamientoPersonaId
	 */
	public Long getMandamientoPersonaId() {
		return mandamientoPersonaId;
	}
	/**
	 * Asigna el valor al campo mandamientoPersonaId.
	 * @param mandamientoPersonaId a asignar
	 */
	public void setMandamientoPersonaId(Long mandamientoPersonaId) {
		this.mandamientoPersonaId = mandamientoPersonaId;
	}
	/**
	 * M&eacute;todo de acceso al campo estatusId.
	 * @return El valor del campo estatusId
	 */
	public Long getEstatusId() {
		return estatusId;
	}
	/**
	 * Asigna el valor al campo estatusId.
	 * @param estatusId a asignar
	 */
	public void setEstatusId(Long estatusId) {
		this.estatusId = estatusId;
	}
	/**
	 * M&eacute;todo de acceso al campo fechaEjecucion.
	 * @return El valor del campo fechaEjecucion
	 */
	public Date getFechaEjecucion() {
		return fechaEjecucion;
	}
	/**
	 * Asigna el valor al campo fechaEjecucion.
	 * @param fechaEjecucion a asignar
	 */
	public void setFechaEjecucion(Date fechaEjecucion) {
		this.fechaEjecucion = fechaEjecucion;
	}
	/**
	 * M&eacute;todo de acceso al campo folioInterInstitucional.
	 * @return El valor del campo folioInterInstitucional
	 */
	public String getFolioInterInstitucional() {
		return folioInterInstitucional;
	}
	/**
	 * Asigna el valor al campo folioInterInstitucional.
	 * @param folioInterInstitucional a asignar
	 */
	public void setFolioInterInstitucional(String folioInterInstitucional) {
		this.folioInterInstitucional = folioInterInstitucional;
	}
	/**
	 * M&eacute;todo de acceso al campo tipoMandamiento.
	 * @return El valor del campo tipoMandamiento
	 */
	public List<Long> getTipoMandamiento() {
		return tipoMandamiento;
	}
	/**
	 * Asigna el valor al campo tipoMandamiento.
	 * @param tipoMandamiento a asignar
	 */
	public void setTipoMandamiento(List<Long> tipoMandamiento) {
		this.tipoMandamiento = tipoMandamiento;
	}
	/**
	 * M&eacute;todo de acceso al campo audienciaId.
	 * @return El valor del campo audienciaId
	 */
	public Long getAudienciaId() {
		return audienciaId;
	}
	/**
	 * Asigna el valor al campo audienciaId.
	 * @param audienciaId a asignar
	 */
	public void setAudienciaId(Long audienciaId) {
		this.audienciaId = audienciaId;
	}
}
