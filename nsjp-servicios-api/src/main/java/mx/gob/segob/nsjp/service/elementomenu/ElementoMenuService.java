/**
 * 
 */
package mx.gob.segob.nsjp.service.elementomenu;

import java.util.List;

import mx.gob.segob.nsjp.comun.enums.menu.TipoMenu;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.elementomenu.ElementoMenuDTO;
import mx.gob.segob.nsjp.dto.usuario.RolDTO;

/**
 * @author LuisMG
 *
 */
public interface ElementoMenuService {
	/**
	 * Dado un Rol se regresa el men� asociado hasta ese rol 
	 * @param rolDTO
	 * @return
	 * @throws NSJPNegocioException
	 */
	
	public List<ElementoMenuDTO> consultarElementosMenuXRol (RolDTO rolDTO, TipoMenu tm) throws NSJPNegocioException;
	/**
	 * Dado un id de elementoMenu y idRol v�lido se regresa el �rbol de asociaciones de dicho elementoMenu
	 * @param eMDTO
	 * @return
	 * @throws NSJPNegocioException
	 */
	public ElementoMenuDTO consultarElementoMenu (RolDTO rolDTO, ElementoMenuDTO eMDTO) throws NSJPNegocioException;
	
	/**
	 * 
	 * @param rolDTO
	 * @param tm
	 * @return
	 * @throws NSJPNegocioException
	 */
	public List<ElementoMenuDTO> consultarElementosMenuObligatorios () throws NSJPNegocioException;
	
		
}
