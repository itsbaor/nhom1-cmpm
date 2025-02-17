package Demo_security.demo.dto;

public class UserUpdateDto {

    private String firtname;
    private String lastname;
    private String password;

    public UserUpdateDto( String firstname, String lastname, String password) {
        this.firtname = firstname;
        this.lastname = lastname;
        this.password = password;
    }

    // Getters and Setters
    public String getFirtname() {
        return firtname;
    }

    public void setFirtname(String firtname) {
        this.firtname = firtname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
