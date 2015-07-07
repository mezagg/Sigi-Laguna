/**
 * Nombre del Programa : InvolucradoSolicitudDefensorDAOImplTest.java
 * Autor                            : GustavoBP
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 01/11/2012
 * Marca de cambio        : N/A
 * Descripcion General    : Clase para generar pruebas unitarias de los medotos de InvolucradoSolicitudDefensorDAO
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
package mx.gob.segob.nsjp.dao.test.involucrado.impl;

import java.util.List;

import mx.gob.segob.nsjp.dao.involucrado.InvolucradoSolicitudDefensorDAO;
import mx.gob.segob.nsjp.dao.test.base.BaseTestPersistencia;
import mx.gob.segob.nsjp.model.Involucrado;

/**
 * Clase para generar pruebas unitarias de los medotos de
 * InvolucradoSolicitudDefensorDAO.
 * 
 * @author GustavoBP
 * 
 */
public class InvolucradoSolicitudDefensorDAOImplTest extends
		BaseTestPersistencia<InvolucradoSolicitudDefensorDAO> {

	public void testConsultarInvolucradosSolicitudDefensor() {
		Long idSolcitudDefensor = 2718L;
		List<Involucrado> involucrados = daoServcice
				.consultarInvolucradosSolicitudDefensor(idSolcitudDefensor);
		assertFalse("La lista no debe regresar vacia", involucrados.isEmpty());
		logger.info("Involucrados:" + involucrados.size());
		for (Involucrado involucrado : involucrados) {
			logger.info("*************************: "
					+ involucrado.getElementoId());
			logger.info("Involucrado:" + involucrado.getFolioElemento());
			logger.info("*************************: "
					+ involucrado.getEsDetenido());
		}
	}
}
