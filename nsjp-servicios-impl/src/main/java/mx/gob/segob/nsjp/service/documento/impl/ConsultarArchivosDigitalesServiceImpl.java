package mx.gob.segob.nsjp.service.documento.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.archivo.ArchivoDigitalDAO;
import mx.gob.segob.nsjp.dao.documento.DocumentoDAO;
import mx.gob.segob.nsjp.dao.solicitud.SolicitudAdjuntosDAO;
import mx.gob.segob.nsjp.dto.archivo.ArchivoDigitalDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.model.Documento;
import mx.gob.segob.nsjp.model.SolicitudAdjuntos;
import mx.gob.segob.nsjp.service.archivo.impl.transform.ArchivoDigitalTransformer;
import mx.gob.segob.nsjp.service.documento.ConsultarArchivosDigitalesService;
import mx.gob.segob.nsjp.service.documento.impl.tranform.DocumentoTransformer;

@Service
@Transactional
public class ConsultarArchivosDigitalesServiceImpl implements ConsultarArchivosDigitalesService{

	@Autowired SolicitudAdjuntosDAO solicitudAdjuntosDAO;
	@Autowired DocumentoDAO documentoDAO;	
	@Autowired
	private ArchivoDigitalDAO archivoDigitalDAO;
	@Override
	public List<ArchivoDigitalDTO> consultarArchivosDigitalesXSolicitud(
			Long IdSolicitud) throws NSJPNegocioException{
		List<ArchivoDigitalDTO> archivosDTO = new ArrayList<ArchivoDigitalDTO>(); 
		List<SolicitudAdjuntos> archivos = solicitudAdjuntosDAO.consultarAdjuntosXSolicitud(IdSolicitud,null); 
		
		if(archivos.size()>0)
		{
			for(SolicitudAdjuntos row:archivos)
			{
				archivosDTO.add(ArchivoDigitalTransformer.transformarArchivoDigital(row.getArchivoDigital()));
			}
		}
		 
		return archivosDTO;
	}
	
	@Override
	public List<DocumentoDTO> consultarArchivosDigitalesXSolicitudPericial(
			Long IdSolicitud,Boolean esPdf) throws NSJPNegocioException{
		List<SolicitudAdjuntos> archivos = solicitudAdjuntosDAO.consultarAdjuntosXSolicitud(IdSolicitud,esPdf);
		List<DocumentoDTO> documentosDTO = new ArrayList<DocumentoDTO>();
		
		if(archivos.size()>0)
		{
			for(SolicitudAdjuntos row:archivos)
			{
		    	Documento documentoExpediente = documentoDAO.consultarDocumentoPorArchivoDigital(row.getArchivoDigital().getArchivoDigitalId().longValue());
		    	DocumentoDTO documentoDTO = DocumentoTransformer.transformarDocumento(documentoExpediente);
		    	documentoDTO.setArchivoDigital(ArchivoDigitalTransformer.transformarArchivoDigital(documentoExpediente.getArchivoDigital()));
		    	documentosDTO.add(documentoDTO);
			}
		}
		 
		return documentosDTO;
	}
	
	/*
	 * (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.documento.ConsultarArchivosDigitalesService#consultarArchivoDigitalCompleto(java.lang.Long)
	 */
	@Override
	public ArchivoDigitalDTO consultarArchivoDigitalCompleto(Long documentoId)
			throws NSJPNegocioException {
		return ArchivoDigitalTransformer.
		transformarArchivoDigital(
					archivoDigitalDAO.consultarArchivoDigitalPorDocumento(documentoId));
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.documento.ConsultarArchivosDigitalesService#consultarDocumentosPorArchivosDigitales(java.util.List)
	 */
	@Override
	public Map<Long, DocumentoDTO> consultarDocumentosPorArchivosDigitales(
			List<Long> lstArchivoDigitalId) throws NSJPNegocioException {
		Map<Long,DocumentoDTO> mapaDocumentos = null;
		List<Documento> documentosAsociados = documentoDAO.consultarDocumentosPorArchivosDigitales(lstArchivoDigitalId);
		if (documentosAsociados != null){
			mapaDocumentos = new HashMap<Long, DocumentoDTO>();
			for (Documento docto : documentosAsociados){
				if (!mapaDocumentos.containsKey(docto.getArchivoDigital().getArchivoDigitalId())){
					mapaDocumentos.put(docto.getArchivoDigital().getArchivoDigitalId(), 
							DocumentoTransformer.transformarDocumento(docto));
				}
			}
		}
		return mapaDocumentos;
	}
}
