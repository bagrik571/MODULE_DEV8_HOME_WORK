with Youngest AS (
    select 'YOUNGEST' as "TYPE", "NAME", "BIRTHDAY"
    from "worker"
    where "BIRTHDAY" = (select min("BIRTHDAY") from "worker")
),
Oldest as (
    select 'OLDEST' as "TYPE", "NAME", "BIRTHDAY"
    from "worker"
    where "BIRTHDAY" = (select max("BIRTHDAY") from "worker")
)
select * from Youngest
union all
select * from Oldest;