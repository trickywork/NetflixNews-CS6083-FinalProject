package netflix.news.system.controller;

import netflix.news.system.dto.ApiResponse;
import netflix.news.system.dto.ContractDTO;
import netflix.news.system.dto.PageRequest;
import netflix.news.system.dto.PageResponse;
import netflix.news.system.entity.Contract;
import netflix.news.system.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

/**
 * REST Controller for Contract operations (Employee only)
 */
@RestController
@RequestMapping("/api/contracts")
public class ContractController {
    
    @Autowired
    private ContractService contractService;
    
    /**
     * Get all contracts
     */
    @GetMapping
    public ApiResponse<List<Contract>> getAll() {
        return ApiResponse.success(contractService.findAll());
    }
    
    /**
     * Get contracts with pagination
     */
    @GetMapping("/page")
    public ApiResponse<PageResponse<Contract>> getPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPage(page);
        pageRequest.setSize(size);
        return ApiResponse.success(contractService.findWithPagination(pageRequest));
    }
    
    /**
     * Get contract by ID
     */
    @GetMapping("/{id}")
    public ApiResponse<Contract> getById(@PathVariable Integer id) {
        Contract contract = contractService.findById(id);
        if (contract == null) {
            return ApiResponse.notFound("Contract not found");
        }
        return ApiResponse.success(contract);
    }
    
    /**
     * Get contracts by web series ID
     */
    @GetMapping("/web-series/{webSeriesId}")
    public ApiResponse<List<Contract>> getByWebSeriesId(@PathVariable Integer webSeriesId) {
        return ApiResponse.success(contractService.findByWebSeriesId(webSeriesId));
    }
    
    /**
     * Get contracts by production house ID
     */
    @GetMapping("/production-house/{productionHouseId}")
    public ApiResponse<List<Contract>> getByProductionHouseId(@PathVariable Integer productionHouseId) {
        return ApiResponse.success(contractService.findByProductionHouseId(productionHouseId));
    }
    
    /**
     * Get total contract value
     */
    @GetMapping("/stats/total-value")
    public ApiResponse<BigDecimal> getTotalValue() {
        return ApiResponse.success(contractService.getTotalContractValue());
    }
    
    /**
     * Create new contract
     */
    @PostMapping
    public ApiResponse<Contract> create(@Valid @RequestBody ContractDTO dto) {
        try {
            Contract created = contractService.create(dto);
            return ApiResponse.success("Contract created successfully", created);
        } catch (Exception e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }
    
    /**
     * Update contract
     */
    @PutMapping("/{id}")
    public ApiResponse<Contract> update(@PathVariable Integer id, @Valid @RequestBody ContractDTO dto) {
        try {
            dto.setContractId(id);
            Contract updated = contractService.update(dto);
            return ApiResponse.success("Contract updated successfully", updated);
        } catch (Exception e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }
    
    /**
     * Delete contract
     */
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Integer id) {
        try {
            contractService.delete(id);
            return ApiResponse.success("Contract deleted successfully");
        } catch (Exception e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }
}
