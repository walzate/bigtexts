--DROP TABLE BIGTEXTS_FILE;
--DROP TABLE EXECUTION_LOG;
CREATE TABLE EXECUTION_LOG (
        ID SERIAL NOT NULL,
        INITIAL_DATE TIMESTAMP,
	FINAL_DATE TIMESTAMP,
        TOTAL_TIME BIGINT,
        UPLOADING_DATE TIMESTAMP,
        PIPELINE VARCHAR(10000),
        PREPROCESSING_TASKS VARCHAR(1000),
        NUMBER_OF_SLAVES INTEGER,
	STATUS VARCHAR(100),
        PRIMARY KEY (ID)
);

CREATE TABLE BIGTEXTS_FILE (
    ID SERIAL NOT NULL,
    NAME VARCHAR(1000),
    WEIGHT BIGINT,
    ID_EXECUTION_LOG INTEGER,
    PRIMARY KEY (ID),
    FOREIGN KEY (ID_EXECUTION_LOG) REFERENCES EXECUTION_LOG (ID)
);

--DROP SEQUENCE EXECUTION_LOG_ID RESTRICT;
--CREATE SEQUENCE EXECUTION_LOG_ID
--AS INTEGER
--START WITH 1;

--Adicionando espacio a la columna en la que se almacena el pipeline
--ALTER TABLE EXECUTION_LOG ALTER COLUMN PIPELINE SET DATA TYPE VARCHAR(10000);

--Adicionando la columna de estado
--ALTER TABLE EXECUTION_LOG add COLUMN STATUS VARCHAR(100);
--update EXECUTION_LOG set status = 'COMPLETED';

--Consulta
select o.PREPROCESSING_TASKS,to_char(avg(total_time), 'FM9999999999D00') total_time, NUMBER_OF_SLAVES, NUMBER_OF_ARCHIVES, 
to_char(TOTAL_WEIGHT,'FM9999999999D00') TOTAL_WEIGHT, to_char(AVERAGE_WEIGHT,'FM9999999999D00') AVERAGE_WEIGHT
, count(*) ITERATIONS
, NAME
from (
	Select l.ID,l.PREPROCESSING_TASKS,l.INITIAL_DATE,l.FINAL_DATE,l.TOTAL_TIME,l.UPLOADING_DATE,l.NUMBER_OF_SLAVES, 
	count(*) NUMBER_OF_ARCHIVES, sum(f.WEIGHT) TOTAL_WEIGHT, avg(f.WEIGHT) AVERAGE_WEIGHT,l.PIPELINE, f.NAME
	from EXECUTION_LOG l
	left join BIGTEXTS_FILE f
	on f.ID_EXECUTION_LOG = l.ID
	group by l.ID,l.INITIAL_DATE,l.FINAL_DATE,l.TOTAL_TIME,l.UPLOADING_DATE,l.PIPELINE,l.NUMBER_OF_SLAVES, l.PREPROCESSING_TASKS, f.NAME
	order by l.id
	--order by total_time desc
) as o 
group by PREPROCESSING_TASKS, NUMBER_OF_SLAVES, NUMBER_OF_ARCHIVES, TOTAL_WEIGHT, AVERAGE_WEIGHT, NAME
order by o.PREPROCESSING_TASKS,average_weight, number_of_slaves asc;

--Consulta de los archivos
select * 
--Delete 
from BIGTEXTS_FILE
where ID_EXECUTION_LOG = 22;

select * 
--Delete
from EXECUTION_LOG
where id = 22;



select o.PREPROCESSING_TASKS,to_char(avg(total_time), 'FM9999999999D00') total_time, NUMBER_OF_SLAVES, NUMBER_OF_ARCHIVES, 
to_char(TOTAL_WEIGHT,'FM9999999999D00') TOTAL_WEIGHT, to_char(AVERAGE_WEIGHT,'FM9999999999D00') AVERAGE_WEIGHT
, count(*) ITERATIONS
, NAME
from (
	Select l.ID,l.PREPROCESSING_TASKS,l.status,l.INITIAL_DATE,l.FINAL_DATE,l.TOTAL_TIME,l.UPLOADING_DATE,l.NUMBER_OF_SLAVES, 
	count(*) NUMBER_OF_ARCHIVES, sum(f.WEIGHT) TOTAL_WEIGHT, avg(f.WEIGHT) AVERAGE_WEIGHT,l.PIPELINE, f.NAME
	from EXECUTION_LOG l
	left join BIGTEXTS_FILE f
	on f.ID_EXECUTION_LOG = l.ID
	where status = 'COMPLETED'
	group by l.ID,l.INITIAL_DATE,l.FINAL_DATE,l.TOTAL_TIME,l.UPLOADING_DATE,l.PIPELINE,l.NUMBER_OF_SLAVES, l.PREPROCESSING_TASKS, f.NAME,l.status
	order by l.id desc
	--order by total_time desc
) as o 
where o.status = 'COMPLETED'
group by PREPROCESSING_TASKS, NUMBER_OF_SLAVES, NUMBER_OF_ARCHIVES, TOTAL_WEIGHT, AVERAGE_WEIGHT, NAME
order by o.PREPROCESSING_TASKS asc,o.TOTAL_WEIGHT desc, number_of_slaves asc;


select * from execution_log l
left join BIGTEXTS_FILE f
on f.ID_EXECUTION_LOG = l.ID
where preprocessing_tasks='Tokenizer'
and number_of_slaves = 3
and f.name = '1.txt'
and status = 'COMPLETED'
--order by l.id;
order by l.total_time;

--FIXED
select *  from execution_log
--update execution_log set status = 'FIXED'
where id in(
8
);

select *  from execution_log
--update execution_log set total_time = total_time -10
where id in(
24
);

select * from 
execution_log l
where preprocessing_tasks = 'Tokenizer,POS-Tagger-ES,'
pg19699.txt
--Consulta de los archivos
select * 
--Delete 
from BIGTEXTS_FILE
where ID_EXECUTION_LOG in(
542,
539





);

select * 
--Delete
from EXECUTION_LOG
where id in(
542,
539
);