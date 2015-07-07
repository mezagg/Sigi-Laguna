/**
 * Nombre del Programa : ConsultarIndicadorServiceImplTest.java
 * Autor                            : GustavoBP
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 19/06/2012
 * Marca de cambio        : N/A
 * Descripcion General    : Clase que implementa las pruebas unitarias de los servicios que consultan los indicadores.
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
package mx.gob.segob.nsjp.service.test.indicador.impl;

import java.util.HashMap;
import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.indicador.Indicadores;
import mx.gob.segob.nsjp.dto.indicador.IndicadorDTO;
import mx.gob.segob.nsjp.service.indicador.ConsultarIndicadorService;
import mx.gob.segob.nsjp.service.test.base.BaseTestServicios;

/**
 * Clase que implementa las pruebas unitarias de los servicios que consultan los indicadores.
 * 
 * @author GustavoBP
 */
public class ConsultarIndicadorServiceImplTest extends
		BaseTestServicios<ConsultarIndicadorService> {	

	public void testConsultarIndicador(){
		//Indicador con varias columnas 
//		Indicadores indicador = Indicadores.INDICADOR_81;
		//Indicador con una sola columna
		Indicadores indicador = Indicadores.INDICADOR_92;

		HashMap<String, String> valores = new HashMap<String, String>();
		valores.put("fechaIncio", "20/06/2011");
		valores.put("fechaFin", "20/06/2012");
		
		try {
			List<Object[]> resultado = service.consultarIndicador(indicador,  valores);
			assertTrue("La lista debe de obtener un registro", !resultado.isEmpty());
			logger.info("Resultado:"+ resultado);
			
			//Verificar que es una lista con Arreglos de Objetos
			//Si NO es una lista, se debe de Generar una con dicho objeto
			if(! (resultado.get(0) instanceof Object[])){
				//Se sobre escribe la posicion por un arreglo con el unico objeto
				Object temp = resultado.get(0);
				resultado.remove(0);
				resultado.add(0,new Object[]{temp});
			}
			
			for (Object[] objects : resultado) {
				logger.info("Resultados----");
				for (int i = 0; i < objects.length; i++) {
					logger.info("Resultado:"+ objects[i]);					
				}
			}
			
		} catch (NSJPNegocioException e) {
			logger.info(e.getMessage(), e);
		}
	}

	public void testConsultarIndicadorPorInstitucionActual(){
		
		try {
			List<IndicadorDTO>  resultado = service.consultarIndicadorPorInstitucionActual(false);
			assertTrue("La lista debe de obtener un registro", !resultado.isEmpty());
			for (IndicadorDTO indicadorDTO : resultado) {
				logger.info(" IndicadorDTO:"+ indicadorDTO);
			}
			
		} catch (NSJPNegocioException e) {
			logger.info(e.getMessage(), e);
		}
		
	}
}
