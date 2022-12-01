package kr.inhatc.spring.cart.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import kr.inhatc.spring.item.entity.Item;
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
public class CartItem extends BaseEntity{
    
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY )
    @Column(name = "cart_item_id")
    private Long id;
    
//    카트가 여러 개임
    @ManyToOne(fetch = FetchType.LAZY)
//    cart id랑 조인을 걸어준다
    @JoinColumn(name = "cart_id")
    private Cart cart;
    
//    하나의 상품에 여러 개가 담김
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;
    
    private int count;
    

}
