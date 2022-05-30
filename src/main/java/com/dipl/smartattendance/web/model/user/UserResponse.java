package com.dipl.smartattendance.web.model.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
/**
 * Response object for User
 */
public class UserResponse {
    String id;
    String nip;
    String fullName;
}
