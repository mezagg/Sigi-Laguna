/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.segob.nsjp.service.actividad.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import mx.gob.segob.nsjp.dto.ConfActividadDocumentoRolDTO;
import mx.gob.segob.nsjp.model.ConfActividadDocumentoRol;
import mx.gob.segob.nsjp.model.Forma;
import mx.gob.segob.nsjp.model.Rol;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.forma.impl.transform.FormaTransformer;

/**
 *
 * @author pamela
 */
public class ConfActividadDocumentoRolTransformer {

    public static ConfActividadDocumentoRolDTO transformarConfActividadDocumento(ConfActividadDocumentoRol confActividadDocumentoRol) {
        ConfActividadDocumentoRolDTO confActividadDocumentoRolDTO = new ConfActividadDocumentoRolDTO();

        confActividadDocumentoRolDTO.setConfActividadDocumentoRolId(confActividadDocumentoRol.getConfActividadDocumentoRolId());
        confActividadDocumentoRolDTO.setMuestraEnCombo(confActividadDocumentoRol.getMuestraEnCombo());
        confActividadDocumentoRolDTO.setDescripcion(confActividadDocumentoRol.getDescripcion());
        confActividadDocumentoRolDTO.setRolId(confActividadDocumentoRol.getRol().getRolId());
        if (confActividadDocumentoRol.getTipoDocumento() != null) {
            confActividadDocumentoRolDTO.setTipoDocumentoId(confActividadDocumentoRol.getTipoDocumento().getValorId());
        }
        confActividadDocumentoRolDTO.setUsaEditor(confActividadDocumentoRol.getUsaEditor());
        confActividadDocumentoRolDTO.setActivo(confActividadDocumentoRol.getActivo());
        if (confActividadDocumentoRol.getTipoDocumento() != null) {
            confActividadDocumentoRolDTO.setTipoActividadId(confActividadDocumentoRol.getTipoDocumento().getValorId());
            confActividadDocumentoRolDTO.setNombreDocumento(confActividadDocumentoRol.getTipoDocumento().getValor());
        }
        if (confActividadDocumentoRol.getForma() != null) {
            confActividadDocumentoRolDTO.setForma((FormaTransformer.transformarForma(confActividadDocumentoRol.getForma())));
        }
        confActividadDocumentoRolDTO.setNombreActividad(confActividadDocumentoRol.getTipoActividad().getValor());
        

        return confActividadDocumentoRolDTO;
    }

    public static ConfActividadDocumentoRol transformarConfActividadDocumento(ConfActividadDocumentoRolDTO cadDTO) {
        if (cadDTO == null) {
            return null;
        }

        ConfActividadDocumentoRol cad = new ConfActividadDocumentoRol();

        cad.setConfActividadDocumentoRolId(cadDTO.getConfActividadDocumentoRolId());
        cad.setActivo(cadDTO.getActivo());
        cad.setDescripcion(cadDTO.getDescripcion());
        if (cadDTO.getForma() != null && cadDTO.getForma().getFormaId() != null
                && cadDTO.getForma().getFormaId() > 0) {
            cad.setForma(new Forma(cadDTO.getForma().getFormaId()));
        }
        cad.setMuestraEnCombo(cadDTO.getMuestraEnCombo());
        if (cadDTO.getRolId() != null && cadDTO.getRolId() > 0) {
            cad.setRol(new Rol(cadDTO.getRolId()));
        }
        if (cadDTO.getTipoActividadId() != null && cadDTO.getTipoActividadId() > 0) {
            cad.setTipoActividad(new Valor(cadDTO.getTipoActividadId()));
        }
        if (cadDTO.getTipoDocumentoId() != null && cadDTO.getTipoDocumentoId() > 0) {
            cad.setTipoDocumento(new Valor(cadDTO.getTipoDocumentoId()));
        }
        cad.setUsaEditor(cadDTO.getUsaEditor());

        return cad;
    }

    public static Set<ConfActividadDocumentoRol> listTransformer(List<ConfActividadDocumentoRolDTO> lstCADDTO) {
        HashSet<ConfActividadDocumentoRol> hsCAD = null;
        if (lstCADDTO != null) {
            hsCAD = new HashSet<ConfActividadDocumentoRol>();
            for (ConfActividadDocumentoRolDTO cad : lstCADDTO) {
                hsCAD.add(new ConfActividadDocumentoRol(cad.getConfActividadDocumentoRolId()));
            }
        }

        return hsCAD;

    }
}
