package hellojpa;

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
            embeddedChange(em);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    private static void embeddedChange(EntityManager em) {
        Address address = new Address("city", "street", "10");

        Member member1 = new Member();
        member1.setUsername("member1");
        member1.setHomeAddress(address);
        em.persist(member1);

        Address copyAddress = new Address(address.getCity(), address.getStreet(), address.getZipcode());


        Member member2 = new Member();
        member2.setUsername("member2");
        member2.setHomeAddress(copyAddress);
        em.persist(member2);

        member1.getHomeAddress().setCity("newCity"); // member2도 변경
    }

    private static void embedded(EntityManager em) {
        Member member = new Member();
        member.setUsername("hello");
        member.setHomeAddress(new Address("city", "street", "10"));
        member.setWorkPeriod(new Period());
        em.persist(member);
    }

    private static void cascade(EntityManager em) {

        Child child1 = new Child();
        Child child2 = new Child();

        Parent parent = new Parent();
        parent.addChild(child1);
        parent.addChild(child2);

        em.persist(parent);
//        em.persist(child1);
//        em.persist(child2);
    }

    private static void lazyLoading(EntityManager em) {
        Team team = new Team();
        team.setName("teamA");
        em.persist(team);

        Member member = new Member();
        member.setUsername("member1");
        member.setTeam(team);
        em.persist(member);

        em.flush();
        em.clear();

//        Member m = em.find(Member.class, member.getId());
//        System.out.println("m = " + m.getTeam().getClass());
        List<Member> members = em.createQuery("select m from Member m join fetch m.team", Member.class)
                .getResultList();
    }

    private static void proxyBasic(EntityManager em) {
        Member member = new Member();
        member.setUsername("hello");
        em.persist(member);

        em.flush();
        em.clear();

        Member findMember = em.getReference(Member.class, member.getId()); // db 쿼리 X

        System.out.println("findMember = " + findMember.getClass());
        System.out.println("findMember = " + findMember.getId());
        System.out.println("findMember = " + findMember.getUsername());
    }

    private static void printMember(Member member) {
        String username = member.getUsername();
        System.out.println("username = " + username);
    }

    private static void printMemberAndTeam(Member member) {
        String username = member.getUsername();
        System.out.println("username = " + username);

        Team team = member.getTeam();
        System.out.println("team = " + team.getName());
    }

    private static void mappedSuperclass(EntityManager em) {
        Member member = new Member();
        member.setUsername("user1");
        member.setCreatedBy("kim");
        member.setCreatedDate(LocalDateTime.now());

        em.persist(member);

    }

    private static void inherit_join(EntityManager em) {
        Movie movie = new Movie();
        movie.setDirector("aaaa");
        movie.setActor("bbbb");
        movie.setName("바람과 함께 사라지다");
        movie.setPrice(10000);
        em.persist(movie);

        em.flush();
        em.clear();

        Movie findMovie = em.find(Movie.class, movie.getId());
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
