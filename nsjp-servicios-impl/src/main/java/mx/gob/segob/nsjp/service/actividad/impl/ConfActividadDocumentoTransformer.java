/**
 * Nombre del Programa : ConfActividadDocumentoTransformer.java
 * Autor                            : Jacob Lobaco
 * Compania                         : Ultrasist
 * Proyecto                         : NSJP                    Fecha: 06-jul-2011
 * Marca de cambio        : N/A
 * Descripcion General    : N/A
 * Programa Dependient    :N/A
 * Programa Subsecuente   :N/A
 * Cond. de ejecucion     :N/A
 * Dias de ejecucion      :N/A                                Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                            :N/A
 * Compania                         :N/A
 * Proyecto                         :N/A                      Fecha: N/A
 * Modificacion           :N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.service.actividad.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mx.gob.segob.nsjp.dto.ConfActividadDocumentoDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.model.ConfActividadDocumento;
import mx.gob.segob.nsjp.model.Forma;
import mx.gob.segob.nsjp.model.JerarquiaOrganizacional;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.forma.impl.transform.FormaTransformer;


/**
 * Realiza las funciones de conversion entre ConfActividadDocumento y ConfActividadDocumentoDTO.
 * @version 1.0
 * @author Jacob Lobaco
 */
public class ConfActividadDocumentoTransformer {

    /**
     * Transforma un ConfActividadDocumento en un ConfActividadDocumentoDTO.
     * @param confActividadDocumento Un ConfActividadDocumento basico a tranformar.
     * @return Un ConfActividadDocumentoDTO.
     */
    public static ConfActividadDocumentoDTO transformarConfActividadDocumento(ConfActividadDocumento confActividadDocumento){
        ConfActividadDocumentoDTO confActividadDocumentoDTO = new ConfActividadDocumentoDTO();
        
        confActividadDocumentoDTO.setConfActividadDocumentoId(confActividadDocumento.getConfActividadDocumentoId());
        confActividadDocumentoDTO.setMuestraEnCombo(confActividadDocumento.getMuestraEnCombo());
        confActividadDocumentoDTO.setAccion(confActividadDocumento.getAccion());
        confActividadDocumentoDTO.setGrupoActividad(confActividadDocumento.getGrupoActividad());
        confActividadDocumentoDTO.setNombreActividad(confActividadDocumento.getTipoActividad().getValor());
        if(confActividadDocumento.getTipoDocumento()!=null)
        	confActividadDocumentoDTO.setNombreDocumento(confActividadDocumento.getTipoDocumento().getValor());
        confActividadDocumentoDTO.setTipoActividadId(confActividadDocumento.getTipoActividad().getValorId());
        if(confActividadDocumento.getTipoDocumento()!=null)
        	confActividadDocumentoDTO.setTipoDocumentoId(confActividadDocumento.getTipoDocumento().getValorId());
        
        if(confActividadDocumento.getEstadoCambioExpediente()!= null)
        	confActividadDocumentoDTO.setEstadoCambioExpediente(new ValorDTO(
        			confActividadDocumento.getEstadoCambioExpediente().getValorId(), 
        			confActividadDocumento.getEstadoCambioExpediente().getValor()));
        
        if(confActividadDocumento.getForma()!= null)
        	confActividadDocumentoDTO.setForma(FormaTransformer.transformarForma(confActividadDocumento.getForma()));
        
        confActividadDocumentoDTO.setUsaEditor(confActividadDocumento.getUsaEditor());
        return confActividadDocumentoDTO;
    }

    /**
     * Transforma un ConfActividadDocumentoDTO en un ConfActividadDocumento basico.
     * @param confActividadDocumentoDTO El DTO a transformar.
     * @return Un objeto de tipo ConfActividadDocumento
     */
    public static ConfActividadDocumento transformarConfActividadDocumento(ConfActividadDocumentoDTO cadDTO){
    	if(cadDTO==null)
    		return null;
    	
        ConfActividadDocumento cad = new ConfActividadDocumento();
        
        cad.setConfActividadDocumentoId(cadDTO.getConfActividadDocumentoId());
		if (cadDTO.getTipoActividadId() != null
				&& cadDTO.getTipoActividadId() > 0) {
			cad.setTipoActividad(new Valor(cadDTO.getTipoActividadId()));
		}
		if (cadDTO.getJerarquiaOrganizacional() != null
				&& cadDTO.getJerarquiaOrganizacional()
						.getJerarquiaOrganizacionalId() != null
				&& cadDTO.getJerarquiaOrganizacional()
						.getJerarquiaOrganizacionalId() > 0) {
			cad.setJerarquiaOrganizacional(new JerarquiaOrganizacional(cadDTO
					.getJerarquiaOrganizacional()
					.getJerarquiaOrganizacionalId()));
		}
		cad.setMuestraEnCombo(cadDTO.getMuestraEnCombo());
		cad.setAccion(cadDTO.getAccion());
		if (cadDTO.getTipoDocumentoId() != null
				&& cadDTO.getTipoDocumentoId() > 0) {
			cad.setTipoDocumento(new Valor(cadDTO.getTipoDocumentoId()));
		}
		if (cadDTO.getEstadoCambioExpediente() != null
				&& cadDTO.getEstadoCambioExpediente().getIdCampo() != null
				&& cadDTO.getEstadoCambioExpediente().getIdCampo() > 0) {
			cad.setEstadoCambioExpediente(new Valor(cadDTO
					.getEstadoCambioExpediente().getIdCampo()));
		}
		cad.setUsaEditor(cadDTO.getUsaEditor());
        cad.setGrupoActividad(cadDTO.getGrupoActividad());
		if (cadDTO.getForma() != null && cadDTO.getForma().getFormaId() != null
				&& cadDTO.getForma().getFormaId() > 0) {
			cad.setForma(new Forma(cadDTO.getForma().getFormaId()));
		}
		if (cadDTO.getCategoriaActividad() != null
				&& cadDTO.getCategoriaActividad().getIdCampo() != null
				&& cadDTO.getCategoriaActividad().getIdCampo() > 0) {
			cad.setCategoriaActividad(new Valor(cadDTO.getCategoriaActividad()
					.getIdCampo()));
		}
		if (cadDTO.getTipoDocumentoId() != null
				&& cadDTO.getTipoDocumentoId() > 0L) {
			Valor tipoDocumento = new Valor(cadDTO.getTipoDocumentoId());
			cad.setTipoDocumento(tipoDocumento);
		}
        return cad;
    }
    
    public static Set<ConfActividadDocumento> listTransformer (List<ConfActividadDocumentoDTO> lstCADDTO){
    	HashSet<ConfActividadDocumento> hsCAD=null;
    	if (lstCADDTO!=null){
    		hsCAD= new HashSet<ConfActividadDocumento>();
    		for (ConfActividadDocumentoDTO cad : lstCADDTO){
    			hsCAD.add(new ConfActividadDocumento(cad.getConfActividadDocumentoId()));
    		}
    	}
    	
    	return hsCAD;
    	
    }
}
