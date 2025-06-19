-- User 테이블
CREATE TABLE "user" (
                        id BIGSERIAL PRIMARY KEY,
                        username TEXT NOT NULL,
                        name TEXT NOT NULL,
                        password TEXT NOT NULL,
                        grade BIGINT NOT NULL,
                        class_number BIGINT NOT NULL,
                        student_number BIGINT NOT NULL,
                        created_at TIMESTAMP,
                        updated_at TIMESTAMP
);

-- Assignment 테이블
CREATE TABLE assignment (
                            id BIGSERIAL PRIMARY KEY,
                            name TEXT NOT NULL,
                            title TEXT NOT NULL,
                            description TEXT NOT NULL,
                            created_at TIMESTAMP,
                            updated_at TIMESTAMP
);

-- Code 테이블
CREATE TABLE code (
                      id BIGSERIAL PRIMARY KEY,
                      likes BIGINT NOT NULL,
                      views BIGINT NOT NULL,
                      code TEXT NOT NULL,
                      title TEXT NOT NULL,
                      description TEXT NOT NULL,
                      field INTEGER NOT NULL,
                      user_id BIGINT NOT NULL,
                      assignment_id BIGINT,
                      created_at TIMESTAMP,
                      updated_at TIMESTAMP,
                      CONSTRAINT fk_code_user FOREIGN KEY (user_id) REFERENCES "user"(id),
                      CONSTRAINT fk_code_assignment FOREIGN KEY (assignment_id) REFERENCES assignment(id)
);

-- Comment 테이블
CREATE TABLE comment (
                         id BIGSERIAL PRIMARY KEY,
                         content TEXT NOT NULL,
                         code_id BIGINT NOT NULL,
                         author_id BIGINT NOT NULL,
                         created_at TIMESTAMP,
                         updated_at TIMESTAMP,
                         CONSTRAINT fk_comment_code FOREIGN KEY (code_id) REFERENCES code(id),
                         CONSTRAINT fk_comment_author FOREIGN KEY (author_id) REFERENCES "user"(id)
);

-- Like 테이블
CREATE TABLE "like" (
                        id BIGSERIAL PRIMARY KEY,
                        user_id BIGINT NOT NULL,
                        code_id BIGINT NOT NULL,
                        created_at TIMESTAMP,
                        updated_at TIMESTAMP,
                        CONSTRAINT fk_like_user FOREIGN KEY (user_id) REFERENCES "user"(id),
                        CONSTRAINT fk_like_code FOREIGN KEY (code_id) REFERENCES code(id),
                        CONSTRAINT uq_like_user_code UNIQUE (user_id, code_id)
);
