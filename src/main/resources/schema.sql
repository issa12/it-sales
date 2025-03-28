DROP TABLE IF EXISTS PRODUCTS;
DROP TABLE IF EXISTS CATEGORIES;
DROP TABLE IF EXISTS TAXES;

CREATE TABLE TAXES (
  id INT   PRIMARY KEY,
  name VARCHAR(250) NOT NULL,
  rate NUMERIC(5, 2) NOT NULL DEFAULT 0.0
);
CREATE TABLE CATEGORIES (
  id INT  PRIMARY KEY,
  name VARCHAR(250) NOT NULL,
  tax_id INT,
  FOREIGN KEY (tax_id) REFERENCES TAXES(id)
);



CREATE TABLE PRODUCTS (
  id INT   PRIMARY KEY,
  name VARCHAR(250) NOT NULL,
  category_id INT,
  FOREIGN KEY (category_id) REFERENCES CATEGORIES(id)
);
