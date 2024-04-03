BEGIN;

CREATE TABLE IF NOT EXISTS users (
    id serial primary key,
    user_id character varying(255) not null,
    first_name character varying(255) ,
    last_name character varying(255) ,
    email character varying(255) ,
    phone character varying(255) ,
    bio character varying(255) ,
    reference_id character varying(255) ,
    qr_code_secret character varying(255) ,
    qr_code_image_uri TEXT,
    image_url character varying(255),
    last_login TIMESTAMP(6) with time zone default current_timestamp,
    login_attempts integer default 0,
    mfa boolean not null default false,
    enabled boolean not null default false,
    account_non_expired boolean not null default false,
    account_non_blocked boolean not null default false,
    created_by bigint not null,
    updated_by bigint not null,
    created_at TIMESTAMP(6) with time zone default current_timestamp,
    updated_at TIMESTAMP(6) with time zone default current_timestamp,
    constraint uq_users_email unique (email),
    constraint uq_users_user_id unique (user_id),
    constraint fk_users_created_by foreign key (created_by) references users (id) match simple on update cascade on delete cascade,
    constraint fk_users_updated_by foreign key (updated_by) references users (id) match simple on update cascade on delete cascade
);

CREATE TABLE IF NOT EXISTS confirmations (
    id serial primary key,
    user_id character varying(255) not null,
    key character varying(255) ,
    reference_id character varying(255) ,
    created_by bigint not null,
    updated_by bigint not null,
    created_at TIMESTAMP(6) with time zone default current_timestamp,
    updated_at TIMESTAMP(6) with time zone default current_timestamp,
    constraint uq_confirmations_user_id unique (user_id),
    constraint uq_confirmations_key unique (key),
    constraint fk_confirmations_user_id foreign key (user_id) references users (id) match simple on update cascade on delete cascade,
    constraint fk_confirmations_created_by foreign key (created_by) references users (id) match simple on update cascade on delete cascade,
    constraint fk_confirmations_updated_by foreign key (updated_by) references users (id) match simple on update cascade on delete cascade
);

CREATE TABLE IF NOT EXISTS credentials (
    id serial primary key,
    user_id character varying(255) not null,
    password character varying(255) ,
    reference_id character varying(255) ,
    created_by bigint not null,
    updated_by bigint not null,
    created_at TIMESTAMP(6) with time zone default current_timestamp,
    updated_at TIMESTAMP(6) with time zone default current_timestamp,
    constraint uq_credentials_user_id unique (user_id),
    constraint fk_credentials_user_id foreign key (user_id) references users (id) match simple on update cascade on delete cascade,
    constraint fk_credentials_created_by foreign key (created_by) references users (id) match simple on update cascade on delete cascade,
    constraint fk_credentials_updated_by foreign key (updated_by) references users (id) match simple on update cascade on delete cascade
);

CREATE TABLE IF NOT EXISTS documents (
    id serial primary key,
    document_id character varying(255) not null,
    extension character varying(10) ,
    formatted_size character varying(255) ,
    icon character varying(255) ,
    name character varying(255) ,
    size character varying(255) ,
    uri character varying(255) ,
    description character varying(255) ,
    reference_id character varying(255) ,
    created_by bigint not null,
    updated_by bigint not null,
    created_at TIMESTAMP(6) with time zone default current_timestamp,
    updated_at TIMESTAMP(6) with time zone default current_timestamp,
    constraint uq_documents_document_id unique (document_id),
    constraint fk_documents_created_by foreign key (created_by) references users (id) match simple on update cascade on delete restrict ,
    constraint fk_documents_updated_by foreign key (updated_by) references users (id) match simple on update cascade on delete restrict
);

CREATE TABLE IF NOT EXISTS roles (
    id serial primary key,
    authority character varying(255) not null,
    name character varying(255) ,
    reference_id character varying(255) ,
    created_by bigint not null,
    updated_by bigint not null,
    created_at TIMESTAMP(6) with time zone default current_timestamp,
    updated_at TIMESTAMP(6) with time zone default current_timestamp,
    constraint fk_roles_created_by foreign key (created_by) references users (id) match simple on update cascade on delete restrict ,
    constraint fk_roles_updated_by foreign key (updated_by) references users (id) match simple on update cascade on delete restrict
);

CREATE TABLE IF NOT EXISTS user_roles (
    id serial primary key,
    user_id bigint not null,
    role_id bigint not null,
    constraint fk_user_roles_user_id foreign key (user_id) references users (id) match simple on update cascade on delete restrict ,
    constraint fk_user_roles_role_id foreign key (role_id) references users (id) match simple on update cascade on delete restrict
);

CREATE INDEX IF NOT EXISTS index_users_email ON users (email);

CREATE INDEX IF NOT EXISTS index_users_user_id ON users (user_id);

CREATE INDEX IF NOT EXISTS index_confirmations_user_id ON confirmations (user_id);

CREATE INDEX IF NOT EXISTS index_credentials_user_id ON credentials (user_id);

CREATE INDEX IF NOT EXISTS index_user_roles_user_id ON user_roles (user_id);

END;