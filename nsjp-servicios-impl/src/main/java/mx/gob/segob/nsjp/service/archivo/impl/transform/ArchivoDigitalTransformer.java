/**
 * Nombre del Programa : ArchivoDigitalTransformer.java
 * Autor                            : Emigdio Hernández
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 30/05/2011
 * Marca de cambio        : N/A
 * Descripcion General    : Clase tranformadora para el objeto de Archivo Digital
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
package mx.gob.segob.nsjp.service.archivo.impl.transform;

import java.util.ArrayList;
import java.util.List;

import mx.gob.segob.nsjp.comun.util.ManejadorArchivos;
import mx.gob.segob.nsjp.dto.archivo.ArchivoDigitalDTO;
import mx.gob.segob.nsjp.dto.archivo.ArchivoDigitalWSDTO;
import mx.gob.segob.nsjp.model.ArchivoDigital;

/**
 * Clase transformadora para el objeto de Archivo Digital
 * @author Emigdio Hernández
 *
 */
public class ArchivoDigitalTransformer {

	/**
	 * Transforma un objeto del tipo <code>ArchivoDigitalDTO</code> en un objeto del modelo
	 * de persistencia del tipo <code>ArchivoDigital</code>
	 * @param src Datos fuente del DTO
	 * @return Objeto transformado
	 */
	public static ArchivoDigital transformarArchivoDigitalDTO(ArchivoDigitalDTO src){
		ArchivoDigital archivo = null;
		if(src != null){
			archivo = new ArchivoDigital();
						
			archivo.setArchivoDigitalId(src.getArchivoDigitalId());
			archivo.setContenido(src.getContenido());
			archivo.setNombreArchivo(src.getNombreArchivo());
			archivo.setTipoArchivo(src.getTipoArchivo());
			
		}
		
		return archivo;
	}
	
	public static List<ArchivoDigitalDTO> transformarListaArchivoDigitalDTO(List<ArchivoDigital> src){
		List<ArchivoDigitalDTO> loArchivoDigitalDTOs = null;
		
		if(src == null || src.size() == 0)
			return loArchivoDigitalDTOs;
		else{
			loArchivoDigitalDTOs = new ArrayList<ArchivoDigitalDTO>();
			for (ArchivoDigital archivoDigital : src) {
				loArchivoDigitalDTOs.add(transformarArchivoDigital(archivoDigital));
			}
			return loArchivoDigitalDTOs;
		}
	}
	
	
	
	public static List<ArchivoDigital> transformarListaArchivoDigital(List<ArchivoDigitalDTO> src){
		List<ArchivoDigital> loArchivoDigitalDTOs = null;
		if(src == null || src.size() == 0)
			return loArchivoDigitalDTOs;
		else{
			loArchivoDigitalDTOs = new ArrayList<ArchivoDigital>();
			for (ArchivoDigitalDTO archivoDigitalDTO : src) {
				loArchivoDigitalDTOs.add(transformarArchivoDigitalDTO(archivoDigitalDTO));
			}
			return loArchivoDigitalDTOs;
		}
	}
	
	/**
	 * Transforma un objeto del tipo <code>ArchivoDigital</code> en un DTO
	 *  del tipo <code>ArchivoDigitalDTO</code>
	 * @param src Datos fuente del archivo digital
	 * @return Objeto transformado
	 */
	public static ArchivoDigitalDTO transformarArchivoDigital(ArchivoDigital src){
		ArchivoDigitalDTO archivo = null;
		if(src != null){
			archivo = new ArchivoDigitalDTO();
			archivo.setArchivoDigitalId(src.getArchivoDigitalId());
			if(src.getRutaArchivo()!=null && (!src.getRutaArchivo().equals(""))){
	    		if(src.getContenido()==null){
	    			archivo.setContenido( new ManejadorArchivos().recuperaArchivo(src.getRutaArchivo()));
	    		}else{
	    			archivo.setContenido(src.getContenido());
	    		}
	    	}else{
	    		if(src.getContenido()!=null){
	    			archivo.setContenido(src.getContenido());
	    		}else{
	    			archivo.setContenido(null);
	    		}
	    	}
			archivo.setNombreArchivo(src.getNombreArchivo());
			archivo.setTipoArchivo(src.getTipoArchivo());
		}
		return archivo;
	}
	/**
	 * Transforma un objeto del tipo <code>ArchivoDigital</code> en un DTO
	 *  del tipo <code>ArchivoDigitalDTO</code>
	 * @param src Datos fuente del archivo digital
	 * @return Objeto transformado
	 */
	public static ArchivoDigitalDTO transformarArchivoDigitalBasico(ArchivoDigital src){
		ArchivoDigitalDTO archivo = null;
		if(src != null){
			archivo = new ArchivoDigitalDTO();
			archivo.setArchivoDigitalId(src.getArchivoDigitalId());
			archivo.setNombreArchivo(src.getNombreArchivo());
			archivo.setTipoArchivo(src.getTipoArchivo());
		}
		return archivo;
	}
	/**
	 * Actualiza un objeto del tipo <code>ArchivoDigital</code> con los datos de un objeto
	 * <code>ArchivoDigitalDTO</code> sin alterar el ID 
	 * @param destino Archivo destino
	 * @param src Archivo fuente
	 */
	public static void transformarArchivoDigitalUpdate(ArchivoDigital destino,ArchivoDigitalDTO src){
		
		if(src != null){
			destino.setContenido(src.getContenido());
			destino.setNombreArchivo(src.getNombreArchivo());
			destino.setTipoArchivo(src.getTipoArchivo());
			
		}
		
	}
	
	public static ArchivoDigitalDTO transformarArchivoDigitalDTO(
			ArchivoDigitalWSDTO input) {
		ArchivoDigitalDTO archivo = new ArchivoDigitalDTO();
		
		archivo.setNombreArchivo(input.getNombreArchivo());
		archivo.setContenido(input.getContenido());
		archivo.setTipoArchivo(input.getTipoArchivo());

		return archivo;
	}
	
	
	public static mx.gob.segob.nsjp.ws.cliente.enviarcarpetainvestigacion.ArchivoDigitalWSDTO transformarArchivoDigitalWSDTO(ArchivoDigitalDTO input) {
		mx.gob.segob.nsjp.ws.cliente.enviarcarpetainvestigacion.ArchivoDigitalWSDTO archivo = 
			new mx.gob.segob.nsjp.ws.cliente.enviarcarpetainvestigacion.ArchivoDigitalWSDTO();
		archivo.setNombreArchivo(input.getNombreArchivo());
		archivo.setContenido(input.getContenido());
		archivo.setTipoArchivo(input.getTipoArchivo());

		return archivo;
	}
	
	public static mx.gob.segob.nsjp.ws.cliente.enviarinformepolicialhomologado.ArchivoDigitalWSDTO transformarArchivoDigitalIPHWSDTO(ArchivoDigitalDTO input) {
		mx.gob.segob.nsjp.ws.cliente.enviarinformepolicialhomologado.ArchivoDigitalWSDTO archivo = 
			new mx.gob.segob.nsjp.ws.cliente.enviarinformepolicialhomologado.ArchivoDigitalWSDTO();
		archivo.setNombreArchivo(input.getNombreArchivo());
		archivo.setContenido(input.getContenido());
		archivo.setTipoArchivo(input.getTipoArchivo());

		return archivo;
	}
	
}
