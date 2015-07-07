/**
 * Nombre del Programa  : UsuarioService.java
 * Autor                : Daniel Jim�nez
 * Compania             : TATTVA-IT
 * Proyecto             : NSJP                    Fecha: 27 Jul 2011
 * Marca de cambio      : N/A
 * Descripcion General  : Servicio que administra la informaci�n de Usuarios
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
import mx.gob.segob.nsjp.dto.elementomenu.ElementoMenuDTO;
import mx.gob.segob.nsjp.dto.usuario.FuncionDTO;
import mx.gob.segob.nsjp.dto.usuario.RolDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;

/**
 * Servicio que administra la informaci�n de Usuarios
 * 
 * @version 1.0
 * @author Daniel Jim�nez
 * 
 */
public interface UsuarioService {
//    FIXME Daniel Jimenez Documentar
    public Long registrarUsuario(UsuarioDTO usuario, List<RolDTO> roles)
            throws NSJPNegocioException;
    /**
     * Servicio para asociar la jerarquia organizacional de un rol a un
     * funcionario.
     * @param long Clave del funcionario
     * @param Long identificador del rol
     * @throws NSJPNegocioException
     */
    public void asociarJerarquiDeRolaFuncionario(Long claveFuncionario, Long rolId)
    	throws NSJPNegocioException;
    
    public UsuarioDTO consultarUsuarioPorClaveFuncionario(Long claveFuncionario)
            throws NSJPNegocioException;

    /**
     * Servicio utilizado para la actualizaci�n de un usuario.
     * Es posible actualizar:
     * 		-ClaveUsuario
     * 		-PalabraSecreta
     * 		-Rol 
     * En el caso del Rol, la pantalla solo puede aceptar un Rol; 
     * sin embargo, las relaciones de BD puede soportar m�s de un rol y 
     * el servicio est� preparado para dicho fin.
     * 
     * @param usuarioDTO
     * @param roles
     * @throws NSJPNegocioException
     */
    public boolean actualizarUsuario(UsuarioDTO usuarioDTO, List<RolDTO> roles)
            throws NSJPNegocioException;
    /**
     * Operaci�n que valida que el usuario exista en el sistema y con
     * passcoincida.
     * 
     * @param usr
     *            Requeridos:
     *            <ul>
     *            <li>claveUsuario</li>
     *            <li>palabraSecreta</li>
     *            </ul>
     * @return Un <code>UsuarioDTO</code> para cuando el uuario existe y su
     *         palabra secreta coincide, <code>null</code> en caso contrario.
     * @throws NSJPNegocioException
     */
    public UsuarioDTO login(UsuarioDTO usr) throws NSJPNegocioException;
    
    UsuarioDTO validarUsuario(UsuarioDTO usr) throws NSJPNegocioException;
    
    void logout(UsuarioDTO usr) throws NSJPNegocioException;
    
    void desbloquearUsuario()throws NSJPNegocioException;
    
    public UsuarioDTO validaContrasenia(UsuarioDTO usr) throws NSJPNegocioException;
    
    void actualizarUsuarioBasico(UsuarioDTO usuarioDTO) throws NSJPNegocioException;
    public void agregaUsuariosChat()throws NSJPNegocioException;
    public String buscaParametroChat()throws NSJPNegocioException ;
    public String buscaParametroImagenes()throws NSJPNegocioException ;
    public ElementoMenuDTO consultarMenuXUsuario (UsuarioDTO usuario)throws NSJPNegocioException;
	public boolean anularUsuarioDeSistema(Long idUsuario);
	boolean buscarUsuarioEnSesion(UsuarioDTO usuarioDTO) throws NSJPNegocioException;
	String buscarUsuarioEnSesionId(UsuarioDTO usuarioDTO);

	/**
	 * Consulta un usuario a partir de su clave
	 * 
	 * @param claveUsuario
	 * @return
	 * @throws NSJPNegocioException
	 */
	UsuarioDTO consultarUsuarioPorClaveUsuario(String claveUsuario)
			throws NSJPNegocioException;
	
	/**
	 * Apartir de la clave de usuario y passsword, se valida la autentificaci�n.
	 * y se obtiene el Usuario con su funcionario.
	 * No se activa la sesi�n, ni se registra la informaci�n de acceso.
	 * Se requiere:
	 * 		ClaveUsuario
	 * 		Password
	 * 
	 * @param usuarioDTO
	 * @return
	 * @throws NSJPNegocioException
	 */
	public UsuarioDTO obtenerUsuarioValidado(UsuarioDTO usuarioDTO)
			throws NSJPNegocioException;
	
	UsuarioDTO buscaUsuario(UsuarioDTO usuarioDTO)
			throws NSJPNegocioException ;
	/**
	 * M�todo encargado de regresar si un usuario tiene o no una sesi�n iniciada donde 
	 * Regresa null si no hay una sesi�n iniciada y regresa la sesi�n si hay una sesi�n iniciada
	 * 
	 * @param usuarioDTO
	 * @return
	 * @throws NSJPNegocioException
	 */
	
	public UsuarioDTO validarSesion (UsuarioDTO usuarioDTO) throws NSJPNegocioException;
	
	public boolean validarFuncionXUsuario (UsuarioDTO usrDTO, FuncionDTO fncDTO) throws NSJPNegocioException;
	
	public UsuarioDTO consultarUsuarioXClaveUsuario (UsuarioDTO usrDTO) throws NSJPNegocioException;
	
    
}
