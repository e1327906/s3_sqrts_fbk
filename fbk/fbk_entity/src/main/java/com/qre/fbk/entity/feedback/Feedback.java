package com.qre.fbk.entity.feedback;

import com.qre.fbk.entity.base.AbstractPersistableEntity;
import com.qre.fbk.entity.base.DbFieldName;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = DbFieldName.FEEDBACK_TABLE)
public class Feedback extends AbstractPersistableEntity<UUID> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = DbFieldName.FEEDBACK_ID)
    private UUID feedback_id;

    @Column(name = DbFieldName.NAME)
    private String name;

    @Column(name = DbFieldName.EMAIL)
    private String email;

    @Column(name = DbFieldName.CATEGORY)
    private String category;

    @Column(name = DbFieldName.MESSAGE)
    private String message;

    @Override
    public UUID getId() {
        return feedback_id;
    }

}
