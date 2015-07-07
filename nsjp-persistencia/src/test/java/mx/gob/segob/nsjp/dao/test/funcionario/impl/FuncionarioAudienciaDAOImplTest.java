package mx.gob.segob.nsjp.dao.test.funcionario.impl;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.funcionario.FuncionarioAudienciaDAO;
import mx.gob.segob.nsjp.dao.test.base.BaseTestPersistencia;
import mx.gob.segob.nsjp.model.FuncionarioAudiencia;
import mx.gob.segob.nsjp.model.FuncionarioAudienciaId;

/**
 * Permite consultar funcionarios por audiencia
 * @version 1.0
 * @author rgama
 * 
 */
public class FuncionarioAudienciaDAOImplTest extends
		BaseTestPersistencia<FuncionarioAudienciaDAO> {

	public void testConsultarCoordinadorDefensores() {

		FuncionarioAudiencia loFuncionarioAudiencia = new FuncionarioAudiencia(
				new FuncionarioAudienciaId(286L, 22L), null, null);
		daoServcice.delete(loFuncionarioAudiencia);

	}

	public void testConsultarFuncionariosPorAudiencia() {
		daoServcice.consultarFuncionariosPorAudiencia(286L);
	}
	
	public void testActualizarInvolucradosNoTitularesPorEspecialidad() {
		try {
			daoServcice.actualizarInvolucradosNoTitularesPorEspecialidad(176L, 661L, 1730L);
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
}