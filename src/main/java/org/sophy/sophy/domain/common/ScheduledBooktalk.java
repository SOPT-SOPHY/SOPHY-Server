package org.sophy.sophy.domain.common;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import org.sophy.sophy.domain.Booktalk;

@Entity
@Getter
@NoArgsConstructor
public class ScheduledBooktalk {

    @Id
    @GeneratedValue
    @Column(name = "scheduled_booktalk_id")
    private Long id;

    @OneToMany(mappedBy = "scheduledBooktalk")
    private List<Booktalk> scheduledBooktalks = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scheduled_booktalk_converter_id")
    private ScheduledBooktalkConverter scheduledBooktalkConverter;
}
