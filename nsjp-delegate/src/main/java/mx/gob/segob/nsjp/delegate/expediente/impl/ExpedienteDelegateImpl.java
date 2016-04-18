/**
 *
 * Nombre del Programa : ExpedienteDelegate.java                                    
 * Autor                            : Cesar Agustin                                               
 * Compania                    : Ultrasist                                                
 * Proyecto                      : NSJP                    Fecha: 30/03/2011 
 * Marca de cambio        : N/A                                                     
 * Descripcion General    : Implementaci&oacute;n del delegate para los servicios de expediente                       
 * Programa Dependiente  :N/A                                                      
 * Programa Subsecuente :N/A                                                      
 * Cond. de ejecucion        :N/A                                                      
 * Dias de ejecucion          :N/A                             Horario: N/A       
 *                              MODIFICACIONES                                       
 *------------------------------------------------------------------------------           
 * Autor                       :N/A                                                           
 * Compania               :N/A                                                           
 * Proyecto                 :N/A                                   Fecha: N/A       
 * Modificacion           :N/A                                                           
 *------------------------------------------------------------------------------           
 */
package mx.gob.segob.nsjp.delegate.expediente.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.expediente.EstatusExpediente;
import mx.gob.segob.nsjp.comun.enums.expediente.EtapasExpediente;
import mx.gob.segob.nsjp.comun.enums.expediente.OrigenExpediente;
import mx.gob.segob.nsjp.comun.enums.expediente.TipoDeBusquedaDeExpediente;
import mx.gob.segob.nsjp.comun.enums.expediente.TipoExpediente;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.delegate.expediente.ExpedienteDelegate;
import mx.gob.segob.nsjp.dto.ActividadDTO;
import mx.gob.segob.nsjp.dto.caso.CasoDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatEtapaDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.expediente.ActaCircunstanciadaDTO;
import mx.gob.segob.nsjp.dto.expediente.BitacoraEstatusNumExpedienteDTO;
import mx.gob.segob.nsjp.dto.expediente.BitacoraPermisoExpedienteDTO;
import mx.gob.segob.nsjp.dto.expediente.DatosGeneralesExpedienteDTO;
import mx.gob.segob.nsjp.dto.expediente.DatosGeneralesExpedienteUAVDDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoPersonaDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteViewDTO;
import mx.gob.segob.nsjp.dto.expediente.FiltroExpedienteDTO;
import mx.gob.segob.nsjp.dto.expediente.HistoricoExpedienteDTO;
import mx.gob.segob.nsjp.dto.expediente.NotaExpedienteDTO;
import mx.gob.segob.nsjp.dto.expediente.RelNumExpedienteAuditoriaDTO;
import mx.gob.segob.nsjp.dto.expediente.TurnoDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.institucion.AreaDTO;
import mx.gob.segob.nsjp.dto.institucion.InstitucionDTO;
import mx.gob.segob.nsjp.dto.institucion.JerarquiaOrganizacionalDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDTO;
import mx.gob.segob.nsjp.dto.usuario.RolDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.service.actividad.CanalizarCausaService;
import mx.gob.segob.nsjp.service.catalogo.ConsultarEtapasService;
import mx.gob.segob.nsjp.service.expediente.ActualizarExpedienteService;
import mx.gob.segob.nsjp.service.expediente.ActualizarTipoExpedienteService;
import mx.gob.segob.nsjp.service.expediente.AdministrarNumeroExpedienteService;
import mx.gob.segob.nsjp.service.expediente.AsignarNumeroExpedienteService;
import mx.gob.segob.nsjp.service.expediente.AsociarDocumentoCausaTOCAService;
import mx.gob.segob.nsjp.service.expediente.BuscarExpedienteService;
import mx.gob.segob.nsjp.service.expediente.ConsultarActaCircunstanciadaService;
import mx.gob.segob.nsjp.service.expediente.ConsultarBandejaVisitaduriaService;
import mx.gob.segob.nsjp.service.expediente.ConsultarCausasPorIdCasoService;
import mx.gob.segob.nsjp.service.expediente.ConsultarDetalleExpedienteService;
import mx.gob.segob.nsjp.service.expediente.ConsultarExpedientesPorEtapaService;
import mx.gob.segob.nsjp.service.expediente.ConsultarNotasPorExpedienteService;
import mx.gob.segob.nsjp.service.expediente.ConsultarRelNumExpedientePorIdAuditoriaService;
import mx.gob.segob.nsjp.service.expediente.EnviarCarpetaDeInvestigacionProcuraduriaService;
import mx.gob.segob.nsjp.service.expediente.GenerarExpedienteJARService;
import mx.gob.segob.nsjp.service.expediente.RegistarNotasExpedienteService;
import mx.gob.segob.nsjp.service.expediente.RegistrarActaCircunstanciadaService;
import mx.gob.segob.nsjp.service.expediente.RegistrarPermisoExpedienteService;
import mx.gob.segob.nsjp.service.graficacion.GraficaDenunciaVSTipoDelitoService;
import mx.gob.segob.nsjp.service.graficacion.GraficaDeterminacionPorDenunciaService;
import mx.gob.segob.nsjp.service.institucion.JerarquiaOrganizacionalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author CesarAgustin
 * @version 1.0
 */
@Service("expedienteDelegate")
public class ExpedienteDelegateImpl implements ExpedienteDelegate {

	
	@Autowired
	private BuscarExpedienteService service;
	@Autowired
	private AsignarNumeroExpedienteService asignarNoService;
	@Autowired
	private GenerarExpedienteJARService generarExpedienteJARService;
	@Autowired
	private AsociarDocumentoCausaTOCAService asociarDocumentoCausaTOCAService;
	@Autowired
	private AdministrarNumeroExpedienteService administrarNumeroExpediente;
	@Autowired
	private JerarquiaOrganizacionalService jerarquiaOrganizacionalService;
	@Autowired
	private ConsultarExpedientesPorEtapaService consultarExpedientesPorEtapaService;
	@Autowired
	private ActualizarTipoExpedienteService actualizarTipoExpedienteService;
	@Autowired
	private RegistarNotasExpedienteService registarNotasExpedienteService;
	@Autowired
	private ConsultarNotasPorExpedienteService consultarNotasPorExpedienteService;
	@Autowired
	private CanalizarCausaService canalizarCausaService;
	@Autowired
	private RegistrarActaCircunstanciadaService registrarActaCircunstanciadaService;
	@Autowired
	private ConsultarCausasPorIdCasoService consultarCausasPorIdCasoService;
	@Autowired
	private ConsultarActaCircunstanciadaService consultarActaCircunstanciadaService;
	@Autowired
	private ConsultarDetalleExpedienteService consultarDetalleExpedienteService;
	@Autowired
	private EnviarCarpetaDeInvestigacionProcuraduriaService enviarCarpetaDeInvestigacionProcuraduriaService;
	@Autowired
	private ActualizarExpedienteService actualizarExpedienteService;
	@Autowired
	private GraficaDenunciaVSTipoDelitoService graficaDenunciaVSTipoDelitoService;
	@Autowired
	private GraficaDeterminacionPorDenunciaService graficaDeterminacionPorDenunciaService;
	@Autowired
	private ConsultarBandejaVisitaduriaService consultarBandejaVisitaduriaService;	
	@Autowired
	private ConsultarRelNumExpedientePorIdAuditoriaService consultarRelNumExpedientePorIdAuditoriaService;
	@Autowired
	private RegistrarPermisoExpedienteService registrarPermisoExpedienteService;
	@Autowired
	private ConsultarEtapasService consultarEtapasService; 
	
	@Override
	public List<ExpedienteDTO> buscarExpedientes(
			FiltroExpedienteDTO filtrosBusquedaExpediente)
			throws NSJPNegocioException {
		return service.buscarExpedientes(filtrosBusquedaExpediente);
	}
	
	@Override
	public List<ExpedienteDTO> buscarExpedientesPorNumExpDiscriminanteArea(
			FiltroExpedienteDTO filtrosBusquedaExpediente)
			throws NSJPNegocioException {
		return service.buscarExpedientesPorNumExpDiscriminanteArea(filtrosBusquedaExpediente);
	}

	@Override
	public ExpedienteDTO obtenerExpediente(ExpedienteDTO expedienteDTO)
			throws NSJPNegocioException {
		return service.obtenerExpediente(expedienteDTO);
	}
	
	@Override
	public ExpedienteDTO obtenerExpedienteDefensoria(ExpedienteDTO expedienteDTO)
			throws NSJPNegocioException {
		return service.obtenerExpedienteDefensoria(expedienteDTO);
	}

	@Override
	public List<ExpedienteDTO> consultarExpedientesPorCaso(CasoDTO caso)
			throws NSJPNegocioException {
		return this.service.consultarExpedientesPorCaso(caso);
	}

	@Override
	public List<InvolucradoDTO> consultarExpedientesPorFiltros(
			FiltroExpedienteDTO filtrosBusquedaExpediente)
			throws NSJPNegocioException {
		return service.buscarExpedientesPorFiltros(filtrosBusquedaExpediente);
	}
	
	@Override
	public List<InvolucradoDTO> consultarExpedientesPorFiltrosYDiscriminante(
			FiltroExpedienteDTO filtrosBusquedaExpediente,UsuarioDTO usuarioFirmado)
			throws NSJPNegocioException {
		return service.buscarExpedientesPorFiltrosYDiscriminante(filtrosBusquedaExpediente, usuarioFirmado);
	}

	@Override
	public ExpedienteDTO asignarNumeroExpediente(ExpedienteDTO expedienteDTO)
			throws NSJPNegocioException {
		return asignarNoService.asignarNumeroExpediente(expedienteDTO);
	}

	@Override
	public Boolean asignarNumerosDeExpediente(List<ExpedienteDTO> listaExpedientes)
			throws NSJPNegocioException {
		return asignarNoService.asignarNumerosDeExpediente(listaExpedientes);
	}

	@Override
	public ExpedienteDTO asignarNumeroExpediente(TurnoDTO turno)
			throws NSJPNegocioException {
		return this.asignarNoService.asignarNumeroExpediente(turno);
	}
        
        @Override
	public ExpedienteDTO asignarNumeroExpedienteSinCaso(TurnoDTO turno)
			throws NSJPNegocioException {
		return this.asignarNoService.asignarNumeroExpedienteSinCaso(turno);
	}

	@Override
	public ExpedienteDTO generarExpJusticaAltRest(ExpedienteDTO expedienteDTO)
			throws NSJPNegocioException {
		return this.generarExpedienteJARService
				.generarExpJusticaAltRest(expedienteDTO);
	}

	@Override
	public List<ExpedienteDTO> consultarExpedienteActividadAreaAnio(
			FiltroExpedienteDTO filtroExpedienteDTO)
			throws NSJPNegocioException {

		return this.service
				.consultarExpedienteActividadAreaAnio(filtroExpedienteDTO);
	}
	
	@Override
	public List<ExpedienteDTO> consultarCanalizadosCoordinadorAmpGeneral(
			FiltroExpedienteDTO filtroExpedienteDTO)
			throws NSJPNegocioException {
		return this.service.consultarCanalizadosCoordinadorAmpGeneral(filtroExpedienteDTO);
	}

	@Override
	public void asociarDocumentoCausaTOCA(DocumentoDTO documento,
			ExpedienteDTO causa, ActividadDTO actividadDTO) throws NSJPNegocioException {
		asociarDocumentoCausaTOCAService.asociarDocumentoCausaTOCA(documento,
				causa, actividadDTO);
	}

	@Override
	public ExpedienteDTO crearAsignarNumeroExpediente(UsuarioDTO usuarioDTO,
			ExpedienteDTO expedienteDTO, InstitucionDTO institucionDTO)
			throws NSJPNegocioException {
		return administrarNumeroExpediente.crearAsignarNumeroExpediente(
				usuarioDTO, expedienteDTO, institucionDTO);
	}

	@Override
	public String consultarUltimoNumeroExpediente(Long idInstitucion)
			throws NSJPNegocioException {
		return administrarNumeroExpediente
				.consultarUltimoNumeroExpediente(idInstitucion);
	}

	@Override
	public ExpedienteDTO crearExpediente() throws NSJPNegocioException {
		return administrarNumeroExpediente.crearExpediente();
	}

	@Override
	public void asociarNumExpediente(ExpedienteDTO expedienteDto)
			throws NSJPNegocioException {
		administrarNumeroExpediente.asociarNumExpediente(expedienteDto);
	}

	@Override
	public boolean asociarNumCarpetaASolicitud(ExpedienteDTO expedienteDTO,
			Long idSolicitud) throws NSJPNegocioException {
		return administrarNumeroExpediente.asociarNumCarpetaASolicitud(
				expedienteDTO, idSolicitud);
	}

	@Override
	public String consultarPrefijo(InstitucionDTO institucionDTO)
			throws NSJPNegocioException {
		return jerarquiaOrganizacionalService.consultarPrefijo(institucionDTO);
	}

	@Override
	public List<ExpedienteDTO> consultarExpedientesPorEtapa(
			EtapasExpediente etapa, Long usuarioId, Long areaId)
			throws NSJPNegocioException {
		return consultarExpedientesPorEtapaService
				.consultarExpedientesPorEtapa(etapa, usuarioId, areaId);
	}

	/**
	 * Consulta los datos generales de un expediente en base a su identificador.
	 * 
	 * @param expedienteDTO
	 *            Identificador del expdiente en el sistema e identificador del
	 *            area: <b>expedienteId</b> y <b>area.areaId</b> <br>
	 * @return El expediente.
	 * @throws NSJPNegocioException
	 *             En caso de ocurrir alg&uacute;n error de negocio al consultar.
	 */
	public DatosGeneralesExpedienteDTO obtenerDatosGeneralesDeExpediente(
			ExpedienteDTO expedienteDTO) throws NSJPNegocioException {
		return service.obtenerDatosGeneralesExpediente(expedienteDTO);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ExpedienteDTO consultarDetalleExpediente(
			ExpedienteDTO expedienteDto, UsuarioDTO usuarioDto)
			throws NSJPNegocioException {
		return consultarDetalleExpedienteService.consultarDetalleExpediente(expedienteDto, usuarioDto); 
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ExpedienteDTO> consultarExpedientesPorUsuarioYEtapa(
			UsuarioDTO usuario, Long idEtapa) throws NSJPNegocioException {
		List<ExpedienteDTO> expedientesPorEtapa = consultarExpedientesPorEtapaService
				.consultarExpedientesPorEtapa(
						EtapasExpediente.getByValor(idEtapa),
						usuario.getFuncionario().getClaveFuncionario(), null);
		return expedientesPorEtapa;
	}

	@Override
	public List<ExpedienteDTO> consultarExpedientesPorEtapaDefensoria(
			UsuarioDTO usuario, Long etapaValorId) throws NSJPNegocioException {
		List<ExpedienteDTO> expedientesPorEtapa = consultarExpedientesPorEtapaService
				.consultarExpedientesPorEtapaDefensoria(etapaValorId, usuario
						.getFuncionario().getClaveFuncionario(), null);
		return expedientesPorEtapa;
	}

	
	@Override
	public ExpedienteDTO obtenerNumeroExpedienteByNumExp(
			ExpedienteDTO expedienteDTO,UsuarioDTO usuario) throws NSJPNegocioException {
		return this.service.obtenerNumeroExpedienteByNumExp(expedienteDTO,usuario);
	}

	/**
	 * Metodo que permite actualizar el tipo de Expediente
	 * 
	 * @param expedienteDTO
	 *            .NumeroExpedienteId: Representa el numero del expediente a
	 *            actualizar
	 * @param tipo
	 *            : Representa el tipo de expediente, usar enum TipoExpediente
	 * @throws NSJPNegocioException
	 */
	public void actualizarTipoExpediente(ExpedienteDTO expedienteDTO,
			OrigenExpediente tipo) throws NSJPNegocioException {
		actualizarTipoExpedienteService.actualizarTipoExpediente(expedienteDTO,
				tipo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.gob.segob.nsjp.delegate.expediente.ExpedienteDelegate#
	 * obtenerExpedientePorNumeroExpediente(java.lang.String)
	 */
	@Override
	public ExpedienteDTO obtenerExpedientePorNumeroExpediente(
			String numeroExpediente) throws NSJPNegocioException {

		return service.obtenerExpedientePorNumeroExpediente(numeroExpediente);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.gob.segob.nsjp.delegate.expediente.ExpedienteDelegate#
	 * consultarExpedientesConEventosHistorico(java.lang.Long,
	 * mx.gob.segob.nsjp.dto.usuario.UsuarioDTO)
	 */
	@Override
	public List<ExpedienteDTO> consultarExpedientesConEventosHistorico(
			Long casoId, UsuarioDTO usuario) throws NSJPNegocioException {
		return service.consultarExpedientesConEventosHistorico(casoId, usuario);
	}

	@Override
	public List<ExpedienteDTO> consultarNumeroExpedienteHistorico(UsuarioDTO usuario)
			throws NSJPNegocioException {

		return this.service.consultarNumeroExpedienteHistorico(usuario);
	}

	@Override
	public List<ExpedienteDTO> consultarNumeroExpedienteByEstatus(
			TipoExpediente tipoExpediente, EstatusExpediente estatusExpediente)
			throws NSJPNegocioException {
		return this.service.consultarNumeroExpedienteByEstatus(tipoExpediente,
				estatusExpediente);
	}

	public List<NotaExpedienteDTO> registrarActualizarNotasExpediente(
			List<NotaExpedienteDTO> notas, ExpedienteDTO expedienteDTO)
			throws NSJPNegocioException {
		return (registarNotasExpedienteService.registrarActualizarNotasExpediente(
				notas, expedienteDTO));
	}

	public List<NotaExpedienteDTO> consultarNotasPorExpediente(
			ExpedienteDTO expedienteDTO) throws NSJPNegocioException {
		return consultarNotasPorExpedienteService.consultarNotas(expedienteDTO);
	}

	public ActividadDTO canalizarCausa(ExpedienteDTO inputExpediente,
			ActividadDTO actividadDTO, Boolean bDelitoPrincipalGrave,
			Boolean bInputadoReincidente, Boolean bSeguimientoInterno)
			throws NSJPNegocioException {

		return canalizarCausaService.canalizarCausaServiceImpl(inputExpediente,
				actividadDTO, bDelitoPrincipalGrave, bInputadoReincidente,
				bSeguimientoInterno);
	}

	@Override
	public ExpedienteDTO registrarActaCircunstanciada(
			ActaCircunstanciadaDTO actaDTO, ExpedienteDTO expedienteDTO)
			throws NSJPNegocioException {
		return registrarActaCircunstanciadaService
				.registrarActaCircunstanciada(actaDTO, expedienteDTO);
	}

	@Override
	public List<ExpedienteDTO> consultarExpedientesUsuarioArea(
			UsuarioDTO usuarioDTO) throws NSJPNegocioException {		
		return this.service.consultarExpedientesUsuarioArea(usuarioDTO);
	}

	@Override
	public List<ExpedienteDTO> consultarExpedientePorAreaEstatusRemitente(
			UsuarioDTO usuarioDTO, AreaDTO area,
			Long estatusExpediente) throws NSJPNegocioException {
		return service.consultarExpedientePorAreaEstatusRemitente(usuarioDTO, area,
				estatusExpediente);
	}


	
	public EtapasExpediente consultarEtapaDelExpediente(Long numeroExpedienteId)
			throws NSJPNegocioException {
		return consultarExpedientesPorEtapaService.consultarEtapaDelExpediente(numeroExpedienteId);
	}

	@Override
	public void cambiarEtapaDelExpediente(Long numeroExpedienteId,
			EtapasExpediente etapa) throws NSJPNegocioException {
		consultarExpedientesPorEtapaService.cambiarEtapaDelExpediente(numeroExpedienteId, etapa);
	}
	/*
	 * (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.expediente.ExpedienteDelegate#consultarNumeroExpedientePorCaso(java.lang.String)
	 */
	@Override
	public List<ExpedienteDTO> consultarNumeroExpedientePorCasoDeSolicitud(
			Long solicitudId) throws NSJPNegocioException {
		return administrarNumeroExpediente.consultarNumeroExpedientePorCasoDeSolicitud(solicitudId);
	}
	/*
	 * (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.expediente.ExpedienteDelegate#asignarNumeroExpedienteASolicitud(java.lang.Long, java.lang.Long)
	 */
	@Override
	public void asignarNumeroExpedienteASolicitud(Long numeroExpedienteId,
			Long solicitudId) throws NSJPNegocioException{
		asignarNoService.asignarNumeroExpedienteASolicitud(numeroExpedienteId, solicitudId);
	}

	@Override
	public List<ExpedienteDTO> consultarHistoricoCausasExpediente()
			throws NSJPNegocioException {		
		return service.consultarHistoricoCausasExpediente();
	}

	@Override
	public List<ExpedienteDTO> consultarCarpetasEjecucionPorCausa(
			ExpedienteDTO expedienteDTO) throws NSJPNegocioException {		
		return service.consultarCarpetasEjecucionPorCausa(expedienteDTO);
	}	
    
	@Override
	public List<ExpedienteDTO> consultarCausasPorIdCaso(Long idCaso)throws NSJPNegocioException {		
		return consultarCausasPorIdCasoService.consultarCausasPorIdCasoService(idCaso);
	}
	
	public String asociarExpedienteAFuncionario(Long idNumeroExpediente, Long idFuncionario) throws NSJPNegocioException{
		return administrarNumeroExpediente.asociarExpedienteAFuncionario(idNumeroExpediente, idFuncionario);
	}
	/*
	 * (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.expediente.ExpedienteDelegate#consultarExpedientesPorFiltro(java.util.Date, java.util.Date, mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO, mx.gob.segob.nsjp.comun.enums.expediente.TipoExpediente, java.lang.Long)
	 */
	@Override
	public List<ExpedienteDTO> consultarExpedientesPorFiltro(Date fechaInicio,
			Date fechaFin, FuncionarioDTO usuario, TipoExpediente tipo,
			Long numeroExpedientePadreId) throws NSJPNegocioException {
		return service.consultarExpedientesPorFiltro(fechaInicio, fechaFin, usuario, tipo, numeroExpedientePadreId);
	}

	@Override
	public List<ExpedienteDTO> consultarNumeroExpedientePorNumeroCaso(
			String caso) throws NSJPNegocioException{
		return service.consultarNumeroExpedientePorNumeroCaso(caso);
	}

	@Override
	public ActaCircunstanciadaDTO consultarActaCircunstaciada(
			ExpedienteDTO expedienteDTO) throws NSJPNegocioException {
		return consultarActaCircunstanciadaService.consultarActaCircunstaciada(expedienteDTO);
	}
	
	@Override
    public ExpedienteDTO enviarCarpetaDeInvestigacion(
    		String numeroGeneralCaso, String folioSolicitud)
            throws NSJPNegocioException{
		return enviarCarpetaDeInvestigacionProcuraduriaService.enviarCarpetaDeInvestigacion(numeroGeneralCaso, folioSolicitud);
	}
	
	public ExpedienteDTO actualizarEstatusExpediente(ExpedienteDTO expedienteDTO) throws NSJPNegocioException{
		return actualizarExpedienteService.actualizarEstatusExpediente(expedienteDTO); 
	}


	@Override
	public List<ExpedienteDTO> consultarNumeroExpedienteByTipoYEstatus(
			TipoExpediente tipoExpediente, EstatusExpediente estatusExpediente)
			throws NSJPNegocioException {		
		return service.consultarNumeroExpedienteByTipoYEstatus(tipoExpediente, estatusExpediente, null);
	}
	
    public NotaExpedienteDTO consultarNotaPorId(Long idNota)throws NSJPNegocioException {
    	return consultarNotasPorExpedienteService.consultarNotaPorId(idNota);
    }

	@Override
	public ExpedienteDTO obtenerDetalleCarpetaEjecucion(
			ExpedienteDTO expedienteDTO) throws NSJPNegocioException {
		//FIXME ¿Y la funcionalidad?
		return null;
	}

	@Override
	public List<Object[]> expedientesPorMes(Date fechaInicial, Date fechaFin) {		
		return graficaDenunciaVSTipoDelitoService.expedientesPorMes(fechaInicial, fechaFin);
	}

	@Override
	public List<Object[]> obtenerNumExpPorEstatusYMes(Date inicial,
			Date fin, EstatusExpediente estatusExpediente)
			throws NSJPNegocioException {		
		return graficaDeterminacionPorDenunciaService.obtenerNumExpPorEstatusYMes(inicial, fin, estatusExpediente);
	}

    @Override
    public Long obtenerExpedienteIdPorNumExpId(ExpedienteDTO expedienteDTO)
            throws NSJPNegocioException {
        return service.obtenerExpedienteIdPorNumExpId(expedienteDTO);
    }

    @Override
	public List<ExpedienteDTO> consultarExpedientesPorUsuarioAreaEstatus(
			UsuarioDTO usuarioDTO, Long estatus) throws NSJPNegocioException {
    	return service.consultarExpedientesPorUsuarioAreaEstatus(usuarioDTO, estatus);
    }
	
    @Override
    public List<ExpedienteDTO>  asignarNumeroExpedienteAuditoria(List<ExpedienteDTO> listaNumeroExpedienteAuditados) throws NSJPNegocioException{
    	return asignarNoService.asignarNumeroExpedienteAuditoria(listaNumeroExpedienteAuditados);
    }
    
    @Override
	public List<RelNumExpedienteAuditoriaDTO> consultarNumeroVisitaduriaPorFiltro(RelNumExpedienteAuditoriaDTO filtro )  throws NSJPNegocioException{
    	return consultarBandejaVisitaduriaService.consultarNumeroVisitaduriaPorFiltro(filtro);
    }
    
    @Override
	public RelNumExpedienteAuditoriaDTO consultarRelacionPorIdAuditoria(Long idAuditoria) throws NSJPNegocioException{
		return consultarRelNumExpedientePorIdAuditoriaService.consultarRelacionPorIdAuditoria(idAuditoria);
	}
    
    @Override
	public List<FuncionarioDTO> consultarFuncionariosPorEstatusDeparamento(Long idEstatus, Long idDepartamento) 
	throws NSJPNegocioException {
		return consultarBandejaVisitaduriaService.consultarFuncionariosPorEstatusDeparamento(idEstatus, idDepartamento);
	}

	@Override
	public ExpedienteDTO consultarNumExpPorFuncionario(Long claveFuncionario,
			Long numExpId) throws NSJPNegocioException {		
		return service.consultarNumExpPorFuncionarioYNumExp(claveFuncionario, numExpId);
	}

	@Override
	public List<ExpedienteDTO> consultarNumExpPorFuncionario(
			Long claveFuncionario) throws NSJPNegocioException {		
		return service.consultarNumExpPorFuncionario(claveFuncionario);
	}
	
	/**
	 * 
	 * @param claveFuncionario
	 * @param numExpId
	 * @throws NSJPNegocioException
	 */
	public void asignarPermisoExpedienteFuncionario(Long claveFuncionario, Long numExpId,Date fechaLimite, Boolean permiso, UsuarioDTO usuarioFirmado) throws NSJPNegocioException {
		registrarPermisoExpedienteService.registrarPermisoExpedienteFuncionario(claveFuncionario, numExpId, fechaLimite, permiso,usuarioFirmado);
	}

	@Override
	public void eliminarPermisoExpedienteFuncionario(Long claveFuncionario,
			Long numExpId, UsuarioDTO usuarioFirmado) throws NSJPNegocioException {
		registrarPermisoExpedienteService.eliminarPermisoExpedienteFuncionario(claveFuncionario, numExpId, usuarioFirmado);
	}

	public ExpedienteDTO consultarExpedienteRelacionadoAArea (String numeroExpediente, Long areaId  ) throws NSJPNegocioException {
		return service.consultarExpedienteRelacionadoAArea(numeroExpediente, areaId); 
	}

    @Override
    public List<ExpedienteDTO> consultarNumeroExpedienteByEstatus(
            EstatusExpediente estatusExpediente) throws NSJPNegocioException {
        return service.consultarNumeroExpedienteByTipoYEstatus(null, estatusExpediente, null);
        }
    
	@Override
	public List<ExpedienteDTO> consultarNumeroDeExpedienteConHechoPorFiltros(
			EstatusExpediente estatusExpediente, UsuarioDTO usuario,
			Date fechaInicio, Date fechaFin) throws NSJPNegocioException {
		return service.consultarNumeroDeExpedienteConHechoPorFiltros(estatusExpediente, usuario, fechaInicio, fechaFin);
	}
	
	@Override
	public List<ExpedienteDTO> consultarNumeroDeExpedienteSinHechoPorFiltros(
			EstatusExpediente estatusExpediente, UsuarioDTO usuario,
			Date fechaInicio, Date fechaFin) throws NSJPNegocioException {
		return service.consultarNumeroDeExpedienteSinHechoPorFiltros(estatusExpediente, usuario, fechaInicio, fechaFin);
	}

	public ExpedienteDTO generarNuevoExpedienteUAVD(DelitoPersonaDTO delitoPersonaDTO) throws NSJPNegocioException{
		return administrarNumeroExpediente.generarNuevoExpedienteUAVD(delitoPersonaDTO);
	}
	

    public DatosGeneralesExpedienteUAVDDTO obtenerResumenDeExpedienteUAVD(SolicitudDTO solicitudDTO)
            		throws NSJPNegocioException{
		return service.obtenerResumenDeExpedienteUAVD(solicitudDTO);
    }
    
    public ExpedienteDTO generarExpediente(ExpedienteDTO expedienteDTO)
		throws NSJPNegocioException{
    return administrarNumeroExpediente.generarNuevoExpedienteConCaso(expedienteDTO);
    }

	public ExpedienteDTO generarExpedienteSinCaso(ExpedienteDTO expedienteDTO)
			throws NSJPNegocioException{
		return administrarNumeroExpediente.generarNuevoExpedienteSinCaso(expedienteDTO);
	}
        public CasoDTO generarNuevoNUC(UsuarioDTO usuario, Long numeroExpedienteId )  throws NSJPNegocioException{
            	return administrarNumeroExpediente.generarNuevoNUC(usuario, numeroExpedienteId);
        }

	public List<ExpedienteDTO> buscarRemisionesConIPH(EstatusExpediente estatusExpediente)throws NSJPNegocioException{
		return service.buscarRemisionesConIPH(estatusExpediente);
	}
	public List<ExpedienteDTO> buscarRemisionesConIPH(EstatusExpediente estatusExpediente,Long idDiscrimiante)throws NSJPNegocioException{
		return service.buscarRemisionesConIPH(estatusExpediente,idDiscrimiante);
	}
	
	public ExpedienteDTO obtenerExpedientePorExpedienteId(ExpedienteDTO expedienteDTO)
            throws NSJPNegocioException {
		return service.obtenerExpediente(expedienteDTO);
	}

	@Override
	public List<ExpedienteDTO> consultarExpedientesPorFiltroST(
			Date fechaInicio, Date fechaFin, UsuarioDTO usuario,
			List<Long> estatusExpediente,Long idDiscriminante, Long rolId, Long idDistrito, Long idFuncionario) throws NSJPNegocioException {
		return service.consultarExpedientesPorFiltroST(fechaInicio, fechaFin,
				usuario, estatusExpediente, idDiscriminante , rolId, idDistrito, idFuncionario);

	}

	@Override
	public JerarquiaOrganizacionalDTO consultarOrigendeExpediente(
			ExpedienteDTO expediente) throws NSJPNegocioException {
		return consultarDetalleExpedienteService.consultarOrigendeExpediente(expediente);
	}

	@Override
	public Boolean actualizarCatDiscriminanteDeExpediente(
			ExpedienteDTO expedienteDto) throws NSJPNegocioException {
		return actualizarExpedienteService.actualizarCatDiscriminanteDeExpediente(expedienteDto);
}

	@Override
	public List<RelNumExpedienteAuditoriaDTO> consultarNumeroVisitaduriaUIE(
			RelNumExpedienteAuditoriaDTO relNumExpedienteAuditoriaDTO) throws NSJPNegocioException {
		return consultarBandejaVisitaduriaService.consultarNumeroVisitaduriaUIE(relNumExpedienteAuditoriaDTO);		
	}

	@Override
	public void actualizarEstatusNumerosDeExpedientesPorRolST(
			List<Long> roles, Long idExpediente,Long nuevoEstatusNumeroExpediente , Long nuevoEstatusExpediente) throws NSJPNegocioException{
		service.actualizarEstatusNumerosDeExpedientesPorRolST(roles, idExpediente, nuevoEstatusNumeroExpediente, nuevoEstatusExpediente);
	}

	@Override
	public Long consultarEstatusNumeroExpedienteByNumeroExpedienteId(
			Long numeroExpedienteId) throws NSJPNegocioException {
		return administrarNumeroExpediente.consultarEstatusNumeroExpedienteByNumeroExpedienteId(numeroExpedienteId);
	}
	
	@Override
	
	public Boolean reasignarFuncionarioDeNumeroExpediente(List<ExpedienteDTO> lstExpedienteDTO, HistoricoExpedienteDTO historicoExpedienteDTO, RolDTO rolActivo)
			throws NSJPNegocioException{
		return actualizarExpedienteService.reasignarFuncionarioDeNumeroExpediente(lstExpedienteDTO, historicoExpedienteDTO, rolActivo);
	}
	
	public ExpedienteDTO obtenerExpedientePorNumeroExpedienteYNumeroCaso(
			String numeroExpediente, String numCaso) throws NSJPNegocioException {
		return service.obtenerExpedientePorNumeroExpedienteYNumeroCaso(numeroExpediente, numCaso);
	}

	@Override
	public List<String> buscarNumerosExpedientesByIdExpediente(
			ExpedienteDTO idExpediente) throws NSJPNegocioException {
		return service.buscarNumerosExpedientesByIdExpediente(idExpediente);
	}

	@Override
	public List<ExpedienteDTO> consultarExpedienteCoorAT(
			FiltroExpedienteDTO filtroExpedienteDTO)
			throws NSJPNegocioException {
		return service.consultarExpedienteCoorAT(filtroExpedienteDTO);
	}

	@Override
	public ExpedienteDTO asignarNumeroCasoSolicitudDefensor(
			ExpedienteDTO expedienteDTO) throws NSJPNegocioException {
		return actualizarExpedienteService.asignarNumeroCasoSolicitudDefensor(expedienteDTO);
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.expediente.ExpedienteDelegate#consultarExpedientesRSPorNumeroExpediente(mx.gob.segob.nsjp.dto.expediente.FiltroExpedienteDTO)
	 */
	@Override
	public List<ExpedienteDTO> consultarExpedientesRSPorNumeroExpediente(
			FiltroExpedienteDTO filtro) throws NSJPNegocioException {
		return service.consultarExpedientesRSPorNumeroExpediente(filtro);
	}
	
	public List<ExpedienteViewDTO> consultarExpedientesConSP(TipoDeBusquedaDeExpediente tipoBusqueda,
			HashMap<String, String> valores) throws NSJPNegocioException{
		return service.consultarExpedientesConSP(tipoBusqueda, valores);
	}

	@Override
	public ExpedienteDTO consultarExpedientePorExpedienteId(
			ExpedienteDTO expedienteDTO) throws NSJPNegocioException {
		return service.obtenerExpedientePorExpedienteId(expedienteDTO);
	}
	
	@Override
	public ExpedienteDTO consultarExpedientePorNumeroDeCaso(CasoDTO casoDto,
			UsuarioDTO usuarioDto) throws NSJPNegocioException {
		return service.consultarExpedientePorNumeroDeCaso(casoDto, usuarioDto);
	}
	
	public void actualizarJerarquiaOrganizacionalANumExp(Long idNumeroExpediente, Long jerarquiaOrganizacional) throws NSJPNegocioException{
		administrarNumeroExpediente.actualizarJerarquiaOrganizacionalANumExp(idNumeroExpediente, jerarquiaOrganizacional);
	}
	
	public ExpedienteViewDTO consultarGeneralesDeHistorialDeExp(Long idNumeroExpediente) throws NSJPNegocioException {
		return service.consultarGeneralesDeHistorialDeExp(idNumeroExpediente);
	}
	
	public List<ExpedienteDTO> consultarNumerosExpedientesPorIdExpediente(Long idExpediente,List<Long> idsJeraruqiasOrganizacionales)throws NSJPNegocioException {
		return service.consultarNumerosExpedientesPorIdExpediente(idExpediente, idsJeraruqiasOrganizacionales);		
	}

	
	public int obtenDetalleDeCanalizacionDeNumeroExpediente(Long idNumeroExpediente)throws NSJPNegocioException {
		return service.obtenDetalleDeCanalizacionDeNumeroExpediente(idNumeroExpediente);
	}

	public List<CatEtapaDTO> consultarEtapasJerarquiaPorPadre(Boolean esEtapaExpediente)
			throws NSJPNegocioException{
		return consultarEtapasService.consultarEtapasJerarquiaPorPadre(esEtapaExpediente);
	}
	public List<BitacoraPermisoExpedienteDTO> obtenerPermisosDeExpediente(Long idExpediente) throws NSJPNegocioException {
		return service.obtenerPermisosDeExpediente(idExpediente);
	}
	
	public List<BitacoraEstatusNumExpedienteDTO> consultarBitacoraEstatusNumExpedientePorIdExpediente(Long expedienteId) throws NSJPNegocioException {
		return service.consultarBitacoraEstatusNumExpedientePorIdExpediente(expedienteId);
	}
	
	public void actualizarCatUIEDeExpediente(ExpedienteDTO expedienteDTO) throws NSJPNegocioException {
		actualizarExpedienteService.actualizarCatUIEDeExpediente(expedienteDTO);
	}
	
	public ValorDTO consultaOrigenExpediente(Long idExpediente) throws NSJPNegocioException {
		return service.consultaOrigenExpediente(idExpediente);
	}
		
	@Override
	public List<ExpedienteDTO> buscadorDeExpedientesAReasignarPM(FiltroExpedienteDTO filtroExpedienteDTO)throws NSJPNegocioException {
		return this.service.buscadorDeExpedientesAReasignarPM(filtroExpedienteDTO);
	}
	
    public void reasignarNumerosDeExpedientePM(List<Long> idNumerosExpedientes, Long idNuevoFuncionario, UsuarioDTO usuario, Long idFuncionarioActual) throws NSJPNegocioException{
    	asignarNoService.reasignarNumerosDeExpedientePM(idNumerosExpedientes, idNuevoFuncionario, usuario, idFuncionarioActual);
    }
    
    public List<ExpedienteViewDTO> consultaCiudadana(String apaterno, String amaterno, String nombre, String expediente) throws NSJPNegocioException {
    	return service.consultaCiudadana(apaterno, amaterno, nombre, expediente);
    }
	
}
