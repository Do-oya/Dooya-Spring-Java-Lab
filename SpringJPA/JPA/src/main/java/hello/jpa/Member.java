//package hello.jpa;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//@Entity
//@Getter
//@Setter
//public class Member {
//
//    @Id
//    @Column(name = "MEMBER_ID")
//    private String id;
//    private String username;
//
//    @ManyToOne
//    @JoinColumn(name = "TEAM_ID")
//    private Team team;
//
//    public Member(String id, String username) {
//        this.id = id;
//        this.username = username;
//    }
//
//    public Member() {
//
//    }
//}
