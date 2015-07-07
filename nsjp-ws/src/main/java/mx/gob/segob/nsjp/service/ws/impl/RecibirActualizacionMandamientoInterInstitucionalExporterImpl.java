/**
 * Nombre del Programa	: RecibirActualizacionMandamientoInterInstitucionalExporterImpl.java
 * Autor				: AlejandroGA
 * Compania             : Ultrasist
 * Proyecto             : NSJP                    Fecha: 09/07/2013
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

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.documento.DocumentoWSDTO;
import mx.gob.segob.nsjp.dto.documento.MandamientoWSDTO;
import mx.gob.segob.nsjp.service.mandamiento.RecibirActualizacionMandamientoInterInstitucionalService;
import mx.gob.segob.nsjp.service.ws.RecibirActualizacionMandamientoInterInstitucionalExporter;
import mx.gob.segob.nsjp.service.ws.infra.impl.ApplicationContextAwareWSNSJPImpl;

import org.apache.log4j.Logger;

/**
 * @author AlejandroGA
 * @version 1.0
 */
@WebService(endpointInterface = "mx.gob.segob.nsjp.service.ws.RecibirActualizacionMandamientoInterInstitucionalExporter")
public class RecibirActualizacionMandamientoInterInstitucionalExporterImpl
		implements RecibirActualizacionMandamientoInterInstitucionalExporter {

	private final static Logger logger = Logger
			.getLogger(RecibirActualizacionMandamientoInterInstitucionalExporterImpl.class);

	private RecibirActualizacionMandamientoInterInstitucionalService service;

	@Override
	public void recibirActualizacionMandamientoInterInstitucional(
			MandamientoWSDTO mandamientoWSDTO, DocumentoWSDTO documentoWSDTO)
			throws NSJPNegocioException {

		logger.debug("RecibirActualizacionMandamientoInterInstitucionalExporterImpl - recibirActualizacionMandamientoInterInstitucionalService");
		logger.debug("Parametros- mandamientoWSDTO=" + mandamientoWSDTO);
		logger.debug("Parametros- documentoWSDTO=" + documentoWSDTO);

		service = (RecibirActualizacionMandamientoInterInstitucionalService) ApplicationContextAwareWSNSJPImpl.springApplicationContext
				.getBean("recibirActualizacionMandamientoInterInstitucionalService");

		service.recibirActualizacionMandamientoInterInstitucional(
				mandamientoWSDTO, documentoWSDTO);
	}

}
