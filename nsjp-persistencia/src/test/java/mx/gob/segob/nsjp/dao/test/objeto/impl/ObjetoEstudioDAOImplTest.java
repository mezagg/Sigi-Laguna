/**
* Nombre del Programa 		: ObjetoEstudioDAOImplTest.java
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
package mx.gob.segob.nsjp.dao.test.objeto.impl;

import java.util.List;

import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dao.objeto.ObjetoEstudioDAO;
import mx.gob.segob.nsjp.dao.test.base.BaseTestPersistencia;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.model.ObjetoEstudio;
import mx.gob.segob.nsjp.model.Valor;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author EdgarTE
 *
 */
public class ObjetoEstudioDAOImplTest extends BaseTestPersistencia<ObjetoEstudioDAO> {
	
	public void testConsultarEstudiosPorTipoObjeto(){
		Valor tipoObj = new Valor();
		tipoObj.setValorId(241L);
		
		List<ObjetoEstudio> objEstudio = daoServcice.consultarEstudiosPorTipoObjeto(tipoObj);
		
		if (objEstudio != null && !objEstudio.isEmpty()){
			for (ObjetoEstudio obj : objEstudio){
				logger.info("Tipo de objeto: "+obj.getTipoObjeto().getValor()+" estudio asociado: "+obj.getEstudioPericial().getValor());
			}
		}
		
		final PaginacionDTO pag2 = PaginacionThreadHolder.get();
		if (pag2 != null){
			if (pag2 != null && pag2.getTotalRegistros() != null) {
				logger.info("<total>" + pag2.getTotalPaginas() + "</total>");
				logger.info("<records>" + pag2.getTotalRegistros() + "</records>");
			} else {
				logger.info("<total>0</total>");
				logger.info("<records>0</records>");
			}
		}
	}

}
