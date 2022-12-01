package kr.inhatc.spring.member.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.crypto.password.PasswordEncoder;

import kr.inhatc.spring.member.constant.Role;
import kr.inhatc.spring.member.dto.MemberFormDto;
import kr.inhatc.spring.utils.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter
@ToString
// default 생성자
@NoArgsConstructor
// 전체 생성자
@AllArgsConstructor
public class Member extends BaseEntity{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto increment
    @Column(name = "member_id")
    private Long id;
    
    private String name;
    
    @Column(nullable = false, unique = true, length = 100)
    private String email;
    
    private String password;
    
    private String address;
    
    @Enumerated(EnumType.STRING)
    private Role role;
    
    
    //사용자 만들기 -> 받아온 DTO를 entity로 변경해준다
    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
        //먼저 Member entity를 만들어준다.
        Member member = new Member();
        member.setName(memberFormDto.getName()); //실제 웹에서 받아올 이름이니까 dto에서 받아온다
        member.setEmail(memberFormDto.getEmail());
        member.setAddress(memberFormDto.getAddress()); 
        member.setRole(Role.USER); //기본으로 다 학생으로 가입
        
        String password = passwordEncoder.encode(memberFormDto.getPassword()); //일단 비밀번호를 플레인 텍스트로 받아온다. 후 encode를 이용해서 암호화
        member.setPassword(password); //암호화 된 패스워드를 넣는다
        
        //만든 member 리턴
        return member;
    }

}
