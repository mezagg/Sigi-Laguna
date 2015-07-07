package mx.gob.segob.nsjp.dao.documento;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.model.RelacionSolicitudDocumentoFuncionario;
import mx.gob.segob.nsjp.model.Solicitud;

/**
 * 
 * @author JoseFP
 *
 */
public interface RelacionSolicitudDocumentoFuncionarioDAO extends GenericDao<RelacionSolicitudDocumentoFuncionario, Long> {
	
	/**
	 * 
	 * @param solicitud
	 * @return
	 * @throws NSJPNegocioException
	 */
	public List<RelacionSolicitudDocumentoFuncionario> consultarRelacionesPorSolicitud(Solicitud solicitud) throws NSJPNegocioException;
	
	/**
	 * 
	 * @param idSolicitud
	 * @param idFuncionarioAnterior
	 * @param idFuncionarioNuevo
	 * @throws NSJPNegocioException
	 */
	public void actualizaFuncionarioDeDocumentosSegunRelacion(Long idSolicitud,
			Long idFuncionarioAnterior, Long idFuncionarioNuevo)
			throws NSJPNegocioException;
	
	
	/**
	 * 
	 * @param idSolicitud
	 * @param idFuncionarioAnterior
	 * @param idFuncionarioNuevo
	 * @throws NSJPNegocioException
	 */
	public void actualizaFuncionarioDeRelacion(Long idSolicitud,
			Long idFuncionarioAnterior, Long idFuncionarioNuevo)
			throws NSJPNegocioException;
	/**
	 * 
	 * @param idSolicitud
	 * @param idFuncionarioAnterior
	 * @param idFuncionarioNuevo
	 * @throws NSJPNegocioException
	 */
	public void actualizaFuncionarioDeActividadSegunRelacion(Long idSolicitud,
			Long idFuncionarioAnterior, Long idFuncionarioNuevo)
			throws NSJPNegocioException;	
	

}
