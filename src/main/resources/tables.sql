BEGIN;

DROP TABLE IF EXISTS character CASCADE;
CREATE TABLE character
(
    literal TEXT NOT NULL PRIMARY KEY
);

DROP TABLE IF EXISTS codepoint;
CREATE TABLE codepoint
(
    character TEXT NOT NULL REFERENCES character (literal),
    type      TEXT NOT NULL,
    codepoint TEXT NOT NULL,
    PRIMARY KEY (character, type)
);

DROP TABLE IF EXISTS radical;
CREATE TABLE radical
(
    character TEXT NOT NULL REFERENCES character (literal),
    type      TEXT NOT NULL,
    radical   INT  NOT NULL,
    PRIMARY KEY (character, type)
);

DROP TABLE IF EXISTS miscellaneous;
CREATE TABLE miscellaneous
(
    character    TEXT NOT NULL PRIMARY KEY REFERENCES character (literal),
    grade        INT,
    stroke_count INT  NOT NULL,
    frequency    INT,
    variant_type TEXT,
    variant      TEXT,
    jlpt         INT,
    radical_name TEXT
);

DROP TABLE IF EXISTS dictionary;
CREATE TABLE dictionary
(
    character  TEXT NOT NULL REFERENCES character (literal),
    dictionary TEXT NOT NULL,
    volume     INT,
    page       INT,
    value      TEXT NOT NULL,
    PRIMARY KEY (character, dictionary)
);

DROP TABLE IF EXISTS code;
CREATE TABLE code
(
    character TEXT NOT NULL REFERENCES character (literal),
    type      TEXT NOT NULL,
    code      TEXT NOT NULL,
    PRIMARY KEY (character, type, code)
);

DROP TABLE IF EXISTS reading;
CREATE TABLE reading
(
    character TEXT NOT NULL REFERENCES character (literal),
    type      TEXT NOT NULL,
    reading   TEXT NOT NULL,
    PRIMARY KEY (character, type, reading)
);

DROP TABLE IF EXISTS meaning;
CREATE TABLE meaning
(
    character TEXT NOT NULL REFERENCES character (literal),
    language  TEXT NOT NULL,
    meaning   TEXT NOT NULL,
    PRIMARY KEY (character, language, meaning)
);

DROP TABLE IF EXISTS nanori;
CREATE TABLE nanori
(
    character TEXT NOT NULL REFERENCES character (literal),
    nanori    TEXT NOT NULL,
    PRIMARY KEY (character, nanori)
);

END;
