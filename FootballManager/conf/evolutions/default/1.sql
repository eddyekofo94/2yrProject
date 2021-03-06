

# --- !Ups

create table fixtures (
  match_id                  bigint not null,
  week                      integer,
  home_team_id              bigint,
  away_team_id              bigint,
  home_score                integer,
  away_score                integer,
  played                    boolean,
  constraint pk_fixtures primary key (match_id))
;

create table player (
  player_id                 bigint not null,
  jersey_num                integer,
  player_name               varchar(255),
  position_id               bigint,
  att_val                   integer,
  def_val                   integer,
  mid_fval                  integer,
  gk_val                    integer,
  health                    integer,
  team_team_id              bigint,
  injury                    boolean,
  salary                    double,
  transfer_value            integer,
  constraint pk_player primary key (player_id))
;

create table position (
  id                        bigint not null,
  position                  varchar(255),
  constraint pk_position primary key (id))
;

create table team (
  team_id                   bigint not null,
  user_id                   bigint,
  team_name                 varchar(255),
  team_score                integer,
  constraint pk_team primary key (team_id))
;

create table user (
  usertype                  varchar(31) not null,
  userid                    bigint auto_increment not null,
  name                      varchar(255),
  loginname                 varchar(255),
  password                  varchar(255),
  number_of_training        integer,
  bankaccount               integer,
  ready                     boolean,
  constraint pk_user primary key (userid))
;


create table fixtures_team (
  fixtures_match_id              bigint not null,
  team_team_id                   bigint not null,
  constraint pk_fixtures_team primary key (fixtures_match_id, team_team_id))
;
create sequence fixtures_seq ;

create sequence player_seq start with 200;

create sequence position_seq;

create sequence team_seq start with 6;

alter table player add constraint fk_player_position_1 foreign key (position_id) references position (id) on delete restrict on update restrict;
create index ix_player_position_1 on player (position_id);
alter table player add constraint fk_player_team_2 foreign key (team_team_id) references team (team_id) on delete restrict on update restrict;
create index ix_player_team_2 on player (team_team_id);



alter table fixtures_team add constraint fk_fixtures_team_fixtures_01 foreign key (fixtures_match_id) references fixtures (match_id) on delete restrict on update restrict;

alter table fixtures_team add constraint fk_fixtures_team_team_02 foreign key (team_team_id) references team (team_id) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists fixtures;

drop table if exists fixtures_team;

drop table if exists player;

drop table if exists position;

drop table if exists team;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists fixtures_seq;

drop sequence if exists player_seq;

drop sequence if exists position_seq;

drop sequence if exists team_seq;

