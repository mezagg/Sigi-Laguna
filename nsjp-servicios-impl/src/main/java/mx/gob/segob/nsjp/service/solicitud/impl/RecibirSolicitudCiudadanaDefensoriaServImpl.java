/**
* Nombre del Programa : RecibirSolicitudCiudadanaDefensoriaImpl.java
* Autor                            : cesarAgutin
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 17 May 2011
* Marca de cambio        : N/A
* Descripcion General    : Contrato para el servicio Recibir una solicitud de defensoria por un ciudadano
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
import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.enums.documento.TipoDocumento;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.expediente.EstatusExpediente;
import mx.gob.segob.nsjp.comun.enums.expediente.EtapasExpediente;
import mx.gob.segob.nsjp.comun.enums.expediente.TipoMovimiento;
import mx.gob.segob.nsjp.comun.enums.forma.Formas;
import mx.gob.segob.nsjp.comun.enums.institucion.Areas;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.enums.involucrado.SituacionJuridica;
import mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud;
import mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.expediente.NumeroExpedienteDAO;
import mx.gob.segob.nsjp.dao.involucrado.DefensaInvolucradoDAO;
import mx.gob.segob.nsjp.dao.involucrado.InvolucradoDAO;
import mx.gob.segob.nsjp.dao.solicitud.SolicitudDefensorDAO;
import mx.gob.segob.nsjp.dto.catalogo.CatDelitoDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.configuracion.ConfInstitucionDTO;
import mx.gob.segob.nsjp.dto.elemento.CalidadDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.institucion.AreaDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDefensorDTO;
import mx.gob.segob.nsjp.model.ConfInstitucion;
import mx.gob.segob.nsjp.model.DefensaInvolucrado;
import mx.gob.segob.nsjp.model.DefensaInvolucradoId;
import mx.gob.segob.nsjp.model.Forma;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.model.Involucrado;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.model.RegistroBitacora;
import mx.gob.segob.nsjp.model.Solicitud;
import mx.gob.segob.nsjp.model.SolicitudDefensor;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.bitacora.RegistrarBitacoraService;
import mx.gob.segob.nsjp.service.delito.ConsultarDelitoService;
import mx.gob.segob.nsjp.service.delito.GuardarDelitoService;
import mx.gob.segob.nsjp.service.documento.GenerarNumeroDeAcuseDeAtencionService;
import mx.gob.segob.nsjp.service.expediente.ActualizarEtapaExpedienteService;
import mx.gob.segob.nsjp.service.expediente.AsignarNumeroExpedienteService;
import mx.gob.segob.nsjp.service.expediente.impl.transform.ExpedienteTransformer;
import mx.gob.segob.nsjp.service.involucrado.IngresarIndividuoService;
import mx.gob.segob.nsjp.service.involucrado.impl.transform.InvolucradoTransformer;
import mx.gob.segob.nsjp.service.solicitud.GenerarFolioSolicitudService;
import mx.gob.segob.nsjp.service.solicitud.RecibirSolicitudCiudadanaDefensoriaService;
import mx.gob.segob.nsjp.service.solicitud.impl.transform.SolicitudDefensorTransformer;
import mx.gob.segob.nsjp.service.solicitud.impl.transform.SolicitudTransformer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Contrato para el servicio Recibir una solicitud de defensoria por un ciudadano.
 * @version 1.0
 * @author cesarAgustin
 *
 */
@Service
@Transactional
public class RecibirSolicitudCiudadanaDefensoriaServImpl implements
		RecibirSolicitudCiudadanaDefensoriaService {

	
	@Autowired
	private SolicitudDefensorDAO solicitudDefensorDAO;
	@Autowired
	private InvolucradoDAO involucradoDAO;
	@Autowired
	private IngresarIndividuoService ingresarIndividuoService;
	@Autowired
	private DefensaInvolucradoDAO defensaInvolucradoDAO;
	@Autowired
	private AsignarNumeroExpedienteService asignarNumeroExpedienteService;
	@Autowired
	private ActualizarEtapaExpedienteService actualizaEtapaExpedienteService;
	@Autowired
	private GuardarDelitoService guardarDelitoService;
	@Autowired
	private ConsultarDelitoService consultarDelitoService;
	@Autowired
    private GenerarFolioSolicitudService generarFolioSolicitudService;
	@Autowired
	private GenerarNumeroDeAcuseDeAtencionService generarNumeroDeAcuseDeAtencionService;
	@Autowired
    private NumeroExpedienteDAO numExpDao;
	@Autowired
	private RegistrarBitacoraService registrarBitacoraService;
	
	private final static Logger logger = Logger.getLogger(RecibirSolicitudCiudadanaDefensoriaServImpl.class);
	
	@Override
	//MOD defensorATE
	public SolicitudDefensorDTO registrarSolicitudAsesoriaLegal(ExpedienteDTO expedienteDTO)
			throws NSJPNegocioException {
		
		 logger.debug("**SERVICIO registrarSolicitudAsesoriaLegal - Parametro:: "+ expedienteDTO);
		 
		if (expedienteDTO == null
				|| expedienteDTO.getUsuario() == null
				|| expedienteDTO.getUsuario().getFuncionario() == null
				|| expedienteDTO.getUsuario().getFuncionario()
						.getClaveFuncionario() == null
				|| expedienteDTO.getUsuario().getFuncionario()
						.getClaveFuncionario() <= 0L
				|| expedienteDTO.getDiscriminante() == null
				|| expedienteDTO.getDiscriminante().getCatDiscriminanteId() <= 0L) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		logger.debug(" GENERAR EXPEDIENTE (SIN CASO) Y SOLICITUD ");

		//DATOS PARA EXPEDIENTE - NUMERO EXPEDIENTE
		expedienteDTO.setEtapa(new ValorDTO(EtapasExpediente.ASESORIA
				.getValorId()));
		//Unificar la generaci&oacute;n del expediente en una sola &acute;rea
		AreaDTO areaDTO = new AreaDTO(Areas.ATENCION_TEMPRANA_DEFENSORIA);
		expedienteDTO.setArea(areaDTO);
		expedienteDTO.setEstatus(new ValorDTO(EstatusExpediente.ABIERTO
				.getValorId()));
		expedienteDTO.setFechaApertura(new Date());
		//NO SE CREA EL CASO
		expedienteDTO.setCasoDTO(null);
		//NO SE ASOCIA ETAPA
		expedienteDTO.setCatEtapaDTO(null);
		
		expedienteDTO = asignarNumeroExpedienteService
				.asignarNumeroExpedienteDefensoria(expedienteDTO);
		
		//DATOS DE SOLICITUD DE ASESORIA LEGAL
		SolicitudDefensor solicitudDefensor= new SolicitudDefensor();
		solicitudDefensor.setConfInstitucion(new ConfInstitucion(Instituciones.DEF
				.getValorId()));
		solicitudDefensor.setFechaCreacion(new Date());
		solicitudDefensor.setEstatus(new Valor(EstatusSolicitud.ABIERTA
				.getValorId()));		
		solicitudDefensor.setTipoSolicitud(new Valor(
				TiposSolicitudes.ASESORIA_DEFENSORIA.getValorId()));
		solicitudDefensor.setForma(new Forma(Formas.SOLICITUD_DE_ASESORIA_LEGAL
				.getValorId()));
		solicitudDefensor.setTipoDocumento(new Valor(
				TipoDocumento.SOLICITUD_DE_ASESORIA_LEGAL.getValorId()));
		solicitudDefensor.setNombreDocumento(TipoDocumento.SOLICITUD_DE_ASESORIA_LEGAL
				.name());
		solicitudDefensor.setFolioSolicitud(generarFolioSolicitudService
				.generarFolioSolicitud());
		if (expedienteDTO.getUsuario() != null
				&& expedienteDTO.getUsuario().getFuncionario() != null) {
			solicitudDefensor.setFuncionarioSolicitante(new Funcionario(
					expedienteDTO.getUsuario().getFuncionario()
							.getClaveFuncionario()));
			solicitudDefensor.setNombreSolicitante(expedienteDTO.getUsuario()
					.getFuncionario().getNombreCompleto());
		}
		if (expedienteDTO.getNumeroExpediente() != null) {
			solicitudDefensor.setNumeroExpedienteAsociado(expedienteDTO
					.getNumeroExpediente());
		}
		if (expedienteDTO.getNumeroExpedienteId() != null
				&& expedienteDTO.getNumeroExpedienteId() > 0L) {
			solicitudDefensor.setNumeroExpediente(new NumeroExpediente(expedienteDTO
					.getNumeroExpedienteId()));
		}
		
		Long solicitudId = solicitudDefensorDAO.create(solicitudDefensor);
		
		logger.debug("expedienteId:" + expedienteDTO.getExpedienteId()
				+ "solicitud Id:" + solicitudId);
		
		//TODO GPB Preguntar a Analisis si es necesario
		//ACTIVIDAD  Asociar la solicitud a una actividad
//		Long actividad = Actividades.SOLICITAR_ASESORIA_LEGAL.getValorId();
//		Long idActividadRegistrada = registrarActividadService
//				.registrarActividad(expedienteDTO, expedienteDTO
//						.getUsuario().getFuncionario(), actividad);
//		logger.debug(" Actividad Registrada:" + idActividadRegistrada);
//
//		// Asociar la actividad a la solicitud de asesoria
//		Actividad actividadActual = actividadDAO
//				.read(idActividadRegistrada);
//		actividadActual.setDocumento(solicitud);
//		actividadDAO.saveOrUpdate(actividadActual);
//		
		//Datos de Respuesta
		SolicitudDefensorDTO solicitudDTO = SolicitudDefensorTransformer
				.transformarSolicitudDefensoriaBasico(solicitudDefensor);
		solicitudDTO.setExpedienteDTO(expedienteDTO);
		return solicitudDTO;
	}
	
	@Override
	//MOD defensorATE
	public SolicitudDefensorDTO actualizarSolicitudAsesoriaLegal(
			SolicitudDefensorDTO solicitudAsesoriaDTO)
			throws NSJPNegocioException {

		logger.debug("SERVICIO PARA GENERAR SOLICITUD ASESORIA LEGAL PARA UN IMPUTADO");
		if (solicitudAsesoriaDTO == null
				|| solicitudAsesoriaDTO.getInvolucradoDTO() == null
				|| solicitudAsesoriaDTO.getExpedienteDTO() == null
				|| solicitudAsesoriaDTO.getExpedienteDTO().getExpedienteId() == null
				|| solicitudAsesoriaDTO.getExpedienteDTO().getExpedienteId() <= 0L
				|| solicitudAsesoriaDTO.getExpedienteDTO().getUsuario() == null
				|| solicitudAsesoriaDTO.getExpedienteDTO().getUsuario()
						.getFuncionario() == null
				|| solicitudAsesoriaDTO.getExpedienteDTO().getUsuario()
						.getFuncionario().getClaveFuncionario() == null
				|| solicitudAsesoriaDTO.getExpedienteDTO().getUsuario()
						.getFuncionario().getClaveFuncionario() < 0L) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		//Registro de la Solicitud de Asesoria 
		ExpedienteDTO expedienteDTO = solicitudAsesoriaDTO.getExpedienteDTO();

		SolicitudDefensor solicitudAsesoria = solicitudDefensorDAO.read(solicitudAsesoriaDTO
				.getDocumentoId());
		if(solicitudAsesoria ==null){
			throw new NSJPNegocioException(CodigoError.INFORMACION_PARAMETROS_ERRONEA);
		}
		

		// DATOS DE LA SOLICITUD A ACTUALIZAR
		solicitudAsesoria.setMotivo(solicitudAsesoriaDTO.getMotivo());
//		if (solicitudAsesoriaDTO.getTipoAsesoria() != null
//				&& solicitudAsesoriaDTO.getTipoAsesoria().getIdCampo() > 0L) {
//			solicitudAsesoria.setTipoAsesoria(new Valor(solicitudAsesoriaDTO
//					.getTipoAsesoria().getIdCampo()));
//		}
		if (expedienteDTO.getUsuario() != null
				&& expedienteDTO.getUsuario().getFuncionario() != null) {
			solicitudAsesoria.setFuncionarioSolicitante(new Funcionario(
					expedienteDTO.getUsuario().getFuncionario()
							.getClaveFuncionario()));
		}
		solicitudDefensorDAO.update(solicitudAsesoria);
		
		// Actualizar Involucrado Defendido 
		InvolucradoDTO defendidoDTO = solicitudAsesoriaDTO.getInvolucradoDTO();
		defendidoDTO.setExpedienteDTO(expedienteDTO);
		
//		CalidadDTO calidad = new CalidadDTO();
//		calidad.setCalidades(Calidades.DEFENDIDO);
//		calidad.setValorIdCalidad(new ValorDTO(Calidades.DEFENDIDO.getValorId()));
//		defendidoDTO.setCalidadDTO(calidad);

		defendidoDTO.setExpedienteDTO(expedienteDTO);
//		defendido.setValorSituacionJuridica(new ValorDTO(
//				SituacionJuridica.INVITADO.getValorId()));

		Long defendidoId = ingresarIndividuoService
				.ingresarIndividuo(defendidoDTO);
		
     
		defendidoDTO.setElementoId(defendidoId);

        //Valores de Respuesta
        solicitudAsesoriaDTO.setInvolucradoDTO(defendidoDTO);
        solicitudAsesoriaDTO.setFolioSolicitud(solicitudAsesoria.getFolioSolicitud());
        
		return solicitudAsesoriaDTO;
	}

	
	
	
	
	
	
	
	@Override
	public void guardarMotivoSolicitudDefensoria(SolicitudDefensorDTO solicitudDefensorDTO) throws NSJPNegocioException {
		
			if (logger.isDebugEnabled())
				logger.debug("SERVICIO PARA ACTUALZIAR LA SOLICITUD DEFENSORIA PEDIDA POR UN CIUDADANO");
		
			if (solicitudDefensorDTO==null)
				throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
			logger.debug("BUSCANDO SOLICITUD   ------>  " + solicitudDefensorDTO.getDocumentoId());
			SolicitudDefensor solicitudDefensor = solicitudDefensorDAO.read(solicitudDefensorDTO.getDocumentoId());
			
			solicitudDefensor.setMotivo(solicitudDefensorDTO.getMotivo());
			solicitudDefensor.setFechaLimite(solicitudDefensorDTO.getFechaLimite());

			solicitudDefensorDAO.update(solicitudDefensor);
		}

	@Override
	public InvolucradoDTO guardarSolicitanteSolicitudDefensoria(
			InvolucradoDTO solicitante) throws NSJPNegocioException {

		ExpedienteDTO expedienteDTO;
		if (logger.isDebugEnabled())
			logger.debug("SERVICIO PARA GENERAR SOLICITUD DEFENSORIA PEDIDA POR UN CIUDADANO SOLICITANTE");

		if (solicitante==null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);

		expedienteDTO = solicitante.getExpedienteDTO();
		
		//Se asigna la calidad de solicitante		
		CalidadDTO loCalidadSolicitante = new CalidadDTO();
		loCalidadSolicitante.setCalidades(Calidades.SOLICITANTE);
		loCalidadSolicitante.setValorIdCalidad(new ValorDTO(Calidades.SOLICITANTE.getValorId()));		
		solicitante.setCalidadDTO(loCalidadSolicitante);
		

		

		//Se registra el solicitante
		Long idSolicitante = ingresarIndividuoService.ingresarIndividuo(solicitante);


		logger.debug("BUSCANDO SOLICITUD PARA ASIGNARLE EL ID DEL SOLICITANTE   ------>  " + solicitante.getIdSolicitudDefensor());
		SolicitudDefensor solicitudDefensor = solicitudDefensorDAO.read(solicitante.getIdSolicitudDefensor());
		solicitudDefensor.setInvolucradoSolicitante(new Involucrado(idSolicitante));
		solicitudDefensorDAO.update(solicitudDefensor);

		InvolucradoDTO solict = new InvolucradoDTO(idSolicitante);
		solict.setExpedienteDTO(expedienteDTO);
		
		return solict;
	}

	
	@Override
	//Se ocupa en datosGeneralesSolicitud.jsp
	public InvolucradoDTO guardarDefendidoSolicitudDefensoria(
            InvolucradoDTO defendido, Long tipoAsesoria, EtapasExpediente etapa)
            throws NSJPNegocioException {
        ExpedienteDTO expedienteDTO;
        Long idDefendido = null;
        if (logger.isDebugEnabled()) {
            logger.debug("SERVICIO PARA GENERAR SOLICITUD DEFENSORIA PARA UN IMPUTADO");
            logger.debug("defendido.getExpedienteDTO().getExpedienteId() :: "
                    + defendido.getExpedienteDTO().getExpedienteId());
            logger.debug("defendido.getExpedienteDTO().getNumeroExpedienteId() :: "
                    + defendido.getExpedienteDTO().getNumeroExpedienteId());
        }
        if (defendido == null)
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        CalidadDTO calidad = null;
        if (defendido.getCalidadDTO() != null) {
            calidad = new CalidadDTO();
            calidad.setCalidades(Calidades.DEFENDIDO);
            calidad.setValorIdCalidad(new ValorDTO(Calidades.DEFENDIDO
                    .getValorId()));
            defendido.setCalidadDTO(calidad);
        } else {
            calidad = defendido.getCalidadDTO();
            defendido.getCalidadDTO().setValorIdCalidad(
                    new ValorDTO(calidad.getCalidades().getValorId()));
        }

        expedienteDTO = defendido.getExpedienteDTO();
        List<DelitoDTO> delitosCometidos = null;
        if (defendido.getDelitosCometidos() != null
                && !defendido.getDelitosCometidos().isEmpty()) {
            List<DelitoDTO> nuevosDelitosDTO = new ArrayList<DelitoDTO>();
            for (DelitoDTO delitoDTO : defendido.getDelitosCometidos()) {
                DelitoDTO loDelitoDTO = new DelitoDTO();
                CatDelitoDTO delito = new CatDelitoDTO();
                delito.setCatDelitoId(delitoDTO.getDelitoId());
                loDelitoDTO.setCatDelitoDTO(delito);
                loDelitoDTO.setEsPrincipal(false);
                loDelitoDTO.setEsProbable(false);
                nuevosDelitosDTO.add(loDelitoDTO);
            }
            guardarDelitoService.guardarDelito(nuevosDelitosDTO, expedienteDTO);
            // consultarDelitosExpediente
            delitosCometidos = consultarDelitoService
                    .consultarDelitoExpediente(expedienteDTO);
            // Una vez registrados los delitos se asocian al imputado
            defendido.setDelitosCometidos(delitosCometidos);
        }
        NumeroExpediente noExp = this.numExpDao.read(defendido
                .getExpedienteDTO().getNumeroExpedienteId());
        if (etapa != null) {
            switch (etapa) {
                case CONCILIACION_Y_MEDIACION :
                    defendido.setValorSituacionJuridica(new ValorDTO(
                            SituacionJuridica.INVITADO.getValorId()));
                    break;
                case INTEGRACION :
                    defendido.setValorSituacionJuridica(new ValorDTO(
                            SituacionJuridica.INDICIADO.getValorId()));
                    break;
                case TECNICA :
                    defendido.setValorSituacionJuridica(new ValorDTO(
                            SituacionJuridica.IMPUTADO.getValorId()));
                    break;
            }

            if (defendido.getValorSituacionJuridica() != null) {
                RegistroBitacora regBitSJ = new RegistroBitacora();

                regBitSJ.setFechaInicio(new Date());
                regBitSJ.setTipoMovimiento(new Valor(
                        TipoMovimiento.CAMBIO_DE_SITUACION_JURIDICA
                                .getValorId()));
                regBitSJ.setNuevo(String.valueOf(defendido.getValorSituacionJuridica().getIdCampo()));
                regBitSJ.setNumeroExpediente(noExp);
                registrarBitacoraService
                        .registrarMovimientoDeExpedienteEnBitacora(regBitSJ);
            }
        }

        idDefendido = ingresarIndividuoService.ingresarIndividuo(defendido);

        //URG-003 Ingresar la validacion en caso de que sea nulo, es decir que se ingrese un defendido
        if (defendido.getElementoId() == null || defendido.getElementoId() == 0L ) {
            defendido.setElementoId(idDefendido);
            DefensaInvolucrado defensaInvolucrado = new DefensaInvolucrado();
            DefensaInvolucradoId id = new DefensaInvolucradoId();
            id.setInvolucradoId(defendido.getElementoId());
            id.setNumeroExpedienteId(defendido.getExpedienteDTO()
                    .getNumeroExpedienteId());
            defensaInvolucrado.setId(id);
            defensaInvolucradoDAO.create(defensaInvolucrado);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("tipoAsesoria :: " + tipoAsesoria);
            logger.debug("etapa :: " + etapa);
        }
        if (tipoAsesoria != null) {
            for (Solicitud sol : noExp.getSolicituds()) {
                if (sol.getTipoSolicitud().getValorId().longValue() == TiposSolicitudes.ASESORIA_DEFENSORIA
                        .getValorId().longValue()) {
                    SolicitudDefensor sd = (SolicitudDefensor) sol;
                    sd.setTipoAsesoria(new Valor(tipoAsesoria));
                    //URG-003 Se actualiza el motivo que se ingresa en el defendido
                    sd.setMotivo(defendido.getMotivoComparecencia());
                    
                    this.solicitudDefensorDAO.update(sd);
                    break;
                }
            }
        }
        if (etapa != null) {
            if (noExp == null) {
                noExp = this.numExpDao.read(defendido.getExpedienteDTO()
                        .getNumeroExpedienteId());
            }
            noExp.setEtapa(new Valor(etapa.getValorId()));
            this.numExpDao.update(noExp);
            RegistroBitacora regBitEta = new RegistroBitacora();

            regBitEta.setFechaInicio(new Date());
            regBitEta.setTipoMovimiento(new Valor(
                    TipoMovimiento.CAMBIO_DE_ETAPA_DE_EXPEDIENTE.getValorId()));
            regBitEta.setNuevo(String.valueOf(etapa.getValorId()));
            regBitEta.setNumeroExpediente(noExp);
            registrarBitacoraService
                    .registrarMovimientoDeExpedienteEnBitacora(regBitEta);
        }
        return defendido;

    }	
	
	@Override
	@Deprecated
	public SolicitudDefensorDTO generarAcuseAtencion(ExpedienteDTO expedienteDTO)
			throws NSJPNegocioException {

		SolicitudDefensorDTO solicitudDefensorDTO= new SolicitudDefensorDTO();
		SolicitudDefensor solicitudDefensor = null;			
		if (logger.isDebugEnabled())
			logger.debug("SERVICIO PARA LA GENERACION DE LA SOLCICITUD DE DEFENSORIA");
		
		if(expedienteDTO.getUsuario() != null && expedienteDTO.getUsuario().getFuncionario()!= null){
			solicitudDefensorDTO.setFuncionario(new FuncionarioDTO(expedienteDTO.getUsuario().getFuncionario().getClaveFuncionario()));
			solicitudDefensorDTO.setUsuarioSolicitante(new FuncionarioDTO(expedienteDTO.getUsuario().getFuncionario().getClaveFuncionario()));
		}
		
		if(expedienteDTO.getNumeroExpedienteId()==null){
			AreaDTO areaDTO = new AreaDTO(Areas.ATENCION_TEMPRANA_DEFENSORIA);
			expedienteDTO.setArea(areaDTO);
			expedienteDTO.setFechaApertura(new Date());
			expedienteDTO = asignarNumeroExpedienteService.asignarNumeroExpedienteDefensoria(expedienteDTO);
		}else{
			solicitudDefensor = solicitudDefensorDAO.consultarSolicituDefensorPorNumeroExpedienteId(expedienteDTO.getNumeroExpedienteId());
		}
		boolean generarAcuseAtencion = false;
		if(solicitudDefensor == null){
			solicitudDefensor = new SolicitudDefensor();
			solicitudDefensorDTO.setExpedienteDTO(expedienteDTO);
			solicitudDefensorDTO.setInstitucion(new ConfInstitucionDTO(Instituciones.DEF.getValorId()));
			
			logger.debug("Num Exp ID ---->" + solicitudDefensorDTO.getExpedienteDTO().getNumeroExpedienteId());						
			logger.debug("ID EXPD   -------------->  "+solicitudDefensorDTO.getExpedienteDTO().getExpedienteId());			
			logger.debug("Nume EXPD -------------->" + solicitudDefensorDTO.getExpedienteDTO().getNumeroExpediente());
			
			solicitudDefensor = SolicitudTransformer.solDefensorTransformer(solicitudDefensorDTO);
			solicitudDefensor.setFechaCreacion(new Date());
			solicitudDefensor.setForma(new Forma(1L));
			solicitudDefensor.setTipoDocumento(new Valor(82L));//Solicitud
			solicitudDefensor.setNombreDocumento("Nombre Documento");        
//			solicitudDefensor.setEstatus(new Valor(EstatusSolicitud.ABIERTA.getValorId()));
			if (expedienteDTO.getEtapa() != null
					&& expedienteDTO.getEtapa().getIdCampo().intValue() == EtapasExpediente.ASESORIA
							.getValorId().intValue()) {
				solicitudDefensor.setTipoSolicitud(new Valor(
						TiposSolicitudes.ASESORIA_DEFENSORIA.getValorId()));
				solicitudDefensor.setTipoSolicitud(new Valor(
						TiposSolicitudes.ASESORIA_DEFENSORIA.getValorId()));
				solicitudDefensor.setForma(new Forma(
						Formas.SOLICITUD_DE_ASESORIA_LEGAL.getValorId()));
				solicitudDefensor
						.setTipoDocumento(new Valor(
								TipoDocumento.SOLICITUD_DE_ASESORIA_LEGAL
										.getValorId()));
				solicitudDefensor
						.setNombreDocumento(TipoDocumento.SOLICITUD_DE_ASESORIA_LEGAL
								.name());
			} else {
				generarAcuseAtencion = true;
				solicitudDefensor.setTipoSolicitud(new Valor(
						TiposSolicitudes.DEFENSOR.getValorId()));
				solicitudDefensor.setForma(new Forma(Formas.SOLICITUD_DEFENSOR
						.getValorId()));
				solicitudDefensor.setTipoDocumento(new Valor(
						TipoDocumento.SOLICITUD.getValorId()));
				solicitudDefensor.setNombreDocumento(TipoDocumento.SOLICITUD
						.name());
			}
			solicitudDefensor.setFolioSolicitud(generarFolioSolicitudService.generarFolioSolicitud());
			solicitudDefensor.setDocumentoId(solicitudDefensorDAO.create(solicitudDefensor));
		}
		SolicitudDefensorDTO sol = SolicitudDefensorTransformer.transformarSolicitudDefensoria(solicitudDefensorDAO.read(solicitudDefensor.getDocumentoId()));
		if(generarAcuseAtencion){
			Long idAcuse = generarNumeroDeAcuseDeAtencionService.generarNumeroDeAcuse(Instituciones.DEF.getValorId(),solicitudDefensor.getDocumentoId());
			sol.setFolioDocumento(""+idAcuse);
		} 
		sol.getExpedienteDTO().getAvisosDesignacion().clear();
		if(solicitudDefensorDTO.getExpedienteDTO() != null){
			sol.getExpedienteDTO().setExpedienteId(solicitudDefensorDTO.getExpedienteDTO().getExpedienteId());
			sol.getExpedienteDTO().setNumeroExpedienteId(solicitudDefensorDTO.getExpedienteDTO().getNumeroExpedienteId());
			sol.getExpedienteDTO().setNumeroExpediente(solicitudDefensorDTO.getExpedienteDTO().getNumeroExpediente());
		}
		return sol;
	}

	@Override
	public void actualizaImputadoSolicitudDefensoria(InvolucradoDTO imputado)
			throws NSJPNegocioException {

		if (logger.isDebugEnabled())
			logger.debug("SERVICIO PARA ACTUALZIAR UN IMPUTADO DE UNA SOLICITUD DE DEFENSORIA");
	
		if (imputado==null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);

		Involucrado involucradobase = involucradoDAO.read(imputado.getElementoId());
		
		Involucrado	involucradoACT = InvolucradoTransformer.transformarInvolucrado(imputado);
		
		Involucrado imput = InvolucradoTransformer.involucradoUpdate(involucradobase, involucradoACT);
		
		
		this.involucradoDAO.update(imput);
	}

	@Override
	public void actualizaEstatusSolicitudDefensoria(
			SolicitudDefensorDTO solicitudDefensorDTO)
			throws NSJPNegocioException {

		if (logger.isDebugEnabled())
			logger.debug("SERVICIO PARA ACTUALIZAR EL ESTATUS A 'NO ATENDIDO' DE UNA SOLICITUD DE DEFENSORIA");

		if(solicitudDefensorDTO==null){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		SolicitudDefensor solDef = this.solicitudDefensorDAO.read(solicitudDefensorDTO.getDocumentoId());
		
		solDef.setEstatus(new Valor(EstatusSolicitud.ABIERTA.getValorId()));
		
		this.solicitudDefensorDAO.update(solDef);
		
	}

	@Override
	public void actualizarEtapaExpedienteSolicitudDefensoria(
			SolicitudDefensorDTO solicitudDefensorDTO, EtapasExpediente etapas)
			throws NSJPNegocioException {
		
		if (logger.isDebugEnabled())
			logger.debug("SERVICIO PARA LA ACTUALIZACION DE LA ETAPA DE EXPEDIENTE DE DEFENSORIA");
		
		if(solicitudDefensorDTO.getDocumentoId()==null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		
		SolicitudDefensor solDef = solicitudDefensorDAO.read(solicitudDefensorDTO.getDocumentoId());
		
		ExpedienteDTO exp = ExpedienteTransformer.transformarExpedienteBasico(solDef.getNumeroExpediente());
		
		actualizaEtapaExpedienteService.actualizaEtapaExpediente(exp,etapas);
	}	
	
}
