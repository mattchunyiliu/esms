INSERT INTO ref_location_rayon (created_at, title, country_id, region_id)
VALUES (now(), 'г. Джалал-Абад', (SELECT id
                                     FROM ref_location_country
                                     WHERE title = 'Кыргызстан'), (SELECT id
                                                                   FROM ref_location_region
                                                                   WHERE title = 'Джалал-Абадская область')),
       (now(), 'г. Кара-Куль', (SELECT id
                                      FROM ref_location_country
                                      WHERE title = 'Кыргызстан'), (SELECT id
                                                                    FROM ref_location_region
                                                                    WHERE title = 'Джалал-Абадская область')),
       (now(), 'г. Майлуу-Суу', (SELECT id
                                FROM ref_location_country
                                WHERE title = 'Кыргызстан'), (SELECT id
                                                              FROM ref_location_region
                                                              WHERE title = 'Джалал-Абадская область')),
       (now(), 'г. Таш-Кумыр', (SELECT id
                                 FROM ref_location_country
                                 WHERE title = 'Кыргызстан'), (SELECT id
                                                               FROM ref_location_region
                                                               WHERE title = 'Джалал-Абадская область')),
       (now(), 'г. Баткен', (SELECT id
                                      FROM ref_location_country
                                      WHERE title = 'Кыргызстан'), (SELECT id
                                                                    FROM ref_location_region
                                                                    WHERE title = 'Баткенская область')),
       (now(), 'г. Кызыл-Кия', (SELECT id
                             FROM ref_location_country
                             WHERE title = 'Кыргызстан'), (SELECT id
                                                           FROM ref_location_region
                                                           WHERE title = 'Баткенская область')),
       (now(), 'г. Сулюкта', (SELECT id
                             FROM ref_location_country
                             WHERE title = 'Кыргызстан'), (SELECT id
                                                           FROM ref_location_region
                                                           WHERE title = 'Баткенская область')),
       (now(), 'г. Нарын', (SELECT id
                              FROM ref_location_country
                              WHERE title = 'Кыргызстан'), (SELECT id
                                                            FROM ref_location_region
                                                            WHERE title = 'Нарынская область')),
       (now(), 'г. Талас', (SELECT id
                            FROM ref_location_country
                            WHERE title = 'Кыргызстан'), (SELECT id
                                                          FROM ref_location_region
                                                          WHERE title = 'Таласская область')),
       (now(), 'г. Токмак', (SELECT id
                                   FROM ref_location_country
                                   WHERE title = 'Кыргызстан'), (SELECT id
                                                                 FROM ref_location_region
                                                                 WHERE title = 'Чуйская область'));
/*** Town ***/
CREATE TABLE IF NOT EXISTS ref_location_town
(
    id         BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    rayon_id BIGINT REFERENCES ref_location_rayon (id) ON UPDATE CASCADE ON DELETE CASCADE ,
    title      VARCHAR(255),
    UNIQUE (title)
);