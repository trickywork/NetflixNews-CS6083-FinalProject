package netflix.news.system.controller;

import netflix.news.system.dto.ApiResponse;
import netflix.news.system.dto.PageRequest;
import netflix.news.system.dto.PageResponse;
import netflix.news.system.entity.Producer;
import netflix.news.system.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * REST Controller for Producer operations (Employee only)
 */
@RestController
@RequestMapping("/api/producers")
public class ProducerController {
    
    @Autowired
    private ProducerService producerService;
    
    /**
     * Get all producers
     */
    @GetMapping
    public ApiResponse<List<Producer>> getAll() {
        return ApiResponse.success(producerService.findAll());
    }
    
    /**
     * Get producers with pagination
     */
    @GetMapping("/page")
    public ApiResponse<PageResponse<Producer>> getPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPage(page);
        pageRequest.setSize(size);
        return ApiResponse.success(producerService.findWithPagination(pageRequest));
    }
    
    /**
     * Get producer by ID
     */
    @GetMapping("/{id}")
    public ApiResponse<Producer> getById(@PathVariable Integer id) {
        Producer producer = producerService.findById(id);
        if (producer == null) {
            return ApiResponse.notFound("Producer not found");
        }
        return ApiResponse.success(producer);
    }
    
    /**
     * Search producers by name
     */
    @GetMapping("/search")
    public ApiResponse<List<Producer>> search(@RequestParam String keyword) {
        return ApiResponse.success(producerService.searchByName(keyword));
    }
    
    /**
     * Create new producer
     */
    @PostMapping
    public ApiResponse<Producer> create(@Valid @RequestBody Producer producer) {
        try {
            Producer created = producerService.create(producer);
            return ApiResponse.success("Producer created successfully", created);
        } catch (Exception e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }
    
    /**
     * Update producer
     */
    @PutMapping("/{id}")
    public ApiResponse<Producer> update(@PathVariable Integer id, @Valid @RequestBody Producer producer) {
        try {
            producer.setProducerId(id);
            Producer updated = producerService.update(producer);
            return ApiResponse.success("Producer updated successfully", updated);
        } catch (Exception e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }
    
    /**
     * Delete producer
     */
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Integer id) {
        try {
            producerService.delete(id);
            return ApiResponse.success("Producer deleted successfully");
        } catch (Exception e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }
    
    /**
     * Link producer to production house
     */
    @PostMapping("/{producerId}/production-houses/{productionHouseId}")
    public ApiResponse<Void> linkToProductionHouse(
            @PathVariable Integer producerId, 
            @PathVariable Integer productionHouseId) {
        try {
            producerService.linkToProductionHouse(producerId, productionHouseId);
            return ApiResponse.success("Producer linked to production house successfully");
        } catch (Exception e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }
    
    /**
     * Unlink producer from production house
     */
    @DeleteMapping("/{producerId}/production-houses/{productionHouseId}")
    public ApiResponse<Void> unlinkFromProductionHouse(
            @PathVariable Integer producerId, 
            @PathVariable Integer productionHouseId) {
        try {
            producerService.unlinkFromProductionHouse(producerId, productionHouseId);
            return ApiResponse.success("Producer unlinked from production house successfully");
        } catch (Exception e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }
}
