package mx.gob.segob.nsjp.dto.hecho;

import java.util.Date;

import mx.gob.segob.nsjp.dto.base.GenericDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;

public class ConclusionHechoDTO extends GenericDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5630862562347818302L;
	
	private HechoDTO hecho;
	private Date fechaConclusion;
	private ValorDTO tipoConclusion;
	private ValorDTO tipoSubConclusion;
	
	
	public ConclusionHechoDTO() {
		super();
	}

	public ConclusionHechoDTO(HechoDTO hecho, Date fechaConclusion,
			ValorDTO tipoConclusion, ValorDTO tipoSubConclusion) {
		super();
		this.hecho = hecho;
		this.fechaConclusion = fechaConclusion;
		this.tipoConclusion = tipoConclusion;
		this.tipoSubConclusion = tipoSubConclusion;
	}
	
	
	public HechoDTO getHecho() {
		return hecho;
	}
	public void setHecho(HechoDTO hecho) {
		this.hecho = hecho;
	}
	public Date getFechaConclusion() {
		return fechaConclusion;
	}
	public void setFechaConclusion(Date fechaConclusion) {
		this.fechaConclusion = fechaConclusion;
	}
	public ValorDTO getTipoConclusion() {
		return tipoConclusion;
	}
	public void setTipoConclusion(ValorDTO tipoConclusion) {
		this.tipoConclusion = tipoConclusion;
	}
	public ValorDTO getTipoSubConclusion() {
		return tipoSubConclusion;
	}
	public void setTipoSubConclusion(ValorDTO tipoSubConclusion) {
		this.tipoSubConclusion = tipoSubConclusion;
	}

}
