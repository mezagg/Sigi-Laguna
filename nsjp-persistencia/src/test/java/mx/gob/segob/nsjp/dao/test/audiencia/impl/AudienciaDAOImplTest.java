/**
 * Nombre del Programa : AudienciaDAOImplTest.java
 * Autor                            : vaguirre
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 31 May 2011
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
package mx.gob.segob.nsjp.dao.test.audiencia.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.audiencia.BandejaNotificador;
import mx.gob.segob.nsjp.comun.enums.audiencia.EstatusAudiencia;
import mx.gob.segob.nsjp.comun.enums.audiencia.TipoAudiencia;
import mx.gob.segob.nsjp.comun.enums.documento.EstatusNotificacion;
import mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud;
import mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.dao.audiencia.AudienciaDAO;
import mx.gob.segob.nsjp.dao.test.base.BaseTestPersistencia;
import mx.gob.segob.nsjp.model.Audiencia;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.model.Notificacion;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.model.Resolutivo;
import mx.gob.segob.nsjp.model.SalaAudiencia;
import mx.gob.segob.nsjp.model.Valor;

/**
 * Describir el objetivo de la clase con punto al final.
 * 
 * @version 1.0
 * @author vaguirre
 * 
 */
public class AudienciaDAOImplTest extends BaseTestPersistencia<AudienciaDAO> {
	
	public void testconsultarAudienciasDeSolicitudTranscripcion() throws NSJPNegocioException{
		
		Calendar c1 = Calendar.getInstance(); 
		Calendar c2 = Calendar.getInstance();
	 
		c1.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		c2.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:MM:SS");
		
		System.out.println("PRIMER DIA DE ESTA SEMANA="+df.format(c1.getTime()));
		System.out.println("ULTIMO DIA DE ESTA SEMANA="+df.format(c2.getTime()));
		
		System.out.println("PRIMER DIA DE ESTA SEMANA EN DATE="+DateUtils.obtener(df.format(c1.getTime())));
		System.out.println("ULTIMO DIA DE ESTA SEMANA EN DATE="+DateUtils.obtener(df.format(c2.getTime())));
		
		
		List<Audiencia> solicitudes =  daoServcice.consultarAudienciasDeSolicitudAudiencia(295L, 1L, 
				null,DateUtils.obtener(df.format(c1.getTime())) ,DateUtils.obtener(df.format(c2.getTime())) , true, null, false, null, 1L);
		
		logger.info("solicitudes.size():" + solicitudes.size());		
		if(solicitudes!=null && solicitudes.size()>0){			
			for (Audiencia audiencia : solicitudes) {
				if(audiencia!=null && audiencia.getAudienciaId()!=null){
					logger.info("audiencia.getAudienciaId():" + audiencia.getAudienciaId());
				}
			}
		}			 
	}

	/**
     * 
     */
    public void testConsultarAudienciasNuevo() {
        List<Audiencia> data = daoServcice
                .consultarAudienciasPendientesNotificacion(BandejaNotificador.NUEVO);
        assertNotNull("La list ano puede ser null", data);
        assertTrue("La lista debe estar vac�a", data.isEmpty());
        logger.debug("data.size() :: " + data.size());
    }
    /**
     * 
     */
    public void testConsultarAudienciasSeguimiento() {
        List<Audiencia> data = daoServcice
                .consultarAudienciasPendientesNotificacion(BandejaNotificador.SEGUIMIENTO);
        assertNotNull("La list ano puede ser null", data);
        assertFalse("La lista no dbe etar vac�a", data.isEmpty());
        logger.debug("data.size() :: " + data.size());
    }
    /**
     * 
     */
    public void testConsultarAudienciasExpediente() {
        List<Audiencia> data = daoServcice
                .consultarAudienciasporExpediente(160L);
        assertNotNull("La list ano puede ser null", data);
        logger.debug("data.size() :: " + data.size());
        assertFalse("La lista no debe estar vac�a", data.isEmpty());
    }

    public void testCreateImputacion() {
        Audiencia au = new Audiencia();
        au.setConsecutivo(Short.decode("3"));
        au.setTipo(new Valor(TipoAudiencia.IMPUTACION.getValorId()));
        Calendar cal = Calendar.getInstance();
//        cal.set(Calendar.MONTH, 7);
        cal.add(Calendar.DATE, 1);
        cal.set(Calendar.HOUR, 14);
        cal.set(Calendar.MINUTE, 0);
        au.setNumeroExpediente(new NumeroExpediente(2L));
        au.setFechaAudiencia(cal.getTime());
        au.setSalaAudiencia(new SalaAudiencia(1L));
        au.setFechaAsignacionSala(new Date());
        au.setEstatus(new Valor(EstatusAudiencia.SOLICITADA.getValorId()));
        Long id = daoServcice.create(au);
        assertNotNull("No puede ser nulo el ID", id);
    }
    
    
    public void testCreateJuicioOral() {
        int pos =0;
        while (pos<60) {
        Audiencia au = new Audiencia();
        au.setConsecutivo(Short.decode("4"));
        au.setTipo(new Valor(TipoAudiencia.VINCULACION.getValorId()));
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 2);
        cal.set(Calendar.HOUR_OF_DAY, 10);
        cal.set(Calendar.MINUTE, 0);
        au.setNumeroExpediente(new NumeroExpediente(2L));
//        au.setFechaAudiencia(cal.getTime());
        au.setEstatus(new Valor(EstatusAudiencia.SOLICITADA.getValorId()));
        Long id = daoServcice.create(au);
        assertNotNull("No puede ser nulo el ID", id);
        pos++;}
    }
    
    public void testCreateJuicioOralAgendada() {
        Audiencia au = new Audiencia();
        au.setConsecutivo(Short.decode("4"));
        au.setTipo(new Valor(TipoAudiencia.JUICIO_ORAL.getValorId()));
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        cal.set(Calendar.HOUR_OF_DAY, 13);
        cal.set(Calendar.MINUTE, 0);
        au.setNumeroExpediente(new NumeroExpediente(18L));
        au.setFechaAudiencia(cal.getTime());
        au.setEstatus(new Valor(EstatusAudiencia.SOLICITADA.getValorId()));
        au.setFechaAsignacionSala(new Date());
        au.setDuracionEstimada(30);
        au.setSalaAudiencia(new SalaAudiencia(1L));
        Long id = daoServcice.create(au);
        assertNotNull("No puede ser nulo el ID", id);
    }    
    
    public void testCreateImputacionPAsada() {
        int pos = 0;
        while (pos<7){
        Audiencia au = new Audiencia();
        au.setConsecutivo(Short.decode("4"));
        au.setTipo(new Valor(TipoAudiencia.IMPUTACION.getValorId()));
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        cal.set(Calendar.HOUR_OF_DAY, 14);
        cal.set(Calendar.MINUTE, 30);
        au.setNumeroExpediente(new NumeroExpediente(1L));
        au.setFechaAudiencia(cal.getTime());
        au.setEstatus(new Valor(EstatusAudiencia.FINALIZADA.getValorId()));
        au.setFechaAsignacionSala(new Date());
        au.setDuracionEstimada(30);
        au.setSalaAudiencia(new SalaAudiencia(1L));
        Long id = daoServcice.create(au);
        assertNotNull("No puede ser nulo el ID", id);
        pos++;}
    }      
    
    public void testCreateCateo() {
        Audiencia au = new Audiencia();
        au.setConsecutivo(Short.decode("1"));
        au.setTipo(new Valor(TipoAudiencia.CATEO.getValorId()));
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, 9);
        au.setFechaAudiencia(cal.getTime());
        au.setNumeroExpediente(new NumeroExpediente(5L));
        au.setEstatus(new Valor(EstatusAudiencia.SOLICITADA.getValorId()));
        Long id = daoServcice.create(au);
        assertNotNull("No puede ser nulo el ID", id);
    }
    
    public void testConsultarAudienciasPorEstatus(){
    	EstatusAudiencia estatus=EstatusAudiencia.FINALIZADA;
    	
		List<Audiencia> audiencias = daoServcice.consultarAudienciasPorEstatus(estatus.getValorId());
		logger.debug("Existen "+audiencias.size()+" audiencias");
		for (Audiencia aud : audiencias) {
			logger.debug("--------------------------------------------");
			logger.debug("Identificador de la audiencia: "+aud.getAudienciaId());
			logger.debug("fecha y hora de fin audiencia: "+aud.getFechaAudiencia());
			logger.debug("N�mero de causa: "+aud.getNumeroExpediente().getNumeroExpedienteId());
			logger.debug("N�mero de TOCA: "+aud.getNumeroExpediente().getNumeroExpedientePadre().getNumeroExpedienteId());
		}
    }
    
    public void testConsultarResolutivosAudiencia(){
    	Long idAudiencia = 14L;
		List<Resolutivo> resolutivos = daoServcice.consultarResolutivosAudiencia(idAudiencia);
		
		logger.info("Existen "+resolutivos.size()+" resolutivos para la audiencia "+ resolutivos.get(0).getAudiencia().getAudienciaId());
		for (Resolutivo res : resolutivos) {
			logger.info("----------------------------------------------");
			logger.info("Id Resolutivo: "+res.getResolutivoId());
			logger.info("Detalle: "+res.getDetalle());			
		}
    }
    
    public void testBuscarAudienciaByCaso() {
    	List<Audiencia> respuesta = new ArrayList<Audiencia>();
    	respuesta = daoServcice.buscarAudienciaByCausa("NSJZACPJ0100220123333H",2L);
    	assertTrue("La lista debe regresar minimo una audiencia : ", respuesta.size()>0);
    	logger.info("La lista debe regresar minimo una audiencia : " + respuesta.size());
    	for(Audiencia aud :respuesta){
    		logger.info("Audiencia Fecha: " + aud.getFechaAudiencia());
    	}
    }
    
    public void consultarAudienciasByFechasYEstatus() {
    	List<Audiencia> respuesta = new ArrayList<Audiencia>();
		try {
			respuesta = daoServcice.consultarAudienciasByFechasYEstatus(EstatusAudiencia.PROGRAMADA.getValorId(),
										DateUtils.obtener("05/08/2011"), DateUtils.obtener("26/08/2011"));
			assertTrue("La lista debe tener minimo un registro ", respuesta.size()>0);
	    	logger.info("La lista debe tener minimo un registro :: "+ respuesta.size());
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage());
		}
    	
    }
    /**
     * Prueba unitaria para el query de consulta de audiencias con al menos una solicitud asociada en cierto
     * estado y de cierto tipo
     * @author Emigdio Hern�ndez
     */
    public void testConsultarAudienciasConSolicitudesPorTipoYEstado(){
    	
    	
    	
    	List<Audiencia> audiencias = daoServcice.consultarAudienciasConSolicitudesPorTipoYEstado(new TiposSolicitudes[]
    	                                                           {TiposSolicitudes.AUDIO_VIDEO,TiposSolicitudes.TRANSCRIPCION_DE_AUDIENCIA} , 
    			new EstatusSolicitud[]{EstatusSolicitud.ABIERTA},1L);
    	logger.debug("Audiencias: " +audiencias.size());
    }
    
    public void testObtenerAudienciasJudicializadasPorMes () {
    	try {
			List<Object[]> respuesta = daoServcice.obtenerAudienciasJudicializadasPorMes(DateUtils.obtener("01/07/2011"), DateUtils.obtener("31/08/2011"));
			
			assertTrue("La lista debe tener minimo un registro", respuesta.size()>0);
			logger.info("La lista debe tener minimo un registro" + respuesta.size());
			for (Object[] objects : respuesta) {
				logger.info("Mes "+objects[0]+" Total "+ objects[1]);
			}
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage());
		}
    }
    
    public void testObtenerUltimoFolioAudiencia(){
    	String ultimoFolio = daoServcice.obtenerUltimoFolioAudiencia();
    	logger.debug("ultimoFolio:"+ ultimoFolio);
    }
    
    public void testObtnerAudienciaByFolio () {
    	Audiencia respuesta = daoServcice.obtnerAudienciaByFolio("PJ/201100001");
    	
    	assertNotNull(respuesta);
    	logger.info("-------------------------");
    	logger.info("Audiencia ID :: "+respuesta.getAudienciaId());
    	logger.info("-------------------------");
    }

    public void testObtenerFuncionarioDeNumExpedienteDeAudiencia(){
    	
    	Long audienciaId = 124L;
		Funcionario fun = daoServcice.obtenerFuncionarioDeNumExpedienteDeAudiencia(audienciaId );
		assertNotNull(fun);
    	logger.info(" Funcinario: "+ fun.getClaveFuncionario());
    }
    
    public void testBuscarAudiencias(){
    	Date fechaActual = new Date();
    	
    	Calendar cal = Calendar.getInstance();
    	cal.set(2012, 2, 27, 9, 0, 0);
    	
    	Date fechaInicio = cal.getTime();	
    	
    	List<Audiencia> audiencias = daoServcice
		.buscarAudiencias(null, null, fechaInicio,fechaActual, true, null, false,null,1L,null);
    	assertTrue("La lista debe tener minimo un registro", audiencias.size()>0);
		logger.info("La lista debe tener minimo un registro" + audiencias.size());
    	logger.info("Total de Registros:"+ audiencias);
    	for (Audiencia audiencia : audiencias) {
    		logger.info("ID:"+ audiencia.getAudienciaId());
    		logger.info("fecha audiencia:"+ audiencia.getFechaAudiencia());
		}
    }
    
    public void testConsultarAudienciasByFechaAudienciaYSala(){
    	
    	Calendar cal = Calendar.getInstance();
    	
    	cal.set(2012, 2, 27, 9, 0, 0);
    	
    	//Date fechaInicio = new Date();
    	Date fechaInicio = cal.getTime();	
    	Date fechaFin = DateUtils.sumarMinutos(fechaInicio,60);
    	Boolean cierto = daoServcice.consultarAudienciasByFechaAudienciaYSala(fechaInicio,fechaFin, 3L);
    	logger.info("Se puede guardar audiencia?????:"+ cierto);
    }
    
    public void testConsultarAudienciasPorEstatusNotificacion(){
    	
    	Notificacion notificacion = new Notificacion();
    	
    	Valor estatus = new Valor();
    	estatus.setValorId(EstatusNotificacion.NO_ATENDIDA.getValorId());
    	notificacion.setEstatus(estatus);
    	
    	List<Audiencia> audiencias = daoServcice.consultarAudienciasPorEstatusNotificacion(notificacion);
    	
    	logger.info("Total de Registros:"+ audiencias);
    	
    	StringBuilder sB = new StringBuilder();
    	for (Audiencia audiencia : audiencias) {
    		//logger.info("ID:"+ audiencia.getTipo().getValor());
    		sB.append(audiencia.getAudienciaId()+",");
		}
    	logger.info("ID AUDIENCIAS:"+ sB.toString());
    }
    
    /**
     * Prueba unitaria para verificar si existen audiencias con dfechaSala
     * asociadas el numero de expediente 
     */
    public void testsExistenAudienciasConFechaAudienciaPorNumeroExpedienteId(){
		
    	Boolean respuesta = daoServcice.existenAudienciasConFechaAudienciaPorNumeroExpedienteId(679L);
    	
    	logger.info("RESPUESTA:"+ respuesta);
    	assertFalse(respuesta);
	}
}
