/**
 * Nombre del Programa	: FuncionarioExternoAudienciaId.java
 * Autor				: AlejandroGA
 * Compania             : Ultrasist
 * Proyecto             : NSJP                    Fecha: 28 Feb 2013
 * Marca de cambio      : N/A
 * Descripcion General  : FuncionarioExternoAudienciaId, Llave de la entidad de 
 * 						cruce de funcionarioExternoAudiencia
 * Programa Dependiente	: N/A
 * Programa Subsecuente	: N/A
 * Cond. de ejecucion   : N/A
 * Dias de ejecucion    : N/A                             Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                : N/A
 * Compania             : N/A
 * Proyecto             : N/A                             Fecha: N/A
 * Modificacion         : N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author AlejandroGA
 * @version 1.0
 * Llave de la entidad de cruce de funcionarioExternoAudiencia
 */

@Embeddable
public class FuncionarioExternoAudienciaId implements java.io.Serializable{
	
	private static final long serialVersionUID = -665387300943290367L;

	private Long audienciaId;
	private Long funcionarioExternoId;
	
	/**
	 *Default Constructor 
	 */
	public FuncionarioExternoAudienciaId() {
	
	}
	
	/**
	 * @param audienciaId
	 * @param funcionarioExternoId
	 * @param confInstitucionId
	 */
	public FuncionarioExternoAudienciaId(Long audienciaId,
			Long funcionarioExternoId) {
		this.audienciaId = audienciaId;
		this.funcionarioExternoId = funcionarioExternoId;
	}
	
	/**
	 * M&eacute;todo de acceso al campo audienciaId.
	 * @return El valor de audienciaId
	 */
	
	@Column(name = "Audiencia_id", nullable = false, precision = 18, scale = 0)
	public Long getAudienciaId() {
		return audienciaId;
	}
	
	/**
	 * M&eacute;todo de acceso al campo funcionarioExternoId.
	 * @return El valor de funcionarioExternoId
	 */
	@Column(name = "FuncionarioExterno_id", nullable = false, precision = 18, scale = 0)
	public Long getFuncionarioExternoId() {
		return funcionarioExternoId;
	}
	
	/**
	 * Asigna el valor al campo audienciaId
	 * @param audienciaId
	 */
	public void setAudienciaId(Long audienciaId) {
		this.audienciaId = audienciaId;
	}
	
	/**
	 * Asigna el valor al campo funcionarioExternoId
	 * @param funcionarioExternoId
	 */
	public void setFuncionarioExternoId(Long funcionarioExternoId) {
		this.funcionarioExternoId = funcionarioExternoId;
	}
}
