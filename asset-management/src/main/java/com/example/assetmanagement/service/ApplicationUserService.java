package com.example.assetmanagement.service;

import com.example.assetmanagement.dto.ApplicationUserRequest;
import com.example.assetmanagement.entity.ApplicationUser;
import com.example.assetmanagement.entity.UserAccount;
import com.example.assetmanagement.mapper.ApplicationUserMapper;
import com.example.assetmanagement.mapper.UserAccountMapper;
import com.example.assetmanagement.repository.ApplicationUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationUserService {
    private final ApplicationUserRepository applicationUserRepository;


    public void saveUserAccount(ApplicationUserRequest applicationUserRequest) {
        ApplicationUser applicationUser = ApplicationUserMapper.INSTANCE.mapRequestToApplicationUser(applicationUserRequest);

    }
}
