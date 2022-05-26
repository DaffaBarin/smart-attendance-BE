package com.dipl.smartattendance.web.model.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
