package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member); //회원이 저장소에 저장
    Optional<Member> findById(Long id); //id 찾기
    Optional<Member> findByName(String name); //name 찾기
    List<Member> findAll(); //지금까지 저장된 회원 정보 리스트를 반환

}
