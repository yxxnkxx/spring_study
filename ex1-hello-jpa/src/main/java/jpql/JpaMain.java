package jpql;

import jpql.Member;
import jpql.Team;
import hellojpa.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Member member = new Member();
            member.setId(1L);
            member.setUsername("멤버1");
            em.persist(member);

            Team team = new Team();
            team.setId(2L);
            team.setName("팀1");
            em.persist(team);
//            team.getMembers().add(member);
            member.setTeam(team);

            Team team1 = em.find(Team.class, 2L);
            System.out.println(team1.getMembers().size());


            em.flush();
            em.clear();

            Member member1 = em.find(Member.class, 1L);
            System.out.println(member1.getUsername());






            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }



}
