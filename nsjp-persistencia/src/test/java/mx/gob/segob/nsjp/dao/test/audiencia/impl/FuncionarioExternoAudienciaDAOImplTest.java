/**
 * Nombre del Programa 		: FuncionarioExternoDAOImplTest.java
 * Autor 					: AlejandroGA
 * Compania 					: Ultrasist
 * Proyecto 					: NSJP 							Fecha: 01 Marzo 2013
 * Marca de cambio 			: N/A
 * Descripcion General 		: Tests del FuncionarioExternoDAO
 * Programa Dependiente 		: N/A
 * Programa Subsecuente 		: N/A
 * Cond. de ejecucion 		: N/A
 * Dias de ejecucion 		: N/A 							Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor 					: N/A
 * Compania 					: N/A
 * Proyecto 					: N/A 							Fecha: N/A
 * Modificacion 				: N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.dao.test.audiencia.impl;

import java.util.List;

import mx.gob.segob.nsjp.comun.enums.seguridad.Roles;
import mx.gob.segob.nsjp.dao.funcionarioexterno.FuncionarioExternoAudienciaDAO;
import mx.gob.segob.nsjp.dao.test.base.BaseTestPersistencia;
import mx.gob.segob.nsjp.model.FuncionarioExterno;

/**
 * Tests del FuncionarioExternoDAO
 * 
 * @author AlejandroGA
 * @version 1.0
 */
public class FuncionarioExternoAudienciaDAOImplTest extends
		BaseTestPersistencia<FuncionarioExternoAudienciaDAO> {

	public void testConsultarFuncionarioExternoPorRol() {

		List<FuncionarioExterno> funcionarioExternoList = daoServcice
				.consultarFuncionarioExternoPorRol(Roles.DEFENSOR.getValorId(),
						792L);

		if (funcionarioExternoList != null && !funcionarioExternoList.isEmpty()) {

			for (FuncionarioExterno funcionarioExterno : funcionarioExternoList) {
				logger.info("Funcionario Externo...........");
				logger.info("Nombre:" + funcionarioExterno.getNombre());
				logger.info("ApPaterno:"
						+ funcionarioExterno.getApellidoPaterno());
				logger.info("ApMaterno:"
						+ funcionarioExterno.getApellidoMaterno());
				logger.info("Clave Inst Externa:"
						+ funcionarioExterno.getCveFuncionarioInstExt());
				if (funcionarioExterno.getRol() != null) {
					logger.info("Nombre Rol:"
							+ funcionarioExterno.getRol().getNombreRol());
				}
				if (funcionarioExterno.getConfInstitucion() != null) {
					logger.info("Nombre Institucion:"
							+ funcionarioExterno.getConfInstitucion()
									.getNombreInst());
				}

			}
		} else {
			logger.info("NO HAY DATOS");
		}
	}

}
