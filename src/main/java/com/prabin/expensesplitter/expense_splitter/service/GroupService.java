package com.prabin.expensesplitter.expense_splitter.service;

import com.prabin.expensesplitter.expense_splitter.model.Group;
import com.prabin.expensesplitter.expense_splitter.model.User;
import com.prabin.expensesplitter.expense_splitter.payload.response.GroupResponse;
import com.prabin.expensesplitter.expense_splitter.payload.response.UserResponse;
import com.prabin.expensesplitter.expense_splitter.repo.GroupRepository;
import com.prabin.expensesplitter.expense_splitter.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public Group createGroup(String groupName, Long creatorId, List<Long> memberIds) {
//        User creator = userRepository.findById(creatorId)
//                .orElseThrow(() -> new RuntimeException("User not found"));

        User currentProfile = userService.getCurrentProfile();
        List<User> members = userRepository.findAllById(memberIds);
        if (!members.contains(currentProfile)) {
            members.add(currentProfile);
        }

        Group group = Group.builder()
                .name(groupName)
                .createdBy(currentProfile)
                .members(members)
                .build();

        return groupRepository.save(group);
    }

    //    public List<Group> getUserGroups(Long userId) {
//        return groupRepository.findByMembersId(userId);
//    }
    public List<GroupResponse> getUserGroups(Long userId) {
        List<Group> groups = groupRepository.findByMembersId(userId);

        return groups.stream().map(group -> new GroupResponse(
                group.getId(),
                group.getName(),
                group.getMembers().stream()
                        .map(member -> new UserResponse(member.getId(), member.getName(), member.getEmail()))
                        .toList()
        )).toList();
    }
}
