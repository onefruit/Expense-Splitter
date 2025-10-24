package com.prabin.expensesplitter.expense_splitter.repo;

import com.prabin.expensesplitter.expense_splitter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
