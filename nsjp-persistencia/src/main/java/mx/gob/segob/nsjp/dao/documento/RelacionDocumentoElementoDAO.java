/**
* Nombre del Programa : RelacionDocumentoElementoDAO.java
* Autor                            : vaguirre
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 13 Jul 2011
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
package mx.gob.segob.nsjp.dao.documento;

import java.util.List;

import org.hibernate.Query;

import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.model.Elemento;
import mx.gob.segob.nsjp.model.RelacionDocumentoElemento;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author vaguirre
 *
 */
public interface RelacionDocumentoElementoDAO extends GenericDao<RelacionDocumentoElemento, Long> {
    /**
     * 
     * @param documentoId
     * @return
     */
    List<Elemento> consultarElementosPorDocId(Long documentoId);
    
	public void eliminaRelacionDocumentoElementoPorDocId(Long documentoId);

}
