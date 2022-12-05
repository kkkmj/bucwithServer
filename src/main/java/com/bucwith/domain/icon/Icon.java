package com.bucwith.domain.icon;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;


@Getter
@Setter
@Entity
public class Icon {
    @Id
    private String iconCode;
    private String imgUrl;
    private LocalDateTime registDate;
}
