/**
 * Nombre del Programa : AsociarDelitoResponsableVictimaServiceImpl.java
 * Autor                            : adrian
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 07/07/2011
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

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.delito.DelitoDAO;
import mx.gob.segob.nsjp.dao.persona.DelitoPersonaDAO;
import mx.gob.segob.nsjp.dto.expediente.DelitoDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoPersonaDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.model.Delito;
import mx.gob.segob.nsjp.model.DelitoPersona;
import mx.gob.segob.nsjp.model.Involucrado;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.delito.AsociarDelitoResponsableVictimaService;
import mx.gob.segob.nsjp.service.delito.GenerarFolioDelitoPersonaService;
import mx.gob.segob.nsjp.service.delito.impl.transform.DelitoTransfromer;
import mx.gob.segob.nsjp.service.forma.impl.ConsultarFormaPlantillaServiceImpl;
import mx.gob.segob.nsjp.service.usuario.impl.transformer.ValorTransformer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Describir el objetivo de la clase con punto al final.
 * 
 * @version 1.0
 * @author adrian
 * 
 */
@Service
@Transactional
public class AsociarDelitoResponsableVictimaServiceImpl implements
		AsociarDelitoResponsableVictimaService {

	public final static Logger logger = Logger
			.getLogger(ConsultarFormaPlantillaServiceImpl.class);

	@Autowired
	private DelitoPersonaDAO dpDao;
	@Autowired
	private DelitoDAO delitoDAO;
	@Autowired
	private GenerarFolioDelitoPersonaService generarFolioDelitoPersonaService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.gob.segob.nsjp.service.delito.AsociarDelitoResponsableVictimaService
	 * #asociarDelitoResponsableVictima
	 * (mx.gob.segob.nsjp.dto.expediente.DelitoDTO,
	 * mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO,
	 * mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO, java.lang.Long,
	 * java.lang.Long)
	 */
	@Override
	public Long asociarDelitoResponsableVictima(Long asociacionID,
			DelitoDTO delitoDTO, InvolucradoDTO responsableDTO,
			InvolucradoDTO victimaDTO, Long formaParticipacion,
			Long bienTutelado, Long idClasificacion, Long idLugar,
			Long idModalidad,Long idModus, Long idCausa) throws NSJPNegocioException {
		
		if (logger.isDebugEnabled())
			logger.debug("/**** SERVICIO PARA ASOCIAR (SAVEorUPDATE) UN DELITO CON SU PROBABLE RESPONSABLE Y, SI TIENE, CON LA VICTIMA Y CALIDAD DE TUTELADO ****/");

		/* Verificaci&oacute;n de par&aacute;metros */
		if (delitoDTO == null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		else 
		if (responsableDTO == null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		else if (responsableDTO.getElementoId() == null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);

		/* Operaci&oacute;n */
		
		Delito delito = null;
		
		if(delitoDTO != null && delitoDTO.getDelitoId() != null && delitoDTO.getDelitoId() > 0)
			delito = delitoDAO.read(delitoDTO.getDelitoId());
		else{
			//Se crea el Delito
			delito = DelitoTransfromer.transformarDelito(delitoDTO);
			Long idDelitoBD = delitoDAO.create(delito);
			delito.setDelitoId(idDelitoBD);
		}
		
		DelitoPersona delPer = new DelitoPersona();
		delPer.setDelitoPersonaId(asociacionID);
		delPer.setDelito(delito);
		delPer.setProbableResponsable(new Involucrado(responsableDTO
				.getElementoId()));
		if (victimaDTO != null)
			delPer.setVictima(new Involucrado(victimaDTO.getElementoId()));
		if(formaParticipacion != null)
			delPer.setFormaParticipacion(new Valor(formaParticipacion));
		if(bienTutelado!=null){
		delPer.setBienTutelado(new Valor(bienTutelado));}
		
		delPer.setCatDelitoClasificacionId(idClasificacion);
		delPer.setCatDelitoLugarId(idLugar);
		delPer.setCatDelitoModalidadId(idModalidad);
		delPer.setCatDelitoModusId(idModus);
		delPer.setCatDelitoCausaId(idCausa);
		
		//Inicialmente se registra inicialmente en 1
		delPer.setEsActivo(true);
		
		Long id;
		if( asociacionID == null
				|| asociacionID == 0L){
			id= dpDao.create(delPer);
		}
		else{
			id=asociacionID;
			dpDao.update(delPer);
		}
		
		return id;
	}
	
	@Override
	public Boolean existeRelacionProbableResponsableVictimaDelitoFormaParticipacion(Long idDelito, Long idProbableResponsable, Long idVictima, Long idFormaParticipacion)  throws NSJPNegocioException {
		
		Boolean existeRelacion = false;

		if (idDelito == null || idDelito < 0 || idProbableResponsable == null
				|| idProbableResponsable < 0 || idVictima == null
				|| idVictima < 0 ){
			
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		existeRelacion = dpDao.existeRelacionProbableResponsableVictimaDelitoFormaParticipacion(
				idDelito, idProbableResponsable, idVictima, idFormaParticipacion);
		
		return existeRelacion; 
	}

	
	@Override
	public void asociarDelitosConIvolucrados(List<Long> idProbsResps,
			List<Long> idVictimas, Long delitoId) throws NSJPNegocioException {

		
		if (idVictimas == null || idVictimas.isEmpty() || idProbsResps == null
				|| idProbsResps.isEmpty() || delitoId == null || delitoId <= 0L) {
			throw new NSJPNegocioException(
					CodigoError.INFORMACION_PARAMETROS_ERRONEA);
		}

		Delito delito = delitoDAO.read(delitoId);

		if (delito == null) {
			throw new NSJPNegocioException(
					CodigoError.INFORMACION_PARAMETROS_ERRONEA);
		}

		List<DelitoPersona> listaRelDelitoPersona = new ArrayList<DelitoPersona>();

		for (Long idProbableResp : idProbsResps) {
			for (Long idVictima : idVictimas) {

				if (!this
						.existeRelacionProbableResponsableVictimaDelitoFormaParticipacion(
								delitoId, idProbableResp, idVictima, null)) {

					DelitoPersona delPer = new DelitoPersona();

					delPer.setDelito(delito);
					delPer.setProbableResponsable(new Involucrado(
							idProbableResp));
					delPer.setVictima(new Involucrado(idVictima));
					delPer.setEsActivo(true);
					
					String folioDelitoPersona = generarFolioDelitoPersonaService.generarFolioDelitoPersona();
					
					//Validamos la generacion del folio de la relacion delito persona
					if(folioDelitoPersona == null){
						throw new NSJPNegocioException(CodigoError.IMPOSIBLE_GENERAR_FOLIO_RELACION_DELITO_PERSONA);
					}
					delPer.setFolioDelitoPersona(folioDelitoPersona);
					listaRelDelitoPersona.add(delPer);
				}
				//Si ya existe la asociacion NO se debe de repetir
			}
		}

		if(listaRelDelitoPersona != null && !listaRelDelitoPersona.isEmpty()){
			dpDao.createAll(listaRelDelitoPersona);			
		}
	}

	@Override
	public void establecerModosGradosYFormasDeParticipacion(
			List<Long> idsRelsDelPersona,
			DelitoPersonaDTO delitoPersonaDtoInput)
			throws NSJPNegocioException {

		if (logger.isDebugEnabled()) {
			logger.debug("/****BIENVENIDO AL SERVICIO PARA MODIFICAR LOS ATRIBUTOS DE UNA RELACION DELITO PERSONA****/");
		}

		/* Verificaci&oacute;n de par&aacute;metros */
		if (idsRelsDelPersona == null || idsRelsDelPersona.isEmpty()
				|| delitoPersonaDtoInput == null) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		List<DelitoPersona> listaRelacionesDelitoPer = dpDao
				.consultarListaRelacionesDelitoPersona(idsRelsDelPersona);

		if (listaRelacionesDelitoPer == null
				|| listaRelacionesDelitoPer.isEmpty()
				|| listaRelacionesDelitoPer.size() != idsRelsDelPersona.size()) {
			/*
			 * Los tamanios de las listas deben coincidir para asegurar que
			 * todos las relaciones delito persona siguen existiendo y no solo
			 * fueron seleccionadas en vista
			 */
			throw new NSJPNegocioException(
					CodigoError.INFORMACION_PARAMETROS_ERRONEA);
		}

		// Setamos los nuevos valores a la lista obtenida delitoPersonaDtoUpdate
		for (DelitoPersona relDelitoPersonaUpdate : listaRelacionesDelitoPer) {

			// Forma de participacion
			if (delitoPersonaDtoInput.getFormaParticipacion() != null) {
				relDelitoPersonaUpdate.setFormaParticipacion(ValorTransformer
						.transformar(delitoPersonaDtoInput
								.getFormaParticipacion()));
			}
			// Clasificacion
			relDelitoPersonaUpdate
					.setCatDelitoClasificacionId(delitoPersonaDtoInput
							.getCatDelitoClasificacionId());
			// Lugar
			relDelitoPersonaUpdate.setCatDelitoLugarId(delitoPersonaDtoInput
					.getCatDelitoLugarId());
			// Modalidad
			relDelitoPersonaUpdate
					.setCatDelitoModalidadId(delitoPersonaDtoInput
							.getCatDelitoModalidadId());
			// Modus
			relDelitoPersonaUpdate.setCatDelitoModusId(delitoPersonaDtoInput
					.getCatDelitoModusId());
			// id Causa
			relDelitoPersonaUpdate.setCatDelitoCausaId(delitoPersonaDtoInput
					.getCatDelitoCausaId());
			// Estadistica
			relDelitoPersonaUpdate.setCatDelitoEstadisticaId(delitoPersonaDtoInput
					.getCatDelitoEstadisticaId());
			// Tipo
			relDelitoPersonaUpdate.setCatDelitoTipoId(delitoPersonaDtoInput
					.getCatDelitoTipoId());
			// Calificacion
			relDelitoPersonaUpdate.setCatDelitoCalificacionId(delitoPersonaDtoInput
					.getCatDelitoCalificacionId());
			// Concurso
			relDelitoPersonaUpdate.setCatDelitoConcursoId(delitoPersonaDtoInput
					.getCatDelitoConcursoId());
			// Orden de Res
			relDelitoPersonaUpdate.setCatDelitoOrdenResId(delitoPersonaDtoInput
					.getCatDelitoOrdenResId());
		}

		//Actualizamos todos los objetos 
		dpDao.saveOrUpdateAll(listaRelacionesDelitoPer);
	}
}
