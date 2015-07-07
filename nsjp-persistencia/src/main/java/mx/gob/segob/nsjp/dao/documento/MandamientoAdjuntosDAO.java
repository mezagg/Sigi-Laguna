/**
* Nombre del Programa : MandamientoAdjuntosDAO.java
* Autor                            : vaguirre
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 1 Sep 2011
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

import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.model.Documento;
import mx.gob.segob.nsjp.model.MandamientoAdjuntos;
import mx.gob.segob.nsjp.model.MandamientoAdjuntosId;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author vaguirre
 *
 */


public interface MandamientoAdjuntosDAO extends GenericDao<MandamientoAdjuntos, MandamientoAdjuntosId> {
	
	/**
	 * Consulta los documentos asociados a un mandamiento, por el filtro
	 * mandamientoId
	 * 
	 * @param mandamientoId
	 * @return
	 */
	List<Documento> consultarDocumentoMandamientoAdjuntoPorMandamientoId(Long mandamientoId);

}
