package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

// @Repository
public class MemoryMemberRepository implements MemberRepository{

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L; //0,1,2 같은 키 값을 생성해준다.

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        //null이 반환될 가능성이 존재하므로 Optional.ofNullable을 이용해서 null이여도 감싸서 반환
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        //map에서 찾아주면 루프가 끝나면서 반환을 해준다
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        //store에 있는 values들을 전부 반환된다.
        return new ArrayList<>(store.values());
    }
    //store를 싹 비운다
    public void ClearStore(){
        store.clear();
    }
}
