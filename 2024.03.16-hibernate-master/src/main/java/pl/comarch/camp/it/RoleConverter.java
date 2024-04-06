package pl.comarch.camp.it;

import jakarta.persistence.AttributeConverter;

public class RoleConverter implements AttributeConverter<User.Role, String> {
    @Override
    public String convertToDatabaseColumn(User.Role attribute) {
        return switch(attribute) {
            case ADMIN -> "administrator";
            case USER -> "uzytkownik";
        };
    }

    @Override
    public User.Role convertToEntityAttribute(String dbData) {
        return switch(dbData) {
            case "administrator" -> User.Role.ADMIN;
            case "uzytkownik" -> User.Role.USER;
            default -> throw new IllegalArgumentException();
        };
    }
}
