CREATE TABLE members (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    church_id BIGINT,
    name VARCHAR(255),
    phone VARCHAR(50),
    email VARCHAR(255),
    role VARCHAR(100),
    birthday DATE,
    FOREIGN KEY (church_id) REFERENCES churches(id) ON DELETE CASCADE
);

CREATE TABLE events (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    church_id BIGINT,
    title VARCHAR(255),
    description TEXT,
    start_at DATETIME,
    end_at DATETIME,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (church_id) REFERENCES churches(id) ON DELETE CASCADE
);


CREATE TABLE schedules (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    church_id BIGINT,
    name VARCHAR(255),
    date DATE,
    details TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (church_id) REFERENCES churches(id) ON DELETE CASCADE
);

