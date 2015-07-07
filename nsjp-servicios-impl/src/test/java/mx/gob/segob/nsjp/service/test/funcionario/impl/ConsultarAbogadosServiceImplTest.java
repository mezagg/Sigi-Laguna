/**
* Nombre del Programa	: ConsultarAbogadosServiceTest.java
* Autor					: Hugo Serrano
* Compania				: Ultrasist
* Proyecto				: NSJP Fecha: 23 Jun 2011
* Marca de cambio		: N/A
* Descripcion General	: Describir el objetivo de la clase de manera breve
* Programa Dependiente  : N/A
* Programa Subsecuente	: N/A
* Cond. de ejecucion	: N/A
* Dias de ejecucion		: N/A		Horario: N/A
*                              MODIFICACIONES
*------------------------------------------------------------------------------
* Autor					:N/A
* Compania				:N/A
* Proyecto				:N/A Fecha: N/A
* Modificacion			:N/A
*------------------------------------------------------------------------------
*/
package mx.gob.segob.nsjp.service.test.funcionario.impl;


import java.util.ArrayList;
import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.catalogo.CatDistritoDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.service.funcionario.ConsultarAbogadosService;
import mx.gob.segob.nsjp.service.test.base.BaseTestServicios;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author Tattva-IT
 *
 */			 
public class ConsultarAbogadosServiceImplTest extends BaseTestServicios<ConsultarAbogadosService> {
	
	/**
	 * Prueba para consultar defensores activos por distrito
	 */
	public void testConsultarDefensoresActivosPorDistrito() {
		
		List<FuncionarioDTO> ls = new ArrayList<FuncionarioDTO>();
		try {
			CatDistritoDTO distrito = new CatDistritoDTO();
			distrito.setCatDistritoId(1L);

			ls = this.service
					.consultarDefensoresActivosPorDistrito(distrito);
		} catch (NSJPNegocioException e) {
			e.printStackTrace();
		}

		if (ls != null) {
			System.out.println("Numero de defensores encontrados "
					+ ls.size());
			for (FuncionarioDTO funcionario : ls) {
				System.out.println("Defensor:"
						+ funcionario.getNombreCompleto());
			}
		}
	}
}
