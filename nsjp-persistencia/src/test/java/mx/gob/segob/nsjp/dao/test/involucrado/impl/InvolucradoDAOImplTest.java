/**
* Nombre del Programa : InvolucradoDAOImplTest.java
* Autor                            : cesarAgustin
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 12 Apr 2011
* Marca de cambio        : N/A
* Descripcion General    : Clase para generar pruebas unitarias de los medotos de InvolucradoDAO
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
package mx.gob.segob.nsjp.dao.test.involucrado.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.dao.involucrado.InvolucradoDAO;
import mx.gob.segob.nsjp.dao.test.base.BaseTestPersistencia;
import mx.gob.segob.nsjp.dto.expediente.FiltroExpedienteDTO;
import mx.gob.segob.nsjp.model.Caso;
import mx.gob.segob.nsjp.model.Expediente;
import mx.gob.segob.nsjp.model.Involucrado;
import mx.gob.segob.nsjp.model.NombreDemografico;
import mx.gob.segob.nsjp.model.Valor;

/**
 * Clase para generar pruebas unitarias de los medotos de InvolucradoDAO.
 * @version 1.0
 * @author cesarAgustin
 *
 */
public class InvolucradoDAOImplTest extends BaseTestPersistencia<InvolucradoDAO> {

	public void testbuscarExpedientesByNombre() {
		logger.debug("Prueba para recuperar los involucrados que correpondan con el nombre enviado");
		
		FiltroExpedienteDTO filtrosBusquedaExpediente=new FiltroExpedienteDTO();
		filtrosBusquedaExpediente.setNombre("%%");
		filtrosBusquedaExpediente.setApellidos("%lacatolica%");
		filtrosBusquedaExpediente.setApellidoMat( "%%");
	
		List<Involucrado> involucradosResultado = daoServcice.consultarExpedientesByNombre(filtrosBusquedaExpediente);
	
		assertTrue("La lista debe traer almenos un registro ", involucradosResultado.size()>0);
		logger.info("Involucrados : " + involucradosResultado.size());
		for (Involucrado involucrado : involucradosResultado) {
			logger.info("Id Involucrado : " + involucrado.getElementoId());			
		}
		
	}
	
	public void testEliminarInvolucrado(){
		Involucrado inv = daoServcice.read(310L);		
		daoServcice.delete(inv);
		 inv = daoServcice.read(312L);		
		daoServcice.delete(inv);
		 inv = daoServcice.read(313L);		
		daoServcice.delete(inv);
		 inv = daoServcice.read(314L);		
		daoServcice.delete(inv);
		 inv = daoServcice.read(315L);		
		daoServcice.delete(inv);
	}
	
	public void _testConsultarInvolucradosByExpediente() {
		logger.debug("Prueba para recuperar los involucrados que correpondan con el alias enviado");
	
		List<Involucrado> involucradosResultado = daoServcice.consultarInvolucradosByExpediente(820L);
				
		assertTrue("La lista debe traer almenos un registro ", involucradosResultado.size()>0);
		for (Involucrado involucrado : involucradosResultado) {
			//logger.info("Expediente : " + involucrado.getExpediente().getNumeroExpediente());
			logger.info("Id::::"+involucrado.getElementoId());
			logger.info("Nombre::::"+involucrado.getNombreDemograficos().iterator().next().getNombre());
			logger.info("Calidad::::"+involucrado.getCalidad().getTipoCalidad().getValor());
		}
		logger.info("Involucrados : " + involucradosResultado.size());

		
	}
	
	public void _testConsultarInvolucradosByExpedienteLike() {
		logger.debug("Prueba para recuperar los involucrados que correpondan con el alias enviado");
	
		List<Involucrado> involucradosResultado = daoServcice.consultarExpedientesByAlias("%hom%");
				
		assertTrue("La lista debe traer almenos un registro ", involucradosResultado.size()>0);
		logger.info("Involucrados : " + involucradosResultado.size());
		for (Involucrado involucrado : involucradosResultado) {
			logger.info("Expediente : " + involucrado.getExpediente().getNumeroExpediente());
		}
		
	}
	
	public void testObtenerInvolucradosPorExpedienteYCalidades () {
		Calidades[] calidades = new Calidades[] { Calidades.DENUNCIANTE, Calidades.DENUNCIANTE_ORGANIZACION }; 
		List<Involucrado> involucrados = daoServcice.obtenerInvolucradosPorExpedienteYCalidades(470L, calidades,true);
		assertFalse("La lista no puede venir vacia: ", involucrados.isEmpty());
		logger.info("Involucrado " + involucrados.size());
		for (Involucrado involucrado : involucrados) {
			logger.info("Involucrado " + involucrado.getElementoId());
			logger.info("Involucrado " + involucrado);
			logger.info("Involucrado " + involucrado.getNombreDemograficos());
		}
		logger.info("Involucrado " + involucrados.size());
	}
	
	public void testObtenerFolioInterInstInvolucradosPorExpedienteYCalidades() {
		Calidades[] calidades = new Calidades[] { Calidades.DEFENDIDO };
		Long expedienteId = 1L;
		
		List<String> listaFolioDefendidos = daoServcice
				.obtenerFolioInterInstInvolucradosPorExpedienteYCalidades(expedienteId, calidades, null);
		
		assertFalse("La lista no puede venir vacia: ", listaFolioDefendidos.isEmpty());
		logger.info("Involucrado " + listaFolioDefendidos.size());
		for (String involucrado : listaFolioDefendidos) {
			logger.info("Involucrado " + involucrado);
		}
		logger.info("Involucrado " + listaFolioDefendidos.size());
		Set<String> conjuntoFolios = new HashSet<String>(listaFolioDefendidos);
		String folioElemento= "FG15681";
		Boolean esRegistrado = conjuntoFolios.contains(new String(folioElemento));
		logger.info("EsRegistrado:"+ esRegistrado);
	}
	
	public void _testIngresarInvolucrado() {
		//ExpedienteDAOImplTest expedienteDao = new ExpedienteDAOImplTest();
		logger.debug("Prueba para ingresar un nuevo involucrado");
		Expediente expediente = new Expediente(new Long(1), "0");
		
		Involucrado involucrado = new Involucrado();
		
		
		involucrado.setEsVivo(false);		
		involucrado.setMotivoComparecencia("Por rata");		
		involucrado.setExpediente(expediente);
		involucrado.setCondicion((short) 1L);
		
		Long idInvolucrado = daoServcice.create(involucrado);
		assertTrue("El idInvolucrado debe ser mayor a cero", idInvolucrado>0);
	}
	
	public void testObtenerInvolucrados() {
		logger.debug("Prueba para obtener los registros de involucrados");
		
		List<Involucrado> involucrados = daoServcice.obtenerInvolucradosAll();
		assertFalse("La lista no puede venir vacia", involucrados.isEmpty());	
	}
	
	public void testObtenerInvolucradosExpedinteCalidad () {
		List<Involucrado> involucrados =  daoServcice.obtenerInvByIdExpAndCalidad(2830L, Calidades.DENUNCIANTE.getValorId(),null);
		assertTrue("El id debe ser mayor a cero : ", involucrados.size()>0);
		for (Involucrado involucrado : involucrados) {
			logger.info("ID " + involucrado.getElementoId());
			logger.info("ID involucrado " + involucrado.getElementoId());	
//			for (NombreDemografico nombre : involucrado.getNombreDemograficos()) {
//				logger.info("nombre involucrado " + nombre.getNombre());
//				logger.info("nombre involucrado " + nombre.getApellidoPaterno());
//				logger.info("nombre involucrado " + nombre.getApellidoMaterno());
//			}
			logger.info("TOTAL: " + involucrados.size());	

		}
		
	}
	
	public void obtenerInvolucradosByAudiencia(){
		Long audienciaId=2L;
		Calidades calidadId=Calidades.PROBABLE_RESPONSABLE_PERSONA;
		List<Involucrado> involucrados = daoServcice.obtenerInvolucradosByAudiencia(audienciaId, calidadId.getValorId());
		
		logger.info("\n\rExisten "+involucrados.size()+" involucrados");
		for (Involucrado inv : involucrados) {
			logger.info("ID elemento"+inv.getElementoId());
			logger.info("Situacion juridica: "+inv.getSituacionJuridica());
			logger.info("CAlidad: "+inv.getCalidad().getTipoCalidad());
		}
	}
	
	public void testObtenerInvolucradosByNumeroExpedienteIdYCalidades(){
		
		
		List<Involucrado> involucrados = daoServcice.obtenerInvolucradosByCasoYCalidades("YUC/PG/XX/PGE/2011/AA-00000", 
				new Calidades[]{Calidades.VICTIMA_PERSONA,Calidades.PROBABLE_RESPONSABLE_PERSONA});
		for (Involucrado inv : involucrados) {
			logger.info("ID elemento"+inv.getElementoId());
			
		}
		
	}
	
	public void testConsultarInvolucradosByIds(){
		
		List<Long> idInvolucrados = Arrays.asList(1L,2L, 22L);
		
		List<Involucrado> involucrados = daoServcice.consultarInvolucradosByIds(idInvolucrados);
		
		assertTrue("La lista debe traer almenos un registro ", involucrados.size()>0);
		logger.info("#Involucrados : " + involucrados.size());
		for (Involucrado involucradoDTO : involucrados) {
			logger.info(" Involucrado ID: "+ involucradoDTO.getElementoId());
			logger.info(" Involucrado: "+ involucradoDTO);
		}
	}
	
	public void testObtenerExpedientesPorCondicionDetencionInvYMes () {
		try {
			List<Object[]> respuesta = daoServcice.obtenerExpedientesPorCondicionDetencionInvYMes(
										DateUtils.obtener("01/07/2011"), DateUtils.obtener("31/08/2011"), false);
			assertTrue("La lista debe tener minimo un registro ", respuesta.size()>0);
			logger.info("Numero registros rspuesta " + respuesta.size());
			
			for (Object[] objects : respuesta) {
				logger.info("MES "+objects[0]+" REGISTROS "+objects[0]);				
			}
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage());
		}
	}
	
	public void testObtenerInvolucradoByFolioElemento () {
		Involucrado respuesta = daoServcice.obtenerInvolucradoByFolioElemento("pj1");
		
		assertNotNull(respuesta);
		logger.info("-----------------------");
		logger.info("Involucrado ID :: "+respuesta.getElementoId());
		logger.info("-----------------------");
	}
	
	
	public void testConsultarInvolucradosDeAudienciaPorCalidad() throws NSJPNegocioException{
    	Long idAudiencia = 601L;
    	List<Long> calidades = new ArrayList<Long>();
//    	calidades.add(Calidades.PROBABLE_RESPONSABLE_PERSONA.getValorId());
//    	calidades.add(Calidades.TESTIGO.getValorId());
//    	calidades.add(Calidades.VICTIMA_PERSONA.getValorId());
//    	
    	
		List<Involucrado> involucrados = daoServcice.consultarInvolucradosDeAudienciaPorCalidad(idAudiencia, calidades,true);
		logger.info("Existen "+involucrados.size()+" involucrados");
		for (Involucrado involucradoDTO : involucrados) {
			logger.info(" Involucrado ID: "+ involucradoDTO.getElementoId());
			//logger.info(" Nombre: "+ involucradoDTO.getNombreDemograficos().iterator().next().getNombre());
			logger.info(" Calidad: "+ involucradoDTO.getCalidad().getTipoCalidad().getValor());
			logger.info(" EsVivo: "+ involucradoDTO.getEsVivo());
		}	
	}
	
	public void testObtenerVictimasYDenunciantesVictimaDeAudiencia() throws NSJPNegocioException{
    	Long idAudiencia = 573L;
    	
    	
		List<Involucrado> involucrados = daoServcice.obtenerDenuncianteVictimaSinPaginado(idAudiencia,Calidades.DENUNCIANTE.getValorId());
		logger.info("Existen "+involucrados.size()+" involucrados");
		for (Involucrado involucradoDTO : involucrados) {
			logger.info(" Involucrado ID: "+ involucradoDTO.getElementoId());
			logger.info(" Nombre: "+ involucradoDTO.getNombreDemograficos().iterator().next().getNombre());
			logger.info(" Calidad: "+ involucradoDTO.getCalidad().getTipoCalidad().getValor());
		}	
	}
	
	public void testObtenerInvolucradoPorCasoYFolioElemento(){
		
		Caso c = new Caso();
		c.setNumeroGeneralCaso("YUC/FG/XX/PGE/2011/AA-00470");
		
		Involucrado inv = new Involucrado();
		inv.setFolioElemento("FG2446");
		
		try {
			Involucrado resultado = daoServcice.obtenerInvolucradoPorCasoYFolioElemento(inv, c);
			if (resultado != null){
				logger.info("Id del involucrado obtenido: "+resultado.getElementoId());				
			}
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage(),e);
		}
	}
	
	
	public void testConsultarProbablesResponsablesParaSolucitudDeDefensor() {

		try {
			List<Involucrado> involucrados = daoServcice
					.consultarProbablesResponsablesParaSolucitudDeDefensor(
							3081L,
							Calidades.PROBABLE_RESPONSABLE_PERSONA.getValorId(),
							false, true,true);

			if (involucrados != null){
				logger.info("Existen " + involucrados.size() + " involucrados");				
			}

			for (Involucrado involucradoDTO : involucrados) {
				logger.info(" Involucrado ID: "
						+ involucradoDTO.getElementoId());
				logger.info(" Nombre: "
						+ involucradoDTO.getNombreDemograficos().iterator()
								.next().getNombre());
				logger.info(" Calidad: "
						+ involucradoDTO.getCalidad().getTipoCalidad()
								.getValor());
			}

		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void testConsultarInvolucradosXSituacionSinSentencia(){
		Valor situacion = new Valor(1702L);
		
		try {
			List<Involucrado> involucrados = daoServcice.consultarInvolucradosXSituacionSinSentencia(situacion);
			if (involucrados != null
					&& !involucrados.isEmpty()){
				for (Involucrado inv : involucrados){
					logger.info("Id del Involucrado: " + inv.getElementoId());
					logger.info("Situaci&oacute;n jur&iacute;dica del involucrado: " + inv.getSituacionJuridica().getValor());
				}
			}else{
				logger.info("No se encontraron involucrados que se encuentren en la situacion juridica: " + situacion.getValorId());
			}
		} catch (NSJPNegocioException e) {
			e.printStackTrace();
		}
		
	}
	
	public void testConsultarEtapaMaximaInvolucradosDelExpediente() {
		Long expedienteId = 792L;
		Long etapaId = daoServcice
				.consultarEtapaMaximaInvolucradosDelExpediente(expedienteId);
		assertNotNull("No se identifico Etapa:", etapaId);
		logger.info("Etapa:"+ etapaId);
	}
}
