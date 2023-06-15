package org.core.builders;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterRequest {
    private String password;
    private String apiVersion;
    private int partnerId;
    private User user;

    @Data
    @Builder
    public static class User {
        private String firstName;
        private String lastName;
        private String address;
        private String city;
        private String externalId;
        private String email;
        private int countryId;
        private String objectType;
        private String username;
    }
}