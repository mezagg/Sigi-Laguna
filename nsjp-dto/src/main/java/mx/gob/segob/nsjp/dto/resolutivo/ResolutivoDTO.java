/**
 * Nombre del Programa : ResolutivoDTO.java
 * Autor               : Daniel Jiménez
 * Compania            : Ultrasist
 * Proyecto            : NSJP                    Fecha: 27/06/2011
 * Marca de cambio     : N/A
 * Descripcion General : Clase que encapsula los datos de un resolutivo que se muestra en la vista
 * Programa Dependiente: N/A
 * Programa Subsecuente: N/A
 * Cond. de ejecucion  : N/A
 * Dias de ejecucion   : N/A                             Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor               :N/A
 * Compania            :N/A
 * Proyecto            :N/A                                   Fecha: N/A
 * Modificacion        :N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.dto.resolutivo;

import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.base.GenericDTO;
import mx.gob.segob.nsjp.dto.documento.MandamientoDTO;

/**
 * DTO sencillo para la creación de un resolutivo que se genero en la audiencia
 * 
 * @version 1.0
 * @author Emigdio Hernández
 * 
 */
public class ResolutivoDTO extends GenericDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3366514760149897157L;
	private Long resolutivoId;
	private AudienciaDTO audiencia;
	private Date temporizador;
	private String detalle;
	private List<MandamientoDTO> mandamientos;
	
	/**
	 * @return the resolutivoId
	 */
	public Long getResolutivoId() {
		return resolutivoId;
	}
	
	/**
	 * @param resolutivoId the resolutivoId to set
	 */
	public void setResolutivoId(Long resolutivoId) {
		this.resolutivoId = resolutivoId;
	}
	
	/**
	 * @return the audiencia
	 */
	public AudienciaDTO getAudiencia() {
		return audiencia;
	}
	
	/**
	 * @param audiencia the audiencia to set
	 */
	public void setAudiencia(AudienciaDTO audiencia) {
		this.audiencia = audiencia;
	}
	
	/**
	 * @return the temporizador
	 */
	public Date getTemporizador() {
		return temporizador;
	}
	
	/**
	 * @param temporizador the temporizador to set
	 */
	public void setTemporizador(Date temporizador) {
		this.temporizador = temporizador;
	}
	
	/**
	 * @return the detalle
	 */
	public String getDetalle() {
		return detalle;
	}
	
	/**
	 * @param detalle the detalle to set
	 */
	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	/**
	 * M&eacute;todo de acceso al campo mandamientos.
	 * @return El valor del campo mandamientos
	 */
	public List<MandamientoDTO> getMandamientos() {
		return mandamientos;
	}

	/**
	 * Asigna el valor al campo mandamientos.
	 * @param mandamientos a asignar
	 */
	public void setMandamientos(List<MandamientoDTO> mandamientos) {
		this.mandamientos = mandamientos;
	}

}
