# --- !Ups
Insert into User(usertype,userid,name,loginname,password,NUMBER_OF_TRAINING) values ('admin',0,'admin','admin','393151a09f5345add5e41c8d5f01fe378d2c18655dbc2adab92ceb6bb71bf290',0);

INSERT into POSITION(id,POSITION ) VALUES(0,'Not Assigned');
INSERT into POSITION(id,POSITION ) VALUES(1,'Goalkeeper');
INSERT into POSITION(id,POSITION ) VALUES(2,'Defense');
INSERT into POSITION(id,POSITION ) VALUES(3,'Midfield');
INSERT into POSITION(id,POSITION ) VALUES(4,'Striker');
INSERT into POSITION(id,POSITION ) VALUES(5,'Sub');

INSERT into TEAM (TEAM_ID,USER_ID,team_name,team_score) VALUES ( 0,0,'Transfer market',0);
INSERT into TEAM (TEAM_ID,USER_ID,team_name,team_score) VALUES (1,null,'Liverpool',0);
INSERT into TEAM (TEAM_ID,USER_ID,team_name,team_score) VALUES (2,null,'Manchester United',0);
INSERT into TEAM (TEAM_ID,USER_ID,team_name,team_score) VALUES (3,null,'Everton',0);
INSERT into TEAM (TEAM_ID,USER_ID,team_name,team_score) VALUES (4,null,'Chelsea',0);
INSERT into TEAM (TEAM_ID,USER_ID,team_name,team_score) VALUES (5,null,'Arsenal',0);
INSERT into TEAM (TEAM_ID,USER_ID,team_name,team_score) VALUES (6,null,'Crystal Palace',0);

INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (1,12,'Craig Hill',1,8,5,1,5,10,0,False,5959,5066);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (2,10,'Donald Howard',2,4,2,5,8,10,0,False,6234,5662);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (3,1,'Walter Campbell',2,3,7,6,9,10,0,False,9632.3,6945);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (4,35,'Eugene Richardson',2,9,6,4,3,10,0,False,4568,4678);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (5,2,'Adam Hall',2,9,5,8,6,10,0,False,8664,9562);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (6,5,'Robert Green',3,8,5,1,5,10,0,False,7202,7564);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (7,8,'Chris Edwards',3,8,6,1,3,10,0,False,6687,6998);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (8,55,'Micheal Power',3,8,5,3,1,10,0,False,8531,8646);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (9,21,'Graham Lalor',3,4,3,4,2,10,0,False,1000,1356);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (10,22,'Eddy Ekofo',4,4,5,6,8,10,0,False,9850,9812);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (11,15,'Phillip Cheape',4,8,5,5,5,10,0,False,8995,8655);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (12,13,'Jurijs Cicelimovs',0,8,5,7,6,10,0,False,9153,8645);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (13,64,'Kenneth Perez',0,5,8,1,6,10,0,False,6135,6864);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (14,2,'Philip Simmons',0,3,3,4,9,10,0,False,7643,6443);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (15,98,'Alan Rogers',0,9,5,6,5,10,0,False,5046,5050);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (16,85,'Roger Martin',1,5,2,6,5,8,1,False,6432,6465);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (17,45,'Ryan Morgan',2,2,6,8,8,10,1,False,2249,2265);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (18,64,'Carlos Murphy',2,2,2,4,9,10,1,False,6452,4319);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (19,19,'Johnny Sanders',2,8,5,1,5,10,1,False,5959,5066);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (20,35,'Charles Hughes',2,8,2,3,3,10,1,False,5999,5662);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (21,7,'David Barnes',3,4,4,6,9,10,1,False,5219,5846);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (22,9,'Eric Gray',3,8,6,7,9,10,1,False,5649,5946);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (23,36,'Brandon Roberts',3,3,7,5,6,10,1,False,5222,2016);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (24,22,'Jeremy Bailey',3,6,3,4,5,10,1,False,2265,2253);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (25,92,'Dnnis Butler',4,5,2,9,4,10,1,False,4561,3135);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (26,46,'George Parker',4,8,5,4,5,10,1,False,6456,2254);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (27,91,'Joe Sanchez',0,4,5,9,5,10,1,False,5959,5066);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (28,53,'William Young',0,6,5,8,5,10,1,False,5569,5796);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (29,47,'Todd Walker',0,8,5,5,7,10,1,False,9559,5466);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (30,30,'Willie Baker',0,4,5,6,5,10,1,False,4545,6546);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (31,29,'Daniel Cox',1,6,5,1,3,10,2,False,5365,5946);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (32,34,'James Robinson',2,9,5,1,5,10,2,False,6666,4689);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (33,28,'Scott Collins',2,6,5,7,5,10,2,False,4384,4683);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (34,4,'Christopher Bennett',2,5,5,6,5,10,2,False,5565,5066);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (35,16,'Howard Watson',2,8,8,1,5,10,2,False,8975,9213);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (36,41,'Matthew King',3,5,3,6,8,10,2,False,4623,4568);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (37,42,'Matthew Thompson',3,4,5,1,8,10,2,False,5339,3456);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (38,48,'Conor Griffin',3,2,4,3,6,10,2,False,1358,1356);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (39,52,'Keith Long',3,8,5,1,5,10,2,False,4563,7896);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (40,56,'Gary Lee',4,8,3,8,5,10,2,False,5559,5866);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (49,37,'Jeffrey Carter',4,7,5,6,5,10,2,False,3546,5631);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (50,38,'Joshua Price',0,8,5,4,5,10,2,False,2301,4643);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (51,18,'Simon Harris',0,6,5,5,2,10,2,False,8959,5316);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (52,73,'Clarence Peterson',0,3,5,5,6,10,2,False,1356,5449);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (53,61,'Gregory Bryant',0,5,3,1,8,10,2,False,6959,6566);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (54,71,'Douglas Ward',1,7,5,4,5,10,3,False,6799,6896);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (55,81,'Thomas Wood',2,6,5,4,5,10,3,False,7945,3546);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (56,91,'Earl Lopez',2,4,5,1,5,10,3,False,2135,2304);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (57,95,'Peter Jackson',2,1,5,1,10,10,3,False,6959,7966);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (58,45,'Jason Coleman',2,8,5,8,5,10,3,False,5461,7631);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (59,63,'Larry Mitchell',3,7,5,1,5,10,3,False,5879,5864);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (60,72,'Lee Healey',3,6,8,3,4,10,3,False,6431,4315);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (61,72,'Stuart Strudwick',3,6,8,3,4,10,3,False,6431,4315);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (62,72,'Bryce Peters',3,6,8,3,4,10,3,False,6431,4315);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (63,72,'Selwyn Patton',4,6,8,3,4,10,3,False,6431,4315);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (64,72,'Cam Thompson',4,6,8,3,4,10,3,False,6431,4315);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (65,72,'Algar Macey',0,6,8,3,4,10,3,False,6431,4315);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (66,72,'Brett Hadaway',0,6,8,3,4,10,3,False,6431,4315);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (67,72,'Trey Daniel',0,6,8,3,4,10,3,False,6431,4315);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (69,72,'Langdon Longstaff',0,6,8,3,4,10,3,False,6431,4315);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (70,72,'Rudy Page',1,6,8,3,4,10,4,False,6431,4315);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (71,72,'Theo Jervis',2,6,8,3,4,10,4,False,6431,4315);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (72,72,'Emmett Masterson',2,6,8,3,4,10,4,False,6431,4315);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (73,72,'Quin Stenet',2,6,8,3,4,10,4,False,6431,4315);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (74,72,'Titus Dennell',2,6,8,3,4,10,4,False,6431,4315);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (75,72,'Micah Jakeman',3,6,8,3,4,10,4,False,6431,4315);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (76,72,'Huey Christopher',3,6,8,3,4,10,4,False,6431,4315);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (77,72,'Sherwood Gabriels',3,6,8,3,4,10,4,False,6431,4315);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (78,72,'Woody Ryer',3,6,8,3,4,10,4,False,6431,4315);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (79,72,'Randy Harrelson',4,6,8,3,4,10,4,False,6431,4315);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (80,72,'Benji Winton',4,6,8,3,4,10,4,False,6431,4315);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (81,72,'Bradley Bryan',0,6,8,3,4,10,4,False,6431,4315);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (82,72,'Kennith Lawrence',0,6,8,3,4,10,4,False,6431,4315);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (83,72,'Raynard Croft',0,6,8,3,4,10,4,False,6431,4315);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (84,72,'Vern Johns',0,6,8,3,4,10,4,False,6431,4315);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (85,72,'Walt Danell',1,6,8,3,4,10,5,False,6431,4315);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (86,72,'Rolf Howe',2,6,8,3,4,10,5,False,6431,4315);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (87,72,'Bernard Toft',2,6,8,3,4,10,5,False,6431,4315);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (88,72,'Briar Sheppard',2,6,8,3,4,10,5,False,6431,4315);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (89,72,'Lake Irwin',2,6,8,3,4,10,5,False,6431,4315);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (90,72,'Reuben Caldwell',3,6,8,3,4,10,5,False,6431,4315);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (91,72,'Gallagher Alberts',3,6,8,3,4,10,5,False,6431,4315);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (92,72,'Hamnet Berry',3,6,8,3,4,10,5,False,6431,4315);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (93,72,'Lanny Williams',3,6,8,3,4,10,5,False,6431,4315);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (94,72,'Geordie Gray',4,6,8,3,4,10,5,False,6431,4315);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (95,72,'Trevor Phillips',4,6,8,3,4,10,5,False,6431,4315);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (96,72,'Wilburn Cobb',0,6,8,3,4,10,5,False,6431,4315);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (97,72,'Merit Christians',0,6,8,3,4,10,5,False,6431,4315);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (98,72,'Beverly Attwood',0,6,8,3,4,10,5,False,6431,4315);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (99,72,'Loyd Newton',0,6,8,3,4,10,5,False,6431,4315);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (100,72,'Terrell Derrick',1,6,8,3,4,10,6,False,6431,4315);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (101,72,'Red Ayton',2,6,8,3,4,10,6,False,6431,4315);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (102,72,'Ainsley Boone',2,6,8,3,4,10,6,False,6431,4315);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (103,72,'Oscar Evelyn',2,6,8,3,4,10,6,False,6431,4315);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (104,72,'Roscoe Ewart',2,6,8,3,4,10,6,False,6431,4315);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (105,72,'Daly Christison',3,6,8,3,4,10,6,False,6431,4315);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (106,72,'Kyle Tod',3,6,8,3,4,10,6,False,6431,4315);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (107,72,'Kynaston Hibbert',3,6,8,3,4,10,6,False,6431,4315);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (108,72,'Colten Rounds',3,6,8,3,4,10,6,False,6431,4315);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (109,72,'Fredrick Nathans',4,6,8,3,4,10,6,False,6431,4315);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (110,72,'Baldric Giffard',4,6,8,3,4,10,6,False,6431,4315);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (111,72,'Lyndon North',0,6,8,3,4,10,6,False,6431,4315);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (112,72,'Thorburn Christopherson',0,6,8,3,4,10,6,False,6431,4315);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (113,72,'Corbin Ryer',0,6,8,3,4,10,6,False,6431,4315);
INSERT into PLAYER (PLAYER_ID,jersey_num,player_name,position_id,att_val,def_val,MID_FVAL,gk_val,health,TEAM_TEAM_ID,injury,salary,transfer_value) VALUES (114,72,'Otto Wallis',0,6,8,3,4,10,6,False,6431,4315);

# --- !Downs
Delete from User where userid = 0;
Delete from FIXTURES_TEAM ;
DELETE FROM FIXTURES;
DELETE FROM PLAYER;
DELETE FROM TEAM;
DELETE FROM POSITION;