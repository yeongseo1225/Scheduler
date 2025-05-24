# Scheduler

| 기능                           | Method   | URL               | request                                     | response  |
| ---------------------------- | -------- | ----------------- | ------------------------------------------- | --------- |
| 일정 등록<br>새 일정 생성             | `POST`   | `/schedules`      | Body: `title`, `writer`, `password`         | 생성된 일정 정보 |
| 일정 조회<br>선택한 일정 1개 조회        | `GET`    | `/schedules/{id}` | Path Variable: `id`                         | 단건 일정 정보  |
| 일정 목록 조회<br>전체 또는 조건부 조회     | `GET`    | `/schedules`      | Query Params: `writer`, `modifiedDate` (선택) | 일정 목록     |
| 일정 수정<br>할일/작성자 수정 (비밀번호 필요) | `PATCH`  | `/schedules/{id}` | Body: `title`, `writer`, `password`         | 수정된 일정 정보 |
| 일정 삭제<br>비밀번호 확인 후 삭제        | `DELETE` | `/schedules/{id}` | Body: `password`                            | 삭제 성공 메시지 |
