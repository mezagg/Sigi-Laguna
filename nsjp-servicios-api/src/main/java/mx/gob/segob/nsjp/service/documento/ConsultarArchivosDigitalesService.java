package mx.gob.segob.nsjp.service.documento;

import java.util.List;
import java.util.Map;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.archivo.ArchivoDigitalDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;

public interface ConsultarArchivosDigitalesService {
	
	/**
	 * Operación que permite consultar cuales son los archivos digitales
	 * asociados a una Solicitud.
	 * @param idSolicitud : Long El id de solicitud de la cual se quieran consultar sus
	 * archivos digitales asociados	 
	 * @return List<ArchivoDigitalDTO> Lista con los archivos digitales asociados a la solicitud
	 * @throws NSJPNegocioException
	 */
	public List<ArchivoDigitalDTO> consultarArchivosDigitalesXSolicitud(Long IdSolicitud) throws NSJPNegocioException;
	
	
	/**
	 * Consulta un archivo digital completo en base al ID de documento
	 * @param documentoId ID del documento para buscar el archivo digital
	 * @return Archivo digital Completo
	 * @throws NSJPNegocioException
	 * @author Emigdio Hernández
	 */
	ArchivoDigitalDTO consultarArchivoDigitalCompleto(Long documentoId) throws NSJPNegocioException;
	
	
	/**
	 * Consulta los documentos asociados a un archivo digital completo en base
	 * @param IdSolicitud del documento para buscar el documento
	 * @return Documentos completos
	 * @throws NSJPNegocioException 
	 */
	List<DocumentoDTO> consultarArchivosDigitalesXSolicitudPericial(Long IdSolicitud,Boolean esPdf) throws NSJPNegocioException;
	
    /**
     * M&eacute;todo que lleva a cabo la consulta de los documentos que se encuentran relacionados con 
     * una lista de identificadores de archivos digitales. 
     * @param lstArchivoDigitalId - Lista de identificadores de archivos digitales de los cuales se van
     * 								a consultar los documentos relacionados.
     * @return Map<Long,Documento> - Mapa compuesto con el identificador del archivo digital como llave 
     * 								 y como valor el documento asociado a dicho archivo digital.
     * @throws NSJPNegocioException - En el caso de que la lista de identificadores de archivos digitales
     * 								  sea nula o se encuentre vac&iacute;a.
     */
    Map<Long,DocumentoDTO> consultarDocumentosPorArchivosDigitales(List<Long> lstArchivoDigitalId) throws NSJPNegocioException;
	
}
