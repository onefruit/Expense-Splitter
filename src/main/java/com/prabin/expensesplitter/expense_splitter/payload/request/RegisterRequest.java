package com.prabin.expensesplitter.expense_splitter.payload.request;

import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
}
