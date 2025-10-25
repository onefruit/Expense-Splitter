package com.prabin.expensesplitter.expense_splitter.payload.request;

import lombok.Data;

import java.util.List;

@Data
public class CreateGroupRequest {
    private String name;
    private List<Long> memberIds;
//    private Long userId;
}
