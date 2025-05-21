package ru.master.service.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ru.master.service.auth.model.TimestampedEntity;
import ru.master.service.constant.MasterDocumentType;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "master_documents")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MasterDocument extends TimestampedEntity {

    @Enumerated(EnumType.STRING)
    MasterDocumentType type;

    String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "master_id")
    MasterProfile master;
}
