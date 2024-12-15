//package hello.jpa;
//
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.EntityManagerFactory;
//import jakarta.persistence.Persistence;
//
//public class JpaEx04 {
//    public static void main(String[] args) {
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
//        EntityManager em = emf.createEntityManager();
//
//        Team team1 = new Team("team1", "팀1");
//
//        Member member1 = new hello.jpa.Member("member1", "회원1");
//        member1.setTeam(team1); // 연관관계 설정 member1 -> team1
//        em.persist(member1);
//
//        Member member2 = new Member("member2", "회원2");
//        member2.setTeam(team1);
//        em.persist(member2);
//
//        Member member = em.find(Member.class, "member1");
//        Team team = member.getTeam();
//        System.out.println(team.getName());
//    }
//}
