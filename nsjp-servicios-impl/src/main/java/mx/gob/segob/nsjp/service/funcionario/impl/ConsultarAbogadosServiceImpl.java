/**
 * Nombre del Programa	: ConsultarAbogadosServiceImpl.java
 * Autor                : cesarAgustin
 * Compania             : Ultrasist
 * Proyecto             : NSJP                    Fecha: 18 May 2011
 * Marca de cambio      : N/A
 * Descripcion General  : Implementacion de metodos del servicio de busqueda de abogados defensores
 * Programa Dependiente :N/A
 * Programa Subsecuente :N/A
 * Cond. de ejecucion   :N/A
 * Dias de ejecucion    :N/A                             Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                 :N/A
 * Compania              :N/A
 * Proyecto              :N/A                                 Fecha: N/A
 * Modificacion          :N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.service.funcionario.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.funcionario.DefensorDAO;
import mx.gob.segob.nsjp.dto.catalogo.CatDistritoDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.service.funcionario.ConsultarAbogadosService;
import mx.gob.segob.nsjp.service.funcionario.impl.transform.FuncionarioTransformer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementacion de metodos del servicio de busqueda de abogados defensores.
 * 
 * @version 1.0
 * @author cesarAgustin
 * 
 */
@Service
@Transactional
public class ConsultarAbogadosServiceImpl implements ConsultarAbogadosService {

	@Autowired
	private DefensorDAO defensorDAO;

	private final static Logger logger = Logger
			.getLogger(ConsultarAbogadosServiceImpl.class);

	/**
	 * @deprecated
	 */
	@Override
	public List<FuncionarioDTO> consultarDefensoresActivos()
			throws NSJPNegocioException {

		return null;
	}

	
	@Override
	public List<FuncionarioDTO> consultarDefensoresActivosPorDistrito(
			CatDistritoDTO distrito) throws NSJPNegocioException {

		if (logger.isDebugEnabled()) {
			logger.debug("SERVICIO PARA LA CONSULTA DE FUNCIONARIOS DEFENSORES");
		}

		List<Funcionario> listaFuncionarios = new ArrayList<Funcionario>();
		
		if (distrito != null && distrito.getCatDistritoId() != null
				&& distrito.getCatDistritoId() > 0L) {
			listaFuncionarios = this.defensorDAO
					.consultarDefensoresActivosPorCatDistritoId(distrito
							.getCatDistritoId());
		} else {
			listaFuncionarios = this.defensorDAO
					.consultarDefensoresActivosPorCatDistritoId(null);
		}

		logger.debug("NUMERO DE DEFENSORES ENCONTRADOS....."
				+ listaFuncionarios != null ? listaFuncionarios.size(): "0");

		List<FuncionarioDTO> listaFuncionarioDto = new ArrayList<FuncionarioDTO>();

		for (Funcionario funcionario : listaFuncionarios) {
			listaFuncionarioDto.add(FuncionarioTransformer
					.transformarFuncionario(funcionario));
		}

		return listaFuncionarioDto;
	}
	

	@Override
	public List<FuncionarioDTO> obtenerDefensoresConCargaMenor(
			List<FuncionarioDTO> ldefensoresDTO) throws NSJPNegocioException {
		if (logger.isDebugEnabled())
			logger.debug("Servicio para obtener un subconjunto de funcionarios con carga de trabajo menor.");

		if (ldefensoresDTO == null || ldefensoresDTO.isEmpty())
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);

		// La solucion es hacer q la clase de FuncionarioDTO implemente la
		// interfaz Comparable e implementar el metodo de compareTo
		// por medio de atributo "cargaTrabajo" y hacer uso de
		// Collections.sort(ldefensoresDTO). No se modifico la clase de
		// Funcionario
		Collections.sort(ldefensoresDTO);

		// Para obtener un subconjunto se tomará bajo una promedio de las Carga
		// de Trabajo
		double suma = 0d;
		for (FuncionarioDTO funcionarioDTO : ldefensoresDTO) {
			suma += funcionarioDTO.getCargaTrabajo();
		}
		double promedio = suma / ldefensoresDTO.size();

		for (int i = 0; i < ldefensoresDTO.size(); i++) {
			FuncionarioDTO funcionario = ldefensoresDTO.get(i);
			if (funcionario.getCargaTrabajo() > promedio) {
				ldefensoresDTO.remove(funcionario);
				i--;
			}
		}

		return ldefensoresDTO;
	}

	@Override
	public Object asignarAleatoriamenteElemento(Object[] lista)
			throws NSJPNegocioException {
		Random aleatorio = new Random();
		if (logger.isDebugEnabled())
			logger.debug("Servicio para obtener un objeto aelatoriamente de una lista de objetos.");

		if (lista == null || lista.length == 0)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);

		return (lista.length == 1 ? lista[0] : lista[aleatorio
				.nextInt(lista.length - 1)]);
	}
}
