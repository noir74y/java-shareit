CREATE TABLE IF NOT EXISTS users (
  id    INTEGER GENERATED BY DEFAULT AS IDENTITY,
  name  VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  CONSTRAINT pk_user PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS items
(
    id          INTEGER GENERATED BY DEFAULT AS IDENTITY,
    name        VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    available   BOOLEAN      NOT NULL,
    owner_id    INTEGER      NOT NULL,
    CONSTRAINT pk_item PRIMARY KEY (id),
    CONSTRAINT fk_owner FOREIGN KEY (owner_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS bookings
(
    id         INTEGER GENERATED BY DEFAULT AS IDENTITY,
    item_id    INTEGER,
    booker_id  INTEGER,
    start_date TIMESTAMP WITHOUT TIME ZONE,
    end_date   TIMESTAMP WITHOUT TIME ZONE,
    status     VARCHAR(255),
    CONSTRAINT pk_booking PRIMARY KEY (id),
    CONSTRAINT fk_item FOREIGN KEY (item_id) REFERENCES items (id),
    CONSTRAINT fk_booker FOREIGN KEY (booker_id) REFERENCES users (id)
);