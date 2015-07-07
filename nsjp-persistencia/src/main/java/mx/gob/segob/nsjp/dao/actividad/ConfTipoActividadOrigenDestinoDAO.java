/**
* Nombre del Programa : ConfTipoActividadOrigenDestinoDAO.java
* Autor                            : AntonioBV
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 19/09/2012
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
package mx.gob.segob.nsjp.dao.actividad;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.model.ConfTipoActividadOrigenDestino;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author AntonioBV
 *
 */
public interface ConfTipoActividadOrigenDestinoDAO extends GenericDao<ConfTipoActividadOrigenDestino, Long> {
	
	/**
	 * M&eacute;todo que consulta una <code>ConfTipoActividadOrigenDestino</code> en base a un filtro
	 * @param filtro para la b&uacute;squeda 
	 * @return <code>ConfTipoActividadOrigenDestino</code> que corresponde al filtro
	 */
	public List<ConfTipoActividadOrigenDestino> consultarConfTipoActividadOrigenDestino(ConfTipoActividadOrigenDestino filtro)
	throws NSJPNegocioException;
	
}
