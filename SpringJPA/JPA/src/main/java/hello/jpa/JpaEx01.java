//package hello.jpa;
//
//import hello.jpa.entity.Member;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.EntityManagerFactory;
//import jakarta.persistence.Persistence;
//
//public class JpaEx01 {
//    public static void main(String[] args) {
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
//
//        // 객체를 생성한 생태(비영속)
//        Member member = new Member();
//        member.setMemberId("memberA");
//        member.setUsername("테스트");
//
//        EntityManager em = emf.createEntityManager();
//        em.getTransaction().begin();
//
//        // 객체를 저장한 상태(영속)
//        // 영속 상태가 된다고 바로 DB에 쿼리가 날라가는 것은 아님 -> 이후에 커밋해야 저장
//        em.persist(member);
//
//        // 회원 엔티티를 영속성 컨텍스트에서 분리, 준영속 상태
//        em.detach(member);
//
//        // 객체를 삭제한 상태(삭제)
//        em.remove(member);
//    }
//}
