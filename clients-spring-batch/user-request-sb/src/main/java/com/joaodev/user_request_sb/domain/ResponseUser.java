package com.joaodev.user_request_sb.domain;

import java.util.List;

import com.joaodev.user_request_sb.dto.UserDTO;

public class ResponseUser {

    private List<UserDTO> content;

    public List<UserDTO> getContent() {
        return content;
    }
}
