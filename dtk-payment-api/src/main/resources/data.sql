-- test Data

insert into consultation (consulter, consulter_email, consultant, consultation_type, cost, process_status)
    values ('김수현', 'zktl3575@gmail.com', '박주형', '30분 비대면 상담', '100', 'APPROVED');
insert into consultation (consulter, consulter_email, consultant, consultation_type, cost, process_status)
    values ('김수현', 'zktl3575@gmail.com', '박주형', '30분 비대면 상담', '100', 'APPROVED');
insert into consultation (consulter, consulter_email, consultant, consultation_type, cost, process_status)
    values ('김수현', 'zktl3575@gmail.com', '박주형', '30분 비대면 상담', '100', 'APPROVED');

-- insert into payment (consultation_id, payment_uid, cost, status)
--     values (1, 'imp1234', 10000, 'READY');