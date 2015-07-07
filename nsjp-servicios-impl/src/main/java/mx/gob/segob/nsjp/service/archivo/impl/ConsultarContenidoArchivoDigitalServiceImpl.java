/**
* Nombre del Programa : ConsultarContenidoArchivoDigitalServiceImpl.java
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
package mx.gob.segob.nsjp.service.archivo.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.actividad.Actividades;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.archivo.ArchivoDigitalDAO;
import mx.gob.segob.nsjp.dao.elemento.ElementoArchivoDigitalDAO;
import mx.gob.segob.nsjp.dao.medida.MedidaAdjuntosDAO;
import mx.gob.segob.nsjp.dto.archivo.ArchivoDigitalDTO;
import mx.gob.segob.nsjp.dto.medida.MedidaDTO;
import mx.gob.segob.nsjp.model.ArchivoDigital;
import mx.gob.segob.nsjp.service.archivo.ConsultarContenidoArchivoDigitalService;
import mx.gob.segob.nsjp.service.archivo.impl.transform.ArchivoDigitalTransformer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementaci�n del servicio de negocio para la consulta del conteido
 * de un archivo digitial.
 * @version 1
 * @author Emigdio Hern�ndez
 *
 */
@Service
@Transactional
public class ConsultarContenidoArchivoDigitalServiceImpl implements
		ConsultarContenidoArchivoDigitalService {
	
	@Autowired
	ArchivoDigitalDAO archivoDigitalDAO;
	@Autowired
	MedidaAdjuntosDAO medidaAdjuntosDAO;
	@Autowired
	ElementoArchivoDigitalDAO elementoArchivoDigitalDAO;
	
	
	
	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.documento.ConsultarContenidoArchivoDigitalService#consultarContenidoArchivoDigitalPorArchivoODocumento(java.lang.Long, java.lang.Long)
	 */
	@Override
	public byte[] consultarContenidoArchivoDigitalPorArchivoODocumento (
			Long documentoId, Long archivoId) throws NSJPNegocioException{
		byte [] contenido = null;
		
		if(archivoId != null){
			contenido = archivoDigitalDAO.consultarContenidoPorDocumentoOArchivo(archivoId, true);
		}else if(documentoId != null){
			contenido = archivoDigitalDAO.consultarContenidoPorDocumentoOArchivo(documentoId,false);
		}
		return contenido;
	}


	public ArchivoDigitalDTO consultarArchivoDitalSinContenidoPorActividad(Long idExpediente, Actividades actividad) throws NSJPNegocioException{
		 ArchivoDigital archivo = archivoDigitalDAO.consultarArchivoDitalSinContenidoPorActividad(idExpediente, actividad);
		 return ArchivoDigitalTransformer.transformarArchivoDigital(archivo);
	}
	
	@Override
	public ArchivoDigitalDTO consultarArchivoDigitalXElementoId(Long idElemento)
			throws NSJPNegocioException {
		if (idElemento == null || idElemento <= 0L) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		ArchivoDigital archivoDigital = archivoDigitalDAO
				.consultarArchivoDigitalXElementoId(idElemento);
		ArchivoDigitalDTO archivoDigitalDTO = ArchivoDigitalTransformer
				.transformarArchivoDigital(archivoDigital);

		return archivoDigitalDTO;
	}


	@Override
	public List<ArchivoDigitalDTO> consultarArchivoDigitalPorMedida(
			MedidaDTO medidaDTO) {
		
		List<ArchivoDigital> archivosDigMedida = medidaAdjuntosDAO.consultarArchivosDigitalesPorMedida(medidaDTO.getDocumentoId());
		
		List<ArchivoDigitalDTO> archivosMedidaDTO = new ArrayList<ArchivoDigitalDTO>();
		for (ArchivoDigital archivoDigital : archivosDigMedida) {
			archivosMedidaDTO.add(ArchivoDigitalTransformer.transformarArchivoDigital(archivoDigital));
		}				
		return archivosMedidaDTO;
	}

	/*
	 * (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.archivo.ConsultarContenidoArchivoDigitalService#consultarArchivoDigitalCompletoPorArchivoODocumento(java.lang.Long, java.lang.Long)
	 */
	@Override
	public ArchivoDigitalDTO consultarArchivoDigitalCompletoPorArchivoODocumento(
			Long documentoId, Long archivoId) throws NSJPNegocioException {

		
		if(archivoId != null && archivoId > 0L){
			return ArchivoDigitalTransformer.transformarArchivoDigital(archivoDigitalDAO.read(archivoId));
		}else if(documentoId != null){
			return ArchivoDigitalTransformer.transformarArchivoDigital(
					archivoDigitalDAO.consultarArchivoDigitalPorDocumento(documentoId));
		}
		return null;
		
	}

	
	@Override
	public List<ArchivoDigitalDTO> consultarArchivosDigitalesXElementoId(Long elementoId)throws NSJPNegocioException {
		
		List<ArchivoDigital> archivosDigMedida = elementoArchivoDigitalDAO.consultarArchivosDigitalesXElementoId(elementoId);
		
		List<ArchivoDigitalDTO> archivosMedidaDTO = new ArrayList<ArchivoDigitalDTO>();
		for (ArchivoDigital archivoDigital : archivosDigMedida) {
			archivosMedidaDTO.add(ArchivoDigitalTransformer.transformarArchivoDigital(archivoDigital));
		}				
		return archivosMedidaDTO;
	}	
	
}
