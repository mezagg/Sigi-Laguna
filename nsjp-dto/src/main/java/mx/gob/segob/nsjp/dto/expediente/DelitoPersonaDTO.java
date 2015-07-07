/**
* Nombre del Programa : DelitoPersonaDTO.java
* Autor                            : adrian
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 08/07/2011
* Marca de cambio        : N/A
* Descripcion General    : Describir el objetivo de la clase de manera breve
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
import mx.gob.segob.nsjp.dto.caso.CasoDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author adrian
 *
 */
public class DelitoPersonaDTO extends GenericDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6704804363615016528L;
	
	private Long delitoPersonaId;
	private ValorDTO bienTutelado;
	private ValorDTO formaParticipacion;
	private DelitoDTO delito;
	private InvolucradoDTO victima;
	private InvolucradoDTO probableResponsable;
	private Boolean esActivo;
	private Long catDelitoClasificacionId;
    private Long catDelitoLugarId;
    private Long catDelitoModalidadId;
    private Long catDelitoModusId;
    private Long catDelitoCausaId;
    private CasoDTO casoDTO;
    private String folioDelitoPersona;
    private Long catDelitoEstadisticaId;
    private Long catDelitoTipoId;
    private Long catDelitoCalificacionId;
    private Long catDelitoConcursoId;
    private Long catDelitoOrdenResId;
	
	
	public DelitoPersonaDTO(Long delitoPersonaId, ValorDTO bienTutelado,
			ValorDTO formaParticipacion, DelitoDTO delito,
			InvolucradoDTO victima, InvolucradoDTO probableResponsable,
			Boolean esActivo, Long catDelitoClasificacionId,
			Long catDelitoLugarId, Long catDelitoModalidadId,
			Long catDelitoModusId, Long catDelitoCausaId, CasoDTO casoDTO,
			String folioDelitoPersona, Long catDelitoEstadisticaId,
			Long catDelitoTipoId, Long catDelitoCalificacionId,
			Long catDelitoConcursoId, Long catDelitoOrdenResId) {
		super();
		this.delitoPersonaId = delitoPersonaId;
		this.bienTutelado = bienTutelado;
		this.formaParticipacion = formaParticipacion;
		this.delito = delito;
		this.victima = victima;
		this.probableResponsable = probableResponsable;
		this.esActivo = esActivo;
		this.catDelitoClasificacionId = catDelitoClasificacionId;
		this.catDelitoLugarId = catDelitoLugarId;
		this.catDelitoModalidadId = catDelitoModalidadId;
		this.catDelitoModusId = catDelitoModusId;
		this.catDelitoCausaId = catDelitoCausaId;
		this.casoDTO = casoDTO;
		this.folioDelitoPersona = folioDelitoPersona;
		this.catDelitoEstadisticaId = catDelitoEstadisticaId;
		this.catDelitoTipoId = catDelitoTipoId;
		this.catDelitoCalificacionId = catDelitoCalificacionId;
		this.catDelitoConcursoId = catDelitoConcursoId;
		this.catDelitoOrdenResId = catDelitoOrdenResId;
	}
	public DelitoPersonaDTO() {
	}
	/**
	 * Método de acceso al campo delitoPersonaId.
	 * @return El valor del campo delitoPersonaId
	 */
	public Long getDelitoPersonaId() {
		return delitoPersonaId;
	}
	/**
	 * Asigna el valor al campo delitoPersonaId.
	 * @param delitoPersonaId el valor delitoPersonaId a asignar
	 */
	public void setDelitoPersonaId(Long delitoPersonaId) {
		this.delitoPersonaId = delitoPersonaId;
	}
	/**
	 * Método de acceso al campo bienTutelado.
	 * @return El valor del campo bienTutelado
	 */
	public ValorDTO getBienTutelado() {
		return bienTutelado;
	}
	/**
	 * Asigna el valor al campo bienTutelado.
	 * @param bienTutelado el valor bienTutelado a asignar
	 */
	public void setBienTutelado(ValorDTO bienTutelado) {
		this.bienTutelado = bienTutelado;
	}
	/**
	 * Método de acceso al campo formaParticipacion.
	 * @return El valor del campo formaParticipacion
	 */
	public ValorDTO getFormaParticipacion() {
		return formaParticipacion;
	}
	/**
	 * Asigna el valor al campo formaParticipacion.
	 * @param formaParticipacion el valor formaParticipacion a asignar
	 */
	public void setFormaParticipacion(ValorDTO formaParticipacion) {
		this.formaParticipacion = formaParticipacion;
	}
	/**
	 * Método de acceso al campo delito.
	 * @return El valor del campo delito
	 */
	public DelitoDTO getDelito() {
		return delito;
	}
	/**
	 * Asigna el valor al campo delito.
	 * @param delito el valor delito a asignar
	 */
	public void setDelito(DelitoDTO delito) {
		this.delito = delito;
	}
	/**
	 * Método de acceso al campo victima.
	 * @return El valor del campo victima
	 */
	public InvolucradoDTO getVictima() {
		return victima;
	}
	/**
	 * Asigna el valor al campo victima.
	 * @param victima el valor victima a asignar
	 */
	public void setVictima(InvolucradoDTO victima) {
		this.victima = victima;
	}
	/**
	 * Método de acceso al campo probableResponsable.
	 * @return El valor del campo probableResponsable
	 */
	public InvolucradoDTO getProbableResponsable() {
		return probableResponsable;
	}
	/**
	 * Asigna el valor al campo probableResponsable.
	 * @param probableResponsable el valor probableResponsable a asignar
	 */
	public void setProbableResponsable(InvolucradoDTO probableResponsable) {
		this.probableResponsable = probableResponsable;
	}
	/**
	 * Método de acceso al campo esActivo.
	 * @return El valor del campo esActivo
	 */
	public Boolean getEsActivo() {
		return esActivo;
	}
	/**
	 * Asigna el valor al campo esActivo.
	 * @param esActivo el valor esActivo a asignar
	 */
	public void setEsActivo(Boolean esActivo) {
		this.esActivo = esActivo;
	}

	public Long getCatDelitoClasificacionId() {
		return catDelitoClasificacionId;
}
	public void setCatDelitoClasificacionId(Long catDelitoClasificacionId) {
		this.catDelitoClasificacionId = catDelitoClasificacionId;
	}
	public Long getCatDelitoLugarId() {
		return catDelitoLugarId;
	}
	public void setCatDelitoLugarId(Long catDelitoLugarId) {
		this.catDelitoLugarId = catDelitoLugarId;
	}
	public Long getCatDelitoModalidadId() {
		return catDelitoModalidadId;
	}
	public void setCatDelitoModalidadId(Long catDelitoModalidadId) {
		this.catDelitoModalidadId = catDelitoModalidadId;
	}
	public Long getCatDelitoModusId() {
		return catDelitoModusId;
	}
	public void setCatDelitoModusId(Long catDelitoModusId) {
		this.catDelitoModusId = catDelitoModusId;
	}
	public Long getCatDelitoCausaId() {
		return catDelitoCausaId;
	}
	public void setCatDelitoCausaId(Long catDelitoCausaId) {
		this.catDelitoCausaId = catDelitoCausaId;
	}
	/**
	 * @return the casoDTO
	 */
	public CasoDTO getCasoDTO() {
		return casoDTO;
	}
	/**
	 * @param casoDTO the casoDTO to set
	 */
	public void setCasoDTO(CasoDTO casoDTO) {
		this.casoDTO = casoDTO;
	}
	/**
	 * @return the folioDelitoPersona
	 */
	public String getFolioDelitoPersona() {
		return folioDelitoPersona;
	}
	/**
	 * @param folioDelitoPersona the folioDelitoPersona to set
	 */
	public void setFolioDelitoPersona(String folioDelitoPersona) {
		this.folioDelitoPersona = folioDelitoPersona;
	}
	public Long getCatDelitoEstadisticaId() {
		return catDelitoEstadisticaId;
	}
	public void setCatDelitoEstadisticaId(Long catDelitoEstadisticaId) {
		this.catDelitoEstadisticaId = catDelitoEstadisticaId;
	}
	public Long getCatDelitoTipoId() {
		return catDelitoTipoId;
	}
	public void setCatDelitoTipoId(Long catDelitoTipoId) {
		this.catDelitoTipoId = catDelitoTipoId;
	}
	public Long getCatDelitoCalificacionId() {
		return catDelitoCalificacionId;
	}
	public void setCatDelitoCalificacionId(Long catDelitoCalificacionId) {
		this.catDelitoCalificacionId = catDelitoCalificacionId;
	}
	public Long getCatDelitoConcursoId() {
		return catDelitoConcursoId;
	}
	public void setCatDelitoConcursoId(Long catDelitoConcursoId) {
		this.catDelitoConcursoId = catDelitoConcursoId;
	}
	public Long getCatDelitoOrdenResId() {
		return catDelitoOrdenResId;
	}
	public void setCatDelitoOrdenResId(Long catDelitoOrdenResId) {
		this.catDelitoOrdenResId = catDelitoOrdenResId;
	}
	
}
