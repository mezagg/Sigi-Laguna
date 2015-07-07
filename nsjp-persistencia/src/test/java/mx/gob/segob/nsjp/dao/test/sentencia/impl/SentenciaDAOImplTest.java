/**
* Nombre del Programa : SentenciaDAOImplTest.java
* Autor                            : AntonioBV
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 26/01/2012
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
package mx.gob.segob.nsjp.dao.test.sentencia.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.sentencia.SentenciaDAO;
import mx.gob.segob.nsjp.dao.test.base.BaseTestPersistencia;
import mx.gob.segob.nsjp.model.Expediente;
import mx.gob.segob.nsjp.model.NombreDemografico;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.model.Sentencia;
import mx.gob.segob.nsjp.model.Solicitud;
import mx.gob.segob.nsjp.model.Valor;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author AntonioBV
 *
 */
public class SentenciaDAOImplTest  extends BaseTestPersistencia<SentenciaDAO> {
    	
	public void testConsultarSentenciaPorId() {
    	
    	Sentencia sentencia = new Sentencia();
    	NumeroExpediente numExp = new NumeroExpediente();
    	numExp.setNumeroExpediente("NSJZACSP01000201233339");
    	sentencia.setNumeroExpediente(numExp);
    	sentencia.setSentenciaId(5L);
//    	sentencia.setBcumplida(null);
    	sentencia.setCnumeroCausa("NSJZACPJ010022012333AN");
		try {
			sentencia = daoServcice.consultarSentencia(sentencia);
			logger.info("Identificador de la sentencia: " + sentencia.getSentenciaId());
			logger.info("Identificador del Numero de expediente: " +sentencia.getNumeroExpediente().getNumeroExpedienteId());
			logger.info("NUS: "+ sentencia.getCnus());
		} catch (Exception e) {
			e.printStackTrace();
		}
        assertNotNull(sentencia.getBcumplida());
    }	

	public void testConsultarNUS() {
    	
		
		NombreDemografico nombreDemografico = new NombreDemografico();
		nombreDemografico.setCurp("DETF930401HMCXLL");	
		List<Sentencia> lstSentencia = null;
		try {
			lstSentencia = daoServcice.consultarNUS(nombreDemografico, true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
        assertNotNull(lstSentencia);
        assertTrue(lstSentencia.isEmpty());
    }
	
	public void testConsultarSentenciaPorNusYCausa(){
		Sentencia sentencia = new Sentencia();
		sentencia.setCnumeroCausa("NSJZACPJ0100220123334H");
		sentencia.setCnus("3444");
		try {
			Sentencia sentenciaBD = daoServcice.consultarSentenciaPorNusYCausa(sentencia);
			logger.info("Id de la sentencia: "+ sentenciaBD.getSentenciaId());
			logger.info("Numero de causa: "+ sentenciaBD.getCnumeroCausa());
			logger.info("NUS: "+sentenciaBD.getCnus());
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public void testConsultarSentenciasJEJ(){
		Sentencia s = new Sentencia();
		NumeroExpediente numExp = new NumeroExpediente();
		Valor estatusNumExp = new Valor(2488L);
		Set<Solicitud> solicitudes = new HashSet<Solicitud>();
		Solicitud sol = new Solicitud();
		Valor estatusSolicitud = new Valor(0L);
		sol.setEstatus(estatusSolicitud);
		solicitudes.add(sol);
		numExp.setEstatus(estatusNumExp);
		numExp.setSolicituds(solicitudes);
		s.setNumeroExpediente(numExp);
		try {
			List<Sentencia> sentenciasBD = daoServcice.consultarSentenciasJEJ(s);
			if (sentenciasBD != null 
					&& !sentenciasBD.isEmpty()){
				for (Sentencia sen : sentenciasBD){
					logger.info("Id de la sentencia: "+sen.getSentenciaId());
					logger.info("Id del numero de expediente: "+sen.getNumeroExpediente().getNumeroExpedienteId());
					logger.info("Estatus del numero de expediente: " + sen.getNumeroExpediente().getEstatus());
				}
			}
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public void testConsultarSentenciasXEstado(){
		Sentencia s = new Sentencia();
		NumeroExpediente numExp = new NumeroExpediente();
		Expediente exp = new Expediente();
		Valor estatusNumExp = new Valor(2488L);
		exp.setEstatus(estatusNumExp);
		numExp.setExpediente(exp);
		numExp.setEstatus(estatusNumExp);
		s.setNumeroExpediente(numExp);
		try {
			List<Sentencia> sentenciasBD = daoServcice.consultarSentenciasXEstado(s);
			if (sentenciasBD != null 
					&& !sentenciasBD.isEmpty()){
				for (Sentencia sen : sentenciasBD){
					logger.info("Id de la sentencia: "+sen.getSentenciaId());
					logger.info("Id del numero de expediente: "+sen.getNumeroExpediente().getNumeroExpedienteId());
					logger.info("Estatus del numero de expediente: " + sen.getNumeroExpediente().getEstatus());
				}
			}
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public void testConsultarSentenciasPorNumeroExpediente(){
		List<Long> numerosExpediente = new ArrayList<Long>();
		numerosExpediente.add(319L);
		numerosExpediente.add(323L);
		numerosExpediente.add(324L);
		numerosExpediente.add(360L);
		numerosExpediente.add(361L);
		numerosExpediente.add(364L);
		numerosExpediente.add(368L);
		numerosExpediente.add(397L);
		numerosExpediente.add(398L);
		try {
			List<Sentencia> sentenciasBD = daoServcice.consultarSentenciasPorIdsNumExp(numerosExpediente);
			if (sentenciasBD != null 
					&& !sentenciasBD.isEmpty()){
				logger.info("Numero de resultados: "+sentenciasBD.size());
				for (Sentencia sen : sentenciasBD){
					logger.info("Id de la sentencia: "+sen.getSentenciaId());
					logger.info("Id del numero de expediente: "+sen.getNumeroExpediente().getNumeroExpedienteId());
					logger.info("Estatus del numero de expediente: " + sen.getNumeroExpediente().getEstatus());
				}
			}
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage(), e);
		}
	}
}

