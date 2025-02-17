package Demo_security.demo.dto;

public class AuthenticationDto {
    private String username;
    private String password;
    private boolean authenticated;  // Trả về kết quả xác thực

    // Constructor
    public AuthenticationDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getter, Setter
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    // Builder pattern for AuthenticationDto
    public static AuthenticationDtoBuilder builder() {
        return new AuthenticationDtoBuilder();
    }

    public static class AuthenticationDtoBuilder {
        private String username;
        private String password;
        private boolean authenticated;

        public AuthenticationDtoBuilder username(String username) {
            this.username = username;
            return this;
        }

        public AuthenticationDtoBuilder password(String password) {
            this.password = password;
            return this;
        }

        public AuthenticationDtoBuilder authenticated(boolean authenticated) {
            this.authenticated = authenticated;
            return this;
        }

        public AuthenticationDto build() {
            AuthenticationDto dto = new AuthenticationDto(username, password);
            dto.setAuthenticated(authenticated);
            return dto;
        }
    }
}
