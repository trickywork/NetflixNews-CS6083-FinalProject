package netflix.news.system.service;

import netflix.news.system.dto.PageRequest;
import netflix.news.system.dto.PageResponse;
import netflix.news.system.entity.Account;
import netflix.news.system.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for account management
 */
@Service
public class AccountService {
    
    @Autowired
    private AccountMapper accountMapper;
    
    /**
     * Find account by ID
     */
    public Account findById(Integer accountId) {
        return accountMapper.findById(accountId);
    }
    
    /**
     * Get all accounts
     */
    public List<Account> findAll() {
        return accountMapper.findAll();
    }
    
    /**
     * Search accounts by name
     */
    public List<Account> searchByName(String keyword) {
        return accountMapper.searchByName(keyword);
    }
    
    /**
     * Get accounts with pagination
     */
    public PageResponse<Account> findWithPagination(PageRequest pageRequest) {
        List<Account> accounts = accountMapper.findWithPagination(
                pageRequest.getOffset(), pageRequest.getSize());
        long total = accountMapper.count();
        return PageResponse.of(accounts, pageRequest.getPage(), pageRequest.getSize(), total);
    }
    
    /**
     * Update account
     */
    @Transactional
    public Account update(Account account) {
        Account existing = accountMapper.findById(account.getAccountId());
        if (existing == null) {
            throw new RuntimeException("Account not found");
        }
        
        accountMapper.update(account);
        return accountMapper.findById(account.getAccountId());
    }
    
    /**
     * Delete account
     */
    @Transactional
    public void delete(Integer accountId) {
        accountMapper.deleteById(accountId);
    }
    
    /**
     * Get total number of accounts
     */
    public long count() {
        return accountMapper.count();
    }
}
