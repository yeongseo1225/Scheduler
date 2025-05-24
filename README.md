# Scheduler
Postman API 문서 링크
https://documenter.getpostman.com/view/43496090/2sB2qcBfbg

## API
| 기능            | Method   | URL                 | request                                      | response             | 상태코드       |
|-----------------|----------|---------------------|----------------------------------------------|----------------------|----------------|
| 일정 생성       | `POST`   | `/schedules`        | Body: `title`, `writer`, `password`          | 생성된 일정 정보     | 200: 정상등록  |
| 선택 일정 조회  | `GET`    | `/schedules/{id}`   | Path Variable: `id`                          | 단건 일정 정보       | 200: 정상조회  |
| 전체 일정 조회  | `GET`    | `/schedules`        | Query Params: `writer`, `modifiedDate`       | 일정 목록             | 200: 정상조회  |
| 일정 수정       | `PATCH`  | `/schedules/{id}`   | Body: `title`, `writer`, `password`          | 수정된 일정 정보     | 200: 정상수정  |
| 일정 삭제       | `DELETE` | `/schedules/{id}`   | Body: `password`                             | 삭제 성공 메시지     | 200: 정상삭제  |

## ERD 설계 – Schedule 테이블

| 컬럼명        | 타입         | 설명             |
|---------------|--------------|------------------|
| id            | BIGINT       | 기본키, 자동 증가 |
| title         | VARCHAR(255) | 할 일 제목        |
| writer        | VARCHAR(100) | 작성자명          |
| password      | VARCHAR(100) | 비밀번호 (입력용) |
| created_at    | DATETIME     | 작성 시각         |
| modified_at   | DATETIME     | 수정 시각         |
