package in.b2k.model.enums;

public enum Role {
    ADMIN("A"), USER("U");

    public String getRole() {
        return role;
    }

    private String role;

    Role(String role) {
        this.role = role;
    }
}
