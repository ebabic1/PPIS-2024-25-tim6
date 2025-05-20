package ba.unsa.etf.ppis.tim6.mapper;

import ba.unsa.etf.ppis.tim6.dto.UserDTO;
import ba.unsa.etf.ppis.tim6.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "user_id", source = "userId")
    @Mapping(target = "password_hash", source = "passwordHash")
    @Mapping(target = "role_id", source = "role.roleId")
    @Mapping(target = "first_name", source = "firstName")
    @Mapping(target = "last_name", source = "lastName")
    @Mapping(target = "phone_number", source = "phoneNumber")
    @Mapping(target = "status", source = "status")
    UserDTO userToUserDTO(User user);
}
