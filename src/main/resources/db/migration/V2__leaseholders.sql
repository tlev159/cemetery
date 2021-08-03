CREATE TABLE leaseholders (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              leaseholder_name VARCHAR(200) NOT NULL,
                              address VARCHAR(255) NOT NULL,
                              phone VARCHAR(100) NOT NULL,
                              leased_at DATE NOT NULL,
                              type_of_grave VARCHAR(50) NOT NULL,
                              grave_id BIGINT,
                              CONSTRAINT `fk_grave_lh_id` FOREIGN KEY (grave_id) REFERENCES graves (id)
);