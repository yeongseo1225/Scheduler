CREATE TABLE writers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL,
    created_at DATETIME NOT NULL,
    modified_at DATETIME NOT NULL
);


CREATE TABLE schedules (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    password VARCHAR(100) NOT NULL,
    writer_id BIGINT NOT NULL,
    created_at DATETIME NOT NULL,
    modified_at DATETIME NOT NULL,
    FOREIGN KEY (writer_id) REFERENCES writers(id)
);


