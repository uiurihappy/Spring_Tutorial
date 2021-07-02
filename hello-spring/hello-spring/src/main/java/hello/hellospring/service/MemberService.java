package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.awt.*;
import java.util.Optional;

@Transactional //jpa를 사용하기 위한 트랜잭션
//spring bean
// @Service //스프링이 올라올 때 연결을 위해 스프링 컨테이너에 서비스 등록
public class MemberService {

    private final MemberRepository memberRepository;

    // @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /*
     *회원 가입
     */
    public Long join(Member member){
        //AOP (Aspect Oriented Programming)
        //시간 측정
        //long start = System.currentTimeMillis();
        //try{
            //같은 이름이 있는 중복 회원 x
            //optional로 감싸면 optional안에 객체가 있다는 뜻이다.
            //optional의 여러 메소드 사용 가능
            //꺼내서 사용하고 싶으면 result.get(); 으로 사용하면 된다.
            validateDuplicateMember(member); //중복 회원 검증

            memberRepository.save(member);
            return member.getId();
        //}finally {
            //long finish = System.currentTimeMillis();
            //long timeMs = finish - start;
            //System.out.println("join = " + timeMs + "ms");
        //}

    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
        //orElseGet: 값이 있으면 꺼내고 값이 없으면 해당 메소드를 실행 result.orElseGet()
        //값이 있으면 예외 처리 실행
                .ifPresent(m -> {
                    throw  new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /*
     *전체 회원 조회
     */
    public List<Member> findMembers(){
        //시간 측정
        //long start = System.currentTimeMillis();
        //try{
            return memberRepository.findAll();

        //}finally {
        //    long finish = System.currentTimeMillis();
        //    long timeMs = finish - start;
        //    System.out.println("findMembers " + timeMs + "ms");
        //}

    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }
}
