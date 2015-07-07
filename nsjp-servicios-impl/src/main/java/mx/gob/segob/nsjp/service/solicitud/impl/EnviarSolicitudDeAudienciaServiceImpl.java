/**
 * Nombre del Programa : EnviarSolicitudDeAudienciaServiceImpl.java
 * Autor                            : Jacob Lobaco
 * Compania                         : Ultrasist
 * Proyecto                         : NSJP                    Fecha: 14-jul-2011
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
package mx.gob.segob.nsjp.service.solicitud.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.catalogo.ValorDAO;
import mx.gob.segob.nsjp.dto.expediente.DelitoPersonaDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudAudienciaDTO;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.delito.ConsultarDelitoPersonaService;
import mx.gob.segob.nsjp.service.expediente.BuscarExpedienteService;
import mx.gob.segob.nsjp.service.infra.PJClienteService;
import mx.gob.segob.nsjp.service.involucrado.ConsultarIndividuoService;
import mx.gob.segob.nsjp.service.solicitud.EnviarSolicitudDeAudienciaService;
import mx.gob.segob.nsjp.service.solicitud.RegistrarSolicitudService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @version 1.0
 * @author Jacob Lobaco
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class EnviarSolicitudDeAudienciaServiceImpl implements
		EnviarSolicitudDeAudienciaService {

	/**
	 * Logger de la clase.
	 */
	private final static Logger logger = Logger
			.getLogger(EnviarSolicitudDeAudienciaServiceImpl.class);

	@Autowired
	private PJClienteService clientePJWebService;

	@Autowired
	private RegistrarSolicitudService registrarSolicitudService;

	@Autowired
	private BuscarExpedienteService expedienteService;
	
	@Autowired
	private ConsultarDelitoPersonaService consultarDelitoPersonaService;
	
	@Autowired
	private ValorDAO valorDAO;
	
	@Autowired
	private ConsultarIndividuoService consultarIndividuoService;

	public SolicitudAudienciaDTO enviarSolicitudDeAudiencia(
			SolicitudAudienciaDTO solicitudAudienciaDto,
			ExpedienteDTO expedienteInput, Long idDistrito, Long idTribunal,
			Long idClaveFuncionario) throws NSJPNegocioException {

		logger.info("INICIA - enviarSolicitudDeAudiencia(...)");

		if (solicitudAudienciaDto == null
				|| expedienteInput == null
				|| idDistrito == null
				|| idTribunal == null
				|| idClaveFuncionario == null
				|| expedienteInput.getNumeroExpedienteId() == null
				|| expedienteInput.getNumeroExpedienteId() <= 0L
				|| expedienteInput.getUsuario() == null
				|| expedienteInput.getUsuario().getFuncionario() == null
				|| solicitudAudienciaDto.getAudiencia() == null
				|| solicitudAudienciaDto.getAudiencia().getInvolucrados() == null
				|| solicitudAudienciaDto.getAudiencia().getInvolucrados()
						.isEmpty()
				|| solicitudAudienciaDto.getAudiencia().getTipoAudiencia() == null
				|| solicitudAudienciaDto.getAudiencia().getTipoAudiencia()
						.getIdCampo() == null
				|| solicitudAudienciaDto.getAudiencia().getTipoAudiencia()
						.getIdCampo() <= 0L) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		/*
		 * Se obtiene el expediente en base al numero de expediente por default
		 * NO se pide que se consulten:
		 * expParam.setInvolucradosSolicitados(false);
		 * expParam.setDomiciliosInvolucradoSolicitados(false);
		 * expParam.setObjetosSolicitados(false);
		 */
		final ExpedienteDTO expComplete = this.expedienteService
				.obtenerExpediente(expedienteInput);

		if (expComplete == null) {
			logger.error("No existe el expediente con num de exp:"
					+ expedienteInput.getNumeroExpedienteId());
			throw new NSJPNegocioException(
					CodigoError.INFORMACION_PARAMETROS_ERRONEA);
		}

		expComplete.setUsuario(expedienteInput.getUsuario());
		solicitudAudienciaDto.setExpedienteDTO(expedienteInput);

		/*
		 * Permite registrar el tipo de audiencia en la descripci&oacute;n de una
		 * solicitud, solo para datos del historial de un expediente
		 */
		Valor loTipoSolicitud = valorDAO.read(solicitudAudienciaDto
				.getAudiencia().getTipoAudiencia().getIdCampo());

		if (loTipoSolicitud != null) {
			solicitudAudienciaDto.setObservaciones(loTipoSolicitud.getValor());
		}

		List<InvolucradoDTO> listaProbablesResponsablesDTO = new ArrayList<InvolucradoDTO>();

		// Consultamos todos los datos de los probables responsables
		for (InvolucradoDTO probableResponsable : solicitudAudienciaDto
				.getAudiencia().getInvolucrados()) {
			probableResponsable.setFotoElementoSolicitado(false);
			listaProbablesResponsablesDTO.add(consultarIndividuoService
					.obtenerInvolucrado(probableResponsable));
		}

		if (listaProbablesResponsablesDTO.isEmpty()) {
			logger.error("El expediente no tiene estos probables responsables:");
			throw new NSJPNegocioException(
					CodigoError.INFORMACION_PARAMETROS_ERRONEA);
		}

		
		expComplete.setInvolucradosDTO(listaProbablesResponsablesDTO);

		/*
		 * Consultamos la relaci&oacute;n delito persona y obtenemos las
		 * v&iacute;ctimas
		 */
		List<DelitoPersonaDTO> relacionesDelitoPersona = consultarDelitoPersonaService
				.consultarDelitosVictimaImputadoPorExpediente(expComplete);

		if (relacionesDelitoPersona == null
				|| relacionesDelitoPersona.isEmpty()) {
			logger.error("No existen relaciones delito-persona en el expediente para los Prob Resp:");
			throw new NSJPNegocioException(
					CodigoError.INFORMACION_PARAMETROS_ERRONEA);
		}

		HashMap<String, Long> hashVictimas = new HashMap<String, Long>();
		List<InvolucradoDTO> listaVictimasDTO = new ArrayList<InvolucradoDTO>();

		/*
		 * Obtenemos las v&iacute;ctimas y las guardamos en el la lista de v&iacute;ctimas sin
		 * que se repitan.
		 */
		for (DelitoPersonaDTO relDelPer : relacionesDelitoPersona) {

			if (!hashVictimas.containsKey(relDelPer.getVictima()
					.getFolioElemento())) {

				InvolucradoDTO victimaDto = new InvolucradoDTO(relDelPer
						.getVictima().getElementoId());
				listaVictimasDTO.add(consultarIndividuoService
						.obtenerInvolucrado(victimaDto));

				hashVictimas.put(relDelPer.getVictima().getFolioElemento(),
						relDelPer.getVictima().getElementoId());
			}
		}

		//Agregamos las v&iacute;ctimas al expediente para que se env&iacute;en
		expComplete.getInvolucradosDTO().addAll(listaVictimasDTO);
		
		registrarSolicitudService.registrarSolicitud(solicitudAudienciaDto);
		
		SolicitudAudienciaDTO solicitudAudiencia = clientePJWebService
				.enviarSolicitudAudiencia(solicitudAudienciaDto, expComplete,
						idDistrito, idTribunal, idClaveFuncionario,
						relacionesDelitoPersona);
		
		if (solicitudAudiencia == null
				|| solicitudAudiencia.getDocumentoId() == null
				|| solicitudAudiencia.getDocumentoId() <= 0L) {
			logger.error("NO SE HA PODIDO GENERAR LA SOLUCITUD****");
			throw new NSJPNegocioException(
					CodigoError.IMPOSIBLE_GENERAR_SOLICITUD_DE_AUDIENCIA);
		}
	

		logger.info(" ENVIAR LA SOLICITUD: " + " idDistrito:" + idDistrito
				+ " idTribunal:" + idTribunal + " idClaveFuncionario:"
				+ idClaveFuncionario);

		logger.info("Fin - enviarSolicitudDeAudiencia(...)");

		return solicitudAudiencia;
	}
}
