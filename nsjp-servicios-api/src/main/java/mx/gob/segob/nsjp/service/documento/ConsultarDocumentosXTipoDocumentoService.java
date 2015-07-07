/**
* Nombre del Programa : ConsultarDocumentosXTipoDocumentoService.java
* Autor                            : adrian
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 28/06/2011
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
package mx.gob.segob.nsjp.service.documento;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author adrian
 *
 */
public interface ConsultarDocumentosXTipoDocumentoService {

	/**
	  * Operación que realiza la funcionalidad de consultar los documentos asociados a un expediente y al tipo de documento
	  * 
	  * @param expedienteDTO: El número de expediente
	  * @param tipoDocumento: El tipo de documento a consultar.
	  * @return Regresa un listado de objetos de tipo Documento, en caso contrario regresa null.
	  * @throws NSJPNegocioException
	  */
	List<DocumentoDTO> consultarDocumentosXTipoDocumento(
			ExpedienteDTO expedienteDTO, Long tipoDocumento)throws NSJPNegocioException;
	/**
	 * Permite consultar documentos en base al tipo sin importar si existe actividad
	 * Se usa para consultar medidas cautelares.
	 * @param expedienteDTO
	 * @param tipoDocumento
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<DocumentoDTO> consultarDocumentosSinActividadXExpedienteYTipoDocumento(
			ExpedienteDTO expedienteDTO, Long tipoDocumento)throws NSJPNegocioException;
	
	/**
	 * M&eacute;todo que lleva a cabo la consulta de los documentos asociados a un expediente
	 * y que corresponden con los tipos pasados dentro de la lista de tipos de documento
	 * pasada como par&aacute;metros.
	 * @param expediente - ExpedienteDTO del cu&aacute;l se van a obtener los documentos asociados.
	 * @param tiposDocumento - List<ValorDTO> con los tipos de documento sobre los cuales se va 
	 * 						   a realizar la consulta.
	 * @return List<DocumentoDTO> - List<DocumentoDTO> con los documentos que cumplieron con los filtros
	 * 							 	pasados como par&aacute;metros.
	 * @throws NSJPNegocioException - En el caso de que no se env&iacute;en todos los par&aacute;metros
	 * 								  obligatorios.
	 */
	public List<DocumentoDTO> consultarDocumentosPorExpedienteYTipos(ExpedienteDTO expediente,
			List<ValorDTO> tiposDocumento) throws NSJPNegocioException;

}
