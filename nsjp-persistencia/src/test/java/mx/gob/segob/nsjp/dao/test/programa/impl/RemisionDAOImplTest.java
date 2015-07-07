/**
* Nombre del Programa : RemisionDAOImplTest.java
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
package mx.gob.segob.nsjp.dao.test.programa.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.programa.RemisionDAO;
import mx.gob.segob.nsjp.dao.test.base.BaseTestPersistencia;
import mx.gob.segob.nsjp.model.CatTipoRemision;
import mx.gob.segob.nsjp.model.Remision;
import mx.gob.segob.nsjp.model.Sentencia;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author AntonioBV
 *
 */
public class RemisionDAOImplTest  extends BaseTestPersistencia<RemisionDAO> {
    	
	public void testConsultarRemisionPorId() {
    	
    	Remision remision = new Remision();
    	remision.setRemisionId(1L);
    	remision.setImontoDanioReparado(null);
		try {
			remision = daoServcice.consultarRemisionPorId(remision);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
        assertNotNull(remision.getImontoDanioReparado());
    }
	
	public void testConsultarRemisionPorFiltros(){
		Sentencia sentencia = new Sentencia();
		sentencia.setSentenciaId(117L);
		
		CatTipoRemision tipoRemision = new CatTipoRemision();
		tipoRemision.setCatTipoRemisionId(5L);
		
		Remision remision = new Remision();
		remision.setCatTipoRemision(tipoRemision);
		remision.setSentencia(sentencia);
		
		try {
			Remision remisionBD = daoServcice.consultaRemisionPorFiltros(remision);
			logger.info("Id Remision: "+remisionBD.getRemisionId());
			logger.info("Id Tipo Remision: "+remisionBD.getCatTipoRemision().getCatTipoRemisionId());
			logger.info("Id sentencia: "+remisionBD.getSentencia().getSentenciaId());
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public void testConsultarComplementoRemisiones(){
		
		//Lista de catTipoRemision
		List<Long> listaCatTipoRemisionId = new ArrayList<Long>();
		listaCatTipoRemisionId.add(1L);
		//listaCatTipoRemisionId.add(3L);
		//SentenciaId
		Long sentenciaId = 144L;

		try{
			List<Remision> listaComplementeRemisiones =  daoServcice.consultarComplementoRemisiones(listaCatTipoRemisionId, sentenciaId);
			assertNotNull(listaComplementeRemisiones);
			//assertTrue(!listaComplementeRemisiones.isEmpty());
			logger.info("Tamaño de la lista: "+listaComplementeRemisiones.size());
			for (Remision remisionBD:listaComplementeRemisiones){
				logger.info("Id Remision: "+remisionBD.getRemisionId());
				logger.info("Id Tipo Remision: "+remisionBD.getCatTipoRemision().getCatTipoRemisionId());
				logger.info("Id sentencia: "+remisionBD.getSentencia().getSentenciaId());
			}
			
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
	}
}

