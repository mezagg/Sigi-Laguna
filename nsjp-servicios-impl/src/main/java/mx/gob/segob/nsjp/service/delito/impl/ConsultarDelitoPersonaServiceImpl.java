/**
* Nombre del Programa : ConsultarDelitoPersonaServiceImpl.java
* Autor                            : adrian
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 08/07/2011
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
package mx.gob.segob.nsjp.service.delito.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.relacion.Relaciones;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.organizacion.OrganizacionDAO;
import mx.gob.segob.nsjp.dao.persona.DelitoPersonaDAO;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoPersonaDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoViewDTO;
import mx.gob.segob.nsjp.dto.organizacion.OrganizacionDTO;
import mx.gob.segob.nsjp.model.DelitoPersona;
import mx.gob.segob.nsjp.model.Expediente;
import mx.gob.segob.nsjp.model.Organizacion;
import mx.gob.segob.nsjp.service.delito.ConsultarDelitoPersonaService;
import mx.gob.segob.nsjp.service.delito.impl.transform.DelitoPersonaTransfromer;
import mx.gob.segob.nsjp.service.involucrado.ConsultarInvolucradoAudienciaService;
import mx.gob.segob.nsjp.service.organizacion.IngresarOrganizacionService;
import mx.gob.segob.nsjp.service.organizacion.impl.transform.OrganizacionTransformer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author adrian
 *
 */
@Service
@Transactional
public class ConsultarDelitoPersonaServiceImpl implements
		ConsultarDelitoPersonaService {
	public final static Logger logger = 
		Logger.getLogger(ConsultarDelitoPersonaServiceImpl.class);
	
	@Autowired
	private DelitoPersonaDAO delPerDao;
	
	@Autowired
	private ConsultarInvolucradoAudienciaService consultarInvolucradoAudienciaService;
	
	@Autowired
	private OrganizacionDAO organizacionDAO;

	@Autowired
	private IngresarOrganizacionService ingresarOrganizacionService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.gob.segob.nsjp.service.delito.ConsultarDelitoPersonaService#
	 * consultarDelitosVictimaPorImputado(java.lang.Long, java.lang.Long)
	 */
	@Override
	public List<DelitoPersonaDTO> consultarDelitosVictimaPorImputado(
			Long idInvolucrado, Long idExpediente) throws NSJPNegocioException {

		if (logger.isDebugEnabled())
			logger.debug("/**** SERVICIO PARA CONSULTAR DELITO PERSONA POR EXPEDIENTE Y PROBABLE RESPONSABLE ****/");

		/* Verificación de parámetros */
		if (idInvolucrado == null || idExpediente == null) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		List<DelitoPersona> delitos = delPerDao
				.consultarDelitosPorImputadoResponsableConVictima(idInvolucrado,
						idExpediente);

		List<DelitoPersonaDTO> delitosDTOS = new ArrayList<DelitoPersonaDTO>();

		for (DelitoPersona dPer : delitos) {
			DelitoPersonaDTO del = DelitoPersonaTransfromer
					.transformarDelitoPersonaDTO(dPer);
			if (del.getVictima() != null
					&& del.getVictima().getTipoPersona() != null
					&& del.getVictima().getTipoPersona().equals(0L)) {
				Organizacion organizacion = organizacionDAO
						.obtenerOrganizacionByRelacion(del.getVictima()
								.getElementoId(), new Long(
								Relaciones.ORGANIZACION_INVOLUCRADA.ordinal()));
				del.getVictima().setOrganizacionDTO(
						OrganizacionTransformer
								.transformarOrganizacionBasico(organizacion));
			}
			if (del.getProbableResponsable() != null
					&& del.getProbableResponsable().getTipoPersona() != null
					&& del.getProbableResponsable().getTipoPersona().equals(0L)) {
				Organizacion organizacion = organizacionDAO
						.obtenerOrganizacionByRelacion(
								del.getProbableResponsable().getElementoId(),
								new Long(Relaciones.ORGANIZACION_INVOLUCRADA
										.ordinal()));
				del.getProbableResponsable().setOrganizacionDTO(
						OrganizacionTransformer
								.transformarOrganizacionBasico(organizacion));
			}

			delitosDTOS.add(del);
		}
		return delitosDTOS;
	}

	@Override
	public List<DelitoPersonaDTO> consultarDelitosVictimaImputadoPorExpediente(
			ExpedienteDTO expedienteCompleto) throws NSJPNegocioException {

		// El expediente debe de tene la incormacion de los involucrados
		if (expedienteCompleto == null
				|| expedienteCompleto.getExpedienteId() == null
				|| expedienteCompleto.getExpedienteId() <= 0L
				|| expedienteCompleto.getInvolucradosDTO() == null
				|| expedienteCompleto.getInvolucradosDTO().isEmpty()) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		List<DelitoPersonaDTO> relacionesDelitoPersona = new ArrayList<DelitoPersonaDTO>();

		// Relaciones Delito Persona por id de probable responsable
		List<InvolucradoDTO> probablesResponsables = expedienteCompleto
				.getInvolucradoByCalidad(Calidades.PROBABLE_RESPONSABLE_PERSONA);

		List<InvolucradoDTO> probablesResponsablesOrganizacion = expedienteCompleto
				.getInvolucradoByCalidad(Calidades.PROBABLE_RESPONSABLE_ORGANIZACION);

		if (probablesResponsablesOrganizacion != null
				&& !probablesResponsablesOrganizacion.isEmpty()) {
			probablesResponsables.addAll(probablesResponsablesOrganizacion);
		}

		if (!probablesResponsables.isEmpty()) {
			for (InvolucradoDTO involucrado : probablesResponsables) {
				List<DelitoPersonaDTO> relacionesDelitoPersonaTemp = this
						.consultarDelitosVictimaPorImputado(
								involucrado.getElementoId(),
								expedienteCompleto.getExpedienteId());

				if (relacionesDelitoPersonaTemp != null
						&& !relacionesDelitoPersonaTemp.isEmpty()) {

					relacionesDelitoPersona.addAll(relacionesDelitoPersonaTemp);
				}
			}
		}
		return relacionesDelitoPersona;
	}
	
	@Override
	public List<DelitoPersonaDTO> consultarVictimaImputadoPorDelito(
			Long idDelito, Long idExpediente) throws NSJPNegocioException {
		if (logger.isDebugEnabled())
			logger.debug("/**** SERVICIO PARA CONSULTAR DELITO PERSONA POR EXPEDIENTE Y DELITO ****/");
		
		/*Verificación de parámetros*/
		if(idExpediente==null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		
		List<DelitoPersona> delitos = delPerDao.consultarVictimaImputadoPorDelito(idDelito, idExpediente);
		List<DelitoPersonaDTO> delitosDTOS = new ArrayList<DelitoPersonaDTO>();
		for (DelitoPersona dPer : delitos) {
			DelitoPersonaDTO del = DelitoPersonaTransfromer.transformarDelitoPersonaDTO(dPer);
			validarInvolucradosOrganizaciones(del);
			delitosDTOS.add(del);
		}
		return delitosDTOS;
	}
	
	@Override
	public Boolean existeRelacionPersonaDelitoExpediente(
			Long idDelito, Long idExpediente) throws NSJPNegocioException {
		if (logger.isDebugEnabled())
			logger.debug("/**** SERVICIO PARA VALIDAR SI EXISTE UNA RELACION DEL DELITO CON UNA PERSONA (DELITOPERSONA) ****/");
		
		/*Verificación de parámetros*/
		if(idExpediente==null){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);			
		}
		
		List<DelitoPersona> delitos = delPerDao.consultarVictimaImputadoPorDelito(idDelito, idExpediente);
		return delitos.isEmpty()?false:true; //Si esta vacia no existe relacion, si no esta vacia existe relacion 
	}
	
	@Override
	public List<DelitoPersonaDTO> consultarDelitosPersonaPorExpedienteIdsDelito(
			List<Long> idsDelitos, Long idExpediente) throws NSJPNegocioException {
		if (logger.isDebugEnabled())
			logger.debug("/**** SERVICIO PARA CONSULTAR DELITO PERSONA POR EXPEDIENTE Y LISTA DE IDS DE DELITOS ****/");
		
		/*Verificación de parámetros*/
		if(idExpediente==null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		
		List<DelitoPersona> delitos = delPerDao.consultarDelitosPersonaPorExpedienteIdsDelito(idsDelitos, idExpediente);
		List<DelitoPersonaDTO> delitosDTOS = new ArrayList<DelitoPersonaDTO>();
		for (DelitoPersona dPer : delitos) {
			delitosDTOS.add(DelitoPersonaTransfromer.transformarDelitoPersonaDTO(dPer));
		}
		return delitosDTOS;
	}
	
	/*
	 * (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.delito.ConsultarDelitoPersonaService#consultarDelitosVictimaPorImputado(java.lang.Long)
	 */
	@Override
	public List<DelitoPersonaDTO> consultarDelitosVictimaPorImputado(
			Long idInvolucrado) {
		List<DelitoPersona> delitosPer = delPerDao.consultarDelitosPorImputadoResponsable(idInvolucrado);
		List<DelitoPersonaDTO> resultado = new ArrayList<DelitoPersonaDTO>();
		for(DelitoPersona delito:delitosPer){
			resultado.add(DelitoPersonaTransfromer.transformarDelitoPersonaDTO(delito));
		}
		
		return resultado;
	}

	/**
	 * Servicio que permite consultar la relacion de probables responsables
	 * @author ricardog
	 */
	@Override
	public List<DelitoPersonaDTO> consultarVictimaImputadoPorExpediente(
			Long idExpediente) throws NSJPNegocioException {
		if (logger.isDebugEnabled())
			logger.debug("/**** SERVICIO PARA CONSULTAR DELITOS PERSONA POR EXPEDIENTE ****/");
		
		/*Verificación de parámetros*/
		if(idExpediente==null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		
		Expediente expInput = new Expediente();
		expInput.setExpedienteId(idExpediente);
		
		List<DelitoPersonaDTO> delitosDTOS = new ArrayList<DelitoPersonaDTO>();
		List<DelitoPersona> delitosPersona = delPerDao.consultarRelacionesDelitoPersonaDelExpediente( expInput);
		for (DelitoPersona dPer : delitosPersona) {
			DelitoPersonaDTO del = DelitoPersonaTransfromer.transformarDelitoPersonaDTO(dPer);
			if(del.getVictima().getTipoPersona().equals(0L)){
				Organizacion organizacion = organizacionDAO.obtenerOrganizacionByRelacion(
						del.getVictima().getElementoId(),new Long(Relaciones.ORGANIZACION_INVOLUCRADA.ordinal()));
				del.getVictima().setOrganizacionDTO(OrganizacionTransformer.transformarOrganizacionBasico(organizacion));
			}
			if(del.getProbableResponsable().getTipoPersona().equals(0L)){
				Organizacion organizacion = organizacionDAO.obtenerOrganizacionByRelacion(
						del.getProbableResponsable().getElementoId(),new Long(Relaciones.ORGANIZACION_INVOLUCRADA.ordinal()));
				del.getProbableResponsable().setOrganizacionDTO(OrganizacionTransformer.transformarOrganizacionBasico(organizacion));
			}
			
			delitosDTOS.add(del);
		}
		
		return delitosDTOS;
	}
	
	@Override
	public void desactivarDelitoPersona(Long delitoPersonaId)throws NSJPNegocioException {
		delPerDao.desactivarDelitoPersona(delitoPersonaId);
	}
	
	@Override
	public Boolean eliminarDelitoPersona(Long delitoPersonaId)throws NSJPNegocioException {
		if(delitoPersonaId==null || delitoPersonaId < 0){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		DelitoPersona delitoPersona = delPerDao.read(delitoPersonaId);
		if(delitoPersona==null){
			throw new NSJPNegocioException(CodigoError.INFORMACION_PARAMETROS_ERRONEA);
		}
		delPerDao.delete(delitoPersona);
		return (true);
	}
	
	@Override
	public DelitoPersonaDTO consultarDelitoPersonaPorId(Long idDelitoPersona) throws NSJPNegocioException{
		if (logger.isDebugEnabled())
			logger.debug("/**** SERVICIO PARA CONSULTAR DELITO PERSONA POR IDENTIFICADOR ****/");
		
		/*Verificación de parámetros*/
		if(idDelitoPersona==null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		
		DelitoPersona loDelitoPersona = delPerDao.read(idDelitoPersona);
		return DelitoPersonaTransfromer.transformarDelitoPersonaDTO(loDelitoPersona);
		
	}

	@Override
	public List<DelitoPersonaDTO> consultarRelacionesDelitoPersonaPorAudiencia(
			AudienciaDTO audienciaDto) throws NSJPNegocioException {
		
		if (audienciaDto == null || audienciaDto.getId() == null
				|| audienciaDto.getId() <= 0L
				|| audienciaDto.getExpediente() == null
				|| audienciaDto.getExpediente().getExpedienteId() == null
				|| audienciaDto.getExpediente().getExpedienteId() <= 0L) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		List<Long> calidades = new ArrayList<Long>();
		calidades.add(Calidades.PROBABLE_RESPONSABLE_PERSONA.getValorId());		
		calidades.add(Calidades.PROBABLE_RESPONSABLE_ORGANIZACION.getValorId());
		
		List<InvolucradoViewDTO> involucrados = consultarInvolucradoAudienciaService
				.obtenerInvolucradosAudienciaPorCalidades(audienciaDto,
						calidades);
		
		List<Long> imputadosId = new ArrayList<Long>();
		for(InvolucradoViewDTO invo:involucrados){
			imputadosId.add(invo.getInvolucradoId());
		}

		List<DelitoPersona> relDelitoPersonaInvolucradosAudiencia= delPerDao.consultarRelacionesDelitoPersonaPorIdsImputados(imputadosId, audienciaDto.getExpediente().getExpedienteId());
		
		List<DelitoPersonaDTO> relDelitoPersonaDTOInvolucradosAudiencia = new ArrayList<DelitoPersonaDTO>();
		
		for(DelitoPersona relDelitoPer :relDelitoPersonaInvolucradosAudiencia){
			relDelitoPersonaDTOInvolucradosAudiencia.add(DelitoPersonaTransfromer.transformarDelitoPersonaDTO((relDelitoPer)));
			
		}
		
		return relDelitoPersonaDTOInvolucradosAudiencia;
	}
	
	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.delito.ConsultarDelitoPersonaService#consultarDelitosPersonaPorIds(java.util.List)
	 */
	@Override
	public List<DelitoPersonaDTO> consultarDelitosPersonaPorIds(
			List<Long> idsDelitoPersona) throws NSJPNegocioException {
		List<DelitoPersonaDTO> delitosPersonaDTO = new ArrayList<DelitoPersonaDTO>();
		List<DelitoPersona> delitosPersona = delPerDao.consultarDelitosPersonaPorIds(idsDelitoPersona);
		for (DelitoPersona dp : delitosPersona){
			delitosPersonaDTO.add(DelitoPersonaTransfromer.transformarDelitoPersonaDTO(dp));
		}
		return delitosPersonaDTO;
	}
	

	@Override
	public List<DelitoPersonaDTO> consultarRelacionesDelitoPersonaDelExpediente(
			ExpedienteDTO expedienteDto) throws NSJPNegocioException {

		if (expedienteDto == null || expedienteDto.getExpedienteId() == null
				|| expedienteDto.getExpedienteId() <= 0L) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		Expediente expInput = new Expediente();
		expInput.setExpedienteId(expedienteDto.getExpedienteId());

		List<DelitoPersona> listaRelDelPer = delPerDao
				.consultarRelacionesDelitoPersonaDelExpediente(expInput);

		List<DelitoPersonaDTO> listaRelaciones = null;

		if (listaRelDelPer != null && !listaRelDelPer.isEmpty()) {

			listaRelaciones = new ArrayList<DelitoPersonaDTO>();
			DelitoPersonaDTO delitoPersonaDTO = null;
			for (DelitoPersona dPer : listaRelDelPer) {
				delitoPersonaDTO = DelitoPersonaTransfromer
						.transformarDelitoPersonaDTO(dPer);
				validarInvolucradosOrganizaciones(delitoPersonaDTO);
				listaRelaciones.add(delitoPersonaDTO);
			}
		}

		return listaRelaciones;
	}

	private void validarInvolucradosOrganizaciones(
			DelitoPersonaDTO delitoPersonaDTO) {
		if(delitoPersonaDTO.getVictima().getTipoPersona().equals(0L)){
			try {
				OrganizacionDTO organizacionDTO = 
						ingresarOrganizacionService.consultarOrganizacionPorInvolucrado(delitoPersonaDTO.getVictima().getElementoId());
				if(organizacionDTO != null){
					delitoPersonaDTO.getVictima().setOrganizacionDTO(organizacionDTO);
					if(organizacionDTO.getRepresentanteLegal() != null && organizacionDTO.getRepresentanteLegal().getNombresDemograficoDTO() != null){
						delitoPersonaDTO.getVictima().setNombresDemograficoDTO(organizacionDTO.getRepresentanteLegal().getNombresDemograficoDTO());
					}
				}
			} catch (NSJPNegocioException e) {
				e.printStackTrace();
			}
		}
		if(delitoPersonaDTO.getProbableResponsable().getTipoPersona().equals(0L)){
			try {
				OrganizacionDTO organizacionDTO = 
						ingresarOrganizacionService.consultarOrganizacionPorInvolucrado(delitoPersonaDTO.getProbableResponsable().getElementoId());
				if(organizacionDTO != null){
					delitoPersonaDTO.getProbableResponsable().setOrganizacionDTO(organizacionDTO);
					if(organizacionDTO.getRepresentanteLegal() != null && organizacionDTO.getRepresentanteLegal().getNombresDemograficoDTO() != null){
						delitoPersonaDTO.getProbableResponsable().setNombresDemograficoDTO(organizacionDTO.getRepresentanteLegal().getNombresDemograficoDTO());
					}
				}
			} catch (NSJPNegocioException e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public DelitoPersonaDTO consultarUnicaRelacionDelitoPersonaPorFolio(
			DelitoPersonaDTO delitoPersonaDTO) throws NSJPNegocioException {

		if (delitoPersonaDTO == null
				|| delitoPersonaDTO.getFolioDelitoPersona() == null
				|| delitoPersonaDTO.getFolioDelitoPersona().trim().isEmpty()) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		DelitoPersona delitoPersona = new DelitoPersona();
		delitoPersona.setFolioDelitoPersona(delitoPersonaDTO
				.getFolioDelitoPersona());

		delitoPersona = delPerDao
				.consultarRelacionDelitoPersonaPorFolio(delitoPersona);

		delitoPersonaDTO = DelitoPersonaTransfromer
				.transformarDelitoPersonaDTO(delitoPersona);
		validarInvolucradosOrganizaciones(delitoPersonaDTO);

		return delitoPersonaDTO;
	}
}
