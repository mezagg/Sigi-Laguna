/**
 * Nombre del Programa 		: MandamientoPersonaDAOImplTest.java
 * Autor 					: AlejandroGA
 * Compania 				: Ultrasist
 * Proyecto 				: NSJP 								Fecha: 06/06/2013
 * Marca de cambio 			: N/A
 * Descripcion General 		: Clase para test de mandamientoPersona
 * Programa Dependiente 	: N/A
 * Programa Subsecuente 	: N/A
 * Cond. de ejecucion 		: N/A
 * Dias de ejecucion 		: N/A 								Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor 					: N/A
 * Compania 				: N/A
 * Proyecto 				: N/A 								Fecha: N/A
 * Modificacion 			: N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.dao.test.mandamiento.impl;

import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.documento.EstatusMandamiento;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dao.mandamiento.MandamientoPersonaDAO;
import mx.gob.segob.nsjp.dao.test.base.BaseTestPersistencia;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.dto.mandamiento.FiltroMandamientoPersonaDTO;
import mx.gob.segob.nsjp.model.Mandamiento;
import mx.gob.segob.nsjp.model.MandamientoPersona;
import mx.gob.segob.nsjp.model.Persona;
import mx.gob.segob.nsjp.model.Valor;

/**
 * @author AlejandroGA
 *
 */
public class MandamientoPersonaDAOImplTest extends BaseTestPersistencia<MandamientoPersonaDAO> {
	
	public void testGenerarMandamientoPersona(){
		logger.info("CREANDO UN MANDAMIENTO PERSONA");
		
		//Mandamiento Persona
		//List<MandamientoPersona> mandamientosPersona = new ArrayList<MandamientoPersona>();
		MandamientoPersona mandamientoPersona = new MandamientoPersona();
		
		//mandamientoPersona.setMandamientoPersonaId();
		mandamientoPersona.setMandamiento(new Mandamiento(2611L));
		mandamientoPersona.setPersona(new Persona(4391L));
		mandamientoPersona.setEstatus(new Valor(EstatusMandamiento.EN_PROCESO.getValorId()));
		mandamientoPersona.setFechaEjecucion(new Date());
		mandamientoPersona.setFolioInterInstitucional("FOLIO1233457");
		//mandamientosPersona.add(mandamientoPersona);
		//mandamiento.setMandamientosPersona(mandamientosPersona);

		daoServcice.create(mandamientoPersona);
	}
	
	public void testObtenerUltimoFolioMandamientoPersona(){
		logger.info("OBTENER EL ULTIMO FOLIO MANDAMIENTO PERSONA");
		Long ultimoFolio=daoServcice.obtenerUltimoFolioMandamientoPersona();
		logger.info("ULTIMO FOLIO OBTENIDO::::::::::::" + ultimoFolio);
		assertEquals(new Long("00008"), ultimoFolio);
	}
	
	public void testLeerMandamientoPersonaPorId(){
		
		MandamientoPersona mandamientoPersona = daoServcice.read(17L);
		logger.info("Folio:" + mandamientoPersona.getFolioInterInstitucional());
		logger.info("Id:" + mandamientoPersona.getMandamientoPersonaId());
		logger.info("Valor estatus:" + mandamientoPersona.getEstatus().getValor());
		logger.info("Fecha ejecucion:" + mandamientoPersona.getFechaEjecucion());
		logger.info("Nombre demograficos:" + mandamientoPersona.getPersona().getNombreDemograficos());
		logger.info("FECHA RESOLUTIVO:" + mandamientoPersona.getMandamiento().getResolutivo().getTemporizador());
		logger.info("DOCUMENTO PERSONA:" + mandamientoPersona.getMandamientosPersonaDocumento());
	}
	
	
	public void testConsultarMandamientosPersonaPorFiltro() {
		
		final PaginacionDTO pag = new PaginacionDTO();
		pag.setCampoOrd("probResponsable");
		pag.setDirOrd("asc");
		PaginacionThreadHolder.set(pag);

		FiltroMandamientoPersonaDTO filtroMandamientoPersonaDTO = new FiltroMandamientoPersonaDTO();
		
		//filtroMandamientoPersonaDTO.setFolioInterInstitucional("PJ/MJ2013-00007");
		filtroMandamientoPersonaDTO.setMandamientoId(2719L);
		//filtroMandamientoPersonaDTO.setMandamientoPersonaId(6L);
		//filtroMandamientoPersonaDTO.setPersonaId(4400L);
		//filtroMandamientoPersonaDTO.setEstatusId(6359L);
		//filtroMandamientoPersonaDTO.setFechaEjecucion(new Date());

		List<MandamientoPersona> listaMP = daoServcice
				.consultarMandamientosPersonaPorFiltro(filtroMandamientoPersonaDTO);
		
		//assertEquals(1, listaMP.size());

		for (MandamientoPersona mandamientoPersona : listaMP) {
			logger.info("Folio:"
					+ mandamientoPersona.getFolioInterInstitucional());
			logger.info("Id:" + mandamientoPersona.getMandamientoPersonaId());
			logger.info("Valor estatus:"
					+ mandamientoPersona.getEstatus().getValor());
			logger.info("Fecha ejecucion:"
					+ mandamientoPersona.getFechaEjecucion());
			logger.info("Nombre demograficos:"
					+ mandamientoPersona.getPersona().getNombreDemograficos().iterator());
			logger.info("FECHA RESOLUTIVO:"
					+ mandamientoPersona.getMandamiento().getResolutivo()
							.getTemporizador());
			logger.info("DOCUMENTO PERSONA:"
					+ mandamientoPersona.getMandamientosPersonaDocumento());
		}
	}

}
