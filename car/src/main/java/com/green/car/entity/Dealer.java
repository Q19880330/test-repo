package com.green.car.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString(exclude = "cars")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "dealer_seq", sequenceName = "dealer_seq", allocationSize = 1)
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
//직렬화 프로세스중 cars필드를 무시하게 변경
//(하이버네이트가 생성한 필드를 무시하도록 설정)
public class Dealer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "dealer_seq")
    @Column(name = "dealer_id")
    private Long id;
    private String name;
    private String phone;
    private String location;
    @JsonIgnore
    @OneToMany(mappedBy = "dealer", cascade = CascadeType.ALL)
    //관계 기본키 => 외래키
    //cascade 딜러가 삭제되면 등록자동차도 삭제된다.
    private List<Car> cars = new ArrayList<>();
}
