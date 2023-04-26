package jpabook.jpashop.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable @Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Period {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
