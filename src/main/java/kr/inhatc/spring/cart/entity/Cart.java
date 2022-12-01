package kr.inhatc.spring.cart.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import kr.inhatc.spring.member.entity.Member;
import kr.inhatc.spring.utils.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Cart extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY )
    @Column(name = "cart_id")
    private Long id;
    
//    일대일관계
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id") //member의 member_id와 join 하겠다는 그런 의미
    private Member member;

}
