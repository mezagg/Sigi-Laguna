/**
 * Nombre del Programa : ObtenerObjetoService.java
 * Autor                            : vaguirre
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 18 Jul 2011
 * Marca de cambio        : N/A
 * Descripcion General    : Interface de servicio que obtiene un objeto
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
package mx.gob.segob.nsjp.service.objeto;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.objeto.ObjetoDTO;

/**
 * Interface de servicio que obtiene un objeto.
 * 
 * @version 1.0
 * @author vaguirre
 * 
 */
public interface ObtenerObjetoService {
    /**
     * Recupera un objeto de la BD.
     * 
     * @param input
     *            requerido <b>elementoId</b>.
     * @return
     * @throws NSJPNegocioException
     */
    ObjetoDTO obtenerObjeto(ObjetoDTO input) throws NSJPNegocioException;
    
    /**
     * Permite saber si un objeto tiene asociada una cadena de custodia
     * @param idObjeto
     * @return
     * @throws NSJPNegocioException
     */
    public Boolean existeCadenaDeCustodiaPorIdObjeto(Long idObjeto)throws NSJPNegocioException;
    
    /**
     * Permite saber si un objeton esta asociado a una evidencia 
     * y a su ves la evdencia tiene eslabones
     * @return
     * @throws NSJPNegocioException
     */
    public Boolean existenEslabonesPorIdObjeto(Long idObjeto)throws NSJPNegocioException;

    /**
     * M&eacute;todo que obtiene los objetos filtrados por expediente y tipo.
     * @param expedienteDTO
     * @param tipoObjeto
     * @return Lista de Objetos
     * @throws NSJPNegocioException
     */
	List<ObjetoDTO> consultarObjetos(
			ExpedienteDTO expedienteDTO, ValorDTO tipoObjeto)
			throws NSJPNegocioException;
    
}
