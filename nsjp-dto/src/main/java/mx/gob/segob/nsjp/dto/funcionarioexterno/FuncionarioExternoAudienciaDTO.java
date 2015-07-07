/**
 * Nombre del Programa	: FuncionarioExternoAudienciaDTO.java
 * Autor				: AlejandroGA
 * Compania             : Ultrasist
 * Proyecto             : NSJP                    Fecha: 28 Feb 2013
 * Marca de cambio      : N/A
 * Descripcion General  : FuncionarioExternoAudienciaDTO
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

import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.base.GenericDTO;
import mx.gob.segob.nsjp.dto.configuracion.ConfInstitucionDTO;

/**
 * @author AlejandroGA
 * @version 1.0
 */
public class FuncionarioExternoAudienciaDTO extends GenericDTO{
	
	private static final long serialVersionUID = -13846557662823339L;
	
	private FuncionarioExternoAudienciaIdDTO funcionarioExternoAudienciaIdDTO;
	private FuncionarioExternoDTO FuncionarioExternoDTO; 
	private AudienciaDTO audienciaDTO;
	private ConfInstitucionDTO confInstitucionDTO; //TODO QUITAR
	private Boolean esPresente;
	private Boolean esTitular;
	
	
	/**
	 *Default constructor 
	 */
	public FuncionarioExternoAudienciaDTO() {
	}

	/**
	 * @param funcionarioExternoAudienciaIdDTO
	 */
	public FuncionarioExternoAudienciaDTO(
			FuncionarioExternoAudienciaIdDTO funcionarioExternoAudienciaIdDTO) {
		this.funcionarioExternoAudienciaIdDTO = funcionarioExternoAudienciaIdDTO;
	}
	
	/**
	 * @param funcionarioExternoAudienciaIdDTO
	 * @param funcionarioExternoDTO
	 * @param audienciaDTO
	 * @param confInstitucionDTO
	 */
	public FuncionarioExternoAudienciaDTO(
			FuncionarioExternoAudienciaIdDTO funcionarioExternoAudienciaIdDTO,
			FuncionarioExternoDTO funcionarioExternoDTO,
			AudienciaDTO audienciaDTO,ConfInstitucionDTO confInstitucionDTO) {

		this.funcionarioExternoAudienciaIdDTO = funcionarioExternoAudienciaIdDTO;
		this.FuncionarioExternoDTO = funcionarioExternoDTO;
		this.audienciaDTO = audienciaDTO;
		this.confInstitucionDTO = confInstitucionDTO;
	}
	

	/**
	 * M&eacute;todo de acceso al campo funcionarioExternoAudienciaIdDTO
	 * @return funcionarioExternoAudienciaIdDTO
	 */
	public FuncionarioExternoAudienciaIdDTO getFuncionarioExternoAudienciaIdDTO() {
		return funcionarioExternoAudienciaIdDTO;
	}
	
	/**
	 * M&eacute;todo de acceso al campo
	 * @return FuncionarioExternoDTO
	 */
	public FuncionarioExternoDTO getFuncionarioExternoDTO() {
		return FuncionarioExternoDTO;
	}
	
	/**
	 * M&eacute;todo de acceso al campo
	 * @return audienciaDTO
	 */
	public AudienciaDTO getAudienciaDTO() {
		return audienciaDTO;
	}
	
	/**
	 * M&eacute;todo de acceso al campo
	 * @return confInstitucionDTO
	 */
	public ConfInstitucionDTO getConfInstitucionDTO() {
		return confInstitucionDTO;
	}
	
	/**
	 * M&eacute;todo de acceso al campo
	 * @return esPresente
	 */
	public Boolean getEsPresente() {
		return esPresente;
	}
	
	/**
	 * M&eacute;todo de acceso al campo
	 * @return esTitular
	 */
	public Boolean getEsTitular() {
		return esTitular;
	}
	
	/**
	 * Asigna el valor al campo funcionarioExternoAudienciaIdDTO
	 * @param funcionarioExternoAudienciaIdDTO
	 */
	public void setFuncionarioExternoAudienciaIdDTO(
			FuncionarioExternoAudienciaIdDTO funcionarioExternoAudienciaIdDTO) {
		this.funcionarioExternoAudienciaIdDTO = funcionarioExternoAudienciaIdDTO;
	}
	
	/**
	 * Asigna el valor al campo funcionarioExternoDTO
	 * @param funcionarioExternoDTO
	 */
	public void setFuncionarioExternoDTO(FuncionarioExternoDTO funcionarioExternoDTO) {
		FuncionarioExternoDTO = funcionarioExternoDTO;
	}
	
	/**
	 * Asigna el valor al campo audienciaDTO
	 * @param audienciaDTO
	 */
	public void setAudienciaDTO(AudienciaDTO audienciaDTO) {
		this.audienciaDTO = audienciaDTO;
	}
	
	/**
	 * Asigna el valor al campo confInstitucionDTO
	 * @param confInstitucionDTO
	 */
	public void setConfInstitucionDTO(ConfInstitucionDTO confInstitucionDTO) {
		this.confInstitucionDTO = confInstitucionDTO;
	}
	
	/**
	 * Asigna el valor al campo esPresente
	 * @param esPresente
	 */
	public void setEsPresente(Boolean esPresente) {
		this.esPresente = esPresente;
	}
	
	/**
	 * Asigna el valor al campo esTitular
	 * @param esTitular
	 */
	public void setEsTitular(Boolean esTitular) {
		this.esTitular = esTitular;
	}
}
