/**
* Nombre del Programa : RegionDAO.java
* Autor                            : asanchez
* Compania                    : Lucasian Labs
* Proyecto                      : NSJP                    Fecha: 11 Aug 2015
* Marca de cambio        : N/A
* Descripcion General    : Objeto de accedo a datos para la tabla de region.
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
package mx.gob.segob.nsjp.dao.catalogo;

import java.util.List;

import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.model.Region;

/**
 * Objeto de accedo a datos para la tabla de entidad federativa.
 * @version 1.0
 * @author vaguirre
 *
 */
public interface RegionDAO extends GenericDao<Region, Long> {
    /**
     * Recupera todos los registros de la entidad EntidadFederativa
     * @return
     */
	List<Region> consultarTodos();
    
}
