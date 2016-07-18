/**
 * 
 */
package mx.gob.segob.nsjp.dao.test.funcion.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.test.base.BaseTestPersistencia;
import mx.gob.segob.nsjp.dao.usuario.FuncionDAO;
import mx.gob.segob.nsjp.model.ElementoMenu;
import mx.gob.segob.nsjp.model.Funcion;
import mx.gob.segob.nsjp.model.Usuario;

/**
 * @author LuisMG
 * 
 */
public class FuncionDAOImplTest extends BaseTestPersistencia<FuncionDAO> {

	public void testConsultarFuncionesXUsuario() throws NSJPNegocioException {
		Usuario usuario = new Usuario(1L);


		List<Funcion> funciones = daoServcice
				.consultarFuncionesXUsuario(usuario);
		assertNotNull(funciones);
		for (int i = 0; i < funciones.size(); i++) {
			System.out
					.println("Funcion id: " + funciones.get(i).getFuncionId());
			System.out.println("Funcion Nombre: "
					+ funciones.get(i).getNombreFuncion());
		}
	}

	public void testValidarFuncionXUsuario() throws NSJPNegocioException {
		Usuario usuario = new Usuario(178L);
		Funcion funcion = new Funcion(275L);
		try {


			List<Funcion> funciones = daoServcice.validarFuncionXUsuario(68L
					, funcion.getFuncionId());
			assertNotNull(funciones);
			for (int i = 0; i < funciones.size(); i++) {
				System.out.println("Funcion id: "
						+ funciones.get(i).getFuncionId());
				System.out.println("Funcion Nombre: "
						+ funciones.get(i).getNombreFuncion());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
