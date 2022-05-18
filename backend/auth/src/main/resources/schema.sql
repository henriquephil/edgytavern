CREATE TABLE client
(
    id TEXT PRIMARY KEY,
    secret TEXT NOT NULL
);

CREATE TABLE users
(
    id TEXT PRIMARY KEY,
    username TEXT NOT NULL,
    password TEXT NOT NULL,
    active BOOLEAN NOT NULL,
    display_name TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL
);

CREATE TABLE token
(
    access_token TEXT PRIMARY KEY,
    user_id TEXT NOT NULL,
    client_id TEXT NOT NULL,
    token_type TEXT NOT NULL,
    access_token_expiration TIMESTAMP NOT NULL,
    refresh_token TEXT,
    refresh_token_expiration TIMESTAMP,
    CONSTRAINT fk_token_user FOREIGN KEY(user_id) REFERENCES users(id),
    CONSTRAINT fk_token_client FOREIGN KEY(client_id) REFERENCES client(id),
	CONSTRAINT un_token_refresh UNIQUE(refresh_token)
);
