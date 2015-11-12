/**
*
* Nombre del Programa : ExpedienteDAOImplTest.java                                    
* Autor                            : Cesar Agustin                                               
* Compania                    : Ultrasist                                                
* Proyecto                      : NSJP                    Fecha: 30/03/2011 
* Marca de cambio        : N/A                                                     
* Descripcion General    : Prueba para el DAO de Expediente                      
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


package mx.gob.segob.nsjp.dao.test.expediente.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.actividad.Actividades;
import mx.gob.segob.nsjp.comun.enums.institucion.Areas;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.indicador.Indicadores;
import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dao.expediente.ExpedienteDAO;
import mx.gob.segob.nsjp.dao.test.base.BaseTestPersistencia;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.dto.caso.CasoDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatDiscriminanteDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatDistritoDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteViewDTO;
import mx.gob.segob.nsjp.dto.expediente.FiltroExpedienteDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.institucion.AreaDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.model.ConfInstitucion;
import mx.gob.segob.nsjp.model.Expediente;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.model.Valor;

/**
 * 
 * @author CesarAgustin
 * @version 1.0
 * 
 */
public class ExpedienteDAOImplTest extends BaseTestPersistencia<ExpedienteDAO> {

	public void testObtenerUltimoNumTipo () {
		String respuesta = daoServcice.obtenerUltimoNumTipoExp("ExpedienteRestDefensoria");
		assertNotNull("El string no debes ser nulo ", respuesta); 
	}
	
	public void testBuscarUltimoNumeroPorExpedienteIdAreaId(){
		Long areaId = 7L;
//		Long expedienteId = 47L; //49L;
		Long expedienteId = 296L;
		Expediente expediente = daoServcice.buscarUltimoNumeroPorExpedienteIdAreaId(expedienteId, areaId);
		assertNotNull("El expediente no debes ser nulo ", expediente);
		logger.info(" Expediente: "+ expediente.getExpedienteId());
		logger.info(" Expediente: "+ expediente.getNumeroExpedienteId());
		logger.info(" Expediente: "+ expediente.getNumeroExpediente());
		
	}
	public void _testObtenerPorEvidencia () {
		logger.debug("Prueba para obtener los expedientes que correspondan con la evidencia solicitada");

		List<Expediente> respuesta = daoServcice.buscarExpedientes("%NSJYUCPJ20114433333%",null,26L);
		assertFalse("La lista no puede venir vacia", respuesta.isEmpty());

		logger.info("Respuesta :: " + respuesta.size());
	}
	
	public void _testObtenerExpedientes() {
		logger.debug("Prueba para obtener los expedientes que correspondan con el parametro enviado");
		Long expedienteID = 1L;
		List<NumeroExpediente> respuesta = daoServcice.buscarNumeroExpedientes(expedienteID,10L);
		assertFalse("La lista no puede venir vacia", respuesta.isEmpty());

		logger.info("Respuesta :: " + respuesta.size());
		for (NumeroExpediente expediente : respuesta) {
//			logger.info(" Expediente: "+ expediente.getExpedienteId());
			logger.info(" Expediente: "+ expediente.getNumeroExpediente());
			logger.info(" Expediente: "+ expediente.getNumeroExpedienteId());
			
			
		}
	}

	public void _testRecuperarUltimoNumero() {
		logger.debug("Prueba para obtener todos los registros de Expediente ");

		String respuesta = daoServcice.obtenerUltimoNumero(44L);

		assertNotNull("No se ha obtenido el mï¿½ximo de manera correcta",
				respuesta);
		assertTrue("No se ha obtenido el mï¿½ximo de manera correcta",
				respuesta.length() > 0);
		logger.debug("Respuesta :: " + respuesta);
	}

	public void _testInsertar() {
		final Expediente exp = new Expediente();
		/*final Caso cas = new Caso();
		cas.setCasoId(1L);
		exp.setCaso(cas);*/
		exp.setFechaCreacion(new Date());
		exp.setNumeroExpediente("EXP0000INST0023");
		exp.setDescNarrativa("Narrativa de prueba 22");
		Long resultado = daoServcice.create(exp);
		assertTrue("El resultado debe ser mayor a 0 : ", resultado>0); 		
	}
	
	public void testConsultarExpedientesPorIdCaso(){
	    List<Expediente> respuesta = daoServcice.consultarExpedientesPorIdCaso(2941L, 10L);
	    assertNotNull("No puede ser nula la lista", respuesta);
	    for (Expediente expediente : respuesta) {
			logger.info("ExpedienteId:"+expediente.getExpedienteId());
			logger.info("NumeroExpedienteId:"+expediente.getNumeroExpedienteId());
		}
	    assertFalse("No puede estar vacia la lista", respuesta.isEmpty());
	}
	
	public void testConsultarExpedientesPorId(){
	    List<Expediente> respuesta = daoServcice.consultarExpedientesPorId(2941L, 10L);
	    assertNotNull("No puede ser nula la lista", respuesta);
	    assertFalse("No puede estar vacia la lista", respuesta.isEmpty());
	}
	
	public void consultarExpedienteIdPorNumeroExpediente(){
		String numeroExpediente = "NSJYUCPJ2011263333W";
	    Long respuesta = daoServcice.consultarExpedienteIdPorNumeroExpediente(numeroExpediente);
	    assertNotNull("No puede ser nulo", respuesta);
	    logger.info("Respuesta"+ respuesta);
	}
	
	public void _testObtenerExpedientesPorActividad(){
	    List<Expediente> respuesta = daoServcice.consultarExpedientesPorActividadActual(1L,1L);
	    assertNotNull("No puede ser nula la lista", respuesta);
	    assertFalse("No puede estar vacï¿½a la lista", respuesta.isEmpty());
	}
	
	public void testConsultarExpedientesActividadAreaAnio() {				
		List<NumeroExpediente> respuesta = daoServcice.consultarExpedientesActividadAreaAnio(null);
		assertTrue("La lista debe tener minimo un registro ", respuesta.size()>0);
		
		logger.info("La lista debe tener minimo un registro " + respuesta.size());		
	}

	public void testConsultarExpsActividadAreaAnio() {				
		
		FiltroExpedienteDTO filtroExpedienteDTO = new FiltroExpedienteDTO();
		
		filtroExpedienteDTO.setIdActividad(Actividades.RECIBIR_CANALIZACION_JAR.getValorId());
		filtroExpedienteDTO.setIdArea(new Long(Areas.ATENCION_TEMPRANA_PG_PENAL.ordinal()));
		//filtroExpedienteDTO.setAnio(2012L);
		filtroExpedienteDTO.setIdDiscriminante(1L);
		//filtroExpedienteDTO.setIdFuncionario(254L);
		filtroExpedienteDTO.setestatusMenuCoorJAR(2L);
		//filtroExpedienteDTO.setExpedientesAsignados(true);
		//filtroExpedienteDTO.setIdJerarquiaRemitos(44L);
		
		List<NumeroExpediente> respuesta = daoServcice.consultarExpedientesActividadAreaAnio(filtroExpedienteDTO);
		
		if(respuesta!=null && respuesta.size()>0){
			for (NumeroExpediente numeroExpediente : respuesta) {
				logger.info("Expediente: " + numeroExpediente.getNumeroExpediente());	
			}			
		}
		
	}

	public void testConsultarExpedientesPorIdCasoConfInstitucion(){
		
		Long idCaso = 4L;
		Long idConfInstitucion = null;
		
		List<Expediente> expedientes = daoServcice.consultarExpedientesPorIdCasoConfInstitucion(idCaso, idConfInstitucion);
		
		if(expedientes!= null ){
			for (Expediente expediente : expedientes) {
				logger.info("ID:"+expediente.getExpedienteId());
			} 
		}
	}
	
	public void testConsultarCausasPorIdCaso(){
		
		Long idCaso = 1L;  
		
		List<Expediente> expedientes = daoServcice.consultarCausasPorIdCaso(idCaso);
		
		if(expedientes!= null ){
			for (Expediente expediente : expedientes) {
				logger.info("IDExpediente:"+expediente.getExpedienteId());
				logger.info("IDNumeroExpediente:"+expediente.getNumeroExpedienteId());
				logger.info("Numero Expediente:"+expediente.getNumeroExpediente());
			} 
		}
	}

public void testReadExpediente(){
	Expediente expediente = daoServcice.read(792L);
	assertNotNull(expediente);
	logger.info("IDExpediente:"+expediente.getExpedienteId());
	logger.info("IDNumeroExpediente:"+expediente.getNumeroExpedienteId());
	logger.info("Numero Expediente:"+expediente.getNumeroExpediente());
	if(expediente.getNumeroExpedientes()!=null){
		for (NumeroExpediente nexp : expediente.getNumeroExpedientes()) {
			logger.info("-----------------");
			logger.info("id: "+nexp.getNumeroExpedienteId());
			logger.info("numero:"+nexp.getNumeroExpediente());
			if(nexp.getNumeroExpedientePadre()!=null)
			logger.info("padre: "+nexp.getNumeroExpedientePadre().getNumeroExpedienteId());
//			if(nexp.getNumeroExpedientes()!=null)
//			logger.info("# hijos:"+nexp.getNumeroExpedientes().size());
		}
	}
}

	public void testObtenerExpedientesPorMes () {
		
		try {
			List<Object[]> resultado = daoServcice.obtenerExpedientesPorMes(DateUtils.obtener("01/07/2011"), DateUtils.obtener("31/08/2011"));
			assertTrue("La lista debe tener minimo un registro ", resultado.size()>0);
			
			for (Object[] objects : resultado) {
				logger.info("Mes "+ objects[0] + " num " + objects[1]);
			}
		} catch (NSJPNegocioException e) {
			e.printStackTrace();
		}
	
		
	}
	
	public void testRecuperarUltimoNumeroDeExpedienteDeProcu() {
		logger.debug("Prueba para obtener todos los registros de Expediente ");

		String respuesta = daoServcice.obtenerUltimoNumeroDeExpediente(44L);

		assertNotNull("No se ha obtenido el mï¿½ximo de manera correcta",
				respuesta);
		assertTrue("No se ha obtenido el mï¿½ximo de manera correcta",
				respuesta.length() > 0);
		logger.debug("Respuesta :: " + respuesta);
	}
	
	public void testObtenerUltimoNumeroDeExpedientePoderJudicial() {

//		String cadenaBusqueda = "EJ/2014-PJ-ZAC-J01";
		String cadenaBusqueda = "CA/2013-PJ-ZAC-J01";
		Character separador = '/';
		String numeroExpediente = daoServcice
				.obtenerUltimoNumeroDeExpedientePoderJudicial(cadenaBusqueda,
						separador);
		logger.debug("Ultimo consecutivo:" + numeroExpediente);
		assertNotNull("No se ha obtenido algun valor.", numeroExpediente);
	}
	
	public void testsConsultarInstitucionActual() throws NSJPNegocioException{
		
		ConfInstitucion conf = daoServcice.consultarInsitucionActual();
		logger.debug("Clave :: " + conf.getClave());
		logger.debug("Nombre Inst :: " + conf.getNombreInst());
		logger.debug("Conf ID :: " + conf.getConfInstitucionId());
		logger.debug("Instalacion Actual? :: " + conf.getEsInstalacionActual());
	}
	
	public void testConsultarIndicador () {
		//Indicador con varias columnas 
//		Indicadores indicador = Indicadores.INDICADOR_81;
		//Indicador con una sola columna
		Indicadores indicador = Indicadores.INDICADOR_84;
		
		HashMap<String, String> valores = new HashMap<String, String>();
		valores.put("fechaIncio", "20/06/2011");
		valores.put("fechaFin", "20/06/2013");
		
		try {
			List<Object[]> resultado = daoServcice.consultarIndicador(indicador, valores);
			assertTrue("La lista debe tener minimo un registro ", resultado.size()>0);
			
			logger.info("Resultados instanceof: "+ (resultado.get(0) instanceof Object[]));
			
			//Verificar que es una lista con Arreglos de Objetos
			//Si NO es una lista, se debe de Generar una con dicho objeto
			if(! (resultado.get(0) instanceof Object[])){
				//Se sobre escribe la posicion por un arreglo con el unico objeto
				Object temp = resultado.get(0);
				resultado.remove(0);
				resultado.add(0,new Object[]{temp});
			}
			for (Object[] objects : resultado) {
				logger.info("Resultados----");
				for (int i = 0; i < objects.length; i++) {
					logger.info("Resultado:"+ objects[i]);					
				}
			}
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		}
	}
	
	
	public void testBuscadorDeExpedientes() {
	
		try {
			
			FiltroExpedienteDTO filtroExpedienteDTO = new FiltroExpedienteDTO();
			filtroExpedienteDTO.setNumeroExpediente("NSJYUCPJ0100220123334Y");
			
			List<Expediente> expedientes = daoServcice.buscadorDeExpedientes(filtroExpedienteDTO);
			for (Expediente exp:expedientes) {
				logger.info("NUMERO EXPEDIENTE:"+ exp.getNumeroExpediente());					
			}
			
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		}
	}
	
	
	public void testConsultarExpedientesCanalizados() {				
		
		FiltroExpedienteDTO filtroExpedienteDTO = new FiltroExpedienteDTO();
		
		filtroExpedienteDTO.setIdArea(new Long(Areas.UNIDAD_INVESTIGACION.ordinal()));
		filtroExpedienteDTO.setIdActividad(new Long(Actividades.ATENDER_CANALIZACION_UI.getValorId()));
		
		
		List<NumeroExpediente> respuesta = daoServcice.consultarExpedientesCanalizados(filtroExpedienteDTO);
		
		if(respuesta!=null && respuesta.size()>0){
			for (NumeroExpediente numeroExpediente : respuesta) {
				logger.info("Expediente: " + numeroExpediente.getNumeroExpediente());	
			}			
		}
		logger.info("TOTAL :::::::::: " + respuesta.size() + "::::::::::::::");
		
	}
	
	public void testConsultarExpedientesCanalizadosNoAtendidos() {				
		
		FiltroExpedienteDTO filtroExpedienteDTO = new FiltroExpedienteDTO();
		
		filtroExpedienteDTO.setIdArea(new Long(Areas.UNIDAD_INVESTIGACION.ordinal()));
		
		filtroExpedienteDTO.setIdActividad(new Long(Actividades.RECIBIR_CANALIZACION_UI.getValorId()));
		filtroExpedienteDTO.setIdTipoActividadComplemento(Actividades.ATENDER_CANALIZACION_UI.getValorId());
		
				
		List<NumeroExpediente> respuesta = daoServcice.consultarExpedientesCanalizadosNoAtendidos(filtroExpedienteDTO);
					
		if(respuesta!=null && respuesta.size()>0){
			for (NumeroExpediente numeroExpediente : respuesta) {
				//logger.info("Numero Expediente ID: " + numeroExpediente.getNumeroExpedienteId());	
				logger.info("Numero Expediente: " + numeroExpediente.getNumeroExpediente());	
			}			
		}
		logger.info("TOTAL :::::::::: " + respuesta.size() + "::::::::::::::");
				
	}
	
	public void testConsultarExpedientesRSPorNumeroExpediente() {				
		
		FiltroExpedienteDTO filtroExpedienteDTO = new FiltroExpedienteDTO();
		
		filtroExpedienteDTO.setNumeroExpediente("NSJZACSP0100020123333C");
		AreaDTO areaActual = new AreaDTO();
		areaActual.setAreaId(61L);
		
		UsuarioDTO usuario = new UsuarioDTO();
		usuario.setAreaActual(areaActual);
		filtroExpedienteDTO.setUsuario(usuario);
		
		List<Long> estatusExpediente = new ArrayList<Long>();
		estatusExpediente.add(2488L);
		estatusExpediente.add(2100L);
		
		filtroExpedienteDTO.setEstatusNumeroExpediente(estatusExpediente);		
				
		List<Expediente> respuesta;
		try {
			respuesta = daoServcice.consultarExpedientesRSPorNumeroExpediente(filtroExpedienteDTO);
			if(respuesta!=null && !respuesta.isEmpty()){
				for (Expediente expediente : respuesta) {
					//logger.info("Numero Expediente ID: " + numeroExpediente.getNumeroExpedienteId());	
					logger.info("Id Expediente: " + expediente.getExpedienteId());	
				}			
			}
			logger.info("TOTAL :::::::::: " + respuesta.size() + "::::::::::::::");
		} catch (NSJPNegocioException e) {
			e.printStackTrace();
		}				
	}
	
	public void testConsultarExpedientePorNumeroDeCaso() {		
		
		CasoDTO casoDto = new CasoDTO();
		casoDto.setNumeroGeneralCaso("ZAC/FG/XX/PGU/2012/AA-00669");
		
		UsuarioDTO usuarioDto = new UsuarioDTO();
		FuncionarioDTO funcionarioDto = new FuncionarioDTO();
		CatDiscriminanteDTO discriminanteDto = new CatDiscriminanteDTO();
		discriminanteDto.setCatDiscriminanteId(3L);
		discriminanteDto.setDistrito(new CatDistritoDTO(1L));
		funcionarioDto.setDiscriminante(discriminanteDto);
		usuarioDto.setFuncionario(funcionarioDto);
		
		try {
			Expediente expRecuperado= daoServcice.consultarExpedientePorNumeroDeCaso(casoDto, usuarioDto);
			
			logger.info("EXPEDIENTE RECUPERADO :::::::::::"+expRecuperado);
			
		} catch (NSJPNegocioException e) {
			e.printStackTrace();
		}
	}
	
	public void testEsExpedienteCanalizadoYAtendido(){
		Long idNumeroExpediente = 3810L;
		int resp = daoServcice.obtenDetalleDeCanalizacionDeNumeroExpediente(idNumeroExpediente);
		if(resp == 0){
			logger.info("EXPEDIENTE SIN CANALIZACIONES");
		}else{
			if(resp == 1){
				logger.info("EL EXPEDIENTE NO HA SIDO ASIGNADO");
			}else{
				logger.info("EL EXPEDIENTE FUE ASIGNADO");
			}
		}
		
	}
	
	public void testConsultarEstatusDeExpedientesDiferentes(){
		List<Valor> resultado = daoServcice.consultarEstatusDeExpedientesDiferentes();
		for (Valor loRegistro : resultado) {
			logger.info(loRegistro.getValorId());
			logger.info(loRegistro.getValor());
		}
	}

	public void testConsultarExpedientesPorEtapaDefensoria() {				
		
		Long etapaValorId = 7579L;
		Long usuarioId = 78L;
		Long areaId = null;

		List<NumeroExpediente> respuesta = daoServcice.consultarExpedientesPorEtapaDefensoria(etapaValorId, usuarioId, areaId );
		assertFalse("La lista no puede ser vacia!", respuesta.isEmpty());
		if(respuesta!=null && respuesta.size()>0){
			for (NumeroExpediente numeroExpediente : respuesta) {
				logger.info("Expediente: " + numeroExpediente.getNumeroExpediente());	
			}			
		}
		logger.info("TOTAL :::::::::: " + respuesta.size() + "::::::::::::::");
		
	}
	
	
	
	public void testBuscadorDeExpedientesAReasignarPM() {				
		
		FiltroExpedienteDTO filtroExpedienteDTO = new FiltroExpedienteDTO();
		
		filtroExpedienteDTO.setNumeroExpediente("GENERAL");
		FuncionarioDTO loFuncionarioDTO = new FuncionarioDTO(0L);
		CatDiscriminanteDTO loCatDiscriminanteDTO = new CatDiscriminanteDTO();
		loCatDiscriminanteDTO.setDistrito(new CatDistritoDTO(1L));
		loFuncionarioDTO.setDiscriminante(loCatDiscriminanteDTO);
		
		UsuarioDTO usuario = new UsuarioDTO();
		usuario.setFuncionario(loFuncionarioDTO);		
		filtroExpedienteDTO.setUsuario(usuario);
		
		List<NumeroExpediente> respuesta;
		respuesta = daoServcice.buscadorDeExpedientesAReasignarPM(filtroExpedienteDTO);
		if(respuesta!=null && !respuesta.isEmpty()){
			for (NumeroExpediente numExpediente : respuesta) {
//				logger.info("Numero de caso: " + numExpediente.getExpediente().getCaso().getNumeroGeneralCaso());	
//				logger.info("Numero Expediente ID: " + numExpediente.getNumeroExpedienteId());	
				logger.info("Numero de expediente: " + numExpediente.getNumeroExpediente());	
//				logger.info("Estatus del numero de expediente: " + numExpediente.getEstatus().getValor());	
//				logger.info("Nombre del responsable: " + numExpediente.getFuncionario().getNombreCompleto());	
//				logger.info("Delito Principal: " + numExpediente.getExpediente().getDelitos());	
			}			
		}
		logger.info("TOTAL :::::::::: " + respuesta.size() + "::::::::::::::");				
	}
	
public void testBuscadorDeExpedientesAReasignarPMQueryNativo() {
		PaginacionDTO loPaginacionDTO = new PaginacionDTO();
		loPaginacionDTO.setCampoOrd("1");
		loPaginacionDTO.setDirOrd("asc");
		
		PaginacionThreadHolder.set(loPaginacionDTO);
		FiltroExpedienteDTO filtroExpedienteDTO = new FiltroExpedienteDTO();
		
		filtroExpedienteDTO.setNumeroExpediente("GENERAL");
		FuncionarioDTO loFuncionarioDTO = new FuncionarioDTO(0L);
		CatDiscriminanteDTO loCatDiscriminanteDTO = new CatDiscriminanteDTO();
		loCatDiscriminanteDTO.setDistrito(new CatDistritoDTO(1L));
		loFuncionarioDTO.setDiscriminante(loCatDiscriminanteDTO);
		
		UsuarioDTO usuario = new UsuarioDTO();
		usuario.setFuncionario(loFuncionarioDTO);		
		filtroExpedienteDTO.setUsuario(usuario);
		
		List<Long> respuesta;
		respuesta = daoServcice.buscadorDeExpedientesAReasignarPMQueryNativo(filtroExpedienteDTO,Boolean.TRUE);
		if(respuesta!=null && !respuesta.isEmpty()){
			for (Long numExpediente : respuesta) {
				logger.info("Numero de expediente: " + numExpediente);	
			}			
		}
		logger.info("TOTAL :::::::::: " + respuesta.size() + "::::::::::::::");				
	}


public void testConsultarExpedientesActividadAreaAnioJarAsignados(){
	FiltroExpedienteDTO filtroExpedienteDTO = new FiltroExpedienteDTO();
	filtroExpedienteDTO.setIdDiscriminante(1L);
	filtroExpedienteDTO.setestatusMenuCoorJAR(2L);
	List<NumeroExpediente>  lista = daoServcice.consultarExpedientesActividadAreaJarAsignados(filtroExpedienteDTO);
	for (NumeroExpediente elemento : lista) {
		logger.info("Expediente ID" + elemento.getExpediente().getExpedienteId());		
		logger.info("Numero de expediente ID" + elemento.getNumeroExpedienteId());				
	}
	logger.info("tamaño: " + lista.size());	
}


public void testConsultaCiudadadana(){
	String nombre = "gamasoft";
	String apaterno = "";
	String amaterno = "";
	String expediente = "00008";
	List<ExpedienteViewDTO> respuesta = daoServcice.consultaCiudadana(apaterno, amaterno, nombre, expediente);
	
	for (ExpedienteViewDTO expedienteViewDTO : respuesta) {
		logger.info("Numero de caso: " + expedienteViewDTO.getNumeroGeneralCaso());
		logger.info("Numero de expediente: " + expedienteViewDTO.getNumeroExpediente());
		logger.info("Victima: " + expedienteViewDTO.getVictima());
		logger.info("Delito: " + expedienteViewDTO.getDelito());
		logger.info("Funcionario: " + expedienteViewDTO.getNombreFuncionario());
		logger.info("Area: " + expedienteViewDTO.getArea());
		logger.info("Estatus: " + expedienteViewDTO.getEstatus());
	}
	logger.info("El tamñano de la lista es: " + respuesta.size());
	
}


}
