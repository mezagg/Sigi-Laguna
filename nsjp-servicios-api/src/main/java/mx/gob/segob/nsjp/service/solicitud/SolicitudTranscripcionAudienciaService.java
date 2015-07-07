package mx.gob.segob.nsjp.service.solicitud;

import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudTranscripcionAudienciaDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;

public interface SolicitudTranscripcionAudienciaService {

	AudienciaDTO consultarAudienciaDeSolicitudTranscripcion(SolicitudDTO solicitud) throws NSJPNegocioException;
	List<SolicitudTranscripcionAudienciaDTO> consultarSolicitudTranscripcionAudienciaPorTipoYEstatus(Long idAudiencia, Long tipoId, List<Long> estatusId) throws NSJPNegocioException;
	List<SolicitudTranscripcionAudienciaDTO> consultarSolicitudMaster(
			Long idAudiencia, Long idTipo) throws NSJPNegocioException; 
	
	/**
	 * Consulta las solicitudes de transcripci�n de audiencia que est�n en cierto estatus y que sean de cierto tipo
	 * @param tipoId Tipo de la solicitud de transcripci�n (transcripci�n o A/V)
	 * @param estatusId Estatus de la solicitud de transcripci�n
	 * @param fechaIni  Fecha inicial, de creaci�n de la solicitud.
	 * @param fechaFin  Fecha final, de creaci�n del a solicitud.
	 * @author Emigdio - GBP
	 * @author Emigdio Hern�ndez
	 * @return Lista de solicitudes
	 */

	List<SolicitudTranscripcionAudienciaDTO> consultarSolicitudTranscripcionAudienciaPorTipoYEstatus(Long tipoId,Long estatusId, UsuarioDTO usuario, Date fechaIni, Date fechaFin);
	/**
	 * Consulta el detalle de una solicitud de transcripci�n de audiencia en base a su 
	 * llave primaria
	 * 
	 * @param solicitudId Identificador de la solicitud a buscar
	 * @return Solicitud encontrada, null en caso de no encontrarla
	 */
	SolicitudTranscripcionAudienciaDTO consultarDetalleSolicitudTranscripcion(Long solicitudId);
	/**
	 * Env�a v�a servicio web la actualizaci�n del estatus de una solicitud de transcripci�n de audiencia
	 * junto con el archivo digital generado para esta
	 * @param solicitudId ID de la solicitud a enviar
	 * @author Emigdio Hern�ndez
	 */
	void enviarActualizacionSolicitudTranscripcionAudiencia(Long solicitudId) throws NSJPNegocioException;
}
