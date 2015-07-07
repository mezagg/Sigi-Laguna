/**
* Nombre del Programa : RemisionDAO.java
* Autor                            : AntonioBV
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 26/01/2012
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
package mx.gob.segob.nsjp.dao.programa;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.model.Remision;

import org.springframework.stereotype.Repository;

/**
 * 
 * Acceso a la información de Remision
 * 
 * @version 1.0
 * @author AntonioBV
 *
 */
@Repository
public interface RemisionDAO extends GenericDao<Remision, Long> {
		
	/**
	 * M&eacute;todo que consulta una remision por id
	 * @return Remision
	 * @throws NSJPNegocioException
	 */
	public Remision consultarRemisionPorId(Remision remision)throws NSJPNegocioException;
	
	/**
	 * M&eacute;todo que lleva a cabo la consulta de una remisi&oacute;n en base al tipo
	 * de remisi&oacute;n y a la sentencia asociada.
	 * @param remision - Remision de la cual se obtendr&aacute;n los atributos para llevar 
	 * 					 a cabo la consulta.
	 * 
	 * @return Remision - Informaci&oacute;n de la remisi&oacute;n registrada dentro de la 
	 * 					  base de datos.
	 * @throws NSJPNegocioException - en el caso de que no se ingresen los datos obligatorios.
	 */
	public Remision consultaRemisionPorFiltros(Remision remision) throws NSJPNegocioException;
	
	/**
	 * M&eacute;todo que lleva a cabo la consulta de una lista de remisiones en base a la
	 * sentecia asociada y al cat tipo de remisi&oacute;n.
	 * 
	 * P. ej. Si una sentencia, tiene asociado tres remisiones la primera con CatTipoRemisionId = 1,
	 * la segunda con CatTipoRemisionId = 2 y la tercera con CatTipoRemisionId = 3, y en la lista de cat tipo
	 * remision tenemos 1L y 2L; obtendr&aacute; una remisi&oacute;n, la cual ser&aacute; la del CatTipoRemisionId = 3.
	 * 
	 * P. ej. Si la lista de listaCatTipoRemisionId es vac&iacutea; o nula, devolvera todas las remisiones asociadas
	 * a la sentencia(si las hay).
	 * 
	 * @param listaCatTipoRemisionId - Lista de CatTipoRemisionId
	 * @param sentenciaId - id de la sentencia asociada a las remisiones
	 * 
	 * @return Lista de remisiones con el catTipoRemisionId complemento de dicha sentencia
	 */
	public List<Remision> consultarComplementoRemisiones(List<Long> listaCatTipoRemisionId, Long sentenciaId);
		
}
