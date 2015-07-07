/**
* Nombre del Programa : CatTipoTrabajoExternoDAO.java
* Autor                            : AntonioBV
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 26/01/2012
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
package mx.gob.segob.nsjp.dao.programa;

import java.util.List;

import org.springframework.stereotype.Repository;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.model.CatTipoTrabajoExterno;

/**
 * 
 * Acceso a la informaci�n de CatTipoTrabajoExterno
 * 
 * @version 1.0
 * @author AntonioBV
 *
 */
@Repository
public interface CatTipoTrabajoExternoDAO extends GenericDao<CatTipoTrabajoExterno, Long> {
	/**
	 * M&eacute;todo que consulta todos los tipos de trabajo externo
	 * @return Lista de CatTipoTrabajoExterno
	 * @throws NSJPNegocioException
	 */
	public List<CatTipoTrabajoExterno> consultarCatTipoTrabajoExterno()throws NSJPNegocioException;
		
}
