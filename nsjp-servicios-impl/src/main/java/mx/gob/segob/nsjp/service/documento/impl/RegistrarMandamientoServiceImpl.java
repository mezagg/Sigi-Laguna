/**
 * Nombre del Programa : RegistrarMandamientoServiceImpl.java
 * Autor                            : vaguirre
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 7 Sep 2011
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

import mx.gob.segob.nsjp.comun.enums.actividad.Actividades;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.institucion.Areas;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.caso.CasoDAO;
import mx.gob.segob.nsjp.dao.expediente.NumeroExpedienteDAO;
import mx.gob.segob.nsjp.dao.involucrado.InvolucradoDAO;
import mx.gob.segob.nsjp.dto.ActividadDTO;
import mx.gob.segob.nsjp.dto.caso.CasoDTO;
import mx.gob.segob.nsjp.dto.documento.MandamientoDTO;
import mx.gob.segob.nsjp.dto.documento.MandamientoWSDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoPersonaDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.institucion.AreaDTO;
import mx.gob.segob.nsjp.dto.mandamiento.MandamientoPersonaDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.model.Caso;
import mx.gob.segob.nsjp.model.Involucrado;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.service.delito.ConsultarDelitoPersonaService;
import mx.gob.segob.nsjp.service.documento.RegistrarMandamientoService;
import mx.gob.segob.nsjp.service.expediente.BuscarExpedienteService;
import mx.gob.segob.nsjp.service.infra.impl.transform.registrarmandamiento.MandamientoWSDTOTransformer;
import mx.gob.segob.nsjp.service.mandamiento.AdministrarMandamientoJudicialService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementacion del servicio para registrar el mandamiento enviado por PJ.
 * 
 * @version 1.0
 * @author vaguirre
 * 
 */
@Service("registrarMandamientoService")
@Transactional (propagation = Propagation.REQUIRED)
public class RegistrarMandamientoServiceImpl implements
		RegistrarMandamientoService {
	/**
	 * Logger.
	 */
	private final static Logger logger = Logger
			.getLogger(RegistrarMandamientoServiceImpl.class);
	
	@Autowired
	private CasoDAO casoDAO;
	@Autowired
	private BuscarExpedienteService buscarExpedienteService;
	@Autowired
	private InvolucradoDAO involucradoDAO;
	@Autowired
	private NumeroExpedienteDAO numeroExpedienteDAO;
	@Autowired
	private ConsultarDelitoPersonaService consultarDelitoPersonaService;
	@Autowired
	private AdministrarMandamientoJudicialService administrarMandamientoJudicialService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.gob.segob.nsjp.service.documento.RegistrarMandamientoService#
	 * registrarMandamiento(mx.gob.segob.nsjp.dto.documento.MandamientoWSDTO)
	 */
	@Override
	public void registrarMandamiento(MandamientoWSDTO mandamientoWSDTO)
			throws NSJPNegocioException {
		
		if (logger.isDebugEnabled()) {
			logger.debug("/**** SERVICIO PARA REGISTRAR MANDAMIENTO JUDICIAL EN PG ****/");
		}

		if (mandamientoWSDTO == null
				|| mandamientoWSDTO.getNumeroCaso() == null
				|| mandamientoWSDTO.getArchivoDigital() == null
				|| mandamientoWSDTO.getConfInstitucionId() == null
				|| mandamientoWSDTO.getFechaCreacion() == null
				|| mandamientoWSDTO.getFolioDocumento() == null
				|| mandamientoWSDTO.getIdTipoMandamiento() == null
				|| mandamientoWSDTO.getNumeroCausa() == null
				|| mandamientoWSDTO.getDelitosPersona() == null
				|| mandamientoWSDTO.getDelitosPersona().isEmpty()) {
			logger.error("MANDAMIENTO_WSDTO CON PARAMETROS INSUFICIENTES***");
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		Caso casoBD = casoDAO.consultarCasoPorNumeroCaso(mandamientoWSDTO
				.getNumeroCaso());
		
		if(casoBD == null || casoBD.getCasoId() == null){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		//Buscar el expediente del Area de unidad de investigacion
		CasoDTO casoDTO = new CasoDTO();
		casoDTO.setCasoId(casoBD.getCasoId());

		UsuarioDTO usuarioDTOFiltro = new UsuarioDTO();
		usuarioDTOFiltro.setAreaActual(new AreaDTO(Areas.UNIDAD_INVESTIGACION));
		casoDTO.setUsuario(usuarioDTOFiltro);

		List<ExpedienteDTO> respuesta = buscarExpedienteService
		.consultarExpedientesPorCaso(casoDTO);
		
		if (respuesta == null || respuesta.isEmpty()
				|| respuesta.get(0) == null
				|| respuesta.get(0).getNumeroExpedienteId() == null
				|| respuesta.get(0).getExpedienteId() == null) {
			logger.error("NO SE ENCONTRARON EXPEDIENTE DEL AREA UI***");
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		ExpedienteDTO expedienteObtenido = new ExpedienteDTO();
		
		// Obtener el Numero de Expediente Id a asociar
		expedienteObtenido.setNumeroExpedienteId(respuesta.get(0)
				.getNumeroExpedienteId());
		expedienteObtenido.setExpedienteId(respuesta.get(0).getExpedienteId());
		
		logger.debug("Se obtuvo el expediente con id:: "
				+ expedienteObtenido.getExpedienteId());
		
		//Obtener el NumeroExpediente para obtener el usuario
		NumeroExpediente numeroExpediente = numeroExpedienteDAO
				.read(expedienteObtenido.getNumeroExpedienteId());
		
		if(numeroExpediente == null || numeroExpediente.getFuncionario() == null){
			logger.error("NO SE ENCONTRO EL NUMERO DE EXPEDIENTE O FUNCIONARIO***");
			throw new NSJPNegocioException(CodigoError.FALLA_OPERACION_BD);
		}
		
		//Mandamiento que sera guardado
		MandamientoDTO mandamientoDTO = MandamientoWSDTOTransformer.Transformar(mandamientoWSDTO);
		
		/**
		 * Seteamos el funcionario
		 */
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		usuarioDTO.setFuncionario(new FuncionarioDTO(numeroExpediente.getFuncionario().getClaveFuncionario()));
		mandamientoDTO.setUsuario(usuarioDTO);
		
		/**
		 * Seteamos el expediente obtenido del caso
		 */
		mandamientoDTO.setExpedienteDTO(expedienteObtenido);
		
		/**
		 * Obtenemos id de las relaciones delitos persona
		 * y los id de los probables responsables
		 */
		//Lista con los valores de ids
		List<DelitoPersonaDTO> listaRelacionesDelitoPersonaObtenida = new ArrayList<DelitoPersonaDTO>();
		
		for (DelitoPersonaDTO delitoPersonaDto : mandamientoDTO
				.getDelitosPersona()) {

			logger.info("Buscando delito persona con folio:"
					+ delitoPersonaDto.getFolioDelitoPersona());

			delitoPersonaDto = consultarDelitoPersonaService
					.consultarUnicaRelacionDelitoPersonaPorFolio(delitoPersonaDto);
			
			if (delitoPersonaDto == null) {
				logger.error("NO SE ENCONTRO LA RELACION DELITO PERSONA***");
				throw new NSJPNegocioException(CodigoError.INFORMACION_PARAMETROS_ERRONEA);
			}
			
			listaRelacionesDelitoPersonaObtenida.add(delitoPersonaDto);
		}
		mandamientoDTO.getDelitosPersona().clear();
		mandamientoDTO.setDelitosPersona(listaRelacionesDelitoPersonaObtenida);
		
		/**
		 * Obtenemos el id de los PR a partir de su folio en mandamientoPersona
		 */
		//Lista con los valores de ids
		List<MandamientoPersonaDTO> listaMandamientoPersonaObtenida = new ArrayList<MandamientoPersonaDTO>();
		
		for (MandamientoPersonaDTO mandamientoPersonaDto : mandamientoDTO
				.getMandamientosPersona()) {

			Involucrado involucrado = involucradoDAO
					.consultarInvolucradoPorFolioElemento(mandamientoPersonaDto
							.getPersona().getFolioElemento());
			if (involucrado == null) {
				logger.error("NO SE ENCONTRO EL INVOLUCRADO CON FOLIO***"
						+ mandamientoPersonaDto.getPersona().getFolioElemento());
				throw new NSJPNegocioException(
						CodigoError.INFORMACION_PARAMETROS_ERRONEA);
			}
			mandamientoPersonaDto.getPersona().setElementoId(
					involucrado.getElementoId());
			
			listaMandamientoPersonaObtenida.add(mandamientoPersonaDto);
		}
		mandamientoDTO.getMandamientosPersona().clear();
		mandamientoDTO.setMandamientosPersona(listaMandamientoPersonaObtenida);
		
		/**
		 * obetener la actividadId
		 */
		ActividadDTO actividadDTO = new ActividadDTO();
		
		actividadDTO.setActividadId(Actividades.GENERAR_MANDAMIENTO_JUDICIAL.getValorId());
		mandamientoDTO.setActividadDTO(actividadDTO);
		
		administrarMandamientoJudicialService.generarMandamientoJudicial(mandamientoDTO);
		
	}
}
