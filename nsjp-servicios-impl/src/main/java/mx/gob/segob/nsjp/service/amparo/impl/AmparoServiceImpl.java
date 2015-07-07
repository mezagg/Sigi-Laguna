/**
 * Nombre del Programa : AmparoServiceImpl.java
 * Autor                            : rgama
 * Compania                         : Ultrasist
 * Proyecto                         : NSJP                    Fecha: 15-Feb-2012
 * Marca de cambio        : N/A
 * Descripcion General    : N/A
 * Programa Dependient    :N/A
 * Programa Subsecuente   :N/A
 * Cond. de ejecucion     :N/A
 * Dias de ejecucion      :N/A                                Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                            :N/A
 * Compania                         :N/A
 * Proyecto                         :N/A                      Fecha: N/A
 * Modificacion           :N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.service.amparo.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.relacion.Relaciones;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.documento.DocumentoDAO;
import mx.gob.segob.nsjp.dao.documento.DocumentoValorDAO;
import mx.gob.segob.nsjp.dao.documento.RelacionDocumentoElementoDAO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoValorDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.model.Documento;
import mx.gob.segob.nsjp.model.DocumentoValor;
import mx.gob.segob.nsjp.model.Elemento;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.amparo.AmparoService;
import mx.gob.segob.nsjp.service.documento.impl.tranform.DocumentoTransformer;
import mx.gob.segob.nsjp.service.involucrado.ConsultarIndividuoService;
import mx.gob.segob.nsjp.service.relacion.RegistrarRelacionService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @version 1.0
 * @author rgama
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class AmparoServiceImpl implements AmparoService {

    /**
      * Logger de la clase.
      */
    private final static Logger logger = Logger
            .getLogger(AmparoServiceImpl.class);

    
    @Autowired 
    private RelacionDocumentoElementoDAO relacionDocumentoElementoDAO;
    
	@Autowired
	private ConsultarIndividuoService consultarIndividuoService;
	
	@Autowired
	private DocumentoValorDAO documentoValorDAO;
	
	@Autowired
	private RegistrarRelacionService registrarRelacionService;
	
	@Autowired
	private DocumentoDAO documentoDAO;

	@Override
	public List<InvolucradoDTO> consultarInvolucradosXAmparo(Long idAmparo)
			throws NSJPNegocioException {

		if (idAmparo == null || idAmparo <=0L) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		logger.info("El id del Amparo recibido es: " + idAmparo);
		List<InvolucradoDTO> listaInvolucradosDTO = null;

		List<Elemento> listaAmparados = relacionDocumentoElementoDAO
				.consultarElementosPorDocId(idAmparo);

		if (listaAmparados != null && !listaAmparados.isEmpty()) {
			
			listaInvolucradosDTO = new ArrayList<InvolucradoDTO>();
			
			for (Elemento involucrado : listaAmparados) {
				listaInvolucradosDTO.add(consultarIndividuoService
						.obtenerInvolucrado(new InvolucradoDTO(involucrado
								.getElementoId())));
			}
		}
		return listaInvolucradosDTO;
	}

	@Override
	public void insertarEstatusAmparo(DocumentoValorDTO documentoValorDTO)
			throws NSJPNegocioException {
		if (documentoValorDTO == null 
				|| documentoValorDTO.getDocumentoId() == null || documentoValorDTO.getDocumentoId() <=0L
				|| documentoValorDTO.getEstatus() == null || documentoValorDTO.getEstatus().getIdCampo() == null
				|| documentoValorDTO.getEstatus().getIdCampo() <=0L) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		DocumentoValor documentoValor = DocumentoTransformer.transformarDocumentoValor(documentoValorDTO);
		documentoValorDAO.create(documentoValor);
	}

	@Override
	public Map<Long, DocumentoValorDTO> consultarEstatusPorIdsDocumentos(
			List<Long> idsDocumentos) throws NSJPNegocioException {
		if(idsDocumentos == null 
				|| idsDocumentos.isEmpty()){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		Map<Long, DocumentoValorDTO> map = new HashMap<Long, DocumentoValorDTO>();
		for (DocumentoValor documentoValor : documentoValorDAO.consultarDocumentosValorPorIds(idsDocumentos)) {
			map.put(documentoValor.getIdDocumento(), DocumentoTransformer.transformarDocumentoValor(documentoValor));
		}
		return map;
	}

	@Override
	public DocumentoValorDTO consultarEstatusPorIdDocumento(Long idDocumento)
			throws NSJPNegocioException {
		DocumentoValor documentoValor = documentoValorDAO.consultarDocumentosValorPorDocumentoId(idDocumento);
		return DocumentoTransformer.transformarDocumentoValor(documentoValor);
	}

	@Override
	public void actualizaAmparo(DocumentoDTO amparo, DocumentoValorDTO estatus,
			List<Long> idsInvolucrados) throws NSJPNegocioException {
		if(amparo == null || amparo.getDocumentoId() == null || amparo.getDocumentoId() < 0L){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		Long idAmparo = amparo.getDocumentoId();
		Documento documento = documentoDAO.read(idAmparo);
		if(amparo.getFolioDocumento() != null && !amparo.getFolioDocumento().isEmpty()){			
			documento.setFolioDocumento(amparo.getFolioDocumento());
		}
		if(amparo.getDescripcion() != null && !amparo.getDescripcion().isEmpty()){			
			documento.setDescripcion(amparo.getDescripcion());
		}
		if(estatus != null && estatus.getEstatus() != null
				&& estatus.getEstatus().getIdCampo() != null){			
			DocumentoValor documentoValor = documentoValorDAO.consultarDocumentosValorPorDocumentoId(idAmparo);
			documentoValor.setEstatusDocumento(new Valor(estatus.getEstatus().getIdCampo()));
		}
		
		if(idsInvolucrados != null && !idsInvolucrados.isEmpty()){
			relacionDocumentoElementoDAO.eliminaRelacionDocumentoElementoPorDocId(idAmparo);
			for (Long idInv : idsInvolucrados) {
				registrarRelacionService.registrarRelacionDocumentoElemento(new Long(Relaciones.AMPARADO.ordinal()),idInv,idAmparo);
			}
		}
	}
   
}
