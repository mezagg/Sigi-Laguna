/**
 * Nombre del Programa : InformePolicialHomologadoWSDTO.java
 * Autor                            : GustavoBP                                               
 * Compania                    : Ultrasist                                                
 * Proyecto                      : NSJP                    Fecha: 06/09/2011 
 * Marca de cambio        : N/A                                                     
 * Descripcion General    : DTO de intercambio entre sistemas para transportar los datos b�sicos de un IPH.  
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

package mx.gob.segob.nsjp.dto.informepolicial;

import java.util.Date;

import mx.gob.segob.nsjp.dto.base.GenericWSDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteWSDTO;


/**
 * DTO de intercambio entre sistemas para transportar los datos b�sicos de un IPH.
 *  
 * @version 1.0
 * @author GustavoBP
 *
 */
public class InformePolicialHomologadoWSDTO extends GenericWSDTO{

    /**
	 * 
	 */
	private static final long serialVersionUID = 619216717610941311L;
	private Long informePolicialHomologadoId;
    private ExpedienteWSDTO expediente;
    private Long tipoParticipacion; //ValorDTO
    private Date fechaCaptura;
    private String objetivosGenerales;
    private Long numeroOficio;
    private Long anio;
    private Long folioIPH;
    private String claveInterInstitucionalDelito;
	/**
	 * 
	 */
	public InformePolicialHomologadoWSDTO() {
	}
	/**
	 * M�todo de acceso al campo informePolicialHomologadoId.
	 * @return El valor del campo informePolicialHomologadoId
	 */
	public Long getInformePolicialHomologadoId() {
		return informePolicialHomologadoId;
	}
	/**
	 * Asigna el valor al campo informePolicialHomologadoId.
	 * @param informePolicialHomologadoId el valor informePolicialHomologadoId a asignar
	 */
	public void setInformePolicialHomologadoId(Long informePolicialHomologadoId) {
		this.informePolicialHomologadoId = informePolicialHomologadoId;
	}
	/**
	 * M�todo de acceso al campo expediente.
	 * @return El valor del campo expediente
	 */
	public ExpedienteWSDTO getExpediente() {
		return expediente;
	}
	/**
	 * Asigna el valor al campo expediente.
	 * @param expediente el valor expediente a asignar
	 */
	public void setExpediente(ExpedienteWSDTO expediente) {
		this.expediente = expediente;
	}
	/**
	 * M�todo de acceso al campo tipoParticipacion.
	 * @return El valor del campo tipoParticipacion
	 */
	public Long getTipoParticipacion() {
		return tipoParticipacion;
	}
	/**
	 * Asigna el valor al campo tipoParticipacion.
	 * @param tipoParticipacion el valor tipoParticipacion a asignar
	 */
	public void setTipoParticipacion(Long tipoParticipacion) {
		this.tipoParticipacion = tipoParticipacion;
	}
	/**
	 * M�todo de acceso al campo fechaCaptura.
	 * @return El valor del campo fechaCaptura
	 */
	public Date getFechaCaptura() {
		return fechaCaptura;
	}
	/**
	 * Asigna el valor al campo fechaCaptura.
	 * @param fechaCaptura el valor fechaCaptura a asignar
	 */
	public void setFechaCaptura(Date fechaCaptura) {
		this.fechaCaptura = fechaCaptura;
	}
	/**
	 * M�todo de acceso al campo objetivosGenerales.
	 * @return El valor del campo objetivosGenerales
	 */
	public String getObjetivosGenerales() {
		return objetivosGenerales;
	}
	/**
	 * Asigna el valor al campo objetivosGenerales.
	 * @param objetivosGenerales el valor objetivosGenerales a asignar
	 */
	public void setObjetivosGenerales(String objetivosGenerales) {
		this.objetivosGenerales = objetivosGenerales;
	}
	/**
	 * M�todo de acceso al campo numeroOficio.
	 * @return El valor del campo numeroOficio
	 */
	public Long getNumeroOficio() {
		return numeroOficio;
	}
	/**
	 * Asigna el valor al campo numeroOficio.
	 * @param numeroOficio el valor numeroOficio a asignar
	 */
	public void setNumeroOficio(Long numeroOficio) {
		this.numeroOficio = numeroOficio;
	}
	/**
	 * M�todo de acceso al campo anio.
	 * @return El valor del campo anio
	 */
	public Long getAnio() {
		return anio;
	}
	/**
	 * Asigna el valor al campo anio.
	 * @param anio el valor anio a asignar
	 */
	public void setAnio(Long anio) {
		this.anio = anio;
	}
	/**
	 * M�todo de acceso al campo folioIPH.
	 * @return El valor del campo folioIPH
	 */
	public Long getFolioIPH() {
		return folioIPH;
	}
	/**
	 * Asigna el valor al campo folioIPH.
	 * @param folioIPH el valor folioIPH a asignar
	 */
	public void setFolioIPH(Long folioIPH) {
		this.folioIPH = folioIPH;
	}
	/**
	 * @return the claveInterInstitucionalDelito
	 */
	public String getClaveInterInstitucionalDelito() {
		return claveInterInstitucionalDelito;
	}
	/**
	 * @param claveInterInstitucionalDelito the claveInterInstitucionalDelito to set
	 */
	public void setClaveInterInstitucionalDelito(
			String claveInterInstitucionalDelito) {
		this.claveInterInstitucionalDelito = claveInterInstitucionalDelito;
	}
    
}
