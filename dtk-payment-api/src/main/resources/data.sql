-- test Data
insert into payment(consultation_id, imp_uid, payment_pg_id, cost, status)
    values (1L, 'imp_001', 'KAKAO', 10000, 'PAID');

insert into consultation(consulter, consulter_email, consultant, consultation_type, process_status)
    values ('김수현', 'zktl3575@gmail.com', '박주형', '15분 전화 상담', 'WAITING_CONSULTATION');
