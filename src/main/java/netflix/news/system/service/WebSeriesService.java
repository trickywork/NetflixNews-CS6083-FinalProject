package netflix.news.system.service;

import netflix.news.system.dto.PageRequest;
import netflix.news.system.dto.PageResponse;
import netflix.news.system.dto.WebSeriesDTO;
import netflix.news.system.entity.WebSeries;
import netflix.news.system.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Service for web series management
 */
@Service
public class WebSeriesService {
    
    @Autowired
    private WebSeriesMapper webSeriesMapper;
    
    @Autowired
    private CountryMapper countryMapper;
    
    @Autowired
    private WebSeriesCountryMapper webSeriesCountryMapper;
    
    @Autowired
    private WebSeriesDubbingMapper dubbingMapper;
    
    @Autowired
    private WebSeriesSubtitleMapper subtitleMapper;
    
    @Autowired
    private FeedbackMapper feedbackMapper;
    
    /**
     * Find web series by ID with all related data
     */
    public WebSeries findById(Integer id) {
        WebSeries webSeries = webSeriesMapper.findById(id);
        if (webSeries != null) {
            loadRelatedData(webSeries);
        }
        return webSeries;
    }
    
    /**
     * Get all web series
     */
    public List<WebSeries> findAll() {
        List<WebSeries> list = webSeriesMapper.findAll();
        list.forEach(this::loadRelatedData);
        return list;
    }
    
    /**
     * Search web series by name
     */
    public List<WebSeries> searchByName(String keyword) {
        List<WebSeries> list = webSeriesMapper.searchByName(keyword);
        list.forEach(this::loadRelatedData);
        return list;
    }
    
    /**
     * Find web series by type
     */
    public List<WebSeries> findByType(String type) {
        List<WebSeries> list = webSeriesMapper.findByType(type);
        list.forEach(this::loadRelatedData);
        return list;
    }
    
    /**
     * Get web series with pagination
     */
    public PageResponse<WebSeries> findWithPagination(PageRequest pageRequest) {
        List<WebSeries> list = webSeriesMapper.findWithPagination(
                pageRequest.getOffset(), pageRequest.getSize());
        list.forEach(this::loadRelatedData);
        long total = webSeriesMapper.count();
        return PageResponse.of(list, pageRequest.getPage(), pageRequest.getSize(), total);
    }
    
    /**
     * Create new web series with related data
     */
    @Transactional
    public WebSeries create(WebSeriesDTO dto) {
        WebSeries webSeries = new WebSeries();
        webSeries.setName(dto.getName());
        webSeries.setNumberOfEpisodes(dto.getNumberOfEpisodes());
        webSeries.setOriginalLanguage(dto.getOriginalLanguage());
        webSeries.setReleaseDate(dto.getReleaseDate());
        webSeries.setType(dto.getType());
        webSeries.setTotalViewers(dto.getTotalViewers() != null ? dto.getTotalViewers() : 0);
        webSeries.setTechnicalInterruption(dto.getTechnicalInterruption() != null ? dto.getTechnicalInterruption() : "No");
        
        webSeriesMapper.insert(webSeries);
        
        // Add related data
        updateRelatedData(webSeries.getWebSeriesId(), dto);
        
        return findById(webSeries.getWebSeriesId());
    }
    
    /**
     * Update web series
     */
    @Transactional
    public WebSeries update(WebSeriesDTO dto) {
        WebSeries existing = webSeriesMapper.findById(dto.getWebSeriesId());
        if (existing == null) {
            throw new RuntimeException("Web series not found");
        }
        
        existing.setName(dto.getName());
        existing.setNumberOfEpisodes(dto.getNumberOfEpisodes());
        existing.setOriginalLanguage(dto.getOriginalLanguage());
        existing.setReleaseDate(dto.getReleaseDate());
        existing.setType(dto.getType());
        existing.setTotalViewers(dto.getTotalViewers() != null ? dto.getTotalViewers() : existing.getTotalViewers());
        existing.setTechnicalInterruption(dto.getTechnicalInterruption() != null ? dto.getTechnicalInterruption() : existing.getTechnicalInterruption());
        
        webSeriesMapper.update(existing);
        
        // Update related data
        updateRelatedData(dto.getWebSeriesId(), dto);
        
        return findById(dto.getWebSeriesId());
    }
    
    /**
     * Delete web series
     */
    @Transactional
    public void delete(Integer id) {
        webSeriesMapper.deleteById(id);
    }
    
    /**
     * Get all distinct types
     */
    public List<String> getAllTypes() {
        return webSeriesMapper.findAllTypes();
    }
    
    /**
     * Get viewers statistics by type
     */
    public List<Map<String, Object>> getViewersByType() {
        return webSeriesMapper.getViewersByType();
    }
    
    /**
     * Get top rated web series
     */
    public List<WebSeries> getTopRated(int limit) {
        List<WebSeries> list = webSeriesMapper.findTopRated(limit);
        list.forEach(this::loadRelatedData);
        return list;
    }
    
    /**
     * Get total count
     */
    public long count() {
        return webSeriesMapper.count();
    }
    
    /**
     * Load related data (countries, dubbing, subtitles, rating)
     */
    private void loadRelatedData(WebSeries webSeries) {
        webSeries.setCountries(countryMapper.findByWebSeriesId(webSeries.getWebSeriesId()));
        webSeries.setDubbingLanguages(dubbingMapper.findByWebSeriesId(webSeries.getWebSeriesId()));
        webSeries.setSubtitleLanguages(subtitleMapper.findByWebSeriesId(webSeries.getWebSeriesId()));
        webSeries.setAverageRating(feedbackMapper.getAverageRatingByWebSeries(webSeries.getWebSeriesId()));
    }
    
    /**
     * Update related data (countries, dubbing, subtitles)
     */
    private void updateRelatedData(Integer webSeriesId, WebSeriesDTO dto) {
        // Update countries
        webSeriesCountryMapper.deleteByWebSeriesId(webSeriesId);
        if (dto.getCountryIds() != null) {
            for (Integer countryId : dto.getCountryIds()) {
                webSeriesCountryMapper.insert(webSeriesId, countryId);
            }
        }
        
        // Update dubbing languages
        dubbingMapper.deleteByWebSeriesId(webSeriesId);
        if (dto.getDubbingLanguages() != null) {
            for (String lang : dto.getDubbingLanguages()) {
                dubbingMapper.insert(webSeriesId, lang);
            }
        }
        
        // Update subtitle languages
        subtitleMapper.deleteByWebSeriesId(webSeriesId);
        if (dto.getSubtitleLanguages() != null) {
            for (String lang : dto.getSubtitleLanguages()) {
                subtitleMapper.insert(webSeriesId, lang);
            }
        }
    }
}