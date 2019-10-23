BEGIN;

CREATE INDEX character_index ON character (id, literal);

CREATE INDEX codepoint_index ON codepoint (character, type, codepoint);

CREATE INDEX radical_index ON radical (character, type, radical);

CREATE INDEX miscellaneous_index ON miscellaneous (character, grade, stroke_count, frequency, variant_type, variant, jlpt, radical_name);

CREATE INDEX dictionary_index ON dictionary (character, dictionary, value);

CREATE INDEX code_index ON code (character, code);

CREATE INDEX reading_index ON reading (character, type, reading);

CREATE INDEX meaning_index ON meaning (character, language, reading);

CREATE INDEX nanori_index ON nanori (character, nanori);

END;