CREATE TABLE schedules (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(100) NOT NULL,
    date DATE NOT NULL
);

CREATE TABLE schedule_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    schedule_id BIGINT NOT NULL,
    member_id BIGINT NOT NULL,
    ministry_role VARCHAR(100),

    CONSTRAINT fk_schedule FOREIGN KEY (schedule_id)
        REFERENCES schedules (id) ON DELETE CASCADE,

    CONSTRAINT fk_member FOREIGN KEY (member_id)
        REFERENCES members (id)
);