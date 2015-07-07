package mx.gob.segob.nsjp.service.documento.impl;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.enums.documento.EstatusNotificacion;
import mx.gob.segob.nsjp.comun.enums.documento.TipoDocumento;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.expediente.EstatusExpediente;
import mx.gob.segob.nsjp.comun.enums.expediente.EtapasExpediente;
import mx.gob.segob.nsjp.comun.enums.expediente.TipoMovimiento;
import mx.gob.segob.nsjp.comun.enums.forma.Formas;
import mx.gob.segob.nsjp.comun.enums.institucion.Areas;
import mx.gob.segob.nsjp.comun.enums.involucrado.SituacionJuridica;
import mx.gob.segob.nsjp.comun.enums.relacion.Relaciones;
import mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud;
import mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.avisodetencion.AvisoDetencionDAO;
import mx.gob.segob.nsjp.dao.documento.AvisoDesignacionDAO;
import mx.gob.segob.nsjp.dao.documento.DocumentoDAO;
import mx.gob.segob.nsjp.dao.domicilio.DomicilioDAO;
import mx.gob.segob.nsjp.dao.expediente.ExpedienteDAO;
import mx.gob.segob.nsjp.dao.expediente.NumeroExpedienteDAO;
import mx.gob.segob.nsjp.dao.funcionario.FuncionarioDAO;
import mx.gob.segob.nsjp.dao.involucrado.DefensaInvolucradoDAO;
import mx.gob.segob.nsjp.dao.involucrado.InvolucradoDAO;
import mx.gob.segob.nsjp.dao.involucrado.InvolucradoSolicitudDefensorDAO;
import mx.gob.segob.nsjp.dao.solicitud.SolicitudDAO;
import mx.gob.segob.nsjp.dao.solicitud.SolicitudDefensorDAO;
import mx.gob.segob.nsjp.dto.caso.CasoDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.documento.AvisoDesignacionDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.institucion.AreaDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDefensorDTO;
import mx.gob.segob.nsjp.model.AvisoDesignacion;
import mx.gob.segob.nsjp.model.AvisoDetencion;
import mx.gob.segob.nsjp.model.DefensaInvolucrado;
import mx.gob.segob.nsjp.model.DefensaInvolucradoId;
import mx.gob.segob.nsjp.model.Documento;
import mx.gob.segob.nsjp.model.Domicilio;
import mx.gob.segob.nsjp.model.Expediente;
import mx.gob.segob.nsjp.model.Forma;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.model.Involucrado;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.model.RegistroBitacora;
import mx.gob.segob.nsjp.model.Solicitud;
import mx.gob.segob.nsjp.model.SolicitudDefensor;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.bitacora.RegistrarBitacoraService;
import mx.gob.segob.nsjp.service.documento.AvisoDesignacionService;
import mx.gob.segob.nsjp.service.documento.impl.tranform.AvisoDesignacionTransformer;
import mx.gob.segob.nsjp.service.domicilio.impl.transform.DomicilioTransformer;
import mx.gob.segob.nsjp.service.expediente.AsignarNumeroExpedienteService;
import mx.gob.segob.nsjp.service.involucrado.IngresarIndividuoService;
import mx.gob.segob.nsjp.service.involucrado.impl.transform.InvolucradoTransformer;
import mx.gob.segob.nsjp.service.solicitud.GenerarFolioSolicitudService;
import mx.gob.segob.nsjp.service.usuario.impl.transformer.ValorTransformer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional 
@Service
public class AvisoDesignacionServiceImpl implements AvisoDesignacionService {

	private final static Logger logger = Logger.getLogger(AvisoDesignacionServiceImpl.class);
	
	@Autowired
	private AvisoDetencionDAO avisoDetencionDAO;
	@Autowired
	private AvisoDesignacionDAO avisoDesignacionDAO;
	@Autowired
	private AsignarNumeroExpedienteService asignarNumeroExpedienteService;
	@Autowired 
	private ExpedienteDAO expedienteDAO;
	@Autowired
	private SolicitudDefensorDAO solicitudDefensorDAO;
	@Autowired
	private GenerarFolioSolicitudService generarFolioSolicitudService;
	@Autowired
	private NumeroExpedienteDAO numeroExpedienteDAO;
	@Autowired
	private InvolucradoDAO involucradoDAO;
	@Autowired
	private DefensaInvolucradoDAO defensaInvolucradoDAO;
	@Autowired
	private IngresarIndividuoService ingresarIndividuoService;
	@Autowired
	private RegistrarBitacoraService registrarBitacoraService;
	@Autowired
	private DocumentoDAO docDao;
    @Autowired
    private DomicilioDAO domDAO;
    @Autowired
    private InvolucradoSolicitudDefensorDAO involucradoSolicitudDefensorDAO;
    @Autowired
    private SolicitudDAO solicitudDAO;
    @Autowired
    private FuncionarioDAO funcionarioDAO; 
	
	@Override
	//MOD CoordinadorDef, Defensor
	public List<AvisoDesignacionDTO> consultarAvisosDesignacion(
			EstatusNotificacion estadoNotificacion, FuncionarioDTO funcionario, TiposSolicitudes tipoSolicitud, Long distritoId) throws NSJPNegocioException {
		
		Long claveFuncionario = funcionario != null ? claveFuncionario = funcionario
				.getClaveFuncionario() : null;
		Long estadoNotificacionId = estadoNotificacion != null ? estadoNotificacion.getValorId() : null;
		Long tipoSolicitudId = tipoSolicitud != null ? tipoSolicitud
				.getValorId() : null;
		
		List<AvisoDesignacion> avisos = avisoDesignacionDAO.consultarAvisosDesignacion(estadoNotificacionId, claveFuncionario, tipoSolicitudId, distritoId);
		
		List<AvisoDesignacionDTO> designaciones = new LinkedList<AvisoDesignacionDTO>();
		AvisoDesignacionDTO dto = null;
		
		//TODO GBP Revisar para que se requiere setTieneSolicitudDefensorExterno
		for (AvisoDesignacion aviso : avisos) {
		    dto = AvisoDesignacionTransformer.transformar(aviso);
			List<Documento> lista = docDao.consultarDocumentosByExpedienteIdYForma(aviso.getExpediente().getExpedienteId(), Formas.SOLICITUD_DEFENSOR_EXTERNO);
			if (lista!=null && !lista.isEmpty()) {
			    dto.setTieneSolicitudDefensorExterno(Boolean.TRUE);
			}
			designaciones.add(dto);
		}
		
		return designaciones;
	}
	
	@Override
	public AvisoDesignacionDTO consultarAvisoDesignacion(Long idAviso)
			throws NSJPNegocioException {

		if (idAviso == null || idAviso <= 0L) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		logger.debug("Recuperando Aviso de Designacion con id :: " + idAviso);

		AvisoDesignacion aviso = avisoDesignacionDAO.read(idAviso);

		AvisoDesignacionDTO avisoDTO = null;

		if (aviso != null) {
			avisoDTO = AvisoDesignacionTransformer.transformar(aviso);

			if (avisoDTO.getExpediente() != null
					&& avisoDTO.getExpediente().getNumeroExpedienteId() != null) {
				Involucrado defendido = defensaInvolucradoDAO
						.consultarInvolucradoNumeroExpedienteDefensoria(avisoDTO
								.getExpediente().getNumeroExpedienteId());
				if (defendido != null) {
					avisoDTO.getExpediente().setInputado(
							InvolucradoTransformer
									.transformarInvolucrado(defendido));
				}
			}
		}

		return avisoDTO;
	}

	@Override
	@Transactional
	@Deprecated
	public AvisoDesignacionDTO registrarAvisoDesignacion(
            AvisoDesignacionDTO designacion) throws NSJPNegocioException {

        String folio = "";
        Expediente expediente = null;
        AvisoDetencion avisoDetencion = null;
        SolicitudDefensor solicitud = null;
        ExpedienteDTO nuevoExp = null;
        Involucrado invDefendido = null;
        if (designacion.getExpediente() == null) {

            if (designacion.getAvisoDetencion() != null) {
                avisoDetencion = avisoDetencionDAO.read(designacion
                        .getAvisoDetencion().getDocumentoId());
                folio = avisoDetencion.getDetencion().getInvolucrado()
                        .getFolioElemento();
                // se obtiene el expediente del involucrado cuando se registro el aviso de detencion
                nuevoExp = new ExpedienteDTO(avisoDetencion.getDetencion().getInvolucrado().getExpediente().getExpedienteId());
            } else {
                solicitud = solicitudDefensorDAO.read(designacion
                        .getSolicitudDefensor().getDocumentoId());
                folio = solicitud.getFolioElementoDetenido();
             // se obtiene el expediente del involucrado cuando se registro la solicitud de defensor
                if (solicitud.getNumeroExpediente() != null ){
                	nuevoExp = new ExpedienteDTO(solicitud.getNumeroExpediente().getExpediente().getExpedienteId());                	
                }else{
                	nuevoExp = new ExpedienteDTO();
                }
            }

            InvolucradoDTO invProcuraduria = new InvolucradoDTO();
            invProcuraduria.setFolioElemento(folio);
            
            nuevoExp.setArea(new AreaDTO(Areas.COORDINACION_DEFENSORIA));
            CasoDTO caso = new CasoDTO();
            invDefendido = involucradoDAO
                    .obtenerInvolucradoByFolioElemento(folio);
            final InvolucradoDTO defendido = InvolucradoTransformer
                    .clonarInvolucrado(invDefendido, Calidades.DEFENDIDO, false);
            
            Domicilio domicilio = domDAO.obtenerDomicilioByRelacion(invDefendido.getElementoId(), new Long(Relaciones.RESIDENCIA.ordinal())); 
            Domicilio domicilioNotificacion = domDAO.obtenerDomicilioByRelacion(invDefendido.getElementoId(), new Long(Relaciones.NOTIFICACION.ordinal())); 
            
            if (domicilio!=null) {
                defendido.setDomicilio(DomicilioTransformer.transformarDomicilio(domicilio));
                defendido.getDomicilio().setElementoId(null);
            }
            if (domicilioNotificacion!=null) {
                defendido.setDomicilioNotificacion(DomicilioTransformer.transformarDomicilio(domicilioNotificacion));
                defendido.getDomicilioNotificacion().setElementoId(null);
            }
            
            if (designacion.getAvisoDetencion() != null) {
                nuevoExp.setEtapa(new ValorDTO(EtapasExpediente.INTEGRACION
                        .getValorId()));
                caso.setNumeroGeneralCaso(designacion.getAvisoDetencion()
                        .getNumeroCasoAsociado());
                defendido.setValorSituacionJuridica(new ValorDTO(
                        SituacionJuridica.INDICIADO.getValorId()));
            } else {
                nuevoExp.setEtapa(new ValorDTO(EtapasExpediente.TECNICA
                        .getValorId()));
                caso.setNumeroGeneralCaso(designacion.getSolicitudDefensor()
                        .getNumeroCasoAsociado());
                defendido.setValorSituacionJuridica(new ValorDTO(
                        SituacionJuridica.IMPUTADO.getValorId()));
            }

            nuevoExp.setUsuario(designacion.getUsuario());
            nuevoExp = asignarNumeroExpedienteService
                    .asignarNumeroExpedienteDefensoria(nuevoExp);
            designacion.setExpediente(nuevoExp);

            defendido.setElementoId(ingresarIndividuoService
                    .ingresarIndividuo(defendido));
            DefensaInvolucrado defensaInvolucrado = new DefensaInvolucrado();
            final DefensaInvolucradoId id = new DefensaInvolucradoId();
            id.setInvolucradoId(defendido.getElementoId());
            id.setNumeroExpedienteId(nuevoExp.getNumeroExpedienteId());
            defensaInvolucrado.setId(id);
            defensaInvolucrado.setInvolucradoPg(new Involucrado(invDefendido
                    .getElementoId()));
            defensaInvolucradoDAO.create(defensaInvolucrado);
            if (defendido.getValorSituacionJuridica() != null) {
                RegistroBitacora regBitSJ = new RegistroBitacora();

                regBitSJ.setFechaInicio(new Date());
                regBitSJ.setTipoMovimiento(new Valor(
                        TipoMovimiento.CAMBIO_DE_SITUACION_JURIDICA
                                .getValorId()));
                regBitSJ.setNuevo(String.valueOf(defendido.getValorSituacionJuridica().getIdCampo()));
                regBitSJ.setNumeroExpediente(new NumeroExpediente(nuevoExp.getNumeroExpedienteId()));
                registrarBitacoraService
                        .registrarMovimientoDeExpedienteEnBitacora(regBitSJ);
            }

        } else {
            solicitud = solicitudDefensorDAO.read(designacion
                    .getSolicitudDefensor().getDocumentoId());
            expediente = expedienteDAO.read(solicitud.getExpediente()
                    .getExpedienteId());
            NumeroExpediente ne = expediente.getNumeroExpedientes().iterator()
                    .next();
            designacion.getExpediente().setExpedienteId(
                    expediente.getExpedienteId());
            designacion.getExpediente().setNumeroExpedienteId(
                    ne.getNumeroExpedienteId());
            designacion.getExpediente().setNumeroExpediente(
                    ne.getNumeroExpediente());
        }

        designacion.setEstatus(new ValorDTO(EstatusNotificacion.NO_ATENDIDA
                .getValorId()));
        AvisoDesignacion avisoDesignacion = AvisoDesignacionTransformer
                .transformar(designacion);
        avisoDesignacion.setForma(new Forma(Formas.SOLICITUD.getValorId()));
        avisoDesignacion.setTipoDocumento(new Valor(TipoDocumento.SOLICITUD
                .getValorId()));
        avisoDesignacion.setFechaCreacion(Calendar.getInstance().getTime());
        avisoDesignacion.setNombreDocumento("Aviso de Designacion ");
        avisoDesignacion.setFolioNotificacion(generarFolioSolicitudService
                .generarFolioNotificacion());
        avisoDesignacion.setConsecutivoNotificacion(avisoDesignacion
                .getFolioNotificacion().substring(3));
        designacion
                .setDocumentoId(avisoDesignacionDAO.create(avisoDesignacion));

        expediente = new Expediente(designacion.getExpediente()
                .getExpedienteId());

        if (designacion.getAvisoDetencion() != null) {
            avisoDetencion.setEstatus(new Valor(EstatusNotificacion.ATENDIDA
                    .getValorId()));
            avisoDetencion.setExpediente(expediente);
            avisoDetencionDAO.update(avisoDetencion);
        } else {

            solicitud.setEstatus(new Valor(EstatusSolicitud.CERRADA
                    .getValorId()));
            solicitudDefensorDAO.update(solicitud);
        }

        return designacion;
    }
	
	@Override
	@Transactional
    public AvisoDesignacionDTO registrarAvisoDesignacionPorReasignacionDefensor(
            ExpedienteDTO input) throws NSJPNegocioException {
        if (input.getNumeroExpedienteId() == null) {
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        }
        AvisoDesignacion aviso = new AvisoDesignacion();
        NumeroExpediente numExp = numeroExpedienteDAO.read(input
                .getNumeroExpedienteId());
        numExp.setFuncionario(new Funcionario(input.getUsuario()
                .getFuncionario().getClaveFuncionario()));
        numeroExpedienteDAO.update(numExp);
        AvisoDesignacion avisoAnt = null;
        for (AvisoDesignacion item : numExp.getExpediente()
                .getAvisoDesignaciones()) {
            if (avisoAnt == null
                    || item.getDocumentoId() > avisoAnt.getDocumentoId()) {
                avisoAnt = item;
            }
        }
        if (avisoAnt != null) {
            if(avisoAnt.getAvisoDetencion() != null){
            	aviso.setAvisoDetencion(avisoAnt.getAvisoDetencion());            	
            }
            if(avisoAnt.getSolicitudDefensor() != null){
            	aviso.setSolicitudDefensor(avisoAnt.getSolicitudDefensor());
            }
            cerrarAvisoDesignacion(avisoAnt.getDocumentoId());
        }
        Expediente expediente = numExp.getExpediente();
        aviso.setExpediente(expediente);
        aviso.setEstatus(new Valor(EstatusNotificacion.NO_ATENDIDA.getValorId()));
        aviso.setForma(new Forma(Formas.SOLICITUD.getValorId()));
        aviso.setTipoDocumento(new Valor(TipoDocumento.SOLICITUD.getValorId()));
        aviso.setFechaCreacion(Calendar.getInstance().getTime());
        aviso.setNombreDocumento("Aviso de Designacion ");
        aviso.setFolioNotificacion(generarFolioSolicitudService
                .generarFolioNotificacion());
        aviso.setConsecutivoNotificacion(aviso.getFolioNotificacion()
                .substring(3));
        aviso.setDocumentoId(avisoDesignacionDAO.create(aviso));
        final RegistroBitacora regBitFun = new RegistroBitacora();

        regBitFun.setFechaInicio(new Date());
        regBitFun.setTipoMovimiento(new Valor(
                TipoMovimiento.ASIGNACION_DE_EXPEDIENTE.getValorId()));
        regBitFun.setNuevo("Sin defensor asignado");
        regBitFun.setNumeroExpediente(numExp);

        registrarBitacoraService
                .registrarMovimientoDeExpedienteEnBitacora(regBitFun);
        return AvisoDesignacionTransformer.transformar(aviso);
    }

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	//MOD defensorATE
	public AvisoDesignacionDTO designarAbogadoDefensorAsesoria(
			AvisoDesignacionDTO avisoDesignacionDTO)
			throws NSJPNegocioException {

		if (avisoDesignacionDTO == null
				|| avisoDesignacionDTO.getFuncionario() == null
				|| avisoDesignacionDTO.getFuncionario().getClaveFuncionario() == null
				|| avisoDesignacionDTO.getFuncionario().getClaveFuncionario() <= 0L
				|| avisoDesignacionDTO.getSolicitudDefensor() == null
				|| avisoDesignacionDTO.getSolicitudDefensor().getDocumentoId() <= 0L) {

			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		// Obtenemos la solicitud de defensor que se asociara al aviso de
		// designacion
		SolicitudDefensor solicitudDefensor = solicitudDefensorDAO
				.read(avisoDesignacionDTO.getSolicitudDefensor()
						.getDocumentoId());

		if (solicitudDefensor == null
				|| solicitudDefensor.getExpediente() == null
				|| solicitudDefensor.getExpediente().getExpedienteId() == null
				|| solicitudDefensor.getExpediente().getExpedienteId() <= 0L) {
			throw new NSJPNegocioException(
					CodigoError.INFORMACION_PARAMETROS_ERRONEA);
		}

		logger.info("Aviso Designacion de la solcitud:"
				+ ((solicitudDefensor.getAvisoDesignacion() != null)? solicitudDefensor
				.getAvisoDesignacion().getDocumentoId() : "No tienen"));
		
		// Se genera o actualiza, el aviso de designacion
		AvisoDesignacion aviso = solicitudDefensor.getAvisoDesignacion();
		if (aviso != null && aviso.getDocumentoId() != null
				&& aviso.getDocumentoId() > 0L) {
			aviso.setFuncionario(new Funcionario(avisoDesignacionDTO
					.getFuncionario().getClaveFuncionario()));
		} else {
			aviso = new AvisoDesignacion();
			aviso.setFechaCreacion(Calendar.getInstance().getTime());
			aviso.setEstatus(new Valor(EstatusNotificacion.NO_ATENDIDA.getValorId()));
			aviso.setForma(new Forma(Formas.SOLICITUD_DE_ASESORIA_LEGAL
					.getValorId()));
			aviso.setTipoDocumento(new Valor(
					TipoDocumento.SOLICITUD_DE_ASESORIA_LEGAL.getValorId()));
			aviso.setNombreDocumento("Aviso de Designacion ");
			aviso.setFolioNotificacion(generarFolioSolicitudService
					.generarFolioNotificacion());
			aviso.setConsecutivoNotificacion(aviso.getFolioNotificacion()
					.substring(3));
			aviso.setSolicitudDefensor(solicitudDefensor);
			aviso.setExpediente(solicitudDefensor.getExpediente());
			aviso.setFuncionario(new Funcionario(avisoDesignacionDTO
					.getFuncionario().getClaveFuncionario()));
		}
		avisoDesignacionDAO.saveOrUpdate(aviso);

		// Cambiar el estatus de la solicitud de defensor a ASIGNADO
		solicitudDefensor.setEstatus(new Valor(EstatusSolicitud.ASIGNADO
				.getValorId()));
		solicitudDefensorDAO.update(solicitudDefensor);

		
		//Cambiar el estatus del numero de expediente
		if (solicitudDefensor.getNumeroExpediente() != null) {
			NumeroExpediente numeroExpediente = solicitudDefensor
					.getNumeroExpediente();
			numeroExpediente.setEstatus(new Valor(EstatusExpediente.ASIGNADO
					.getValorId()));
			numeroExpediente.setFuncionario(new Funcionario(avisoDesignacionDTO
					.getFuncionario().getClaveFuncionario()));
			numeroExpedienteDAO.update(numeroExpediente);
			logger.debug("Se actualizo el estatus de NumeroExpediente ("
					+ numeroExpediente.getNumeroExpedienteId() + "):"
					+ numeroExpediente.getEstatus().getValor());
		}
		
		//Datos de Respuesta
		avisoDesignacionDTO.getSolicitudDefensor().setEstatus(
				ValorTransformer.transformar(solicitudDefensor.getEstatus()));
		
		return avisoDesignacionDTO;
	}
	
	
	@Override
	@Transactional
	//MOD CoordinadorDEF
	//TODO GBP Hacer los cambios similares como designarAbogadoDefensorAsesoria
	public void designarAbogadoDefensor(
			AvisoDesignacionDTO avisoDesignacionInput,
			Boolean cambiarEstatusExpediente, Boolean esIterativo)
			throws NSJPNegocioException {
	
		if (avisoDesignacionInput == null
				|| avisoDesignacionInput.getFuncionario() == null
				|| avisoDesignacionInput.getFuncionario().getClaveFuncionario() == null
				|| avisoDesignacionInput.getFuncionario().getClaveFuncionario() <= 0L
				|| avisoDesignacionInput.getSolicitudDefensor() == null
				|| avisoDesignacionInput.getSolicitudDefensor()
						.getDocumentoId() <= 0L) {

			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		// Obtenemos la solicitud de defensor que se asociara al aviso de
		// designacion
		SolicitudDefensor solicitudDefensor = solicitudDefensorDAO
				.read(avisoDesignacionInput.getSolicitudDefensor()
						.getDocumentoId());

		if (solicitudDefensor == null
				|| solicitudDefensor.getExpediente() == null
				|| solicitudDefensor.getExpediente().getExpedienteId() == null
				|| solicitudDefensor.getExpediente().getExpedienteId() <= 0L) {
			throw new NSJPNegocioException(
					CodigoError.INFORMACION_PARAMETROS_ERRONEA);
		}

		// FIXME Si el cambio de que la solicitud para que vea el aviso de
		// designaci&oacute;n ya no es necesario hacer uso de una consulta  
		logger.info("Aviso Designacion de la solcitud:"+solicitudDefensor.getAvisoDesignacion());
		
		AvisoDesignacion avisoSolicitud = avisoDesignacionDAO
				.consultarAvisoDesignacionPorSolicitudDeDefensor(avisoDesignacionInput
						.getSolicitudDefensor().getDocumentoId());
		
		// La solicitud ya cuenta con un aviso de designacion, (Actualizar aviso
		// designacion)
		if (avisoSolicitud != null) {
			throw new NSJPNegocioException(
					CodigoError.INFORMACION_PARAMETROS_ERRONEA);
		}

		// Generamos el nuevo aviso de designacion
		AvisoDesignacion aviso = new AvisoDesignacion();

		aviso.setFuncionario(new Funcionario(avisoDesignacionInput
				.getFuncionario().getClaveFuncionario()));
		aviso.setSolicitudDefensor(solicitudDefensor);
		aviso.setExpediente(solicitudDefensor.getExpediente());
		aviso.setFechaCreacion(Calendar.getInstance().getTime());
		aviso.setEstatus(new Valor(
				EstatusNotificacion.NO_ATENDIDA.getValorId(),
				EstatusNotificacion.NO_ATENDIDA.name()));
		aviso.setForma(new Forma(Formas.SOLICITUD.getValorId()));
		aviso.setTipoDocumento(new Valor(TipoDocumento.SOLICITUD.getValorId()));
        aviso.setNombreDocumento("Aviso de Designacion ");
        aviso.setFolioNotificacion(generarFolioSolicitudService
                .generarFolioNotificacion());
        aviso.setConsecutivoNotificacion(aviso.getFolioNotificacion()
                .substring(3));
		
		if(solicitudDefensor.getAudiencia() != null){
			aviso.setAudiencia(solicitudDefensor.getAudiencia());
		}

		avisoDesignacionDAO.create(aviso);
		
		// Se hace copia de los PR(imputados) con la calidad de Defendido
		generarCopiaImputadosADefendidoSolicitudDefensor(
				solicitudDefensor.getDocumentoId(), solicitudDefensor
						.getExpediente().getExpedienteId());
		
		//Cambiar el estatus de la solicitud de defensor a EN PROCESO
		solicitudDefensor.setEstatus(new Valor(EstatusSolicitud.EN_PROCESO
				.getValorId()));
		solicitudDefensorDAO.update(solicitudDefensor);
		
		if(cambiarEstatusExpediente != null && cambiarEstatusExpediente.equals(true)){
			
			// Cambiamos el responsable del numero expediente por el defensor seleccionado
			NumeroExpediente nExpediente = aviso.getExpediente()
					.getNumeroExpedientes().iterator().next();
			nExpediente.setFuncionario(aviso.getFuncionario());

			// Cambiamos el estatus del expediente
			Long idEstatus = EstatusExpediente.ABIERTO_TECNICA_SIN_CARPETA.getValorId();
			
//			Long idEstatus = EstatusExpediente.ABIERTO_RESTAURATIVA.getValorId();
//
//			if (nExpediente.getEtapa() != null) {
//				switch (EtapasExpediente.getByValor(nExpediente.getEtapa()
//						.getValorId())) {
//				case ASESORIA:
//					idEstatus = EstatusExpediente.ABIERTO.getValorId();
//					break;
//				case CONCILIACION_Y_MEDIACION:
//					idEstatus = EstatusExpediente.ABIERTO_RESTAURATIVA.getValorId();
//					break;
//				case INTEGRACION:
//					idEstatus = EstatusExpediente.ABIERTO_INTEGRACION.getValorId();
//					break;
//				case TECNICA:
//					idEstatus = EstatusExpediente.ABIERTO_TECNICA_SIN_CARPETA
//							.getValorId();
//					break;
//				case EJECUCION:
//					idEstatus = EstatusExpediente.ABIERTO_EJECUCION.getValorId();
//					break;
//				}
//			}
			nExpediente.setEstatus(new Valor(idEstatus));
			numeroExpedienteDAO.update(nExpediente);

			//Registramos la asigancion del expediente en la bitacora
			final RegistroBitacora regBitFun = new RegistroBitacora();

			regBitFun.setFechaInicio(new Date());
			regBitFun.setTipoMovimiento(new Valor(
					TipoMovimiento.ASIGNACION_DE_EXPEDIENTE.getValorId()));
			regBitFun.setNuevo(String.valueOf(aviso.getFuncionario()
					.getClaveFuncionario()));
			regBitFun.setNumeroExpediente(nExpediente);

			registrarBitacoraService
					.registrarMovimientoDeExpedienteEnBitacora(regBitFun);

			//Registramos el cambio de estatus del expediente en la bitacora
			final RegistroBitacora regBitEstatus = new RegistroBitacora();

			regBitEstatus.setFechaInicio(new Date());
			regBitEstatus.setTipoMovimiento(new Valor(
					TipoMovimiento.CAMBIO_DE_ESTATUS_DE_EXPEDIENTE.getValorId()));
			regBitEstatus.setNuevo(String.valueOf(idEstatus));
			regBitEstatus.setNumeroExpediente(nExpediente);

			registrarBitacoraService
					.registrarMovimientoDeExpedienteEnBitacora(regBitEstatus);			
		}
	
		
		if(esIterativo != null && esIterativo.equals(true)){			
			//Ahora verificamos si existen otras solicitudes de defensor asociadas mismo caso 
			//y al mismo numero expediente
			// sin aviso de designacion, y lo generamos
			//para cada una de ellas
			List<SolicitudDefensor> listaSolicitudesSinAvisoDesignacion = solicitudDefensorDAO
					.consultarSolDeDefensorPorNumeroExpediente(
							solicitudDefensor.getNumeroCasoAsociado(), null,
							true, false);
			
			for (SolicitudDefensor solicitudDef : listaSolicitudesSinAvisoDesignacion) {
				AvisoDesignacionDTO avisoDesignacionFiltro = new AvisoDesignacionDTO();
				avisoDesignacionFiltro
						.setSolicitudDefensor(new SolicitudDefensorDTO(
								solicitudDef.getDocumentoId()));
				avisoDesignacionFiltro.setFuncionario(new FuncionarioDTO(
						avisoDesignacionInput.getFuncionario()
								.getClaveFuncionario()));
				//Se invoca a si mismo para generar un aviso para las otras solicitudes
				//que se encontaron
				this.designarAbogadoDefensor(avisoDesignacionFiltro, false,
						false);
			}
		}		
	}
	
	@Override
	@Transactional
	public void reasignarAbogadoDefensorExpediente(
			AvisoDesignacionDTO AvisoDesignacionInput)
			throws NSJPNegocioException {

		if (AvisoDesignacionInput == null
				|| AvisoDesignacionInput.getFuncionario() == null
				|| AvisoDesignacionInput.getFuncionario().getClaveFuncionario() == null
				|| AvisoDesignacionInput.getFuncionario().getClaveFuncionario() <= 0L
				|| AvisoDesignacionInput.getExpediente() == null
				|| AvisoDesignacionInput.getExpediente()
						.getNumeroExpedienteId() == null
				|| AvisoDesignacionInput.getExpediente()
						.getNumeroExpedienteId() <= 0L) {

			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		NumeroExpediente numExpRecuperado = numeroExpedienteDAO
				.read(AvisoDesignacionInput.getExpediente()
						.getNumeroExpedienteId());

		if (numExpRecuperado == null) {
			throw new NSJPNegocioException(
					CodigoError.INFORMACION_PARAMETROS_ERRONEA);
		}

		/* Si el funcionario seleccionado es diferente 
		 * al funcionario duenio del expediente
		 */
		if (numExpRecuperado.getFuncionario() != null
				&& numExpRecuperado.getFuncionario().getClaveFuncionario() != null
				&& !numExpRecuperado
						.getFuncionario()
						.getClaveFuncionario()
						.equals(AvisoDesignacionInput.getFuncionario()
								.getClaveFuncionario())) {
			
			/* verificar si el expediente tiene una solicitud de carpeta de
			* invetigacion abierta, o en proceso
			*/
			Solicitud solFiltro = new Solicitud();
			solFiltro.setNumeroExpediente(new NumeroExpediente(numExpRecuperado
					.getNumeroExpedienteId()));
			
			Long[] idES= {EstatusSolicitud.ABIERTA.getValorId(),EstatusSolicitud.EN_PROCESO.getValorId()};
			Long[] idTS= {TiposSolicitudes.CARPETA_DE_INVESTIGACION.getValorId()};
			List<Long> lstIdEstatusSolicitud= Arrays.asList(idES);
			List<Long> lstIdTipoSolicitud = Arrays.asList(idTS);
			
			List<Solicitud> solicitudesDeCarpetaInv = solicitudDAO
					.consultarSolicitudesConCriterios(solFiltro,
							lstIdEstatusSolicitud, lstIdTipoSolicitud, null);
			
			if (solicitudesDeCarpetaInv != null
					&& !solicitudesDeCarpetaInv.isEmpty()) {
				//Existe al menos una solicitud de carpeta de investigacion abierta o en proceso
				throw new NSJPNegocioException(
						CodigoError.NUM_EXP_CON_SOLICITUD_ACTIVA_DE_CARPETA_DE_INVESTIGACION);
			}
			else{
				//modificar el numero de expediente con la clave del funcionario y todos los avisos de
				//designacion de ese expediente
				Funcionario funcionarioUpdate = funcionarioDAO
						.read(AvisoDesignacionInput.getFuncionario()
								.getClaveFuncionario());
				
				if(funcionarioUpdate == null){
					throw new NSJPNegocioException(
							CodigoError.INFORMACION_PARAMETROS_ERRONEA);
				}
					
				numExpRecuperado.setFuncionario(funcionarioUpdate);
				numeroExpedienteDAO.update(numExpRecuperado);
				
				List<AvisoDesignacion> lstAvisosDesignacion = avisoDesignacionDAO
						.consultarAvisosDesignacionPorIdExpediente(numExpRecuperado
								.getExpediente().getExpedienteId());
				
				if (lstAvisosDesignacion != null
						&& !lstAvisosDesignacion.isEmpty()) {
					
					for (AvisoDesignacion avisoDesignacion : lstAvisosDesignacion) {
						avisoDesignacion.setFuncionario(funcionarioUpdate);
						avisoDesignacionDAO.update(avisoDesignacion);
					}
				}
			}
		}else{
			throw new NSJPNegocioException(
					CodigoError.MISMO_FUNCIONARIO);
		}

	}

	@Override
	@Transactional
	public void cerrarAvisoDesignacion(Long idAvisoDesignacion) {

		AvisoDesignacion aviso = avisoDesignacionDAO.read(idAvisoDesignacion);
		aviso.setEstatus(new Valor(EstatusNotificacion.ATENDIDA.getValorId()));
		avisoDesignacionDAO.update(aviso);
	}

	@Override
	public Boolean generarCopiaImputadosADefendidoSolicitudDefensor(
			Long solicitudDefensorId, Long expedienteId)
			throws NSJPNegocioException {

		Boolean resultado = false;
		if (solicitudDefensorId == null || solicitudDefensorId < 0) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		List<Involucrado> listaInvolucrados = involucradoSolicitudDefensorDAO
				.consultarInvolucradosSolicitudDefensor(solicitudDefensorId);

		//Consultar los folios de los defendidos asociados al expediente
		List<String> listaFolioDefendidos = involucradoDAO
				.obtenerFolioInterInstInvolucradosPorExpedienteYCalidades(
						expedienteId, new Calidades[] { Calidades.DEFENDIDO },
						null);
		Set<String> conjuntoFolios = new HashSet<String>(listaFolioDefendidos);

		for (Involucrado involucrado : listaInvolucrados) {
			
			//Buscar si el defendido ya se ha duplicado
			Boolean esRegistrado = conjuntoFolios.contains(new String(involucrado.getFolioElemento()));
			//Si no esta registrado, se hace el registro correspondiente
			if(!esRegistrado){
				InvolucradoDTO defendido = InvolucradoTransformer
						.clonarInvolucrado(involucrado, Calidades.DEFENDIDO, false);
	
				// Relacion con el domicilio
				Domicilio domicilio = domDAO.obtenerDomicilioByRelacion(
						defendido.getElementoId(),
						new Long(Relaciones.RESIDENCIA.ordinal()));
				Domicilio domicilioNotificacion = domDAO
						.obtenerDomicilioByRelacion(defendido.getElementoId(),
								new Long(Relaciones.NOTIFICACION.ordinal()));
	
				if (domicilio != null) {
					defendido.setDomicilio(DomicilioTransformer
							.transformarDomicilio(domicilio));
					defendido.getDomicilio().setElementoId(null);
				}
				if (domicilioNotificacion != null) {
					defendido.setDomicilioNotificacion(DomicilioTransformer
							.transformarDomicilio(domicilioNotificacion));
					defendido.getDomicilioNotificacion().setElementoId(null);
				}
				
				//Se copia el folio interinstitucional de origen de la copia
				defendido.setFolioElemInterInstitucional(involucrado.getFolioElemento());
				
				// se guarda el involucrado con calidad de Defendido y domicilio
				defendido.setElementoId(ingresarIndividuoService
						.ingresarIndividuo(defendido));
	
				resultado = true;
			}
		}
		return resultado;
	}
}
