/**
* Nombre del Programa 		: ConsultarObjetoEstudioServiceImpl.java
* Autor 					: EdgarTE
* Compania 					: Ultrasist
* Proyecto 					: NSJP 							Fecha: 11 Sep 2012
* Marca de cambio 			: N/A
* Descripcion General 		: Describir el objetivo de la clase de manera breve
* Programa Dependiente 		: N/A
* Programa Subsecuente 		: N/A
* Cond. de ejecucion 		: N/A
* Dias de ejecucion 		: N/A 							Horario: N/A
*                              MODIFICACIONES
*------------------------------------------------------------------------------
* Autor 					: N/A
* Compania 					: N/A
* Proyecto 					: N/A 							Fecha: N/A
* Modificacion 				: N/A
*------------------------------------------------------------------------------
*/
package mx.gob.segob.nsjp.service.objeto.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.objeto.ObjetoEstudioDAO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.objeto.ObjetoEstudioDTO;
import mx.gob.segob.nsjp.model.ObjetoEstudio;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.objeto.ConsultarObjetoEstudioService;
import mx.gob.segob.nsjp.service.objeto.impl.transform.ObjetoEstudioTransformer;
import mx.gob.segob.nsjp.service.usuario.impl.transformer.ValorTransformer;


/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author EdgarTE
 *
 */
@Service
@Transactional
public class ConsultarObjetoEstudioServiceImpl implements
		ConsultarObjetoEstudioService {
	
	@Autowired
	private ObjetoEstudioDAO objetoEstudioDAO;

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.objeto.ConsultarObjetoEstudioService#consultarEstudiosPorTipoObjeto(mx.gob.segob.nsjp.dto.catalogo.ValorDTO)
	 */
	@Override
	public List<ObjetoEstudioDTO> consultarEstudiosPorTipoObjeto(
			ValorDTO tipoObj) throws NSJPNegocioException{
		
		if (tipoObj == null 
				|| tipoObj.getIdCampo() == null 
				|| tipoObj.getIdCampo() < 1L){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		
		Valor obj = ValorTransformer.transformar(tipoObj);
		
		List<ObjetoEstudio> objetos = objetoEstudioDAO.consultarEstudiosPorTipoObjeto(obj);
		List<ObjetoEstudioDTO> objetosDTO = null;
		
		if (objetos != null && !objetos.isEmpty()){
			objetosDTO = new ArrayList<ObjetoEstudioDTO>();
			for (ObjetoEstudio objEst : objetos){
				objetosDTO.add(ObjetoEstudioTransformer.transformar(objEst));
			}
		}
		return objetosDTO;
	}

}
