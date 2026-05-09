package netflix.news.system.service;

import netflix.news.system.dto.PageRequest;
import netflix.news.system.dto.PageResponse;
import netflix.news.system.entity.ProductionHouse;
import netflix.news.system.mapper.ProductionHouseMapper;
import netflix.news.system.mapper.ProducerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for production house management
 */
@Service
public class ProductionHouseService {
    
    @Autowired
    private ProductionHouseMapper productionHouseMapper;
    
    @Autowired
    private ProducerMapper producerMapper;
    
    /**
     * Find production house by ID with producers
     */
    public ProductionHouse findById(Integer id) {
        ProductionHouse ph = productionHouseMapper.findById(id);
        if (ph != null) {
            ph.setProducers(producerMapper.findByProductionHouseId(id));
        }
        return ph;
    }
    
    /**
     * Get all production houses
     */
    public List<ProductionHouse> findAll() {
        return productionHouseMapper.findAll();
    }
    
    /**
     * Search production houses by name
     */
    public List<ProductionHouse> searchByName(String keyword) {
        return productionHouseMapper.searchByName(keyword);
    }
    
    /**
     * Get production houses with pagination
     */
    public PageResponse<ProductionHouse> findWithPagination(PageRequest pageRequest) {
        List<ProductionHouse> list = productionHouseMapper.findWithPagination(
                pageRequest.getOffset(), pageRequest.getSize());
        long total = productionHouseMapper.count();
        return PageResponse.of(list, pageRequest.getPage(), pageRequest.getSize(), total);
    }
    
    /**
     * Create new production house
     */
    @Transactional
    public ProductionHouse create(ProductionHouse productionHouse) {
        productionHouseMapper.insert(productionHouse);
        return productionHouse;
    }
    
    /**
     * Update production house
     */
    @Transactional
    public ProductionHouse update(ProductionHouse productionHouse) {
        ProductionHouse existing = productionHouseMapper.findById(productionHouse.getProductionHouseId());
        if (existing == null) {
            throw new RuntimeException("Production house not found");
        }
        productionHouseMapper.update(productionHouse);
        return productionHouseMapper.findById(productionHouse.getProductionHouseId());
    }
    
    /**
     * Delete production house
     */
    @Transactional
    public void delete(Integer id) {
        productionHouseMapper.deleteById(id);
    }
    
    /**
     * Get total count
     */
    public long count() {
        return productionHouseMapper.count();
    }
}
