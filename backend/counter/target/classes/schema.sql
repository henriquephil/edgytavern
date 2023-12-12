CREATE TABLE IF NOT EXISTS public.register
(
    id bigint NOT NULL,
    ended timestamp without time zone,
    establishment_hash character varying(255),
    open boolean NOT NULL,
    started timestamp without time zone,
    CONSTRAINT register_pkey PRIMARY KEY (id)
)


CREATE TABLE IF NOT EXISTS public.bill
(
    id bigint NOT NULL,
    ended timestamp without time zone,
    open boolean NOT NULL,
    started timestamp without time zone,
    tab_hash character varying(255),
    register_id bigint,
    CONSTRAINT bill_pkey PRIMARY KEY (id),
    CONSTRAINT fka84iuedgt4q9fn9616no1ihan FOREIGN KEY (register_id)
        REFERENCES public.register (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)


CREATE TABLE IF NOT EXISTS public.order_lot
(
    id bigint NOT NULL,
    created_at timestamp without time zone,
    bill_id bigint,
    spot json,
    CONSTRAINT order_lot_pkey PRIMARY KEY (id),
    CONSTRAINT fkn01u28plvwk8v8glv69yxelse FOREIGN KEY (bill_id)
        REFERENCES public.bill (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)


CREATE TABLE IF NOT EXISTS public.order_item
(
    id bigint NOT NULL,
    asset json,
    final_value numeric(19,2),
    quantity integer NOT NULL,
    status character varying(255),
    order_lot_id bigint,
    CONSTRAINT order_item_pkey PRIMARY KEY (id),
    CONSTRAINT fkl67rfy3oucsii1goxiqm0dx5n FOREIGN KEY (order_lot_id)
        REFERENCES public.order_lot (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
