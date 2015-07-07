/**
* Nombre del Programa : AsignacionCentroDetencionDAOImplTest.java
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

import mx.gob.segob.nsjp.dao.programa.AsignacionCentroDetencionDAO;
import mx.gob.segob.nsjp.dao.test.base.BaseTestPersistencia;
import mx.gob.segob.nsjp.model.AsignacionCentroDetencion;
import mx.gob.segob.nsjp.model.Sentencia;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author AntonioBV
 *
 */
public class AsignacionCentroDetencionDAOImplTest  extends BaseTestPersistencia<AsignacionCentroDetencionDAO> {
    	
	public void testConsultarAsignacionCentroDetencionPorId() {
    	
    	AsignacionCentroDetencion asignacionCentroDetencion = new AsignacionCentroDetencion();
    	asignacionCentroDetencion.setAsignacionCentroDetencionId(1L);
    	asignacionCentroDetencion.setBarraigado(null);
		try {
			asignacionCentroDetencion = daoServcice.consultarAsignacionCentroDetencionPorId(asignacionCentroDetencion);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
        assertNotNull(asignacionCentroDetencion.getBarraigado());
    }
	
	public void testConsultarCentroDetencionActual() {
    	
    	Sentencia sentencia = new Sentencia();
    	sentencia.setSentenciaId(8L);
		try {
			AsignacionCentroDetencion asignacionCentroDetencion = daoServcice.consultarAsignacionCentroDetencionActual(sentencia);
			if (asignacionCentroDetencion != null){
				logger.info("Id Asignacion centro detencion actual: "+asignacionCentroDetencion.getAsignacionCentroDetencionId());				
			}else{
				logger.info("No se encontro una asignacion de centro de detencion actual.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}

