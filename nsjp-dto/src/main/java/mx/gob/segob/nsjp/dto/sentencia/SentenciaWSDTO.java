package mx.gob.segob.nsjp.dto.sentencia;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.dto.base.GenericWSDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteWSDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoWSDTO;
import mx.gob.segob.nsjp.dto.programas.RemisionWSDTO;

public class SentenciaWSDTO extends GenericWSDTO {

	// Fields
	/**
	 * 
	 */
	private static final long serialVersionUID = 1455546445806832054L;
	private Long sentenciaId;
	private ExpedienteWSDTO numeroExpedienteDTO;
	private Long tipoDeSentenciaId;
	private InvolucradoWSDTO involucradoDTO;
	private String cnus;
	private String cnumeroCausa;
	private Boolean blesionado;
	private Date dfechaInicioPena;
	private Date dfechaFinPena;
	private Long ipuntosPorAcumular;
	private Boolean bcumplida;
	private List<RemisionWSDTO> remisions = new ArrayList<RemisionWSDTO>();
	private Date dfechaCreacion;
	private Long idEstatusNumExp; 
	
	/**
	 * @return the sentenciaId
	 */
	public final Long getSentenciaId() {
		return sentenciaId;
	}
	
	/**
	 * @param sentenciaId the sentenciaId to set
	 */
	public final void setSentenciaId(Long sentenciaId) {
		this.sentenciaId = sentenciaId;
	}
	
	/**
	 * @return the numeroExpedienteDTO
	 */
	public final ExpedienteWSDTO getNumeroExpedienteDTO() {
		return numeroExpedienteDTO;
	}
	
	/**
	 * @param numeroExpedienteDTO the numeroExpedienteDTO to set
	 */
	public final void setNumeroExpedienteDTO(ExpedienteWSDTO numeroExpedienteDTO) {
		this.numeroExpedienteDTO = numeroExpedienteDTO;
	}
	
	/**
	 * @return the tipoDeSentenciaId
	 */
	public final Long getTipoDeSentenciaId() {
		return tipoDeSentenciaId;
	}
	
	/**
	 * @param tipoDeSentenciaId the tipoDeSentenciaId to set
	 */
	public final void setTipoDeSentenciaId(Long tipoDeSentenciaId) {
		this.tipoDeSentenciaId = tipoDeSentenciaId;
	}
	
	/**
	 * @return the involucradoDTO
	 */
	public final InvolucradoWSDTO getInvolucradoDTO() {
		return involucradoDTO;
	}
	
	/**
	 * @param involucradoDTO the involucradoDTO to set
	 */
	public final void setInvolucradoDTO(InvolucradoWSDTO involucradoDTO) {
		this.involucradoDTO = involucradoDTO;
	}
	
	/**
	 * @return the cnus
	 */
	public final String getCnus() {
		return cnus;
	}
	
	/**
	 * @param cnus the cnus to set
	 */
	public final void setCnus(String cnus) {
		this.cnus = cnus;
	}
	
	/**
	 * @return the cnumeroCausa
	 */
	public final String getCnumeroCausa() {
		return cnumeroCausa;
	}
	
	/**
	 * @param cnumeroCausa the cnumeroCausa to set
	 */
	public final void setCnumeroCausa(String cnumeroCausa) {
		this.cnumeroCausa = cnumeroCausa;
	}
	
	/**
	 * @return the blesionado
	 */
	public final Boolean getBlesionado() {
		return blesionado;
	}
	
	/**
	 * @param blesionado the blesionado to set
	 */
	public final void setBlesionado(Boolean blesionado) {
		this.blesionado = blesionado;
	}
	
	/**
	 * @return the dfechaInicioPena
	 */
	public final Date getDfechaInicioPena() {
		return dfechaInicioPena;
	}
	
	/**
	 * @param dfechaInicioPena the dfechaInicioPena to set
	 */
	public final void setDfechaInicioPena(Date dfechaInicioPena) {
		this.dfechaInicioPena = dfechaInicioPena;
	}
	
	/**
	 * @return the dfechaFinPena
	 */
	public final Date getDfechaFinPena() {
		return dfechaFinPena;
	}
	
	/**
	 * @param dfechaFinPena the dfechaFinPena to set
	 */
	public final void setDfechaFinPena(Date dfechaFinPena) {
		this.dfechaFinPena = dfechaFinPena;
	}
	
	/**
	 * @return the ipuntosPorAcumular
	 */
	public final Long getIpuntosPorAcumular() {
		return ipuntosPorAcumular;
	}
	
	/**
	 * @param ipuntosPorAcumular the ipuntosPorAcumular to set
	 */
	public final void setIpuntosPorAcumular(Long ipuntosPorAcumular) {
		this.ipuntosPorAcumular = ipuntosPorAcumular;
	}
	
	/**
	 * @return the bcumplida
	 */
	public final Boolean getBcumplida() {
		return bcumplida;
	}
	
	/**
	 * @param bcumplida the bcumplida to set
	 */
	public final void setBcumplida(Boolean bcumplida) {
		this.bcumplida = bcumplida;
	}
	
	/**
	 * M&eacute;todo de acceso al campo remisions.
	 * @return El valor del campo remisions
	 */
	public List<RemisionWSDTO> getRemisions() {
		return remisions;
	}
	
	/**
	 * Asigna el valor al campo remisions.
	 * @param remisions el valor remisions a asignar
	 */
	public void setRemisions(List<RemisionWSDTO> remisions) {
		this.remisions = remisions;
	}

	/**
	 * M&eacute;todo de acceso al campo dfechaCreacion.
	 * @return El valor del campo dfechaCreacion
	 */
	public Date getDfechaCreacion() {
		return dfechaCreacion;
	}

	/**
	 * Asigna el valor al campo dfechaCreacion.
	 * @param dfechaCreacion el valor dfechaCreacion a asignar
	 */
	public void setDfechaCreacion(Date dfechaCreacion) {
		this.dfechaCreacion = dfechaCreacion;
	}

	/**
	 * M&eacute;todo de acceso al campo idEstatusNumExp.
	 * @return El valor del campo idEstatusNumExp
	 */
	public Long getIdEstatusNumExp() {
		return idEstatusNumExp;
	}

	/**
	 * Asigna el valor al campo idEstatusNumExp.
	 * @param idEstatusNumExp el valor idEstatusNumExp a asignar
	 */
	public void setIdEstatusNumExp(Long idEstatusNumExp) {
		this.idEstatusNumExp = idEstatusNumExp;
	}
}