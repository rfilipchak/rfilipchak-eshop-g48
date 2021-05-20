package ua.mainacademy.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class User {

    private Long id;
    @NonNull
    private String login;
    @NonNull
    private String password;
    @NonNull
    private String firstName;

    private String lastName;
    @NonNull
    private String email;

    private String phone;
}
