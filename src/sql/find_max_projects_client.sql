select w."NAME" as "NAME", count(pw."PROJECT_ID") as "PROJECT_COUNT"
from worker w
join project_worker pw on w."ID" = pw."WORKER_ID"
group by w."ID", w."NAME"
having count(pw."PROJECT_ID") = (
    select MAX(project_count)
    from (
        select count("PROJECT_ID") project_count
        from project_worker
        group by "WORKER_ID") as counts);