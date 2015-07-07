/**
*
* Nombre del Programa : DelitoDAOImplTest.java                                    
* Autor                            : Ricardo Gama                                               
* Compania                    : Ultrasist                                                
* Proyecto                      : NSJP                    Fecha: 30/03/2011 
* Marca de cambio        : N/A                                                     
* Descripcion General    : Prueba unitaria para el DAO de Delito                      
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

package mx.gob.segob.nsjp.dao.test.delito.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.dao.persona.DelitoPersonaDAO;
import mx.gob.segob.nsjp.dao.test.base.BaseTestPersistencia;
import mx.gob.segob.nsjp.model.CatDelito;
import mx.gob.segob.nsjp.model.Delito;
import mx.gob.segob.nsjp.model.DelitoPersona;
import mx.gob.segob.nsjp.model.Expediente;
import mx.gob.segob.nsjp.model.Involucrado;

/**
 * 
 * @author rgama
 * @version 1.0
 *
 */
public class DelitoPersonaDAOImplTest extends BaseTestPersistencia<DelitoPersonaDAO> {

	public void testConsultarDelitosPorImputado () {
		logger.info("Prueba unitaria para consultar Delitos por caso");						
		List<DelitoPersona> respuesta = daoServcice.consultarDelitosPorImputado(36L,1L);
		assertFalse("La lista debe tener registros de Delito ", respuesta.isEmpty());		
		logger.info("Delitos recuperados : " + respuesta.size());		
	}	
	
	public void testConsultarDelitoPerByInvolucrado () {
		logger.info("Prueba unitaria para consultar los delitos relacionados a un expediente");		
		List<DelitoPersona> respuesta = daoServcice.consultarDelitoPerByInvolucrado(2004L);
		logger.info("Delitos obtenidos : " + respuesta);
		assertTrue("La lista de delitos debe tener al menos un registro ", respuesta.size()>0);
		logger.info("Delitos obtenidos : " + respuesta.size());
	}
	
	public void testConsultarDelitosPorImputadoResponsable(){
		Long idInvolucrado=4L; //348L //
		Long idExpediente=1L;
		List<DelitoPersona> delitos = daoServcice.consultarDelitosPorImputadoResponsable(idInvolucrado, idExpediente);
		logger.info("Existen "+delitos.size()+" delitoPersona");
		for (DelitoPersona dPer : delitos) {
			logger.info("-------------------------------");
			logger.info("ID: "+dPer.getDelitoPersonaId());
			logger.info("Responsable: "+dPer.getProbableResponsable().getElementoId());
			logger.info("Forma: "+dPer.getFormaParticipacion().getValor());
			logger.info("Victima: "+dPer.getVictima().getElementoId());
			
		}
	}
	
	public void testConsultarDelitosPorImputadoResponsableConVictima(){
		
		Long idInvolucrado=14869L;
		Long idExpediente=3081L;
		
		List<DelitoPersona> delitos = daoServcice.consultarDelitosPorImputadoResponsableConVictima(idInvolucrado, idExpediente);
		
		logger.info("Existen "+delitos.size()+" delitoPersona");
		for (DelitoPersona dPer : delitos) {
			logger.info("-------------------------------");
			logger.info("ID: "+dPer.getDelitoPersonaId());
			logger.info("Responsable: "+dPer.getProbableResponsable().getElementoId());
			logger.info("Forma: "+dPer.getFormaParticipacion().getValor());
			logger.info("Victima: "+dPer.getVictima().getElementoId());
			
		}
	}
	
	public void testConsultarVictimaImputadoPorDelito(){
		Long idDelito= null;//13L;
		Long idExpediente=34L;
		List<DelitoPersona> delitos = daoServcice.consultarVictimaImputadoPorDelito(idDelito, idExpediente);
		logger.info("Existen "+delitos.size()+" delitoPersona");
		for (DelitoPersona dPer : delitos) {
			logger.info("-------------------------------");
			logger.info("ID: "+dPer.getDelitoPersonaId());
			logger.info("Responsable: "+dPer.getProbableResponsable().getElementoId());
			logger.info("Forma: "+dPer.getFormaParticipacion().getValor());
			logger.info("Victima: "+dPer.getVictima().getElementoId());
			
		}
	}
	
	public void testConsultarDelitosPersonaPorExpedienteIdsDelito(){
		Long idExpediente=34L;
		List<Long> idsDelitos = new ArrayList<Long>();
//		idsDelitos.add(13L);
//		idsDelitos.add(211L);
//		idsDelitos.add(212L);
		
		List<DelitoPersona> delitos = daoServcice.consultarDelitosPersonaPorExpedienteIdsDelito(idsDelitos , idExpediente);
		logger.info("Existen "+delitos.size()+" delitoPersona");
		for (DelitoPersona dPer : delitos) {
			logger.info("-------------------------------");
			logger.info("ID: "+dPer.getDelitoPersonaId());
			logger.info("Responsable: "+dPer.getProbableResponsable().getElementoId());
			logger.info("Forma: "+dPer.getFormaParticipacion().getValor());
			logger.info("Victima: "+dPer.getVictima().getElementoId());
			
		}
	}
	public void testInsertar(){
	    DelitoPersona dp = new DelitoPersona();
	    dp.setProbableResponsable(new Involucrado(354L));
	    Delito del = new Delito();
	    del.setDelitoId(19L);
	    dp.setDelito(del);
	    daoServcice.create(dp);
	}
	
	public void testConsultarDelitosProbableResp(){
		List<DelitoPersona> delitos = daoServcice.consultarDelitosPorImputadoResponsable(4L);
		logger.debug("Delitos: " + delitos.size());
	}
	
	public void desactivarDelitoPersona(){
		daoServcice.desactivarDelitoPersona(4L);
	}
	
	public void testObtenerDetenidosPorMesYDelito() {		
		try {
			Long respuesta = daoServcice.obtenerDetenidosPorMesYDelito(DateUtils.obtener("01/07/2011"), 
											DateUtils.obtener("31/08/2011"), new Long(1));
			
			assertNotNull("La lista debe tener minimo un registro ", respuesta);
			logger.info("La lista debe tener minimo un registro " + respuesta);
		} catch (NSJPNegocioException e) {
			e.printStackTrace();
		}
	}

	public void testExisteRelacionProbableResponsableVictimaDelito() {
		Long idDelito = 1404L;
		Long idProbableResponsable = 8900L;
		Long idVictima = 8897L;
		Long idFormaParticipacion = 2162L;
		
		Boolean existe = false;
		
		existe = daoServcice
				.existeRelacionProbableResponsableVictimaDelitoFormaParticipacion(
						idDelito, idProbableResponsable, idVictima,
						idFormaParticipacion);
		
		logger.info("Existe:"+ existe);
	}
	
	public void testConsultarRelacionesDelitoPersonaPorIdsImputados() {
		
		List <Long> imputadosId = new ArrayList<Long>();
		imputadosId.add(3255L);
		imputadosId.add(3284L);
//		imputadosId.add(3098L);
		Long expedienteId = 548L;
		
		
		List<DelitoPersona> listaDelPer;
		try {
			listaDelPer = daoServcice
					.consultarRelacionesDelitoPersonaPorIdsImputados(imputadosId,expedienteId);
			
			if(listaDelPer == null || listaDelPer.isEmpty()){
				logger.info("Lista relaciones delito persona Vacia:");
			}
			
			for(DelitoPersona relDelitoPer:listaDelPer){
				logger.info("RelDelitoPersonaId:"+relDelitoPer.getDelitoPersonaId());
				logger.info("Probable Responsable:"+relDelitoPer.getProbableResponsable().getElementoId());
				logger.info("Delito:"+relDelitoPer.getDelito().getCatDelito().getNombre());
				logger.info("Victima:"+relDelitoPer.getVictima().getElementoId());
			}
			
		} catch (NSJPNegocioException e) {
			
			logger.error("Parametos insuficientes", e);
			//NSJPNegocioException e;
		}
	}
	
	public void testConsultarDelitoPersonaPorFiltro(){
		DelitoPersona filtro = new DelitoPersona();
		
		Involucrado invl = new Involucrado();
		invl.setFolioElemento("FG15926");
		
		Involucrado victima = new Involucrado();
		victima.setFolioElemento("FG15923");
		
		CatDelito catDel = new CatDelito();
		catDel.setClaveInterInstitucional("CVE001");
		Delito del = new Delito();
		del.setCatDelito(catDel);
		
		filtro.setProbableResponsable(invl);
		filtro.setVictima(victima);
		filtro.setDelito(del);
		
		try {
			DelitoPersona delitoBD = daoServcice.consultarDelitoPersonaPorCriterios(filtro);
			if (delitoBD != null){
				logger.info("Id DelitoPersona: " + delitoBD.getDelitoPersonaId());
				if (delitoBD.getProbableResponsable() != null){
					logger.info("Id Probable: " + delitoBD.getProbableResponsable().getElementoId());					
				}
				if (delitoBD.getVictima() != null){
					logger.info("Id Victima: " + delitoBD.getVictima().getElementoId());					
				}
				if (delitoBD.getDelito() != null){
					logger.info("Id Delito: " + delitoBD.getDelito().getDelitoId());					
				}
			}
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public void testConsultarRelacionesDelitoPersonaDelExpediente() {
		try {
			Expediente expedienteInput = new Expediente();
			Long expedienteId = 3274L;
			expedienteInput.setExpedienteId(expedienteId);

			List<DelitoPersona> listaRelDelPer = daoServcice
					.consultarRelacionesDelitoPersonaDelExpediente(expedienteInput);

			if (listaRelDelPer != null && !listaRelDelPer.isEmpty()) {
				for (DelitoPersona delPer : listaRelDelPer) {
					logger.info("Rel_Del_Persona_ID=" + delPer.getDelitoPersonaId());
				}
			}

		} catch (NSJPNegocioException e) {

			logger.error("Parametos insuficientes", e);
			// NSJPNegocioException e;
		}
	}
	
	public void testConsultarListaRelacionesDelitoPersona() {
		try {
			
			List<Long> idsRelsDelPer = new ArrayList<Long>();
			idsRelsDelPer.add(1105L);
			idsRelsDelPer.add(1106L);
			idsRelsDelPer.add(1110L);
			
			List<DelitoPersona> listaRelDelPer = daoServcice.consultarListaRelacionesDelitoPersona(idsRelsDelPer);
			if (listaRelDelPer != null && !listaRelDelPer.isEmpty()) {
				
				logger.info("LISTA DE RELACIONES DELITO PERSONA...................");
				for (DelitoPersona delPer : listaRelDelPer) {
					logger.info("_________________________________________________");
					logger.info("Rel_Del_Persona_ID=" + delPer.getDelitoPersonaId());
					logger.info("Delito_id=" + delPer.getDelito().getDelitoId());
					logger.info("Probable_Responsable_id=" + delPer.getProbableResponsable().getElementoId());
					logger.info("Victima_id=" + delPer.getVictima().getElementoId());
					logger.info("_________________________________________________");
				}
			}else if(listaRelDelPer == null){
				logger.info("LISTA NULL");
			}else if(listaRelDelPer.isEmpty()){
				logger.info("LISTA VACIA");
			}

		} catch (NSJPNegocioException e) {

			logger.error("Parametos insuficientes", e);
			// NSJPNegocioException e;
		}
	}
	
	public void testObtenerUltimoFolioDelitoPersona(){
		try {
			Long folio = daoServcice.obtenerUltimoFolioDelitoPersona();
			logger.info("La primera ocasion el folio obtenido es nulo, folio:"+ folio);
			assertNull("folio:", folio);
			//assertEquals("folios esperados", "FG20131", folio);
		} catch (Exception e) {
			logger.error("Exepcion", e);
		}
	}
	
	public void testConsultarRelacionDelitoPersonaPorFolio(){
		try{
			DelitoPersona delitoPer = new DelitoPersona();
			delitoPer.setFolioDelitoPersona("FG2013-1298713");
			
			delitoPer = daoServcice.consultarRelacionDelitoPersonaPorFolio(delitoPer);
			assertNotNull("folio:", delitoPer);
			logger.info("-------------------------------");
			logger.info("Id relacion DP: " + delitoPer.getDelitoPersonaId());
			logger.info("Folio: " + delitoPer.getFolioDelitoPersona());
			logger.info("Delito: "
					+ delitoPer.getDelito().getCatDelito()
							.getNombre());
			logger.info("Responsable: "
					+ delitoPer.getProbableResponsable().getElementoId());
			logger.info("Victima: "
					+ delitoPer.getVictima().getElementoId());
			
		}catch(Exception e){
			logger.error("Exepcion", e);
		}
	}
	
 
}
