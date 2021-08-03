CREATE TABLE obituaries (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(200) NOT NULL,
  name_of_mother VARCHAR(200) NOT NULL,
  date_of_birth DATE NOT NULL,
  date_of_rip DATE NOT NULL,
  grave_id BIGINT,
  CONSTRAINT fk_grave_o_id FOREIGN KEY (grave_id) REFERENCES graves (id)
);