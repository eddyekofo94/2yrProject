# --- Created by Ebean DDL
# TO stop Ebean DDL generation, remove this COMMENT AND START using Evolutions

# --- !Ups

CREATE TABLE fixtures (
  match_id    INTEGER NOT NULL,
  league_name VARCHAR(255),
  start_time  VARCHAR(255),
  end_time    VARCHAR(255),
  team_id     INTEGER,
  home_score  INTEGER,
  away_score  INTEGER,
  played      BOOLEAN,
  CONSTRAINT pk_fixtures PRIMARY KEY (match_id)
);

CREATE TABLE player (
  player_id   BIGINT NOT NULL,
  jersey_num  INTEGER,
  player_name VARCHAR(255),
  position    VARCHAR(255),
  att_val     INTEGER,
  def_val     INTEGER,
  mid_fval    INTEGER,
  gk_val      INTEGER,
  CONSTRAINT pk_player PRIMARY KEY (player_id)
);

CREATE TABLE team (
  team_id      BIGINT NOT NULL,
  max_players  INTEGER,
  max_on_field INTEGER,
  max_subs     INTEGER,
  user_id      INTEGER,
  team_name    VARCHAR(255),
  team_score   INTEGER,
  CONSTRAINT pk_team PRIMARY KEY (team_id)
);

CREATE SEQUENCE fixtures_seq;

CREATE SEQUENCE player_seq;

CREATE SEQUENCE team_seq;


# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

DROP TABLE IF EXISTS fixtures;

DROP TABLE IF EXISTS player;

DROP TABLE IF EXISTS team;

SET REFERENTIAL_INTEGRITY TRUE;

DROP SEQUENCE IF EXISTS fixtures_seq;

DROP SEQUENCE IF EXISTS player_seq;

DROP SEQUENCE IF EXISTS team_seq;

