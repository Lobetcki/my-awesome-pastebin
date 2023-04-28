create table paste (
   url varchar(255) not null,
   body varchar(255),
   data_created timestamp,
   data_expired timestamp,
   status varchar(255),
   title varchar(255),
   primary key (url)
);
