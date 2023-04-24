package hellojpa;

import javax.persistence.*;
import java.util.Date;

@Entity
@SequenceGenerator(name = "member_seq_generator", sequenceName = "member_seq")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "member_seq_generator")
    private Long id;
    @Column(name = "name")
    private String username;

    public Member(){
    }


}
