/**
* Nombre del Programa 		: BitacoraElementoDAOImplTest.java
* Autor 					: EdgarAT
* Compania 					: Ultrasist
* Proyecto 					: NSJP 								Fecha: 07/11/2012
* Marca de cambio 			: N/A
* Descripcion General 		: Describir el objetivo de la clase de manera breve
* Programa Dependiente 		: N/A
* Programa Subsecuente 		: N/A
* Cond. de ejecucion 		: N/A
* Dias de ejecucion 		: N/A 								Horario: N/A
*                              MODIFICACIONES
*------------------------------------------------------------------------------
* Autor 					: N/A
* Compania 					: N/A
* Proyecto 					: N/A 								Fecha: N/A
* Modificacion 				: N/A
*------------------------------------------------------------------------------
*/
package mx.gob.segob.nsjp.dao.test.elemento.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.elemento.BitacoraElementoDAO;
import mx.gob.segob.nsjp.dao.test.base.BaseTestPersistencia;
import mx.gob.segob.nsjp.model.BitacoraElemento;
import mx.gob.segob.nsjp.model.Elemento;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author EdgarAT
 *
 */
public class BitacoraElementoDAOImplTest extends BaseTestPersistencia<BitacoraElementoDAO> {
	
	public void testconsultarBitacoraElementos(){
		BitacoraElemento bitacoraUno = new BitacoraElemento();
		bitacoraUno.setElemento(new Elemento(3338L));
		
		BitacoraElemento bitacoraDos = new BitacoraElemento();
		bitacoraDos.setElemento(new Elemento(3337L));
		
		List<BitacoraElemento> bitacoras = new ArrayList<BitacoraElemento>();
		bitacoras.add(bitacoraUno);
		bitacoras.add(bitacoraDos);
		
		try {
			List<BitacoraElemento> bitacorasConsultadas = daoServcice.consultarBitacoraElementos(bitacoras);
			if (bitacorasConsultadas != null
					&& !bitacorasConsultadas.isEmpty()){
				for (BitacoraElemento bit : bitacorasConsultadas){
					logger.info("Id de la bitacora: "+bit.getBitacoraElementoId());
					logger.info("Fecha de modificacion: "+bit.getFechaModificacion());
					logger.info("Id del elemento: "+bit.getElemento().getElementoId());
					logger.info("___________________________________________________________________________");
				}
			}
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage(),e);
		}
	}
	
	public void testconsultarBitacoraXElemento(){
		BitacoraElemento bitacora = new BitacoraElemento();
		bitacora.setElemento(new Elemento(3337L));
		try {
			bitacora = daoServcice.consultarBitacoraXElemento(bitacora);
			logger.info("Id de la bitacora: "+bitacora.getBitacoraElementoId());
			logger.info("Fecha de modificacion: "+bitacora.getFechaModificacion());
			logger.info("Id del elemento: "+bitacora.getElemento().getElementoId());
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage(), e);
		}
	}

}