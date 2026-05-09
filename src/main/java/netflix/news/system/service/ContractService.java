package netflix.news.system.service;

import netflix.news.system.dto.ContractDTO;
import netflix.news.system.dto.PageRequest;
import netflix.news.system.dto.PageResponse;
import netflix.news.system.entity.Contract;
import netflix.news.system.mapper.ContractMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service for contract management
 */
@Service
public class ContractService {
    
    @Autowired
    private ContractMapper contractMapper;
    
    /**
     * Find contract by ID
     */
    public Contract findById(Integer id) {
        return contractMapper.findById(id);
    }
    
    /**
     * Get all contracts
     */
    public List<Contract> findAll() {
        return contractMapper.findAll();
    }
    
    /**
     * Find contracts by web series ID
     */
    public List<Contract> findByWebSeriesId(Integer webSeriesId) {
        return contractMapper.findByWebSeriesId(webSeriesId);
    }
    
    /**
     * Find contracts by production house ID
     */
    public List<Contract> findByProductionHouseId(Integer productionHouseId) {
        return contractMapper.findByProductionHouseId(productionHouseId);
    }
    
    /**
     * Get contracts with pagination
     */
    public PageResponse<Contract> findWithPagination(PageRequest pageRequest) {
        List<Contract> list = contractMapper.findWithPagination(
                pageRequest.getOffset(), pageRequest.getSize());
        long total = contractMapper.count();
        return PageResponse.of(list, pageRequest.getPage(), pageRequest.getSize(), total);
    }
    
    /**
     * Create new contract
     */
    @Transactional
    public Contract create(ContractDTO dto) {
        Contract contract = new Contract();
        contract.setWebSeriesId(dto.getWebSeriesId());
        contract.setProductionHouseId(dto.getProductionHouseId());
        contract.setSignDate(dto.getSignDate());
        // End date will be calculated in SQL as sign_date + 1 year
        contract.setPerEpisodeFee(dto.getPerEpisodeFee());
        
        contractMapper.insert(contract);
        return contractMapper.findById(contract.getContractId());
    }
    
    /**
     * Update contract
     */
    @Transactional
    public Contract update(ContractDTO dto) {
        Contract existing = contractMapper.findById(dto.getContractId());
        if (existing == null) {
            throw new RuntimeException("Contract not found");
        }
        
        existing.setWebSeriesId(dto.getWebSeriesId());
        existing.setProductionHouseId(dto.getProductionHouseId());
        existing.setSignDate(dto.getSignDate());
        existing.setPerEpisodeFee(dto.getPerEpisodeFee());
        
        contractMapper.update(existing);
        return contractMapper.findById(dto.getContractId());
    }
    
    /**
     * Delete contract
     */
    @Transactional
    public void delete(Integer id) {
        contractMapper.deleteById(id);
    }
    
    /**
     * Get total contract value
     */
    public BigDecimal getTotalContractValue() {
        return contractMapper.getTotalContractValue();
    }
    
    /**
     * Get total count
     */
    public long count() {
        return contractMapper.count();
    }
}
