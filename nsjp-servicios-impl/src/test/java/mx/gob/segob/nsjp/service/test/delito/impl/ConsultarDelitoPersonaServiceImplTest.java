/**
* Nombre del Programa : ConsultarDelitoPersonaServiceImplTest.java
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
package mx.gob.segob.nsjp.service.test.delito.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoPersonaDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.service.delito.ConsultarDelitoPersonaService;
import mx.gob.segob.nsjp.service.expediente.BuscarExpedienteService;
import mx.gob.segob.nsjp.service.test.base.BaseTestServicios;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author adrian
 *
 */
public class ConsultarDelitoPersonaServiceImplTest extends BaseTestServicios<ConsultarDelitoPersonaService> {

	@Autowired
    private BuscarExpedienteService expedienteService;
	
	/**
	 * Test method for {@link mx.gob.segob.nsjp.service.delito.impl.ConsultarDelitoPersonaServiceImpl#consultarDelitosVictimaPorImputado(java.lang.Long, java.lang.Long)}.
	 */
	public void testConsultarDelitosVictimaPorImputado() {
		Long idInvolucrado=15709L;
		Long idExpediente=3212L;
		
		try {
			List<DelitoPersonaDTO> delitos = service.consultarDelitosVictimaPorImputado(idInvolucrado, idExpediente);
			logger.info("Existen "+delitos.size()+" delitoPersona");
			for (DelitoPersonaDTO dPer : delitos) {
				logger.info("-------------------------------");
				logger.info("ID: "+dPer.getDelitoPersonaId());
				logger.info("Responsable: "+dPer.getProbableResponsable().getElementoId());
				logger.info("Responsable Folio: "+dPer.getProbableResponsable().getFolioElemento());
				logger.info("Forma: "+dPer.getFormaParticipacion().getValor());
				logger.info("Victima: "+dPer.getVictima().getElementoId());
				logger.info("Victima Folio: "+dPer.getVictima().getFolioElemento());
				logger.info("Victima Nombre: "+dPer.getVictima().getNombreCompleto());
			}
		} catch (NSJPNegocioException e) {
			logger.info(e.getMessage(), e);
		}
	}
	
	public void testConsultarDelitosVictimaImputadoPorExpediente() {
		Long expedienteId = 3176L;
		Long numeroExpedienteId = 3585L;

		try {
			ExpedienteDTO expedienteCompleto = new ExpedienteDTO(expedienteId);
			expedienteCompleto.setNumeroExpedienteId(numeroExpedienteId);

			expedienteCompleto.setInvolucradosSolicitados(true);
			expedienteCompleto = expedienteService
					.obtenerExpediente(expedienteCompleto);

			List<DelitoPersonaDTO> delitos = service
					.consultarDelitosVictimaImputadoPorExpediente(expedienteCompleto);
			logger.info("Existen " + delitos.size() + " delitoPersona");
			for (DelitoPersonaDTO dPer : delitos) {
				logger.info("-------------------------------");
				logger.info("ID: " + dPer.getDelitoPersonaId());
				logger.info("Responsable: "
						+ dPer.getProbableResponsable().getElementoId());
				logger.info("Responsable Folio: "
						+ dPer.getProbableResponsable().getFolioElemento());
				logger.info("Forma: " + dPer.getFormaParticipacion().getValor());
				logger.info("Victima: " + dPer.getVictima().getElementoId());
				logger.info("Victima Folio: "
						+ dPer.getVictima().getFolioElemento());
				logger.info("Victima Nombre: "
						+ dPer.getVictima().getNombreCompleto());
			}
		} catch (NSJPNegocioException e) {
			logger.info(e.getMessage(), e);
		}
	}
	
	public void testValidarRelacionPersonaDelitoExpediente(){
		Long idDelito=16L;
		Long idExpediente=34L;
		try {
			Boolean existeRelacion = service.existeRelacionPersonaDelitoExpediente(idDelito, idExpediente);
			logger.info("Existen Relacion:"+ existeRelacion);
		} catch (NSJPNegocioException e) {
			logger.info(e.getMessage(), e);
		}
	}
	
	public void testConsultarVictimaImputadoPorDelito(){
		Long idDelito=13L;
		Long idExpediente=34L;
		try {
			List<DelitoPersonaDTO> delitos = service.consultarVictimaImputadoPorDelito(idDelito, idExpediente);
			logger.info("Existen "+delitos.size()+" delitoPersona");
			for (DelitoPersonaDTO dPer : delitos) {
				logger.info("-------------------------------");
				logger.info("ID: "+dPer.getDelitoPersonaId());
				logger.info("Responsable: "+dPer.getProbableResponsable().getElementoId());
				logger.info("Responsable: "+dPer.getProbableResponsable().getNombreCompleto());
				logger.info("Forma: "+dPer.getFormaParticipacion().getValor());
				logger.info("Victima: "+dPer.getVictima().getElementoId());
				logger.info("Victima Nombre: "+dPer.getVictima().getNombreCompleto());
			}
		} catch (NSJPNegocioException e) {
			logger.info(e.getMessage(), e);
		}
	}
	
	
	public void testConsultarDelitosPersonaPorExpedienteIdsDelito(){
		Long idExpediente=34L;
		List<Long> idsDelitos = new ArrayList<Long>();
		idsDelitos.add(13L);
		idsDelitos.add(211L);
		idsDelitos.add(212L);
		
		try {
			
			List<DelitoPersonaDTO> delitos = service.consultarDelitosPersonaPorExpedienteIdsDelito(idsDelitos, idExpediente);
			logger.info("Existen "+delitos.size()+" delitoPersona");
			for (DelitoPersonaDTO dPer : delitos) {
				logger.info("-------------------------------");
				logger.info("ID: "+dPer.getDelitoPersonaId());
				logger.info("Responsable: "+dPer.getProbableResponsable().getElementoId());
				logger.info("Responsable: "+dPer.getProbableResponsable().getNombreCompleto());
				logger.info("Forma: "+dPer.getFormaParticipacion().getValor());
				logger.info("Victima: "+dPer.getVictima().getElementoId());
				logger.info("Victima Nombre: "+dPer.getVictima().getNombreCompleto());
			}
		} catch (NSJPNegocioException e) {
			logger.info(e.getMessage(), e);
		}
	}
	
	public void testConsultarVictimaImputadoPorExpediente(){
		Long idExpediente=1024L;
		try {
			List<DelitoPersonaDTO> delitos = service.consultarVictimaImputadoPorExpediente(idExpediente);
			assertFalse("la lista no puede ser vacia", delitos.isEmpty());
			logger.info("Existen "+delitos.size()+" delitoPersona");
			for (DelitoPersonaDTO dPer : delitos) {
				logger.info("-------------------------------");
				logger.info("ID: "+dPer.getDelitoPersonaId());
				logger.info("Responsable: "+dPer.getProbableResponsable().getElementoId());
				logger.info("Responsable: "+dPer.getProbableResponsable().getNombreCompleto());
				logger.info("Forma: "+dPer.getFormaParticipacion().getValor());
				logger.info("Victima: "+dPer.getVictima().getElementoId());
				logger.info("Victima Nombre: "+dPer.getVictima().getNombreCompleto());
			}
			logger.info("\nExisten "+delitos.size()+" delitoPersona");
		} catch (NSJPNegocioException e) {
			logger.info(e.getMessage(), e);
		}
	}
	
	
	public void testDesactivarDelitoPersona() {
			try {
				service.desactivarDelitoPersona(5L);
			} catch (NSJPNegocioException e) {
				logger.info(e.getMessage(), e);
			}
	}
	
	public void testConsultarDelitosVictimaPorImputadoUno() {		
		List<DelitoPersonaDTO> respuesta = service.consultarDelitosVictimaPorImputado(725L);
		
		assertTrue("La lista debe tener minimo un registro :: ",respuesta.size()>0);
		for (DelitoPersonaDTO delitoPersonaDTO : respuesta) {
			logger.info("Delito persona :: "+delitoPersonaDTO.getDelitoPersonaId());
		}
	}

	public void testEliminarDelitoPersona(){
		Long delitoPersonaId = 544L;
		Boolean eliminado;
		try {
			eliminado = service.eliminarDelitoPersona(delitoPersonaId);
			logger.info("Valor:"+ eliminado);
			assertFalse("Se ha eliminado con exito.",eliminado);
		} catch (NSJPNegocioException e) {
			logger.info(e.getMessage(), e);
		}
	}
	
	public void testConsultarDelitoPersonaPorId(){
		Long idDelitoPersona=730L;
		try {
			DelitoPersonaDTO loDelito = service.consultarDelitoPersonaPorId(idDelitoPersona);
			
				logger.info("-------------------------------");
				logger.info("ID: "+loDelito.getDelitoPersonaId());
				logger.info("Delito: "+loDelito.getDelito().getNombreDelito());
				logger.info("Responsable: "+loDelito.getProbableResponsable().getElementoId());
				logger.info("Responsable: "+loDelito.getProbableResponsable().getNombreCompleto());
				logger.info("Forma: "+loDelito.getFormaParticipacion().getValor());
				logger.info("Victima: "+loDelito.getVictima().getElementoId());
				logger.info("Victima Nombre: "+loDelito.getVictima().getNombreCompleto());
		} catch (NSJPNegocioException e) {
			logger.info(e.getMessage(), e);
		}
	}
	
	public void tetsConsultarRelacionesDelitoPersonaPorAudiencia(){
	
		AudienciaDTO audienciaDto = new AudienciaDTO();
		audienciaDto.setId(629L);
		audienciaDto.setExpediente(new ExpedienteDTO(525L));
		
		try {
			List<DelitoPersonaDTO> listaDelitoPer = service.consultarRelacionesDelitoPersonaPorAudiencia(audienciaDto);
			
			if(listaDelitoPer == null || listaDelitoPer.isEmpty()){
				logger.info("LA LISTA ESTA VACÍA");
			}
			
			for(DelitoPersonaDTO loDelito:listaDelitoPer){				
				logger.info("-------------------------------");
				logger.info("ID: "+loDelito.getDelitoPersonaId());
				logger.info("Delito: "+loDelito.getDelito().getNombreDelito());
				logger.info("Responsable: "+loDelito.getProbableResponsable().getElementoId());
				logger.info("Responsable: "+loDelito.getProbableResponsable().getNombreCompleto());
				logger.info("Victima: "+loDelito.getVictima().getElementoId());
				logger.info("Victima Nombre: "+loDelito.getVictima().getNombreCompleto());
			}
		} catch (NSJPNegocioException e) {
			logger.info(e.getMessage(), e);
		}
	}
	
	public void testConsultarRelacionDelitoPersonaPorFolio() {
		try {

			DelitoPersonaDTO delitoPersonaDTO = new DelitoPersonaDTO();
			delitoPersonaDTO.setFolioDelitoPersona("FG2013-464");
			
			delitoPersonaDTO = service
					.consultarUnicaRelacionDelitoPersonaPorFolio(delitoPersonaDTO);
			
			assertNotNull("delito Persona:", delitoPersonaDTO);
			
			logger.info("-------------------------------");
			logger.info("Id relacion DP: " + delitoPersonaDTO.getDelitoPersonaId());
			logger.info("Folio: " + delitoPersonaDTO.getFolioDelitoPersona());
			logger.info("Delito: "
					+ delitoPersonaDTO.getDelito().getDelitoId());
			logger.info("Responsable: "
					+ delitoPersonaDTO.getProbableResponsable().getElementoId());
			logger.info("Responsable Folio: "
					+ delitoPersonaDTO.getProbableResponsable().getFolioElemento());
			logger.info("Responsable: "
					+ delitoPersonaDTO.getProbableResponsable()
							.getNombreCompleto());
			logger.info("Victima: "
					+ delitoPersonaDTO.getVictima().getElementoId());
			logger.info("Victima Folio: "
					+ delitoPersonaDTO.getVictima().getFolioElemento());
			logger.info("Victima Nombre: "
					+ delitoPersonaDTO.getVictima().getNombreCompleto());
		} catch (NSJPNegocioException e) {
			logger.info(e.getMessage(), e);
		}
	}
}
