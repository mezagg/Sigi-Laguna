package mx.gob.segob.nsjp.dao.solicitud;

import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.model.SolicitudTranscripcionAudiencia;

public interface SolicitudTranscricpionAudienciaDAO extends GenericDao<SolicitudTranscripcionAudiencia, Long>{

	List<SolicitudTranscripcionAudiencia> consultarSolicitudesTrascripcionAudienciaPendientes();
	List<SolicitudTranscripcionAudiencia> consultarSolicitudTranscripcionAudienciaPorTipoYEstatus(Long idAudiencia, Long tipoId,List<Long> estatusId);
	List<SolicitudTranscripcionAudiencia> consultarSolicitudMaster(Long idAudiencia, Long idTipo);
	/**
	 * Consulta las solicitudes de transcripci�n de audiencia que est�n en cierto estatus y que sean de cierto tipo
	 * @param tipoId Tipo de la solicitud de transcripci�n (transcripci�n o A/V)
	 * @param estatusId Estatus de la solicitud de transcripci�n
	 * @param fechaIni  Fecha inicial, de creaci�n de la solicitud.
	 * @param fechaFin  Fecha final, de creaci�n del a solicitud.
	 * @author Emigdio - GBP
	 * @return Lista de solicitudes
	 */
	List<SolicitudTranscripcionAudiencia> consultarSolicitudTranscripcionAudienciaPorTipoYEstatus(Long tipoId,Long estatusId,Long dif,Long discriminanteId,Date fechaIni, Date fechaFin, Funcionario funcionario);

	
}
