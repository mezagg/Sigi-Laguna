/**
* Nombre del Programa : RecibirExpedienteService.java
* Autor                            : GustavoBP
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 08/09/2011
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
package mx.gob.segob.nsjp.service.expediente;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.ssp.informepolicial.InformePolicialHomologadoDTO;

/**
 * 
 * @version 1.0
 * @author GustavoBP
 *
 */
public interface RecibirExpedienteService {
	/**
	 * Metodo que permite crear un expediente asociado a una agencia, permitira crear un numero expediente con
	 * 2 digitos para el Distrito y 3 para las agencias
	 * @param iphDTO
	 * @param idAgencia
	 * @return
	 * @throws NSJPNegocioException
	 */
	ExpedienteDTO guardarExpedienteRecibido(InformePolicialHomologadoDTO iphDTO, Long idAgencia)throws NSJPNegocioException ;
	/**
	 * Permite generar el cas, si el idAgencia no es nullo entonces liga el expediente a esa agencia
	 * de lo contraio solo crea el expediente.
	 * @param idAgencia Representa el idAgencia a la que pertenecera el expediente
	 * @param claveDelito Clave del delito asociado al delito en el IPH
	 * @return
	 * @throws NSJPNegocioException
	 */
	ExpedienteDTO generarCasoExpediente(Long idAgencia,String claveDelito) throws NSJPNegocioException ;
}
