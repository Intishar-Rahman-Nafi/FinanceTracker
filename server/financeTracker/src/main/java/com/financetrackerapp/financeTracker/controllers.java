package com.financetrackerapp.financeTracker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

@RestController
public class controllers {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Check if 'users' table is accessible
    @GetMapping("/test-users-table")
    public List<Map<String, Object>> testUsersTable() {
        return jdbcTemplate.queryForList("SELECT * FROM users LIMIT 5");
    }

    // Check if other tables exist (returns empty if not found)
    @GetMapping("/test-tables")
    public String testTables() {
        try {
            jdbcTemplate.queryForList("SELECT 1 FROM accounts LIMIT 1");
            jdbcTemplate.queryForList("SELECT 1 FROM transactions LIMIT 1");
            jdbcTemplate.queryForList("SELECT 1 FROM categories LIMIT 1");
            return "✅ All tables exist!";
        } catch (Exception e) {
            return "❌ Missing tables: " + e.getMessage();
        }
    }
}