package pl.shonsu.shop.security.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private boolean enabled;

    //    @Id
//    @GeneratedValue(generator = "UUID")
//    @GenericGenerator(
//            name = "UUID",
//            strategy = "org.hibernate.id.UUIDGenerator"
//    )
    @Column(name = "uuid", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    //@Type(type = "uuid-char")
    private UUID uuid = UUID.randomUUID();
    @ElementCollection
    @CollectionTable(name = "authorities", joinColumns = @JoinColumn(name = "username", referencedColumnName = "username"))
    @Column(name = "authority")
    @Enumerated(EnumType.STRING)
    private List<UserRole> authorities;

    public static UserBuilder builder() {
        return new UserBuilder();
    }

//    @PrePersist
//    private void assignUUID() {
//        this.uuid = UUID.randomUUID();
//    }

    public static class UserBuilder {
        private UUID uuid  =  UUID.randomUUID();
    }
}
