# Scheduler
Postman API 문서 링크
[https://documenter.getpostman.com/view/43496090/2sB2qcBfbg](https://documenter.getpostman.com/view/43496090/2sB2qcBfbg)

## API
| 기능            | Method   | URL                 | request                                      | response             | 상태코드       |
|-----------------|----------|---------------------|----------------------------------------------|----------------------|----------------|
| 사용자 등록     | `POST`   | `/writers`          | Body: `name`, `email`                        | 등록된 사용자 정보   | 200: 정상등록  |
| 일정 생성       | `POST`   | `/schedules`        | Body: `title`, `writerId`, `password`        | 생성된 일정 정보     | 200: 정상등록  |
| 선택 일정 조회  | `GET`    | `/schedules/{id}`   | Path Variable: `id`                          | 단건 일정 정보       | 200: 정상조회  |
| 전체 일정 조회  | `GET`    | `/schedules`        | Query Params: `writerId`, `modifiedDate`     | 일정 목록             | 200: 정상조회  |
| 일정 수정       | `PATCH`  | `/schedules/{id}`   | Body: `title`, `writerName`, `writerEmail`, `password` | 수정된 일정 정보     | 200: 정상수정  |
| 일정 삭제       | `DELETE` | `/schedules/{id}`   | Body: `password`                             | 삭제 성공 메시지     | 200: 정상삭제  |


## ERD 설계 – Schedule 테이블

![제목 없는 다이어그램](https://github.com/user-attachments/assets/ef352be3-87e2-415b-b857-399d87592c08)

