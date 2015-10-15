package mx.gob.segob.nsjp.delegate.region.impl;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.delegate.region.RegionDelegate;
import mx.gob.segob.nsjp.dto.domicilio.RegionDTO;
import mx.gob.segob.nsjp.service.region.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by israel on 9/22/15.
 */
@Service("regionDelegate")
public class RegionDelegateImpl implements RegionDelegate{

    @Autowired
    private RegionService regionService;

    public List<RegionDTO> consultarTodos()throws NSJPNegocioException{
        return regionService.consultarTodos();
    }
}
