/**
 * 
 */
package mx.gob.segob.nsjp.dto.hecho;

import java.util.Date;

import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;

/**
 * @author JorgeFO
 *
 */
public class ConclusionNumeroExpedienteDTO {
	
	private Long numeroExpediente;
	private Date fechaConclusion;
	private ValorDTO tipoConclusion;
	private ValorDTO tipoSubConclusion;
	private String fechaConclusionFortmat;
	/**
	 * @return the numeroExpediente
	 */
	public Long getNumeroExpediente() {
		return numeroExpediente;
	}
	/**
	 * @param numeroExpediente the numeroExpediente to set
	 */
	public void setNumeroExpediente(Long numeroExpediente) {
		this.numeroExpediente = numeroExpediente;
	}
	/**
	 * @return the fechaConclusion
	 */
	public Date getFechaConclusion() {
		return fechaConclusion;
	}
	/**
	 * @param fechaConclusion the fechaConclusion to set
	 */
	public void setFechaConclusion(Date fechaConclusion) {
		this.fechaConclusion = fechaConclusion;
	}
	/**
	 * @return the tipoConclusion
	 */
	public ValorDTO getTipoConclusion() {
		return tipoConclusion;
	}
	/**
	 * @param tipoConclusion the tipoConclusion to set
	 */
	public void setTipoConclusion(ValorDTO tipoConclusion) {
		this.tipoConclusion = tipoConclusion;
	}
	/**
	 * @return the tipoSubConclusion
	 */
	public ValorDTO getTipoSubConclusion() {
		return tipoSubConclusion;
	}
	/**
	 * @param tipoSubConclusion the tipoSubConclusion to set
	 */
	public void setTipoSubConclusion(ValorDTO tipoSubConclusion) {
		this.tipoSubConclusion = tipoSubConclusion;
	}
	/**
	 * @return the fechaConclusionFortmat
	 */
	public String getFechaConclusionFortmat() {
		return fechaConclusionFortmat;
	}
	/**
	 * @param fechaConclusionFortmat the fechaConclusionFortmat to set
	 */
	public void setFechaConclusionFortmat(String fechaConclusionFortmat) {
		this.fechaConclusionFortmat = fechaConclusionFortmat;
	}
	
	
}
