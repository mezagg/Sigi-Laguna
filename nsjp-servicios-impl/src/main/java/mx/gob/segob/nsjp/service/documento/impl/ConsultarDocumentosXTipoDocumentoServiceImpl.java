/**
 * Nombre del Programa : ConsultarDocumentosXTipoDocumentoServiceImpl.java
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
package mx.gob.segob.nsjp.service.documento.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.documento.DocumentoDAO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.model.Documento;
import mx.gob.segob.nsjp.model.Expediente;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.documento.ConsultarDocumentosXTipoDocumentoService;
import mx.gob.segob.nsjp.service.documento.impl.tranform.DocumentoTransformer;
import mx.gob.segob.nsjp.service.expediente.impl.transform.ExpedienteTransformer;
import mx.gob.segob.nsjp.service.forma.impl.ConsultarFormaPlantillaServiceImpl;
import mx.gob.segob.nsjp.service.usuario.impl.transformer.ValorTransformer;

/**
 * Describir el objetivo de la clase con punto al final.
 * 
 * @version 1.0
 * @author adrian
 * 
 */
@Service
@Transactional
public class ConsultarDocumentosXTipoDocumentoServiceImpl implements
		ConsultarDocumentosXTipoDocumentoService {

	public final static Logger logger = Logger
			.getLogger(ConsultarFormaPlantillaServiceImpl.class);

	@Autowired
	private DocumentoDAO docDao;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.gob.segob.nsjp.service.documento.ConsultarDocumentosXTipoDocumentoService
	 * #consultarDocumentosXTipoDocumento(mx.gob.segob.nsjp.dto.expediente.
	 * ExpedienteDTO, java.lang.Long)
	 */
	@Override
	public List<DocumentoDTO> consultarDocumentosXTipoDocumento(
			ExpedienteDTO expedienteDTO, Long tipoDocumento)
			throws NSJPNegocioException {
		
		/* Revisar par�metros */
		if(expedienteDTO==null||tipoDocumento==null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		else if(expedienteDTO.getNumeroExpedienteId()==null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		
		/* Operaci�n */
		List<Documento> documentos = docDao.consultarDocumentosXExpedienteYTipoDocumento(expedienteDTO.getNumeroExpedienteId(),tipoDocumento);
		
		/* Transformaci�n */
		List<DocumentoDTO> docsDTO = new ArrayList<DocumentoDTO>();
		for (Documento doc : documentos) {
			docsDTO.add(DocumentoTransformer.transformarDatosLista(doc));
		}
		return docsDTO;
	}
	
	public List<DocumentoDTO> consultarDocumentosSinActividadXExpedienteYTipoDocumento(
			ExpedienteDTO expedienteDTO, Long tipoDocumento)
			throws NSJPNegocioException {
		
		/* Revisar par�metros */
		if(expedienteDTO==null||tipoDocumento==null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		else if(expedienteDTO.getNumeroExpedienteId()==null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);

		/* Operaci�n */
		List<Documento> documentos = docDao.consultarDocumentosSinActividadXExpedienteYTipoDocumento(expedienteDTO.getNumeroExpedienteId(),tipoDocumento);

		/* Transformaci�n */
		List<DocumentoDTO> docsDTO = new ArrayList<DocumentoDTO>();
		for (Documento doc : documentos) {
			docsDTO.add(DocumentoTransformer.transformarDatosLista(doc));
		}
		return docsDTO;
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.documento.ConsultarDocumentosXTipoDocumentoService#consultarDocumentosPorExpedienteYTipos(mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO, java.util.List)
	 */
	@Override
	public List<DocumentoDTO> consultarDocumentosPorExpedienteYTipos(
			ExpedienteDTO expediente, List<ValorDTO> tiposDocumento)
			throws NSJPNegocioException {
		
		if(expediente == null 
				|| expediente.getExpedienteId() == null
				|| expediente.getExpedienteId() < 1L
				|| tiposDocumento == null
				|| tiposDocumento.isEmpty()){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		Expediente expConsulta = ExpedienteTransformer.transformarExpedienteBasicoDefensoria(expediente);
		List<Valor> tiposDocConsulta = new ArrayList<Valor>();
		
		for (ValorDTO tipoDoc : tiposDocumento){
			tiposDocConsulta.add(ValorTransformer.transformar(tipoDoc));
		}
		
		List<Documento> documentosBD = docDao.consultarDocumentosPorExpedienteYTipos(expConsulta, tiposDocConsulta);
		
		List<DocumentoDTO> docsDTO = null;
		if (documentosBD != null
				&& !documentosBD.isEmpty()){
			docsDTO = new ArrayList<DocumentoDTO>();
			for (Documento doc : documentosBD) {
				docsDTO.add(DocumentoTransformer.transformarDocumento(doc));
			}
		}
		return docsDTO;
	}

}
