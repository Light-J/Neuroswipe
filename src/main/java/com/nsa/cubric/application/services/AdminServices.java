package com.nsa.cubric.application.services;

public interface AdminServices {
    boolean removeUser(Long userId);
    Integer removeUserResponses(Long userId);
    boolean updateUserRole(Long userId, String role);
    boolean updateUserDisabledStatus(Long userId, String disabled);


}
