/**
 * Nombre del Programa 		: RecibirActualizacionMandamientoInterInstitucionalServiceImpl.java
 * Autor 					: AlejandroGA
 * Compania 				: Ultrasist
 * Proyecto 				: nsjp-modelo 					Fecha: 09/07/2013
 * Marca de cambio 			: N/A
 * Descripcion General 		: Clase Implementacion para lo relacionado con la actualizacion del mandamiento judicial
 * Programa Dependiente 	: N/A
 * Programa Subsecuente 	: N/A
 * Cond. de ejecucion 		: N/A
 * Dias de ejecucion 		: N/A 								Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor 					: N/A
 * Compania 				: N/A
 * Proyecto 				: N/A 								Fecha: N/A
 * Modificacion 			: N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.service.mandamiento.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.institucion.Areas;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.documento.MandamientoDAO;
import mx.gob.segob.nsjp.dao.expediente.NumeroExpedienteDAO;
import mx.gob.segob.nsjp.dao.institucion.ConfInstitucionDAO;
import mx.gob.segob.nsjp.dao.mandamiento.MandamientoPersonaDAO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoWSDTO;
import mx.gob.segob.nsjp.dto.documento.MandamientoDTO;
import mx.gob.segob.nsjp.dto.documento.MandamientoWSDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.institucion.AreaDTO;
import mx.gob.segob.nsjp.dto.mandamiento.FiltroMandamientoPersonaDTO;
import mx.gob.segob.nsjp.dto.mandamiento.MandamientoPersonaDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.model.ConfInstitucion;
import mx.gob.segob.nsjp.model.Expediente;
import mx.gob.segob.nsjp.model.Mandamiento;
import mx.gob.segob.nsjp.model.MandamientoPersona;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.service.documento.impl.tranform.MandamientoJudicialTransformer;
import mx.gob.segob.nsjp.service.funcionario.impl.transform.FuncionarioTransformer;
import mx.gob.segob.nsjp.service.infra.impl.transform.recibiractualizacionmandamiento.DocumentoWSDTOTransformer;
import mx.gob.segob.nsjp.service.infra.impl.transform.recibiractualizacionmandamiento.MandamientoWSDTOTransformer;
import mx.gob.segob.nsjp.service.mandamiento.AdministrarMandamientoJudicialService;
import mx.gob.segob.nsjp.service.mandamiento.RecibirActualizacionMandamientoInterInstitucionalService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author AlejandroGA
 * @version 1.0
 */

@Service("recibirActualizacionMandamientoInterInstitucionalService")
@Transactional(propagation = Propagation.REQUIRED)
public class RecibirActualizacionMandamientoInterInstitucionalServiceImpl
		implements RecibirActualizacionMandamientoInterInstitucionalService {

	/**
	 * Logger.
	 */
	private final static Logger logger = Logger
			.getLogger(RecibirActualizacionMandamientoInterInstitucionalServiceImpl.class);

	@Autowired
	private MandamientoDAO mandamientoDAO;
	@Autowired
	private MandamientoPersonaDAO mandamientoPersonaDAO;
	@Autowired
	private NumeroExpedienteDAO numeroExpedienteDAO;
	@Autowired
	private AdministrarMandamientoJudicialService administrarMandamientoJudicialService;
	@Autowired
	private ConfInstitucionDAO confInstitucionDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.gob.segob.nsjp.service.mandamiento.
	 * RecibirActualizacionMandamientoInterInstitucionalService
	 * #recibirActualizacionMandamientoInterInstitucional
	 * (mx.gob.segob.nsjp.dto.documento.MandamientoWSDTO,
	 * mx.gob.segob.nsjp.dto.documento.DocumentoWSDTO)
	 */
	@Override
	public void recibirActualizacionMandamientoInterInstitucional(
			MandamientoWSDTO mandamientoWSDTO, DocumentoWSDTO documentoWSDTO)
			throws NSJPNegocioException {

		if (logger.isDebugEnabled()) {
			logger.debug("*** RECIBIENDO ACTUALIZACION DE MANDAMIENTO JUDICIAL WS ***");
		}

		if (mandamientoWSDTO == null
				|| mandamientoWSDTO.getMandamientosPersona() == null
				|| mandamientoWSDTO.getFolioDocumento() == null
				|| documentoWSDTO == null
				|| documentoWSDTO.getArchivoDigital() == null
				|| documentoWSDTO.getArchivoDigital().getContenido() == null
				|| documentoWSDTO.getJerarquiaOrganizacional() == null
				|| documentoWSDTO.getFechaCreacion() == null) {

			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		Mandamiento mandamiento = mandamientoDAO
				.obtenerMandamientoPorFolioDoc(mandamientoWSDTO
						.getFolioDocumento());

		if (mandamiento == null) {
			logger.error("NO EXISTE EL MANDAMIENTO CON FOLIO:"
					+ mandamientoWSDTO.getFolioDocumento() + "****");
			throw new NSJPNegocioException(
					CodigoError.INFORMACION_PARAMETROS_ERRONEA);
		}

		MandamientoDTO mandamientoLocal = MandamientoJudicialTransformer
				.transformarMandamiento(mandamiento);
		MandamientoDTO mandamientoRemoto = MandamientoWSDTOTransformer
				.Transformar(mandamientoWSDTO);
		DocumentoDTO documentoCambioEstatus = DocumentoWSDTOTransformer
				.transformar(documentoWSDTO);

		/**
		 * Consultamos los mandamientos persona para cambiarles el estatus y
		 * obtener su id
		 */
		List<MandamientoPersonaDTO> listaMandamientosPersonaLocal = new ArrayList<MandamientoPersonaDTO>();

		for (MandamientoPersonaDTO mandamientoPersonaRemoto : mandamientoRemoto
				.getMandamientosPersona()) {

			if (mandamientoPersonaRemoto.getFolioInterInstitucional() == null
					|| mandamientoPersonaRemoto.getFolioInterInstitucional()
							.isEmpty()
					|| mandamientoPersonaRemoto.getEstatus() == null
					|| mandamientoPersonaRemoto.getEstatus().getIdCampo() == null) {
				logger.error("MANDAMIENTO PERSONA, SIN FOLIO INTERINSTITUCIONAL");
				throw new NSJPNegocioException(
						CodigoError.PARAMETROS_INSUFICIENTES);
			}

			FiltroMandamientoPersonaDTO filtroMandamientoPersonaDTO = new FiltroMandamientoPersonaDTO();

			filtroMandamientoPersonaDTO
					.setFolioInterInstitucional(mandamientoPersonaRemoto
							.getFolioInterInstitucional());

			List<MandamientoPersona> listaEncontrados = mandamientoPersonaDAO
					.consultarMandamientosPersonaPorFiltro(filtroMandamientoPersonaDTO);

			if (listaEncontrados == null || listaEncontrados.isEmpty()) {
				logger.error("MANDAMIENTO PERSONA CON FOLIO:"
						+ mandamientoPersonaRemoto.getFolioInterInstitucional()
						+ "NO ENCONTRADO");
				throw new NSJPNegocioException(
						CodigoError.INFORMACION_PARAMETROS_ERRONEA);
			}

			MandamientoPersonaDTO mandamientoPersonaActualizar = MandamientoPersonaTransformer
					.transformar(listaEncontrados.get(0));

			// Actualizamos estatus y actualizamos fecha de ejecucion
			mandamientoPersonaActualizar.setEstatus(mandamientoPersonaRemoto
					.getEstatus());
			mandamientoPersonaActualizar
					.setFechaEjecucion(mandamientoPersonaRemoto
							.getFechaEjecucion());

			listaMandamientosPersonaLocal.add(mandamientoPersonaActualizar);
		}

		ConfInstitucion instActual = confInstitucionDAO
				.consultarIntitucionActual();

		if (instActual == null || instActual.getConfInstitucionId() == null) {
			logger.error("NO SE ENCONTRO LA INSTITUCION ACTUAL***");
			throw new NSJPNegocioException(CodigoError.FALLA_OPERACION_BD);
		}

		UsuarioDTO usuarioDTO = new UsuarioDTO();

		if (instActual.getConfInstitucionId().equals(Instituciones.PGJ.getValorId())) {
			/**
			 * Obtenemos el expediente y el funcionario a traves de la actividad
			 * del mandamiento guardado
			 */
			if (mandamiento.getActividad() == null
					|| mandamiento.getActividad().getExpediente() == null
					|| mandamiento.getActividad().getExpediente()
							.getExpedienteId() == null) {
				logger.error("NO SE ENCONTRO EL EXPEDIENTE RELACIONADO AL MANDAMIENTO***");
				throw new NSJPNegocioException(
						CodigoError.INFORMACION_PARAMETROS_ERRONEA);
			}

			// Obtener el NumeroExpediente para obtener el usuario
			NumeroExpediente numeroExpediente = numeroExpedienteDAO
					.obtenerNumeroExpediente(mandamiento.getActividad()
							.getExpediente().getExpedienteId(),
							Areas.UNIDAD_INVESTIGACION.parseLong());

			if (numeroExpediente == null
					|| numeroExpediente.getFuncionario() == null
					|| numeroExpediente.getFuncionario().getClaveFuncionario() == null) {
				logger.error("NO SE ENCONTRO EL NUMERO DE EXPEDIENTE O FUNCIONARIO***");
				throw new NSJPNegocioException(CodigoError.FALLA_OPERACION_BD);
			}

			usuarioDTO.setFuncionario(FuncionarioTransformer
					.transformarFuncionarioBasico(numeroExpediente
							.getFuncionario()));

			usuarioDTO.setAreaActual(new AreaDTO(Areas.UNIDAD_INVESTIGACION));

			mandamientoLocal.setExpedienteDTO(new ExpedienteDTO(mandamiento
					.getActividad().getExpediente().getExpedienteId()));

		} else if (instActual.getConfInstitucionId().equals(Instituciones.PJ.getValorId())) {

			usuarioDTO.setFuncionario(FuncionarioTransformer
					.transformarFuncionarioBasico(mandamiento.getActividad()
							.getFuncionario()));
			usuarioDTO.setAreaActual(new AreaDTO(Areas.DEPARTAMENTO_CAUSA));

			mandamientoLocal.setUsuario(usuarioDTO);

			Expediente expediente = mandamiento.getResolutivo().getAudiencia()
					.getExpediente();

			mandamientoLocal.setExpedienteDTO(new ExpedienteDTO(expediente
					.getExpedienteId()));
		}

		/**
		 * Invocamos actualizar mandamiento y mandamientos persona,
		 */
		Long idDocumentoCamnbio = administrarMandamientoJudicialService
				.actualizarMandamientoPersona(listaMandamientosPersonaLocal,
						usuarioDTO, mandamientoLocal, documentoCambioEstatus);

		if (idDocumentoCamnbio == null || idDocumentoCamnbio < 0L) {
			throw new NSJPNegocioException(
					CodigoError.EJCUCION_OPERACION_ESTADO_INCORRECTO);
		}
	}

}
