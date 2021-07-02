package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import javax.persistence.EntityManager;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository{

    //jpa는 EntityManager로 모든 것을 동작한다.
    //Spring boot가 알아서 데이터베이스 연결까지 해서 EntityManager를 만든다.
    //만든 걸 인젝션만 하면 된다.
    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        //jpa가 insert 쿼리까지 다 만들어서 데이터를 삽입한다.
        //id까지 setid 다 해서 삽입
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        //Optional로 명시적 형 변환 해준다.
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        //name은 pk인 id와 다르게 특별한 jpql이라는 객체지향 쿼리를 사용해야 한다.
        //차이: jpa는 매핑 과정을 알아서 해준다.
        // 트랜잭션이라는게 필요하다.
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        //jpql 쿼리문
        //객체를 대상으로 쿼리를 날린다.
        List<Member> result = em.createQuery("select m from Member m", Member.class)
                .getResultList();
        return result;

    }
}
