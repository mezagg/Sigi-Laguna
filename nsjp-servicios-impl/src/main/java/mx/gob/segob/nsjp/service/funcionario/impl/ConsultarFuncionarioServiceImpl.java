package mx.gob.segob.nsjp.service.funcionario.impl;

import java.util.ArrayList;
import java.util.List;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.funcionario.FuncionarioDAO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.service.funcionario.ConsultarFuncionarioService;
import mx.gob.segob.nsjp.service.funcionario.impl.transform.FuncionarioTransformer;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ConsultarFuncionarioServiceImpl implements
        ConsultarFuncionarioService {
    private static final Logger logger = Logger.getLogger(ConsultarFuncionarioServiceImpl.class);
    
    @Autowired
    private FuncionarioDAO funcionarioDAO;
    
    @Override
    public FuncionarioDTO obtenerFuncionarioDTO(FuncionarioDTO funcionario)
            throws NSJPNegocioException {
        Funcionario func = funcionarioDAO.read(funcionario.getClaveFuncionario());
        return FuncionarioTransformer.transformarFuncionario(func);
    }
    
    @Override
    public List<FuncionarioDTO> consultarSubordinadosUAVD() throws NSJPNegocioException {
        List<Funcionario> subordinadosBD = funcionarioDAO.consultarSubordinadosUAVD();
        if (subordinadosBD != null) {
            return FuncionarioTransformer.transformarFuncionarios(subordinadosBD);
        }
        return new ArrayList<FuncionarioDTO>();
    }
    
}
