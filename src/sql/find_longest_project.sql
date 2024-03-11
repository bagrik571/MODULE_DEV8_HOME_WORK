select "ID" as "PROJECT_ID",
 DATE_PART('year', "FINISH_DATE") * 12 + DATE_PART('month', "FINISH_DATE") - DATE_PART('year', "START_DATE") * 12 - DATE_PART('month', "START_DATE") AS "MONTH_COUNT"
from project
group by "ID" having DATE_PART('year', "FINISH_DATE") * 12 + DATE_PART('month', "FINISH_DATE") - DATE_PART('year', "START_DATE") * 12 - DATE_PART('month', "START_DATE")
   =(select max(month_count) 
   from (
   select DATE_PART('year', "FINISH_DATE") * 12 + DATE_PART('month', "FINISH_DATE") - DATE_PART('year', "START_DATE") * 12 - DATE_PART('month', "START_DATE") month_count
    from project group by "ID")counts); 
  