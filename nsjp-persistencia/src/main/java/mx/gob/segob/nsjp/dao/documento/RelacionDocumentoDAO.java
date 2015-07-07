/**
* Nombre del Programa 		: RelacionDocumentoDAO.java
* Autor 					: EdgarAT
* Compania 					: Ultrasist
* Proyecto 					: NSJP 								Fecha: 11/01/2013
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
package mx.gob.segob.nsjp.dao.documento;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.model.Documento;
import mx.gob.segob.nsjp.model.RelacionDocumento;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author EdgarAT
 *
 */
public interface RelacionDocumentoDAO extends GenericDao<RelacionDocumento, Long> {
	
	/**
	 * M&eacute;todo que lleva a cabo la consulta de las relaciones de documentos a 
	 * partir del documento principal.
	 * 
	 * @param doc - <code>Documento</code> que contiene la informaci&oacute;n del 
	 * 				documento principal a partir del cual se filtrar&aacute; la 
	 * 				consulta.
	 * @return List<RelacionDocumento> - Lista de relaciones documento que concuerdan 
	 * 									 con los par&aacute;metros ingresados como 
	 * 									 filtro. 
	 * @throws NSJPNegocioException - En el caso de que no se ingresen los 
	 * 								  par&aacute;metros indispensables.
	 */
	public List<RelacionDocumento> consultarRelacionesPorDocPrincipal(Documento doc) throws NSJPNegocioException;

}
