package mx.gob.segob.nsjp.delegate.entidadfederativa.impl;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.delegate.entidadfederativa.EntidadFederativaDelegate;
import mx.gob.segob.nsjp.dto.domicilio.EntidadFederativaDTO;
import mx.gob.segob.nsjp.service.entidadfederativa.EntidadFederativaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by israel on 8/4/15.
 */
@Service("entidadFederativaDelegate")
public class EntidadFederativaDelegateImpl implements EntidadFederativaDelegate {

    @Autowired
    private EntidadFederativaService entidadFederativaService;

    @Override
    public List<EntidadFederativaDTO> consultarEndidadesFederativasTodas() throws NSJPNegocioException {
        return entidadFederativaService.consultarEndidadesFederativasTodas();
    }
}
