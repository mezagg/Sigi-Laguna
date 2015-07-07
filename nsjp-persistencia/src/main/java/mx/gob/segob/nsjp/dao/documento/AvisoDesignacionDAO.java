package mx.gob.segob.nsjp.dao.documento;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.model.AvisoDesignacion;

public interface AvisoDesignacionDAO extends GenericDao<AvisoDesignacion, Long> {

	/**
	 * Consulta los avisos de Designacion por:
	 * - Estatus del Aviso de Designacion (Notificacion)
	 * - Clave de funcionario
	 * - Tipo de Solicitud
	 * - Distrito
	 * 
	 * @param estadoNotificacionId
	 * @param claveFuncionario
	 * @param tipoSolicitudId
	 * @param distritoId
	 * @return
	 * @throws NSJPNegocioException
	 */
	//MOD CoordinadorDef, Defensor
	List<AvisoDesignacion> consultarAvisosDesignacion(Long estadoNotificacionId,
			Long claveFuncionario, Long tipoSolicitudId, Long distritoId)
			throws NSJPNegocioException;

	/**
	 * Consulta los avisos de designacion para un expediente específico
	 * @param idExpediente
	 * @return
	 * @throws NSJPNegocioException
	 */
	public List<AvisoDesignacion> consultarAvisosDesignacionPorIdExpediente(
			Long idExpediente) throws NSJPNegocioException;
	
	/**
	 * Consulta el aviso de designacion por el id de la solicitud de defensor
	 * @param solicitudId
	 * @return
	 * @throws NSJPNegocioException
	 */
	public AvisoDesignacion consultarAvisoDesignacionPorSolicitudDeDefensor(
			Long solicitudId) throws NSJPNegocioException;
	
}
