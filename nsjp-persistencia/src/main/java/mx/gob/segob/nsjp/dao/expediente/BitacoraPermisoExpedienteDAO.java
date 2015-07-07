/**
* Nombre del Programa : BitacoraPermisoExpedienteDAO.java
* Autor                            : rgama
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 16 Ene 2013
* Marca de cambio        : N/A
* Descripcion General    : Describir el objetivo de la clase de manera breve
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
package mx.gob.segob.nsjp.dao.expediente;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.model.BitacoraPermisoExpediente;

/**
 * @author rgama
 *
 */
public interface BitacoraPermisoExpedienteDAO extends
		GenericDao<BitacoraPermisoExpediente, Long> {

	/**
	 * Permite obtener el historial de los permisos de un expediente
	 * @param numExpId
	 * @return
	 */
	public List<BitacoraPermisoExpediente> obtenerPermisosDeNumeroExpediente(Long numExpId);

	
	/**
	 * Permite dejar los registros como inactivos en la bitacora para el funcionario y expediente asociados
	 * @param claveFuncionario
	 * @param numExpId
	 * @return
	 * @throws NSJPNegocioException
	 */
	public boolean actualizarRegistrosActivosEnBitacora(Long claveFuncionario,Long numExpId) throws NSJPNegocioException;

}
