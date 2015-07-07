/**
 * Nombre del Programa : REgistrarReplicaCasoServiceImpl.java
 * Autor                            : vaguirre
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 21 Aug 2011
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
package mx.gob.segob.nsjp.service.caso;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.caso.CasoDTO;
import mx.gob.segob.nsjp.dto.caso.CasoWSDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatDistritoDTO;

/**
 * Describir el objetivo de la clase con punto al final.
 * 
 * @version 1.0
 * @author vaguirre
 * 
 */
public interface RegistrarReplicaCasoService {

    /**
     * REgistra la replica del caso.
     * 
     * @param casoWS
     * @throws NSJPNegocioException
     */
    void registraReplicaCaso(CasoWSDTO casoWS) throws NSJPNegocioException;
    
    /**
     * Servicio que se encarga de validar si el caso existe
     * En caso de que no exista, se crea un nuevo caso con el caso asociado.
     * Además, se valida si existe un expediente asociado al caso, 
     * en caso de no existir, se cera uno nuevo y sea asocia a dicho caso.
     *  
     * @param casoDTO
     * @param catDistritoDTO Utilizado para defensoria
     * @return
     * @throws NSJPNegocioException
     */
	CasoDTO registrarCasoExpediente(CasoDTO casoDTO, CatDistritoDTO catDistritoDTO)
			throws NSJPNegocioException;
}
