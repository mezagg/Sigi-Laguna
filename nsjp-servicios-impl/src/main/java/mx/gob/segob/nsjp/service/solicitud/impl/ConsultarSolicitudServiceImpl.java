/**
 * Nombre del Programa : ConsultarSolicitudServiceImpl.java
 * Autor                            : vaguirre
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 16 Jun 2011
 * Marca de cambio        : N/A
 * Descripcion General    : Implementaci�n para la consulta de las solicitudes
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
package mx.gob.segob.nsjp.service.solicitud.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.institucion.Areas;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud;
import mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.caso.CasoDAO;
import mx.gob.segob.nsjp.dao.documento.AvisoDesignacionDAO;
import mx.gob.segob.nsjp.dao.expediente.ExpedienteDAO;
import mx.gob.segob.nsjp.dao.expediente.NumeroExpedienteDAO;
import mx.gob.segob.nsjp.dao.institucion.JerarquiaOrganizacionalDAO;
import mx.gob.segob.nsjp.dao.solicitud.SolicitudDAO;
import mx.gob.segob.nsjp.dao.solicitud.SolicitudMandamientoDAO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.documento.AvisoDesignacionDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDefensorDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudMandamientoDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.model.AvisoDesignacion;
import mx.gob.segob.nsjp.model.Caso;
import mx.gob.segob.nsjp.model.ConfInstitucion;
import mx.gob.segob.nsjp.model.Expediente;
import mx.gob.segob.nsjp.model.JerarquiaOrganizacional;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.model.Solicitud;
import mx.gob.segob.nsjp.model.SolicitudDefensor;
import mx.gob.segob.nsjp.model.SolicitudMandamiento;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.documento.impl.tranform.AvisoDesignacionTransformer;
import mx.gob.segob.nsjp.service.expediente.impl.transform.ExpedienteTransformer;
import mx.gob.segob.nsjp.service.solicitud.ConsultarSolicitudService;
import mx.gob.segob.nsjp.service.solicitud.impl.transform.ConfInstitucionTransformer;
import mx.gob.segob.nsjp.service.solicitud.impl.transform.SolicitudAudienciaTransformer;
import mx.gob.segob.nsjp.service.solicitud.impl.transform.SolicitudDefensorTransformer;
import mx.gob.segob.nsjp.service.solicitud.impl.transform.SolicitudTransformer;
import mx.gob.segob.nsjp.service.usuario.impl.transformer.ValorTransformer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementaci�n para la consulta de las solicitudes.
 * 
 * @version 1.0
 * @author vaguirre
 * 
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class ConsultarSolicitudServiceImpl implements ConsultarSolicitudService {
	private final static Logger LOGGER = Logger
			.getLogger(ConsultarSolicitudServiceImpl.class);
	@Autowired
	private SolicitudDAO solDao;
	@Autowired
	private ExpedienteDAO expDAO;
	@Autowired
	private NumeroExpedienteDAO numExpDAO;
	@Autowired
	private CasoDAO casoDAO;
	@Autowired
	private JerarquiaOrganizacionalDAO jerarquiaOrganizacionalDAO;
	@Autowired
	private AvisoDesignacionDAO avisoDesignacionDAO;
	@Autowired
	private SolicitudMandamientoDAO solicitudMandamientoDAO;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.gob.segob.nsjp.service.solicitud.ConsultarSolicitudService#
	 * conultarSolicitud(mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO)
	 */
	@Override
	public List<SolicitudDTO> consultarSolicitudesPorExpediente(
			ExpedienteDTO filtro) throws NSJPNegocioException {
		LOGGER.debug("filtro.getNumeroExpedienteId() :: "
				+ filtro.getNumeroExpedienteId());
		List<Solicitud> fromBD = this.solDao
				.consultarSolicitudesPorExpediente(filtro
						.getNumeroExpedienteId());

		return SolicitudAudienciaTransformer.transformarSolicitudes(fromBD);
	}
    
	@Override
	public SolicitudDTO obtenerSolicitud(SolicitudDTO sol)
			throws NSJPNegocioException {
		Solicitud solicitud;
		long id = sol.getDocumentoId();
        LOGGER.debug("solicitudDTO.getDocumentoId() :: " + id);
		solicitud = solDao.consultarSolicitudPorDocumentoId(id);
		Expediente expeTemp = solicitud.getExpediente();
		String numeroExpStr="";
		ExpedienteDTO expe=null;
		boolean op=false;
		if(solicitud.getExpediente()!=null){
		NumeroExpediente numeroExp=numExpDAO.obtenerNumeroExpedienteXExpediente(solicitud.getExpediente().getExpedienteId());
		
		expeTemp.setNumeroExpediente(numeroExp.getNumeroExpediente());
		}else if(solicitud.getNumeroCasoAsociado()!=null){
			Caso caso=casoDAO.consultarCasoPorNumeroCaso(solicitud.getNumeroCasoAsociado());
			List<Expediente> listExp=expDAO.consultarExpedientesPorIdCaso(caso.getCasoId(), Areas.UNIDAD_INVESTIGACION.parseLong());
			if(!listExp.isEmpty()){
				NumeroExpediente numeroExpediente=numExpDAO.obtenerNumeroExpediente(listExp.get(0).getExpedienteId(), Areas.UNIDAD_INVESTIGACION.parseLong());
				if(numeroExpediente!=null && numeroExpediente.getNumeroExpediente()!=null){
					numeroExpStr=numeroExpediente.getNumeroExpediente();
					expe=ExpedienteTransformer.transformaExpediente(listExp.get(0));
					 op=true;
				}
			}
		}
		
		if (solicitud instanceof SolicitudDefensor) {
			LOGGER.debug("/**** SOLICITUD DEFENSORIA ****/");
			SolicitudDefensorDTO solDef=SolicitudDefensorTransformer.transformarSolicitudDefensoria((SolicitudDefensor)solicitud);
			if(op){
				solDef.setExpedienteDTO(expe);
				solDef.getExpedienteDTO().setNumeroExpediente(numeroExpStr);
			}
			return solDef;
		}
		SolicitudDTO solicitudDTO=SolicitudTransformer.solicitudTransformer(solicitud);
		if(op){
			solicitudDTO.setExpedienteDTO(expe);
			solicitudDTO.getExpedienteDTO().setNumeroExpediente(numeroExpStr);
		}
		if(solicitudDTO.getTipoSolicitudDTO()!=null 
				&& solicitudDTO.getTipoSolicitudDTO().getIdCampo().equals(TiposSolicitudes.CARPETA_DE_INVESTIGACION.getValorId())){
			solicitudDTO.setInstitucion(ConfInstitucionTransformer.transformarInstitucion(expDAO.consultarInsitucionActual()));
		}
		return solicitudDTO;
	}
	/*
	 * (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.solicitud.ConsultarSolicitudService#consultarSolicitudesPorNumeroExpedienteYTipo(java.lang.Long, mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes)
	 */
	@Override
	public List<SolicitudDTO> consultarSolicitudesPorNumeroExpedienteYTipo(
			Long numeroExpedienteId, TiposSolicitudes tipo)
			throws NSJPNegocioException {
		List<SolicitudDTO> res = new ArrayList<SolicitudDTO>();
		List<Solicitud> solsBD = solDao.consultarSolicitudesPorNumeroExpedienteYTipo(numeroExpedienteId, tipo);
		for(Solicitud sol:solsBD){
			res.add(SolicitudTransformer.solicitudTransformer(sol));
		}
		return res;
	}
	/*
	 * (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.solicitud.ConsultarSolicitudService#consultarNumeroExpedienteDeSolicitud(java.lang.Long)
	 */
	@Override
	public Long consultarNumeroExpedienteDeSolicitud(Long solicitudId) {
		return solDao.consultarNumeroExpedienteDeSolicitud(solicitudId);
	}
	
	@Override
	public SolicitudDTO consultarDatosDeSolicitud(String folioSolicitud)
			throws NSJPNegocioException {		
		
		if( folioSolicitud == null || folioSolicitud.trim().isEmpty()){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		Solicitud solBD = solDao.obtenerSolicitudPorFolio(folioSolicitud);
		
		return SolicitudTransformer.solicitudTransformer(solBD);
	}
	
	@Override
	public List<SolicitudDTO> consultarSolicitudesGeneradas(List<Long> idEstatusSolicitud,
			List<Long> idTipoSolicitud, Long idAreaOrigen, Long idFuncionarioSolicitante ) throws NSJPNegocioException{
		LOGGER.info("Servicio para consultarSolicitudesGeneradas");
		
		LOGGER.info("Lista idEstatusSolicitud:"+ idEstatusSolicitud + 
				"Lista idTipoSolicitud:"+ idTipoSolicitud + 
				"idAreaOrigen:"+ idAreaOrigen + 
				"idFuncionarioDestinatario "+ idFuncionarioSolicitante );
		
		//List<Solicitud> solicitudes = solDao.consultarSolicitudesGeneradas(idEstatusSolicitud, idTipoSolicitud, idAreaOrigen, idFuncionarioSolicitante );
		List<Solicitud> solicitudes = solDao.consultarSolicitudesGeneradasPorNumeroExpediente(idEstatusSolicitud, idTipoSolicitud, idAreaOrigen, idFuncionarioSolicitante,null);
		
		if (idTipoSolicitud != null && !idTipoSolicitud.isEmpty()
				&& idTipoSolicitud.size() == 1){
			Long tipoSolicitud = idTipoSolicitud.get(0);
			if (tipoSolicitud.equals(TiposSolicitudes.TRABAJO_SOCIAL.getValorId())
					|| tipoSolicitud.equals(TiposSolicitudes.ATENCION_JURIDICA.getValorId())
					|| tipoSolicitud.equals(TiposSolicitudes.ATENCION_PSICOLOGICA.getValorId())){
				for (Solicitud solicitud : solicitudes){
					if (solicitud.getNumeroExpediente() != null && solicitud.getNumeroExpediente().getExpediente() != null
							&& solicitud.getNumeroExpediente().getExpediente().getCaso() != null
							&& solicitud.getNumeroExpediente().getExpediente().getCaso().getCasoId() != null
							&& solicitud.getNumeroExpediente().getExpediente().getCaso().getCasoId() > 0){
						Long numeroCaso = solicitud.getNumeroExpediente().getExpediente().getCaso().getCasoId();
						if (idAreaOrigen != null & idAreaOrigen > 0 && idAreaOrigen.equals(Areas.COORDINACION_UNIDAD_INVESTIGACION.parseLong())){
							idAreaOrigen = Areas.UNIDAD_INVESTIGACION.parseLong();
						}
						String numeroDeCaso = numExpDAO.consultarNumeroExpedienteXCasoXJerarquia(numeroCaso, idAreaOrigen);
						//Se cambia en el NumeroExpediente de UAVD por el NumeroExpediente del area que genero la solicitud de UAVD
						//con la finalidad de rastrear cual es el NumeroExpediente a partir del cual se realizo la solicitud
						solicitud.getNumeroExpediente().setNumeroExpediente(numeroDeCaso);
					}
				}
			}
		}

		LOGGER.info("Solicitudes:"+ solicitudes.size());
		List<SolicitudDTO> solicitudesDTO = new ArrayList<SolicitudDTO>();
		for(Solicitud sol:solicitudes){
			solicitudesDTO.add(SolicitudTransformer.solicitudTransformer(sol));
		}
		return solicitudesDTO;
	}
	
	@Override
	public List<SolicitudDTO> consultarSolicitudesGeneradasPorNumeroExpediente(List<Long> idEstatusSolicitud,
			List<Long> idTipoSolicitud, Long idAreaOrigen, Long idFuncionarioSolicitante, String numeroExpediente  ) throws NSJPNegocioException {
		LOGGER.info("Servicio para consultarSolicitudesGeneradasPorNumeroExpediente");
		
		LOGGER.info("Lista idEstatusSolicitud:"+ idEstatusSolicitud + 
				"Lista idTipoSolicitud:"+ idTipoSolicitud + 
				"idAreaOrigen:"+ idAreaOrigen + 
				"idFuncionarioDestinatario "+ idFuncionarioSolicitante );
		
		
		ConfInstitucion confInstitucion = solDao.consultarInsitucionActual();
		
		LOGGER.debug(":::::::::::::confInstitucion obtenido::::"+confInstitucion.getConfInstitucionId());
		
		List<Solicitud> solicitudes = null;
		
		if(confInstitucion.getConfInstitucionId().equals(Instituciones.PJ.getValorId())){
			solicitudes = solDao
			.consultarSolicitudesGeneradasPorNumeroExpediente(
					idEstatusSolicitud, idTipoSolicitud, idAreaOrigen,
					idFuncionarioSolicitante, numeroExpediente);
		}else{
			solicitudes = solDao
			.consultarSolicitudesGeneradasPorNumeroExpediente(
					idEstatusSolicitud, idTipoSolicitud, null,
					null, numeroExpediente);
		}
		
		

		LOGGER.info("Solicitudes:"+ solicitudes.size());
		List<SolicitudDTO> solicitudesDTO = new ArrayList<SolicitudDTO>();
		for(Solicitud sol:solicitudes){
			solicitudesDTO.add(SolicitudTransformer.solicitudTransformer(sol));
		}
		return solicitudesDTO;
		
	}
	
	@Override
	public List<SolicitudDTO> consultarSolicitudesParaAtender(List<Long> idEstatusSolicitud,
			List<Long> idTipoSolicitud, Long idAreaDestino, Long idFuncionarioDestinatario, Long catDiscriminanteOrigen) throws NSJPNegocioException{
		
		LOGGER.info("Servicio para consultarSolicitudesParaAtender");
		
		LOGGER.info("Lista idEstatusSolicitud:"+ idEstatusSolicitud + 
				"Lista idTipoSolicitud:"+ idTipoSolicitud + 
				"idAreaDestino:"+ idAreaDestino + 
				"catDiscriminanteOrigen:"+ catDiscriminanteOrigen +
				"idFuncionarioDestinatario "+ idFuncionarioDestinatario );
		
		List<Solicitud> solicitudes = solDao.consultarSolicitudesParaAtender(idEstatusSolicitud, idTipoSolicitud, idAreaDestino, idFuncionarioDestinatario,catDiscriminanteOrigen);

		LOGGER.info("Solicitudes:"+ solicitudes.size());
		List<SolicitudDTO> solicitudesDTO = new ArrayList<SolicitudDTO>();
		for(Solicitud sol:solicitudes){
			SolicitudDTO solicitudDTO=SolicitudTransformer.solicitudTransformer(sol);
			if(solicitudDTO.getExpedienteDTO()==null){
				String numeroCaso=sol.getNumeroCasoAsociado();
				Caso caso=casoDAO.consultarCasoPorNumeroCaso(numeroCaso);
				List<NumeroExpediente>list=numExpDAO.consultarNumeroExpedientePorNumeroCaso(numeroCaso);
				for (NumeroExpediente numeroExpediente : list) {
					if(numeroExpediente.getJerarquiaOrganizacional().getJerarquiaOrganizacionalId().equals(10L)){
						solicitudDTO.setExpedienteDTO(ExpedienteTransformer.transformaExpediente(numeroExpediente.getExpediente()));
						solicitudDTO.getExpedienteDTO().setNumeroExpediente(numeroExpediente.getNumeroExpediente());
					}
					
				}
			}
			solicitudesDTO.add(solicitudDTO);
		}
		return solicitudesDTO;
	}
	
	@Override
	public List<SolicitudDTO> consultarSolicitudesPorTipoYEstatus(
			TiposSolicitudes tipoSolicitud, EstatusSolicitud estatusSolicitud, Long claveFuncionario,UsuarioDTO usuario) throws NSJPNegocioException {
		
		if (LOGGER.isDebugEnabled())
			LOGGER.debug("/**** SERVICIO PARA CONSULTAR LAS SOLICITUDES POR TIPO Y ESTATUS ****/");
		
		/*
		* Usado para obtener el discriminante Id
		*/
		  long discriminanteId = 0L; 
				

		if (usuario != null
				&& usuario.getFuncionario() != null
				&& usuario.getFuncionario().getDiscriminante() != null
				&& usuario.getFuncionario().getDiscriminante().getCatDiscriminanteId() != null) {

			discriminanteId = usuario.getFuncionario().getDiscriminante()
					.getCatDiscriminanteId();
		}
		
		List<SolicitudDTO> solRetorno = new ArrayList<SolicitudDTO>();
		// TODO GBP: Cuando es beneficio de preliberacion consulta solicitudes normales. Identificar usos
//		if (tipoSolicitud.equals(TiposSolicitudes.BENEFICIO_PRELIBERACION)) {
//			List<SolicitudAudiencia> solicitudesAud = solicitudAudienciaDAO.consultarSolicitudesAudienciaPorTipoyEstado(tipoSolicitud, estatusSolicitud);
//		
//			for (SolicitudAudiencia solicitudAudiencia : solicitudesAud) {				
//				SolicitudAudienciaDTO solAudDTO = SolicitudAudienciaTransformer.transformarSolicitud(solicitudAudiencia);				
//				solAudDTO.setNumCarpetaEjecucion(solicitudAudiencia.getNumeroExpediente().getNumeroExpediente());
//				
//				NumeroExpediente causa = numExpDAO.obtenerCausaByExpediente(solicitudAudiencia.getNumeroExpediente().getExpediente().getExpedienteId());
//				if (causa!=null)
//					solAudDTO.setNumCausa(causa.getNumeroExpediente());
//				
//				solRetorno.add(solAudDTO);
//			}				
//		} else  {
			List<Solicitud> solicitudes = solDao.consultarSolicitudesPorTipoYEstatus(tipoSolicitud.getValorId(), estatusSolicitud.getValorId(), claveFuncionario,discriminanteId);
			
			for (Solicitud solicitud : solicitudes) {
				SolicitudDTO solDTO = new SolicitudDTO();
				solDTO = SolicitudTransformer.solicitudTransformer(solicitud);
				solDTO.setNumCarpetaEjecucion(solicitud.getNumeroExpediente().getNumeroExpediente());
				
				NumeroExpediente causa = numExpDAO.obtenerCausaByExpediente(solicitud.getNumeroExpediente().getExpediente().getExpedienteId());
				solDTO.setNumCausa(causa.getNumeroExpediente());
				
				solRetorno.add(solDTO);
			}
//		}
		LOGGER.debug("/**** Respuesta :. " + solRetorno.size());
		return solRetorno;
	}

	@Override
	public List<SolicitudDTO> consultarSolicitudesPorTipoYNoEstatus(
			TiposSolicitudes tipoSolicitud, EstatusSolicitud estatusSolicitud, Long claveFuncionario) throws NSJPNegocioException {
		
		if (LOGGER.isDebugEnabled())
			LOGGER.debug("/**** SERVICIO PARA CONSULTAR LAS SOLICITUDES POR TIPO Y ESTATUS ****/");
		
		List<Solicitud> solicitudes = solDao.consultarSolicitudesPorTipoYNoEstatus(tipoSolicitud.getValorId(), estatusSolicitud.getValorId(), claveFuncionario);
		
		List<SolicitudDTO> solRetorno = new ArrayList<SolicitudDTO>();
		for (Solicitud solicitud : solicitudes) {
			SolicitudDTO solDTO = new SolicitudDTO();
			solDTO = SolicitudTransformer.solicitudTransformer(solicitud);
			solRetorno.add(solDTO);
		}
		
		return solRetorno;
	}

	@Override
	public SolicitudDTO consultarSolicitudXId(Long solicitudId)throws NSJPNegocioException{
		
		SolicitudDTO detSolicitud = new SolicitudDTO();
		if(solicitudId==null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		Solicitud solOrigen = solDao.read(solicitudId);
		detSolicitud = SolicitudTransformer.solicitudTransformer(solOrigen);
		return detSolicitud;
	}

	@Override
	public List<SolicitudDTO> consultarSolicitudesConCriterios(
			SolicitudDTO solicitudDTO, List<Long> idEstatusSolicitud,
			List<Long> idTipoSolicitud, String tipoConsulta)
			throws NSJPNegocioException {
		Solicitud solicitud = SolicitudTransformer.solicitudTransformer(solicitudDTO);
		
		List<Solicitud> lstSolicitudes = solDao.consultarSolicitudesConCriterios(solicitud, idEstatusSolicitud, idTipoSolicitud, tipoConsulta);
		List<SolicitudDTO> lstSolicitudesDTO = new ArrayList<SolicitudDTO>();
		for (Solicitud tmp : lstSolicitudes){
			SolicitudDTO tmpDTO = SolicitudTransformer.solicitudTransformer(tmp);
			lstSolicitudesDTO.add(tmpDTO);
		}
		
		return lstSolicitudesDTO;
	}

	@Override
	public List<SolicitudDTO> consultarSolicitudesPorFiltro(
			SolicitudDTO solFiltro, Boolean esAsociadaNumeroExpediente,
			Long distritoId) throws NSJPNegocioException {
		
		Solicitud solicitudFiltro = SolicitudTransformer
				.solicitudTransformer(solFiltro);
		
		AvisoDesignacion avisoDesignacionFiltro = null;
		if (solFiltro.getAvisoDesignacionDTO() != null) {

			avisoDesignacionFiltro = AvisoDesignacionTransformer
					.transformar(solFiltro.getAvisoDesignacionDTO());
		}
				
		List<Solicitud> lstSolicitudes = solDao.consultarSolicitudPorFiltro(
				solicitudFiltro, esAsociadaNumeroExpediente, distritoId, avisoDesignacionFiltro);

		List<SolicitudDTO> lstSolicitudesDTO = null;

		if (lstSolicitudes != null && !lstSolicitudes.isEmpty()) {

			lstSolicitudesDTO = new ArrayList<SolicitudDTO>();

			for (Solicitud solicitud : lstSolicitudes) {
				SolicitudDTO solicitudDTO = SolicitudTransformer
						.solicitudTransformer(solicitud);				
				if(solicitud.getTipoSolicitud() != null && solicitud.getTipoSolicitud().getValorId() != null
						&& solicitud.getTipoSolicitud().getValorId() == TiposSolicitudes.AUDIENCIA.getValorId()){
					if(solicitud.getAreaOrigen() != null && solicitud.getAreaOrigen() > 0){
						JerarquiaOrganizacional loJeOrganizacional = jerarquiaOrganizacionalDAO.read(solicitud.getAreaOrigen());
						if(loJeOrganizacional != null){
							//Nombre del area origen que hizo la solicitud
							solicitudDTO.setNombreAreaOrigen(loJeOrganizacional.getNombre());
						}
					}
				}

				ConfInstitucion confInstitucion = solDao
						.consultarInsitucionActual();
				if (confInstitucion.getConfInstitucionId().equals(
						Instituciones.DEF.getValorId())) {
					// Aviso de designaci&oacute;n para obtener el defensor
					// asociado
					AvisoDesignacion avisoDesignacion = avisoDesignacionDAO
							.consultarAvisoDesignacionPorSolicitudDeDefensor(solicitud
									.getDocumentoId());
					if (avisoDesignacion != null) {
						AvisoDesignacionDTO avisoDesignacionDTO = AvisoDesignacionTransformer
								.transformar(avisoDesignacion);
						solicitudDTO
								.setAvisoDesignacionDTO(avisoDesignacionDTO);
					}
				}
				lstSolicitudesDTO.add(solicitudDTO);
			}
		}
		return lstSolicitudesDTO;
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.solicitud.ConsultarSolicitudService#consultarSolicitudesPorExpedienteTiposDocumentoYEstatus(mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO, java.util.List, mx.gob.segob.nsjp.dto.catalogo.ValorDTO)
	 */
	@Override
	public List<SolicitudDTO> consultarSolicitudesPorExpedienteTiposDocumentoYEstatus(
			ExpedienteDTO expediente, List<ValorDTO> tiposDocumento,
			ValorDTO estatusSolicitud) throws NSJPNegocioException {
		
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
		
		Valor estatusConsulta = ValorTransformer.transformar(estatusSolicitud);
		
		List<Solicitud> solicitudesBD = solDao.consultarSolicitudesPorExpedienteTiposDocumentoYEstatus(expConsulta, tiposDocConsulta, estatusConsulta);
		
		List<SolicitudDTO> solsDTO = null;
		if (solicitudesBD != null
				&& !solicitudesBD.isEmpty()){
			solsDTO = new ArrayList<SolicitudDTO>();
			for (Solicitud sol : solicitudesBD) {
				solsDTO.add(SolicitudTransformer.solicitudTransformer(sol));
			}
		}
		return solsDTO;
	}
	
	
	
	public List<SolicitudDTO> consultarSolicitudesPorTipoYNumeroExps(Long idCaso, List<Long> idTipoSolicitud) 
		throws NSJPNegocioException {
		
		if(idCaso == null){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		List<Solicitud> solicitudesBD = solDao.consultarSolicitudesPorTipoYNumeroExps(idCaso, idTipoSolicitud);
		
		List<SolicitudDTO> solsDTO = null;
		if (solicitudesBD != null
				&& !solicitudesBD.isEmpty()){
			solsDTO = new ArrayList<SolicitudDTO>();
			for (Solicitud sol : solicitudesBD) {
				solsDTO.add(SolicitudTransformer.solicitudTransformerBasico(sol));
			}
		}
		return solsDTO;
	}

	@Override
	public List<SolicitudMandamientoDTO> consultarSolicitudesMandatoJudicialPorFiltro(
			SolicitudMandamientoDTO solicitudMandamientoDTO)
			throws NSJPNegocioException {

		if (solicitudMandamientoDTO == null) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		List<SolicitudMandamiento> listaSolicitudesMandamiento = solicitudMandamientoDAO
				.consultarSolicitudesMandatoJudicialPorFiltro(SolicitudTransformer
						.transformarSolicitudMandamientoBasico(solicitudMandamientoDTO));

		List<SolicitudMandamientoDTO> listaMandamientosDTO = null;

		if (listaSolicitudesMandamiento != null
				&& !listaSolicitudesMandamiento.isEmpty()) {

			listaMandamientosDTO = new ArrayList<SolicitudMandamientoDTO>();

			for (SolicitudMandamiento solMand : listaSolicitudesMandamiento) {
				listaMandamientosDTO.add(SolicitudTransformer
						.transformarSolicitudMandamientoBasico(solMand));
			}
		}
		return listaMandamientosDTO;
	}
	
}
