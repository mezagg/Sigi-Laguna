/**
 * Nombre del Programa : AudienciaDelegateImpl.java
 * Autor                            : vaguirre
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 30 May 2011
 * Marca de cambio        : N/A
 * Descripcion General    : Implementaci&oacute;n del delegate para las audiencias
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
package mx.gob.segob.nsjp.delegate.audiencia.impl;

import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.audiencia.EstatusAudiencia;
import mx.gob.segob.nsjp.comun.enums.audiencia.TipoAudiencia;
import mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud;
import mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.delegate.audiencia.AudienciaDelegate;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaJAVSTransporteDTO;
import mx.gob.segob.nsjp.dto.audiencia.CatEstadoPermisoDTO;
import mx.gob.segob.nsjp.dto.audiencia.DiaDisponibilidadDTO;
import mx.gob.segob.nsjp.dto.audiencia.EventoDTO;
import mx.gob.segob.nsjp.dto.audiencia.FiltroAudienciaDTO;
import mx.gob.segob.nsjp.dto.audiencia.MesDisponibilidadDTO;
import mx.gob.segob.nsjp.dto.audiencia.PermisoAudienciaDTO;
import mx.gob.segob.nsjp.dto.audiencia.SalaAudienciaDTO;
import mx.gob.segob.nsjp.dto.bitacora.BitacoraDescargaDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatDistritoDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatalogoDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.configuracion.ConfInstitucionDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.documento.NotificacionDTO;
import mx.gob.segob.nsjp.dto.evidencia.EvidenciaDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioAudienciaDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoAudienciaDTO;
import mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoAudienciaIdDTO;
import mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoAudienciaDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.organizacion.OrganizacionDTO;
import mx.gob.segob.nsjp.dto.relacion.CatRelacionDTO;
import mx.gob.segob.nsjp.dto.resolutivo.ResolutivoDTO;
import mx.gob.segob.nsjp.dto.resolutivo.ResolutivoViewDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudAudienciaDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.service.audiencia.AdministrarAudienciaJAVSService;
import mx.gob.segob.nsjp.service.audiencia.AsignarJuezAudienciaService;
import mx.gob.segob.nsjp.service.audiencia.AsignarSalaTemporalService;
import mx.gob.segob.nsjp.service.audiencia.BuscaDistritosService;
import mx.gob.segob.nsjp.service.audiencia.CalcularCargaTrabajoAudienciaService;
import mx.gob.segob.nsjp.service.audiencia.CalcularCargaTrabajoService;
import mx.gob.segob.nsjp.service.audiencia.ConsultarAudienciaService;
import mx.gob.segob.nsjp.service.audiencia.ConsultarAudienciasPorEstatusService;
import mx.gob.segob.nsjp.service.audiencia.ConsultarComplejidadAudienciaService;
import mx.gob.segob.nsjp.service.audiencia.ConsultarDisponibilidadSalasService;
import mx.gob.segob.nsjp.service.audiencia.ConsultarEstadoPermisoService;
import mx.gob.segob.nsjp.service.audiencia.ConsultarEventosService;
import mx.gob.segob.nsjp.service.audiencia.ConsultarFuncionarioExternoAudienciaService;
import mx.gob.segob.nsjp.service.audiencia.ConsultarPermisosAudienciaService;
import mx.gob.segob.nsjp.service.audiencia.ConsultarResolutivosAudienciaService;
import mx.gob.segob.nsjp.service.audiencia.ConsultarTipoAudienciaPorIdentificadorService;
import mx.gob.segob.nsjp.service.audiencia.EliminarFuncionarioAudienciaService;
import mx.gob.segob.nsjp.service.audiencia.EliminarFuncionarioExternoAudienciaService;
import mx.gob.segob.nsjp.service.audiencia.FinalizarAudienciaService;
import mx.gob.segob.nsjp.service.audiencia.IngresarFuncionarioAudienciaService;
import mx.gob.segob.nsjp.service.audiencia.IngresarFuncionarioExternoAudienciaService;
import mx.gob.segob.nsjp.service.audiencia.IngresarInvolucradoAudienciaService;
import mx.gob.segob.nsjp.service.audiencia.MarcarAudienciaResolutivosService;
import mx.gob.segob.nsjp.service.audiencia.ProgramarAudienciaService;
import mx.gob.segob.nsjp.service.audiencia.RegistrarAsistenciaEnAudienciaService;
import mx.gob.segob.nsjp.service.audiencia.RegistrarResolutivoAudienciaService;
import mx.gob.segob.nsjp.service.documento.AsociarDocumentoAService;
import mx.gob.segob.nsjp.service.funcionario.ConsultarFuncionariosJuecesDisponiblesParaAudienciaService;
import mx.gob.segob.nsjp.service.funcionarioexterno.FuncionarioExternoService;
import mx.gob.segob.nsjp.service.graficacion.GraficaDeterminacionPorDenunciaService;
import mx.gob.segob.nsjp.service.involucrado.ConsultarIndividuoService;
import mx.gob.segob.nsjp.service.involucrado.RegistrarTestigoEnAudienciaService;
import mx.gob.segob.nsjp.service.objeto.ConsultarObjetosAudienciaService;
import mx.gob.segob.nsjp.service.objeto.RegistrarObjetoEnAudienciaService;
import mx.gob.segob.nsjp.service.solicitud.ConsultarSolicitudesAudienciaService;
import mx.gob.segob.nsjp.service.solicitud.ConsultarTipoSolicitudAudienciaService;
import mx.gob.segob.nsjp.service.solicitud.RegistrarSolicitudService;
import mx.gob.segob.nsjp.service.solicitud.SolicitudTranscripcionAudienciaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementaci&oacute;n del delegate para las audiencias.
 * 
 * @version 1.0
 * @author vaguirre
 * 
 */
@Service("audienciaDelegate")
public class AudienciaDelegateImpl implements AudienciaDelegate {

	@Autowired
	private ConsultarEventosService consultarEventosService;
	@Autowired
	private ConsultarSolicitudesAudienciaService consultarSolicutdes;
	@Autowired
	private ConsultarDisponibilidadSalasService consultaDisponibilidadService;
	@Autowired
	private ConsultarAudienciaService cosultaAudienciasService;
	@Autowired
	private ConsultarPermisosAudienciaService cosultarPermisosAudienciaService;
	@Autowired
	private ConsultarEstadoPermisoService cosultarEstadosPermisoservice;
//	@Autowired
//	private ConsultarSolicitudService solicitudService;
	@Autowired
	private RegistrarSolicitudService registrarSolicitudService;
	@Autowired
	private AsignarSalaTemporalService asginarSalaTemporalService;
	@Autowired
	private ConsultarFuncionariosJuecesDisponiblesParaAudienciaService consultarFuncionarioService;
	@Autowired
	private ProgramarAudienciaService programarAudienciaService;
    @Autowired
    private ConsultarAudienciasPorEstatusService consultarAudienciasPorEstatusService;
    @Autowired
    private ConsultarResolutivosAudienciaService consultarResolutivosAudienciaService;
    @Autowired
    private MarcarAudienciaResolutivosService marcarAudienciaResolutivosService;
    @Autowired
    private AsignarJuezAudienciaService asignarJuezAudienciaService;
    @Autowired
    private ConsultarObjetosAudienciaService consultarObjetoEnAudienciaService;
    @Autowired
    private RegistrarTestigoEnAudienciaService registrarTestigoEnAudiencia;
    @Autowired
    private FinalizarAudienciaService finalizarAudienciaService;
    @Autowired
    private SolicitudTranscripcionAudienciaService solicitudTranscripcionAudienciaService;
    @Autowired
    private RegistrarObjetoEnAudienciaService registrarObjetoEnAudiencia;
    @Autowired
    private ConsultarTipoAudienciaPorIdentificadorService consultarTipoAudienciaPorIdentificadorService;
    @Autowired
    private ConsultarComplejidadAudienciaService consultarComplejidadAudienciaService;
    @Autowired
    private CalcularCargaTrabajoService calcularCargaTrabajoService;
    @Autowired
    CalcularCargaTrabajoAudienciaService calcularCargaTrabajoAudienciaService;
    @Autowired
    private GraficaDeterminacionPorDenunciaService graficaDeterminacionPorDenunciaService;
    @Autowired
    private IngresarFuncionarioAudienciaService ingresarFuncionario;
    @Autowired
    private RegistrarAsistenciaEnAudienciaService registrarAsistenciaEnAudienciaService;
    @Autowired
    private IngresarInvolucradoAudienciaService ingresarInvolucradoAudienciaService;
    @Autowired
    private ConsultarTipoSolicitudAudienciaService consultarTipoSolicitudAudienciaService; 
    @Autowired
    private AdministrarAudienciaJAVSService administrarAudienciaJAVSService;
    @Autowired 
    private AsociarDocumentoAService asociarDocumentoAService; 
    @Autowired
    private EliminarFuncionarioAudienciaService eliminarFuncionarioAudienciaService;
    @Autowired
    private ConsultarIndividuoService consultarIndividuoService;
    @Autowired
    private ConsultarFuncionarioExternoAudienciaService consultarFuncionarioExternoAudienciaService;
    @Autowired
    private FuncionarioExternoService funcionarioExternoService;
    @Autowired
    private IngresarFuncionarioExternoAudienciaService ingresarFuncionarioExternoService;
    @Autowired
    private EliminarFuncionarioExternoAudienciaService eliminarFuncionarioExternoAudienciaService;
    @Autowired
    private BuscaDistritosService buscaDistritosService;
    
    @Override
    public List<EventoDTO> consultarEventosParaNotificar(EventoDTO input)
            throws NSJPNegocioException {
        return this.consultarEventosService.consultarEventos(input);
    }

	@Autowired
	private RegistrarResolutivoAudienciaService registrarResolutivoAudienciaService;

	@Override
	public EventoDTO obtenerEvento(EventoDTO input) throws NSJPNegocioException {
		return this.consultarEventosService.obtenerEvento(input);
	}

	@Override
	public List<SolicitudAudienciaDTO> consultarSolicitudesAudiencia(
			UsuarioDTO filtro) throws NSJPNegocioException {
		return consultarSolicutdes.consultarSolicitudesAudiencia(filtro);
	}

	@Override
	public MesDisponibilidadDTO consultarDisponibilidadPorMes(
			MesDisponibilidadDTO filtro) throws NSJPNegocioException {
		return this.consultaDisponibilidadService
				.consultarDisponibilidadSalas(filtro);
	}

	@Override
	public List<SolicitudDTO> consultarOtrasSolicitudes(UsuarioDTO filtro)
			throws NSJPNegocioException {
		return consultarSolicutdes.consultarOtrasSolicitudes(filtro);
	}

	@Override
	public DiaDisponibilidadDTO consultarDisponibilidadPorDia(
			DiaDisponibilidadDTO filtro) throws NSJPNegocioException {
		return this.consultaDisponibilidadService
				.consultarDisponibilidadDiaSalas(filtro);
	}

	@Override
	public  List<SalaAudienciaDTO> obtenerNombresSalas(UsuarioDTO usr)
			throws NSJPNegocioException {
		return consultaDisponibilidadService.obtenerNombresSalas(usr);
	}

	@Override
	public List<AudienciaDTO> buscarAudiencias(FiltroAudienciaDTO filtro)
			throws NSJPNegocioException {
		return cosultaAudienciasService.buscarAudiencias(filtro);
	}
	
	@Override
	public List<PermisoAudienciaDTO> buscarPermisosAudienciasPorEstadoYFecha(Long estado, Date fechaInicio, Date fechaFin, Long discriminanteId)
			throws NSJPNegocioException {
		return cosultarPermisosAudienciaService.buscarPermisosAudienciaPorEstado(estado, fechaInicio, fechaFin, discriminanteId);
	}
	
	@Override
	public List<BitacoraDescargaDTO> buscarBitacoraDescargaPorFecha(Date fechaInicio, Date fechaFin, Long discriminanteId)
			throws NSJPNegocioException {
		return cosultarPermisosAudienciaService.buscarBitacoraDescargaPorFecha(fechaInicio, fechaFin, discriminanteId);
	}
	
	@Override
	public List<BitacoraDescargaDTO> buscarBitacoraDescargaPorAudiencia(Long audiencia, Long discriminanteId)
			throws NSJPNegocioException {
		return cosultarPermisosAudienciaService.buscarBitacoraDescargaPorAudiencia(audiencia, discriminanteId);
	}

	@Override
	public Boolean esAudienciaJAVS(Long audienciaId)
			throws NSJPNegocioException {
		return administrarAudienciaJAVSService.esJAVS(audienciaId);
	}
	
	@Override
	public Long solicitarPermiso(Long audienciaId, UsuarioDTO usuarioDTO, Date fechaHoy)
			throws NSJPNegocioException {
		return administrarAudienciaJAVSService.generarPermiso(audienciaId, usuarioDTO, fechaHoy);
	}
	
	@Override
	public Long solicitarPermisoExterno(Long audiencia, UsuarioDTO usuarioDTO, Long ConfInstId)
			throws NSJPNegocioException {
		return administrarAudienciaJAVSService.generarPermisoExterno(audiencia, usuarioDTO, ConfInstId);
	}
	
	@Override
	public Long verificarPermiso(Long audienciaId, UsuarioDTO usuarioDTO)
			throws NSJPNegocioException {
		return administrarAudienciaJAVSService.verificarPermiso(audienciaId, usuarioDTO);
	}

	@Override
	public AudienciaJAVSTransporteDTO verificarPermisoExterno(Long audienciaId, UsuarioDTO usuarioDTO, ConfInstitucionDTO confInstDto)
			throws NSJPNegocioException {
		return administrarAudienciaJAVSService.verificarPermisoExterno(audienciaId, usuarioDTO, confInstDto);
	}
	
	@Override
	public List<CatEstadoPermisoDTO> buscarEstadosPermisos()
			throws NSJPNegocioException {
		return cosultarEstadosPermisoservice.buscarEstadosPermiso();
	}
	
	@Override
	public Long cambiarEstadoPermisoAudiencia(Long estado, Long permisoAudiencia, Date fechaHoy, String claveUsuarioAsignador)
			throws NSJPNegocioException {
		
		return cosultarPermisosAudienciaService.cambiarEstadoPermisoAudiencia(estado, permisoAudiencia, fechaHoy, claveUsuarioAsignador);
	}

    public List<AudienciaDTO> buscarAudienciasSinTranscripcionResolutivos() throws NSJPNegocioException{
    	return cosultaAudienciasService.buscarAudienciasSinTranscripcionResolutivos();
    }

	@Override
	public AudienciaDTO obtenerAudiencia(AudienciaDTO input)
			throws NSJPNegocioException {
		return cosultaAudienciasService.obtenerAudiencia(input);
	}

	@Override
	public AudienciaDTO obtenerAudiencia(SolicitudAudienciaDTO solAudIn)
			throws NSJPNegocioException {
		return this.cosultaAudienciasService.obtenerAudiencia(solAudIn);
	}

	@Override
	@Transactional
	public SolicitudDTO registrarSolicitud(SolicitudDTO input)
			throws NSJPNegocioException {
		return this.registrarSolicitudService.registrarSolicitud(input);
	}

	@Override
	public void asignarSalaTemporal(AudienciaDTO audConSala)
			throws NSJPNegocioException {
		this.asginarSalaTemporalService.asignarSalaTemporal(audConSala);

	}

	@Override
	public List<FuncionarioDTO> consultarJuecesDisponiblesParaFechaYHoraAudiencia(
			Date fecha, Integer duracionEstimada, UsuarioDTO usuarioDto) throws NSJPNegocioException {
		return consultarFuncionarioService
				.consultarJuecesDisponiblesParaFechaYHoraAudiencia(fecha,
						duracionEstimada, usuarioDto);
	}

	@Override
	public List<AudienciaDTO> consultarAudienciasPorEstatus(Long estatus)
			throws NSJPNegocioException {
		return consultarAudienciasPorEstatusService.consultarAudienciasPorEstatus(estatus);
	}

	@Override
	public List<AudienciaDTO> consultarAudienciasFromPoderJudicial(AudienciaDTO audiencia)throws NSJPNegocioException{
		return consultarAudienciasPorEstatusService.consultarAudienciasFromPoderJudicial(audiencia);
	}

	@Override
	public List<AudienciaDTO> consultarAudienciasFromPoderJudicial(
			AudienciaDTO audiencia, Long claveFuncionarioExt,
			String cadenaEstatus, String cadenaTipo, Long confInstId)throws NSJPNegocioException{
		return consultarAudienciasPorEstatusService.consultarAudienciasFromPoderJudicial(
				audiencia, claveFuncionarioExt, cadenaEstatus, cadenaTipo, confInstId);
	}

	@Override
	public AudienciaDTO consultarAudienciaFromPoderJudicial(AudienciaDTO audiencia)throws NSJPNegocioException{
		return consultarAudienciasPorEstatusService.consultarAudienciaFromPoderJudicial(audiencia);
	}
	
	@Override
	public List<ResolutivoDTO> consultarResolutivosAudiencia(Long idAudiencia)
			throws NSJPNegocioException {
		return consultarResolutivosAudienciaService.consultarResolutivosAudiencia(idAudiencia);
	}

	@Override
	public void marcarAudienciaResolutivos(Long idAudiencia)
			throws NSJPNegocioException {
		marcarAudienciaResolutivosService.marcarAudienciaResolutivos(idAudiencia);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.gob.segob.nsjp.delegate.audiencia.AudienciaDelegate#
	 * consultarJuezAutomaticoADesignar(java.util.Date, java.lang.Integer)
	 */
	@Override
	public List<FuncionarioDTO> consultarJuezAutomaticoADesignar(Date fecha,
			Integer duracionEstimada,AudienciaDTO audiencia,boolean juezSustituto, FuncionarioDTO funcionarioDTO) throws NSJPNegocioException {
		return consultarFuncionarioService.consultarJuezAutomaticoADesignar(
				fecha, duracionEstimada, audiencia,juezSustituto, funcionarioDTO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.gob.segob.nsjp.delegate.audiencia.AudienciaDelegate#
	 * guardarProgramacionAudiencia
	 * (mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO)
	 */
	@Override
	public void guardarProgramacionAudiencia(AudienciaDTO audiencia)
			throws NSJPNegocioException {
		programarAudienciaService.guardarProgramacionAudiencia(audiencia);
	}

	@Override
	public void asignarJuezAudiencia(AudienciaDTO audiencia,
			List<FuncionarioDTO> jueces) throws NSJPNegocioException {
		asignarJuezAudienciaService.asignarJuezAudiencia(audiencia, jueces);
	}
	
	@Override
	public void registrarTestigoEnAudiencia(AudienciaDTO audiencia,
			InvolucradoDTO testigo) throws NSJPNegocioException {
		registrarTestigoEnAudiencia.registrarTestigoEnAudiencia(audiencia,
				testigo);

	}

	@Override
	public void registrarObjetoEnAudiencia(Long audienciaId, Long insitutcion,
			String descripcion, Long condicionFisica, String noCustodia, Long noPrueba)
			throws NSJPNegocioException {
		
		registrarObjetoEnAudiencia.registrarObjetoEnAudiencia(audienciaId, insitutcion,
				descripcion, condicionFisica, noCustodia, noPrueba);
	}

	public Long registrarResolutivoAudiencia(ResolutivoDTO resolutivo) throws NSJPNegocioException {
		return registrarResolutivoAudienciaService.registrarResolutivoAudiencia(resolutivo);
	}

	@Override
	public void modificarResolutivoAudiencia(ResolutivoDTO resolutivo)
			throws NSJPNegocioException {
		registrarResolutivoAudienciaService.modificarResolutivoAudiencia(resolutivo);
	}

	@Override
	public void eliminarResolutivoAudiencia(ResolutivoDTO resolutivo)
			throws NSJPNegocioException {
		registrarResolutivoAudienciaService.eliminarResolutivoAudiencia(resolutivo);
	}
	
	public List<ResolutivoViewDTO> leerResolutivosAudiencia(Long idAudiencia) throws NSJPNegocioException{
		return consultarResolutivosAudienciaService.consultarResolutivosByAudienciaId(idAudiencia);
	}
	
	@Override
	public List<EvidenciaDTO> consultarObjetosAudiencia(EventoDTO input)
			throws NSJPNegocioException {
		return consultarObjetoEnAudienciaService.consultarObjetosEnAudiencia(input);
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.audiencia.AudienciaDelegate#consultarTipoAudienciaPorIdentificador(mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO)
	 */
	@Override
	public ValorDTO consultarTipoAudienciaPorIdentificador(
			AudienciaDTO audienciaDTO) throws NSJPNegocioException {
		return consultarTipoAudienciaPorIdentificadorService.consultarTipoAudienciaPorIdentificador(audienciaDTO);
	}

	@Override
	public ValorDTO consultarComplejidadAudiencia(AudienciaDTO audienciaDTO)
			throws NSJPNegocioException {
		return consultarComplejidadAudienciaService.consultarComplejidadAudiencia(audienciaDTO);
	}
	
	public void actualizarComplejidadAudiencia(Long tipoAudiencia, Long complejidad) throws NSJPNegocioException{
		consultarComplejidadAudienciaService.actualizarComplejidadAudiencia(tipoAudiencia, complejidad);
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.audiencia.AudienciaDelegate#calcularCargaTrabajo(java.lang.Long, java.lang.Long)
	 */
	@Override
	public Double calcularCargaTrabajo(Long complejidadAudiencia,
			Long sumatoriaPRDelito) throws NSJPNegocioException {
		return calcularCargaTrabajoService.calcularCargaTrabajo(complejidadAudiencia, sumatoriaPRDelito);
	}
	
	public Double calcularCargaTrabajo(AudienciaDTO audienciaDTO)throws NSJPNegocioException{
		return calcularCargaTrabajoAudienciaService.calcularCargaTrabajoAudiencia(audienciaDTO);
	}
	
	public void finalizarAudiencia(AudienciaDTO audiencia)
			throws NSJPNegocioException {
		finalizarAudienciaService.finalizarAudienciaService(audiencia);
	}


	public AudienciaDTO consultarAudienciaBySolicictudTranscripcionId(
			SolicitudDTO solicitud) throws NSJPNegocioException {
		
		return solicitudTranscripcionAudienciaService.consultarAudienciaDeSolicitudTranscripcion(solicitud);
	}

	@Override
	public Boolean validarExistenciaPruebas(AudienciaDTO audienciaDTO)
			throws NSJPNegocioException {
		return cosultaAudienciasService.validarExistenciaPruebas(audienciaDTO);
	}

	@Override
	public List<AudienciaDTO> consultarAudienciasByTipoYFecha(
			AudienciaDTO aunAudienciaDTO, TipoAudiencia tipoAudiencia,UsuarioDTO usuario)
			throws NSJPNegocioException {	
		return this.cosultaAudienciasService.consultarAudienciasByTipoYFecha(aunAudienciaDTO, tipoAudiencia,usuario);
	}

	@Override
	public AudienciaDTO obtenerAudienciaByNumeroAudiencia(
			AudienciaDTO audienciaDTO) throws NSJPNegocioException {		
		return this.cosultaAudienciasService.obtenerAudienciaByNumeroAudiencia(audienciaDTO);
	}

	@Override
	public List<AudienciaDTO> consultarAudienciaByNumeroExpedienteYEstatus(
			ExpedienteDTO expedienteDTO, EstatusAudiencia estatusAudiencia)
			throws NSJPNegocioException {		
		return cosultaAudienciasService.consultarAudienciaByNumeroExpedienteYEstatus(expedienteDTO, estatusAudiencia);
	}
	
	@Override
	public List<AudienciaDTO> consultarAudienciaByNumeroExpedienteYListaEstatus(
			ExpedienteDTO expedienteDTO, EstatusAudiencia[] estatusAudiencia)
			throws NSJPNegocioException {		
		return cosultaAudienciasService.consultarAudienciaByNumeroExpedienteYListaEstatus(expedienteDTO, estatusAudiencia);
	}
	
	/*
	 * (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.audiencia.AudienciaDelegate#consultarAudienciasConSolicitudesPorTipoYEstado(mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes[], mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud[])
	 */
	@Override
	public List<AudienciaDTO> consultarAudienciasConSolicitudesPorTipoYEstado(
			TiposSolicitudes[] tipos, EstatusSolicitud[] estados,UsuarioDTO usuario)
			throws NSJPNegocioException {
		return cosultaAudienciasService.consultarAudienciasConSolicitudesPorTipoYEstado(tipos, estados,usuario);
	}

	@Override
	public List<AudienciaDTO> consultarAudienciasByTipoYEstatus(
			TipoAudiencia tipoAudiencia, EstatusAudiencia estatusAudiencia)
			throws NSJPNegocioException {
		
		return cosultaAudienciasService.consultarAudienciasByTipoYEstatus(tipoAudiencia, estatusAudiencia);
	}

	@Override
	public List<AudienciaDTO> consultarAudienciasByFechasyEstatus(AudienciaDTO audiencia) throws NSJPNegocioException{
		return cosultaAudienciasService.consultarAudienciasByFechasyEstatus(audiencia);
	}

	@Override
	public List<Object[]> obtenerAudienciasJudicializadasPorMes(
			Date fechaInicial, Date fechaFin) throws NSJPNegocioException {		
		return graficaDeterminacionPorDenunciaService.obtenerAudienciasJudicializadasPorMes(fechaInicial, fechaFin);
	}

    @Override
    public void ingresarFuncionarioAudiencia(FuncionarioDTO funcionario,
            AudienciaDTO audiencia) throws NSJPNegocioException {
       this.ingresarFuncionario.ingresarFuncionarioAudiencia(funcionario, audiencia);
        
    }
    
    @Override
    public void eliminarFuncionarioAudiencia(FuncionarioDTO funcionario,
            AudienciaDTO audiencia) throws NSJPNegocioException {
       this.eliminarFuncionarioAudienciaService.eliminarFuncionarioAudiencia(funcionario, audiencia);
        
    }

	@Override
	public void registrarAsistenciaInvolucrado(Long involucradoId,
			Long audienciaId, boolean presente) throws NSJPNegocioException {

		registrarAsistenciaEnAudienciaService.registrarAsistenciaInvolucrado(involucradoId, audienciaId, presente);
		
	}

	@Override
	public void registrarAsistenciaFuncionario(Long claveFuncionario,
			Long audienciaId, boolean presente, Boolean esTitular) throws NSJPNegocioException {
		registrarAsistenciaEnAudienciaService.registrarAsistenciaFuncionario(claveFuncionario, audienciaId, presente,esTitular);
		
	}
	
	@Override
	public void registrarAsistenciaFuncionarioExterno(FuncionarioExternoAudienciaDTO funcionarioExternoAudienciaDTO) throws NSJPNegocioException {
		registrarAsistenciaEnAudienciaService.registrarAsistenciaFuncionarioExterno(funcionarioExternoAudienciaDTO);
	}
	
	@Override
	public FuncionarioAudienciaDTO consultarAsistenciaFuncionario(Long claveFuncionario, Long audienciaId) 
		throws NSJPNegocioException {
		return registrarAsistenciaEnAudienciaService.consultarAsistenciaFuncionario(claveFuncionario, audienciaId);
		
	}
	
	@Override
	public FuncionarioExternoAudienciaDTO consultarFuncionarioExternoAudienciaPorId(
			FuncionarioExternoAudienciaIdDTO funcionarioExternoAudienciaIdDTO)
			throws NSJPNegocioException {
		return consultarFuncionarioExternoAudienciaService
				.ConsultarFuncionarioExternoAudienciaPorId(funcionarioExternoAudienciaIdDTO);

	}
	
	@Override
	public InvolucradoAudienciaDTO consultarAsistenciaInvolucradoAudiencia(Long claveInvolucradoAudiencia, Long audienciaId) 
		throws NSJPNegocioException {
		return registrarAsistenciaEnAudienciaService.consultarAsistenciaInvolucradoAudiencia(claveInvolucradoAudiencia, audienciaId);
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.audiencia.AudienciaDelegate#asociarInvolucradoAAudiencia(java.lang.Long, java.lang.Long)
	 */
	@Override
	public void asociarInvolucradoAAudiencia(Long involucradoId,
			Long AudienciaId) throws NSJPNegocioException {
		ingresarInvolucradoAudienciaService.asociarInvolucradoAAudiencia(involucradoId, AudienciaId);
		
	}

	@Override
	public Long crearAudienciaSiguiente(AudienciaDTO audienciaDTO)
			throws NSJPNegocioException {
		return programarAudienciaService.crearAudienciaSiguiente(audienciaDTO);
	}
	
	@Override
	public List<CatalogoDTO> consultarTipoSolicitudAudienciaSiguientes(TipoAudiencia audienciaActual ) throws NSJPNegocioException {
		return consultarTipoSolicitudAudienciaService.consultarTipoSolicitudAudienciaSiguientes(audienciaActual);
	}

	@Override
	public Long agendarAudiencia(Long idAudiencia) throws NSJPNegocioException {
		return administrarAudienciaJAVSService.agendarAudiencia(idAudiencia);
	}
	
	@Override
	public Long reagendarAudiencia(Long idAudiencia) throws NSJPNegocioException {
		return administrarAudienciaJAVSService.reagendarAudiencia(idAudiencia);
	}

	@Override
	public Long consultarAudiencia(Long idAudiencia)
			throws NSJPNegocioException {
		return administrarAudienciaJAVSService.consultarAudiencia(idAudiencia);
	}

	@Override
	public Long eliminarAudiencia(Long idAudiencia)
			throws NSJPNegocioException {
		return administrarAudienciaJAVSService.eliminarAudiencia(idAudiencia);
	}

	@Override
	public DocumentoDTO asociarDocumentoAAudiencia(AudienciaDTO audienciaDTO,
			DocumentoDTO documentoDTO) throws NSJPNegocioException {
		return asociarDocumentoAService.asociarDocumentoAAudiencia(audienciaDTO, documentoDTO);
	}
	
	public Long cancelarAudiencia(Long idAudiencia)
	throws NSJPNegocioException {
		return administrarAudienciaJAVSService.cancelarAudiencia(idAudiencia);
	}

	public boolean cancelarAudienciaJAVS(Long idAudiencia) throws NSJPNegocioException {
		return administrarAudienciaJAVSService.cancelarAudienciaJAVS(idAudiencia);
	}
	
	public Long cancelarAudienciaSistema(Long idAudiencia)
			throws NSJPNegocioException {
				return administrarAudienciaJAVSService.cancelarAudienciaSistema(idAudiencia);
	}

	public Long consultarEstadoAudiencia(Long idAudiencia)
			throws NSJPNegocioException {
				return administrarAudienciaJAVSService.consultarEstadoAudiencia(idAudiencia);
	}

	public String datosSalaJAVS(Long audienciaId)
			throws NSJPNegocioException {
				return administrarAudienciaJAVSService.laSalaJAVS(audienciaId); 
	}

	public String obteniendoPathsJAVS(Long audienciaId)
			throws NSJPNegocioException {
				return administrarAudienciaJAVSService.obteniendoPathsJAVS(audienciaId); 
	}

	public byte[] generandoRegistroMaestroJVL(Long audienciaId)
			throws NSJPNegocioException {
				return administrarAudienciaJAVSService.generandoRegistroMaestroJVL(audienciaId);
	}
	
	public Long conglomeradoJAVS(Long audienciaId)
	throws NSJPNegocioException {
		return administrarAudienciaJAVSService.generandoConglomeradoJAVS(audienciaId);
	}

	public List<SolicitudAudienciaDTO> consultarSolicitudesAudienciaConCriterios (
			SolicitudAudienciaDTO solicitudAudienciaDTO,
			List<Long> lstIdEstatusSolicitud, List<Long> lstIdTipoSolicitud,
			List<Long> lstIdEstatusAudiencia, List<Long> lstIdTipoAudiencia,
			String tipoConsulta
			) throws NSJPNegocioException {
		return consultarSolicutdes.
		consultarSolicitudesAudienciaConCriterios(
				solicitudAudienciaDTO, 
				lstIdEstatusSolicitud,
				lstIdTipoSolicitud, 
				lstIdEstatusAudiencia,				
				lstIdTipoAudiencia, 
				tipoConsulta);
	}
	
	public void actualizaCaracterAudiencia(Long audienciaId, Boolean esPublica) throws NSJPNegocioException {
		administrarAudienciaJAVSService.actualizaCaracterAudiencia(audienciaId, esPublica);
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.audiencia.AudienciaDelegate#consultarAudienciasPJByEstadoYNumCaso(mx.gob.segob.nsjp.dto.catalogo.ValorDTO, mx.gob.segob.nsjp.dto.caso.CasoDTO)
	 */
//	@Override
//	public List<AudienciaDTO> consultarAudienciasPJByEstadoYNumCaso(
//			ValorDTO estado, CasoDTO caso) throws NSJPNegocioException {
//		return consultarAudienciasPorEstatusService.consultarAudienciasPJByEstadoYNumCaso(estado, caso);
//	}	
//	
//	public void actualizaCaracterAudiencia(Long audienciaId, Boolean esPublica) throws NSJPNegocioException {
//		administrarAudienciaJAVSService.actualizaCaracterAudiencia(audienciaId, esPublica);
//	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.audiencia.AudienciaDelegate#asociarOrganizacionAAudiencia(java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	@Override
	public void asociarOrganizacionAAudiencia(Long organizacionId,
			Long audienciaId, Long involucradoId) throws NSJPNegocioException {
		
		if (organizacionId == 0L && involucradoId > 0L){
			InvolucradoDTO involucrado = new InvolucradoDTO();
			involucrado.setElementoId(involucradoId);
			involucrado.setTipoPersona(0L);
			involucrado = consultarIndividuoService.obtenerIndividuoDTO(involucrado);
			if (involucrado.getOrganizacionDTO() != null){
				organizacionId = involucrado.getOrganizacionDTO().getElementoId();
			}
		}
		
		ingresarInvolucradoAudienciaService.asociarOrganizacionAAudiencia(organizacionId, audienciaId);
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.audiencia.AudienciaDelegate#consultarOrganizacionesAudiencia(mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO)
	 */
	@Override
	public List<OrganizacionDTO> consultarOrganizacionesAudiencia(
			AudienciaDTO audiencia) throws NSJPNegocioException {
		return ingresarInvolucradoAudienciaService.consultarOrganizacionesAudiencia(audiencia);
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.audiencia.AudienciaDelegate#obtenerInvolucradoByRelacion(mx.gob.segob.nsjp.dto.organizacion.OrganizacionDTO, mx.gob.segob.nsjp.dto.relacion.CatRelacionDTO)
	 */
	@Override
	public InvolucradoDTO obtenerInvolucradoByRelacion(
			OrganizacionDTO organizacionDTO, CatRelacionDTO catRelacionDTO)
			throws NSJPNegocioException {
		return ingresarInvolucradoAudienciaService.obtenerInvolucradoByRelacion(organizacionDTO, catRelacionDTO);
	}

	@Override
	public List<EventoDTO> consultarEventosPorEstatusNotificacion(
			NotificacionDTO input) throws NSJPNegocioException {
		return this.consultarEventosService
				.consultarEventosPorEstatusNotificacion(input);
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.audiencia.AudienciaDelegate#consultarComplejidadTipoAudiencia(mx.gob.segob.nsjp.dto.catalogo.ValorDTO)
	 */
	@Override
	public ValorDTO consultarComplejidadTipoAudiencia(ValorDTO tipoAudiencia)
			throws NSJPNegocioException {
		return consultarComplejidadAudienciaService.consultarComplejidadTipoAudiencia(tipoAudiencia);
	}

	@Override
	public List<FuncionarioExternoDTO> consultarFuncionarioExternoAudienciaPorRol(
			FuncionarioExternoAudienciaDTO funcionarioExternoAudienciaDTO)
			throws NSJPNegocioException {
		return consultarFuncionarioExternoAudienciaService.consultarFuncionarioExternoAudienciaPorRol(funcionarioExternoAudienciaDTO);
	}
	
	@Override
	public List<FuncionarioExternoDTO> consultarFuncionarioExternoAudienciaNotificaciones(
			AudienciaDTO audienciaDTO)
			throws NSJPNegocioException {
		return consultarFuncionarioExternoAudienciaService.consultarFuncionarioExternoAudienciaNotificaciones(audienciaDTO);
	}

	@Override
	public List<FuncionarioExternoDTO> consultarFuncionarioExternoPorFiltroNoAsociadoALaAudiencia(
			FuncionarioExternoDTO funcionarioExternoDTO,
			AudienciaDTO audienciaDTO) throws NSJPNegocioException {

		return funcionarioExternoService.consultarFuncionarioExternoPorFiltroNoAsociadoALaAudiencia(funcionarioExternoDTO, audienciaDTO);
	}

	@Override
	public FuncionarioExternoAudienciaDTO ingresarFuncionarioExternoAudiencia(
			FuncionarioExternoDTO funcionarioExternoDTO,
			AudienciaDTO audienciaDTO, Boolean ingresarActualizarFuncExterno)
			throws NSJPNegocioException {
		return ingresarFuncionarioExternoService
				.ingresarFuncionarioExternoAudiencia(funcionarioExternoDTO,
						audienciaDTO, ingresarActualizarFuncExterno);
	}

	@Override
	public void eliminarFuncionarioExternoAudiencia(
			FuncionarioExternoAudienciaIdDTO funcionarioExternoAudienciaIdDTO)
			throws NSJPNegocioException {
		eliminarFuncionarioExternoAudienciaService
				.eliminarFuncionarioExternoDeAudiencia(funcionarioExternoAudienciaIdDTO);
	}	
	@Override
	public CatDistritoDTO buscaDistritosPorDiscriminante(Long discriminante){
		return buscaDistritosService.buscaDistritosPorDiscriminante(discriminante);
	}
}
