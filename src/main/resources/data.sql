
INSERT INTO TAXES (id, name, rate) VALUES (2, 'imported', 5);
INSERT INTO TAXES(id, name, rate) VALUES (1, 'general', 10);


INSERT INTO CATEGORIES (id, name, tax_id) VALUES (1, 'General', 1);
INSERT INTO CATEGORIES (id, name, tax_id) VALUES (2, 'book', null);
INSERT INTO CATEGORIES (id, name, tax_id) VALUES (3, 'medical', null);
INSERT INTO CATEGORIES (id, name, tax_id) VALUES (4, 'food', null);


INSERT INTO PRODUCTS (id, name, category_id) VALUES (1, 'book',  2);
INSERT INTO PRODUCTS (id, name, category_id) VALUES (2, 'music CD',  1);
INSERT INTO PRODUCTS (id, name, category_id) VALUES (3, 'chocolate bar',  4);
INSERT INTO PRODUCTS (id, name, category_id) VALUES (4, 'box of chocolates',  4);
INSERT INTO PRODUCTS (id, name, category_id) VALUES (5, 'bottle of perfume',  1);
INSERT INTO PRODUCTS (id, name, category_id) VALUES (6, 'packet of headache pills',  3);
INSERT INTO PRODUCTS (id, name, category_id) VALUES (7, 'phone',  4);

