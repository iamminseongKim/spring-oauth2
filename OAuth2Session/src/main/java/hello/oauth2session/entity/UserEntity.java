package hello.oauth2session.entity;

import hello.oauth2session.dto.SiteType;
import hello.oauth2session.dto.UserRoll;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class UserEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;

    @Enumerated(EnumType.STRING)
    private UserRoll userRoll;
    @Enumerated(EnumType.STRING)
    private SiteType siteType;

}
