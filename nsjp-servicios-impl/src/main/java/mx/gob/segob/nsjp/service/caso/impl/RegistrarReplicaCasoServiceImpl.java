/**
 * Nombre del Programa : RegistrarReplicaCasoServiceImpl.java
 * Autor                            : vaguirre
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 21 Aug 2011
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
package mx.gob.segob.nsjp.service.caso.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.caso.CasoDAO;
import mx.gob.segob.nsjp.dao.expediente.ExpedienteDAO;
import mx.gob.segob.nsjp.dao.expediente.NumeroExpedienteDAO;
import mx.gob.segob.nsjp.dto.caso.CasoDTO;
import mx.gob.segob.nsjp.dto.caso.CasoWSDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatDiscriminanteDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatDistritoDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoWSDTO;
import mx.gob.segob.nsjp.model.Caso;
import mx.gob.segob.nsjp.model.CatDiscriminante;
import mx.gob.segob.nsjp.model.ConfInstitucion;
import mx.gob.segob.nsjp.model.Expediente;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.service.caso.RegistrarReplicaCasoService;
import mx.gob.segob.nsjp.service.caso.impl.transform.CasoTransformer;
import mx.gob.segob.nsjp.service.catalogo.ConsultarDiscriminanteService;
import mx.gob.segob.nsjp.service.delito.GuardarDelitoService;
import mx.gob.segob.nsjp.service.expediente.impl.transform.ExpedienteTransformer;
import mx.gob.segob.nsjp.service.infra.impl.transform.enviarReplicaCaso.InvolucradoWSDTOTransformer;
import mx.gob.segob.nsjp.service.involucrado.IngresarIndividuoService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Describir el objetivo de la clase con punto al final.
 * 
 * @version 1.0
 * @author vaguirre
 * 
 */
@Service("registrarReplicaCasoService")
@Transactional
public class RegistrarReplicaCasoServiceImpl implements
		RegistrarReplicaCasoService {
	/**
	 * Logger
	 */
	private final static Logger logger = Logger
			.getLogger(RegistrarReplicaCasoServiceImpl.class);

	@Autowired
	private IngresarIndividuoService individuoService;
	@Autowired
	private GuardarDelitoService guardarDelitoService;

	@Autowired
	private CasoDAO casoDao;
	@Autowired
	private ExpedienteDAO expedienteDao;
	@Autowired
	private ConsultarDiscriminanteService consultarDiscriminanteService;
	@Autowired
	private NumeroExpedienteDAO numeroExpedienteDAO;
	
	@Override
	public void registraReplicaCaso(CasoWSDTO casoWS)
			throws NSJPNegocioException {

		if (casoWS == null || casoWS.getNumeroGeneralCaso() == null
				|| casoWS.getNumeroGeneralCaso().isEmpty()) {
			logger.error("FALTA CASO, O NUMERO DE CASO");
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		logger.info("Inicia registraReplicaCaso(...)");
		final Caso rowExistente = this.casoDao
				.obtenerCasoByNumeroGeneral(casoWS.getNumeroGeneralCaso());

		if (rowExistente != null) {
			logger.error("CASO EXISTENTE!!!!");
			
			//Actualizar el distrito solo si viene y es para Defensoria
			if (casoWS.getDistrito() != null) {
				this.actualizarDiscriminanteDistritoExpedienteDefensoria(casoWS
						.getDistrito().getClaveDistrito(), casoWS
						.getNumeroGeneralCaso(), rowExistente.getCasoId());
			}
			return;
		}
		
		final Caso casoNuevo = new Caso();
		casoNuevo.setNumeroGeneralCaso(casoWS.getNumeroGeneralCaso());
		casoNuevo.setFechaApertura(casoWS.getFechaApertura());

		casoDao.create(casoNuevo);

		final Expediente expNuevo = new Expediente();
		
		expNuevo.setCaso(casoNuevo);
		expNuevo.setFechaCreacion(new Date());
		
		ConfInstitucion confInstitucion = casoDao.consultarInsitucionActual();
		//Se asocia el discriminante para DEFENSORIA
		if (confInstitucion != null
				&& confInstitucion.getConfInstitucionId().equals(
						Instituciones.DEF.getValorId())
				&& casoWS.getDistrito() != null) {
			CatDiscriminanteDTO catDiscriminanteDTO = consultarDiscriminanteService
					.buscarDiscrimianterPorClaveDistritoDefensoria(casoWS
							.getDistrito().getClaveDistrito());
			if (catDiscriminanteDTO != null
					&& catDiscriminanteDTO.getCatDiscriminanteId() != null
					&& catDiscriminanteDTO.getCatDiscriminanteId() > 0L) {

				expNuevo.setDiscriminante(new CatDiscriminante(
						catDiscriminanteDTO.getCatDiscriminanteId()));
			}
		}
		
		final Long expNuevoId = this.expedienteDao.create(expNuevo);
		
		expNuevo.setExpedienteId(expNuevoId);

		final ExpedienteDTO expDto = new ExpedienteDTO(expNuevoId);
		InvolucradoDTO involucradoDTO = null;
		List<DelitoDTO> listaDelitosExpediente = new ArrayList<DelitoDTO>();
		for (InvolucradoWSDTO involucradoWSDTO : casoWS.getInvolucradosDTO()) {

			involucradoDTO = new InvolucradoDTO();

			involucradoDTO = InvolucradoWSDTOTransformer
					.transformar(involucradoWSDTO);
			involucradoDTO.setExpedienteDTO(expDto);

			Long idIndividuo = individuoService
					.ingresarIndividuoInterInstitucion(involucradoDTO, true);

			logger.info("Se ingreso un individuo con id: " + idIndividuo);
			if (involucradoDTO.getDelitosCometidos() != null
					&& !involucradoDTO.getDelitosCometidos().isEmpty()) {
				listaDelitosExpediente.addAll(involucradoDTO
						.getDelitosCometidos());
			}
		}

		/*
		 * Guardamos los delitos cometidos, en el expediente, si los delitos ya
		 * existen el servicio ya nos los repite
		 */
		guardarDelitoService.guardarDelitoExpedienteInterinstitucional(
				listaDelitosExpediente, expDto);

		logger.info("Fin registraReplicaCaso(...)");
	}
	
	@Override
	public CasoDTO registrarCasoExpediente(CasoDTO casoDTO, CatDistritoDTO catDistritoDTO)
			throws NSJPNegocioException {

		logger.info("Inicia registrarCasoExpediente(...)");
		if (casoDTO == null || casoDTO.getNumeroGeneralCaso() == null
				|| casoDTO.getNumeroGeneralCaso().trim().isEmpty()
				|| casoDTO.getFechaApertura() == null) {
			logger.debug("No se cuenta con los datos suficiente para el caso!!");
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		List<ExpedienteDTO> expedientesDTO = null;

		// Verificar la existencia del caso
		Caso casoBD = this.casoDao.obtenerCasoByNumeroGeneral(casoDTO
				.getNumeroGeneralCaso());

		// Existe el caso
		if (casoBD != null) {
			logger.error("CASO EXISTENTE: " + casoBD.getCasoId() + " - "
					+ casoBD.getNumeroGeneralCaso());

			casoDTO = CasoTransformer.transformarCasoBasico(casoBD);

			// Obtener expedientes asociados
			List<Expediente> expedientesAsociados = this.expedienteDao
					.consultarExpedientesPorNumeroCaso(casoDTO
							.getNumeroGeneralCaso());

			// En caso de que no existan expedientes
			if (expedientesAsociados == null || expedientesAsociados.isEmpty()) {
				logger.error("El caso:" + casoDTO.getNumeroGeneralCaso()
						+ " no tiene expediente asociado!");
				ExpedienteDTO expedienteDTO = this
						.crearExpedienteConCaso(casoBD, catDistritoDTO);
				expedientesDTO = new ArrayList<ExpedienteDTO>();
				expedientesDTO.add(expedienteDTO);
			} else {
				//Existen expedientes
				// Actualizar el distrito solo si viene y es para Defensoria
				if (catDistritoDTO != null) {
					this.actualizarDiscriminanteDistritoExpedienteDefensoria(
							catDistritoDTO.getClaveDistrito(),
							casoBD.getNumeroGeneralCaso(), casoBD.getCasoId());
				}
				
				expedientesDTO = ExpedienteTransformer
						.transformarExpedientesBasico(expedientesAsociados);
			}
			casoDTO.setExpedintesDTO(expedientesDTO);
		} else {
			logger.error("CASO INEXISTENTE!!!!");
			Caso casoNuevo = new Caso();
			casoNuevo.setNumeroGeneralCaso(casoDTO.getNumeroGeneralCaso());
			casoNuevo.setFechaApertura(casoDTO.getFechaApertura());
			Long casoId = casoDao.create(casoNuevo);

			logger.debug("Caso Creado:" + casoId + " - "
					+ casoNuevo.getNumeroGeneralCaso());

			ExpedienteDTO expedienteDTO = this
					.crearExpedienteConCaso(casoNuevo, catDistritoDTO);

			expedientesDTO = new ArrayList<ExpedienteDTO>();
			expedientesDTO.add(expedienteDTO);

			casoDTO.setCasoId(casoNuevo.getCasoId());
			casoDTO.setExpedintesDTO(expedientesDTO);
		}

		logger.info("Finaliza registrarCasoExpediente(...)" + casoDTO);
		return casoDTO;
	}

	/**
	 * M&eacute;todo que se encarga de crear un expediente, asociado al caso que
	 * es pasado como par&aacute;metro.
	 * 
	 * @param casoNuevo
	 * @param catDistritoDTO  distrito para defensoria
	 * @return
	 */
	private ExpedienteDTO crearExpedienteConCaso(Caso casoNuevo,
			CatDistritoDTO catDistritoDTO) throws NSJPNegocioException {
		ExpedienteDTO expedienteDTO = null;

		if (casoNuevo == null || casoNuevo.getCasoId() == null) {
			return expedienteDTO;
		}

		Expediente expNuevo = new Expediente();

		expNuevo.setCaso(casoNuevo);
		expNuevo.setFechaCreacion(new Date());

		ConfInstitucion confInstitucion = casoDao.consultarInsitucionActual();
		
		//Se asocia el discriminante para DEFENSORIA
		if (confInstitucion != null
				&& confInstitucion.getConfInstitucionId().equals(
						Instituciones.DEF.getValorId())
				&& catDistritoDTO!=null ) {
			CatDiscriminanteDTO catDiscriminanteDTO = consultarDiscriminanteService
					.buscarDiscrimianterPorClaveDistritoDefensoria(catDistritoDTO.getClaveDistrito());
			if (catDiscriminanteDTO != null
					&& catDiscriminanteDTO.getCatDiscriminanteId() != null
					&& catDiscriminanteDTO.getCatDiscriminanteId() > 0L) {

				expNuevo.setDiscriminante(new CatDiscriminante(
						catDiscriminanteDTO.getCatDiscriminanteId()));
			}
		}
		
		Long expNuevoId = this.expedienteDao.create(expNuevo);
		logger.debug("Expediente Creado:" + expNuevoId);

		expedienteDTO = ExpedienteTransformer
				.transformarExpedienteBasico(expNuevo);
		return expedienteDTO;
	}
	
	/**
	 * Servicio que se encarga de actualizar el Discriminante(Distrito) 
	 * de los expedientes asociados al caso.
	 * Solo aplica para Defensoria
	 * 
	 * @param claveDistrito
	 * @param numeroGeneralCaso
	 * @param casoId
	 * @throws NSJPNegocioException
	 */
	private void actualizarDiscriminanteDistritoExpedienteDefensoria(
			String claveDistrito, String numeroGeneralCaso, Long casoId)
			throws NSJPNegocioException {
		ConfInstitucion confInstitucion = casoDao.consultarInsitucionActual();

		// Actualizar el Discriminante solo DEFENSORIA
		if (confInstitucion != null
				&& confInstitucion.getConfInstitucionId().equals(
						Instituciones.DEF.getValorId())
				&& claveDistrito != null && !claveDistrito.trim().isEmpty()
				&& numeroGeneralCaso != null
				&& !numeroGeneralCaso.trim().isEmpty()) {

			// Verificar si no se hen creado Numeros de Expedientes
			List<NumeroExpediente> numerosExpediente = numeroExpedienteDAO
					.consultarNumeroExpedientePorNumeroCaso(numeroGeneralCaso);
			// No existen Numero de Expediente, se actualiza el
			// discriminante
			if (numerosExpediente == null || numerosExpediente.isEmpty()) {
				
				CatDiscriminanteDTO catDiscriminanteDTO = consultarDiscriminanteService
						.buscarDiscrimianterPorClaveDistritoDefensoria(claveDistrito);
				
				if (catDiscriminanteDTO != null
						&& catDiscriminanteDTO.getCatDiscriminanteId() != null
						&& catDiscriminanteDTO.getCatDiscriminanteId() > 0L) {
					
					List<Expediente> expedientes = expedienteDao
							.consultarCausasPorIdCaso(casoId);
					for (Expediente expediente : expedientes) {

						expediente.setDiscriminante(new CatDiscriminante(
								catDiscriminanteDTO.getCatDiscriminanteId()));
						expedienteDao.update(expediente);
					}
				}
			}
		}
	}
}
