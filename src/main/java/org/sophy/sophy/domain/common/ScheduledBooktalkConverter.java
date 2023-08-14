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
public class ScheduledBooktalkConverter {

    @Id
    @GeneratedValue
    @Column(name = "scheduled_booktalk_converter_id")
    private Long id;

    @OneToMany(mappedBy = "scheduledBooktalkConverter")
    private static List<ScheduledBooktalk> scheduledBooktalkConverter = new ArrayList<>();

    private static Integer port = 0;

    public static void setPort(Integer port) {
        ScheduledBooktalkConverter.port = port;
    }

    public static Integer getPort() {
        return port;
    }

    public static List<Booktalk> getScheduledBooktalk() {
        return scheduledBooktalkConverter.get(port).getScheduledBooktalks();
    }
}
