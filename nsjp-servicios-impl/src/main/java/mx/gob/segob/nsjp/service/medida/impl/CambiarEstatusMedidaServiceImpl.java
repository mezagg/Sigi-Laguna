package mx.gob.segob.nsjp.service.medida.impl;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.medida.MedidaDAO;
import mx.gob.segob.nsjp.dto.configuracion.ConfInstitucionDTO;
import mx.gob.segob.nsjp.dto.documento.MedidaCautelarDTO;
import mx.gob.segob.nsjp.model.Medida;
import mx.gob.segob.nsjp.service.medida.CambiarEstatusMedidaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CambiarEstatusMedidaServiceImpl implements
		CambiarEstatusMedidaService {

	@Autowired 
	MedidaDAO medidaDAO;
	@Override
	public MedidaCautelarDTO cambiarEstatusMedida(Long idMedida, Long idEstatus)
			throws NSJPNegocioException {
		
		MedidaCautelarDTO medidaCautelarDTO = null;
		medidaDAO.cambiarEstatusMedida(idMedida, idEstatus);
		
		Medida medida = medidaDAO.read(idMedida);
		if (medida != null) {
			medidaCautelarDTO = new MedidaCautelarDTO();
			medidaCautelarDTO.setDocumentoId(medida.getDocumentoId());
			if (medida.getConfInstitucion() != null
					&& medida.getConfInstitucion().getConfInstitucionId() != null) {
				medidaCautelarDTO.setConfInstitucion(new ConfInstitucionDTO(
						medida.getConfInstitucion().getConfInstitucionId()));
			}
			medidaCautelarDTO.setFolioDocumento(medida.getFolioDocumento());
		}
		return medidaCautelarDTO;
	}
}
