package netflix.news.system.controller;

import netflix.news.system.dto.ApiResponse;
import netflix.news.system.entity.Country;
import netflix.news.system.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * REST Controller for Country operations
 */
@RestController
@RequestMapping("/api/countries")
public class CountryController {
    
    @Autowired
    private CountryService countryService;
    
    /**
     * Get all countries
     */
    @GetMapping
    public ApiResponse<List<Country>> getAll() {
        return ApiResponse.success(countryService.findAll());
    }
    
    /**
     * Get country by ID
     */
    @GetMapping("/{id}")
    public ApiResponse<Country> getById(@PathVariable Integer id) {
        Country country = countryService.findById(id);
        if (country == null) {
            return ApiResponse.notFound("Country not found");
        }
        return ApiResponse.success(country);
    }
    
    /**
     * Create new country (Employee only)
     */
    @PostMapping
    public ApiResponse<Country> create(@Valid @RequestBody Country country) {
        try {
            Country created = countryService.create(country);
            return ApiResponse.success("Country created successfully", created);
        } catch (Exception e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }
    
    /**
     * Update country (Employee only)
     */
    @PutMapping("/{id}")
    public ApiResponse<Country> update(@PathVariable Integer id, @Valid @RequestBody Country country) {
        try {
            country.setCountryId(id);
            Country updated = countryService.update(country);
            return ApiResponse.success("Country updated successfully", updated);
        } catch (Exception e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }
    
    /**
     * Delete country (Employee only)
     */
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Integer id) {
        try {
            countryService.delete(id);
            return ApiResponse.success("Country deleted successfully");
        } catch (Exception e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }
}
