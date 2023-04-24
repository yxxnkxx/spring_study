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

//        getById(em);
//        update(em);
//        jpql(em);
        em_update(em);
        emf.close();
    }

    private static void save(EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Member member = new Member();
//            member.setId(1L);
//            member.setName("HelloA");
            em.persist(member);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
    }

    private static void getById(EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Member findMember = em.find(Member.class, 1L);
//            System.out.println("findMember = " + findMember.getId());
//            System.out.println("findMember.getName() = " + findMember.getName());
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
    }

    private static void delete(EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Member findMember = em.find(Member.class, 1L);
            em.remove(findMember);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
    }

    private static void update(EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Member findMember = em.find(Member.class, 1L);
//            findMember.setName("HelloJPA");
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
    }

    private static void jpql(EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            List<Member> result = em.createQuery("select m from Member m", Member.class).getResultList();
            for (Member member : result) {
//                System.out.println("member = " + member.getName());
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
    }

    private static void context(EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
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


            // sql 실행
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
    }

    private static void em_update(EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Member member = em.find(Member.class, 100L);
//            member.setName("ZZZZZ");

//            em.persist(member);

            System.out.println("===============");
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
    }

}
