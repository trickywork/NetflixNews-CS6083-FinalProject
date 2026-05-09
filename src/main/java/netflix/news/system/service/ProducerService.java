package netflix.news.system.service;

import netflix.news.system.dto.PageRequest;
import netflix.news.system.dto.PageResponse;
import netflix.news.system.entity.Producer;
import netflix.news.system.mapper.ProducerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for producer management
 */
@Service
public class ProducerService {
    
    @Autowired
    private ProducerMapper producerMapper;
    
    /**
     * Find producer by ID
     */
    public Producer findById(Integer id) {
        return producerMapper.findById(id);
    }
    
    /**
     * Get all producers
     */
    public List<Producer> findAll() {
        return producerMapper.findAll();
    }
    
    /**
     * Search producers by name
     */
    public List<Producer> searchByName(String keyword) {
        return producerMapper.searchByName(keyword);
    }
    
    /**
     * Get producers with pagination
     */
    public PageResponse<Producer> findWithPagination(PageRequest pageRequest) {
        List<Producer> list = producerMapper.findWithPagination(
                pageRequest.getOffset(), pageRequest.getSize());
        long total = producerMapper.count();
        return PageResponse.of(list, pageRequest.getPage(), pageRequest.getSize(), total);
    }
    
    /**
     * Create new producer
     */
    @Transactional
    public Producer create(Producer producer) {
        // Check if email already exists
        if (producerMapper.findByEmail(producer.getEmailAddress()) != null) {
            throw new RuntimeException("Email address already exists");
        }
        producerMapper.insert(producer);
        return producer;
    }
    
    /**
     * Update producer
     */
    @Transactional
    public Producer update(Producer producer) {
        Producer existing = producerMapper.findById(producer.getProducerId());
        if (existing == null) {
            throw new RuntimeException("Producer not found");
        }
        
        // Check if email is being changed to one that already exists
        Producer byEmail = producerMapper.findByEmail(producer.getEmailAddress());
        if (byEmail != null && !byEmail.getProducerId().equals(producer.getProducerId())) {
            throw new RuntimeException("Email address already exists");
        }
        
        producerMapper.update(producer);
        return producerMapper.findById(producer.getProducerId());
    }
    
    /**
     * Delete producer
     */
    @Transactional
    public void delete(Integer id) {
        producerMapper.deleteById(id);
    }
    
    /**
     * Link producer to production house
     */
    @Transactional
    public void linkToProductionHouse(Integer producerId, Integer productionHouseId) {
        producerMapper.linkToProductionHouse(producerId, productionHouseId);
    }
    
    /**
     * Unlink producer from production house
     */
    @Transactional
    public void unlinkFromProductionHouse(Integer producerId, Integer productionHouseId) {
        producerMapper.unlinkFromProductionHouse(producerId, productionHouseId);
    }
    
    /**
     * Get total count
     */
    public long count() {
        return producerMapper.count();
    }
}
