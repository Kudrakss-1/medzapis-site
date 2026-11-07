package ru.medzapis.model;

import jakarta.persistence.*;

@Entity
@Table(name = "app_users")
public class AppUser {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false, unique=true) private String username;
    @Column(nullable=false) private String password;
    @Column(nullable=false) private String fullName;
    @Enumerated(EnumType.STRING) @Column(nullable=false) private Role role;
    private String specialization;

    protected AppUser() {}
    public AppUser(String username, String password, String fullName, Role role, String specialization) {
        this.username=username; this.password=password; this.fullName=fullName; this.role=role; this.specialization=specialization;
    }
    public Long getId(){return id;}
    public String getUsername(){return username;}
    public String getPassword(){return password;}
    public String getFullName(){return fullName;}
    public Role getRole(){return role;}
    public String getSpecialization(){return specialization;}
}
