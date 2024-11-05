--liquibase formatted sql
--changeset pep_sxx:1
INSERT INTO users (id, name, pin)
VALUES (1, 'Bob', 'D/4avRoIIVNTwjPW4AlhPpXuxCU4Mqdhryj/N6xaFQw='),
       (2, 'Pit', '7e4p+IJUO5VmILJtDuDn6VA5mxxCIvXeBeBkJbTJlek='),
       (3, 'Rom', 'MYruP+2MnQQNNaf8H6d2+zEwODOqLeiFNU3fPUTY+2k='),
       (4, 'Kat', 'efBvj94zNGFznyIAkKI8sqefbXFL7hANDktK8kkpRhk='),
       (5, 'Nik', 'wfMw0K/zHByHQD8eQ0e8whr/fBeZCHI1NfKzFyNwJSU='),
       (6, 'Joy', '12l1cEYvdWK4PoEljeDx5Bgy6YBy5Ew27I7+xGeG4k4='),
       (7, 'Gek', 'QcmR62pmJCwEVBkSRCeBg85Yz0przTcveZ5LnMAYhq8='),
       (8, 'Tim', 'KSaicx9LMSwImCys+AYesUv2XBqHzF1w6GTgecYiBzE='),
       (9, 'Jon', 'iI3yWuNXckJKVgxxUqHeeURA4Opc/uYoKDM6RWpQbgU='),
       (10, 'Tom', 'mvFbM25qlhmShTffMLLmojdlafz51+dz7M7eZWBlKaA=');

--changeset pep_sxx:2
INSERT INTO bank_account (users_id, money)
VALUES (1, 1000),
       (2, 1000),
       (3, 1000),
       (4, 1000),
       (5, 1000),
       (6, 1000),
       (7, 1000),
       (8, 1000),
       (9, 1000),
       (10, 1000),
       (10, 1000),
       (10, 1000),
       (10, 1000),
       (10, 1000),
       (10, 1000),
       (10, 1000),
       (10, 1000),
       (10, 1000),
       (10, 1000),
       (10, 1000);
