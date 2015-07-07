/**
 * Nombre del Programa : SolicitudDelegateImpl.java
 * Autor                            : cesarAgustin
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 17 May 2011
 * Marca de cambio        : N/A
 * Descripcion General    : Implementacion del delegate para los metodos de comunicacion de Solicitud entre la vista y los servicios
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
package mx.gob.segob.nsjp.delegate.solicitud.impl;

import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.expediente.EtapasExpediente;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud;
import mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.delegate.solicitud.SolicitudDelegate;
import mx.gob.segob.nsjp.dto.archivo.ArchivoDigitalDTO;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.caso.CasoDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.sentencia.SentenciaDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudAudienciaDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDefensorDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudMandamientoDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudPericialDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudTranscripcionAudienciaDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.service.institucion.JerarquiaOrganizacionalService;
import mx.gob.segob.nsjp.service.solicitud.ActualizarEstatusSolicitudDefensorService;
import mx.gob.segob.nsjp.service.solicitud.ActualizarEstatusSolicitudService;
import mx.gob.segob.nsjp.service.solicitud.AdjuntarArchivoASolicitudService;
import mx.gob.segob.nsjp.service.solicitud.AdministrarNumeroExpedienteSolicitudAudienciaService;
import mx.gob.segob.nsjp.service.solicitud.AsociarSolicitudANumeroExpedienteService;
import mx.gob.segob.nsjp.service.solicitud.AtenderSolicitudDeDefensorService;
import mx.gob.segob.nsjp.service.solicitud.AtenderSolicitudPreliberacionService;
import mx.gob.segob.nsjp.service.solicitud.AutorizarSolicitudEvidenciaService;
import mx.gob.segob.nsjp.service.solicitud.ConsultarSolicitudService;
import mx.gob.segob.nsjp.service.solicitud.ConsultarSolicitudXEstatusService;
import mx.gob.segob.nsjp.service.solicitud.ConsultarSolicitudesAudienciaService;
import mx.gob.segob.nsjp.service.solicitud.ConsultarSolicitudesDefensoriaService;
import mx.gob.segob.nsjp.service.solicitud.EnviarSolicitudDeTranscripcionService;
import mx.gob.segob.nsjp.service.solicitud.EnviarSolicitudService;
import mx.gob.segob.nsjp.service.solicitud.RecibirSolicitudCiudadanaDefensoriaService;
import mx.gob.segob.nsjp.service.solicitud.RegistrarAvisoDeDetencionService;
import mx.gob.segob.nsjp.service.solicitud.RegistrarSolicitudDefensorEncargadoSalaService;
import mx.gob.segob.nsjp.service.solicitud.RegistrarSolicitudService;
import mx.gob.segob.nsjp.service.solicitud.SolicitarDefensorService;
import mx.gob.segob.nsjp.service.solicitud.SolicitudTranscripcionAudienciaService;
import mx.gob.segob.nsjp.service.solicitud.ValidarSolicitudDeDefensorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementacion del delegate para los metodos de comunicacion de Solicitud entre la vista y los servicios.
 * @version 1.0
 * @author cesarAgustin
 *
 */
@Service("solicitudDelegate")
public class SolicitudDelegateImpl implements SolicitudDelegate {

    @Autowired
    private RecibirSolicitudCiudadanaDefensoriaService recibirSolicitudCiudadanaDefensoriaService;
    @Autowired
    private SolicitarDefensorService solicitarDefensorService;
    @Autowired
    private ConsultarSolicitudService solicitudService;
    @Autowired
    private SolicitudTranscripcionAudienciaService transcripcionAudienciaService;
    @Autowired
    private AtenderSolicitudPreliberacionService atenderSolicitudPreliberacionService;
    @Autowired
    private AutorizarSolicitudEvidenciaService autorizarEvidenciaService;
    
       //TODO ESTE METODO TAMBI�N SE ENCUENTRA EN AudienciaDelegateImp decidir si se va a quitar de all�, porque pertenece a esta clase
	@Autowired
	private ConsultarSolicitudesDefensoriaService consultarSolicitidesDefensoriaService;
	@Autowired
	private ActualizarEstatusSolicitudService actualizarEstatusSolicitudService;
	@Autowired
	private ConsultarSolicitudXEstatusService consultarSolicitudXEstatusService;
	@Autowired
	private AsociarSolicitudANumeroExpedienteService asociarSolicitudANumeroExpedienteService;
	@Autowired
	private AdjuntarArchivoASolicitudService adjuntarArchivoASolicitudService;
	@Autowired
	private RegistrarSolicitudService registrarSolicitudService;
	@Autowired
	private RegistrarSolicitudDefensorEncargadoSalaService registrarSolicitudDefensorEncargadoSalaService;
	@Autowired
	private ActualizarEstatusSolicitudDefensorService actualizarEstatusSolicitudDefensorService;
	@Autowired
	private RegistrarAvisoDeDetencionService registrarAvisoDeDetencionService;
	@Autowired
	private ConsultarSolicitudesAudienciaService consultarSolicitudesAudienciaService;
	@Autowired
	private JerarquiaOrganizacionalService jerarquiaOrganizacionalService;
	@Autowired
	private EnviarSolicitudDeTranscripcionService enviarSolicitudDeTranscripcionService;	
	@Autowired
	private EnviarSolicitudService enviarSolicitudService;
	@Autowired
	private ValidarSolicitudDeDefensorService validarSolicitudDeDefensorService;
	@Autowired
	private AtenderSolicitudDeDefensorService atenderSolicitudDeDefensorService;
	@Autowired
	private AdministrarNumeroExpedienteSolicitudAudienciaService administrarNumeroExpedienteSolicitudAudienciaService;
	
    @Override
    public List<SolicitudDTO> consultarSolicitudesPorExpediente(
            ExpedienteDTO filtro) throws NSJPNegocioException {
        return solicitudService.consultarSolicitudesPorExpediente(filtro);
    }

    public SolicitudDTO obtenerDetalleSolicitud(SolicitudDTO sol)
            throws NSJPNegocioException {
        return solicitudService.obtenerSolicitud(sol);
    }
    
    @Override
    public SolicitudDefensorDTO obtenerSolicitudDefensor(SolicitudDefensorDTO sol)
    	throws NSJPNegocioException {
    	return consultarSolicitidesDefensoriaService.obtenerSolicitudDefensor(sol);
	}

	@Override
	public SolicitudDefensorDTO registrarSolicitudAsesoriaLegal(
			ExpedienteDTO expedienteDTO) throws NSJPNegocioException {
		return (this.recibirSolicitudCiudadanaDefensoriaService
				.registrarSolicitudAsesoriaLegal(expedienteDTO));
	}
    
    @Override
    public void guardarMotivoSolicitudDefensoria(SolicitudDefensorDTO solicitudDefensorDTO)
            throws NSJPNegocioException {

        this.recibirSolicitudCiudadanaDefensoriaService.guardarMotivoSolicitudDefensoria(solicitudDefensorDTO);
    }

    @Override
    public InvolucradoDTO guardarSolicitanteSolicitudDefensoria(
            InvolucradoDTO solicitante) throws NSJPNegocioException {

        return this.recibirSolicitudCiudadanaDefensoriaService.guardarSolicitanteSolicitudDefensoria(solicitante);
    }

	@Override
	public SolicitudDefensorDTO actualizarSolicitudAsesoriaLegal(SolicitudDefensorDTO solicitudDefensorDTO)
			throws NSJPNegocioException {
		return this.recibirSolicitudCiudadanaDefensoriaService
				.actualizarSolicitudAsesoriaLegal(solicitudDefensorDTO);
	}
    
    @Override
    public InvolucradoDTO guardarDefendidoSolicitudDefensoria(
            InvolucradoDTO imputado, Long tipoAsesoria, EtapasExpediente etapa) throws NSJPNegocioException {

        return recibirSolicitudCiudadanaDefensoriaService.guardarDefendidoSolicitudDefensoria(imputado,tipoAsesoria, etapa);
    }
    
    @Override
    @Deprecated
    public SolicitudDefensorDTO generarAcuseAtencion(ExpedienteDTO expedienteDto)
            throws NSJPNegocioException {

        return recibirSolicitudCiudadanaDefensoriaService.generarAcuseAtencion(expedienteDto);
    }

    @Override
    public void actualizaImputadoSolicitudDefensoria(InvolucradoDTO imputado)
            throws NSJPNegocioException {
        this.recibirSolicitudCiudadanaDefensoriaService.actualizaImputadoSolicitudDefensoria(imputado);

    }

    @Override
    public void turnarSolicitudDefensoriaCiudadanaACoordinar(
            SolicitudDefensorDTO solicitudDefensorDTO)
            throws NSJPNegocioException {

        recibirSolicitudCiudadanaDefensoriaService.actualizaEstatusSolicitudDefensoria(
                solicitudDefensorDTO);

    }

    @Override
    public List<SolicitudDefensorDTO> obtenerSolicitudesDefensoriaPorEstatus(EstatusSolicitud estatusSolc,Instituciones institucion)
            throws NSJPNegocioException {

        return this.consultarSolicitidesDefensoriaService.obtenerSolicitudesDefensoriaPorEstatus(estatusSolc,institucion);
    }
    
    @Override
    public List<SolicitudDefensorDTO> obtenerSolicitudesAsesoriaDefensoriaPorEstatus(EstatusSolicitud estatusSolc,Instituciones institucion)
            throws NSJPNegocioException {

        return this.consultarSolicitidesDefensoriaService.obtenerSolicitudesAsesoriaDefensoriaPorEstatus(estatusSolc,institucion);
    }

    @Override
    public void actualizaEstatusSolicitud(SolicitudDTO solicitud,
            EstatusSolicitud estatus) throws NSJPNegocioException {
        actualizarEstatusSolicitudService.actualizaEstatusSolicitud(solicitud, estatus);

    }

	@Override
	public SolicitudDefensorDTO registrarSolicitudDefensorPJ(
			AudienciaDTO audienciaDTO, List<InvolucradoDTO> listaProbablesResponsables)
			throws NSJPNegocioException {
		return (registrarSolicitudDefensorEncargadoSalaService
				.registrarSolicitudDefensorPJ(audienciaDTO, listaProbablesResponsables));
	}
	
	public void registrarAvisoDeDetencion(Long imputadoId, CasoDTO noCaso,Long idAgencia, String claveAgencia)throws NSJPNegocioException{
		registrarAvisoDeDetencionService.registrarAvisoDeDetencion(imputadoId, noCaso,idAgencia,claveAgencia);
	}
	
	@Override
	public List<? extends SolicitudDTO> consultarSolicitudXEstatus(EstatusSolicitud estatusSolicitud, 
			UsuarioDTO usuario, TiposSolicitudes tipoSolicitud) throws NSJPNegocioException {
		return consultarSolicitudXEstatusService.consultarSolicitudXEstatus(estatusSolicitud, usuario, tipoSolicitud);
		
	}

	@Override
	public void actualizarEtapaExpedienteSolicitudDefensoria(
			SolicitudDefensorDTO solicitud, EtapasExpediente etapa)
			throws NSJPNegocioException {
		
		recibirSolicitudCiudadanaDefensoriaService.actualizarEtapaExpedienteSolicitudDefensoria(solicitud, etapa);
	}

	@Override
	public List<SolicitudTranscripcionAudienciaDTO> consultarSolicitudTranscripcion(
			UsuarioDTO usuario) throws NSJPNegocioException {
		return consultarSolicitudXEstatusService.consultarSolicitudTranscripcion(usuario);
	}

	public List<SolicitudMandamientoDTO> consultarSolicitudesMandatoJudicial() throws NSJPNegocioException{
		return consultarSolicitudXEstatusService.consultarSolicitudMandatoJudicial();
	}
	
	@Override
	public void asociarSolicitudANumeroExpediente(
			ExpedienteDTO expedienteDTO, SolicitudDTO solicitudDTO)
			throws NSJPNegocioException {
		this.asociarSolicitudANumeroExpedienteService.asociarSolicitudANumeroExpediente(expedienteDTO, solicitudDTO);
	}

	public void adjuntarArchivoASolicitud(SolicitudDTO solicitud,
			ArchivoDigitalDTO adjunto) throws NSJPNegocioException {
		adjuntarArchivoASolicitudService.adjuntarArchivoASolicitud(solicitud, adjunto);
	}
	/*
	 * (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.solicitud.SolicitudDelegate#consultarSolicitudesPorNumeroExpedienteYTipo(java.lang.Long, mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes)
	 */
	@Override
	public List<SolicitudDTO> consultarSolicitudesPorNumeroExpedienteYTipo(
			Long numeroExpedienteId, TiposSolicitudes tipo)
			throws NSJPNegocioException {
		return solicitudService.consultarSolicitudesPorNumeroExpedienteYTipo(numeroExpedienteId, tipo);
	}

	@Override
	@Transactional
	public SolicitudDTO registrarSolicitud(SolicitudDTO solicitud) throws NSJPNegocioException {
		return registrarSolicitudService.registrarSolicitud(solicitud);
		
	}

	@Override
	public SolicitudAudienciaDTO registrarSolicitudAudiencia(SolicitudAudienciaDTO solicitud) throws NSJPNegocioException{
		return registrarSolicitudService.registrarSolicitudAudiencia(solicitud);
	}
	
	public SolicitudDTO registrarSolicitudOrdenDeDetencion(SolicitudDTO ordenDetencion, Long idDocumentoAnexo)throws NSJPNegocioException{
		return registrarSolicitudService.registrarSolicitudOrdenDeDetencion(ordenDetencion, idDocumentoAnexo);
	}
	
	@Override
	public SolicitudDTO registrarSolicitudCarpetaInvestigacion(Long idExpediente, UsuarioDTO usuario) throws NSJPNegocioException{
		return registrarSolicitudService.registrarSolicitudCarpetaInvestigacion(idExpediente, usuario);
	}
	
	@Override
	public List<SolicitudDefensorDTO> consultarSolDefensorAsignadas(
			FuncionarioDTO funcionarioDTO) throws NSJPNegocioException {		
		return solicitarDefensorService.consultarSolDefensorAsignadas(funcionarioDTO);
	}

	@Override
	public void actualizarEstatusSolicitudDefensoria(
			SolicitudDefensorDTO solDefensorDTO,
			EstatusSolicitud estatusSolicitud) throws NSJPNegocioException {
		actualizarEstatusSolicitudDefensorService.actualizarEstatusSolicitudDefensor(solDefensorDTO, estatusSolicitud);
	}

	@Override
	public List<SolicitudAudienciaDTO> consultarSolicitudesAudienciaPorTipoyEstado(
			TiposSolicitudes tipo, EstatusSolicitud estado) {
		return consultarSolicitudesAudienciaService.consultarSolicitudesAudienciaPorTipoyEstado(tipo,estado);
	}

	@Override
	public Long consultarNumeroExpedienteDeSolicitud(Long solicitudId) {
		return solicitudService.consultarNumeroExpedienteDeSolicitud(solicitudId);
	}

	@Override
	public List<SolicitudDefensorDTO> consultarSolicitudesDefensoriaByHistoricoYEstatus(
			EstatusSolicitud estatusSolicitud) throws NSJPNegocioException {		
		return this.consultarSolicitidesDefensoriaService.consultarSolicitudesDefensoriaByHistoricoYEstatus(estatusSolicitud);
	}

	@Override
	public SolicitudDefensorDTO reasignarDefensorAExpediente(ExpedienteDTO expedienteDTO,
			FuncionarioDTO funcionarioDTO) throws NSJPNegocioException {
		return solicitarDefensorService.reasignarDefensorAExpediente(expedienteDTO, funcionarioDTO);
	}	
	
    @Override
    public void actualizaEstatusSolicitud(String folioSolicitud, EstatusSolicitud estatus) throws NSJPNegocioException {
        actualizarEstatusSolicitudService.actualizaEstatusSolicitud(folioSolicitud, estatus);

    }
    public SolicitudDTO consultarDatosDeSolicitud(String folioSolicitud) throws NSJPNegocioException{
    	return solicitudService.consultarDatosDeSolicitud(folioSolicitud);
    }

	@Override
	public SolicitudTranscripcionAudienciaDTO registrarSolicitudTranscripcionAudiencia(
			SolicitudTranscripcionAudienciaDTO solicitud) throws NSJPNegocioException {
		return registrarSolicitudService.registrarSolicitudTranscripcionAudiencia(solicitud);
	}

	/*
	 * (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.solicitud.SolicitudDelegate#adjuntarArchivoASolicitudBasico(mx.gob.segob.nsjp.dto.solicitud.SolicitudDTO, mx.gob.segob.nsjp.dto.archivo.ArchivoDigitalDTO)
	 */
	@Override
	public void adjuntarArchivoASolicitudBasico(SolicitudDTO solicitud,
			ArchivoDigitalDTO adjunto) throws NSJPNegocioException {
		adjuntarArchivoASolicitudService.adjuntarArchivoASolicitudBasico(solicitud, adjunto);
	}

	@Override
	public List<SolicitudDTO> consultarSolicitudesGeneradas(List<Long> idEstatusSolicitud,
			List<Long> idTipoSolicitud, Long idAreaOrigen, Long idFuncionarioRemitente  ) throws NSJPNegocioException{
		return solicitudService.consultarSolicitudesGeneradas(idEstatusSolicitud, idTipoSolicitud, idAreaOrigen, idFuncionarioRemitente);
	}

	@Override
	public List<SolicitudDTO> consultarSolicitudesGeneradasPorNumeroExpediente(
			List<Long> idEstatusSolicitud, List<Long> idTipoSolicitud,
			Long idAreaOrigen, Long idFuncionarioSolicitante,
			String numeroExpediente) throws NSJPNegocioException {
		return solicitudService.consultarSolicitudesGeneradasPorNumeroExpediente(idEstatusSolicitud, idTipoSolicitud, idAreaOrigen, idFuncionarioSolicitante, numeroExpediente);
	}
	
	@Override
	public List<SolicitudDTO> consultarSolicitudesParaAtender(List<Long> idEstatusSolicitud,
			List<Long> idTipoSolicitud, Long idAreaDestino, Long idFuncionarioSolicitante, Long catDiscriminanteOrigen) throws NSJPNegocioException{
		return solicitudService.consultarSolicitudesParaAtender(idEstatusSolicitud, idTipoSolicitud, idAreaDestino, idFuncionarioSolicitante, catDiscriminanteOrigen);
	}

	
	@Override
	public List<ValorDTO> consultarTipoSolictudesPorJerarquiaOrganizacional(
			Long idJerarquiaOrganizacional) throws NSJPNegocioException {
		return jerarquiaOrganizacionalService.consultarTipoSolictudesPorJerarquiaOrganizacional(idJerarquiaOrganizacional);
	}

	@Override
	public List<SolicitudDTO> consultarSolicitudesPorTipoYEstatus(
			TiposSolicitudes tipoSolicitudes, EstatusSolicitud estatusSolicitud, Long claveFuncionario,UsuarioDTO usuario)
			throws NSJPNegocioException {		
		return solicitudService.consultarSolicitudesPorTipoYEstatus(tipoSolicitudes, estatusSolicitud, claveFuncionario,usuario);
	}
	
	@Override
	public List<SolicitudDTO> consultarSolicitudesPorTipoYNoEstatus(
			TiposSolicitudes tipoSolicitudes, EstatusSolicitud estatusSolicitud, Long claveFuncionario)
			throws NSJPNegocioException {		
		return solicitudService.consultarSolicitudesPorTipoYNoEstatus(tipoSolicitudes, estatusSolicitud, claveFuncionario);
	}

	@Override
	public List<SolicitudTranscripcionAudienciaDTO> consultarSolicitudTranscripcionAudienciaPorTipoYEstatus(
			Long idAudiencia, Long tipoId, List<Long> estatusId)
			throws NSJPNegocioException {
		return transcripcionAudienciaService.consultarSolicitudTranscripcionAudienciaPorTipoYEstatus(idAudiencia, tipoId, estatusId);
	}

	@Override
	public List<SolicitudTranscripcionAudienciaDTO> consultarSolicitudMaster(
			Long idAudiencia, Long idTipo) throws NSJPNegocioException {

		return transcripcionAudienciaService.consultarSolicitudMaster(idAudiencia, idTipo);
	}

	@Override
	public ExpedienteDTO atenderSolicitudPreliberacion(SolicitudDTO solicitudDTO)
			throws NSJPNegocioException {		
		return atenderSolicitudPreliberacionService.atenderSolicitudPreliberacionService(solicitudDTO);
	}
	/*
	 * (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.solicitud.SolicitudDelegate#consultarSolicitudTranscripcionAudienciaPorTipoYEstatus(java.lang.Long, java.lang.Long)
	 */
	@Override
	public List<SolicitudTranscripcionAudienciaDTO> consultarSolicitudTranscripcionAudienciaPorTipoYEstatus(
			Long tipoId, Long estatusId, UsuarioDTO usuario, Date fechaIni, Date fechaFin) {
		return transcripcionAudienciaService.consultarSolicitudTranscripcionAudienciaPorTipoYEstatus(tipoId, estatusId,usuario,fechaIni, fechaFin);
	}
	/*
	 * (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.solicitud.SolicitudDelegate#consultarDetalleSolicitudTranscripcion(java.lang.Long)
	 */
	@Override
	public SolicitudTranscripcionAudienciaDTO consultarDetalleSolicitudTranscripcion(
			Long solicitudId) {
		return transcripcionAudienciaService.consultarDetalleSolicitudTranscripcion(solicitudId);
	}

	@Override
	public SolicitudDTO consultarSolicitudXId(Long solicitudId) throws NSJPNegocioException {
		return solicitudService.consultarSolicitudXId(solicitudId);
	}

	@Override
	public Long autorizarSolicitudDeEvidencia(SolicitudPericialDTO evidencia) throws NSJPNegocioException {
		return autorizarEvidenciaService.autorizarSolicitudEvidencia(evidencia);
	}
	/*
	 * (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.solicitud.SolicitudDelegate#enviarActualizacionSolicitudTranscripcionAudiencia(java.lang.Long)
	 */
	@Override
	public void enviarActualizacionSolicitudTranscripcionAudiencia(
			Long solicitudId) throws NSJPNegocioException {
		transcripcionAudienciaService.enviarActualizacionSolicitudTranscripcionAudiencia(solicitudId);
		
	}
	
	@Override
	public SolicitudTranscripcionAudienciaDTO enviarSolicitudDeTranscripcionAAreaExterna(SolicitudTranscripcionAudienciaDTO solicitudTranscripcionDto) throws NSJPNegocioException {
		return enviarSolicitudDeTranscripcionService.enviarSolicitudDeTranscripcion(solicitudTranscripcionDto);
	}

	@Override
	public List<SolicitudDTO> consultarSolicitudesConCriterios(
			SolicitudDTO solicitudDTO, List<Long> idEstatusSolicitud,
			List<Long> idTipoSolicitud, String tipoConsulta)
			throws NSJPNegocioException {
		return solicitudService.consultarSolicitudesConCriterios(solicitudDTO, idEstatusSolicitud, idTipoSolicitud, tipoConsulta);
	}
	
	@Override
	public SolicitudDTO enviarSolicitud(SolicitudDTO solicitudDTO,
			Instituciones destino,
			List<DocumentoDTO> lstDocumentoAdjuntos, SentenciaDTO sentenciaDTO) throws NSJPNegocioException {
		return enviarSolicitudService.enviarSolicitud(solicitudDTO, destino, lstDocumentoAdjuntos, sentenciaDTO);
	}
	
	@Override
	public List<Long> obtenerInvolucradosIdConSolicitudDefensor(
			AudienciaDTO audienciaDTO, List<Long> imputadosId)
			throws NSJPNegocioException{
		return registrarSolicitudDefensorEncargadoSalaService.obtenerInvolucradosIdConSolicitudDefensor(audienciaDTO,imputadosId);
	}
	
	@Override
	public String validarSolicitudDeDefensor(ExpedienteDTO expedienteDTO)
			throws NSJPNegocioException {
		return validarSolicitudDeDefensorService.validarSolicitudDeDefensor(expedienteDTO);
	}

	@Override
	public List<SolicitudDTO> consultarSolicitudesPorFiltro(
			SolicitudDTO solFiltro, Boolean esAsociadaNumeroExpediente,
			Long distritoId) throws NSJPNegocioException {
		return solicitudService.consultarSolicitudesPorFiltro(solFiltro,
				esAsociadaNumeroExpediente, distritoId);
	}

	@Override
	public String atenderSolicitudDeDefensor(
			SolicitudDefensorDTO solicitudDefensorDTO,UsuarioDTO usuarioFirmado)
			throws NSJPNegocioException {
		return atenderSolicitudDeDefensorService.atenderSolicitudDeDefensor(solicitudDefensorDTO,usuarioFirmado);
	}

	@Override
	public List<InvolucradoDTO> consultarInvolucradosSolicitudDefensor(
			Long solicitudDefensorId) throws NSJPNegocioException {
		return consultarSolicitidesDefensoriaService
				.consultarInvolucradosSolicitudDefensor(solicitudDefensorId);
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.solicitud.SolicitudDelegate#consultarSolicitudesPorExpedienteTiposDocumentoYEstatus(mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO, java.util.List, mx.gob.segob.nsjp.dto.catalogo.ValorDTO)
	 */
	@Override
	public List<SolicitudDTO> consultarSolicitudesPorExpedienteTiposDocumentoYEstatus(
			ExpedienteDTO expediente, List<ValorDTO> tiposDocumento,
			ValorDTO estatusSolicitud) throws NSJPNegocioException {
		return solicitudService.consultarSolicitudesPorExpedienteTiposDocumentoYEstatus
		(expediente, tiposDocumento, estatusSolicitud);
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.solicitud.SolicitudDelegate#crearSolicitudMandamientoConInfoExistente(mx.gob.segob.nsjp.dto.solicitud.SolicitudMandamientoDTO)
	 */
	@Override
	public SolicitudMandamientoDTO crearSolicitudMandamientoConInfoExistente(
			SolicitudMandamientoDTO solicitudMandamientoDTO)
			throws NSJPNegocioException {
		return registrarSolicitudService.crearSolicitudMandamientoConInfoExistente(solicitudMandamientoDTO);
	}
	
	@Override
	public List<SolicitudDTO> consultarSolicitudesPorTipoYNumeroExps(Long idCaso, List<Long> idTipoSolicitud) 
		throws NSJPNegocioException {
		return solicitudService.consultarSolicitudesPorTipoYNumeroExps(idCaso, idTipoSolicitud);
	}

	@Override
	public Boolean editarNumeroExpedienteSolicitudAudiencia(
			ExpedienteDTO expedienteDTO) throws NSJPNegocioException {
		return administrarNumeroExpedienteSolicitudAudienciaService
				.editarNumeroExpedienteSolicitudAudiencia(expedienteDTO);
	}

	@Override
	public Boolean actualizarNumeroExpedienteSolicitudAudiencia(
			ExpedienteDTO expedienteDTO) throws NSJPNegocioException {
		return administrarNumeroExpedienteSolicitudAudienciaService.actualizarNumeroExpedienteSolicitudAudiencia(expedienteDTO);
		
	}

	@Override
	public Boolean actualizarEstatusSolicitudes(Long nuevoEstatusNumExpId,
			Long numeroExpedienteId) throws NSJPNegocioException {
		return actualizarEstatusSolicitudService.actualizarEstatusSolicitudes(
				nuevoEstatusNumExpId, numeroExpedienteId);
	}

	@Override
	public List<SolicitudMandamientoDTO> consultarSolicitudesMandatoJudicialPorFiltro(
			SolicitudMandamientoDTO solicitudMandamientoDTO)
			throws NSJPNegocioException {
		return solicitudService
				.consultarSolicitudesMandatoJudicialPorFiltro(solicitudMandamientoDTO);
	}
}
