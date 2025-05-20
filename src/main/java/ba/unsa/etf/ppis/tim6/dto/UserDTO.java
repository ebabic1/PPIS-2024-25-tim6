package ba.unsa.etf.ppis.tim6.dto;

import ba.unsa.etf.ppis.tim6.model.Role;
import ba.unsa.etf.ppis.tim6.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long user_id;

    private String username;

    private String password_hash;

    private Long role_id;

    private String first_name;

    private String last_name;

    private String email;

    private String phone_number;

    private String status;
}
