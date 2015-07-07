/**
 * Nombre del Programa : ObtenerFechaActualServiceImpl.java
 * Autor                            : Jacob Lobaco
 * Compania                         : Ultrasist
 * Proyecto                         : NSJP                    Fecha: 23-jun-2011
 * Marca de cambio        : N/A
 * Descripcion General    : N/A
 * Programa Dependient    :N/A
 * Programa Subsecuente   :N/A
 * Cond. de ejecucion     :N/A
 * Dias de ejecucion      :N/A                                Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                            :N/A
 * Compania                         :N/A
 * Proyecto                         :N/A                      Fecha: N/A
 * Modificacion           :N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.service.fecha.impl;

import java.util.Date;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.service.fecha.ObtenerFechaActualService;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @version 1.0
 * @author Jacob Lobaco
 */
@Service
@Transactional
public class ObtenerFechaActualServiceImpl implements ObtenerFechaActualService {

    /**
      * Logger de la clase.
      */
    private final static Logger logger = Logger
            .getLogger(ObtenerFechaActualServiceImpl.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public Date obtenerFechaActual() throws NSJPNegocioException {
        return new Date();
    }

   
}
