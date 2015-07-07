/**
 * 
 */
package mx.gob.segob.nsjp.service.ssp.informepolicial.impl.transform;

import mx.gob.segob.nsjp.dto.ssp.informepolicial.InformePolicialHomologadoDTO;
import mx.gob.segob.nsjp.dto.ssp.informepolicial.InvolucradoIPHDTO;
import mx.gob.segob.nsjp.model.ssp.InvolucradoIph;
import mx.gob.segob.nsjp.service.involucrado.impl.transform.InvolucradoTransformer;

/**
 * @author adrian
 *
 */
public class InvolucradoIPHTransformer {

	public static InvolucradoIPHDTO transformarInvolucradoIPH(
			InvolucradoIph invIph) {
		InvolucradoIPHDTO dto=new InvolucradoIPHDTO();
		
		InformePolicialHomologadoDTO informeDTO=new InformePolicialHomologadoDTO();
		informeDTO.setInformePolicialHomologadoId(invIph.getInformePolicialHomologado().getInformePolicialHomologadoId());
		
		dto.setInformePolicialHomologado(informeDTO);
//		dto.setInvolucrado(InvolucradoTransformer.transformarInvolucrado(invIph.getInvolucrado()));
		
		return dto;
	}

}
