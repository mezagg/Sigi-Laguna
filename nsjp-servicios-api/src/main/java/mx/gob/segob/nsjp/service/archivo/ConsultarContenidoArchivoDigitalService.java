/**
* Nombre del Programa : ConsultarContenidoArchivoDigitalService.java
* Autor                            : Emigdio
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 06/07/2011
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
package mx.gob.segob.nsjp.service.archivo;

import java.util.List;

import mx.gob.segob.nsjp.comun.enums.actividad.Actividades;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.archivo.ArchivoDigitalDTO;
import mx.gob.segob.nsjp.dto.medida.MedidaDTO;

/**
 * Interfaz del servicio de negocio para consultar el contenido de un archivo digital
 * @version 1.0
 * @author Emigdio Hernández
 *
 */
public interface ConsultarContenidoArchivoDigitalService {
	/**
	 * Consulta el contenido de un archivo digital en base al ID del archivo o al ID del documento
	 * @param documentoId En caso de tener el id del documento se envía este ID
	 * @param archivoId En caso de tener el ID del archivo digital se envía este ID
	 * @return Contenido del archivo, null si no se localiza el archivo
	 */
	byte[] consultarContenidoArchivoDigitalPorArchivoODocumento(Long documentoId,Long archivoId) throws NSJPNegocioException;
	/**
	 * Consulta un archivo digital completo de un archivo digital en base al ID del archivo o al ID del documento
	 * @param documentoId En caso de tener el id del documento se envía este ID
	 * @param archivoId En caso de tener el ID del archivo digital se envía este ID
	 * @return Archivo digital con contenido, null en caso de no localizarlo
	 */
	ArchivoDigitalDTO consultarArchivoDigitalCompletoPorArchivoODocumento(Long documentoId,Long archivoId) throws NSJPNegocioException;
	
	/**
	 * Consulta la información de un archivo digital sin su contenido, para efectos de manipulación
	 * en la vista, siempre y cuando este asociado a una actividad de tipo <code>actividad</code>
	 * @param actividad Actividad a la que debe estar asociado el Archivo Digital qu ese desea consultar.
	 * @return ArchivoDigitalDTO con la información del archivo sin su contenido.
	 * @throws NSJPNegocioException
	 */
	public ArchivoDigitalDTO consultarArchivoDitalSinContenidoPorActividad(Long idExpediente, Actividades actividad) throws NSJPNegocioException;
	
	/**
	 * Lee el contenido de un archivo digital en base a su ID de un Elemento
	 * Esto permite consultar la foto asociada a cualquier elemento.
	 * El archivo asociado al Elemento. PJ. Probable Responsable.
	 * 
	 * @param idElemento del elemento a buscar su archivo Digital
	 * @return ArchivoDigitalDTO con el contenido del archivo digital 
	 * @throws NSJPNegocioException
	 */
	ArchivoDigitalDTO consultarArchivoDigitalXElementoId(Long idElemento) throws NSJPNegocioException;

	/**
	 * Obtiene los documentos relacionados a una medida
	 * @author cesarAgustin
	 * @param medidaDTO
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<ArchivoDigitalDTO> consultarArchivoDigitalPorMedida(MedidaDTO medidaDTO);
	
	/**
	 * Consulta archivos digitales asociados a un elemento 
	 * @param elementoId
	 * @return
	 * @throws NSJPNegocioException
	 */
	 List<ArchivoDigitalDTO> consultarArchivosDigitalesXElementoId(Long elementoId)throws NSJPNegocioException;
	 
}
