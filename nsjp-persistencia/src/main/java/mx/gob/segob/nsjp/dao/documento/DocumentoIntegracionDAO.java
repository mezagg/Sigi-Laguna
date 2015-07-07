/**
* Nombre del Programa 		: DocumentoIntegracionDAO.java
* Autor 					: EdgarAT
* Compania 					: Ultrasist
* Proyecto 					: NSJP 								Fecha: 26/11/2012
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
import mx.gob.segob.nsjp.model.DocumentoIntegracion;
import mx.gob.segob.nsjp.model.Expediente;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author EdgarAT
 *
 */
public interface DocumentoIntegracionDAO extends GenericDao<DocumentoIntegracion, Long> {
	
	/**
	 * M&eacute;todo que lleva a cabo la consulta de los documentos que se encuentran
	 * asociados a un expediente y que forman parte de los documentos registrados 
	 * dentro de la tabla DocumentoIntegracion.
	 * @param expediente - Expediente del cual se obtendr&aacute;n los documentos 
	 * 					   asociados.
	 * @return List<Documento> - Lista con los documentos asociados al expediente 
	 * 							 y que se encuentran registrados dentro de la tabla 
	 * 							 DocumentoIntegracion.
	 * @throws NSJPNegocioException -  En el caso de que el expediente pasado como
	 * 								   par&aacute;metro no cuente con un identificador
	 * 								   asociado o el objeto sea nulo. 
	 */
	public List<Documento> consultarDocumentosIntegracionXExpediente(Expediente expediente) throws NSJPNegocioException;
}
