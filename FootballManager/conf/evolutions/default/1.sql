# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table fixtures (
  match_id                  bigint not null,
  league_name               varchar(255),
  week                      integer,
  home_team_id              bigint,
  away_team_id              bigint,
  home_score                integer,
  away_score                integer,
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
  team_id_team_id           bigint,
  injury                    boolean,
  salary                    double,
  transfer_value            double,
  constraint pk_player primary key (player_id))
;

create table position (
  id                        bigint not null,
  position                  varchar(255),
  constraint pk_position primary key (id))
;

create table team (
  team_id                   bigint not null,
  max_players               integer,
  max_on_field              integer,
  max_subs                  integer,
  user_id                   integer,
  team_name                 varchar(255),
  team_score                integer,
  constraint pk_team primary key (team_id))
;

create table user (
  user_id                   bigint not null,
  password                  varchar(255),
  name                      varchar(255),
  login_name                varchar(255),
  team_id                   integer,
  constraint pk_user primary key (user_id))
;


create table fixtures_team (
  fixtures_match_id              bigint not null,
  team_team_id                   bigint not null,
  constraint pk_fixtures_team primary key (fixtures_match_id, team_team_id))
;
create sequence fixtures_seq;

create sequence player_seq;

create sequence position_seq;

create sequence team_seq;

create sequence user_seq;

alter table player add constraint fk_player_position_1 foreign key (position_id) references position (id) on delete restrict on update restrict;
create index ix_player_position_1 on player (position_id);
alter table player add constraint fk_player_teamID_2 foreign key (team_id_team_id) references team (team_id) on delete restrict on update restrict;
create index ix_player_teamID_2 on player (team_id_team_id);



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

drop sequence if exists user_seq;

