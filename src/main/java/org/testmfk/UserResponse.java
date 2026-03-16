package org.testmfk;

import lombok.Data;

@Data
public class UserResponse {
    private String id;
    private CreateUserRequest createUserRequest;
}
