INSERT INTO ref_location_rayon (created_at, title,rayon_type, region_id, country_id)
VALUES (now(), 'г. Каракол',2, (select id from ref_location_region where title = 'Иссыккульская область'),
        (select id from ref_location_country where title = 'Кыргызстан')),
       (now(), 'г. Балыкчы',2, (select id from ref_location_region where title = 'Иссыккульская область'),
        (select id from ref_location_country where title = 'Кыргызстан'));