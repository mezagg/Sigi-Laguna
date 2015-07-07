/**
* Nombre del Programa : BuscarFormaService.java
* Autor                            : Emigdio Hern�ndez
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 30/05/2011
* Marca de cambio        : N/A
* Descripcion General    : Definici�n del contrato de negocio del servicio para buscar Formas
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
package mx.gob.segob.nsjp.service.forma;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.forma.FormaDTO;

/**
 * Definici�n del contrato de negocio del servicio para buscar Formas
 * @author Emigdio Hern�ndez
 *
 */
public interface BuscarFormaService {
	/**
	 * Consulta una Forma en base a su Identificador
	 * @param formaId Identificador de la forma
	 * @return Objeto con los datos de la Forma
	 */
	FormaDTO buscarForma(Long formaId) throws NSJPNegocioException ;

	/**
	 * Consulta todas las  Formas
	 * @return Lista de objetos con los datos de la Formas
	 */
	List<FormaDTO> consultarTodasLasForma() throws NSJPNegocioException ;

		
	
}
