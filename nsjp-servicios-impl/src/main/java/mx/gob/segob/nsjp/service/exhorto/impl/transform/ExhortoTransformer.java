package mx.gob.segob.nsjp.service.exhorto.impl.transform;

import java.util.ArrayList;
import java.util.List;

import mx.gob.segob.nsjp.dto.exhorto.ExhortoDTO;
import mx.gob.segob.nsjp.model.Documento;
import mx.gob.segob.nsjp.model.Exhorto;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.service.funcionario.impl.transform.FuncionarioTransformer;
import mx.gob.segob.nsjp.service.usuario.impl.transformer.ValorTransformer;

import org.apache.log4j.Logger;

public class ExhortoTransformer {

    @SuppressWarnings("unused")
	private static final Logger logger = Logger
            .getLogger(ExhortoTransformer.class);
    
    
    public static ExhortoDTO transformarExhortoToExhortoDTO(Exhorto from) {
    	
    	ExhortoDTO to = new ExhortoDTO();
    	
        if (from == null) {
            return null;
        }
        
        to.setDiligencia(from.getDiligencia());
        to.setDocumento(from.getDocumento().getDocumentoId());
        to.setEsGuardadoParcial(from.getDocumento().getEsGuardadoParcial());
        to.setExhortoId(from.getExhortoId());
        to.setExpediente(from.getExpediente());
        to.setFechaDiligencia(from.getFechaDiligencia());
        to.setFechaEnvio(from.getFechaEnvio());
        to.setFechaVencida(from.getFechaVencida());
        to.setFolio(from.getFolio());
        to.setFuncionario(FuncionarioTransformer.transformarFuncionarioBasico(from.getFuncionario()));
        to.setValorEstatus(ValorTransformer.transformar(from.getValorEstatus()));
        
		if(from.getEsGuardadoParcial()!=null){
			to.setEsGuardadoParcial(from.getEsGuardadoParcial());				
		}
        
        return to;
    }

	public static List<ExhortoDTO> transformarExhortoToExhortoDTOList(List<Exhorto> from){
		
		List<ExhortoDTO> to = null;
		
		if(from == null || from.size() == 0)
			return to;
		else{
			to = new ArrayList<ExhortoDTO>();
			for (Exhorto exhorto : from) {
				to.add(transformarExhortoToExhortoDTO(exhorto));
			}
			return to;
		}
	}
	
    @SuppressWarnings("unused")
	public static Exhorto transformarExhortoDTOToExhorto(ExhortoDTO from) {
    	
    	Exhorto to = new Exhorto();
    	
        if (to == null) {
            return null;
        }
        
        to.setDiligencia(from.getDiligencia());
        to.setDocumento( new Documento(from.getDocumento()));
        to.setExhortoId(from.getExhortoId());
        to.setExpediente(from.getExpediente());
        to.setFechaDiligencia(from.getFechaDiligencia());
        to.setFechaEnvio(from.getFechaEnvio());
        to.setFechaVencida(from.getFechaVencida());
        to.setFolio(from.getFolio());
//        to.setFuncionario(FuncionarioTransformer.transformarFuncionario(from.getFuncionario()));
		if (from.getFuncionario() != null){
			to.setFuncionario(new Funcionario(from.getFuncionario()
					.getClaveFuncionario()));
		}
		
        to.setValorEstatus(ValorTransformer.transformar(from.getValorEstatus()));
        to.setEsGuardadoParcial(from.getEsGuardadoParcial());
        
        return to;
    }
    
	public static List<Exhorto> transformarExhortoDTOToExhortoList(List<ExhortoDTO> from){
		
		List<Exhorto> to = null;
		
		if(from == null || from.size() == 0)
			return to;
		else{
			to = new ArrayList<Exhorto>();
			for (ExhortoDTO exhorto : from) {
				to.add(transformarExhortoDTOToExhorto(exhorto));
			}
			return to;
		}
	}
}
