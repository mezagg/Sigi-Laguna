/**
 * Nombre del Programa		: SalaTemporalDTO.java
 * Autor                    : AlejandroGA
 * Compania                 : Ultrasist
 * Proyecto                 : NSJP						Fecha: 24/08/2011
 * Marca de cambio        	: N/A
 * Descripcion General    	: Describir el objetivo de la clase de manera breve
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
package mx.gob.segob.nsjp.dto.audiencia;

import mx.gob.segob.nsjp.dto.base.GenericDTO;

/**
 * @author AlejandroGA
 * @version 1.1
 */
public class SalaTemporalDTO extends GenericDTO{
	
	
	
	/**
	 * Default serial
	 */
	private static final long serialVersionUID = 6922146009383316763L;
	
	private Long salaTemporalId;
	private String motivo;
	private String domicilioSala;
	private String ubicacionSala;
	
	/**
	 * Constructor default
	 */
	public SalaTemporalDTO() {
		
	}

	/**
	 * Full constructor
	 * @param salaTemporalId
	 * @param motivo
	 * @param domicilioSala
	 * @param ubicacionSala
	 */
	public SalaTemporalDTO(Long salaTemporalId, String motivo,
			String domicilioSala, String ubicacionSala) {
		super();
		this.salaTemporalId = salaTemporalId;
		this.motivo = motivo;
		this.domicilioSala = domicilioSala;
		this.ubicacionSala = ubicacionSala;
	}

	/**
	 * @return the salaTemporalId
	 */
	public Long getSalaTemporalId() {
		return salaTemporalId;
	}

	/**
	 * @param salaTemporalId the salaTemporalId to set
	 */
	public void setSalaTemporalId(Long salaTemporalId) {
		this.salaTemporalId = salaTemporalId;
	}

	/**
	 * @return the motivo
	 */
	public String getMotivo() {
		return motivo;
	}

	/**
	 * @param motivo the motivo to set
	 */
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	/**
	 * @return the domicilioSala
	 */
	public String getDomicilioSala() {
		return domicilioSala;
	}

	/**
	 * @param domicilioSala the domicilioSala to set
	 */
	public void setDomicilioSala(String domicilioSala) {
		this.domicilioSala = domicilioSala;
	}

	/**
	 * @return the ubicacionSala
	 */
	public String getUbicacionSala() {
		return ubicacionSala;
	}

	/**
	 * @param ubicacionSala the ubicacionSala to set
	 */
	public void setUbicacionSala(String ubicacionSala) {
		this.ubicacionSala = ubicacionSala;
	}
}
