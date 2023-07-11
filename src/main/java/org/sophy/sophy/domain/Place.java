package org.sophy.sophy.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Place extends AuditingTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private City city;

    //공간 운영자
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(nullable = false, foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    //private Member operator;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Integer maximum;

    private String placeImage;

    @OneToMany(mappedBy = "place")
    private List<Booktalk> booktalkList = new ArrayList<>();

    @Builder
    public Place(City city, String name, String address, Integer maximum, String placeImage) {
        this.city = city;
        this.name = name;
        this.address = address;
        this.maximum = maximum;
        this.placeImage = placeImage;
        this.booktalkList = new ArrayList<>();
    }

    public void deleteBooktalk(Booktalk booktalk) {
        booktalkList.remove(booktalk);
    }
}
