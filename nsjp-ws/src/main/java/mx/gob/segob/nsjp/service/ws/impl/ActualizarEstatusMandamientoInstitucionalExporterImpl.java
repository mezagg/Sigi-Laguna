/**
 * Nombre del Programa : ActualizarEstatusMandamientoInstitucionalExporterImpl.java
 * Autor                            : rgama
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 7 Sep 2011
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
package mx.gob.segob.nsjp.service.ws.impl;

import javax.jws.WebService;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.documento.MandamientoWSDTO;
import mx.gob.segob.nsjp.service.documento.ActualizarEstatusMandamientoInstitucionalService;
import mx.gob.segob.nsjp.service.ws.ActualizarEstatusMandamientoInstitucionalExporter;
import mx.gob.segob.nsjp.service.ws.infra.impl.ApplicationContextAwareWSNSJPImpl;

import org.apache.log4j.Logger;

/**
 * Describir el objetivo de la clase con punto al final.
 * 
 * @version 1.0
 * @author rgama
 * 
 */
@WebService(endpointInterface = "mx.gob.segob.nsjp.service.ws.ActualizarEstatusMandamientoInstitucionalExporter")
public class ActualizarEstatusMandamientoInstitucionalExporterImpl
        implements
        ActualizarEstatusMandamientoInstitucionalExporter {
    /**
     * Logger.
     */
    private final static Logger logger = Logger
            .getLogger(ActualizarEstatusMandamientoInstitucionalExporterImpl.class);

    /**
     * Spring bean
     */
    private ActualizarEstatusMandamientoInstitucionalService service;

    
    public Boolean actualizarEstatusMandamientoJudicial(MandamientoWSDTO mandamientoWSDTO)
            throws NSJPNegocioException {
        logger.info("Inicia - actualizarEstatusMandamientoJudicial(...)");
        service = (ActualizarEstatusMandamientoInstitucionalService) ApplicationContextAwareWSNSJPImpl.springApplicationContext
                .getBean("actualizarEstatusMandamientoInstitucionalService");
		return service.actualizarEstatusMandamientoJudicial(mandamientoWSDTO);
    }

}
