//package hello.jpa;
//
//import hello.jpa.entity.Member;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.EntityManagerFactory;
//import jakarta.persistence.Persistence;
//
//public class JpaEx02 {
//    public static void main(String[] args) {
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
//        EntityManager em = emf.createEntityManager();
//
//        // 엔티티를 생성한 상태(비영속)
//        Member member = new Member();
//        member.setMemberId("member1");
//        member.setUsername("회원1");
//
//        // 엔티티를 영속 (1차 캐시에 저장됨)
//        em.persist(member);
//
//        // 1차 캐시에서 조회
//        Member findMember = em.find(Member.class, "member1");
//        System.out.println(findMember.getUsername());
//        // 데이터베이스에서 조회
//        Member findMember2 = em.find(Member.class, "member");
//        System.out.println(findMember2.getUsername());
//
//
//        Member a = em.find(Member.class, "member1");
//        Member b = em.find(Member.class, "member1");
//
//        System.out.println(a == b);
//    }
//}
