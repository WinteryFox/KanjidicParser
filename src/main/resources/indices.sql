BEGIN;

CREATE INDEX character_literal_index ON character (literal);

CREATE INDEX codepoint_type_index ON codepoint (type);

CREATE INDEX codepoint_codepoint_index ON codepoint (codepoint);

CREATE INDEX radical_type_index ON radical (type);

CREATE INDEX radical_type_index ON radical (type);

CREATE INDEX radical_radical_index ON radical (radical);

CREATE INDEX miscellaneous_grade_index ON miscellaneous (grade);

CREATE INDEX miscellaneous_stroke_count_index ON miscellaneous (stroke_count);

CREATE INDEX miscellaneous_frequency_index ON miscellaneous (frequency);

CREATE INDEX miscellaneous_jlpt_index ON miscellaneous (jlpt);

CREATE INDEX miscellaneous_radical_name_index ON miscellaneous (radical_name);

CREATE INDEX dictionary_value_index ON dictionary (value);

CREATE INDEX dictionary_dictionary_index ON dictionary (dictionary);

CREATE INDEX code_index ON code (code);

CREATE INDEX reading_reading_index ON reading (reading);

CREATE INDEX reading_type_index ON reading (type);

CREATE INDEX meaning_meaning_index ON meaning (lower(meaning));

CREATE INDEX meaning_language_index ON meaning (lower(language));

CREATE INDEX nanori_index ON nanori (nanori);

END;