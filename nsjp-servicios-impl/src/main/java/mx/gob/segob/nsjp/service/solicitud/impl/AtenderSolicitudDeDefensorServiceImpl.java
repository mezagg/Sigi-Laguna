/**
 * 
 */
package mx.gob.segob.nsjp.service.solicitud.impl;

import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.expediente.TipoMovimiento;
import mx.gob.segob.nsjp.comun.enums.institucion.Areas;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.caso.CasoDAO;
import mx.gob.segob.nsjp.dao.catalogo.CatEtapaDAO;
import mx.gob.segob.nsjp.dao.documento.AvisoDesignacionDAO;
import mx.gob.segob.nsjp.dao.solicitud.SolicitudDAO;
import mx.gob.segob.nsjp.dao.solicitud.SolicitudDefensorDAO;
import mx.gob.segob.nsjp.dto.catalogo.CatEtapaDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.institucion.AreaDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDefensorDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.model.AvisoDesignacion;
import mx.gob.segob.nsjp.model.Caso;
import mx.gob.segob.nsjp.model.CatEtapa;
import mx.gob.segob.nsjp.model.Expediente;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.model.RegistroBitacora;
import mx.gob.segob.nsjp.model.Solicitud;
import mx.gob.segob.nsjp.model.SolicitudDefensor;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.bitacora.RegistrarBitacoraService;
import mx.gob.segob.nsjp.service.expediente.AsignarNumeroExpedienteService;
import mx.gob.segob.nsjp.service.expediente.impl.transform.ExpedienteTransformer;
import mx.gob.segob.nsjp.service.solicitud.AtenderSolicitudDeDefensorService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 * @author AlejandroGA
 *
 */

@Service
public class AtenderSolicitudDeDefensorServiceImpl implements
		AtenderSolicitudDeDefensorService {

	@Autowired
	private SolicitudDAO solicitudDAO;
	@Autowired
	private AsignarNumeroExpedienteService asignarNumeroExpedienteService;
	@Autowired
	private AvisoDesignacionDAO avisoDesignacionDAO;
	@Autowired
	private CasoDAO casoDAO;
	@Autowired
	private SolicitudDefensorDAO solicitudDefensorDAO;
	@Autowired
	private CatEtapaDAO catEtapaDAO; 
	@Autowired
	private RegistrarBitacoraService registrarBitacoraService;
	
    public final static String ABRIR_VISOR = "abrirVisor";
    public final static String SOLICITUD_INEXISTENTE = "No se puede obtener informacion de esta solicitud";
    public final static String SOLICITUD_CON_DEFENSOR = "La solicitud ya cuenta con un defensor asignado";
	
	/**
     * Logger
     */
    private final static Logger logger = Logger
            .getLogger(AtenderSolicitudDeDefensorServiceImpl.class);
	
	@Transactional
	@Override
	public String atenderSolicitudDeDefensor(SolicitudDefensorDTO inputSolDefDto,UsuarioDTO usuarioFirmado)
			throws NSJPNegocioException {

		logger.debug("*********SERVICIO PARA ATENDER UNA SOLICITUD***************");

		if (inputSolDefDto == null || inputSolDefDto.getDocumentoId() == null
				|| inputSolDefDto.getDocumentoId() <= 0L
				|| usuarioFirmado == null
				|| usuarioFirmado.getFuncionario() == null) {

			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		String respuesta = "";
		
		Solicitud solBD = solicitudDAO.read(inputSolDefDto.getDocumentoId());
		
		if (solBD == null) {
			respuesta = SOLICITUD_INEXISTENTE;
		}
		
		logger.debug("Comienza atender solicitud:::::");
		
		if(solBD.getNumeroCasoAsociado() != null){
			
			//NO TIENE NUMERO DE EXPEDIENTE
			if(solBD.getNumeroExpediente() == null){
				//Si la solicitud no tiene numero de expediente, lo generamos
				//y lo asociamos a la solicitud
				Caso casoObtenido = casoDAO.consultarCasoPorNumeroCaso(solBD.getNumeroCasoAsociado());
				
				if(casoObtenido == null || casoObtenido.getExpedientes() == null || casoObtenido.getExpedientes().isEmpty()){
					logger.error("El caso no existe o el caso no tiene ningun expediente");
					throw new NSJPNegocioException(CodigoError.INFORMACION_PARAMETROS_ERRONEA);
				}
				
				Expediente expdedienteObtenido = casoObtenido.getExpedientes().iterator().next();
				
				ExpedienteDTO expedienteDTO = new ExpedienteDTO();
				
				expedienteDTO = ExpedienteTransformer
				.transformaExpediente(expdedienteObtenido);
						
				expedienteDTO.setUsuario(usuarioFirmado);
				
				CatEtapa etapaInicial = null;
				
				if (solBD.getConfInstitucion() != null
						&& solBD.getConfInstitucion().getConfInstitucionId() != null) {
					
					etapaInicial = catEtapaDAO
							.consultarEtapaInicialPorInstitucion(solBD
									.getConfInstitucion()
									.getConfInstitucionId());

					if (etapaInicial == null) {
						logger.error("No se pudo obtener la etapa Inicial");
						throw new NSJPNegocioException(
								CodigoError.INFORMACION_PARAMETROS_ERRONEA);
					}
					
					expedienteDTO.setCatEtapaDTO(new CatEtapaDTO(etapaInicial
							.getCatEtapaId()));
					
					expedienteDTO.setEtapa(new ValorDTO(etapaInicial
							.getCatEtapaId()));
				}

				expedienteDTO.setArea(new AreaDTO(Areas.DEFENSORIA.parseLong()));
				
				ExpedienteDTO expedienteDto = asignarNumeroExpedienteService
						.asignarNumeroExpedienteDefensoria(expedienteDTO);
				
				if(expedienteDto == null || expedienteDto.getNumeroExpedienteId() == null){
					logger.error("No se pudo generar el numero de expediente");
					throw new NSJPNegocioException(CodigoError.EJCUCION_OPERACION_ESTADO_INCORRECTO);
				}

				NumeroExpediente numeroExpedienteSol = new NumeroExpediente(
						expedienteDto.getNumeroExpedienteId());

				solBD.setNumeroExpediente(numeroExpedienteSol);

				solBD.setDestinatario(new Funcionario(usuarioFirmado
						.getFuncionario().getClaveFuncionario()));
				
				//Ajustamos el numero de expediente Asociado 
				if (solBD.getConfInstitucion().getConfInstitucionId()
						.equals(Instituciones.DEF.getValorId())) {
					solBD.setNumeroExpedienteAsociado(expedienteDto.getNumeroExpediente());
				}
				
				solicitudDAO.update(solBD);
				
				//Verificamos si existen otras solicitudes relacionadas al caso,
				//sin numero de expediente y sin aviso de designacion
				List<SolicitudDefensor> listaSolicitudesHermanas = solicitudDefensorDAO
						.consultarSolDeDefensorPorNumeroExpediente(solBD
								.getNumeroCasoAsociado(),null,false,false);
				
				//Si la lista no esta vacia, les asociamos tambien el numero de expediente
				if (listaSolicitudesHermanas != null
						&& !listaSolicitudesHermanas.isEmpty()) {
					
					for (Solicitud solicitudHermana : listaSolicitudesHermanas) {
						solicitudHermana
								.setNumeroExpediente(numeroExpedienteSol);
						solicitudHermana.setDestinatario(new Funcionario(
								usuarioFirmado.getFuncionario()
										.getClaveFuncionario()));
					}
					solicitudDefensorDAO
							.saveOrUpdateAll(listaSolicitudesHermanas);
				}
				
				respuesta=ABRIR_VISOR;
				
				// Registrar cambio de etapa
				RegistroBitacora regBitEta = new RegistroBitacora();
				regBitEta.setFechaInicio(new Date());
				regBitEta.setTipoMovimiento(new Valor(
						TipoMovimiento.CAMBIO_DE_ETAPA_DE_EXPEDIENTE
								.getValorId()));
				regBitEta.setNuevo(String.valueOf(etapaInicial
						.getCatEtapaValor().getValorId()));
				regBitEta.setNumeroExpediente(numeroExpedienteSol);
				registrarBitacoraService
						.registrarMovimientoDeExpedienteEnBitacora(regBitEta);
				logger.info("Se registra en Bitacora :"
						+ numeroExpedienteSol.getNumeroExpedienteId() + "-"
						+ etapaInicial.getCatEtapaValor().getValor());
			} else {
				// SI LA SOLICITUD TIENE NUMERO DE EXPEDIENTE
				AvisoDesignacion avisoDesig = avisoDesignacionDAO
						.consultarAvisoDesignacionPorSolicitudDeDefensor(solBD
								.getDocumentoId());
				if(avisoDesig != null){
					respuesta=SOLICITUD_CON_DEFENSOR;
				}else{
					respuesta=ABRIR_VISOR;
				}				
			}
		}else{
			logger.error("La solicitud no cuenta con numero de caso asociado");
			throw new NSJPNegocioException(CodigoError.INFORMACION_PARAMETROS_ERRONEA);
		}
		
		return respuesta;
	}

}
