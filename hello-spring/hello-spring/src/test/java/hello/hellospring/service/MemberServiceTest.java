package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository repository;    // test 케이스에서는 메소드는 한글로도 수정 가능하다.

    //일종의 단위 테스트로 java 코드로 각 메소드를 구분하여 테스팅 할 수 있다.

    @BeforeEach
    public void beforeEach(){
        //dependency injection (DI)
        repository = new MemoryMemberRepository();
        memberService = new MemberService(repository);
    }

    @AfterEach
    public void afterEach(){
        repository.ClearStore();
    }
    @Test
    void 회원가입() {

        //문법
        //given: 뭔가가 주어졌을때
        Member member = new Member();
        member.setName("Hello");

        //when: 이거를 실행했을때
        Long saveID = memberService.join(member); //이름 저장

        //then: 결과가 이것이 나와야 한다.
        Member findMember = memberService.findOne(saveID).get();
        assertThat(member.getName()).isEqualTo(findMember.getName()); // 검증

    }
    @Test
    public void 중복_회원_예외(){
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
        // try catch문이 애매할 경우
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        /* try catch
        try {
            memberService.join(member2);
            fail();
        }catch (IllegalStateException e){
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다."); //service 예외처리 문구와 같아야 한다.
        }
        */

        //then
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}