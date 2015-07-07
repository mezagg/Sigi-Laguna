/**
 * Nombre del Programa		: MandamientoPersonaWSDTO.java
 * Autor                    : AlejandroGA
 * Compania                 : Ultrasist
 * Proyecto                 : NSJP						Fecha:27/06/2013 
 * Marca de cambio        	: N/A
 * Descripcion General    	: Clase WSDTO para mandamiento persona
 * Programa Dependiente  	: N/A
 * Programa Subsecuente 	: N/A
 * Cond. de ejecucion		: N/A
 * Dias de ejecucion		: N/A                       Horario: N/A
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

import mx.gob.segob.nsjp.dto.persona.PersonaWSDTO;

/**
 * @author AlejandroGA
 * @version 1.0
 */
public class MandamientoPersonaWSDTO {
	
	private Long estatus;
	private Date fechaEjecucion;
	private String folioInterInstitucional;
	private PersonaWSDTO persona;
	
	/**
	 * M&eacute;todo de acceso al campo estatus.
	 * @return El valor del campo estatus
	 */
	public Long getEstatus() {
		return estatus;
	}
	/**
	 * Asigna el valor al campo estatus.
	 * @param estatus a asignar
	 */
	public void setEstatus(Long estatus) {
		this.estatus = estatus;
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
	 * M&eacute;todo de acceso al campo persona.
	 * @return El valor del campo persona
	 */
	public PersonaWSDTO getPersona() {
		return persona;
	}
	/**
	 * Asigna el valor al campo persona.
	 * @param persona a asignar
	 */
	public void setPersona(PersonaWSDTO persona) {
		this.persona = persona;
	}
}
