package com.prabin.expensesplitter.expense_splitter.controller;

import com.prabin.expensesplitter.expense_splitter.model.Group;
import com.prabin.expensesplitter.expense_splitter.model.User;
import com.prabin.expensesplitter.expense_splitter.payload.request.CreateGroupRequest;
import com.prabin.expensesplitter.expense_splitter.payload.response.GroupResponse;
import com.prabin.expensesplitter.expense_splitter.service.GroupService;
import com.prabin.expensesplitter.expense_splitter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Group> createGroup(
            @RequestBody CreateGroupRequest request) {

        User currentProfile = userService.getCurrentProfile();
        Group group = groupService.createGroup(
                request.getName(),
                currentProfile.getId(),
                request.getMemberIds()
        );

        return ResponseEntity.ok(group);
    }

    @GetMapping
    public ResponseEntity<List<GroupResponse>> getUserGroups() {
        User currentProfile = userService.getCurrentProfile();
        List<GroupResponse> groups = groupService.getUserGroups(currentProfile.getId());
        return ResponseEntity.ok(groups);
    }
}