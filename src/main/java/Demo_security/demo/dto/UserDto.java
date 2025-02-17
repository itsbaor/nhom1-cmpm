package Demo_security.demo.dto;

public class UserDto {
    private Long id;
    private String location;
    private String dateOfBirth;
    private String email;
    private String password;
    private String numberPhone;
    private String image;
    private String backgroundImage;
    private String bio;
    private boolean req_user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public boolean isReq_user() {
        return req_user;
    }

    public void setReq_user(boolean req_user) {
        this.req_user = req_user;
    }

    public UserDto(Long id, String location, String dateOfBirth, String email, String password, String numberPhone, String image, String backgroundImage, String bio, boolean req_user) {
        this.id = id;
        this.location = location;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.password = password;
        this.numberPhone = numberPhone;
        this.image = image;
        this.backgroundImage = backgroundImage;
        this.bio = bio;
        this.req_user = req_user;
    }
}
