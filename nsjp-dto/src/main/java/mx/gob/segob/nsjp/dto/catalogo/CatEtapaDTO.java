/**
 * Nombre del Programa : CatEtapaDTO.java
 * Autor                            : GustavoBP
 * Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 21/01/2013
 * Marca de cambio        : N/A
 * Descripcion General    : DTO que representa un registro de Etapa.
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
package mx.gob.segob.nsjp.dto.catalogo;

import java.util.List;

/**
 * Objeto de transferencia que representa un registro en Etapa.
 * 
 * @author GustavoBP
 */
public class CatEtapaDTO implements java.io.Serializable {

	private static final long serialVersionUID = -4447668576072560585L;
	
	private Long catEtapaId;
	private ValorDTO catEtapaValor;
	private CatEtapaDTO catEtapaPadre;
	private Boolean esEtapaExpediente;
	
	//Atributos utilizados para la manipulación en vista
	private Boolean esEtapaPadre;
	private List<CatEtapaDTO> etapasHijas;
	
	public CatEtapaDTO(){
		
	}
	
	public CatEtapaDTO(Long catEtapaId) {
		this.catEtapaId = catEtapaId;
	}
	/**
	 * @return the catEtapaId
	 */
	public Long getCatEtapaId() {
		return catEtapaId;
	}

	/**
	 * @param catEtapaId the catEtapaId to set
	 */
	public void setCatEtapaId(Long catEtapaId) {
		this.catEtapaId = catEtapaId;
	}

	/**
	 * @return the catEtapa
	 */
	public ValorDTO getCatEtapaValor() {
		return catEtapaValor;
	}

	/**
	 * @param catEtapa the catEtapa to set
	 */
	public void setCatEtapaValor(ValorDTO catEtapa) {
		this.catEtapaValor = catEtapa;
	}

	/**
	 * @return the catEtapaPadre
	 */
	public CatEtapaDTO getCatEtapaPadre() {
		return catEtapaPadre;
	}

	/**
	 * @param catEtapaPadre the catEtapaPadre to set
	 */
	public void setCatEtapaPadre(CatEtapaDTO catEtapaPadre) {
		this.catEtapaPadre = catEtapaPadre;
	}

	/**
	 * @return the esEtapaExpediente
	 */
	public Boolean getEsEtapaExpediente() {
		return esEtapaExpediente;
	}

	/**
	 * @param esEtapaExpediente the esEtapaExpediente to set
	 */
	public void setEsEtapaExpediente(Boolean esEtapaExpediente) {
		this.esEtapaExpediente = esEtapaExpediente;
	}

	/**
	 * @return the esEtapaPadre
	 */
	public Boolean getEsEtapaPadre() {
		return esEtapaPadre;
	}

	/**
	 * @param esEtapaPadre the esEtapaPadre to set
	 */
	public void setEsEtapaPadre(Boolean esEtapaPadre) {
		this.esEtapaPadre = esEtapaPadre;
	}

	/**
	 * @return the etapasHijas
	 */
	public List<CatEtapaDTO> getEtapasHijas() {
		return etapasHijas;
	}

	/**
	 * @param etapasHijas the etapasHijas to set
	 */
	public void setEtapasHijas(List<CatEtapaDTO> etapasHijas) {
		this.etapasHijas = etapasHijas;
	}
}
