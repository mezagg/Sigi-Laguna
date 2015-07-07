/**
 * 
 */
package mx.gob.segob.nsjp.service.test.documento.impl;

import mx.gob.segob.nsjp.comun.enums.actividad.Actividades;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.archivo.ArchivoDigitalDTO;
import mx.gob.segob.nsjp.dto.archivo.ArchivoDigitalWSDTO;
import mx.gob.segob.nsjp.dto.documento.ActividadWSDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoWSDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDefensorWSDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudWSDTO;
import mx.gob.segob.nsjp.service.documento.ConsultarArchivosDigitalesService;
import mx.gob.segob.nsjp.service.documento.RecibirAcuseDeReciboDeSolicitudDeDefensorService;
import mx.gob.segob.nsjp.service.test.base.BaseTestServicios;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author AlejandroGA
 *
 */
public class RecibirAcuseDeReciboDeSolicitudDeDefensorServiceImplTest extends
BaseTestServicios<RecibirAcuseDeReciboDeSolicitudDeDefensorService>{
	
	@Autowired
	private ConsultarArchivosDigitalesService consultarArchivosDigitalesService;
	
	public void testRecibirAcuseDeReciboDeSolicitudDeDefensor() throws NSJPNegocioException{
		
		//PARA PG PG PG
		
		//Datos de la solicitud
		SolicitudWSDTO solicitudWsDto = new SolicitudDefensorWSDTO(); 
		solicitudWsDto.setFolioSolicitud("FG/201200210");
		
		//Datos de la actividad
		ActividadWSDTO actividadWSDTO = new ActividadWSDTO(); 
		actividadWSDTO.setTipoActividadId(Actividades.GENERAR_ACUSE_DE_RECIBO_DE_SOLICITUD_DE_DEFENSOR_PUBLICO.getValorId());
		
		
		ArchivoDigitalDTO archivoDigitalDTO = consultarArchivosDigitalesService.consultarArchivoDigitalCompleto(3709L);
		//Archivo digital
		ArchivoDigitalWSDTO archivoDigitalWSDTO = new ArchivoDigitalWSDTO();
		archivoDigitalWSDTO.setConfInstitucionId(Instituciones.DEF.getValorId());
		archivoDigitalWSDTO.setNombreArchivo(archivoDigitalDTO.getNombreArchivo());
		archivoDigitalWSDTO.setContenido(archivoDigitalDTO.getContenido());
		archivoDigitalWSDTO.setTipoArchivo("pdf");
		
		//Documento
		DocumentoWSDTO documentoWsDto = new DocumentoWSDTO();
		documentoWsDto.setArchivoDigital(archivoDigitalWSDTO);
		documentoWsDto.setActividad(actividadWSDTO);
		
		try{
			documentoWsDto = service.recibirAcuseDeReciboDeSolicitudDeDefensor(solicitudWsDto, documentoWsDto);
			logger.info("DOCUMENTOWSDTO REGESO, ID:::::::::::::::"+documentoWsDto.getDocumentoId());
		}catch (NSJPNegocioException e) {
			e.printStackTrace();
		}
	}

}
