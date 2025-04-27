-- member 테이블이 없으면 생성
CREATE TABLE IF NOT EXISTS member (
                                      id VARCHAR(255) PRIMARY KEY,
    status VARCHAR(255)
    );

-- 이제 truncate
TRUNCATE TABLE member;
