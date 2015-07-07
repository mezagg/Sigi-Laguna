/**
* Nombre del Programa 	: ReinsercionServiceImpl.java
* Autor 				: EdgarTE
* Compania 				: Ultrasist
* Proyecto 				: NSJP 								Fecha: 28 Mar 2012
* Marca de cambio 		: N/A
* Descripcion General 	: Describir el objetivo de la clase de manera breve
* Programa Dependiente 	: N/A
* Programa Subsecuente 	: N/A
* Cond. de ejecucion 	: N/A
* Dias de ejecucion 	: N/A 								Horario: N/A
*                              MODIFICACIONES
*------------------------------------------------------------------------------
* Autor 				: N/A
* Compania 				: N/A
* Proyecto 				: N/A 								Fecha: N/A
* Modificacion 			: N/A
*------------------------------------------------------------------------------
*/
package mx.gob.segob.nsjp.service.reinsercion.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.programa.RemisionDAO;
import mx.gob.segob.nsjp.dao.reinsercion.InventarioPertenenciaDAO;
import mx.gob.segob.nsjp.dao.sentencia.SentenciaDAO;
import mx.gob.segob.nsjp.dto.detencion.PertenenciaDTO;
import mx.gob.segob.nsjp.dto.programas.RemisionDTO;
import mx.gob.segob.nsjp.dto.reinsercion.InventarioPertenenciaDTO;
import mx.gob.segob.nsjp.dto.sentencia.SentenciaDTO;
import mx.gob.segob.nsjp.model.InventarioPertenencia;
import mx.gob.segob.nsjp.model.Pertenencia;
import mx.gob.segob.nsjp.model.Remision;
import mx.gob.segob.nsjp.model.Sentencia;
import mx.gob.segob.nsjp.service.detencion.impl.transform.PertenenciaTransformer;
import mx.gob.segob.nsjp.service.programa.impl.transform.AsignacionProgramaTransformer;
import mx.gob.segob.nsjp.service.reinsercion.ReinsercionService;
import mx.gob.segob.nsjp.service.reinsercion.impl.transform.InventarioPertenenciaTransformer;
import mx.gob.segob.nsjp.service.reinsercion.impl.transform.RemisionTransformer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.istack.logging.Logger;

/**
 * Clase que expone los m&eacute;todos de los DAO's para su consumo
 * desde las capas superiores.
 * @version 1.0
 * @author EdgarTE
 *
 */
@Service
@Transactional
public class ReinsercionServiceImpl implements ReinsercionService {
	
	private static final Logger log = Logger.getLogger(ReinsercionServiceImpl.class);
	
	@Autowired
	private InventarioPertenenciaDAO inventarioPertenenciaDAO;
	
	@Autowired
	private RemisionDAO remisionDAO;
	
	@Autowired
	private SentenciaDAO sentenciaDAO;

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.reinsercion.ReinsercionService#crearInventarioPertenencia(mx.gob.segob.nsjp.dto.reinsercion.InventarioPertenenciaDTO)
	 */
	@Override
	public InventarioPertenenciaDTO crearInventarioPertenencia(
			InventarioPertenenciaDTO inventarioPertenenciaDTO) {
		InventarioPertenencia inventarioPertenencia = InventarioPertenenciaTransformer.transformar(inventarioPertenenciaDTO, 
				InventarioPertenenciaTransformer.CON_PERTENENCIAS);
		Long inventarioId = inventarioPertenenciaDAO.create(inventarioPertenencia);
		inventarioPertenenciaDAO.flush();
		inventarioPertenenciaDTO.setInventarioPertenenciaId(inventarioId);
		return inventarioPertenenciaDTO;
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.reinsercion.ReinsercionService#actualizarInventarioPertenencias(mx.gob.segob.nsjp.dto.reinsercion.InventarioPertenenciaDTO)
	 */
	@Override
	public void actualizarInventarioPertenencias(
			InventarioPertenenciaDTO inventarioPertenenciaDTO) {
		InventarioPertenencia inventarioPertenencia = InventarioPertenenciaTransformer.transformar(inventarioPertenenciaDTO, 
				InventarioPertenenciaTransformer.CON_PERTENENCIAS);
		inventarioPertenenciaDAO.merge(inventarioPertenencia);
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.reinsercion.ReinsercionService#consultarPertenenciasPorInventario(mx.gob.segob.nsjp.dto.reinsercion.InventarioPertenenciaDTO)
	 */
	@Override
	public List<PertenenciaDTO> consultarPertenenciasPorInventario(
			InventarioPertenenciaDTO inventarioPertenenciaDTO) throws NSJPNegocioException {
		List <PertenenciaDTO> pertenenciasDTO = null;
		List<Pertenencia> pertenencias = inventarioPertenenciaDAO.consultarPertenenciasPorInventario(inventarioPertenenciaDTO);
		if (pertenencias != null && !pertenencias.isEmpty()){
			pertenenciasDTO = new ArrayList<PertenenciaDTO>();
			for (Pertenencia p : pertenencias){
				pertenenciasDTO.add(PertenenciaTransformer.transformarEntity(p));
			}
		}
		return pertenenciasDTO;
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.reinsercion.ReinsercionService#registrarRemision(mx.gob.segob.nsjp.dto.programas.RemisionDTO)
	 */
	@Override
	public RemisionDTO registrarRemision(RemisionDTO remisionDTO) {
		Remision remision = AsignacionProgramaTransformer.transformar(remisionDTO);
		Long idRemision = remisionDAO.create(remision);
		remisionDTO.setRemisionId(idRemision);
		return remisionDTO;
	}
	
	
	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.reinsercion.ReinsercionService#actualizarRemision(mx.gob.segob.nsjp.dto.programas.RemisionDTO)
	 */
	@Override
	public void actualizarRemision(RemisionDTO dto) throws NSJPNegocioException{
		if (dto.getRemisionId() == null 
				|| dto.getRemisionId() < 1L){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		Remision entity = remisionDAO.read(dto.getRemisionId());
		if (entity != null){
			boolean actualizacionRequerida = Boolean.FALSE;
			if (dto.getCumplida() != null){
				entity.setBcumplida(dto.getCumplida());
				actualizacionRequerida = Boolean.TRUE;
			}
			if (dto.getFechaAutorizacion() != null){
				entity.setdFechaAutorizacion(dto.getFechaAutorizacion());
				actualizacionRequerida = Boolean.TRUE;
			}
			if (dto.getIdiasAcreditados() != null){
				entity.setIdiasAcreditados(dto.getIdiasAcreditados());
				actualizacionRequerida = Boolean.TRUE;
			}
			if (dto.getImontoDanioReparado() != null){
				entity.setImontoDanioReparado(dto.getImontoDanioReparado());
				actualizacionRequerida = Boolean.TRUE;
			}
			if (actualizacionRequerida){
				remisionDAO.update(entity);			
			}
		}
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.reinsercion.ReinsercionService#eliminarRemisionesNoActualizadas(java.util.List)
	 */
	@Override
	public void eliminarRemisionesNoActualizadas(List<RemisionDTO> remisiones, SentenciaDTO sentencia) {
		if (sentencia != null 
				&& sentencia.getSentenciaId() != null 
				&& sentencia.getSentenciaId() > 0L){
			Sentencia sentenciaBD = sentenciaDAO.read(sentencia.getSentenciaId());
			Map<Long, Boolean> remisionesNuevas = new HashMap<Long, Boolean>();
			if (remisiones != null
					&& !remisiones.isEmpty()){
				for (RemisionDTO rem : remisiones){
					remisionesNuevas.put(rem.getCatTipoRemisionDTO().getCatTipoRemisionId(), Boolean.TRUE);
				}
				if (sentencia.getRemisions() != null
						&& !sentencia.getRemisions().isEmpty()
						&& remisionesNuevas != null){
					for (Remision remAnterior : sentenciaBD.getRemisions()){
						if (!remisionesNuevas.containsKey(remAnterior.getCatTipoRemision().getCatTipoRemisionId())){
							remisionDAO.delete(remAnterior);
						}
					}
				}
			}//Si la lista de remisiones llega nula o es vacia, se eliminan todas las remisiones anteriores.
			else{
				if (sentencia.getRemisions() != null
						&& !sentencia.getRemisions().isEmpty()){
					for (Remision remAnterior : sentenciaBD.getRemisions()){
						remisionDAO.delete(remAnterior);
					}				
				}
			}			
		}
	}

	@Override
	public List<RemisionDTO> consultarComplementoRemisiones(
			List<RemisionDTO> remisiones, SentenciaDTO sentenciaDto)
			throws NSJPNegocioException {

		if (sentenciaDto == null || sentenciaDto.getSentenciaId() == null
				|| sentenciaDto.getSentenciaId() <= 0L) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		List<Long> listaCatTipoRemisionId = null;

		if (remisiones != null && !remisiones.isEmpty()) {
			listaCatTipoRemisionId = new ArrayList<Long>();
			for (RemisionDTO remisionDto : remisiones) {
				if (remisionDto.getCatTipoRemisionDTO() == null
						|| remisionDto.getCatTipoRemisionDTO()
								.getCatTipoRemisionId() == null
						|| remisionDto.getCatTipoRemisionDTO()
								.getCatTipoRemisionId() <= 0L) {
					//Si cuenta con remisiones es obligatorio que cuenten con un catTipoRemisionId
					log.info("ES REQUERIDO EL CAT_TIPO_REMISION_ID");
					throw new NSJPNegocioException(
							CodigoError.PARAMETROS_INSUFICIENTES);
				
				}
				listaCatTipoRemisionId.add(remisionDto.getCatTipoRemisionDTO()
						.getCatTipoRemisionId());
			}

		}

		List<Remision> listaComplementoRemisiones = remisionDAO
				.consultarComplementoRemisiones(listaCatTipoRemisionId,
						sentenciaDto.getSentenciaId());

		List<RemisionDTO> listaComplementoRemisionesDto = null;

		if (listaComplementoRemisiones != null
				&& !listaComplementoRemisiones.isEmpty()) {
			listaComplementoRemisionesDto = new ArrayList<RemisionDTO>();
			for (Remision remisionBD : listaComplementoRemisiones) {
				listaComplementoRemisionesDto.add(RemisionTransformer
						.transforma(remisionBD));
			}
		}

		return listaComplementoRemisionesDto;
	}
}
