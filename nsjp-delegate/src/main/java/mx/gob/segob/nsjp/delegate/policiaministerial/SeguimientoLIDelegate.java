/**
 * 
 */
package mx.gob.segob.nsjp.delegate.policiaministerial;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.policiaministerial.SeguimientoLIDTO;

/**
 * @author adrian
 *
 */
public interface SeguimientoLIDelegate {
	
	/**
	 * Operaci�n que permite guardar / actualizar un seguimientoLI
	 * guardar: Id = 0 o NULL
	 * @param seguimientoLIDTO
	 * @return
	 * @throws NSJPNegocioException
	 */
	public Long guardarSeguimientoLI(SeguimientoLIDTO seguimientoLIDTO)throws NSJPNegocioException;
	
	/**
	 * Operaci�n que permite consultar un seguimiento por su ID
	 * @param seguimientoLIDTO: ID
	 * @return
	 * @throws NSJPNegocioException
	 */
	public SeguimientoLIDTO consultarSeguimientoLI(SeguimientoLIDTO seguimientoLIDTO)throws NSJPNegocioException;
	
	/**
	 * Operaci�n que permite consultar los seguimientos que pertenezcan a un numero de expediente
	 * @param expedienteDTO: NumeroExpedienteID / NumeroExpediente
	 * @return
	 * @throws NSJPNegocioException
	 */
	public List<SeguimientoLIDTO> consultarSeguimientosLIXExpedienteId(ExpedienteDTO expedienteDTO)throws NSJPNegocioException;

}
