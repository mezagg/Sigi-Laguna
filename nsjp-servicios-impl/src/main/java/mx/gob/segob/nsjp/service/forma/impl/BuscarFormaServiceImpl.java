/**
* Nombre del Programa : BuscarFormaServiceImpl.java
* Autor                            : Emigdio Hern�ndez
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 30/05/2011
* Marca de cambio        : N/A
* Descripcion General    : Implementaci�n del servicio de negocio para la busqueda de formas de impresi�n de documentos
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
package mx.gob.segob.nsjp.service.forma.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.forma.FormaDAO;
import mx.gob.segob.nsjp.dto.forma.FormaDTO;
import mx.gob.segob.nsjp.model.Forma;
import mx.gob.segob.nsjp.service.forma.BuscarFormaService;
import mx.gob.segob.nsjp.service.forma.impl.transform.FormaTransformer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementaci�n del servicio de negocio para la busqueda de formas de impresi�n de documentos
 * @author Emigdio Hern�ndez
 *
 */
@Service
@Transactional(readOnly=true)
public class BuscarFormaServiceImpl implements BuscarFormaService {

	@Autowired
	FormaDAO formaDAO;
	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.forma.BuscarFormaService#buscarForma(java.lang.Long)
	 */
	@Override
	public FormaDTO buscarForma(Long formaId) throws NSJPNegocioException {
	
		return FormaTransformer.transformarForma(formaDAO.read(formaId));
	
	}
	@Override
	public List<FormaDTO> consultarTodasLasForma() throws NSJPNegocioException {
		List<FormaDTO> lstFormaDTO = new ArrayList<FormaDTO>();
		List<Forma> lstFormas = formaDAO.findAll(null, true);
		for (Forma forma : lstFormas) {
			lstFormaDTO.add(FormaTransformer.transformarForma(forma));
		}
		return lstFormaDTO;
	}

}
