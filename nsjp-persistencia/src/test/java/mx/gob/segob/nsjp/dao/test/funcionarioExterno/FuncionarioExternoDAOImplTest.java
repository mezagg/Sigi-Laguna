/**
 * Nombre del Programa : FuncionarioExternoDAOImplTest.java
 * Autor                            : AAAV
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 31 May 2011
 * Marca de cambio        : N/A
 * Descripcion General    : Archivo de pruebas FuncionarioExternoDAOImplTest.java 
 * Programa Dependiente  :N/A
 * Programa Subsecuente :N/A
 * Cond. de ejecucion        :N/A
 * Dias de ejecucion          :N/A                             Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                       :N/A
 * Compania               :N/A
 * Proyecto                 :N/A                                 Fecha: N/A
 * Modificacion           :N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.dao.test.funcionarioExterno;

import java.util.List;

import mx.gob.segob.nsjp.dao.funcionarioexterno.FuncionarioExternoDAO;
import mx.gob.segob.nsjp.dao.test.base.BaseTestPersistencia;
import mx.gob.segob.nsjp.model.Audiencia;
import mx.gob.segob.nsjp.model.ConfInstitucion;
import mx.gob.segob.nsjp.model.FuncionarioExterno;
import mx.gob.segob.nsjp.model.Rol;

/**
 * Permite consultar funcionarios por audiencia
 * 
 * @version 1.0
 * @author AAAV
 */
public class FuncionarioExternoDAOImplTest extends
		BaseTestPersistencia<FuncionarioExternoDAO> {

	public void testConsultarFuncionarioExterno() {
		FuncionarioExterno fe = daoServcice
				.consultarFuncExternoPorClaveFuncExt(21L, 2L);
		if (fe == null) {
			logger.info("NO EXISTE FUNCIONARIO EXTERNO");
		} else {
			logger.info("fe.getFuncionarioExternoId():"
					+ fe.getFuncionarioExternoId());
		}
	}

	public void testEjecutarQueryConsultarFuncionarioExternoPorFiltro() {

		FuncionarioExterno funcionarioExterno = new FuncionarioExterno();
		funcionarioExterno.setNombre("Raul");
		funcionarioExterno.setApellidoPaterno("De la O");
		funcionarioExterno.setApellidoMaterno("   ");
		//funcionarioExterno.setFuncionarioExternoId(1L);
		//funcionarioExterno.setConfInstitucion(new ConfInstitucion(2L));
		//funcionarioExterno.setRol(new Rol(14L));
		
		
		
		List<FuncionarioExterno> funcionarioExternoList = daoServcice
				.ejecutarQueryConsultarFuncionarioExternoPorFiltro(funcionarioExterno);

		if (funcionarioExternoList != null && !funcionarioExternoList.isEmpty()) {
			logger.info("TAMANIO DE LA LISTA::::::::::::::::::::"
					+ funcionarioExternoList.size());
			for (FuncionarioExterno funcExt : funcionarioExternoList) {
				logger.info("__________________________________________");
				logger.info("funcExt.getFuncionarioExternoId():::"
						+ funcExt.getFuncionarioExternoId());
				logger.info("funcExt.getCveFuncionarioInstExt()::"
						+ funcExt.getCveFuncionarioInstExt());
				if (funcExt.getConfInstitucion() != null) {
					logger.info("funcExt.getConfInstitucion()::::"
							+ funcExt.getConfInstitucion()
									.getConfInstitucionId());
				}
				logger.info("funcExt.getNombre()::::::::::::::::"
						+ funcExt.getNombre());
				logger.info("funcExt.getApellidoPaterno():::::::"
						+ funcExt.getApellidoPaterno());
				logger.info("funcExt.getApellidoMaterno():::::::"
						+ funcExt.getApellidoMaterno());
				if (funcExt.getRol() != null) {
					logger.info("funcExt.getRol().getRolId()::::"
							+ funcExt.getRol().getRolId());
				}
				logger.info("funcExt.getArea()::::::::::::::::::"
						+ funcExt.getArea());
				logger.info("funcExt.getPuesto()::::::::::::::::"
						+ funcExt.getPuesto());
				logger.info("funcExt.getEmail():::::::::::::::::"
						+ funcExt.getEmail());
			}
		} else {
			logger.info("NO HAY DATOS");
		}

	}
	
	public void testConsultarFuncionarioExternoPorFiltroNoAsociadoALaAudiencia() {

		//Datos del filtro funcionario Externo
		FuncionarioExterno funcionarioExterno = new FuncionarioExterno();
		//funcionarioExterno.setNombre("");
		//funcionarioExterno.setApellidoPaterno("");
		//funcionarioExterno.setApellidoMaterno("C");
		//funcionarioExterno.setFuncionarioExternoId(1L);
		funcionarioExterno.setConfInstitucion(new ConfInstitucion(2L));
		funcionarioExterno.setRol(new Rol(14L));
		
		//Datos del filtro audiencia
		Audiencia audiencia = new Audiencia(793L); 
		
		
		List<FuncionarioExterno> funcionarioExternoList = daoServcice
				.consultarFuncionarioExternoPorFiltroNoAsociadoALaAudiencia(funcionarioExterno, audiencia);

		if (funcionarioExternoList != null && !funcionarioExternoList.isEmpty()) {
			logger.info("TAMANIO DE LA LISTA::::::::::::::::::::"
					+ funcionarioExternoList.size());
			for (FuncionarioExterno funcExt : funcionarioExternoList) {
				logger.info("__________________________________________");
				logger.info("funcExt.getFuncionarioExternoId():::"
						+ funcExt.getFuncionarioExternoId());
				logger.info("funcExt.getCveFuncionarioInstExt()::"
						+ funcExt.getCveFuncionarioInstExt());
				if (funcExt.getConfInstitucion() != null) {
					logger.info("funcExt.getConfInstitucion()::::"
							+ funcExt.getConfInstitucion()
									.getConfInstitucionId());
				}
				logger.info("funcExt.getNombre()::::::::::::::::"
						+ funcExt.getNombre());
				logger.info("funcExt.getApellidoPaterno():::::::"
						+ funcExt.getApellidoPaterno());
				logger.info("funcExt.getApellidoMaterno():::::::"
						+ funcExt.getApellidoMaterno());
				if (funcExt.getRol() != null) {
					logger.info("funcExt.getRol().getRolId()::::"
							+ funcExt.getRol().getRolId());
				}
				logger.info("funcExt.getArea()::::::::::::::::::"
						+ funcExt.getArea());
				logger.info("funcExt.getPuesto()::::::::::::::::"
						+ funcExt.getPuesto());
				logger.info("funcExt.getEmail():::::::::::::::::"
						+ funcExt.getEmail());
			}
		} else {
			logger.info("NO HAY DATOS");
		}

	}
}