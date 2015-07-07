/**
* Nombre del Programa : ReinsercionDelegateImpl.java
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
package mx.gob.segob.nsjp.delegate.reinsercion.impl;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.delegate.reinsercion.ReinsercionDelegate;
import mx.gob.segob.nsjp.dto.detencion.PertenenciaDTO;
import mx.gob.segob.nsjp.dto.programas.RemisionDTO;
import mx.gob.segob.nsjp.dto.reinsercion.InventarioPertenenciaDTO;
import mx.gob.segob.nsjp.dto.sentencia.SentenciaDTO;
import mx.gob.segob.nsjp.service.reinsercion.ReinsercionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author EdgarTE
 *
 */
@Service
@Transactional
public class ReinsercionDelegateImpl implements ReinsercionDelegate {
	
	@Autowired
	private ReinsercionService reinsercionService;

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.reinsercion.ReinsercionDelegate#crearInventarioPertenencia(mx.gob.segob.nsjp.dto.reinsercion.InventarioPertenenciaDTO)
	 */
	@Override
	public InventarioPertenenciaDTO crearInventarioPertenencia(
			InventarioPertenenciaDTO inventarioPertenenciaDTO) {
		return reinsercionService.crearInventarioPertenencia(inventarioPertenenciaDTO);
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.reinsercion.ReinsercionDelegate#actualizarInventarioPertenencias(mx.gob.segob.nsjp.dto.reinsercion.InventarioPertenenciaDTO)
	 */
	@Override
	public void actualizarInventarioPertenencias(
			InventarioPertenenciaDTO inventarioPertenenciaDTO) {
		reinsercionService.actualizarInventarioPertenencias(inventarioPertenenciaDTO);
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.reinsercion.ReinsercionDelegate#consultarPertenenciasPorInventario(mx.gob.segob.nsjp.dto.reinsercion.InventarioPertenenciaDTO)
	 */
	@Override
	public List<PertenenciaDTO> consultarPertenenciasPorInventario(
			InventarioPertenenciaDTO inventarioPertenenciaDTO) throws NSJPNegocioException{
		return reinsercionService.consultarPertenenciasPorInventario(inventarioPertenenciaDTO);
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.reinsercion.ReinsercionDelegate#registrarRemision(mx.gob.segob.nsjp.dto.programas.RemisionDTO)
	 */
	@Override
	public RemisionDTO registrarRemision(RemisionDTO remisionDTO) {
		return reinsercionService.registrarRemision(remisionDTO);
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.reinsercion.ReinsercionDelegate#actualizarRemision(mx.gob.segob.nsjp.dto.programas.RemisionDTO)
	 */
	@Override
	public void actualizarRemision(RemisionDTO dto) throws NSJPNegocioException {
		reinsercionService.actualizarRemision(dto);
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.reinsercion.ReinsercionDelegate#eliminarRemisionesNoActualizadas(java.util.List)
	 */
	@Override
	public void eliminarRemisionesNoActualizadas(List<RemisionDTO> remisiones, SentenciaDTO sentencia) {
		reinsercionService.eliminarRemisionesNoActualizadas(remisiones, sentencia);
	}

	@Override
	public List<RemisionDTO> consultarComplementoRemisiones(
			List<RemisionDTO> remisiones, SentenciaDTO sentenciaDto)
			throws NSJPNegocioException {
		return reinsercionService.consultarComplementoRemisiones(remisiones, sentenciaDto);
	}
}
