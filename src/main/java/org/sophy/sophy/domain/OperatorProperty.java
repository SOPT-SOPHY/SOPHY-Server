package org.sophy.sophy.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class OperatorProperty {

    @Id
    @GeneratedValue
    @Column(name = "operator_property_id")
    private Long id;

    @OneToMany(mappedBy = "operatorProperty", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Place> myPlaceList = new ArrayList<>();

    @OneToMany(mappedBy = "operatorProperty", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Booktalk> recruitScheduledBooktalks = new ArrayList<>();
}
