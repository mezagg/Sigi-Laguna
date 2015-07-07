/**
 * 
 */
package mx.gob.segob.nsjp.service.elemento.impl;
import java.util.ArrayList;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.enums.elemento.TipoElemento;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.relacion.Relaciones;
import mx.gob.segob.nsjp.comun.enums.relacion.TipoRelacion;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.archivo.ArchivoDigitalDAO;
import mx.gob.segob.nsjp.dao.elemento.ElementoArchivoDigitalDAO;
import mx.gob.segob.nsjp.dao.elemento.ElementoDAO;
import mx.gob.segob.nsjp.dao.evidencia.EvidenciaDAO;
import mx.gob.segob.nsjp.dao.involucrado.InvolucradoDAO;
import mx.gob.segob.nsjp.dao.persona.DelitoPersonaDAO;
import mx.gob.segob.nsjp.dao.relacion.RelacionDAO;
import mx.gob.segob.nsjp.model.ArchivoDigital;
import mx.gob.segob.nsjp.model.DelitoPersona;
import mx.gob.segob.nsjp.model.Elemento;
import mx.gob.segob.nsjp.model.ElementoArchivoDigital;
import mx.gob.segob.nsjp.model.ElementoArchivoDigitalId;
import mx.gob.segob.nsjp.model.Evidencia;
import mx.gob.segob.nsjp.model.Involucrado;
import mx.gob.segob.nsjp.model.Relacion;
import mx.gob.segob.nsjp.service.elemento.AnularElementoService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * @author MelitonBC
 *
 */
@Service
@Transactional
public class AnularElementoServiceImpl implements AnularElementoService{
	private static final Logger logger = Logger
    .getLogger(AdjuntarArchivoAElementoServiceImpl.class);
	@Autowired
	private ElementoDAO elementoDao;
	@Autowired
	private EvidenciaDAO evidenciaDAO;
	@Autowired
	private RelacionDAO relacionDAO;
	@Autowired
	private DelitoPersonaDAO delitoPersonaDAO;
	@Autowired
	ElementoArchivoDigitalDAO elementoArchivoDigitalDAO;
	@Autowired
	ArchivoDigitalDAO archivoDigitalDAO;
	@Autowired
	InvolucradoDAO involucradoDAO;

	

	@Override
	public Boolean anularElemento(Long idElemento) throws NSJPNegocioException {
		logger.debug("SERVICIO QUE ELIMINA UN ELEMENTO POR SU ID:::::::::: " + idElemento);
		if(idElemento==null || idElemento < 0 ){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		final Elemento elePojo=this.elementoDao.read(idElemento);
		Elemento eleOrganizacion = null;
		Long idOrganizacion = null;
		if(!elePojo.getCalidad().getTipoCalidad().getValorId().equals(Calidades.EVIDENCIA.getValorId()))
			idOrganizacion = consultarInvolucradoMoral(idElemento);
		
		if (elePojo.getCalidad().getTipoCalidad().getValorId()
				.equals(Calidades.EVIDENCIA.getValorId())) {
			// Eliminar la evidencia asociada al Elemento
			final Evidencia evidencia = evidenciaDAO
					.consultarEvidenciaXObjetoId(idElemento);
			//Eliminar la relacion del objeto
			List<Relacion> relacionesObjeto = relacionDAO.consultarRelacionByElemento(idElemento);
			for (Relacion relacionObjeto : relacionesObjeto){
				if (!relacionesObjeto.isEmpty()){
					relacionObjeto.setEsActivo(Boolean.FALSE);
					relacionDAO.update(relacionObjeto);
				}
			}
			
			if (evidencia != null) {
				evidenciaDAO.delete(evidencia);
			}
		}
		else {
	
			Calidades calidadInvolucrado = Calidades.getByValor(elePojo
					.getCalidad().getTipoCalidad().getValorId());
			switch (calidadInvolucrado) {
				case PROBABLE_RESPONSABLE_PERSONA:
				case PROBABLE_RESPONSABLE_ORGANIZACION:
				case DENUNCIANTE:
				case DENUNCIANTE_ORGANIZACION:
				case VICTIMA_PERSONA:
				case QUIEN_DETUVO:
				case DEFENSOR_PRIVADO:
				case DEFENSOR_PUBLICO:
				case REPRESENTANTE_LEGAL:
				case TESTIGO:
				case TRADUCTOR:
				case TUTOR: {
					
					eleOrganizacion = this.elementoDao.read(idOrganizacion);
					// Consultar las relaciones
					List<Relacion> relacionesInvolucrado = relacionDAO.consultarRelacionByElemento(idElemento);
					if(idElemento != idOrganizacion)
						relacionesInvolucrado.addAll(relacionDAO.consultarRelacionByElemento(idOrganizacion));
					//Al anular involucrado busca sus relaciones con lugar para poder eliminarlas
					for (Relacion relacionInvolucrado : relacionesInvolucrado){
						if (relacionInvolucrado.getElementoByComplementoId().getCalidad().getTipoCalidad().getValorId().equals(Calidades.DOMICILIO.getValorId())){
							List<Relacion> relacionesLugar = relacionDAO.consultarRelacionByElemento(relacionInvolucrado.getElementoByComplementoId().getElementoId().longValue());
							for (Relacion relacionLugar : relacionesLugar){
								if (!relacionesLugar.isEmpty())
									relacionLugar.setEsActivo(Boolean.FALSE);
									relacionDAO.update(relacionLugar);
							}
							
							//Pone el Domicilio del Involucrado en EsActivo = 0;
							// ya que este ELemento DOmicilio sigue activo
							relacionInvolucrado.getElementoByComplementoId().setEsActivo(Boolean.FALSE);
							elementoDao.update(relacionInvolucrado.getElementoByComplementoId());
						}
						
						// Eliminar la relación independientemente del tipo de relación
						if (!relacionesInvolucrado.isEmpty()){
							relacionInvolucrado.setEsActivo(Boolean.FALSE);
							relacionDAO.update(relacionInvolucrado);
						}
					}
					break;
				}
			}
			
			// Buscar relaciones con delito
			List<DelitoPersona> delitosPersona = delitoPersonaDAO
					.consultarDelitoPerByInvolucrado(idElemento);
			if (!delitosPersona.isEmpty()) {
				delitoPersonaDAO.deleteAll(delitosPersona);
			}
		}
		
		if(idElemento != idOrganizacion && idOrganizacion != null){
			eleOrganizacion.setEsActivo(Boolean.FALSE);
			this.elementoDao.update(eleOrganizacion);
		}
		elePojo.setEsActivo(Boolean.FALSE);
		this.elementoDao.update(elePojo);
		
		
		List<ArchivoDigital> archivosDigMedida = elementoArchivoDigitalDAO.consultarArchivosDigitalesXElementoId(idElemento);
		
		if(archivosDigMedida != null && archivosDigMedida.size()> 0){
			
			//Eliminar registros de la tabla de cruce ElementoArchivoDigital
			for (ArchivoDigital archivoDigitalBD : archivosDigMedida) {
				ElementoArchivoDigitalId loElementoArchivoDigitalId = new ElementoArchivoDigitalId();
		        loElementoArchivoDigitalId.setArchivoDigitalId(archivoDigitalBD.getArchivoDigitalId());
		        loElementoArchivoDigitalId.setElementoId(idElemento);		        
		        ElementoArchivoDigital loElementoArchivoDigital = new ElementoArchivoDigital();
		        loElementoArchivoDigital.setId(loElementoArchivoDigitalId);
		        elementoArchivoDigitalDAO.delete(loElementoArchivoDigital);
			}
			//Se elimina la lista de archivos digitales
			archivoDigitalDAO.deleteAll(archivosDigMedida);
		}
		
		return true;
	}
	
	@Override
	public List<String> consultarRelacionesElemento(Long idElemento) throws NSJPNegocioException {
		logger.debug("SERVICIO QUE CONSULTA LAS RELACIONES DEL ELEMENTO DE ACUERDO A SU CALDAD:::::::::: " + idElemento);
		if(idElemento==null || idElemento < 0 ){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		/**
		 * C&oacute;digo para buscar las relaciones de una organización
		 * * */		
		Long idRelacion = 0L;
		final List<String> relacionesExistentes = new ArrayList<String>();
		final Relaciones relacionesEnum[] = Relaciones.values();
		final Elemento elePojo=this.elementoDao.read(idElemento);

		if (elePojo.getCalidad().getTipoCalidad().getValorId()
				.equals(Calidades.EVIDENCIA.getValorId())) {
			// Eliminar la evidencia asociada al Elemento
			final Evidencia evidencia = evidenciaDAO
					.consultarEvidenciaXObjetoId(idElemento);
			if (evidencia != null) {
				relacionesExistentes.add(" Evidencia");
			}
			return relacionesExistentes;
		}
		else{

			Calidades calidadInvolucrado = Calidades.getByValor(elePojo
					.getCalidad().getTipoCalidad().getValorId());
			switch (calidadInvolucrado) {
				case PROBABLE_RESPONSABLE_PERSONA:
				case PROBABLE_RESPONSABLE_ORGANIZACION:
				case DENUNCIANTE:
				case DENUNCIANTE_ORGANIZACION:
				case VICTIMA_PERSONA:
				case QUIEN_DETUVO:
				case DEFENSOR_PRIVADO:
				case DEFENSOR_PUBLICO:
				case REPRESENTANTE_LEGAL:
				case TESTIGO:
				case TRADUCTOR:
				case TUTOR: {
					// Consultar las relaciones
					Long idOrganizacion = consultarInvolucradoMoral(idElemento);
					List<Relacion> relacionesJuntasInvolucrado = relacionDAO
							.consultarRelacionByElemento(idElemento);
					
					List<Relacion> relacionesInvolucrado = new ArrayList<Relacion>();
					
					relacionesInvolucrado.addAll(relacionesJuntasInvolucrado);
					relacionesJuntasInvolucrado.clear();
					if (idOrganizacion != idElemento){
						relacionesJuntasInvolucrado = relacionDAO.consultarRelacionByElemento(idOrganizacion);
						relacionesInvolucrado.addAll(relacionesJuntasInvolucrado);
					}
	
					for (Relacion relacion : relacionesInvolucrado) {
						if (relacion != null
								&& relacion.getCatRelacion() != null
								&& relacion.getCatRelacion().getCatRelacionId() != null 
								&& relacion.getTipoRelacion().longValue() == TipoRelacion.EXPLICITA.getValorId()) {
							
									String calidadRelacion = null;
									
									if (idElemento == idOrganizacion){
										idRelacion = idElemento;
									}else{
										idRelacion = idOrganizacion;
									}
									
									if( relacion.getElementoByComplementoId().getElementoId().equals(idRelacion)){
										if(relacion.getElementoBySujetoId().getTipoElemento().getValorId().equals(TipoElemento.ORGANIZACION.getValorId())
												&& relacion.getElementoBySujetoId().getCalidad().getTipoCalidad().getValorId().equals(Calidades.DENUNCIANTE_ORGANIZACION.getValorId())){
											calidadRelacion = "Denunciante";	
										}else{
											calidadRelacion =  relacion.getElementoBySujetoId().getCalidad().getTipoCalidad().getValor();
										}
									}
									else{
										if(relacion.getElementoByComplementoId().getTipoElemento().getValorId().equals(TipoElemento.ORGANIZACION.getValorId())
												&& relacion.getElementoByComplementoId().getCalidad().getTipoCalidad().getValorId().equals(Calidades.DENUNCIANTE_ORGANIZACION.getValorId())){
											calidadRelacion = "Denunciante";	
										}else{
											calidadRelacion =  relacion.getElementoByComplementoId().getCalidad().getTipoCalidad().getValor();
										}
									}
									//logger.info("**Relación:"+ calidadRelacion);
									relacionesExistentes.add(calidadRelacion);
						}		
						
					}
					break;
				}
			}
		
			//Buscar relaciones con delito
			List<DelitoPersona> delitosPersona = delitoPersonaDAO.consultarDelitoPerByInvolucrado(idElemento);
			if(!delitosPersona.isEmpty()){
				relacionesExistentes.add(delitosPersona.size() +  " delito(s)" );
			}
		}
		
		return relacionesExistentes;
	}

	private Long consultarInvolucradoMoral(Long idElemento) {
		Long idOrganizacion = idElemento;
		
		List<Relacion> relaciones  = new ArrayList<Relacion>();
		List<Long> idElementos = new ArrayList<Long>();
		idElementos.add(idElemento);
		
		Involucrado involucrado = involucradoDAO.read(idElemento);
		if(involucrado.getTipoPersona().equals("Moral")){
			relaciones = relacionDAO.consultarRelacionByIdElementoAndTipoRelacion(idElementos, TipoRelacion.IMPLICITA.getValorId(), null);
			
			for (Relacion relacion : relaciones) {
				if(relacion.getElementoByComplementoId().getEsActivo() && relacion.getElementoBySujetoId().getEsActivo() ){
					if (relacion.getElementoByComplementoId().getElementoId() == idElemento){
						idOrganizacion = relacion.getElementoBySujetoId().getElementoId();
					}else if (relacion.getElementoBySujetoId().getElementoId() == idElemento){
						idOrganizacion = relacion.getElementoByComplementoId().getElementoId();
					}
				}
			}
		}
		
		return idOrganizacion;
	}
}
