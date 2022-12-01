package kr.inhatc.spring.order.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import kr.inhatc.spring.member.entity.Member;
import kr.inhatc.spring.order.constant.OrderStatus;
import kr.inhatc.spring.utils.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Table(name = "orders")
@ToString
@NoArgsConstructor
public class Order extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY )
    @Column(name = "order_id")
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    
//    얘는 하나고 저쪽은 여러개임
//    cascadetype all : order1번을 지우면, order 테이블에서 id가 1번인 애들의 값이 싹 다 지워지게 연관 관계를 설정해놓은 것 
//    orphanRemoval = true. 고아객체 제거하겠다는 옵션
//    FetchType.LAZY = 지연로딩 설정
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//    저쪽에서 만들어진 애가 실제로 사용될 때 보면 리스트로 제공된다. 
//    자바에서 둘의 관계를 적용하기 위한 코드일 뿐 실제 디비에는 orderItems가 들어가진 않는다. 
//    db는 그냥 order_id로 조인이 걸림!!
    private List<OrderItem> orderItems = new ArrayList<>();
    
    private LocalDateTime orderDate;
    
    private OrderStatus orderStatus;


}
