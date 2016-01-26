/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.segob.nsjp.dto.catalogo;

import java.io.Serializable;
import java.util.List;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.ws.CodigoRespuestaWS;

/**
 *
 * @author Gerardo Meza
 */
public class RespuestaWSDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
      
    List lista;
    CodigoError codigoError;
    CodigoRespuestaWS codigo;
    
    
    public List getLista() {
        return lista;
    }

    public void setLista(List lista) {
        this.lista = lista;
    }

    public CodigoError getCodigoError() {
        return codigoError;
    }

    public void setCodigoError(CodigoError codigoError) {
        this.codigoError = codigoError;
    }

    public CodigoRespuestaWS getCodigo() {
        return codigo;
    }

    public void setCodigo(CodigoRespuestaWS codigo) {
        this.codigo = codigo;
    }

}
