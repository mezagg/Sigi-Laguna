package mx.gob.segob.nsjp.service.region.impl;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.catalogo.RegionDAO;
import mx.gob.segob.nsjp.dto.domicilio.RegionDTO;
import mx.gob.segob.nsjp.model.Region;
import mx.gob.segob.nsjp.service.region.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by israel on 9/22/15.
 */
@Service
@Transactional
public class RegionServiceImpl implements RegionService {

    @Autowired
    private RegionDAO regionDAO;

    public List<RegionDTO> consultarTodos()throws NSJPNegocioException {

        List<Region> regions = regionDAO.consultarTodos();
        List<RegionDTO> regionDTOs = new ArrayList<RegionDTO>();

        for( Region region : regions){
            RegionDTO regionDTO = new RegionDTO();
            regionDTO.setClaveRegion(region.getClaveRegion());
            regionDTO.setRegionId(region.getRegionId());
            regionDTO.setNombre(region.getNombre());

            regionDTOs.add(regionDTO);
        }

        return regionDTOs;
    }
}
