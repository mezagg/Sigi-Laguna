/**
* Nombre del Programa : IngresarLugarServiceImplTest.java
* Autor                            : cesarAgustin
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 15 Jun 2011
* Marca de cambio        : N/A
* Descripcion General    : Prueba unitaria para ingresar un Lugar
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
package mx.gob.segob.nsjp.service.test.lugar.impl;

import java.util.Date;

import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.domicilio.AsentamientoDTO;
import mx.gob.segob.nsjp.dto.domicilio.CiudadDTO;
import mx.gob.segob.nsjp.dto.domicilio.DomicilioDTO;
import mx.gob.segob.nsjp.dto.domicilio.DomicilioExtranjeroDTO;
import mx.gob.segob.nsjp.dto.domicilio.EntidadFederativaDTO;
import mx.gob.segob.nsjp.dto.domicilio.LugarDTO;
import mx.gob.segob.nsjp.dto.domicilio.MunicipioDTO;
import mx.gob.segob.nsjp.dto.elemento.CalidadDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.service.lugar.IngresarLugarService;
import mx.gob.segob.nsjp.service.test.base.BaseTestServicios;

/**
 * Prueba unitaria para ingresar un Lugar.
 * @version 1.0
 * @author cesarAgustin
 *
 */
public class IngresarLugarServiceImplTest extends
		BaseTestServicios<IngresarLugarService> {

	public void testIngresarLugar() {
		
		LugarDTO respuesta = new LugarDTO();
		DomicilioDTO domicilioDTO = new DomicilioDTO();
		DomicilioExtranjeroDTO domicilioExtranjeroDTO = new DomicilioExtranjeroDTO();
		
		int condicion = 1;
		
		CalidadDTO calidadDTO = new CalidadDTO();
		calidadDTO.setDescripcionEstadoFisico("En buen estado lugar");
		calidadDTO.setValorIdCalidad(new ValorDTO(Calidades.LUGAR_HECHOS.getValorId()));
		calidadDTO.setCalidades(Calidades.LUGAR_HECHOS);
		
		domicilioDTO.setValorCalleId(new ValorDTO(96L));
		domicilioDTO.setEntidadDTO(new EntidadFederativaDTO(23L));
		domicilioDTO.setCiudadDTO(new CiudadDTO());
		domicilioDTO.setMunicipioDTO(new MunicipioDTO(25L));
		domicilioDTO.setValorIdElemento(new ValorDTO(31L));
		domicilioDTO.setCalle("calleLugar1");
		domicilioDTO.setDescripcion("descripcion domicilio lugar 1");
		domicilioDTO.setFechaCreacionElemento(new Date());
		domicilioDTO.setEntreCalle1("entre calle lugar 1");
		domicilioDTO.setEntreCalle2("entre calle lugar 2");
		domicilioDTO.setAlias("Ranchito lugar ");	
		domicilioDTO.setCalidadDTO(calidadDTO);
		domicilioDTO.setExpedienteDTO(new ExpedienteDTO(2L));
		domicilioDTO.setAsentamientoDTO(new AsentamientoDTO(3L));
						
		domicilioExtranjeroDTO.setValorIdElemento(new ValorDTO(31L));
		domicilioExtranjeroDTO.setCalle("calleLugar1");
		domicilioExtranjeroDTO.setDescripcion("descripcion domicilio lugar 1");
		domicilioExtranjeroDTO.setFechaCreacionElemento(new Date());
		domicilioExtranjeroDTO.setEntreCalle1("entre calle lugar 1");
		domicilioExtranjeroDTO.setEntreCalle2("entre calle lugar 2");
		domicilioDTO.setAlias("Ranchito lugar ");	
		domicilioExtranjeroDTO.setCalidadDTO(calidadDTO);
		domicilioExtranjeroDTO.setExpedienteDTO(new ExpedienteDTO(2L));
		domicilioExtranjeroDTO.setCiudad("Prue U");
		domicilioExtranjeroDTO.setMunicipio("Mun U");
		domicilioExtranjeroDTO.setPaisValor(new ValorDTO(2L));
		domicilioExtranjeroDTO.setPais("Prue");
		domicilioExtranjeroDTO.setEstado("Est U");
		
		try {
			if (condicion==1)
				respuesta = service.ingresarLugar(domicilioDTO);
			else if (condicion==6)
				respuesta = service.ingresarLugar(domicilioExtranjeroDTO);
				assertNotNull(respuesta);
				assertTrue("El identificado del lugar debe ser mayor a cero ", respuesta.getElementoId()>0);
		} catch (NSJPNegocioException e) {
			logger.info(e.getMessage());
		}
		
	}
}
