# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table player (
  player_id                 bigint not null,
  jersey_num                integer,
  player_name               varchar(255),
  position                  varchar(255),
  att_val                   integer,
  def_val                   integer,
  mid_fval                  integer,
  gk_val                    integer,
  constraint pk_player primary key (player_id))
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

create sequence player_seq;

create sequence team_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists player;

drop table if exists team;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists player_seq;

drop sequence if exists team_seq;

