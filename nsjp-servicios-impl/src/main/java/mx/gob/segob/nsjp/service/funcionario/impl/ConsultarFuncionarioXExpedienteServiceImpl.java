/**
 * Nombre del Programa : ConsultarFuncionarioXExpedienteServiceImpl.java
 * Autor                            : Jacob Lobaco
 * Compania                         : Ultrasist
 * Proyecto                         : NSJP                    Fecha: 22-jun-2011
 * Marca de cambio        : N/A
 * Descripcion General    : N/A
 * Programa Dependient    :N/A
 * Programa Subsecuente   :N/A
 * Cond. de ejecucion     :N/A
 * Dias de ejecucion      :N/A                                Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                            :N/A
 * Compania                         :N/A
 * Proyecto                         :N/A                      Fecha: N/A
 * Modificacion           :N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.service.funcionario.impl;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.funcionario.FuncionarioDAO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.service.expediente.impl.transform.ExpedienteTransformer;
import mx.gob.segob.nsjp.service.funcionario.ConsultarFuncionarioXExpedienteService;
import mx.gob.segob.nsjp.service.funcionario.impl.transform.FuncionarioTransformer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @version 1.0
 * @author Jacob Lobaco
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class ConsultarFuncionarioXExpedienteServiceImpl implements ConsultarFuncionarioXExpedienteService {

    /**
      * Logger de la clase.
      */
    private final static Logger logger = Logger
            .getLogger(ConsultarFuncionarioXExpedienteServiceImpl.class);

    @Autowired
    private FuncionarioDAO funcionarioDAO;

    @Override
    public FuncionarioDTO consultarFuncionarioXExpediente(ExpedienteDTO expedienteDTO) throws NSJPNegocioException {
    	NumeroExpediente numeroExpediente = ExpedienteTransformer.obtenerDeExpedienteDTO(expedienteDTO);
        Funcionario funcionario = funcionarioDAO.consultarFuncionarioXExpediente(numeroExpediente);
        return funcionario != null ? FuncionarioTransformer.transformarFuncionario(funcionario): null;
    }
   
}
