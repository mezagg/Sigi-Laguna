/**
* Nombre del Programa 		: ActualizacionSentenciaExporterImpl.java
* Autor 					: EdgarAT
* Compania 					: Ultrasist
* Proyecto 					: NSJP 								Fecha: 20/12/2012
* Marca de cambio 			: N/A
* Descripcion General 		: Describir el objetivo de la clase de manera breve
* Programa Dependiente 		: N/A
* Programa Subsecuente 		: N/A
* Cond. de ejecucion 		: N/A
* Dias de ejecucion 		: N/A 								Horario: N/A
*                              MODIFICACIONES
*------------------------------------------------------------------------------
* Autor 					: N/A
* Compania 					: N/A
* Proyecto 					: N/A 								Fecha: N/A
* Modificacion 				: N/A
*------------------------------------------------------------------------------
*/
package mx.gob.segob.nsjp.service.ws.impl;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.apache.log4j.Logger;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.sentencia.SentenciaWSDTO;
import mx.gob.segob.nsjp.service.sentencia.ActualizacionSentenciaService;
import mx.gob.segob.nsjp.service.ws.ActualizacionSentenciaExporter;
import mx.gob.segob.nsjp.service.ws.infra.impl.ApplicationContextAwareWSNSJPImpl;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author EdgarAT
 *
 */
@WebService(endpointInterface = "mx.gob.segob.nsjp.service.ws.ActualizacionSentenciaExporter")
public class ActualizacionSentenciaExporterImpl implements
		ActualizacionSentenciaExporter {
	
	/**
     * Logger.
     */
    private final static Logger LOGGER = Logger
            .getLogger(ActualizacionSentenciaExporterImpl.class);

    /**
     * Spring bean
     */
    private ActualizacionSentenciaService service;

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.sentencia.ActualizacionSentenciaService#actualizarSentencia(mx.gob.segob.nsjp.dto.sentencia.SentenciaWSDTO)
	 */
	@Override
	@WebMethod
	public void actualizarSentencia(SentenciaWSDTO sentenciaWSDTO)
			throws NSJPNegocioException {
		LOGGER.info("Inicia - actualizacionSentenciaService");
        
		service = (ActualizacionSentenciaService) ApplicationContextAwareWSNSJPImpl.springApplicationContext
                .getBean("actualizacionSentenciaService");
		
        service.actualizarSentencia(sentenciaWSDTO);
	}

}
