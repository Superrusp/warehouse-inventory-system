-- FILL GOODS TABLE
INSERT INTO goods(name, description, price) VALUES ('Iphone XS', 'Silver 64Gb', 650);
INSERT INTO goods(name, description, price) VALUES ('Iphone XR', 'Red 128Gb', 550);
INSERT INTO goods(name, description, price) VALUES ('Iphone XS Max', 'Gold 256Gb', 750);
INSERT INTO goods(name, description, price) VALUES ('Iphone XS Max', 'Gold 512Gb', 850);
INSERT INTO goods(name, description, price) VALUES ('Iphone 11 Pro', 'Silver 256Gb', 950);
INSERT INTO goods(name, description, price) VALUES ('Iphone 11 Pro Max', 'Black 512Gb', 1100);

-- FILL WAREHOUSES TABLE
INSERT INTO warehouses(name) VALUES('Warehouse #1');
INSERT INTO warehouses(name) VALUES('Warehouse #2');

-- FILL SHOPS TABLE
INSERT INTO shops(name) VALUES('Shop #1');
INSERT INTO shops(name) VALUES('Shop #2');

-- FILL GOODS_IN_STOCKS TABLE
INSERT INTO goods_in_stocks(goods_id, warehouse_id, available_quantity) VALUES(1, 1, 5);
INSERT INTO goods_in_stocks(goods_id, warehouse_id, available_quantity) VALUES(2, 1, 6);
INSERT INTO goods_in_stocks(goods_id, warehouse_id, available_quantity) VALUES(6, 1, 10);
INSERT INTO goods_in_stocks(goods_id, warehouse_id, available_quantity) VALUES(3, 2, 5);
INSERT INTO goods_in_stocks(goods_id, warehouse_id, available_quantity) VALUES(5, 2, 5);

-- FILL GOODS_IN_SHOPS TABLE
INSERT INTO goods_in_shops(goods_id, shop_id, available_quantity) VALUES(4, 1, 2);
INSERT INTO goods_in_shops(goods_id, shop_id, available_quantity) VALUES(6, 1, 4);
INSERT INTO goods_in_shops(goods_id, shop_id, available_quantity) VALUES(1, 1, 5);
INSERT INTO goods_in_shops(goods_id, shop_id, available_quantity) VALUES(3, 2, 1);
INSERT INTO goods_in_shops(goods_id, shop_id, available_quantity) VALUES(5, 2, 3);

-- FILL DELIVERY_REQUESTS TABLE
INSERT INTO delivery_requests(warehouse_id, request_date, arrival_date)
VALUES(2, TO_DATE('2021/03/27', 'yyyy/mm/dd'),  TO_DATE('2021/04/01', 'yyyy/mm/dd'));

INSERT INTO delivery_requests(warehouse_id, request_date, arrival_date)
VALUES(1, TO_DATE('2021/03/27', 'yyyy/mm/dd'),  TO_DATE('2021/03/30', 'yyyy/mm/dd'));

INSERT INTO delivery_requests(shop_id, request_date, arrival_date)
VALUES(1, TO_DATE('2021/03/28', 'yyyy/mm/dd'),  TO_DATE('2021/03/29', 'yyyy/mm/dd'));

INSERT INTO delivery_requests(shop_id, request_date, arrival_date)
VALUES(2, TO_DATE('2021/03/30', 'yyyy/mm/dd'),  TO_DATE('2021/03/31', 'yyyy/mm/dd'));

-- FILL DELIVERY_REQUEST_GOODS TABLE
INSERT INTO delivery_request_goods(delivery_request_id, goods_id) VALUES(1, 1);
INSERT INTO delivery_request_goods(delivery_request_id, goods_id) VALUES(1, 2);
INSERT INTO delivery_request_goods(delivery_request_id, goods_id) VALUES(1, 3);
INSERT INTO delivery_request_goods(delivery_request_id, goods_id) VALUES(1, 4);
INSERT INTO delivery_request_goods(delivery_request_id, goods_id) VALUES(2, 3);
INSERT INTO delivery_request_goods(delivery_request_id, goods_id) VALUES(2, 2);
INSERT INTO delivery_request_goods(delivery_request_id, goods_id) VALUES(3, 1);
INSERT INTO delivery_request_goods(delivery_request_id, goods_id) VALUES(3, 4);
INSERT INTO delivery_request_goods(delivery_request_id, goods_id) VALUES(3, 5);
INSERT INTO delivery_request_goods(delivery_request_id, goods_id) VALUES(4, 2);
INSERT INTO delivery_request_goods(delivery_request_id, goods_id) VALUES(4, 4);
INSERT INTO delivery_request_goods(delivery_request_id, goods_id) VALUES(4, 5);

-- FILL DELIVERY_ITEMS TABLE
INSERT INTO delivery_items(goods_id, delivery_request_id, delivery_status) VALUES(1, 1,'Shipped');
INSERT INTO delivery_items(goods_id, delivery_request_id, delivery_status) VALUES(2, 1,'Shipped');
INSERT INTO delivery_items(goods_id, delivery_request_id, delivery_status) VALUES(3, 1,'Shipped');
INSERT INTO delivery_items(goods_id, delivery_request_id, delivery_status) VALUES(4, 1,'Shipped');
INSERT INTO delivery_items(goods_id, delivery_request_id, delivery_status) VALUES(3, 2,'Shipped');
INSERT INTO delivery_items(goods_id, delivery_request_id, delivery_status) VALUES(2, 2,'Shipped');
INSERT INTO delivery_items(goods_id, delivery_request_id, delivery_status) VALUES(1, 3,'Processing in progress');
INSERT INTO delivery_items(goods_id, delivery_request_id, delivery_status) VALUES(4, 3,'Processing in progress');
INSERT INTO delivery_items(goods_id, delivery_request_id, delivery_status) VALUES(5, 3,'Processing in progress');
INSERT INTO delivery_items(goods_id, delivery_request_id, delivery_status) VALUES(2, 4,'Processing in progress');
INSERT INTO delivery_items(goods_id, delivery_request_id, delivery_status) VALUES(4, 4,'Processing in progress');
INSERT INTO delivery_items(goods_id, delivery_request_id, delivery_status) VALUES(5, 4,'Processing in progress');