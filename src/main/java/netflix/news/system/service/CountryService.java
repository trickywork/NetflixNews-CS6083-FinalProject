package netflix.news.system.service;

import netflix.news.system.entity.Country;
import netflix.news.system.mapper.CountryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for country management
 */
@Service
public class CountryService {
    
    @Autowired
    private CountryMapper countryMapper;
    
    /**
     * Find country by ID
     */
    public Country findById(Integer id) {
        return countryMapper.findById(id);
    }
    
    /**
     * Get all countries
     */
    public List<Country> findAll() {
        return countryMapper.findAll();
    }
    
    /**
     * Find country by name
     */
    public Country findByName(String name) {
        return countryMapper.findByName(name);
    }
    
    /**
     * Create new country
     */
    @Transactional
    public Country create(Country country) {
        // Check if country already exists
        if (countryMapper.findByName(country.getCountryName()) != null) {
            throw new RuntimeException("Country already exists");
        }
        countryMapper.insert(country);
        return country;
    }
    
    /**
     * Update country
     */
    @Transactional
    public Country update(Country country) {
        Country existing = countryMapper.findById(country.getCountryId());
        if (existing == null) {
            throw new RuntimeException("Country not found");
        }
        countryMapper.update(country);
        return countryMapper.findById(country.getCountryId());
    }
    
    /**
     * Delete country
     */
    @Transactional
    public void delete(Integer id) {
        countryMapper.deleteById(id);
    }
}
