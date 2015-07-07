/**
 * Nombre del Programa : GuardadoDefinitivoDTO.java
 * Autor                            : rgama
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 09 Oct 2012
 * Marca de cambio        : N/A
 * Descripcion General    : DTO para la transferencia de parametros de GuardadoDefinitivo de un documento
 * entre la vista y servicios.
 * Programa Dependiente  :N/A
 * Programa Subsecuente :N/A
 * Cond. de ejecucion        :N/A
 * Dias de ejecucion          :N/A                             Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                       :N/A
 * Compania               :N/A
 * Proyecto                 :N/A                                 Fecha: N/A
 * Modificacion           :N/A
 *------------------------------------------------------------------------------
 */

package mx.gob.segob.nsjp.dto.documento;

import mx.gob.segob.nsjp.dto.base.GenericDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDTO;

/**
 * DTO para la transferencia de parametros de GuardadoDefinitivo de un documento entre la vista y
 * servicios.
 * 
 * @version 1.0
 * @author rgama
 * 
 */
public class GuardadoDefinitivoDTO extends GenericDTO {

	private static final long serialVersionUID = 165768349454775244L;
	
	private ExpedienteDTO expedienteDTO;
	private DocumentoDTO documentoDTO;
	private Long idActividad;
	private Long idClaveFuncionarioAsignado;
	private FuncionarioDTO funcionarioDTO;
	private Long idNuevaActividad;
	private Boolean esGuardadoParcial;
	private SolicitudDTO solicitudDTO;
	private String idsInvolucrados;
	
	private Long solicitudPeritoId;
	private Long involcradoId;
	private Long confActividadId;
	
	/**
	 * @return the expediente
	 */
	public ExpedienteDTO getExpedienteDTO() {
		return expedienteDTO;
	}
	/**
	 * @param expediente the expedienteDTO to set
	 */
	public void setExpedienteDTO(ExpedienteDTO expediente) {
		this.expedienteDTO = expediente;
	}
	/**
	 * @return the documentoDTO
	 */
	public DocumentoDTO getDocumentoDTO() {
		return documentoDTO;
	}
	/**
	 * @param documentoDTO the documentoDTO to set
	 */
	public void setDocumentoDTO(DocumentoDTO documentoDTO) {
		this.documentoDTO = documentoDTO;
	}
	/**
	 * @return the idActividad
	 */
	public Long getIdActividad() {
		return idActividad;
	}
	/**
	 * @param idActividad the idActividad to set
	 */
	public void setIdActividad(Long idActividad) {
		this.idActividad = idActividad;
	}
	/**
	 * @return the idClaveFuncionarioAsignado
	 */
	public Long getIdClaveFuncionarioAsignado() {
		return idClaveFuncionarioAsignado;
	}
	/**
	 * @param idClaveFuncionarioAsignado the idClaveFuncionarioAsignado to set
	 */
	public void setIdClaveFuncionarioAsignado(Long idClaveFuncionarioAsignado) {
		this.idClaveFuncionarioAsignado = idClaveFuncionarioAsignado;
	}
	/**
	 * @return the funcionarioDTO
	 */
	public FuncionarioDTO getFuncionarioDTO() {
		return funcionarioDTO;
	}
	/**
	 * @param funcionarioDTO the funcionarioDTO to set
	 */
	public void setFuncionarioDTO(FuncionarioDTO funcionarioDTO) {
		this.funcionarioDTO = funcionarioDTO;
	}
	/**
	 * @return the idNuevaActividad
	 */
	public Long getIdNuevaActividad() {
		return idNuevaActividad;
	}
	/**
	 * @param idNuevaActividad the idNuevaActividad to set
	 */
	public void setIdNuevaActividad(Long idNuevaActividad) {
		this.idNuevaActividad = idNuevaActividad;
	}
	/**
	 * @return the esGuardadoParcial
	 */
	public Boolean getEsGuardadoParcial() {
		return esGuardadoParcial;
	}
	/**
	 * @param esGuardadoParcial the esGuardadoParcial to set
	 */
	public void setEsGuardadoParcial(Boolean esGuardadoParcial) {
		this.esGuardadoParcial = esGuardadoParcial;
	}
	/**
	 * @return the solicitudDTO
	 */
	public SolicitudDTO getSolicitudDTO() {
		return solicitudDTO;
	}
	/**
	 * @param solicitudDTO the solicitudDTO to set
	 */
	public void setSolicitudDTO(SolicitudDTO solicitudDTO) {
		this.solicitudDTO = solicitudDTO;
	}
	/**
	 * @return the idsInvolucrados
	 */
	public String getIdsInvolucrados() {
		return idsInvolucrados;
	}
	/**
	 * @param idsInvolucrados the idsInvolucrados to set
	 */
	public void setIdsInvolucrados(String idsInvolucrados) {
		this.idsInvolucrados = idsInvolucrados;
	}
	/**
	 * @return the solicitudPeritoId
	 */
	public Long getSolicitudPeritoId() {
		return solicitudPeritoId;
	}
	/**
	 * @param solicitudPeritoId the solicitudPeritoId to set
	 */
	public void setSolicitudPeritoId(Long solicitudPeritoId) {
		this.solicitudPeritoId = solicitudPeritoId;
	}
	
	/**
	 * @return the involcradoId
	 */
	public Long getInvolcradoId() {
		return involcradoId;
	}
	/**
	 * @param involcradoId the involcradoId to set
	 */
	public void setInvolcradoId(Long involcradoId) {
		this.involcradoId = involcradoId;
	}
	/**
	 * @return the confActividadId
	 */
	public Long getConfActividadId() {
		return confActividadId;
	}
	/**
	 * @param confActividadId the confActividadId to set
	 */
	public void setConfActividadId(Long confActividadId) {
		this.confActividadId = confActividadId;
	}
	
}
