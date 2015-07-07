/**
* Nombre del Programa : SolicitudDefensorDAO.java
* Autor                            : cesarAgustin
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 13 May 2011
* Marca de cambio        : N/A
* Descripcion General    : Contrato de metodos de acceso a datos para la entidad SolicitudDefensor
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
package mx.gob.segob.nsjp.dao.solicitud;

import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.model.Audiencia;
import mx.gob.segob.nsjp.model.SolicitudDefensor;

/**
 * Contrato de metodos de acceso a datos para la entidad SolicitudDefensor.
 * @version 1.0
 * @author cesarAgustin
 *
 */
public interface SolicitudDefensorDAO extends
		GenericDao<SolicitudDefensor, Long> {
	
	/**
	 * Generar el ultimo numero para el folio de una solicitud
	 * @return Long
	 */
    Long obtenerUltimoNumero();
    
    
    /**
     * Obtener la lista de las solicitudesd de defensoria con estatus no atendida
     * @return List<SolicitudDefensor>
     */
    List<SolicitudDefensor> obtenerSolicitudesDefensoriaPorEstatus(
    					Long esatus,Instituciones institucion);

    /**
	 * Consulta las solicitudes defensoria que fueron asignadas a un funcionario que es enviado como parametro.
	 * @author cesarAgustin	
	 * @param usuarioDTO
	 * @return Lista de solicitud asignadas al funcionario
	 * @throws NSJPNegocioException
	 */
	List<SolicitudDefensor> consultarSolDefensorAsignadas(Long idUsuario);

	/**
	 * 
	 * @param time
	 * @param estatusSolicitud
	 * @return
	 */
	List<SolicitudDefensor> consultarSolicitudesDefensoriaByHistoricoYEstatus(
			Date fechaHistorico, Long estatusSolicitud);

	/**
	 * 
	 * @param expedienteId
	 * @param claveFuncionario
	 * @return
	 */
	SolicitudDefensor obtenerSolDfensorByExpedienteYFuncionario(
			Long expedienteId, Long claveFuncionario);

	/**
	 * Obtiene una solicitud de defensor asociada a Expediente por numero de expediente id
	 * @param numeroExpedienteId
	 * @return
	 */
	SolicitudDefensor consultarSolicituDefensorPorNumeroExpedienteId(
			Long numeroExpedienteId);


    /**
     * Obtener la lista de las solicitudesd de defensoria con estatus no atendida
     * @return List<SolicitudDefensor>
     */
    List<SolicitudDefensor> obtenerSolicitudesAsesoriaDefensoriaPorEstatus(
    					Long esatus,Instituciones institucion)throws NSJPNegocioException;


	SolicitudDefensor obtenerSolicitudByIdyYipo(Long documentoId, Long idCampo);
	
	
	/**
	 * M&eacute;todo que consulta los imputados que tengan solicitud de defensor
	 * y, opcionalmente, pertenezcan a una audiencia en particular.
	 * 
	 * @param audiencia
	 *            - La audiencia a partir de la cual se van a consultar las
	 *            solicitudes de defensor.
	 * @param imputadosId
	 *            - Lista de imputados que se consultara, para saber si ya
	 *            cuentan con almenos una solicitud.
	 * @return List<Long> - Lista de Id de los involucrados que ya cuentan con
	 *         solicitudes de defensor
	 */
	public List<Long> obtenerInvolucradosIdConSolicitudDefensor(
			Audiencia audiencia, List<Long> imputadosId);
	
	/**
	 * Consulta las solicitudes por filtro
	 * @param numeroCaso
	 * @param numeroExpediente
	 * @param tieneNumeroExpediente
	 * @param tieneAvisoDesignacion
	 * @return Lista de solicitudes de defensor
	 * @throws NSJPNegocioException
	 */
	public List<SolicitudDefensor> consultarSolDeDefensorPorNumeroExpediente(
			String numeroCaso, String numeroExpediente,
			Boolean tieneNumeroExpediente, Boolean tieneAvisoDesignacion)
			throws NSJPNegocioException;

}
