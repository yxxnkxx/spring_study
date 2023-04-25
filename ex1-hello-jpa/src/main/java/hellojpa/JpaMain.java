package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            단방향저장(em);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    private static void 단방향저장(EntityManager em) {
        Team team = new Team();
        team.setName("TeamA");
        em.persist(team);

        Member member = new Member();
        member.setUsername("member1");
        member.changeTeam(team);
        em.persist(member);

        em.flush();
        em.clear();

        Member findMember = em.find(Member.class, member.getId());
        List<Member> members = findMember.getTeam().getMembers();
        for (Member m : members) {
            System.out.println("m = " + m.getUsername());
        }
    }

    private static void save(EntityManager em) {
        Member member = new Member();
        member.setId(1L);
        member.setUsername("HelloA");
        em.persist(member);

    }

    private static void getById(EntityManager em) {

        Member findMember = em.find(Member.class, 1L);
        System.out.println("findMember = " + findMember.getId());


    }

    private static void delete(EntityManager em) {

        Member findMember = em.find(Member.class, 1L);
        em.remove(findMember);

    }

    private static void update(EntityManager em) {

        Member findMember = em.find(Member.class, 1L);
//            findMember.setName("HelloJPA");

    }

    private static void jpql(EntityManager em) {

        List<Member> result = em.createQuery("select m from Member m", Member.class).getResultList();
        for (Member member : result) {
//                System.out.println("member = " + member.getName());

        }
    }

    private static void context(EntityManager em) {

//            // 비영속
//            Member member = new Member();
//            member.setId(100L);
//            member.setName("HelloJPA");

        // 영속
//            em.persist(member);
        Member findMember1 = em.find(Member.class, 100L); // DB
        Member findMember2 = em.find(Member.class, 100L); // 1차 캐시

        // 동일성 보장
        System.out.println("result = " + (findMember1 == findMember2));


    }

    private static void em_update(EntityManager em) {

        Member member = em.find(Member.class, 100L);
//            member.setName("ZZZZZ");

//            em.persist(member);

        System.out.println("===============");

    }

}
