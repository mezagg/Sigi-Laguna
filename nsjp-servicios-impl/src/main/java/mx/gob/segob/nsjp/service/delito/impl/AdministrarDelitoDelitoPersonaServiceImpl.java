/**
 * Nombre del Programa : EliminarDelitoServiceImpl.java
 * Autor                            : GustavoBP
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 16/01/2013
 * Marca de cambio        : N/A
 * Descripcion General    : Implementacion de servicio para elminar delitos y dependencia con DelitoPersona.
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
package mx.gob.segob.nsjp.service.delito.impl;

import java.util.HashMap;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.delito.DelitoDAO;
import mx.gob.segob.nsjp.dao.persona.DelitoPersonaDAO;
import mx.gob.segob.nsjp.dto.expediente.DelitoDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoPersonaDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.model.Delito;
import mx.gob.segob.nsjp.model.DelitoPersona;
import mx.gob.segob.nsjp.service.delito.AdministrarDelitoDelitoPersonaService;
import mx.gob.segob.nsjp.service.delito.GuardarDelitoService;
import mx.gob.segob.nsjp.service.delito.impl.transform.DelitoPersonaTransfromer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementacion de servicio para elminar delitos y dependencia con
 * DelitoPersona.
 * 
 * @author GustavoBP
 */
@Service
@Transactional
public class AdministrarDelitoDelitoPersonaServiceImpl implements
		AdministrarDelitoDelitoPersonaService {

	private final static Logger logger = Logger
			.getLogger(AdministrarDelitoDelitoPersonaServiceImpl.class);

	@Autowired
	private DelitoDAO delitoDao;
	@Autowired
	private DelitoPersonaDAO delitoPerDao;

	@Autowired
	private GuardarDelitoService guardarDelitoService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.gob.segob.nsjp.service.delito.EliminarDelitoService#
	 * eliminarDelitosExpediente(java.lang.Long)
	 */
	/**
	 * Servicion transaccional para la eliminacion de los delitos del expediente
	 * Al finalizar la ejecucuion del codigo, si no hay un problema, se hace el
	 * comit de esta transaccion.
	 * 
	 * @param expedienteId
	 * @throws NSJPNegocioException
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void eliminarDelitosExpediente(Long expedienteId)
			throws NSJPNegocioException {
		if (expedienteId == null || expedienteId <= 0) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		List<Delito> delitos = delitoDao
				.obtenerDelitoPorExpediente(expedienteId);
		if (delitos != null && !delitos.isEmpty()) {
			logger.info("Delitos a Eliminar: " + delitos.size());
			Object[] delitosArray = delitos.toArray();
			for (int contD = 0; contD < delitosArray.length; contD++) {
				Delito delitoBD = (Delito) delitosArray[contD];
				logger.info("Delito a Eliminar: " + delitoBD.getDelitoId());
				delitos.remove(delitoBD);
				delitosArray[contD] = null;
				delitoDao.delete(delitoBD);
				delitoBD = null;
			}
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void registrarDelitosRelacionDelitoExpediente(
			ExpedienteDTO expedienteDTO,
			List<DelitoPersonaDTO> relacionesDelitoPersonaDTO,
			HashMap<String, Long> folioIdInvolucrados)
			throws NSJPNegocioException {

		// DELITOS Y RELACION DELITO-IMPUTADO-VICTIMA
		// Eliminar delitos del expediente Y la DelitoPersona en cascada
		// Haciendo uso de otra transaccion
		this.eliminarDelitosExpediente(expedienteDTO.getExpedienteId());

		HashMap<String, Long> delitosAsociados = null;

		// Los delitos del expediente tienen que se registrados inicialmente.
		if (expedienteDTO.getDelitos() != null
				&& !expedienteDTO.getDelitos().isEmpty()) {
			// Crea la asociaci&oacute;n delito con expediente, siempre y cuando
			// no exista y se tenga registrado el catagolo del delito (mediante
			// la claveinterinstitucional)
			delitosAsociados = guardarDelitoService
					.guardarDelitoExpedienteInterinstitucional(expedienteDTO
							.getDelitos(),
							new ExpedienteDTO(expedienteDTO.getExpedienteId()));
		}

		// Se guardan las relacion Delito-Imputado-Victima
		if (relacionesDelitoPersonaDTO != null
				&& !relacionesDelitoPersonaDTO.isEmpty()) {
			siguienteDelito: for (DelitoPersonaDTO delitoPersonaDTO : relacionesDelitoPersonaDTO) {
				// Buscar los datos de relacion Delito-Imputado-Victima

				if (delitoPersonaDTO.getProbableResponsable() != null
						&& delitoPersonaDTO.getProbableResponsable()
								.getFolioElemento() != null
						&& !delitoPersonaDTO.getProbableResponsable()
								.getFolioElemento().trim().isEmpty()
						&& delitoPersonaDTO.getVictima() != null
						&& delitoPersonaDTO.getVictima().getFolioElemento() != null
						&& !delitoPersonaDTO.getVictima().getFolioElemento()
								.trim().isEmpty()
						&& delitoPersonaDTO.getDelito() != null
						&& delitoPersonaDTO.getDelito().getCatDelitoDTO()
								.getClaveInterInstitucional() != null
						&& !delitoPersonaDTO.getDelito().getCatDelitoDTO()
								.getClaveInterInstitucional().isEmpty()
						&& delitoPersonaDTO.getFolioDelitoPersona() != null
						&& !delitoPersonaDTO.getFolioDelitoPersona().trim()
								.isEmpty()) {

					// Buscar el Imputado por Folio
					Long imputadoId = folioIdInvolucrados.get(delitoPersonaDTO
							.getProbableResponsable().getFolioElemento());

					// Buscar el victima por Folio
					Long victimaId = folioIdInvolucrados.get(delitoPersonaDTO
							.getVictima().getFolioElemento());

					// Buscar el delito asociado al expediente
					Long delitoId = delitosAsociados.get(delitoPersonaDTO
							.getDelito().getCatDelitoDTO()
							.getClaveInterInstitucional());

					if (imputadoId == null || victimaId == null
							|| delitoId == null) {
						logger.error("PARAMETROS INSUFICIENTES PARA CREAR LA RELACION. Imputado:"
								+ imputadoId
								+ " Victima:"
								+ victimaId
								+ " DelitoId:" + delitoId);
						// No se completara el registro
						continue siguienteDelito;
					}

					//Prob resp
					delitoPersonaDTO.setProbableResponsable(new InvolucradoDTO(
							imputadoId));
					//Victima
					delitoPersonaDTO.setVictima(new InvolucradoDTO(victimaId));
					//delitoId
					delitoPersonaDTO.setDelito(new DelitoDTO(delitoId));
					
					DelitoPersona delitoPersonaBD = DelitoPersonaTransfromer
							.transforma(delitoPersonaDTO);
					if (delitoPersonaBD != null) {
						Long idDelitoPersona = delitoPerDao
								.create(delitoPersonaBD);
						logger.info(" Relacion creada:" + idDelitoPersona);
					}

				}
			}
		}
	}

}
