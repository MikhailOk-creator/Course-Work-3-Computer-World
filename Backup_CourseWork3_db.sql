--
-- PostgreSQL database dump
--

-- Dumped from database version 14.2
-- Dumped by pg_dump version 14.2

-- Started on 2022-08-01 19:07:49

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 212 (class 1259 OID 46088)
-- Name: order_status_pon; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.order_status_pon (
    order_id bigint NOT NULL,
    status character varying(255)
);


ALTER TABLE public.order_status_pon OWNER TO postgres;

--
-- TOC entry 213 (class 1259 OID 46091)
-- Name: orders; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.orders (
    id bigint NOT NULL,
    phone character varying(255),
    user_address character varying(255),
    user_email character varying(255),
    user_name character varying(255),
    user_surname character varying(255),
    user_id bigint
);


ALTER TABLE public.orders OWNER TO postgres;

--
-- TOC entry 210 (class 1259 OID 46086)
-- Name: orders_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.orders_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.orders_sequence OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 46099)
-- Name: pdf_file; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pdf_file (
    id bigint NOT NULL,
    file_name character varying(255),
    pdf bytea,
    type character varying(255)
);


ALTER TABLE public.pdf_file OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 46098)
-- Name: pdf_file_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.pdf_file_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.pdf_file_id_seq OWNER TO postgres;

--
-- TOC entry 3363 (class 0 OID 0)
-- Dependencies: 214
-- Name: pdf_file_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.pdf_file_id_seq OWNED BY public.pdf_file.id;


--
-- TOC entry 216 (class 1259 OID 46107)
-- Name: product_type; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.product_type (
    product_id bigint NOT NULL,
    type character varying(255)
);


ALTER TABLE public.product_type OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 46110)
-- Name: products; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.products (
    id bigint NOT NULL,
    description character varying(255),
    image_url character varying(255),
    name character varying(255),
    number bigint,
    price bigint,
    order_id bigint
);


ALTER TABLE public.products OWNER TO postgres;

--
-- TOC entry 211 (class 1259 OID 46087)
-- Name: products_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.products_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.products_sequence OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 46117)
-- Name: user_role; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_role (
    user_id bigint NOT NULL,
    roles character varying(255)
);


ALTER TABLE public.user_role OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 34918)
-- Name: users_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_sequence OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 46121)
-- Name: usr; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.usr (
    id bigint NOT NULL,
    active boolean NOT NULL,
    address character varying(255),
    email character varying(255),
    name character varying(255),
    password character varying(255),
    phone character varying(255),
    real_name character varying(255),
    surname character varying(255)
);


ALTER TABLE public.usr OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 46120)
-- Name: usr_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.usr_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.usr_id_seq OWNER TO postgres;

--
-- TOC entry 3364 (class 0 OID 0)
-- Dependencies: 219
-- Name: usr_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.usr_id_seq OWNED BY public.usr.id;


--
-- TOC entry 3192 (class 2604 OID 46102)
-- Name: pdf_file id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pdf_file ALTER COLUMN id SET DEFAULT nextval('public.pdf_file_id_seq'::regclass);


--
-- TOC entry 3193 (class 2604 OID 46124)
-- Name: usr id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usr ALTER COLUMN id SET DEFAULT nextval('public.usr_id_seq'::regclass);


--
-- TOC entry 3349 (class 0 OID 46088)
-- Dependencies: 212
-- Data for Name: order_status_pon; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.order_status_pon VALUES (1, 'DELIVERED');


--
-- TOC entry 3350 (class 0 OID 46091)
-- Dependencies: 213
-- Data for Name: orders; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.orders VALUES (1, '+79639902768', 'г. Москва; ул. Молодогвардейская; д. 34', 'mikhail.okhapkin@yandex.ru', 'Михаил', 'Охапкин', 1);


--
-- TOC entry 3352 (class 0 OID 46099)
-- Dependencies: 215
-- Data for Name: pdf_file; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3353 (class 0 OID 46107)
-- Dependencies: 216
-- Data for Name: product_type; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.product_type VALUES (1, 'CPU');
INSERT INTO public.product_type VALUES (2, 'GPU');
INSERT INTO public.product_type VALUES (3, 'MOTHERBOARD');
INSERT INTO public.product_type VALUES (5, 'SSD');
INSERT INTO public.product_type VALUES (4, 'RAM');
INSERT INTO public.product_type VALUES (6, 'HDD');
INSERT INTO public.product_type VALUES (7, 'FAN');
INSERT INTO public.product_type VALUES (10, 'FAN');
INSERT INTO public.product_type VALUES (12, 'PSU');
INSERT INTO public.product_type VALUES (11, 'PSU');
INSERT INTO public.product_type VALUES (13, 'CPU');
INSERT INTO public.product_type VALUES (17, 'CPU');
INSERT INTO public.product_type VALUES (18, 'MOTHERBOARD');
INSERT INTO public.product_type VALUES (19, 'GPU');


--
-- TOC entry 3354 (class 0 OID 46110)
-- Dependencies: 217
-- Data for Name: products; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.products VALUES (2, 'Модель GPU: NVIDIA GeForce RTX 3090;
Объем памяти: 24 ГБ;
Тип памяти: GDDR6X;
Макс. подключаемых мониторов: 5;
HDMI: 2 шт;
DisplayPort: 3 шт;
Поддержка VR: +;', 'ade84abb-2592-4f51-98fe-d7b8b5d3b41bAsus_rtx3090.jpg', 'Asus GeForce RTX 3090 TUF OC', 1000, 60000, 1);
INSERT INTO public.products VALUES (10, 'Socket: AMD TR4/TRX4, AMD AM4, Intel 1150, Intel 1155/1156, Intel 1151/1151 v2, Intel 1200; Длина трубки 400 мм; Максимальный TDP: 200 Вт; Уровень шума: 21 дБ; Максимальные обороты вентиляторов: 2000 об/мин;', '889e9602-8000-49b8-95bb-f8871ae58dfbNZXT_X53.jpg', 'NZXT Kraken X53', 250, 16000, NULL);
INSERT INTO public.products VALUES (12, 'Мощность: 500 Вт; Форм-фактор: ATX; КПД: 81 %; Питание MB/CPU: 24+8 (4+4) pin; SATA: 3 шт; MOLEX: 2 шт; PCI-E 6pin: 1 шт; PCI-E 8pin (6+2): 1 шт; Мощность +12V: 408 Вт; Мощность +3.3V +5V: 120 Вт;', 'bfe6f6ed-c02d-46a5-8fb6-c439162356c1FSP_PNR_PRO.jpg', 'FSP PNR PRO', 800, 3500, NULL);
INSERT INTO public.products VALUES (3, 'Socket: Intel LGA 1700;
Форм-фактор: ATX;
Чипсет: Intel Z690;
DDR4: 4 слота(ов);
Форм-фактор слота для памяти: DIMM;
Режим работы: 2-х канальный;
Максимальная тактовая частота: 5333 МГц;', '67377ac9-9dea-43fa-9d23-d775ea1d2e2fASUS_TUF_Intel.jpg', 'Asus TUF GAMING Z690-PLUS WIFI D4', 500, 17000, 1);
INSERT INTO public.products VALUES (13, 'Техпроцесс: 7 нм; Кол-во ядер: 8 cores; Кол-во потоков: 16 threads; Тактовая частота: 3.8 ГГц; Частота TurboBoost / TurboCore: 4.7 ГГц; Модель IGP: отсутствует; Тепловыделение (TDP): 105 Вт; Макс. частота DDR4: 3200 МГц;', 'e9382a05-b40c-42c8-b715-482a7452b376Ryzen75800X.png', 'AMD Ryzen 7 Vermeer 5800X BOX', 1000, 25000, NULL);
INSERT INTO public.products VALUES (17, 'Техпроцесс: 10 нм; Кол-во ядер: 10 cores; Кол-во потоков: 16 threads Тактовая частота высокопроизв. ядер: 3.7 ГГц IGP: UHD Graphics 770; Тепловыделение (TDP): 125 Вт; Макс. объем оперативной памяти: 128 ГБ; Макс. частота DDR4: 3200 МГц;', 'c033a429-a114-4c83-a594-8497d27269a6i512600K.jpg', 'Intel Core i5 Alder Lake i5-12600K BOX', 1500, 12500, NULL);
INSERT INTO public.products VALUES (18, 'Socket: Intel LGA 1200; Форм-фактор: micro-ATX; Чипсет: Intel B460; DDR4: 4 слота(ов); Форм-фактор слота для памяти: DIMM; Режим работы: 2-х канальный; Максимальная тактовая частота: 2933 МГц;', '9ddab60d-ca15-4e8e-b443-8c83e3371e59ASRock_Intel.jpg', 'ASRock B460M Pro4', 500, 4000, NULL);
INSERT INTO public.products VALUES (19, 'Модель GPU: AMD Radeon RX 6600; Объем памяти: 8 ГБ; Тип памяти: GDDR6; Макс. подключаемых мониторов: 4; HDMI: 2 шт; DisplayPort: 2 шт; Поддержка VR: +;', '8ef99466-a1b2-4af0-ad70-a11e47023fd0Gygabyte_rx6600.jpg', 'Gigabyte Radeon RX 6600 EAGLE 8G', 500, 65000, NULL);
INSERT INTO public.products VALUES (1, 'Техпроцесс: 14 нм;
Кол-во ядер: 6 cores;
Кол-во потоков: 12 threads;
Тактовая частота: 2.9 ГГц;
Частота TurboBoost: 4.3 ГГц;
Модель IGP: UHD Graphics 630;
Тепловыделение (TDP): 65 Вт;
Макс. объем оперативной памяти: 128 ГБ;
Макс. частота DDR4: 2666 МГц;', 'ee6b1842-bd73-4121-b116-3f6d307a7712i912900K.jpg', 'Intel Core i9 Alder Lake i9-12900K BOX', 100, 35000, NULL);
INSERT INTO public.products VALUES (6, 'Объем: 1 ТБ; Интерфейсы подключения: SATA, SATA 2, SATA 3; Частота вращения шпинделя: 7200 об/мин; Объем буфера обмена: 64 МБ; Форм-фактор: 3.5 ";', '9656e17c-9735-4c60-999a-32153070828dWD_Blue.jpg', 'WD Blue WD10EZEX', 2000, 3500, 1);
INSERT INTO public.products VALUES (7, 'Назначение: процессор; Socket: AMD AM2/AM3/FM1/FM2, AMD AM4, Intel 1151/1151 v2, Intel 1200, Intel 2011/2011 v3, Intel 2066; Максимальный TDP: 200 Вт; Уровень шума: 21 дБ; Максимальные обороты: 1400 об/мин', '5273d6e8-9b18-44fe-a849-5120f6d43c84Be_quite_DarkRock4.jpg', 'Be quiet Dark Rock 4', 700, 5000, 1);
INSERT INTO public.products VALUES (11, 'Мощность: 500 Вт; Форм-фактор: ATX; КПД: 89 %; Питание MB/CPU: 24+8 (4+4) pin; SATA: 6 шт; MOLEX: 2 шт; PCI-E 8pin (6+2): 2 шт; Мощность +12V: 456 Вт; Мощность +3.3V +5V: 110 Вт;', 'fc4f6871-ed08-42f5-aa04-f90da4e28633Be_quite_Power_9.jpg', 'Be quiet System Power 9', 600, 5000, 1);
INSERT INTO public.products VALUES (4, 'Объем памяти комплекта: 16 ГБ;
Кол-во планок в комплекте: 2 шт;
Форм-фактор памяти: DIMM;
Тип памяти: DDR4;
Тактовая частота: 3200 МГц;', 'e0e5d3c2-fdc7-4692-a81a-1b145ddda8e3Crucial_2x8.jpg', 'Crucial Ballistix DDR4 2x8Gb', 5000, 8000, 1);
INSERT INTO public.products VALUES (5, 'Объем: 500 ГБ; 
Форм-фактор: M.2; 
Интерфейс M.2: PCI-E 3.0 4x; 
Тип памяти: 3D TLC NAND; 
NVMe: +; 
Внешняя скорость записи: 2600 МБ/с; 
Внешняя скорость считывания: 3100 МБ/с; 
Ударостойкость при работе: 1500 G; 
Наработка на отказ: 1.5 млн. ч;', '117d6408-714d-45c1-8551-d64390433b8eSamsung_M2.jpg', 'Samsung 980 NVMe M.2 MZ-V8V500BW', 500, 8000, 1);


--
-- TOC entry 3355 (class 0 OID 46117)
-- Dependencies: 218
-- Data for Name: user_role; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.user_role VALUES (1, 'ADMIN');
INSERT INTO public.user_role VALUES (2, 'USER');
INSERT INTO public.user_role VALUES (6, 'WORKER');
INSERT INTO public.user_role VALUES (7, 'WORKER');
INSERT INTO public.user_role VALUES (8, 'USER');
INSERT INTO public.user_role VALUES (9, 'USER');


--
-- TOC entry 3357 (class 0 OID 46121)
-- Dependencies: 220
-- Data for Name: usr; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.usr VALUES (6, true, '', 'w.o.2004@gmail.com', 'Worker1', '$2a$12$/oOgosFybuh/5STvHMHWSeKjp4.ftl61bkevYb2S3pDADV3bkZsea', '+79072128506', 'Oleg', 'Ivanov');
INSERT INTO public.usr VALUES (7, true, '', 'w.n.2004@gmail.com', 'Worker2', '$2a$12$TGtTJUwce4pFotUETn8.0u9zrv4Yh1N0qbI7VP8xVh8SrD.3xeteq', '+79072168201', 'Natalia', 'Petrova');
INSERT INTO public.usr VALUES (8, true, NULL, 'u.g@gmail.com', 'u2', '$2a$12$8f2FeAsxFN8WUVXn3RqDe.Nz7b4ggQpbHJbIGtnW37V60pUr420V6', NULL, NULL, NULL);
INSERT INTO public.usr VALUES (9, true, NULL, 'u.a@gmail.com', 'u3', '$2a$12$RhkajkGXV90AAzzWRVHcU.SUuo22.royZV9xw2Qru8ue0Rfae4ZKO', NULL, NULL, NULL);
INSERT INTO public.usr VALUES (1, true, 'г. Москва; ул. Молодогвардейская; д. 34', 'mikhail.okhapkin@yandex.ru', 'MikhailOk', '$2a$12$j87/L2SoBAyEB5pIKOVHwO8eskIMsW99JNCT55xwmQEQ6o6kSDy5K', '+79639902768', 'Михаил', 'Охапкин');
INSERT INTO public.usr VALUES (2, true, 'г. Москва; ул. Молодогвардейская; д. 34', 'u.s@gmail.com', 'u', '$2a$12$ipnYDF2Zv2i9cbkfAogl0eRte7Qb/aoWhmODytyZb7Q6AO13g5oc.', '+7(903)683-7020', 'Майа', 'Охапкина');


--
-- TOC entry 3365 (class 0 OID 0)
-- Dependencies: 210
-- Name: orders_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.orders_sequence', 1, true);


--
-- TOC entry 3366 (class 0 OID 0)
-- Dependencies: 214
-- Name: pdf_file_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.pdf_file_id_seq', 1, false);


--
-- TOC entry 3367 (class 0 OID 0)
-- Dependencies: 211
-- Name: products_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.products_sequence', 19, true);


--
-- TOC entry 3368 (class 0 OID 0)
-- Dependencies: 209
-- Name: users_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_sequence', 1, true);


--
-- TOC entry 3369 (class 0 OID 0)
-- Dependencies: 219
-- Name: usr_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.usr_id_seq', 9, true);


--
-- TOC entry 3195 (class 2606 OID 46097)
-- Name: orders orders_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_pkey PRIMARY KEY (id);


--
-- TOC entry 3197 (class 2606 OID 46106)
-- Name: pdf_file pdf_file_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pdf_file
    ADD CONSTRAINT pdf_file_pkey PRIMARY KEY (id);


--
-- TOC entry 3199 (class 2606 OID 46116)
-- Name: products products_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.products
    ADD CONSTRAINT products_pkey PRIMARY KEY (id);


--
-- TOC entry 3201 (class 2606 OID 46128)
-- Name: usr usr_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usr
    ADD CONSTRAINT usr_pkey PRIMARY KEY (id);


--
-- TOC entry 3202 (class 2606 OID 46129)
-- Name: order_status_pon fk5uyhio03opdugg2e4hkqnc5kn; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_status_pon
    ADD CONSTRAINT fk5uyhio03opdugg2e4hkqnc5kn FOREIGN KEY (order_id) REFERENCES public.orders(id);


--
-- TOC entry 3203 (class 2606 OID 46134)
-- Name: orders fk7ncuqw9n77odylknbo8aikc9w; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT fk7ncuqw9n77odylknbo8aikc9w FOREIGN KEY (user_id) REFERENCES public.usr(id);


--
-- TOC entry 3206 (class 2606 OID 46149)
-- Name: user_role fkfpm8swft53ulq2hl11yplpr5; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_role
    ADD CONSTRAINT fkfpm8swft53ulq2hl11yplpr5 FOREIGN KEY (user_id) REFERENCES public.usr(id);


--
-- TOC entry 3204 (class 2606 OID 46139)
-- Name: product_type fkls3uvvo6ebnhsa9c6gn7ybnkm; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_type
    ADD CONSTRAINT fkls3uvvo6ebnhsa9c6gn7ybnkm FOREIGN KEY (product_id) REFERENCES public.products(id);


--
-- TOC entry 3205 (class 2606 OID 46144)
-- Name: products fkr3aftk48ylvntpui7l04kbcc1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.products
    ADD CONSTRAINT fkr3aftk48ylvntpui7l04kbcc1 FOREIGN KEY (order_id) REFERENCES public.orders(id);


-- Completed on 2022-08-01 19:07:49

--
-- PostgreSQL database dump complete
--

