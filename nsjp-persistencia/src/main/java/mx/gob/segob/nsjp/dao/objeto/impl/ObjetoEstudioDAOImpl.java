/**
* Nombre del Programa 		: ObjetoEstudioDAOImpl.java
* Autor 					: EdgarTE
* Compania 					: Ultrasist
* Proyecto 					: NSJP 							Fecha: 11 Sep 2012
* Marca de cambio 			: N/A
* Descripcion General 		: Describir el objetivo de la clase de manera breve
* Programa Dependiente 		: N/A
* Programa Subsecuente 		: N/A
* Cond. de ejecucion 		: N/A
* Dias de ejecucion 		: N/A 							Horario: N/A
*                              MODIFICACIONES
*------------------------------------------------------------------------------
* Autor 					: N/A
* Compania 					: N/A
* Proyecto 					: N/A 							Fecha: N/A
* Modificacion 				: N/A
*------------------------------------------------------------------------------
*/
package mx.gob.segob.nsjp.dao.objeto.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.objeto.ObjetoEstudioDAO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.model.ObjetoEstudio;
import mx.gob.segob.nsjp.model.Valor;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author EdgarTE
 *
 */
@Repository
public class ObjetoEstudioDAOImpl extends GenericDaoHibernateImpl<ObjetoEstudio, Long>
		implements ObjetoEstudioDAO {

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.dao.objeto.ObjetoEstudioDAO#consultarEstudiosPorTipoObjeto(mx.gob.segob.nsjp.model.Valor)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ObjetoEstudio> consultarEstudiosPorTipoObjeto(Valor tipoObj) {
		final PaginacionDTO pag = PaginacionThreadHolder.get();
		
		StringBuffer hqlQuery = new StringBuffer("SELECT oe ");
								 hqlQuery.append("FROM ObjetoEstudio oe ");
								 hqlQuery.append("WHERE oe.tipoObjeto.valorId = "+tipoObj.getValorId().toString()+" ");
	
		return super.ejecutarQueryPaginado(hqlQuery, pag);
	}
}
