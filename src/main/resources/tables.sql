BEGIN;

DROP TABLE IF EXISTS character CASCADE;
CREATE TABLE character
(
    literal TEXT        NOT NULL PRIMARY KEY
);

DROP TABLE IF EXISTS codepoint;
CREATE TABLE codepoint
(
    character INT  NOT NULL REFERENCES character (id),
    type      TEXT NOT NULL,
    codepoint TEXT NOT NULL,
    PRIMARY KEY (character, type)
);

DROP TABLE IF EXISTS radical;
CREATE TABLE radical
(
    character INT  NOT NULL REFERENCES character (id),
    type      TEXT NOT NULL,
    radical   INT  NOT NULL,
    PRIMARY KEY (character, type)
);

DROP TABLE IF EXISTS miscellaneous;
CREATE TABLE miscellaneous
(
    character    INT NOT NULL PRIMARY KEY REFERENCES character (id),
    grade        INT,
    stroke_count INT NOT NULL,
    frequency    INT,
    variant_type TEXT,
    variant      TEXT,
    jlpt         INT,
    radical_name TEXT
);

DROP TABLE IF EXISTS dictionary;
CREATE TABLE dictionary
(
    character  INT  NOT NULL REFERENCES character (id),
    dictionary TEXT NOT NULL,
    volume     INT,
    page       INT,
    value      TEXT NOT NULL,
    PRIMARY KEY (character, dictionary)
);

DROP TABLE IF EXISTS code;
CREATE TABLE code
(
    character INT  NOT NULL REFERENCES character (id),
    type      TEXT NOT NULL,
    code      TEXT NOT NULL,
    PRIMARY KEY (character, type, code)
);

DROP TABLE IF EXISTS reading;
CREATE TABLE reading
(
    character INT  NOT NULL REFERENCES character (id),
    type      TEXT NOT NULL,
    reading   TEXT NOT NULL,
    PRIMARY KEY (character, type, reading)
);

DROP TABLE IF EXISTS meaning;
CREATE TABLE meaning
(
    character INT  NOT NULL REFERENCES character (id),
    language  TEXT NOT NULL,
    meaning   TEXT NOT NULL,
    PRIMARY KEY (character, language, meaning)
);

DROP TABLE IF EXISTS nanori;
CREATE TABLE nanori
(
    character INT  NOT NULL REFERENCES character,
    nanori    TEXT NOT NULL,
    PRIMARY KEY (character, nanori)
);

END;
