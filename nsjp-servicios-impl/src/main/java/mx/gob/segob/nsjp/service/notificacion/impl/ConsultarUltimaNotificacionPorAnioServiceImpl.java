/**
 * Nombre del Programa : ConsultarUltimaNotificacionPorAnioServiceImpl.java
 * Autor                            : Jacob Lobaco
 * Compania                         : Ultrasist
 * Proyecto                         : NSJP                    Fecha: 29-jul-2011
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
package mx.gob.segob.nsjp.service.notificacion.impl;

import java.util.Date;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.documento.NotificacionDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.service.notificacion.ConsultarUltimaNotificacionPorAnioService;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @version 1.0
 * @author Jacob Lobaco
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class ConsultarUltimaNotificacionPorAnioServiceImpl implements
        ConsultarUltimaNotificacionPorAnioService {

    /**
     * Logger de la clase.
     */
    private final static Logger logger =
            Logger.getLogger(ConsultarUltimaNotificacionPorAnioServiceImpl.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public NotificacionDTO consultarUltimaNotificacionPorAnio(FuncionarioDTO
            funcionarioDto) throws NSJPNegocioException {
        NotificacionDTO notificacionDTO = new NotificacionDTO();
        // TODO Jacob Lobaco Obtener ultimo numero consecutivo de la notifiacion
        notificacionDTO.setConsecutivoNotificacion("123456");
        notificacionDTO.setFechaCreacion(new Date());
        // TODO Consultar la ciudad y estado
//        Parametro entidadFed = parametroDao.
//                obtenerPorClave(Parametros.ENTIDAD_FEDERATIVA_DESPLIEGUE);
        notificacionDTO.setLugar("Coyoac�n Distrito Federal");
        return notificacionDTO;
    }
}
