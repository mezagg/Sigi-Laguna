/**
 * Nombre del Programa : AudienciaDAOImplTest.java
 * Autor                            : vaguirre
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 31 May 2011
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
package mx.gob.segob.nsjp.dao.test.bitacora;

import mx.gob.segob.nsjp.dao.bitacora.BitacoraDescargaDAO;
import mx.gob.segob.nsjp.dao.test.base.BaseTestPersistencia;
import mx.gob.segob.nsjp.model.BitacoraDescarga;
import mx.gob.segob.nsjp.model.PermisoAudiencia;

/**
 * Describir el objetivo de la clase con punto al final.
 * 
 * @version 1.0
 * @author AAAV
 * 
 */
public class BitacoraDescargaDAOImplTest extends BaseTestPersistencia<BitacoraDescargaDAO> {
		
	  public void testCreate(){
		  	BitacoraDescarga bd = new BitacoraDescarga();
		  	bd.setPermisoAudiencia(new PermisoAudiencia(19L));
		  	daoServcice.create(bd);		  			  	
	  }
}
