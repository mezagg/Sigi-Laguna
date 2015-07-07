/**
 * Nombre del Programa  : RolService.java
 * Autor                : Daniel Jim�nez
 * Compania             : TATTVA-IT
 * Proyecto             : NSJP                    Fecha: 27 Jul 2011
 * Marca de cambio      : N/A
 * Descripcion General  : Servicio que administra la informaci�n de Roles
 * Programa Dependiente : N/A
 * Programa Subsecuente : N/A
 * Cond. de ejecucion   : N/A
 * Dias de ejecucion    : N/A                             Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                :N/A
 * Compania             :N/A
 * Proyecto             :N/A                                 Fecha: N/A
 * Modificacion         :N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.service.usuario;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.rol.FiltroRolesDTO;
import mx.gob.segob.nsjp.dto.usuario.RolDTO;

/**
 * Servicio que administra la informaci�n de Roles
 * 
 * @version 1.0
 * @author Daniel Jim�nez
 * 
 */
public interface RolService {

    /**
     * Consulta los roles del sistema.
     * @return Lista de roles del sistema
     * @throws NSJPNegocioException
     */
	public List<RolDTO> consultarRoles(FiltroRolesDTO filtroRolesDTO) throws NSJPNegocioException;

	/**
	 * 
	 * @param filtroRolesDTO
	 * @return
	 * @throws NSJPNegocioException
	 */
	public List<RolDTO> consultarRolesPadre (FiltroRolesDTO filtroRolesDTO) throws NSJPNegocioException;
	
	/**
	 * Realiza la actualizacion de un rol
	 * @author cesarAgustin
	 * @param rolDTO
	 * @throws NSJPNegocioException
	 */
	public void actualizarRol(RolDTO rolDTO) throws NSJPNegocioException;
	

	/**
	 * M�todo que consulta la informaci�n de un rol, m�dulos y funciones asociados
	 * @param rol
	 * @return
	 * @throws NSJPNegocioException
	 */
	public RolDTO consultarRolCompleto (RolDTO rol) throws NSJPNegocioException;
	
	/**
	 * M�todo que consulta la informaci�n de un rol y sus m�dulos asociados
	 * @param rol
	 * @return
	 * @throws NSJPNegocioException
	 */
	public RolDTO consultarRol(RolDTO rol) throws NSJPNegocioException;
	
	/**
	 * M�todo que consulta, �NICAMENTE, la informaci�n de un rol
	 * @param rol
	 * @return
	 * @throws NSJPNegocioException
	 */
	public RolDTO consultarRolMinimo (RolDTO rol) throws NSJPNegocioException;
	
	public boolean actualizaModulosRol (RolDTO rolDTO) throws NSJPNegocioException;
	public boolean actualizarConfiguracionRol(RolDTO rolDTO) throws NSJPNegocioException;
	public RolDTO consultarConfiguracionRol(RolDTO rolDTO) throws NSJPNegocioException;
	
	public boolean crearRol (RolDTO rolDTO) throws NSJPNegocioException;
	public Long crearSubRol (RolDTO rolDTO) throws NSJPNegocioException;
	public boolean modificarRol (RolDTO rolDTO) throws NSJPNegocioException;
	public List<RolDTO> consultarSubRoles (RolDTO rol) throws NSJPNegocioException;
	public RolDTO consultarRolPadre(Long idRol) throws NSJPNegocioException;

	
	
}
