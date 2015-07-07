/**
* Nombre del Programa 		: BitacoraElementoDAOImpl.java
* Autor 					: EdgarAT
* Compania 					: Ultrasist
* Proyecto 					: NSJP 								Fecha: 07/11/2012
* Marca de cambio 			: N/A
* Descripcion General 		: Describir el objetivo de la clase de manera breve
* Programa Dependiente 		: N/A
* Programa Subsecuente 		: N/A
* Cond. de ejecucion 		: N/A
* Dias de ejecucion 		: N/A 								Horario: N/A
*                              MODIFICACIONES
*------------------------------------------------------------------------------
* Autor 					: N/A
* Compania 					: N/A
* Proyecto 					: N/A 								Fecha: N/A
* Modificacion 				: N/A
*------------------------------------------------------------------------------
*/
package mx.gob.segob.nsjp.dao.elemento.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.elemento.BitacoraElementoDAO;
import mx.gob.segob.nsjp.model.BitacoraElemento;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author EdgarAT
 *
 */
@Repository
public class BitacoraElementoDAOImpl extends GenericDaoHibernateImpl<BitacoraElemento, Long> 
	implements BitacoraElementoDAO {

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.dao.elemento.BitacoraElementoDAO#consultarBitacoraElementos(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BitacoraElemento> consultarBitacoraElementos(
			List<BitacoraElemento> elementos) throws NSJPNegocioException {
		if (elementos == null 
				|| elementos.isEmpty()){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		StringBuilder queryString = new StringBuilder(" SELECT be ");
								   queryString.append(" FROM BitacoraElemento be ");
								   queryString.append(" WHERE be.elemento.elementoId in (");
								   queryString.append(creaListaIds(elementos));
								   queryString.append(" )");
		Query query = super.getSession().createQuery(queryString.toString());
		return (List<BitacoraElemento>) query.list();
	}
	
	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.dao.elemento.BitacoraElementoDAO#consultarBitacoraXElemento(mx.gob.segob.nsjp.model.BitacoraElemento)
	 */
	@Override
	public BitacoraElemento consultarBitacoraXElemento(BitacoraElemento elemento)
	throws NSJPNegocioException {
		if (elemento == null
				|| elemento.getElemento() == null
				|| elemento.getElemento().getElementoId() == null
				|| elemento.getElemento().getElementoId() < 1L){
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
		StringBuilder queryString = new StringBuilder(" SELECT be ");
		   						   queryString.append(" FROM BitacoraElemento be ");
		   						   queryString.append(" WHERE be.elemento.elementoId = ? ");
		
		Query query = super.getSession().createQuery(queryString.toString());
		query.setParameter(0, elemento.getElemento().getElementoId());
		return (BitacoraElemento) query.uniqueResult();
	}
	
	/**
	 * M&eacute;todo utilitario que lleva a cabo la creaci&oacute;n de una cadena que 
	 * representa la lista de los identificadores de los elementos pasados como 
	 * par&aacute;metros.
	 * @param elementos - List<BitacoraElemento> de la cual se obtendr&aacute; la cadena
	 * 					  con los identificadores.
	 * @return String - Cadena con la lista de identificadores concatenados.
	 */
	private String creaListaIds(List<BitacoraElemento> elementos){
		StringBuilder sb = new StringBuilder("");
		if (elementos != null 
				&& !elementos.isEmpty()){
			int tamanioLista = elementos.size();
			for (int i=0; i<tamanioLista; i++){
				sb.append(elementos.get(i).getElemento().getElementoId());
				if (i<tamanioLista-1){
					sb.append(", ");
				}
			}			
		}
		return sb.toString();
	}

}
