package com.prabin.expensesplitter.expense_splitter.repo;

import com.prabin.expensesplitter.expense_splitter.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findByMembersId(Long userId);
}

