CREATE TABLE master_subservices
(
    master_id     UUID NOT NULL,
    subservice_id UUID NOT NULL,

    CONSTRAINT pk_master_subservice
        PRIMARY KEY (master_id, subservice_id),

    CONSTRAINT fk_master_subservices_master
        FOREIGN KEY (master_id) REFERENCES master_profiles (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_master_subservices_subservice
        FOREIGN KEY (subservice_id) REFERENCES subservices (id)
            ON DELETE CASCADE
);

CREATE INDEX idx_master_subservices_master ON master_subservices(master_id);
CREATE INDEX idx_master_subservices_subservice ON master_subservices(subservice_id);
