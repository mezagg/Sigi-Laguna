/**
* Nombre del Programa : JAVSClienteServiceImpl.java
* Autor                            : GustavoBP
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 8 Nov 2011
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
package mx.gob.segob.nsjp.service.infra.impl;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;

import mx.gob.segob.nsjp.comun.constants.ConstantesGenerales;
import mx.gob.segob.nsjp.comun.enums.configuracion.Parametros;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.parametro.ParametroDAO;
import mx.gob.segob.nsjp.ws.cliente.agendaraudienciajavs.Principal;
import mx.gob.segob.nsjp.ws.cliente.agendaraudienciajavs.PrincipalSoap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementaci�n del cliente del ws.
 * 
 * @version 1.0
 * @author GustavoBP
 *
 */
@Service
@Transactional
public class JAVSClienteServiceImpl implements
		mx.gob.segob.nsjp.service.infra.JAVSClienteService {
	/**
     * Logger.
     */
    private final static Logger logger = Logger
            .getLogger(JAVSClienteServiceImpl.class);
          
    @Autowired
    private ParametroDAO parametroDAO;
    
	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.infra.JAVSClienteService#agendarAudiencia(java.lang.Long)
	 */
	@Override
	public Long agendarAudiencia(Long idAudiencia, String claveUsuario) throws NSJPNegocioException {
		 	
		logger.debug("JAVSClienteService - agendarAudiencia ");
		logger.debug("idAudiencia = " + idAudiencia);
		logger.debug("claveUsuario = " + claveUsuario);

		URL ep;
		long idEvento =0L;
		if(idAudiencia==null || idAudiencia<0 || claveUsuario==null || claveUsuario.trim().isEmpty())
			return idEvento = (long) ConstantesGenerales.FALLO_CONEXION_WEB_SERVICE_JAVS;
		
		try {
			String rutaJAVS = 
				parametroDAO.obtenerPorClave(Parametros.RUTA_JAVS).getValor();
			
			logger.info("****************** AGENDAR AUDIENCIA -> JAVSClienteServiceImpl *********************");
			logger.info(" Ruta:"+ rutaJAVS);
			logger.info("De no presentarse una l�nea con un 'Evento', quiere decir que fall� en la conexi�n :(");
			
			ep = new URL(
						rutaJAVS 
						+ "/Principal.asmx?wsdl");  
			//Los nombres se obtienen de PrincipalSoap_PrincipalSoap_Client generado
			final QName SERVICE_NAME = new QName("http://tempuri.org/", "Principal");
			
			// Configuracion para acceder al web service
			Principal  principal = new Principal(ep, SERVICE_NAME);
			
			PrincipalSoap proxy =  principal.getPrincipalSoap();
			
			logger.info(" Inicia la comunicacion con el WS: "+ proxy);
			idEvento = proxy.agendarAudiencia(idAudiencia.toString(), claveUsuario);
			logger.info(" Evento: "+ idEvento);
			
			return  idEvento;
		}
		catch (Exception e) {
			logger.error("JAVS EXCEPTION"+e.getMessage());
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		}
	}
	
	
	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.infra.JAVSClienteService#consultarAudiencia(java.lang.Long)
	 */
	@Override
	public Long consultarAudiencia(Long idAudiencia, String claveUsuario) throws NSJPNegocioException {
		 	
		logger.debug("JAVSClienteService - consultarAudiencia ");
		logger.debug("idAudiencia = " + idAudiencia);
		logger.debug("claveUsuario = " + claveUsuario);

		URL ep;
		long idEvento =0L;
		if(idAudiencia==null || idAudiencia<0 || claveUsuario==null || claveUsuario.trim().isEmpty())
			return idEvento;
		
		try {
			
			String rutaJAVS = 
				parametroDAO.obtenerPorClave(Parametros.RUTA_JAVS).getValor();

			logger.info("****************** CONSULTAR AUDIENCIA -> JAVSClienteServiceImpl *********************");
			logger.info(" Ruta:"+ rutaJAVS);
			logger.info("De no presentarse una l�nea con un 'Evento', quiere decir que fall� en la conexi�n :(");

			ep = new URL(
						rutaJAVS 
						+ "/Principal.asmx?wsdl");  
			//Los nombres se obtienen de PrincipalSoap_PrincipalSoap_Client generado
			final QName SERVICE_NAME = new QName("http://tempuri.org/", "Principal");
			
			// Configuracion para acceder al web service
			Principal  principal = new Principal(ep, SERVICE_NAME);
			PrincipalSoap proxy =  principal.getPrincipalSoap();
			
			logger.info(" Inicia la comunicacion con el WS: "+ proxy);
			idEvento = proxy.consultarAudiencia(idAudiencia.toString(), claveUsuario);
			logger.info(" Evento: "+ idEvento);
			
			return idEvento;
		}catch (Exception e) {
			logger.error("JAVS EXCEPTION"+e.getMessage());
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		}
	}
	
	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.infra.JAVSClienteService#eliminarAudiencia(java.lang.Long)
	 */
	@Override
	public Long eliminarAudiencia(Long idAudiencia, String claveUsuario) throws NSJPNegocioException {
		 	
		logger.debug("JAVSClienteService - eliminarAudiencia ");
		logger.debug("idAudiencia = " + idAudiencia);
		logger.debug("claveUsuario = " + claveUsuario);
		URL ep;
		Long idEvento =0L;
		if(idAudiencia==null || idAudiencia<0 || claveUsuario==null || claveUsuario.trim().isEmpty())
			return idEvento;
		
		try {
			String rutaJAVS = 
				parametroDAO.obtenerPorClave(Parametros.RUTA_JAVS).getValor();

			logger.info("****************** ELIMINAR AUDIENCIA -> JAVSClienteServiceImpl *********************");
			logger.info(" Ruta:"+ rutaJAVS);
			logger.info("De no presentarse una l�nea con un 'Evento', quiere decir que fall� en la conexi�n :(");
			
			ep = new URL(
						rutaJAVS 
						+ "/Principal.asmx?wsdl");  
			//Los nombres se obtienen de PrincipalSoap_PrincipalSoap_Client generado
			final QName SERVICE_NAME = new QName("http://tempuri.org/", "Principal");
			
			// Configuracion para acceder al web service
			Principal  principal = new Principal(ep, SERVICE_NAME);
			
			PrincipalSoap proxy =  principal.getPrincipalSoap();
			
			logger.info(" Inicia la comunicacion con el WS: "+ proxy);
			idEvento = (long) proxy.eliminarAudiencia(idAudiencia.toString(), claveUsuario);
			logger.info(" Evento: "+ idEvento);
			
			return idEvento;
		} catch (Exception e) {
			logger.error("JAVS EXCEPTION"+e.getMessage());
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		}
	}
	
	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.infra.JAVSClienteService#estadoAudiencia(java.lang.Long)
	 */
	@Override
	public Long estadoAudiencia(Long idAudiencia, String claveUsuario) throws NSJPNegocioException {
		 	
		logger.debug("JAVSClienteService - estadoAudiencia ");
		logger.debug("idAudiencia = " + idAudiencia);
		logger.debug("claveUsuario = " + claveUsuario);
		URL ep;
		Long idEvento = 0L;
		if(idAudiencia==null || idAudiencia<0 || claveUsuario==null || claveUsuario.trim().isEmpty())
			return idEvento;
		
		try {
			String rutaJAVS = 
				parametroDAO.obtenerPorClave(Parametros.RUTA_JAVS).getValor();
			
			logger.info("****************** ESTADO AUDIENCIA -> JAVSClienteServiceImpl *********************");
			logger.info(" Ruta:"+ rutaJAVS);
			logger.info("De no presentarse una l�nea con un 'Evento', quiere decir que fall� en la conexi�n :(");

			ep = new URL(
						rutaJAVS 
						+ "/Principal.asmx?wsdl");  
			//Los nombres se obtienen de PrincipalSoap_PrincipalSoap_Client generado
			final QName SERVICE_NAME = new QName("http://tempuri.org/", "Principal");
			
			// Configuracion para acceder al web service
			Principal  principal = new Principal(ep, SERVICE_NAME);
			
			PrincipalSoap proxy =  principal.getPrincipalSoap();
			
			logger.info(" Inicia la comunicacion con el WS: "+ proxy);
			idEvento = (long) proxy.estadoAudiencia(idAudiencia.toString(), claveUsuario);
			logger.info(" Evento: "+ idEvento);
			
			return idEvento;
		} catch (Exception e) {
			logger.error("JAVS EXCEPTION"+e.getMessage());
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		}
	}
	
	@Override
	public String obteniendoPathsJAVS(Long idAudiencia, String claveUsuario) throws NSJPNegocioException {
		 	
		logger.debug("JAVSClienteService - estadoAudiencia ");
		logger.debug("idAudiencia = " + idAudiencia);
		logger.debug("claveUsuario = " + claveUsuario);
		URL ep;
		String dir = "";
		if(idAudiencia==null || idAudiencia<0 || claveUsuario==null || claveUsuario.trim().isEmpty())
			return dir;
		
		try {
			String rutaJAVS = 
				parametroDAO.obtenerPorClave(Parametros.RUTA_JAVS).getValor();
			
			logger.info("****************** OBTENIENDO PATHS JAVS -> JAVSClienteServiceImpl *********************");
			logger.info(" Ruta:"+ rutaJAVS);
			logger.info("De no presentarse una l�nea con un 'Evento', quiere decir que fall� en la conexi�n :(");

			ep = new URL(
						rutaJAVS 
						+ "/Principal.asmx?wsdl");  
			//Los nombres se obtienen de PrincipalSoap_PrincipalSoap_Client generado
			final QName SERVICE_NAME = new QName("http://tempuri.org/", "Principal");
			
			// Configuracion para acceder al web service
			Principal  principal = new Principal(ep, SERVICE_NAME);
			
			PrincipalSoap proxy =  principal.getPrincipalSoap();
			
			logger.info(" Inicia la comunicacion con el WS: "+ proxy);
			dir = (String) proxy.ligasPath(idAudiencia.toString(), claveUsuario);
			logger.info(" Directorios: "+ dir);
			
			return dir;
		} catch (Exception e) {
			logger.error("JAVS EXCEPTION"+e.getMessage());
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		}
	}
	
	@Override
	public byte[] generandoRegistroMaestroJVL(Long idAudiencia, String claveUsuario) throws NSJPNegocioException {

		if(idAudiencia.equals(0L) || claveUsuario.equals("")){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		logger.debug("JAVSClienteService - estadoAudiencia ");
		logger.debug("idAudiencia = " + idAudiencia);
		logger.debug("claveUsuario = " + claveUsuario);
		URL ep;
		if(idAudiencia==null || idAudiencia<0 || claveUsuario==null || claveUsuario.trim().isEmpty())
			return null;
		
		try {
			String rutaJAVS = 
				parametroDAO.obtenerPorClave(Parametros.RUTA_JAVS).getValor();
			
			logger.info("****************** GENERANDO REGISTRO MAESTRO JVL -> JAVSClienteServiceImpl *********************");
			logger.info(" Ruta:"+ rutaJAVS);
			logger.info("De no presentarse una l�nea con un 'Evento', quiere decir que fall� en la conexi�n :(");

			ep = new URL(
						rutaJAVS 
						+ "/Principal.asmx?wsdl");  
			//Los nombres se obtienen de PrincipalSoap_PrincipalSoap_Client generado
			final QName SERVICE_NAME = new QName("http://tempuri.org/", "Principal");
			
			// Configuracion para acceder al web service
			Principal  principal = new Principal(ep, SERVICE_NAME);
			
			PrincipalSoap proxy =  principal.getPrincipalSoap();
			
			logger.info(" Inicia la comunicacion con el WS: "+ proxy);
			byte[] jvl = proxy.generandoJVL(idAudiencia.toString(), claveUsuario);
			logger.info(" Archivo JVL en bytes: "+ jvl);
			
			return jvl;
		}catch (Exception e) {
			logger.error("JAVS EXCEPTION"+e.getMessage());
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		}
	}
	

	@Override
	public String generandoRegistroMaestroJVLEnString(Long idAudiencia, String claveUsuario) throws NSJPNegocioException {

		if(idAudiencia==null || idAudiencia.equals(0L) || claveUsuario ==null || claveUsuario.trim().equals("")){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		logger.debug("JAVSClienteService - estadoAudiencia ");
		logger.debug("idAudiencia = " + idAudiencia);
		logger.debug("claveUsuario = " + claveUsuario);
		URL ep;
		if(idAudiencia==null || idAudiencia<0 || claveUsuario==null || claveUsuario.trim().isEmpty())
			return null;
		
		try {
			String rutaJAVS = 
				parametroDAO.obtenerPorClave(Parametros.RUTA_JAVS).getValor();

			logger.info("****************** GENERANDO REGISTRO MAESTRO JVL EN STRING -> JAVSClienteServiceImpl *********************");
			logger.info(" Ruta:"+ rutaJAVS);
			logger.info("De no presentarse una l�nea con un 'Evento', quiere decir que fall� en la conexi�n :(");
			
			ep = new URL(
						rutaJAVS 
						+ "/Principal.asmx?wsdl");  
			//Los nombres se obtienen de PrincipalSoap_PrincipalSoap_Client generado
			final QName SERVICE_NAME = new QName("http://tempuri.org/", "Principal");
			
			// Configuracion para acceder al web service
			Principal  principal = new Principal(ep, SERVICE_NAME);
			
			PrincipalSoap proxy =  principal.getPrincipalSoap();
			
			logger.info(" Inicia la comunicacion con el WS: "+ proxy);
			String jvl = proxy.generandoJVLCadena(idAudiencia.toString(), claveUsuario);
			logger.info(" Archivo JVL en String: "+ jvl);
			
			return jvl;
		} catch (Exception e) {
			logger.error("JAVS EXCEPTION"+e.getMessage());
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		}
	}
	
	@Override
	public String generandoConglomerado(Long idAudiencia, String claveUsuario) throws NSJPNegocioException {

		if(idAudiencia ==null || idAudiencia.equals(0L) || claveUsuario==null || claveUsuario.trim().equals("")){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		logger.debug("JAVSClienteService - estadoAudiencia ");
		logger.debug("idAudiencia = " + idAudiencia);
		logger.debug("claveUsuario = " + claveUsuario);
		URL ep;
		
		try {
			String rutaJAVS = 
				parametroDAO.obtenerPorClave(Parametros.RUTA_JAVS).getValor();
			
			logger.info("****************** GENERANDO CONGLOMERADO -> JAVSClienteServiceImpl *********************");
			logger.info(" Ruta:"+ rutaJAVS);
			logger.info("De no presentarse una l�nea con un 'Evento', quiere decir que fall� en la conexi�n :(");

			ep = new URL(
						rutaJAVS 
						+ "/Principal.asmx?wsdl");  
			//Los nombres se obtienen de PrincipalSoap_PrincipalSoap_Client generado
			final QName SERVICE_NAME = new QName("http://tempuri.org/", "Principal");
			
			// Configuracion para acceder al web service
			Principal  principal = new Principal(ep, SERVICE_NAME);
			
			PrincipalSoap proxy =  principal.getPrincipalSoap();
			
			logger.info(" Inicia la comunicacion con el WS: "+ proxy);
			String jvlPaths = proxy.generandoJVLPaths(idAudiencia.toString(), claveUsuario);
			logger.info(" Archivo JVL con paths y datos de conexi�n: "+ jvlPaths);
			
			return jvlPaths;
		} catch (Exception e) {
			logger.error("JAVS EXCEPTION"+e.getMessage());
			throw new NSJPNegocioException(CodigoError.ERROR_COMUNICACION, e);
		}
	}
}
