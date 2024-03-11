select p."ID" as "Project_ID",
       sum(w."SALARY" * extract (month from age(p."FINISH_DATE", p."START_DATE"))) as "Project_Cost"
from "project" p
join "project_worker" pw on p."ID" = pw."PROJECT_ID"
join "worker" w on pw."WORKER_ID" = w."ID"
group by p."ID"
order by "Project_Cost" desc;
