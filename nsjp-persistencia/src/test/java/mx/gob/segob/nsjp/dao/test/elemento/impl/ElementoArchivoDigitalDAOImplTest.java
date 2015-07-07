/**
* Nombre del Programa : ElementoArchivoDigitalDAOImplTest.java
* Autor                            : cesarAgustin
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 29 Aug 2011
* Marca de cambio        : N/A
* Descripcion General    : Pruebas unitarias para metodos de acceso a datos de la entidad ElementoArchivoDigital
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
package mx.gob.segob.nsjp.dao.test.elemento.impl;

import java.util.List;

import mx.gob.segob.nsjp.dao.elemento.ElementoArchivoDigitalDAO;
import mx.gob.segob.nsjp.dao.test.base.BaseTestPersistencia;
import mx.gob.segob.nsjp.model.ArchivoDigital;

/**
 * Pruebas unitarias para metodos de acceso a datos de la entidad ElementoArchivoDigital.
 * @version 1.0
 * @author rgama
 *
 */
public class ElementoArchivoDigitalDAOImplTest extends
		BaseTestPersistencia<ElementoArchivoDigitalDAO> {
	
	public void tesTconsultarDocumentosMedidaCautelarAdjuntosPorMedidaCautelarId(){
		Long elementoId = 12382L;
		
		List<ArchivoDigital> respuesta = daoServcice.consultarArchivosDigitalesXElementoId(elementoId);
		
		assertTrue("La lista debe tener minimo un registro", respuesta.size()>0);
		logger.info("La lista debe tener minimo un registro" + respuesta.size());
		
		for (ArchivoDigital archivoDigital : respuesta) {
			logger.info("ID: "+ archivoDigital.getArchivoDigitalId());
			logger.info("Nombre: "+ archivoDigital.getNombreArchivo());
			logger.info("Tipo: "+ archivoDigital.getTipoArchivo());
		}
		
	}
	
	
	public void testConsultarArchivosDigitalesConImagenXElementoId(){
		Long elementoId = 15345L;
		List<ArchivoDigital> respuesta = daoServcice.consultarArchivosDigitalesSinEvniarXElementoId(elementoId);
		
		assertTrue("La lista debe tener minimo un registro", respuesta.size()>0);
		logger.info("La lista debe tener minimo un registro" + respuesta.size());
		
		for (ArchivoDigital archivoDigital : respuesta) {
			logger.info("ID: "+ archivoDigital.getArchivoDigitalId());
			logger.info("Nombre: "+ archivoDigital.getNombreArchivo());
			logger.info("Tipo: "+ archivoDigital.getTipoArchivo());
			logger.info("Longitud en bytes: "+ archivoDigital.getContenido().length);
		}
		
	}
	
}

