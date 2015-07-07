/**
* Nombre del Programa : RecibirSolicitudCiudadanaDefensoriaService.java
* Autor                            : cesarAgustin
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 17 May 2011
* Marca de cambio        : N/A
* Descripcion General    : Contrato para el servicio Recibir una solicitud de defensoria por un ciudadano
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
package mx.gob.segob.nsjp.service.solicitud;

import mx.gob.segob.nsjp.comun.enums.expediente.EtapasExpediente;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDefensorDTO;

/**
 * Contrato para el servicio Recibir una solicitud de defensoria por un ciudadano.
 * @version 1.0
 * @author cesarAgustin
 *
 */
public interface RecibirSolicitudCiudadanaDefensoriaService {
	
	/**
	 * Servicio que se encarga de crear:
	 * 1) Expediente con:
	 * 		-Etapa:		Asesoria
	 * 		-Area:		Atenci&oacuete;n temprana penal
	 * 		-Estatus:	Abierto
	 * 		-Caso: 		null
	 * 		-CatEtapa	null
	 * 
	 * 2) Solicitud con:
	 * 		-Estaus:			Abierta
	 * 		-Folio:				Generado
	 * 		-Tipo Solicitud:	ASESORIA_DEFENSORIA
	 * 		-Forma:				SOLICITUD_DE_ASESORIA_LEGAL
	 * 		-Tipo Documento: 	SOLICITUD_DE_ASESORIA_LEGAL
	 * 
	 * Para el registro de una asesoria legal
	 *  
	 * Queda documentado el c&oacute;digo para generar una actividad y asociarse con el
	 * Expediente
	 * 
	 * @param expedienteDTO 	Con los datos de: 
	 * 							-Usuario.
	 * 							-Funcionario
	 * 							-Clave Funcionario
	 * 							-Clave del Distrito (del Discriminate)
	 *  
	 * @param expedienteDTO
	 * @return SolicitudDefensorDTO		Con los datos de: 
	 * 							-Expediente Id
	 * 							-NumeroExpedienteId 
	 * 							-Numero Expediente
	 * 							-Solicitud
	 * @throws NSJPNegocioException
	 */
	//MOD defensorATE
	SolicitudDefensorDTO registrarSolicitudAsesoriaLegal(ExpedienteDTO expedienteDTO)
			throws NSJPNegocioException;
	
	/**
	 * Actualiza la etapa del expediente asociado a una solicitud de defensoria
	 * @param solicitudDefensorDTO
	 * @throws NSJPNegocioException
	 */
	
	void actualizarEtapaExpedienteSolicitudDefensoria(
			SolicitudDefensorDTO solicitudDefensorDTO,EtapasExpediente etapas )throws NSJPNegocioException;
	
	/**
	 * Actualiza el estatus de la solocitudd e defensoria para turnarla al coordinador
	 * @param solicitudDefensorDTO
	 * @throws NSJPNegocioException
	 */
	
	void actualizaEstatusSolicitudDefensoria(SolicitudDefensorDTO solicitudDefensorDTO)throws NSJPNegocioException;

	/**
	 * 
	 * @param expedienteDTO
	 * @return SolicitudDefensorDTO
	 * @throws NSJPNegocioException
	 */
	@Deprecated //ver  generarSolicitudAsesoriaLegal
	SolicitudDefensorDTO generarAcuseAtencion(ExpedienteDTO expedienteDTO)throws NSJPNegocioException;
	
	/**
	 * 
	 * @param solicitudDefensorDTO
	 * @return SolicitudDefensorDTO
	 * @throws NSJPNegocioException
	 */
	void guardarMotivoSolicitudDefensoria(SolicitudDefensorDTO solicitudDefensorDTO) throws NSJPNegocioException;
	
	/**
	 * 
	 * @param solicitante
	 * @return InvolucradoDTO
	 * @throws NSJPNegocioException
	 */
	InvolucradoDTO guardarSolicitanteSolicitudDefensoria(InvolucradoDTO solicitante
			 )throws NSJPNegocioException;
	
	/**
	 * Registra la solicitud de asesoria, crea un nuevo expediente,una actividad
	 * y la liga a la solicitud creada Registra el solicitante y se asocia al
	 * expediente.
	 * 
	 * @param solicitudDefensorDTO
	 * @return
	 * @throws NSJPNegocioException
	 */
	// INFO DEF - defensorATE
	SolicitudDefensorDTO actualizarSolicitudAsesoriaLegal(
			SolicitudDefensorDTO solicitudDefensorDTO)
			throws NSJPNegocioException;

	/**
	 * 
	 * @param imputado
	 * @return InvolucradoDTO
	 * @throws NSJPNegocioException
	 */
	InvolucradoDTO guardarDefendidoSolicitudDefensoria(InvolucradoDTO imputado, Long tipoArea, EtapasExpediente etapa) throws NSJPNegocioException;
		
	/**
	 * 
	 * @param imputado
	 * @throws NSJPNegocioException
	 */
	void actualizaImputadoSolicitudDefensoria(InvolucradoDTO imputado) throws NSJPNegocioException;

}
