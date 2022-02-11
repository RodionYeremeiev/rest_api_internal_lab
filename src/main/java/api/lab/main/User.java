package api.lab.main;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private int id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private int userStatus;

    public User(int id, String firstName, String lastName, String password, String phone) {
        this.id = id;
        this.username = "TestUser" + id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = firstName + "_" + lastName + "@epam.com";
        this.password = password;
        this.phone = phone;
        this.userStatus = id;
    }
}
