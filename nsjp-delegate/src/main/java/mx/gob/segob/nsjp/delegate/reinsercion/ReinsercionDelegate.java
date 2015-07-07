/**
* Nombre del Programa : ReinsercionDelegate.java
* Autor                            : EdgarTE
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 29 Mar 2012
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
package mx.gob.segob.nsjp.delegate.reinsercion;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.detencion.PertenenciaDTO;
import mx.gob.segob.nsjp.dto.programas.RemisionDTO;
import mx.gob.segob.nsjp.dto.reinsercion.InventarioPertenenciaDTO;
import mx.gob.segob.nsjp.dto.sentencia.SentenciaDTO;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author EdgarTE
 *
 */
public interface ReinsercionDelegate {

	/**
	 * M&eacute;todo que lleva a cabo el registro de un nuevo inventario de 
	 * pertenencias dentro de la base de datos.
	 * @param inventarioPertenenciaDTO - El inventario de pertenencias a persistir.
	 * @return El inventarioPertenenciaDTO con su identificador 
	 */
	public InventarioPertenenciaDTO crearInventarioPertenencia (InventarioPertenenciaDTO inventarioPertenenciaDTO);

	/**
	 * M&eacute;todo que lleva a cabo la actualizaci&oacute;n de un inventario de
	 * pertenencias dentro de la base de datos.
	 * @param inventarioPertenenciaDTO - El inventario pertenencia a actualizar.
	 */
	public void actualizarInventarioPertenencias (InventarioPertenenciaDTO inventarioPertenenciaDTO);
	
	/**
	 * M&eacute;todo que lleva a cabo la consulta de todas las pertenencias 
	 * asociadas a un inventario.
	 * @param inventarioPertenenciaDTO - El inventario del cual se van a obtener 
	 * 									 las pertenencias.
	 * @return List<PertenenciaDTO> - Lista con las pertenencias asociadas al 
	 * 								  inventario.
	 */
	public List<PertenenciaDTO> consultarPertenenciasPorInventario(
			InventarioPertenenciaDTO inventarioPertenenciaDTO)
			throws NSJPNegocioException;
	
	/**
	 * M&eacute;todo que lleva a cabo la persistencia de una remisi&oacute;n
	 * dentro de la base de datos.
	 * @param remisionDTO - La remisi&oacute;n que se va a persistir dentro de la
	 * 						base de datos.
	 * @return remisionDTO - La remisi&oacute;n con el identificador que se asign&oacute;
	 * 						 una vez que se persisti&oacute; en la base de datos.
	 */
	public RemisionDTO registrarRemision(RemisionDTO remisionDTO);
	
	/**
	 * M&eacute;todo que lleva a cabo la actualizaci&oacute;n de las remisiones
	 * que se encuentran persistidas en la base de datos.
	 * @param dto - Objeto de tipo RemisionDTO con la informaci&oacute;n que se 
	 * 				actualizar&aacute; dentro de la base de datos.
	 * @param entity - Entidad que corresponde con la remisi&oacute;n que se encuentra 
	 * 				   registrada dentro de la base de datos local.
	 */
	public void actualizarRemision(RemisionDTO dto) throws NSJPNegocioException;
	
	/**
	 * M&eacute;todo que lleva a cabo la eliminaci&oacute;n de la base de datos local, de aquellas 
	 * remisiones que no se actualizaron en el momento en que se llev&oacute; a cabo la persistencia
	 * de la informaci&oacute;n actualizada de la sentencia. 
	 * @param remisiones - Lista de <code>RemisionDT</code> que se encuentran asociadas a una sentencia.
	 */
	public void eliminarRemisionesNoActualizadas(List<RemisionDTO> remisiones, SentenciaDTO sentencia);
	
	/**
	 * M&eacute;todo que lleva a cabo la consulta de una lista de remisiones en
	 * base a la sentecia asociada y al cat tipo de remisi&oacute;n.
	 * 
	 * P. ej. Si una sentencia, tiene asociado tres remisiones la primera con
	 * CatTipoRemisionId = 1, la segunda con CatTipoRemisionId = 2 y la tercera
	 * con CatTipoRemisionId = 3, y en la lista de remisiones tenemos el
	 * catTipoRemisionId = 1L para el primer elemento y catTipoRemisionId = 2L
	 * para el segundo elemento; obtendr&aacute; una remisi&oacute;n, la cual
	 * ser&aacute; la del CatTipoRemisionId = 3.
	 * 
	 * P. ej. Si la lista de remisiones es vac&iacutea; o nula, devolvera todas
	 * las remisiones asociadas a la sentencia(si las hay).
	 * 
	 * @param remisiones
	 *            - Lista de remisiones (catTipoRemisionId requerido)
	 * @param sentenciaDto
	 *            - sentencia asociada a las remisiones (sentenciaId requerido)
	 * 
	 * @return Lista de remisiones con el catTipoRemisionId complemento de dicha
	 *         sentencia
	 */
	public List<RemisionDTO> consultarComplementoRemisiones(
			List<RemisionDTO> remisiones, SentenciaDTO sentenciaDto)
			throws NSJPNegocioException;
}
