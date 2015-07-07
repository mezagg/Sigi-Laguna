/**
 *
 * Nombre del Programa : ConsultarEtapasService.java                                    
 * Autor                            : GustavoBP
 * Compania                    : Ultrasist                                                
 * Proyecto                      : NSJP                    Fecha: 21/01/2013 
 * Marca de cambio        : N/A                                                     
 * Descripcion General    : Interface del servicio para recuperar la información de las Etapas de -Involucrado -Expediente                 
 * Programa Dependiente  :N/A                                                      
 * Programa Subsecuente :N/A                                                      
 * Cond. de ejecucion        :N/A                                                      
 * Dias de ejecucion          :N/A                             Horario: N/A       
 *                              MODIFICACIONES                                       
 *------------------------------------------------------------------------------           
 * Autor                       :N/A                                                           
 * Compania               :N/A                                                           
 * Proyecto                 :N/A                                   Fecha: N/A       
 * Modificacion           :N/A                                                           
 *------------------------------------------------------------------------------           
 */
package mx.gob.segob.nsjp.service.catalogo;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.catalogo.CatEtapaDTO;

/**
 * Interface del servicio para recuperar la información de las Etapas de
 * -Involucrado -Expediente
 * 
 * @author GustavoBP
 */
public interface ConsultarEtapasService {

	/**
	 * Servicio que se encarga de consular las etapas de -Expediente
	 * -Involucrado Dependiento de la bandera que se está pasando como
	 * parametro. Utilizado para Defensoria
	 * 
	 * @param esEtapaExpediente
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<CatEtapaDTO> consultarEtapas(Boolean esEtapaExpediente)
			throws NSJPNegocioException;

	/**
	 * Servicio que consulta la jerarquia de etapas, donde la raíz son los
	 * padres y las hojas son las hijas asociadas a cada padre.
	 * 
	 * @param esEtapaExpediente
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<CatEtapaDTO> consultarEtapasJerarquiaPorPadre(Boolean esEtapaExpediente)
			throws NSJPNegocioException;
	
}
