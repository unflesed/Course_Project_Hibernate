package web_service;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String user_name;
    private String password;
    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "profile_id")
    private Profile profile;
    @OneToOne
    @JoinColumn(name = "user_role_id")
    private UserRole userRole;
    @ManyToMany
    @JoinTable(name = "user_services",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "service_id"))
    private List<Service> services = new ArrayList<>();

    public User() {
    }

    public User(String user_name, String password, Profile profile, UserRole userRole) {
        this.user_name = user_name;
        this.password = password;
        this.profile = profile;
        this.userRole = userRole;
    }

    public long getId() {
        return id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addService(Service service){
        services.add(service);
    }

    public void deleteService(Service service){
        services.remove(service);
    }

    public List<Service> getServices() {
        return services;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return id + " " + user_name + " " + password + " " + profile.getFirstName() + " " +
                profile.getLastName() + " " + profile.getPostalCode() +
                " " + profile.geteMail() + " " + profile.getPhoneNumber() +
                " " + userRole.getRole_name() + " " + userRole.getRole_description() +
                " " + services;
    }
}
