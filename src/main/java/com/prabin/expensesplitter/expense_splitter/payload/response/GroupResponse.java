package com.prabin.expensesplitter.expense_splitter.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GroupResponse {
    private Long id;
    private String name;
    private List<UserResponse> members;
//    private UserResponse createdBy;
}

