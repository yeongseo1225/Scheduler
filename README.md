# Scheduler
Postman API 문서 링크
https://documenter.getpostman.com/view/43496090/2sB2qcBfbg

API
| 기능            | Method   | URL                 | request                                      | response             | 상태코드       |
|-----------------|----------|---------------------|----------------------------------------------|----------------------|----------------|
| 일정 생성       | `POST`   | `/schedules`        | Body: `title`, `writer`, `password`          | 생성된 일정 정보     | 200: 정상등록  |
| 선택 일정 조회  | `GET`    | `/schedules/{id}`   | Path Variable: `id`                          | 단건 일정 정보       | 200: 정상조회  |
| 전체 일정 조회  | `GET`    | `/schedules`        | Query Params: `writer`, `modifiedDate`       | 일정 목록             | 200: 정상조회  |
| 일정 수정       | `PATCH`  | `/schedules/{id}`   | Body: `title`, `writer`, `password`          | 수정된 일정 정보     | 200: 정상수정  |
| 일정 삭제       | `DELETE` | `/schedules/{id}`   | Body: `password`                             | 삭제 성공 메시지     | 200: 정상삭제  |
