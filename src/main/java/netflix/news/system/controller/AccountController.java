package netflix.news.system.controller;

import netflix.news.system.dto.ApiResponse;
import netflix.news.system.dto.PageRequest;
import netflix.news.system.dto.PageResponse;
import netflix.news.system.entity.Account;
import netflix.news.system.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * REST Controller for Account operations (Employee only)
 */
@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    
    @Autowired
    private AccountService accountService;
    
    /**
     * Get all accounts
     */
    @GetMapping
    public ApiResponse<List<Account>> getAll() {
        return ApiResponse.success(accountService.findAll());
    }
    
    /**
     * Get accounts with pagination
     */
    @GetMapping("/page")
    public ApiResponse<PageResponse<Account>> getPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPage(page);
        pageRequest.setSize(size);
        return ApiResponse.success(accountService.findWithPagination(pageRequest));
    }
    
    /**
     * Get account by ID
     */
    @GetMapping("/{id}")
    public ApiResponse<Account> getById(@PathVariable Integer id) {
        Account account = accountService.findById(id);
        if (account == null) {
            return ApiResponse.notFound("Account not found");
        }
        return ApiResponse.success(account);
    }
    
    /**
     * Search accounts by name
     */
    @GetMapping("/search")
    public ApiResponse<List<Account>> search(@RequestParam String keyword) {
        return ApiResponse.success(accountService.searchByName(keyword));
    }
    
    /**
     * Update account
     */
    @PutMapping("/{id}")
    public ApiResponse<Account> update(@PathVariable Integer id, @Valid @RequestBody Account account) {
        try {
            account.setAccountId(id);
            Account updated = accountService.update(account);
            return ApiResponse.success("Account updated successfully", updated);
        } catch (Exception e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }
    
    /**
     * Delete account
     */
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Integer id) {
        try {
            accountService.delete(id);
            return ApiResponse.success("Account deleted successfully");
        } catch (Exception e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }
}
