/**
* Nombre del Programa : ConsultarSolicitudesDefensoriaServiceImpl.java
* Autor                            : Hugo Serrano
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 20 Jun 2011
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
package mx.gob.segob.nsjp.service.solicitud.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.enums.configuracion.Parametros;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud;
import mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.delito.DelitoDAO;
import mx.gob.segob.nsjp.dao.involucrado.InvolucradoSolicitudDefensorDAO;
import mx.gob.segob.nsjp.dao.parametro.ParametroDAO;
import mx.gob.segob.nsjp.dao.solicitud.SolicitudDefensorDAO;
import mx.gob.segob.nsjp.dto.expediente.DelitoDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDefensorDTO;
import mx.gob.segob.nsjp.model.Delito;
import mx.gob.segob.nsjp.model.Involucrado;
import mx.gob.segob.nsjp.model.Parametro;
import mx.gob.segob.nsjp.model.SolicitudDefensor;
import mx.gob.segob.nsjp.service.delito.impl.transform.DelitoTransfromer;
import mx.gob.segob.nsjp.service.expediente.BuscarExpedienteService;
import mx.gob.segob.nsjp.service.involucrado.ConsultarIndividuoService;
import mx.gob.segob.nsjp.service.involucrado.impl.transform.InvolucradoTransformer;
import mx.gob.segob.nsjp.service.solicitud.ConsultarSolicitudesDefensoriaService;
import mx.gob.segob.nsjp.service.solicitud.impl.transform.SolicitudDefensorTransformer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author Tattva-IT
 *
 */

@Service
@Transactional
public class ConsultarSolicitudesDefensoriaServiceImpl implements
		ConsultarSolicitudesDefensoriaService {

	@Autowired
	private SolicitudDefensorDAO solicitudDefensorDAO;	
	@Autowired
	private BuscarExpedienteService buscarExpedienteService;
	@Autowired
	private ParametroDAO parametroDAO;
	@Autowired
	private InvolucradoSolicitudDefensorDAO involucradoSolicitudDefensorDAO;
	@Autowired
	private DelitoDAO delitoDAO;
	@Autowired
	private ConsultarIndividuoService consultarIndividuoService;
	
	private static final Logger logger = Logger
    .getLogger(ConsultarSolicitudesDefensoriaServiceImpl.class);

	@Override
	public List<SolicitudDefensorDTO> obtenerSolicitudesDefensoriaPorEstatus(
            EstatusSolicitud estatusSolc, Instituciones institucion)
            throws NSJPNegocioException {

        logger.debug("Servicio para obtener las Solicitudes no asignadas");

        List<SolicitudDefensorDTO> lsSolDDTO = new ArrayList<SolicitudDefensorDTO>();

        List<SolicitudDefensor> lsSolDef = this.solicitudDefensorDAO
                .obtenerSolicitudesDefensoriaPorEstatus((estatusSolc != null
                        ? estatusSolc.getValorId()
                        : null), institucion);
        logger.debug("Solicitudes encontradas -->" + lsSolDef.size());

        lsSolDDTO = SolicitudDefensorTransformer
                .transformarSolicitudesDefensoria(lsSolDef);

        List<SolicitudDefensorDTO> resp = new ArrayList<SolicitudDefensorDTO>();

        if (lsSolDDTO.size() > 0) {
            int i = 0;
            for (SolicitudDefensorDTO sol : lsSolDDTO) {
                if (sol.getExpedienteDTO() != null) {
                    sol.getExpedienteDTO().setInvolucradosSolicitados(true);
                    ExpedienteDTO expDto = buscarExpedienteService
                            .obtenerExpedienteDefensoria(sol.getExpedienteDTO());
                    sol.setExpedienteDTO(expDto);
                }
                logger.debug("ITERACION# -->" + i++);
                resp.add(sol);

            }
        }
        logger.debug("TAMANO DE LA RESP -->" + resp.size());
        return resp;
    }
	
	@Override
	public List<SolicitudDefensorDTO> obtenerSolicitudesAsesoriaDefensoriaPorEstatus(
            EstatusSolicitud estatusSolc, Instituciones institucion)
            throws NSJPNegocioException {

        logger.debug("Servicio para obtener las Solicitudes no asignadas");

        List<SolicitudDefensorDTO> lsSolDDTO = new ArrayList<SolicitudDefensorDTO>();

        List<SolicitudDefensor> lsSolDef = this.solicitudDefensorDAO
                .obtenerSolicitudesAsesoriaDefensoriaPorEstatus((
                        estatusSolc != null ? estatusSolc.getValorId() : null),
                        institucion);
        logger.debug("Solicitudes encontradas -->" + lsSolDef.size());

        lsSolDDTO = SolicitudDefensorTransformer
                .transformarSolicitudesDefensoria(lsSolDef);

        List<SolicitudDefensorDTO> resp = new ArrayList<SolicitudDefensorDTO>();

        if (lsSolDDTO.size() > 0) {
            int i = 0;
            for (SolicitudDefensorDTO s : lsSolDDTO) {
                if (s.getExpedienteDTO() != null) {
                    s.getExpedienteDTO().setInvolucradosSolicitados(true);
                    ExpedienteDTO expDto = buscarExpedienteService
                            .obtenerExpedienteDefensoria(s.getExpedienteDTO());
                    s.setExpedienteDTO(expDto);
                }
                logger.debug("ITERACION# -->" + i++);
                resp.add(s);

            }
        }
        logger.debug("TAMAÑO RESP -->" + resp.size());
        return resp;
    }
	
	@Override
	public List<SolicitudDefensorDTO> consultarSolicitudesDefensoriaByHistoricoYEstatus(
			EstatusSolicitud estatusSolicitud) throws NSJPNegocioException {
		
		if (logger.isDebugEnabled())
			logger.debug("/**** SERVICIO PARA CONSULTAR SOLICITUDES DEFENSORIA HISTORICO POR ESTATUS ****/");
		
		if (estatusSolicitud==null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		
		Parametro parametro = parametroDAO.obtenerPorClave(Parametros.LIMITE_HISTORICO_CONSULTAS);		
		Long dias = new Long(parametro.getValor());

		Calendar calTemp = Calendar.getInstance();
		calTemp.setTime(new Date());
		calTemp.add(calTemp.DATE, -dias.intValue());
		
		
		List<SolicitudDefensor> solDefResp = solicitudDefensorDAO.consultarSolicitudesDefensoriaByHistoricoYEstatus(calTemp.getTime(), estatusSolicitud.getValorId());
		
		List<SolicitudDefensorDTO> solDefDTO = new ArrayList<SolicitudDefensorDTO>();
		for (SolicitudDefensor solicitudDefensor : solDefResp) {
			solDefDTO.add(SolicitudDefensorTransformer.transformarSolicitudDefensoria(solicitudDefensor));
		}
		return solDefDTO;
	}

	
	@Override
	//MOD defensorATE
	public SolicitudDefensorDTO obtenerSolicitudDefensor(
			SolicitudDefensorDTO solDefensorDTO) throws NSJPNegocioException {

		logger.debug("SERVICIO PARA OBTENER UNA SOLICITUD DE DEFENSOR");

		if (solDefensorDTO == null || solDefensorDTO.getDocumentoId() == null
				|| solDefensorDTO.getDocumentoId() <= 0L) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		logger.debug("SOLICITUD DEFENSOR ID::::::::"
				+ solDefensorDTO.getDocumentoId());

		final SolicitudDefensor solicitud = solicitudDefensorDAO
				.read(solDefensorDTO.getDocumentoId());
		
		if(solicitud == null){
			logger.error("NO EXISTE ESTA SOLICITUD");
			throw new NSJPNegocioException(CodigoError.INFORMACION_PARAMETROS_ERRONEA);
		}
		
		
		SolicitudDefensorDTO solicitudDTO = SolicitudDefensorTransformer
				.transformarSolicitudDefensoria(solicitud);
		
		if (solicitudDTO.getExpedienteDTO() != null
				&& solicitudDTO.getExpedienteDTO().getExpedienteId() != null) {
				
			ExpedienteDTO expedienteDTO = solicitudDTO.getExpedienteDTO();
			
			//Para Solicitud de Asesoria Legal - Se recupera el Involucrado
			if (solicitud.getTipoSolicitud() != null
					&& solicitud
							.getTipoSolicitud()
							.getValorId()
							.equals(TiposSolicitudes.ASESORIA_DEFENSORIA
									.getValorId())) {
				// Consultar al Involucrado
				Calidades calidades[]={Calidades.SOLICITANTE};
				List<InvolucradoDTO> defendidos = consultarIndividuoService
						.obtenerInvolucradosPorExpedienteYCalidades(null,
								calidades, true,
								expedienteDTO.getExpedienteId());
				
				//Se obtienen los datos completos del Involucrado
				if (defendidos != null && !defendidos.isEmpty()
						&& defendidos.get(0) != null) {
					InvolucradoDTO defendido = defendidos.get(0);
					defendido = consultarIndividuoService
							.obtenerInvolucrado(defendido);
					//No se requiere en vista
					defendido.setExpedienteDTO(null);
					solicitudDTO.setInvolucradoDTO(defendido);
				}
			}
			
			// Delitos del Expediente
			List<Delito> listaDelitos = delitoDAO
			.obtenerDelitoPorExpediente(expedienteDTO.getExpedienteId());
			if (listaDelitos != null && !listaDelitos.isEmpty()) {
				
				List<DelitoDTO> listaDelitosDTO = new ArrayList<DelitoDTO>();
				
				for (Delito delitosSolicitud : listaDelitos) {
					listaDelitosDTO.add(DelitoTransfromer
							.transformarDelito(delitosSolicitud));
				}
				solicitudDTO.setDelitos(listaDelitosDTO);
			}

			//Seteamos el numero expediente Id de la solicitud 
			if (solicitud.getNumeroExpediente() != null
					&& solicitud.getNumeroExpediente().getNumeroExpedienteId() != null) {
				expedienteDTO.setNumeroExpedienteId(solicitud
						.getNumeroExpediente().getNumeroExpedienteId());
			}
			
			//Atributos no requeridos del expediente
			
			//No se debe subir el archivo digital
			solicitudDTO.setArchivoDigital(null);
			
			//No se sube los siguientes campos
			expedienteDTO.setInvolucradosDTO(null);
			expedienteDTO.setElementosDTO(null);
			expedienteDTO.setObjetosDTO(null);
			expedienteDTO.setDelitos(null);
			expedienteDTO.setDelitoPrincipal(null);
			expedienteDTO.setAvisosDesignacion(null);
			expedienteDTO.setListaDelitos(null);
			expedienteDTO.setEstatus(null);
			solicitudDTO.setExpedienteDTO(expedienteDTO);
			
			//Atributos no requeridos en vista de la solicitud
			if (solicitudDTO.getAvisoDesignacion() != null) {
				if (solicitudDTO.getAvisoDesignacion().getExpediente() != null
						&& solicitudDTO.getAvisoDesignacion().getExpediente()
								.getEstatus() != null) {
					solicitudDTO.getAvisoDesignacion().getExpediente()
							.setEstatus(null);
				}
			}
			if (solicitudDTO.getAudiencia() != null
					&& solicitudDTO.getAudiencia().getExpediente() != null) {
				solicitudDTO.getAudiencia().setExpediente(null);
			}
			
		}
		

		return solicitudDTO;
	}

	@Override
	public List<InvolucradoDTO> consultarInvolucradosSolicitudDefensor(
			Long idSolicitudDefensor) throws NSJPNegocioException {

		if (idSolicitudDefensor == null || idSolicitudDefensor < 0) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		List<InvolucradoDTO> listaInvolucradosDTO = null;

		List<Involucrado> listaInvolucrados = involucradoSolicitudDefensorDAO
				.consultarInvolucradosSolicitudDefensor(idSolicitudDefensor);

		if (listaInvolucrados != null && !listaInvolucrados.isEmpty()) {
			
			listaInvolucradosDTO = new ArrayList<InvolucradoDTO>();
			
			for (Involucrado involucrado : listaInvolucrados) {
				listaInvolucradosDTO.add(InvolucradoTransformer
						.transformarInvolucradoBasico(involucrado));
			}
		}
		return listaInvolucradosDTO;
	}
}
