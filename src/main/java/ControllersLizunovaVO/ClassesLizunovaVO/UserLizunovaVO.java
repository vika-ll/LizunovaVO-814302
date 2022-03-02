package ClassesLizunovaVO;

import java.io.Serializable;

public class UserLizunovaVO implements Serializable{
    private int ID_user;
    private String login;
    private String password;
    private Boolean isBlocked;
    private RoleLizunovaVO userRole;
    private ProfileLizunovaVO userProfile;


    public UserLizunovaVO(int ID_user, String login, String password, Boolean isBlocked, RoleLizunovaVO userRole, ProfileLizunovaVO userProfile) {
        this.ID_user = ID_user;
        this.login = login;
        this.password = password;
        this.isBlocked = isBlocked;
        this.userRole = userRole;
        this.userProfile = userProfile;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Boolean getBlocked() {
        return isBlocked;
    }

    public void setBlocked(Boolean blocked) {
        isBlocked = blocked;
    }

    public RoleLizunovaVO getUserRole() {
        return userRole;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserRole(RoleLizunovaVO userRole) {
        this.userRole = userRole;
    }

    public ProfileLizunovaVO getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(ProfileLizunovaVO userProfile) {
        this.userProfile = userProfile;
    }

    public int getID_user() {
        return ID_user;
    }

    public void setID_user(int ID_user) {
        this.ID_user = ID_user;
    }

}
