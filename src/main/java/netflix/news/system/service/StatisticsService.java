package netflix.news.system.service;

import lombok.RequiredArgsConstructor;
import netflix.news.system.mapper.AccountMapper;
import netflix.news.system.mapper.ContractMapper;
import netflix.news.system.mapper.FeedbackMapper;
import netflix.news.system.mapper.ProducerMapper;
import netflix.news.system.mapper.ProductionHouseMapper;
import netflix.news.system.mapper.WebSeriesMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service for dashboard statistics
 */
@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final WebSeriesMapper webSeriesMapper;
    private final AccountMapper accountMapper;
    private final ContractMapper contractMapper;
    private final FeedbackMapper feedbackMapper;
    private final ProductionHouseMapper productionHouseMapper;
    private final ProducerMapper producerMapper;
    
    /**
     * Get dashboard overview statistics
     */
    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        
        stats.put("totalWebSeries", webSeriesMapper.count());
        stats.put("totalAccounts", accountMapper.count());
        stats.put("totalContracts", contractMapper.count());
        stats.put("totalFeedback", feedbackMapper.count());
        stats.put("totalProductionHouses", productionHouseMapper.count());
        stats.put("totalProducers", producerMapper.count());
        
        BigDecimal contractValue = contractMapper.getTotalContractValue();
        stats.put("totalContractValue", contractValue != null ? contractValue : BigDecimal.ZERO);
        
        return stats;
    }
    
    /**
     * Get viewers by type statistics for charts
     */
    public List<Map<String, Object>> getViewersByType() {
        return webSeriesMapper.getViewersByType();
    }
    
    /**
     * Get rating distribution for charts
     */
    public List<Map<String, Object>> getRatingDistribution() {
        return feedbackMapper.getRatingDistribution();
    }
    
    /**
     * Get web series types
     */
    public List<String> getWebSeriesTypes() {
        return webSeriesMapper.findAllTypes();
    }
}
