/**
 * Nombre del Programa : ExpedienteSesionDTO.java
 * Autor                            : rgama
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 8 Apr 2011
 * Marca de cambio        : N/A
 * Descripcion General    : Archivo que se sube a sesion con toda la informacion 
 * minima y relavante de un expediente
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
package mx.gob.segob.nsjp.dto.expediente;

import mx.gob.segob.nsjp.dto.base.GenericDTO;


/**
 * Archivo que se sube a sesion con toda la informacion 
 * minima y relavante de un expediente
 * 
 * @version 1.0
 * @author rgama
 * 
 */
public class ExpedienteSesionDTO extends GenericDTO {

    /**
	 * 
	 */
    private static final long serialVersionUID = 76740324787091231L;

    /**
     * Id del expediente 
     */
    private Long expedienteId;
    
    /**
     * Número que se muestra al usuario.
     */
    
    private String numeroExpediente;
    
	/**
     * Id del numero de expediente, usado para solicitudes y audiencias
     * principalmente (sólo sistema).
     */
    private Long numeroExpedienteId;
    
 	private String numeroGeneralCaso;
 	
	private Long casoId;

    
	/**
	 * @return the expedienteId
	 */
	public Long getExpedienteId() {
		return expedienteId;
	}

	/**
	 * @param expedienteId the expedienteId to set
	 */
	public void setExpedienteId(Long expedienteId) {
		this.expedienteId = expedienteId;
	}

	/**
	 * @return the numeroExpediente
	 */
	public String getNumeroExpediente() {
		return numeroExpediente;
	}

	/**
	 * @param numeroExpediente the numeroExpediente to set
	 */
	public void setNumeroExpediente(String numeroExpediente) {
		this.numeroExpediente = numeroExpediente;
	}

	/**
	 * @return the numeroExpedienteId
	 */
	public Long getNumeroExpedienteId() {
		return numeroExpedienteId;
	}

	/**
	 * @param numeroExpedienteId the numeroExpedienteId to set
	 */
	public void setNumeroExpedienteId(Long numeroExpedienteId) {
		this.numeroExpedienteId = numeroExpedienteId;
	}

	/**
	 * @return the numeroGeneralCaso
	 */
	public String getNumeroGeneralCaso() {
		return numeroGeneralCaso;
	}

	/**
	 * @param numeroGeneralCaso the numeroGeneralCaso to set
	 */
	public void setNumeroGeneralCaso(String numeroGeneralCaso) {
		this.numeroGeneralCaso = numeroGeneralCaso;
	}

	/**
	 * @return the casoId
	 */
	public Long getCasoId() {
		return casoId;
	}

	/**
	 * @param casoId the casoId to set
	 */
	public void setCasoId(Long casoId) {
		this.casoId = casoId;
	}
    	
}
