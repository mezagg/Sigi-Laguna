/**
* Nombre del Programa : CamposFormaDAOImplTest.java
* Autor                            : Emigdio Hern�ndez
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 02/06/2011
* Marca de cambio        : N/A
* Descripcion General    : Prueba unitaria para el DAO de consulta de campos de la forma
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
package mx.gob.segob.nsjp.dao.test.forma.impl;

import java.util.List;

import mx.gob.segob.nsjp.dao.forma.CamposFormaDAO;
import mx.gob.segob.nsjp.dao.test.base.BaseTestPersistencia;
import mx.gob.segob.nsjp.model.CamposForma;

/**
 * Prueba unitaria para el DAO de consulta de campos de la forma
 * @version 1.0
 * @author Emigdio Hern�ndez
 *
 */
public class CamposFormaDAOImplTest extends BaseTestPersistencia<CamposFormaDAO>{

	/**
	 * Probar la consulta de todos los campos de forma
	 */
	public void testConsultaCamposForma(){
		List<CamposForma> campos = daoServcice.obtenerCamposForma(null);
		
		assertNotNull("La lista no debe de ser nula",campos);
		
	}
	
}
