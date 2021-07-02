package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

// 스프링 컨테이너와 테스트를 함께 실행한다.
@SpringBootTest
// 테스트 케이스에 이 애노테이션이 있으면, 테스트 시작 전에 트랜잭션을 시작하고,
// 테스트 완료 후에 항상 롤백한다.
// 이렇게 하면 DB에 데이터가 남지 않으므로 다음 테스트에 영향을 주지 않는다.
@Transactional
class MemberServiceIntegrationTest {
    //스프링 컨테이너한테 MemberRepository, MemberService 내놔!
    //필드 기반의 Autowired를 한다.
    @Autowired  MemberService memberService;
    @Autowired MemberRepository repository;    // test 케이스에서는 메소드는 한글로도 수정 가능하다.

    @Test
    void 회원가입() {

        //문법
        //given: 뭔가가 주어졌을때
        Member member = new Member();
        member.setName("hello");

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


}