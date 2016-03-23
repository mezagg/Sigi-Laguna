/**
 * Nombre del Programa : ParametroDAOImpl.java
 * Autor                            : cesarAgustin
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 7 Jul 2011
 * Marca de cambio        : N/A
 * Descripcion General    : Implementacion de metodos de acceso a datos para la entidad Parametros
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
package mx.gob.segob.nsjp.dao.parametro.impl;

import java.util.HashMap;
import mx.gob.segob.nsjp.comun.enums.configuracion.Parametros;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.parametro.ParametroDAO;
import mx.gob.segob.nsjp.model.Parametro;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import mx.gob.segob.nsjp.dto.ParametroDTO;

/**
 * Implementacion de metodos de acceso a datos para la entidad Parametros.
 * 
 * @version 1.0
 * @author cesarAgustin
 * 
 */
@Repository
public class ParametroDAOImpl extends GenericDaoHibernateImpl<Parametro, Long>
        implements
            ParametroDAO {

    private static Map mapa;
        
    private Map inicializaParametros(){
            if(mapa!=null)
                return mapa;
            
            mapa = new HashMap();
            List<Parametro> lista = this.findAll("parametroId", true);
            Parametro parDTO = null;
            for(Parametro result:lista){
                parDTO = new Parametro(result.getParametroId(), result
				.getClave(), result.getValor(),
				result.getDescripcion(), result.getTipoValor());
                try{
                Parametros pp= Parametros.valueOf(result.getClave());
                if(pp!=null)
                    mapa.put(pp, parDTO);
                }catch(Exception e){
                    logger.error(e);
                }
            }
            logger.debug("Inicializacion de parametros:"+mapa);
             return mapa;
    }
    public ParametroDAOImpl(){
        
    }

    @Override
    public Parametro obtenerPorClave(Parametros clveParam) {
        inicializaParametros();
        logger.debug("Recuperando " + clveParam);
        Parametro p= (Parametro)mapa.get(clveParam);
        if(p==null){
            final StringBuffer cq = new StringBuffer();
            cq.append(" from Parametro where clave = :cve");
            final Query qry = super.getSession().createQuery(cq.toString())
                    .setParameter("cve", clveParam.name());
            p= (Parametro) qry.uniqueResult();
            if (p != null)
            mapa.put(Parametros.valueOf(p.getClave()), p);
            
        }
        return p;
        /*
        */
    }

    public List<Parametro> obtenerPorClaveBase(String claveBase) {
        logger.debug("Recuperando " + claveBase);
        final StringBuffer cq = new StringBuffer();
        cq.append(" from Parametro where clave like '"+claveBase+"%'");
        final Query qry = super.getSession().createQuery(cq.toString());
        return qry.list();
    }

}
