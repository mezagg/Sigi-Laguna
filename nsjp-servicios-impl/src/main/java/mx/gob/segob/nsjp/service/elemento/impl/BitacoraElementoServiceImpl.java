/**
* Nombre del Programa 		: BitacoraElementoServiceImpl.java
* Autor 					: EdgarAT
* Compania 					: Ultrasist
* Proyecto 					: NSJP 								Fecha: 07/11/2012
* Marca de cambio 			: N/A
* Descripcion General 		: Describir el objetivo de la clase de manera breve
* Programa Dependiente 		: N/A
* Programa Subsecuente 		: N/A
* Cond. de ejecucion 		: N/A
* Dias de ejecucion 		: N/A 								Horario: N/A
*                              MODIFICACIONES
*------------------------------------------------------------------------------
* Autor 					: N/A
* Compania 					: N/A
* Proyecto 					: N/A 								Fecha: N/A
* Modificacion 				: N/A
*------------------------------------------------------------------------------
*/
package mx.gob.segob.nsjp.service.elemento.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.elemento.BitacoraElementoDAO;
import mx.gob.segob.nsjp.dto.elemento.BitacoraElementoDTO;
import mx.gob.segob.nsjp.model.BitacoraElemento;
import mx.gob.segob.nsjp.service.elemento.BitacoraElementoService;
import mx.gob.segob.nsjp.service.elemento.impl.transform.BitacoraElementoTransformer;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author EdgarAT
 *
 */
@Service
@Transactional
public class BitacoraElementoServiceImpl implements BitacoraElementoService {

	@Autowired
	BitacoraElementoDAO bitacoraElementoDAO;
	
	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.elemento.BitacoraElementoService#consultarBitacoraElementos(java.util.List)
	 */
	@Override
	public Map<Long,BitacoraElementoDTO> consultarBitacoraElementos(
			List<BitacoraElementoDTO> elementos) throws NSJPNegocioException {
		Map<Long,BitacoraElementoDTO> bitacoras = null;
		if (elementos != null 
				&& !elementos.isEmpty()){
			List<BitacoraElemento> elementosConsulta = new ArrayList<BitacoraElemento>();
			for (BitacoraElementoDTO bitacora : elementos){
				elementosConsulta.add(BitacoraElementoTransformer.transformar(bitacora));
			}
			List <BitacoraElemento> bitacorasConsultadas = bitacoraElementoDAO.consultarBitacoraElementos(elementosConsulta);
			if (bitacorasConsultadas != null
					&& !bitacorasConsultadas.isEmpty()){
				bitacoras = new HashMap<Long, BitacoraElementoDTO>();
				for (BitacoraElemento bit : bitacorasConsultadas){
					bitacoras.put(bit.getElemento().getElementoId(), BitacoraElementoTransformer.transformar(bit));
				}
			}
		}
		return bitacoras;
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.elemento.BitacoraElementoService#consultarBitacoraXElemento(mx.gob.segob.nsjp.dto.elemento.BitacoraElementoDTO)
	 */
	@Override
	public BitacoraElementoDTO consultarBitacoraXElemento(
			BitacoraElementoDTO elemento) throws NSJPNegocioException {
		BitacoraElementoDTO bitacoraConsultada = null;
		if (elemento != null){
			BitacoraElemento bitacoraBD = bitacoraElementoDAO.consultarBitacoraXElemento(BitacoraElementoTransformer.transformar(elemento));
			if (bitacoraBD != null){
				bitacoraConsultada = BitacoraElementoTransformer.transformar(bitacoraBD);
			}
		}
		return bitacoraConsultada;
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.elemento.BitacoraElementoService#actualizaBitacoraElemento(mx.gob.segob.nsjp.dto.elemento.BitacoraElementoDTO)
	 */
	@Override
	public BitacoraElementoDTO actualizaBitacoraElemento(
			BitacoraElementoDTO bitacoraElemento) throws NSJPNegocioException{
		BitacoraElementoDTO bitacoraActualizada = null;
		if (bitacoraElemento != null){
			if (bitacoraElemento.getBitacoraElementoId() != null
					&& bitacoraElemento.getBitacoraElementoId() > 0L){
				// En el caso de que se cuente con el id de la bit&aacute;cora, &eacute;sta se consulta y se actualiza la info.
				BitacoraElemento bitacoraBD = bitacoraElementoDAO.read(bitacoraElemento.getBitacoraElementoId());
				bitacoraBD = BitacoraElementoTransformer.mergeForUpdate(bitacoraElemento, bitacoraBD);
				bitacoraElementoDAO.update(bitacoraBD);
				bitacoraActualizada = bitacoraElemento;
			}else{
				if (bitacoraElemento.getElemento() != null
						&& bitacoraElemento.getElemento().getElementoId() != null
						&& bitacoraElemento.getElemento().getElementoId() > 0L){
					//Se busca la bit&aacute;cora en base al identificador del elemento si se cuenta con &eacute;l
					BitacoraElemento bitacoraBD = bitacoraElementoDAO.consultarBitacoraXElemento
						(BitacoraElementoTransformer.transformar(bitacoraElemento));
					if (bitacoraBD != null){
						//Si se encuentra la bit&aacute;cora, se actualiza con la nueva informaci&oacute;
						bitacoraBD = BitacoraElementoTransformer.mergeForUpdate(bitacoraElemento, bitacoraBD);
						bitacoraElementoDAO.update(bitacoraBD);
						bitacoraActualizada = BitacoraElementoTransformer.transformar(bitacoraBD);
					}else{
						//No se encontr&oacute; la bit&aacute;cora, por lo que se crea una nueva.
						bitacoraActualizada = persisteBitacora(bitacoraElemento);
					}
				}//Se omite la clausula del else, ya que siempre se debe de contar con un elemento.
			}
		}
		return bitacoraActualizada;
	}
	
	/**
	 * M&eacute;todo utilitario que lleva a cabo la persistencia de una bit&aacute;cora 
	 * asociada a un elemento dentro de la Base de Datos
	 * @param bitacoraElemento - El DTO con la informaci&oacute;n que se va a actulizar 
	 * 							 dentro de la base de datos.
	 * @return BitacoraElementoDTO - El DTO persistido con el identificador que se le
	 * 								 asign&oacute; dentro de la base de datos.
	 */
	private BitacoraElementoDTO persisteBitacora(BitacoraElementoDTO bitacoraElemento){
		BitacoraElemento bitacoraBD = BitacoraElementoTransformer.transformar(bitacoraElemento);
		Long id = bitacoraElementoDAO.create(bitacoraBD);
		bitacoraElemento.setBitacoraElementoId(id);
		return bitacoraElemento;
	}
}
