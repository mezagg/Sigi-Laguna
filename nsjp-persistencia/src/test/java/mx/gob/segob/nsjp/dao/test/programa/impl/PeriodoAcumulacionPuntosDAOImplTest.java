/**
* Nombre del Programa 		: PeriodoAcumulacionPuntosDAOImplTest.java
* Autor 					: EdgarTE
* Compania 					: Ultrasist
* Proyecto 					: NSJP 							Fecha: 20 Sep 2012
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
package mx.gob.segob.nsjp.dao.test.programa.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mx.gob.segob.nsjp.dao.programa.PeriodoAcumulacionPuntosDAO;
import mx.gob.segob.nsjp.dao.test.base.BaseTestPersistencia;
import mx.gob.segob.nsjp.model.ActoBuenaConducta;
import mx.gob.segob.nsjp.model.PeriodoAcumulacionPuntos;
import mx.gob.segob.nsjp.model.Sentencia;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author EdgarTE
 *
 */
public class PeriodoAcumulacionPuntosDAOImplTest extends
		BaseTestPersistencia<PeriodoAcumulacionPuntosDAO> {

	public void testConsultarPeriodoAcumulacionPuntosPorSentencia(){
		
		Sentencia sentencia = new Sentencia();
		sentencia.setSentenciaId(6L);
		
		PeriodoAcumulacionPuntos papFiltro = new PeriodoAcumulacionPuntos();
		papFiltro.setbResumenEmitido(Boolean.FALSE);
		
		ActoBuenaConducta abc = new ActoBuenaConducta();
		abc.setPeriodoAcumulacionPuntos(papFiltro);
		
		Set<ActoBuenaConducta> setAbc = new HashSet<ActoBuenaConducta>();
		setAbc.add(abc);
		
		sentencia.setActoBuenaConductas(setAbc);
		
		try {
			List<PeriodoAcumulacionPuntos> periodos = daoServcice.consultarPeriodosAcumulacionPuntosPorSentencia(sentencia);
			for (PeriodoAcumulacionPuntos pap : periodos){
				logger.info("Id Periodo de acumulacion de puntos: " + pap.getPeriodoAcumulacionPuntosId());
				logger.info("Nombre del periodo de acumulacion de puntos: " + pap.getcNombrePeriodo());
			}
			assertNotNull(periodos);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
