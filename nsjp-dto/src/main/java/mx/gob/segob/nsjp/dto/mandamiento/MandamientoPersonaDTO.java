/**
 * Nombre del Programa 		: MandamientoPersonaDTO.java
 * Autor 					: AlejandroGA
 * Compania 				: Ultrasist
 * Proyecto 				: nsjp-modelo 					Fecha: 07/06/2013
 * Marca de cambio 			: N/A
 * Descripcion General 		: Clase DTO para MandamientoPersona
 * Programa Dependiente 	: N/A
 * Programa Subsecuente 	: N/A
 * Cond. de ejecucion 		: N/A
 * Dias de ejecucion 		: N/A 							Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor 					: N/A
 * Compania 				: N/A
 * Proyecto 				: N/A 							Fecha: N/A
 * Modificacion 			: N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.dto.mandamiento;

import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.dto.catalogo.CatalogoDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.documento.MandamientoDTO;
import mx.gob.segob.nsjp.dto.persona.PersonaDTO;

/**
 * @author AlejandroGA
 * @version 1.0
 */
public class MandamientoPersonaDTO {

	
	//Atributos
	private Long mandamientoPersonaId;
	private ValorDTO estatus;
	private Date fechaEjecucion;
	private String strFechaEjecucion;
	private String strHoraEjecucion;
	private String folioInterInstitucional;
	private PersonaDTO persona;
	private MandamientoDTO mandamiento;
	private List <MandamientoPersonaDocumentoDTO> mandamientosPersonaDocumento;
	//Atributos para vista
	private MandamientoPersonaDocumentoDTO documentoEstatusActual;
	private List<CatalogoDTO> estatusTransicion;
	
	public MandamientoPersonaDTO() {
		
	}


	/**
	 * M&eacute;todo de acceso al campo mandamientoPersonaId.
	 * @return El valor del campo mandamientoPersonaId
	 */
	public Long getMandamientoPersonaId() {
		return mandamientoPersonaId;
	}


	/**
	 * Asigna el valor al campo mandamientoPersonaId.
	 * @param mandamientoPersonaId a asignar
	 */
	public void setMandamientoPersonaId(Long mandamientoPersonaId) {
		this.mandamientoPersonaId = mandamientoPersonaId;
	}


	/**
	 * M&eacute;todo de acceso al campo estatus.
	 * @return El valor del campo estatus
	 */
	public ValorDTO getEstatus() {
		return estatus;
	}


	/**
	 * Asigna el valor al campo estatus.
	 * @param estatus a asignar
	 */
	public void setEstatus(ValorDTO estatus) {
		this.estatus = estatus;
	}


	/**
	 * M&eacute;todo de acceso al campo fechaEjecucion.
	 * @return El valor del campo fechaEjecucion
	 */
	public Date getFechaEjecucion() {
		return fechaEjecucion;
	}


	/**
	 * Asigna el valor al campo fechaEjecucion.
	 * @param fechaEjecucion a asignar
	 */
	public void setFechaEjecucion(Date fechaEjecucion) {
		this.strHoraEjecucion = DateUtils.formatearHoraAm(fechaEjecucion);
		this.strFechaEjecucion = DateUtils.formatear(fechaEjecucion);
		this.fechaEjecucion = fechaEjecucion;
	}


	/**
	 * M&eacute;todo de acceso al campo strFechaEjecucion.
	 * @return El valor del campo strFechaEjecucion
	 */
	public String getStrFechaEjecucion() {
		return strFechaEjecucion;
	}


	/**
	 * Asigna el valor al campo strFechaEjecucion.
	 * @param strFechaEjecucion a asignar
	 */
	public void setStrFechaEjecucion(String strFechaEjecucion) {
		this.strFechaEjecucion = strFechaEjecucion;
	}


	/**
	 * M&eacute;todo de acceso al campo strHoraEjecucion.
	 * @return El valor del campo strHoraEjecucion
	 */
	public String getStrHoraEjecucion() {
		return strHoraEjecucion;
	}


	/**
	 * Asigna el valor al campo strHoraEjecucion.
	 * @param strHoraEjecucion a asignar
	 */
	public void setStrHoraEjecucion(String strHoraEjecucion) {
		this.strHoraEjecucion = strHoraEjecucion;
	}


	/**
	 * M&eacute;todo de acceso al campo folioInterInstitucional.
	 * @return El valor del campo folioInterInstitucional
	 */
	public String getFolioInterInstitucional() {
		return folioInterInstitucional;
	}


	/**
	 * Asigna el valor al campo folioInterInstitucional.
	 * @param folioInterInstitucional a asignar
	 */
	public void setFolioInterInstitucional(String folioInterInstitucional) {
		this.folioInterInstitucional = folioInterInstitucional;
	}
	
	/**
	 * M&eacute;todo de acceso al campo persona.
	 * @return El valor del campo persona
	 */
	public PersonaDTO getPersona() {
		return persona;
	}

	/**
	 * Asigna el valor al campo persona.
	 * @param persona a asignar
	 */
	public void setPersona(PersonaDTO persona) {
		this.persona = persona;
	}

	/**
	 * M&eacute;todo de acceso al campo mandamiento.
	 * @return El valor del campo mandamiento
	 */
	public MandamientoDTO getMandamiento() {
		return mandamiento;
	}


	/**
	 * Asigna el valor al campo mandamiento.
	 * @param mandamiento a asignar
	 */
	public void setMandamiento(MandamientoDTO mandamiento) {
		this.mandamiento = mandamiento;
	}


	/**
	 * M&eacute;todo de acceso al campo mandamientosPersonaDocumento.
	 * @return El valor del campo mandamientosPersonaDocumento
	 */
	public List<MandamientoPersonaDocumentoDTO> getMandamientosPersonaDocumento() {
		return mandamientosPersonaDocumento;
	}


	/**
	 * Asigna el valor al campo mandamientosPersonaDocumento.
	 * @param mandamientosPersonaDocumento a asignar
	 */
	public void setMandamientosPersonaDocumento(
			List<MandamientoPersonaDocumentoDTO> mandamientosPersonaDocumento) {
		this.mandamientosPersonaDocumento = mandamientosPersonaDocumento;
	}


	/**
	 * M&eacute;todo de acceso al campo documentoEstatusActual.
	 * @return El valor del campo documentoEstatusActual
	 */
	public MandamientoPersonaDocumentoDTO getDocumentoEstatusActual() {
		return documentoEstatusActual;
	}


	/**
	 * Asigna el valor al campo documentoEstatusActual.
	 * @param documentoEstatusActual a asignar
	 */
	public void setDocumentoEstatusActual(
			MandamientoPersonaDocumentoDTO documentoEstatusActual) {
		this.documentoEstatusActual = documentoEstatusActual;
	}


	/**
	 * M&eacute;todo de acceso al campo estatusTransicion.
	 * @return El valor del campo estatusTransicion
	 */
	public List<CatalogoDTO> getEstatusTransicion() {
		return estatusTransicion;
	}


	/**
	 * Asigna el valor al campo estatusTransicion.
	 * @param estatusTransicion a asignar
	 */
	public void setEstatusTransicion(List<CatalogoDTO> estatusTransicion) {
		this.estatusTransicion = estatusTransicion;
	}

}
