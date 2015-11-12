/**
 * Nombre del Programa : ObjetoTransformer.java
 * Autor                            : Emigdio Hern�ndez
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 13/06/2011
 * Marca de cambio        : N/A
 * Descripcion General    : Clase de transformaci�n para un Objeto del Expediente.
 * Esta clase tranforma un Objeto a su DTO correspondiente considerando el tipo de objeto
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
package mx.gob.segob.nsjp.service.objeto.impl.transform;

import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.enums.elemento.Elementos;
import mx.gob.segob.nsjp.comun.enums.objeto.Objetos;
import mx.gob.segob.nsjp.dto.cadenadecustoria.CadenaDeCustodiaDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.elemento.CalidadDTO;
import mx.gob.segob.nsjp.dto.evidencia.EvidenciaDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.objeto.ObjetoDTO;
import mx.gob.segob.nsjp.model.*;
import mx.gob.segob.nsjp.service.archivo.impl.transform.ArchivoDigitalTransformer;
import mx.gob.segob.nsjp.service.expediente.impl.transform.ExpedienteTransformer;
import mx.gob.segob.nsjp.service.solicitud.impl.transform.ConfInstitucionTransformer;

/**
 * Clase de transformaci�n para un Objeto del Expediente. Esta clase tranforma
 * un Objeto a su DTO correspondiente considerando el tipo de objeto
 * 
 * @version 1.0
 * @author Emigdio Hern�ndez
 * 
 */
public class ObjetoTransformer {

    /**
     * Transforma un objeto del tipo
     * <code>Objeto</objeto> a su DTO correspondiente pero teniendo en cuenta
     * el tipo de objeto, por ejemplo, si el tipo de objeto es un arma se realiza un casto y luego otra
     * transoformaci�n y se retorna como un <code>ObjetoDTO</code>
     * 
     * @param src
     *            Objeto fuente
     * @return Objeto transformado
     */
    public static ObjetoDTO transformarObjeto(Objeto src) {
        ObjetoDTO destino = new ObjetoDTO();
        
        if(src.getAlmacen() != null)
        	destino.setAlmacen(AlmacenTransformer.transformarAlmacen(src.getAlmacen()));
        
        if (src != null) {

        	if (src instanceof Vehiculo) {

                destino = VehiculoTransformer
                        .transformarVehiculo((Vehiculo) src);

            } else if (src instanceof EquipoComputo) {

                destino = EquipoComputoTransformer
                        .transformarEquipoComputo((EquipoComputo) src);

            } else if (src instanceof Telefonia) {

                destino = TelefoniaTransformer
                        .transformarTelefonia((Telefonia) src);

            } else if (src instanceof Arma) {

                destino = ArmaTransformer.transformarArma((Arma) src);

            } else if (src instanceof Explosivo) {

                destino = ExplosivoTransformer
                        .transformarExplosivo((Explosivo) src);

            } else if (src instanceof Aeronave) {

                destino = AeronaveTransformer
                        .transformarAeronave((Aeronave) src);

            } else if (src instanceof Animal) {

                destino = AnimalTransformer.transformarAnimal((Animal) src);

            } else if (src instanceof DocumentoOficial) {

                destino = DocumentoOficialTransformer
                        .transformarDocumentoOficial((DocumentoOficial) src);

            } else if (src instanceof Embarcacion) {

                destino = EmbarcacionTransformer
                        .transformarEmbarcacion((Embarcacion) src);
            } else if (src instanceof Joya) {

                destino = JoyaTransformer.transformarJoya((Joya) src);

            } else if (src instanceof Numerario) {

                destino = NumerarioTransformer
                        .transformarNumerario((Numerario) src);

            } else if (src instanceof ObraArte) {

                destino = ObraArteTransformer
                        .transformarObraArte((ObraArte) src);

            } else if (src instanceof Sustancia) {

                destino = SustanciaTransformer
                        .transformarSustancia((Sustancia) src);

            } else if (src instanceof Vegetal) {

                destino = VegetalTransformer.transformarVegetal((Vegetal) src);

            } else if (src instanceof ObjetoPericial ) {
            	destino = ObjetoPericialTransformer.transformarObjetoPericialDTO((ObjetoPericial) src);
            } else {

                destino = new ObjetoDTO();

            }

            if(src.getImagenesAsociadas() != null && !src.getImagenesAsociadas().isEmpty() && src.getImagenesAsociadas().size() > 0 ){
            	destino.setImagenesAsociadas(ArchivoDigitalTransformer.transformarListaArchivoDigitalDTO(src.getImagenesAsociadas()));
            }
            
            destino.setDescripcion(src.getDescripcion());

            if(src.getValorByCondicionFisicaVal()!=null)
            {
            destino.setValorByCondicionFisicaVal(new ValorDTO(src
                    .getValorByCondicionFisicaVal().getValorId(), src
                    .getValorByCondicionFisicaVal().getValor()));
            }
            
            destino.setElementoId(src.getElementoId());
            destino.setFolioElemento(src.getFolioElemento());
            
            destino.setTipoObjeto(Objetos.getByValor(src
                    .getValorByTipoObjetoVal().getValorId()));
            destino.setFechaCreacionElemento(src.getFechaCreacionElemento());
            if (src.getCalidad() != null
                    && src.getCalidad().getTipoCalidad() != null) {
                Calidad calidad = src.getCalidad();
                CalidadDTO calidadDTO = new CalidadDTO();
                calidadDTO.setCalidadId(calidad.getCalidadId());
                calidadDTO.setValorIdCalidad(new ValorDTO(calidad
                        .getTipoCalidad().getValorId(), calidad
                        .getTipoCalidad().getValor()));
                calidadDTO.setDescripcionEstadoFisico(calidad
                        .getDescripcionEstadoFisico());
                calidadDTO.setCalidades(Calidades.getByValor(calidad
                        .getTipoCalidad().getValorId()));
                destino.setCalidadDTO(calidadDTO);
            }

            // Jacob Transformamos el almacen en caso que src lo tenga.
            if (src.getAlmacen() != null) {
                destino.setAlmacen(AlmacenTransformer.transformarAlmacen(src
                        .getAlmacen()));
            }
            if(src.getArchivoDigital() != null)        		
        		destino.setFotoDelElemento(ArchivoDigitalTransformer.transformarArchivoDigital(src.getArchivoDigital()));
            
            if(src.getInstitucionPresenta()!= null)
            	destino.setInstitucionPresenta( ConfInstitucionTransformer.transformarInstitucion(src.getInstitucionPresenta()));
            
            if(src.getNombreObjeto() != null)
                destino.setNombreObjeto(src.getNombreObjeto());

            destino.setEsActivo(src.getEsActivo());
            
            if(src.getRelacionHechoVal() != null){
            	destino.setRelacionHechoVal(new ValorDTO(src.getRelacionHechoVal().getValorId(),src.getRelacionHechoVal().getValor()));
    		}

            if( src.getExpediente() != null){
                ExpedienteDTO expedienteDTO = ExpedienteTransformer.transformaExpediente(src.getExpediente());

                destino.setExpedienteDTO(expedienteDTO);
            }
        }

        return destino;
    }

    public static Objeto transformarObjeto(ObjetoDTO objeto) {

        Objeto object = new Objeto();
        object.setElementoId(objeto.getElementoId());

        object.setDescripcion(objeto.getDescripcion());
        if (objeto.getValorByCondicionFisicaVal() != null) {
            object.setValorByCondicionFisicaVal(new Valor(objeto
                    .getValorByCondicionFisicaVal().getIdCampo()));
        }
        if (objeto.getTipoObjeto() != null) {
            object.setValorByTipoObjetoVal(new Valor(objeto.getTipoObjeto()
                    .getValorId()));
        }
        object.setTipoElemento(new Valor(Elementos.OBJETO.getValorId()));
        object.setFechaCreacionElemento(objeto.getFechaCreacionElemento());
        if(objeto.getExpedienteDTO()!=null)
        object.setExpediente(ExpedienteTransformer.transformarExpediente(objeto
                .getExpedienteDTO()));
        object.setEsActivo(objeto.getEsActivo());
        
        if(objeto.getNombreObjeto()!=null)
        	object.setNombreObjeto(objeto.getNombreObjeto());
        
        if(objeto.getRelacionHechoVal() != null && objeto.getRelacionHechoVal().getIdCampo() != null){
        	object.setRelacionHechoVal(new Valor(objeto.getRelacionHechoVal().getIdCampo()));
        }
        	
        return object;
    }
 
    /**
     * 
     * @param input
     * @return
     */
    public static ObjetoDTO tranformarComoEvidenciaBasico(Objeto input) {
        ObjetoDTO resp = new ObjetoDTO();
        EvidenciaDTO evDto = new EvidenciaDTO();
        CadenaDeCustodiaDTO cadDto = new CadenaDeCustodiaDTO();

        cadDto.setFolio(input.getEvidencia().getCadenaDeCustodia().getFolio());
        evDto.setNumeroEvidencia(input.getEvidencia().getNumeroEvidencia());

        evDto.setCadenaDeCustodia(cadDto);
        resp.setEvidencia(evDto);
        
        if(input.getNombreObjeto()!=null)
        	resp.setNombreObjeto(input.getNombreObjeto());
        
        resp.setElementoId(input.getElementoId());
        if(input.getAlmacen()!=null)
        	resp.setAlmacen(AlmacenTransformer.transformarAlmacen(input.getAlmacen()));
        
        if(input.getRelacionHechoVal() != null){
        	resp.setRelacionHechoVal(new ValorDTO(input.getRelacionHechoVal().getValorId(),input.getRelacionHechoVal().getValor()));
        }


        return resp;
    }
    
    public static Objeto transformarObjeto(ObjetoDTO objetoDTO, Objeto object) {

        object.setElementoId(objetoDTO.getElementoId());

        object.setDescripcion(objetoDTO.getDescripcion());
        if (objetoDTO.getValorByCondicionFisicaVal() != null) {
            object.setValorByCondicionFisicaVal(new Valor(objetoDTO
                    .getValorByCondicionFisicaVal().getIdCampo()));
        }
        if (objetoDTO.getTipoObjeto() != null) {
            object.setValorByTipoObjetoVal(new Valor(objetoDTO.getTipoObjeto()
                    .getValorId()));
        }
        object.setTipoElemento(new Valor(Elementos.OBJETO.getValorId()));
        object.setFechaCreacionElemento(objetoDTO.getFechaCreacionElemento());
        if(objetoDTO.getExpedienteDTO()!=null)
        object.setExpediente(ExpedienteTransformer.transformarExpediente(objetoDTO
                .getExpedienteDTO()));
        object.setEsActivo(objetoDTO.getEsActivo());
        
        if(objetoDTO.getNombreObjeto()!=null)
        	object.setNombreObjeto(objetoDTO.getNombreObjeto());
        
        if(objetoDTO.getRelacionHechoVal() != null && objetoDTO.getRelacionHechoVal().getIdCampo() != null){
        	object.setRelacionHechoVal(new Valor(objetoDTO.getRelacionHechoVal().getIdCampo()));
        }
        	
        return object;
    }

}
