package br.com.compass.pb.asyncapiconsumer.entity;
import br.com.compass.pb.asyncapiconsumer.util.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class History {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "post")
    private Post post;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    private Status status;
    public History(Status status, LocalDateTime date){
        this.date = date;
        this.status = status;
    }
}

