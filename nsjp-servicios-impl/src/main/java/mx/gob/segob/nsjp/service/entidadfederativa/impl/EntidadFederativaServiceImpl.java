package mx.gob.segob.nsjp.service.entidadfederativa.impl;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.domicilio.EntidadFederativaDAO;
import mx.gob.segob.nsjp.dto.domicilio.EntidadFederativaDTO;
import mx.gob.segob.nsjp.model.EntidadFederativa;
import mx.gob.segob.nsjp.service.entidadfederativa.EntidadFederativaService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by israel on 8/4/15.
 */
@Service
@Transactional
public class EntidadFederativaServiceImpl implements EntidadFederativaService{

    private final static Logger logger = Logger.getLogger(EntidadFederativaServiceImpl.class);

    @Autowired
    private EntidadFederativaDAO entidadFederativaDAO;

    @Override
    public List<EntidadFederativaDTO> consultarEndidadesFederativasTodas() throws NSJPNegocioException {

        List<EntidadFederativa> entidadFederativas =  entidadFederativaDAO.consultarTodos();


        List<EntidadFederativaDTO> entidadFederativaDTOs = new ArrayList<EntidadFederativaDTO>();
        for (EntidadFederativa entidadFederativa: entidadFederativas){

        }
        return null;
    }
}
