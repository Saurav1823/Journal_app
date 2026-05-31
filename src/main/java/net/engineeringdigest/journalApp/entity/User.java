package net.engineeringdigest.journalApp.entity;

import lombok.Data;
import lombok.NonNull;
import javax.persistence.*;

import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;

@NoArgsConstructor
@Entity
@Table(name = "users")
@Data
public class User {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

    @Column(unique = true)
    @NonNull
//    @JsonAlias({"userName", "username"})
    private String userName;
    @NonNull
    private String password;

   @OneToMany(cascade = CascadeType.ALL)
   @JoinColumn(name = "user_id")
   private List<JournalEntry> journalEntries = new ArrayList<>();


    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;
}
