INSERT INTO ref_location_country (created_at, title)
SELECT now(),
       'Кыргызстан' WHERE NOT EXISTS(
    SELECT * FROM ref_location_country
    WHERE title = 'Кыргызстан');

INSERT INTO ref_location_region (created_at, title, country_id)
VALUES (now(), 'г.Бишкек', (SELECT id
                            FROM ref_location_country
                            WHERE title = 'Кыргызстан')),
       (now(), 'г.Ош', (SELECT id
                        FROM ref_location_country
                        WHERE title = 'Кыргызстан')),
       (now(), 'Чуйская область', (SELECT id
                                   FROM ref_location_country
                                   WHERE title = 'Кыргызстан')),
       (now(), 'Ошская область', (SELECT id
                                  FROM ref_location_country
                                  WHERE title = 'Кыргызстан')),
       (now(), 'Иссыккульская область', (SELECT id
                                         FROM ref_location_country
                                         WHERE title = 'Кыргызстан')),
       (now(), 'Таласская область', (SELECT id
                                     FROM ref_location_country
                                     WHERE title = 'Кыргызстан')),
       (now(), 'Нарынская область', (SELECT id
                                     FROM ref_location_country
                                     WHERE title = 'Кыргызстан')),
       (now(), 'Джалал-Абадская область', (SELECT id
                                           FROM ref_location_country
                                           WHERE title = 'Кыргызстан')),
       (now(), 'Баткенская область', (SELECT id
                                      FROM ref_location_country
                                      WHERE title = 'Кыргызстан'));

INSERT INTO ref_location_rayon
    (created_at, title, region_id, country_id)
VALUES (now(), 'Сулайман-Тоо', (select id from ref_location_region where title = 'г.Ош'),
        (select id from ref_location_country where title = 'Кыргызстан')),
       (now(), 'Ак-Буура', (select id from ref_location_region where title = 'г.Ош'),
        (select id from ref_location_country where title = 'Кыргызстан')),
       (now(), 'Аламудунский район', (select id from ref_location_region where title = 'Чуйская область'),
        (select id from ref_location_country where title = 'Кыргызстан')),
       (now(), 'Жайылский район', (select id from ref_location_region where title = 'Чуйская область'),
        (select id from ref_location_country where title = 'Кыргызстан')),
       (now(), 'Кеминский район', (select id from ref_location_region where title = 'Чуйская область'),
        (select id from ref_location_country where title = 'Кыргызстан')),
       (now(), 'Московский район', (select id from ref_location_region where title = 'Чуйская область'),
        (select id from ref_location_country where title = 'Кыргызстан')),
       (now(), 'Панфиловский район', (select id from ref_location_region where title = 'Чуйская область'),
        (select id from ref_location_country where title = 'Кыргызстан')),
       (now(), 'Сокулукский район', (select id from ref_location_region where title = 'Чуйская область'),
        (select id from ref_location_country where title = 'Кыргызстан')),
       (now(), 'Чуйский район', (select id from ref_location_region where title = 'Чуйская область'),
        (select id from ref_location_country where title = 'Кыргызстан')),
       (now(), 'Ысык-Атинский район', (select id from ref_location_region where title = 'Чуйская область'),
        (select id from ref_location_country where title = 'Кыргызстан')),
       (now(), 'Алайский район', (select id from ref_location_region where title = 'Ошская область'),
        (select id from ref_location_country where title = 'Кыргызстан')),
       (now(), 'Араванский район', (select id from ref_location_region where title = 'Ошская область'),
        (select id from ref_location_country where title = 'Кыргызстан')),
       (now(), 'Кара-Кулжинский район', (select id from ref_location_region where title = 'Ошская область'),
        (select id from ref_location_country where title = 'Кыргызстан')),
       (now(), 'Кара-Сууйский район', (select id from ref_location_region where title = 'Ошская область'),
        (select id from ref_location_country where title = 'Кыргызстан')),
       (now(), 'Ноокатский район', (select id from ref_location_region where title = 'Ошская область'),
        (select id from ref_location_country where title = 'Кыргызстан')),
       (now(), 'Узгенский район', (select id from ref_location_region where title = 'Ошская область'),
        (select id from ref_location_country where title = 'Кыргызстан')),
       (now(), 'Чон-Алайский район', (select id from ref_location_region where title = 'Ошская область'),
        (select id from ref_location_country where title = 'Кыргызстан')),
       (now(), 'Ак-Суйский район', (select id from ref_location_region where title = 'Иссыккульская область'),
        (select id from ref_location_country where title = 'Кыргызстан')),
       (now(), 'Жети-Огузский район', (select id from ref_location_region where title = 'Иссыккульская область'),
        (select id from ref_location_country where title = 'Кыргызстан')),
       (now(), 'Иссык-Кульский район', (select id from ref_location_region where title = 'Иссыккульская область'),
        (select id from ref_location_country where title = 'Кыргызстан')),
       (now(), 'Тонский район', (select id from ref_location_region where title = 'Иссыккульская область'),
        (select id from ref_location_country where title = 'Кыргызстан')),
       (now(), 'Тюпский район', (select id from ref_location_region where title = 'Иссыккульская область'),
        (select id from ref_location_country where title = 'Кыргызстан')),
       (now(), 'Бакай-Атинский район', (select id from ref_location_region where title = 'Таласская область'),
        (select id from ref_location_country where title = 'Кыргызстан')),
       (now(), 'Кара-Бууринский район', (select id from ref_location_region where title = 'Таласская область'),
        (select id from ref_location_country where title = 'Кыргызстан')),
       (now(), 'Манасский район', (select id from ref_location_region where title = 'Таласская область'),
        (select id from ref_location_country where title = 'Кыргызстан')),
       (now(), 'Таласский район', (select id from ref_location_region where title = 'Таласская область'),
        (select id from ref_location_country where title = 'Кыргызстан')),
       (now(), 'Ак-Талинский район', (select id from ref_location_region where title = 'Нарынская область'),
        (select id from ref_location_country where title = 'Кыргызстан')),
       (now(), 'Ат-Башинский район', (select id from ref_location_region where title = 'Нарынская область'),
        (select id from ref_location_country where title = 'Кыргызстан')),
       (now(), 'Жумгальский район', (select id from ref_location_region where title = 'Нарынская область'),
        (select id from ref_location_country where title = 'Кыргызстан')),
       (now(), 'Кочкорский район', (select id from ref_location_region where title = 'Нарынская область'),
        (select id from ref_location_country where title = 'Кыргызстан')),
       (now(), 'Нарынский район', (select id from ref_location_region where title = 'Нарынская область'),
        (select id from ref_location_country where title = 'Кыргызстан')),
       (now(), 'Аксыйскийский район', (select id from ref_location_region where title = 'Джалал-Абадская область'),
        (select id from ref_location_country where title = 'Кыргызстан')),
       (now(), 'Ала-Букинский район', (select id from ref_location_region where title = 'Джалал-Абадская область'),
        (select id from ref_location_country where title = 'Кыргызстан')),
       (now(), 'Базар-Коргонский район', (select id from ref_location_region where title = 'Джалал-Абадская область'),
        (select id from ref_location_country where title = 'Кыргызстан')),
       (now(), 'Ноокенский район', (select id from ref_location_region where title = 'Джалал-Абадская область'),
        (select id from ref_location_country where title = 'Кыргызстан')),
       (now(), 'Сузакский район', (select id from ref_location_region where title = 'Джалал-Абадская область'),
        (select id from ref_location_country where title = 'Кыргызстан')),
       (now(), 'Тогуз-Тороуский район', (select id from ref_location_region where title = 'Джалал-Абадская область'),
        (select id from ref_location_country where title = 'Кыргызстан')),
       (now(), 'Токтогульский район', (select id from ref_location_region where title = 'Джалал-Абадская область'),
        (select id from ref_location_country where title = 'Кыргызстан')),
       (now(), 'Чаткальский район', (select id from ref_location_region where title = 'Джалал-Абадская область'),
        (select id from ref_location_country where title = 'Кыргызстан')),
       (now(), 'Майлысууйский район', (select id from ref_location_region where title = 'Джалал-Абадская область'),
        (select id from ref_location_country where title = 'Кыргызстан')),
       (now(), 'Таш-Комурский район', (select id from ref_location_region where title = 'Джалал-Абадская область'),
        (select id from ref_location_country where title = 'Кыргызстан')),
       (now(), 'Кара-Кульский район', (select id from ref_location_region where title = 'Джалал-Абадская область'),
        (select id from ref_location_country where title = 'Кыргызстан')),
       (now(), 'Баткенский район', (select id from ref_location_region where title = 'Баткенская область'),
        (select id from ref_location_country where title = 'Кыргызстан')),
       (now(), 'Кадамжайский район', (select id from ref_location_region where title = 'Баткенская область'),
        (select id from ref_location_country where title = 'Кыргызстан')),
       (now(), 'Лейлекский район', (select id from ref_location_region where title = 'Баткенская область'),
        (select id from ref_location_country where title = 'Кыргызстан'));

INSERT INTO ref_location_rayon (created_at, title, country_id, region_id)
VALUES (now(), 'Октябрьский район', (SELECT id
                                     FROM ref_location_country
                                     WHERE title = 'Кыргызстан'), (SELECT id
                                                                   FROM ref_location_region
                                                                   WHERE title = 'г.Бишкек')),
       (now(), 'Первомайский район', (SELECT id
                                      FROM ref_location_country
                                      WHERE title = 'Кыргызстан'), (SELECT id
                                                                    FROM ref_location_region
                                                                    WHERE title = 'г.Бишкек')),
       (now(), 'Свердловский район', (SELECT id
                                      FROM ref_location_country
                                      WHERE title = 'Кыргызстан'), (SELECT id
                                                                    FROM ref_location_region
                                                                    WHERE title = 'г.Бишкек')),
       (now(), 'Ленинский район', (SELECT id
                                   FROM ref_location_country
                                   WHERE title = 'Кыргызстан'), (SELECT id
                                                                 FROM ref_location_region
                                                                 WHERE title = 'г.Бишкек'));