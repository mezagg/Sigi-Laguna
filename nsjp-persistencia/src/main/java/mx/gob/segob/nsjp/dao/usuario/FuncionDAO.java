/**
* Nombre del Programa : FuncionDAO.java
* Autor                            : cesarAgustin
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 1 Aug 2011
* Marca de cambio        : N/A
* Descripcion General    : Contrato de metodos de acceso a datos para la entidad Funcion
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
package mx.gob.segob.nsjp.dao.usuario;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.model.ElementoMenu;
import mx.gob.segob.nsjp.model.Funcion;
import mx.gob.segob.nsjp.model.Usuario;

/**
 * Contrato de metodos de acceso a datos para la entidad Funcion.
 * @version 1.0
 * @author cesarAgustin
 *
 */
public interface FuncionDAO extends GenericDao<Funcion, Long> {

	/**
	 * 
	 * @return
	 */
	List<Funcion> consultarFunciones();

	/**
	 * 
	 * @param rolId
	 * @return
	 */
	List<Funcion> consultarFuncionesByRol(Long rolId);
	
	List<Funcion> consultarFuncionesXUsuario (Usuario usuario);
	
	/**
	 * M�todo que dada una funci�n a consultar verifica que el usuario tenga acceso a ella
	 * @param usuario
	 * @param funcion
	 * @return
	 */
	List<Funcion> validarFuncionXUsuario (String role, Long fnc);
	/**
	 * M�todo encargado de regresar toda la informaci�n de una funci�n dado el nombre
	 * @param funcion
	 * @return
	 */
	Funcion consultarFuncionXNombre (Funcion funcion);
	
	/**
	 * Dada una lista de Funciones el m�todo se encargar� de reflejarlo en la base de datos
	 * @param lstFunciones
	 * @return
	 */
	boolean instertarFunciones (List<Funcion> lstFunciones);
	
	
}
