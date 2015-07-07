/**
 * 
 */
package mx.gob.segob.nsjp.service.audiencia;

import mx.gob.segob.nsjp.dto.catalogo.CatDistritoDTO;

/**
 * @author JorgeFO
 *
 */
public interface BuscaDistritosService {
	
	public CatDistritoDTO buscaDistritosPorDiscriminante(Long discriminante);

}
