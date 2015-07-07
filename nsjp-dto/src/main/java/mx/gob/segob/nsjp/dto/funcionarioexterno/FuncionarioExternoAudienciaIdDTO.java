/**
 * Nombre del Programa	: FuncionarioExternoAudienciaIdDTO.java
 * Autor				: AlejandroGA
 * Compania             : Ultrasist
 * Proyecto             : NSJP                    Fecha: 28 Feb 2013
 * Marca de cambio      : N/A
 * Descripcion General  : FuncionarioExternoAudienciaIdDTO
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
package mx.gob.segob.nsjp.dto.funcionarioexterno;

import mx.gob.segob.nsjp.dto.base.GenericDTO;

/**
 * @author AlejandroGA
 *
 */
public class FuncionarioExternoAudienciaIdDTO extends GenericDTO{
	
	private static final long serialVersionUID = 4914006475132838922L;
	
	private Long audienciaId;
	private Long funcionarioExternoId;
	
	/**
	 * Default constructor
	 */
	public FuncionarioExternoAudienciaIdDTO() {
	}


	/**
	 * @param audienciaId
	 * @param funcionarioExternoId
	 */
	public FuncionarioExternoAudienciaIdDTO(Long audienciaId,
			Long funcionarioExternoId) {
		
		this.audienciaId = audienciaId;
		this.funcionarioExternoId = funcionarioExternoId;
	}
	
	
	/**
	 * M&eacute;todo de acceso al campo audienciaId.
	 * @return El valor de audienciaId
	 */
	public Long getAudienciaId() {
		return audienciaId;
	}
	
	/**
	 * M&eacute;todo de acceso al campo funcionarioExternoId.
	 * @return El valor de funcionarioExternoId
	 */
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
