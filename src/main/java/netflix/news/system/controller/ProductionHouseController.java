package netflix.news.system.controller;

import netflix.news.system.dto.ApiResponse;
import netflix.news.system.dto.PageRequest;
import netflix.news.system.dto.PageResponse;
import netflix.news.system.entity.ProductionHouse;
import netflix.news.system.service.ProductionHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * REST Controller for Production House operations (Employee only)
 */
@RestController
@RequestMapping("/api/production-houses")
public class ProductionHouseController {
    
    @Autowired
    private ProductionHouseService productionHouseService;
    
    /**
     * Get all production houses
     */
    @GetMapping
    public ApiResponse<List<ProductionHouse>> getAll() {
        return ApiResponse.success(productionHouseService.findAll());
    }
    
    /**
     * Get production houses with pagination
     */
    @GetMapping("/page")
    public ApiResponse<PageResponse<ProductionHouse>> getPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPage(page);
        pageRequest.setSize(size);
        return ApiResponse.success(productionHouseService.findWithPagination(pageRequest));
    }
    
    /**
     * Get production house by ID
     */
    @GetMapping("/{id}")
    public ApiResponse<ProductionHouse> getById(@PathVariable Integer id) {
        ProductionHouse ph = productionHouseService.findById(id);
        if (ph == null) {
            return ApiResponse.notFound("Production house not found");
        }
        return ApiResponse.success(ph);
    }
    
    /**
     * Search production houses by name
     */
    @GetMapping("/search")
    public ApiResponse<List<ProductionHouse>> search(@RequestParam String keyword) {
        return ApiResponse.success(productionHouseService.searchByName(keyword));
    }
    
    /**
     * Create new production house
     */
    @PostMapping
    public ApiResponse<ProductionHouse> create(@Valid @RequestBody ProductionHouse productionHouse) {
        try {
            ProductionHouse created = productionHouseService.create(productionHouse);
            return ApiResponse.success("Production house created successfully", created);
        } catch (Exception e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }
    
    /**
     * Update production house
     */
    @PutMapping("/{id}")
    public ApiResponse<ProductionHouse> update(@PathVariable Integer id, 
                                                @Valid @RequestBody ProductionHouse productionHouse) {
        try {
            productionHouse.setProductionHouseId(id);
            ProductionHouse updated = productionHouseService.update(productionHouse);
            return ApiResponse.success("Production house updated successfully", updated);
        } catch (Exception e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }
    
    /**
     * Delete production house
     */
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Integer id) {
        try {
            productionHouseService.delete(id);
            return ApiResponse.success("Production house deleted successfully");
        } catch (Exception e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }
}
