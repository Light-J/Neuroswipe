package com.nsa.cubric.application.services;

public interface AdminServicesStatic {
    boolean removeUser(Long userId);
    Integer removeUserResponses(Long userId);
    boolean updateUserRole(Long userId, String role);


}
