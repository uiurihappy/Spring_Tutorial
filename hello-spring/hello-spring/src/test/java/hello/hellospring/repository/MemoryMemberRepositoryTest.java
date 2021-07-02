package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

public class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    //메소드가 어떤 실행을 하고 끝날 때마다 동작을 한다.
    @AfterEach
    public void AfterEach(){
        //한 메소드가 끝날 때마다 repository를 싹 비워준다.
        repository.ClearStore();
    }

    @Test
    public void save(){
        //test
        Member mem = new Member();  //Member 객체
        mem.setName("spring");      //이름 설정

        repository.save(mem);       //repository에 save를 해보는것이다.
        //test code
        Member result = repository.findById(mem.getId()).get();   //내가 넣은 아이디가 잘 저장되었는지

        //검증
        //System.out.println("result = " + (result == mem));
        //글로만 계속 볼 수 없으니 Assertions.assertEquals사용
        //Assertions.assertEquals(mem, result); //junit
        assertThat(mem).isEqualTo(result); //assertj 사용
        //alt + enter로 static import하면 좀 더 짧게 사용 가능

    }

    @Test
    public void findByName(){
        Member mem1 = new Member();
        mem1.setName("spring1");    //이름 설정
        repository.save(mem1);      //멤버 저장

        Member mem2 = new Member();
        mem2.setName("spring2");    //이름 설정
        repository.save(mem2);      //멤버 저장

        //spring1을 찾기
        Member result = repository.findByName("spring1").get();

        //spring2를 찾기하면 에러가 발생 assertThat에서 mem1을 찾기 원하기에
        //Member result2 = repository.findByName("spring2").get();

        assertThat(result).isEqualTo(mem1);

        //전체 클래스 run했을 시 findByName이 에러가 발생하는 이유
        //테스트 순서 보장이 안되서다.
        //findAll에서 spring1,2가 저장이 되버려서다.
        //해결법: 데이터를 클리어 해준다.
    }
    @Test
    public void findAll(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();
        //isEqualTo가 3인 경우 에러 발생, Member 리스트에 2명의 이름밖에 없어서이다.
        assertThat(result.size()).isEqualTo(2);

    }


}
