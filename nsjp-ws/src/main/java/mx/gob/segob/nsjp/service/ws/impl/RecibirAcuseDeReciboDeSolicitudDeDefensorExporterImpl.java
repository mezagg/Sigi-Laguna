/**
 * Nombre del Programa	: RecibirAcuseDeReciboDeSolicitudDeDefensorExporterImpl.java
 * Autor				: AlejandroGA
 * Compania             : Ultrasist
 * Proyecto             : NSJP                    Fecha: 28 Nov 2012
 * Marca de cambio      : N/A
 * Descripcion General  : Implementacion para exponer los servicios como web service
 * Programa Dependiente	: N/A
 * Programa Subsecuente	: N/A
 * Cond. de ejecucion   : N/A
 * Dias de ejecucion    : N/A                             Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                : N/A
 * Compania             : N/A
 * Proyecto             : N/A                                 Fecha: N/A
 * Modificacion         : N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.service.ws.impl;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.documento.DocumentoWSDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudWSDTO;
import mx.gob.segob.nsjp.service.documento.RecibirAcuseDeReciboDeSolicitudDeDefensorService;
import mx.gob.segob.nsjp.service.ws.RecibirAcuseDeReciboDeSolicitudDeDefensorExporter;
import mx.gob.segob.nsjp.service.ws.infra.impl.ApplicationContextAwareWSNSJPImpl;

/**
 * Implementacion para exponer los servicios como interfaz
 * @author AlejandroGA
 * @version 1.0
 */

@WebService(endpointInterface = "mx.gob.segob.nsjp.service.ws.RecibirAcuseDeReciboDeSolicitudDeDefensorExporter")
public class RecibirAcuseDeReciboDeSolicitudDeDefensorExporterImpl implements
		RecibirAcuseDeReciboDeSolicitudDeDefensorExporter {

	private final static Logger logger = Logger.getLogger(RecibirAcuseDeReciboDeSolicitudDeDefensorExporterImpl.class);
	
	private RecibirAcuseDeReciboDeSolicitudDeDefensorService recibirAcuseDeReciboDeSolicitudDeDefensorService;
	
	@Override
	public DocumentoWSDTO recibirAcuseDeReciboDeSolicitudDeDefensor(
			SolicitudWSDTO solicitudWsDto, DocumentoWSDTO documentoWsDto)
			throws NSJPNegocioException {
		
		logger.debug("RecibirAcuseDeReciboDeSolicitudDeDefensorExporterImpl - recibirAcuseDeReciboDeSolicitudDeDefensor");
		logger.debug("Parametros- solicitudWsDto="+solicitudWsDto);
		logger.debug("Parametros- documentoWsDto="+documentoWsDto);
		
		recibirAcuseDeReciboDeSolicitudDeDefensorService = (RecibirAcuseDeReciboDeSolicitudDeDefensorService) ApplicationContextAwareWSNSJPImpl.springApplicationContext.getBean(
        "recibirAcuseDeReciboDeSolicitudDeDefensorService");
		
		return recibirAcuseDeReciboDeSolicitudDeDefensorService.recibirAcuseDeReciboDeSolicitudDeDefensor(solicitudWsDto, documentoWsDto);
	}

}
