/**
 * Nombre del Programa  : GenerarFolioSolicitudService.java
 * Autor                : Daniel Jiménez
 * Compania             : TATTVA-IT
 * Proyecto             : NSJP                    Fecha: 24 Jun 2011
 * Marca de cambio      : N/A
 * Descripcion General  : Genera un nuevo folio de solicitud
 * Programa Dependiente : N/A
 * Programa Subsecuente : N/A
 * Cond. de ejecucion   : N/A
 * Dias de ejecucion    : N/A                             Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                :N/A
 * Compania             :N/A
 * Proyecto             :N/A                                 Fecha: N/A
 * Modificacion         :N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.service.solicitud;

import java.util.Date;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;

public interface GenerarFolioSolicitudService {

	/**
	 * Genera el siguiente folio disponible para asignarlo a una solicitud
	 * @return folio para solicitud
	 * @throws NSJPNegocioException
	 */
	public String generarFolioSolicitud()throws NSJPNegocioException;

	/**
	 * Genera el siguiente folio disponible para asignarlo a una notifiación
	 * @return folio para notificacion
	 * @throws NSJPNegocioException
	 */
	public String generarFolioNotificacion()throws NSJPNegocioException;

	/**
	 * Genera el siguiente folio disponible para asignarlo a un documento con el
	 * siguiente formato: Monograma de la Institucion/AnioConsecutivo
	 * 
	 * Pe:
	 * 
	 * PJ/2013000001
	 * 
	 * El monograma consta de 2 digitos, el Consecutivo 5 digitos
	 * 
	 * @return folio para notificacion
	 * @throws NSJPNegocioException
	 */
	public String generarFoliodDocumento() throws NSJPNegocioException;
	
	/**
	 * Genera el siguiente folio disponible para asignarlo a una audiencia
	 * @return folio para notificacion
	 * @throws NSJPNegocioException
	 */
	String generarFolioAudiencia() throws NSJPNegocioException;
	
	/**
	 * Genera un folio para un mandamiento judicial, respecto a una causa.
	 * Y estar&aacute; conformado de la siguiente manera: 
	 * 
	 * No.Causa|DD-MM-AAA|Abreviac&oacute;n tipo mandamiento|consecutivo 
	 * 
	 * P.e.
	 * 
	 * 		00012/CA/2013-PJ-ZAC-002|30-05-2013|ORE|115
	 * 
	 * el consecutivo estar&aacute; conformado por tres d&iacute;gitos y 
	 * se incrementaran una unidad, cada que se genera un folio para dicha causa.
	 * 
	 * @param tipoMandamiento
	 *            , abreviacion del tipo de mandamiento
	 * @param fechaResolutivo
	 *            , fecha en que se dict&oacute; el resolutivo
	 * @param numeroDeCausa
	 *            , numero de causa para formar el folio.
	 * @return folio del mandamiento judicial
	 * @throws NSJPNegocioException
	 */
	public String generarFolioMandamientoJudicial(Long tipoMandamiento,
			Date fechaResolutivo, String numeroDeCausa)
			throws NSJPNegocioException;
	
	/**
	 * Genera un folio interinstitucional para un mandamiento-persona, el cual
	 * esta conformado de la siguiente manera:
	 * 
	 * siglasInstitucion/MJanio-consecutivo 
	 * 
	 * P.e.
	 * 		PJ/MJ2013-00001 
	 * 
	 * el consecutivo estar&aacute; conformado por cinco d&iacute;gitos y 
	 * se incrementaran por unidad, cada que se genere un folio.
	 * 
	 * 
	 * @return folio del mandamiento-persona
	 * @throws NSJPNegocioException
	 */
	public String generarFolioInterInstitucionalMandamientoPersona() throws NSJPNegocioException;
}
