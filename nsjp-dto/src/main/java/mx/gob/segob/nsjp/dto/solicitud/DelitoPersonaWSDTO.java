
/**
 * Nombre del Programa	:DelitoPersonaWSDTO.java
 * Autor 				:Alejandro Galaviz
 * Compania				:Ultrasist
 * Proyecto				:NSJP				Fecha: 26/09/2012
 * Marca de cambio		:N/A
 * Descripcion General	:WSDTO para recuperar las relaciones delito persona
 * Programa Dependiente	:N/A
 * Programa Subsecuente	:N/A
 * Cond. de ejecucion	:N/A
 * Dias de ejecucion	:N/A                             Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor				:N/A
 * Compania				:N/A
 * Proyecto				:N/A				Fecha: N/A
 * Modificacion			:N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.dto.solicitud;

import mx.gob.segob.nsjp.dto.base.GenericWSDTO;

/**
 * @author AlejandroGA
 * @version 1.0
 * WSDTO para recuperar las relaciones delito persona mediante los folios de 
 * los involucrados y la clave interinstitucional del delito
 */
public class DelitoPersonaWSDTO extends GenericWSDTO {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7672125129149519867L;
	
	private String folioProbableResponsable;
	private String folioVictima;
	private Long bienTutelado;
	private Long formaParticipacion;
	private Boolean esActivo;
	private Long catDelitoClasificacionId;
    private Long catDelitoLugarId;
    private Long catDelitoModalidadId;
    private Long catDelitoModusId;
    private Long catDelitoCausaId;
    // Se agregan nuevos atributos
    private String folioInterInstitucionalDelitoPersona;
    // Se agregan atributos del delito
    private String claveInterIntitucionalDelito;
	private Boolean esProbable;
	private Boolean esPincipal;
    
    
    
	
	/**
	 * @return the folioProbableResponsable
	 */
	public String getFolioProbableResponsable() {
		return folioProbableResponsable;
	}
	/**
	 * @param folioProbableResponsable the folioProbableResponsable to set
	 */
	public void setFolioProbableResponsable(String folioProbableResponsable) {
		this.folioProbableResponsable = folioProbableResponsable;
	}
	/**
	 * @return the folioVictima
	 */
	public String getFolioVictima() {
		return folioVictima;
	}
	/**
	 * @param folioVictima the folioVictima to set
	 */
	public void setFolioVictima(String folioVictima) {
		this.folioVictima = folioVictima;
	}
	/**
	 * @return the claveInterIntitucionalDelito
	 */
	public String getClaveInterIntitucionalDelito() {
		return claveInterIntitucionalDelito;
	}
	/**
	 * @param claveInterIntitucionalDelito the claveInterIntitucionalDelito to set
	 */
	public void setClaveInterIntitucionalDelito(String claveInterIntitucionalDelito) {
		this.claveInterIntitucionalDelito = claveInterIntitucionalDelito;
	}
	/**
	 * @return the esPincipal
	 */
	public Boolean getEsPincipal() {
		return esPincipal;
	}
	/**
	 * @param esPincipal the esPincipal to set
	 */
	public void setEsPincipal(Boolean esPincipal) {
		this.esPincipal = esPincipal;
	}
	/**
	 * @return the bienTutelado
	 */
	public Long getBienTutelado() {
		return bienTutelado;
	}
	/**
	 * @param bienTutelado the bienTutelado to set
	 */
	public void setBienTutelado(Long bienTutelado) {
		this.bienTutelado = bienTutelado;
	}
	/**
	 * @return the formaParticipacion
	 */
	public Long getFormaParticipacion() {
		return formaParticipacion;
	}
	/**
	 * @param formaParticipacion the formaParticipacion to set
	 */
	public void setFormaParticipacion(Long formaParticipacion) {
		this.formaParticipacion = formaParticipacion;
	}
	/**
	 * @return the esActivo
	 */
	public Boolean getEsActivo() {
		return esActivo;
	}
	/**
	 * @param esActivo the esActivo to set
	 */
	public void setEsActivo(Boolean esActivo) {
		this.esActivo = esActivo;
	}
	/**
	 * @return the catDelitoClasificacionId
	 */
	public Long getCatDelitoClasificacionId() {
		return catDelitoClasificacionId;
	}
	/**
	 * @param catDelitoClasificacionId the catDelitoClasificacionId to set
	 */
	public void setCatDelitoClasificacionId(Long catDelitoClasificacionId) {
		this.catDelitoClasificacionId = catDelitoClasificacionId;
	}
	/**
	 * @return the catDelitoLugarId
	 */
	public Long getCatDelitoLugarId() {
		return catDelitoLugarId;
	}
	/**
	 * @param catDelitoLugarId the catDelitoLugarId to set
	 */
	public void setCatDelitoLugarId(Long catDelitoLugarId) {
		this.catDelitoLugarId = catDelitoLugarId;
	}
	/**
	 * @return the catDelitoModalidadId
	 */
	public Long getCatDelitoModalidadId() {
		return catDelitoModalidadId;
	}
	/**
	 * @param catDelitoModalidadId the catDelitoModalidadId to set
	 */
	public void setCatDelitoModalidadId(Long catDelitoModalidadId) {
		this.catDelitoModalidadId = catDelitoModalidadId;
	}
	/**
	 * @return the catDelitoModusId
	 */
	public Long getCatDelitoModusId() {
		return catDelitoModusId;
	}
	/**
	 * @param catDelitoModusId the catDelitoModusId to set
	 */
	public void setCatDelitoModusId(Long catDelitoModusId) {
		this.catDelitoModusId = catDelitoModusId;
	}
	/**
	 * @return the catDelitoCausaId
	 */
	public Long getCatDelitoCausaId() {
		return catDelitoCausaId;
	}
	/**
	 * @param catDelitoCausaId the catDelitoCausaId to set
	 */
	public void setCatDelitoCausaId(Long catDelitoCausaId) {
		this.catDelitoCausaId = catDelitoCausaId;
	}
	/**
	 * @return the folioInterInstitucionalDelitoPersona
	 */
	public String getFolioInterInstitucionalDelitoPersona() {
		return folioInterInstitucionalDelitoPersona;
	}
	/**
	 * @param folioInterInstitucionalDelitoPersona the folioInterInstitucionalDelitoPersona to set
	 */
	public void setFolioInterInstitucionalDelitoPersona(
			String folioInterInstitucionalDelitoPersona) {
		this.folioInterInstitucionalDelitoPersona = folioInterInstitucionalDelitoPersona;
	}
	/**
	 * @return the esProbable
	 */
	public Boolean getEsProbable() {
		return esProbable;
	}
	/**
	 * @param esProbable the esProbable to set
	 */
	public void setEsProbable(Boolean esProbable) {
		this.esProbable = esProbable;
	}

}
