insert into user_role (user_id, roles)
    values (1, 'ADMIN'), (2, 'USER'), (6, 'WORKER'), (7, 'WORKER') ,(8, 'USER'), (9, 'USER');


insert into usr (id, active, address, email, username, password, phone, real_name, surname)
    values (1, true, 'г. Москва; ул. Молодогвардейская; д. 34', 'mikhail.okhapkin@yandex.ru', 'MikhailOk', '$2a$12$j87/L2SoBAyEB5pIKOVHwO8eskIMsW99JNCT55xwmQEQ6o6kSDy5K', '+79639902768', 'Михаил', 'Охапкин'),
           (2, true, 'г. Москва; ул. Молодогвардейская; д. 34', 'u.s@gmail.com', 'u', '$2a$12$ipnYDF2Zv2i9cbkfAogl0eRte7Qb/aoWhmODytyZb7Q6AO13g5oc.', '+7(903)683-7020', 'Майа', 'Охапкина'),
           (6, true, '', 'w.o.2004@gmail.com', 'Worker1', '$2a$12$/oOgosFybuh/5STvHMHWSeKjp4.ftl61bkevYb2S3pDADV3bkZsea', '+79072128506', 'Oleg', 'Ivanov'),
           (7, true, '', 'w.n.2004@gmail.com', 'Worker2', '$2a$12$TGtTJUwce4pFotUETn8.0u9zrv4Yh1N0qbI7VP8xVh8SrD.3xeteq', '+79072168201', 'Natalia', 'Petrova'),
           (8, true, NULL, 'u.g@gmail.com', 'u2', '$2a$12$8f2FeAsxFN8WUVXn3RqDe.Nz7b4ggQpbHJbIGtnW37V60pUr420V6', NULL, NULL, NULL),
           (9, true, NULL, 'u.a@gmail.com', 'u3', '$2a$12$RhkajkGXV90AAzzWRVHcU.SUuo22.royZV9xw2Qru8ue0Rfae4ZKO', NULL, NULL, NULL);

insert into product_type (product_id, type) values (1, 'CPU'), (2, 'GPU'), (3, 'MOTHERBOARD'), (4, 'RAM'), (5, 'SSD'),
                                                   (6, 'HDD'), (7, 'FAN'), (10, 'FAN'), (12, 'PSU'), (11, 'PSU'), (13, 'CPU'),
                                                   (17, 'CPU'), (18, 'MOTHERBOARD'), (19, 'GPU');

insert into products (id, description, image_url, name, number, price, order_id) values (2, 'Модель GPU: NVIDIA GeForce RTX 3090;
Объем памяти: 24 ГБ;
Тип памяти: GDDR6X;
Макс. подключаемых мониторов: 5;
HDMI: 2 шт;
DisplayPort: 3 шт;
Поддержка VR: +;', 'ade84abb-2592-4f51-98fe-d7b8b5d3b41bAsus_rtx3090.jpg', 'Asus GeForce RTX 3090 TUF OC', 1000, 60000, 1),
                                                                                     (10, 'Socket: AMD TR4/TRX4, AMD AM4, Intel 1150, Intel 1155/1156, Intel 1151/1151 v2, Intel 1200; Длина трубки 400 мм; Максимальный TDP: 200 Вт; Уровень шума: 21 дБ; Максимальные обороты вентиляторов: 2000 об/мин;', '889e9602-8000-49b8-95bb-f8871ae58dfbNZXT_X53.jpg', 'NZXT Kraken X53', 250, 16000, NULL),
                                                                                     (12, 'Мощность: 500 Вт; Форм-фактор: ATX; КПД: 81 %; Питание MB/CPU: 24+8 (4+4) pin; SATA: 3 шт; MOLEX: 2 шт; PCI-E 6pin: 1 шт; PCI-E 8pin (6+2): 1 шт; Мощность +12V: 408 Вт; Мощность +3.3V +5V: 120 Вт;', 'bfe6f6ed-c02d-46a5-8fb6-c439162356c1FSP_PNR_PRO.jpg', 'FSP PNR PRO', 800, 3500, NULL),
                                                                                     (3, 'Socket: Intel LGA 1700;
Форм-фактор: ATX;
Чипсет: Intel Z690;
DDR4: 4 слота(ов);
Форм-фактор слота для памяти: DIMM;
Режим работы: 2-х канальный;
Максимальная тактовая частота: 5333 МГц;', '67377ac9-9dea-43fa-9d23-d775ea1d2e2fASUS_TUF_Intel.jpg', 'Asus TUF GAMING Z690-PLUS WIFI D4', 500, 17000, 1),
                                                                                     (13, 'Техпроцесс: 7 нм; Кол-во ядер: 8 cores; Кол-во потоков: 16 threads; Тактовая частота: 3.8 ГГц; Частота TurboBoost / TurboCore: 4.7 ГГц; Модель IGP: отсутствует; Тепловыделение (TDP): 105 Вт; Макс. частота DDR4: 3200 МГц;', 'e9382a05-b40c-42c8-b715-482a7452b376Ryzen75800X.png', 'AMD Ryzen 7 Vermeer 5800X BOX', 1000, 25000, NULL),
                                                                                     (17, 'Техпроцесс: 10 нм; Кол-во ядер: 10 cores; Кол-во потоков: 16 threads Тактовая частота высокопроизв. ядер: 3.7 ГГц IGP: UHD Graphics 770; Тепловыделение (TDP): 125 Вт; Макс. объем оперативной памяти: 128 ГБ; Макс. частота DDR4: 3200 МГц;', 'c033a429-a114-4c83-a594-8497d27269a6i512600K.jpg', 'Intel Core i5 Alder Lake i5-12600K BOX', 1500, 12500, NULL),
                                                                                     (18, 'Socket: Intel LGA 1200; Форм-фактор: micro-ATX; Чипсет: Intel B460; DDR4: 4 слота(ов); Форм-фактор слота для памяти: DIMM; Режим работы: 2-х канальный; Максимальная тактовая частота: 2933 МГц;', '9ddab60d-ca15-4e8e-b443-8c83e3371e59ASRock_Intel.jpg', 'ASRock B460M Pro4', 500, 4000, NULL),
                                                                                     (19, 'Модель GPU: AMD Radeon RX 6600; Объем памяти: 8 ГБ; Тип памяти: GDDR6; Макс. подключаемых мониторов: 4; HDMI: 2 шт; DisplayPort: 2 шт; Поддержка VR: +;', '8ef99466-a1b2-4af0-ad70-a11e47023fd0Gygabyte_rx6600.jpg', 'Gigabyte Radeon RX 6600 EAGLE 8G', 500, 65000, NULL),
                                                                                     (1, 'Техпроцесс: 14 нм;
Кол-во ядер: 6 cores;
Кол-во потоков: 12 threads;
Тактовая частота: 2.9 ГГц;
Частота TurboBoost: 4.3 ГГц;
Модель IGP: UHD Graphics 630;
Тепловыделение (TDP): 65 Вт;
Макс. объем оперативной памяти: 128 ГБ;
Макс. частота DDR4: 2666 МГц;', 'ee6b1842-bd73-4121-b116-3f6d307a7712i912900K.jpg', 'Intel Core i9 Alder Lake i9-12900K BOX', 100, 35000, NULL),
                                                                                     (6, 'Объем: 1 ТБ; Интерфейсы подключения: SATA, SATA 2, SATA 3; Частота вращения шпинделя: 7200 об/мин; Объем буфера обмена: 64 МБ; Форм-фактор: 3.5 ";', '9656e17c-9735-4c60-999a-32153070828dWD_Blue.jpg', 'WD Blue WD10EZEX', 2000, 3500, 1),
                                                                                     (7, 'Назначение: процессор; Socket: AMD AM2/AM3/FM1/FM2, AMD AM4, Intel 1151/1151 v2, Intel 1200, Intel 2011/2011 v3, Intel 2066; Максимальный TDP: 200 Вт; Уровень шума: 21 дБ; Максимальные обороты: 1400 об/мин', '5273d6e8-9b18-44fe-a849-5120f6d43c84Be_quite_DarkRock4.jpg', 'Be quiet Dark Rock 4', 700, 5000, 1),
                                                                                     (11, 'Мощность: 500 Вт; Форм-фактор: ATX; КПД: 89 %; Питание MB/CPU: 24+8 (4+4) pin; SATA: 6 шт; MOLEX: 2 шт; PCI-E 8pin (6+2): 2 шт; Мощность +12V: 456 Вт; Мощность +3.3V +5V: 110 Вт;', 'fc4f6871-ed08-42f5-aa04-f90da4e28633Be_quite_Power_9.jpg', 'Be quiet System Power 9', 600, 5000, 1),
                                                                                     (4, 'Объем памяти комплекта: 16 ГБ;
Кол-во планок в комплекте: 2 шт;
Форм-фактор памяти: DIMM;
Тип памяти: DDR4;
Тактовая частота: 3200 МГц;', 'e0e5d3c2-fdc7-4692-a81a-1b145ddda8e3Crucial_2x8.jpg', 'Crucial Ballistix DDR4 2x8Gb', 5000, 8000, 1),
                                                                                     (5, 'Объем: 500 ГБ;
Форм-фактор: M.2;
Интерфейс M.2: PCI-E 3.0 4x;
Тип памяти: 3D TLC NAND;
NVMe: +;
Внешняя скорость записи: 2600 МБ/с;
Внешняя скорость считывания: 3100 МБ/с;
Ударостойкость при работе: 1500 G;
Наработка на отказ: 1.5 млн. ч;', '117d6408-714d-45c1-8551-d64390433b8eSamsung_M2.jpg', 'Samsung 980 NVMe M.2 MZ-V8V500BW', 500, 8000, 1);

ALTER TABLE ONLY orders ADD CONSTRAINT orders_pkey PRIMARY KEY (id);
ALTER TABLE ONLY pdf_file ADD CONSTRAINT pdf_file_pkey PRIMARY KEY (id);
ALTER TABLE ONLY products ADD CONSTRAINT products_pkey PRIMARY KEY (id);
ALTER TABLE ONLY usr ADD CONSTRAINT usr_pkey PRIMARY KEY (id);
ALTER TABLE ONLY order_status_pon ADD CONSTRAINT fk5uyhio03opdugg2e4hkqnc5kn FOREIGN KEY (order_id) REFERENCES orders(id);
ALTER TABLE ONLY orders ADD CONSTRAINT fk7ncuqw9n77odylknbo8aikc9w FOREIGN KEY (user_id) REFERENCES usr(id);
ALTER TABLE ONLY user_role ADD CONSTRAINT fkfpm8swft53ulq2hl11yplpr5 FOREIGN KEY (user_id) REFERENCES usr(id);
ALTER TABLE ONLY product_type ADD CONSTRAINT fkls3uvvo6ebnhsa9c6gn7ybnkm FOREIGN KEY (product_id) REFERENCES products(id);
ALTER TABLE ONLY products ADD CONSTRAINT fkr3aftk48ylvntpui7l04kbcc1 FOREIGN KEY (order_id) REFERENCES orders(id);