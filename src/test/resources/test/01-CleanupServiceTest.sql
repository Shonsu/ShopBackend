/*insert into category(id, name, slug)
values (1, 'Inne', 'inne');
*/

insert into product (id, name, category_id, description, price, currency)
values (1, 'Samolot', 1, 'dwupłat', 12.55, 'PLN'),
       (2, 'Samochód', 1, 'wyścigówka', 25.55, 'PLN'),
       (3, 'Tramwaj', 1, 'wąskotorowy', 40.55, 'PLN');

/*
insert into cart (id, created)
values (1, '2022-12-21 20:20:48'),
       (2, '2022-12-22 20:20:48'),
       (3, '2022-12-23 20:20:48'),
       (4, '2022-12-27 20:20:48');

insert into cart_item (product_id, quantity, cart_id)
values (1, 2, 1),
       (2, 2, 1),
       (1, 2, 2),
       (2, 2, 2),
       (1, 2, 3),
       (2, 2, 3),
       (3, 2, 3),
       (2, 2, 4),
       (2, 2, 4);
*/