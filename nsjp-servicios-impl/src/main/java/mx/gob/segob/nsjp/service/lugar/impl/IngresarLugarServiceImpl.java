/**
* Nombre del Programa : IngresarLugarServiceImpl.java
* Autor                            : cesarAgustin
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 14 Jun 2011
* Marca de cambio        : N/A
* Descripcion General    : Implementacion del servicio para realizar el ingreso de un Lugar
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
package mx.gob.segob.nsjp.service.lugar.impl;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.lugar.CoordenadaGeograficaDAO;
import mx.gob.segob.nsjp.dto.domicilio.DomicilioDTO;
import mx.gob.segob.nsjp.dto.domicilio.LugarDTO;
import mx.gob.segob.nsjp.dto.elemento.CalidadDTO;
import mx.gob.segob.nsjp.model.Calidad;
import mx.gob.segob.nsjp.model.CoordenadaGeografica;
import mx.gob.segob.nsjp.model.Lugar;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.domicilio.IngresarDomicilioService;
import mx.gob.segob.nsjp.service.lugar.IngresarLugarService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementacion del servicio para realizar el ingreso de un Lugar.
 * @version 1.0
 * @author cesarAgustin
 *
 */
@Service
@Transactional
public class IngresarLugarServiceImpl implements IngresarLugarService {
	
	/**
	 * 
	 */
	public final static Logger logger = Logger.getLogger(IngresarLugarServiceImpl.class);
	
	@Autowired
	private CoordenadaGeograficaDAO coordenadaGeograficaDAO;
	
	@Autowired
	private IngresarDomicilioService ingresarDomicilioService;
	
	@Override
	public LugarDTO ingresarLugar(LugarDTO lugarDTO)
			throws NSJPNegocioException {
		
		if(logger.isDebugEnabled())
			logger.debug("/**** SERVICIO PARA INGRESAR UN LUGAR ****/");
		
		LugarDTO lugarRetDTO = new LugarDTO();
		Long nuevoLugar = new Long(0);
		
		if (lugarDTO instanceof DomicilioDTO) {
			nuevoLugar = ingresarDomicilioService.ingresarDomicilio((DomicilioDTO)lugarDTO);
		} 	
		if (lugarDTO.getLatitud()!=null || lugarDTO.getLongitud()!=null) {
			CoordenadaGeografica coordenadas = new CoordenadaGeografica();
			coordenadas.setLatitud(lugarDTO.getLatitud());
			coordenadas.setLongitud(lugarDTO.getLongitud());
			coordenadas.setLugar(new Lugar(nuevoLugar));
			coordenadaGeograficaDAO.create(coordenadas);
		}
		
		lugarRetDTO.setElementoId(nuevoLugar);		
		return lugarRetDTO;
	}	
	
	/**
	 * Obtiene la calidad del lugar para ser ingresada
	 * @param calidadDTO
	 * @return
	 */
	public Calidad obtenerCalidadLugar (CalidadDTO calidadDTO) {
		if (logger.isDebugEnabled())
			logger.debug("/**** SERVICIO PARA OBTENER LA CALIDAD DEL LUGAR ****/");
		
		Calidad calidad = new Calidad();
		calidad.setDescripcionEstadoFisico(calidadDTO.getDescripcionEstadoFisico());
		calidad.setTipoCalidad(new Valor(calidadDTO.getCalidades().getValorId()));
		return calidad;
	}

}
