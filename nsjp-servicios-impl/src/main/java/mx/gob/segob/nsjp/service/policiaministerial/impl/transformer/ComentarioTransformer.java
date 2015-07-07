/**
 * 
 */
package mx.gob.segob.nsjp.service.policiaministerial.impl.transformer;

import java.util.Date;

import mx.gob.segob.nsjp.dto.policiaministerial.ComentarioDTO;
import mx.gob.segob.nsjp.dto.policiaministerial.LineaInvestigacionDTO;
import mx.gob.segob.nsjp.model.Comentario;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.model.LineaInvestigacion;
import mx.gob.segob.nsjp.service.funcionario.impl.transform.FuncionarioTransformer;

/**
 * @author adrian
 *
 */
public class ComentarioTransformer {

	public static Comentario transformarComentarioDTO(
			ComentarioDTO dto) {
		Date getFechaCreacion=(dto.getFechaCreacion()!=null)?dto.getFechaCreacion():new Date();
		Comentario resp=new Comentario(getFechaCreacion, dto.getDescripcion());
		
		resp.setComentarioId(dto.getComentarioId());
		if(dto.getLineaInvestigacionDTO()!=null)
			resp.setLineaInvestigacion(new LineaInvestigacion(dto.getLineaInvestigacionDTO().getLineaInvestigacionId()));
		if(dto.getFuncionarioDTO()!=null)
			resp.setFuncionario(new Funcionario(dto.getFuncionarioDTO().getClaveFuncionario()));
		return resp;
	}

	public static ComentarioDTO transformarComentario(Comentario scr) {
		ComentarioDTO dto=new ComentarioDTO();
		
		dto.setComentarioId(scr.getComentarioId());
		dto.setFechaCreacion(scr.getFechaCreacion());
		dto.setDescripcion(scr.getDescripcion());
		
		if(scr.getLineaInvestigacion()!=null)
			dto.setLineaInvestigacionDTO(new LineaInvestigacionDTO(scr.getLineaInvestigacion().getLineaInvestigacionId()));
		if(scr.getFuncionario()!=null)
			dto.setFuncionarioDTO(FuncionarioTransformer.transformarFuncionario(scr.getFuncionario()));
		
		return dto;
	}

}
