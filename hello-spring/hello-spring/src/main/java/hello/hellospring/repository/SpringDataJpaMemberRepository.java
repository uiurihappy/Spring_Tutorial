package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {

    //findBy하고 Name을 작성하면 jpa에서
    //자동적으로 jpql로 select m from Member m where m.name = ?으로 만들어준다.
    @Override
    Optional<Member> findByName(String name);

}
