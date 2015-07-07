/**
 * Nombre del Programa : GenericDTO.java                                    
 * Autor: Vladimir Aguirre Piedragil
 * Compania: Ultrasist                                                
 * Proyecto: NSJP 
 * Fecha: 29 Mar 2011 
 * Marca de cambio: N/A                                                     
 * Descripcion General: DTO gen�rico del que deben extender todos los DTO's de la aplicaci�n y poder agrupar funcionalidad com�n.(DRY).
 * Programa Dependiente: N/A                                                      
 * Programa Subsecuente: N/A                                                      
 * Cond. de ejecucion: N/A                                                      
 * Dias de ejecucion: N/A                             Horario: N/A       
 *                              MODIFICACIONES                                       
 *------------------------------------------------------------------------------           
 * Autor                       :N/A                                                           
 * Compania               :N/A                                                           
 * Proyecto                 :N/A                                   Fecha: N/A       
 * Modificacion           :N/A                                                           
 *------------------------------------------------------------------------------           
 */
package mx.gob.segob.nsjp.dto.base;

import java.io.Serializable;

import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;

/**
 * DTO Gen�rico para que extiendan todas los DTO's de la aplicaci�n.
 * 
 * @version 1.0
 * @author vaguirre
 * 
 */
public class GenericDTO implements Serializable {

    /**
     * N�mero de versi�n para la serializaci�n.
     */
    private static final long serialVersionUID = 1595907233365915760L;

    private UsuarioDTO usuario = null;

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return DTOUtil.getDTOasString(this);
    }

    /**
     * M�todo de acceso al campo usuario.
     * 
     * @return El valor del campo usuario
     */
    public UsuarioDTO getUsuario() {
        return usuario;
    }

    /**
     * Asigna el valor al campo usuario.
     * 
     * @param usuario
     *            el valor usuario a asignar
     */
    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

}
