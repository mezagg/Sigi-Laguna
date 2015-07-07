/**
 * 
 */
package mx.gob.segob.nsjp.delegate.parametro;

import mx.gob.segob.nsjp.comun.enums.configuracion.Parametros;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.ParametroDTO;

/**
 * @author adrian
 *
 */
public interface ParametroDelegate {

	/**
	 * Operaci�n que realiza la funcionalidad de consultar un par�metro de configuraci�n asociado a su clave.
	 * @param parametroDTO: nombre=clave
	 * @return
	 * @throws NSJPNegocioException
	 */
	public ParametroDTO consultarParametro(Parametros parametro)throws NSJPNegocioException;
	
	public void modificaValorDeParametro(String valor,Parametros descripcion)throws NSJPNegocioException;

}
