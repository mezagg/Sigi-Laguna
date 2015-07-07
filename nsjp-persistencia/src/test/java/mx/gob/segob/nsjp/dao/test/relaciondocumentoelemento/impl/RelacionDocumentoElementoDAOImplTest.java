/**
* Nombre del Programa	: RelacionDocumentoElementoDAOImplTest.java
* Autor                 : AlejandroGA
* Compania              : Ultrasist
* Proyecto              : NSJP                    Fecha: 22 Marzo 2013
* Marca de cambio       : N/A
* Descripcion General   : Tests para la clase
* Programa Dependiente  : N/A
* Programa Subsecuente 	: N/A
* Cond. de ejecucion    : N/A
* Dias de ejecucion     : N/A                             Horario: N/A
*                              MODIFICACIONES
*------------------------------------------------------------------------------
* Autor                 : N/A
* Compania              : N/A
* Proyecto              : N/A                                 Fecha: N/A
* Modificacion          : N/A
*------------------------------------------------------------------------------
*/
package mx.gob.segob.nsjp.dao.test.relaciondocumentoelemento.impl;

import java.util.List;

import mx.gob.segob.nsjp.dao.documento.RelacionDocumentoElementoDAO;
import mx.gob.segob.nsjp.dao.test.base.BaseTestPersistencia;
import mx.gob.segob.nsjp.model.Elemento;

/**
 * @author AlejandroGA
 * @version 1.0
 */
public class RelacionDocumentoElementoDAOImplTest extends
		BaseTestPersistencia<RelacionDocumentoElementoDAO> {

	public void testConsultarElementosPorDocId() {
		List<Elemento> listaElementos = daoServcice
				.consultarElementosPorDocId(2063L);

		if (listaElementos != null && !listaElementos.isEmpty()) {
			logger.info("El tamaño de la lista es:" + listaElementos.size());
			for (Elemento elemento : listaElementos) {
				logger.info("Elemento Id:" + elemento.getElementoId());
			}
		} else {
			logger.info("No existen datos");
		}
	}

}
