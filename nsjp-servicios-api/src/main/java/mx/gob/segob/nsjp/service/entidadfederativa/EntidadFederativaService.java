package mx.gob.segob.nsjp.service.entidadfederativa;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.domicilio.EntidadFederativaDTO;

import java.util.List;

/**
 * Created by israel on 8/5/15.
 */
public interface EntidadFederativaService {

    public List<EntidadFederativaDTO> consultarEndidadesFederativasTodas() throws NSJPNegocioException;
}
