package com.hcmute.tech_shop.services.interfaces;

import com.hcmute.tech_shop.dtos.requests.UserDTO;
import com.hcmute.tech_shop.entities.User;

public interface UserService {
    User createUser(UserDTO userDTO);
}
