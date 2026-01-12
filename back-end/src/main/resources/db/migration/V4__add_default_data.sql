INSERT INTO [User] (ID, USERNAME, PASSWORD, ROLE, EMAIL)
VALUES
(NEWID(), 'admin', '$2a$10$t36EQyvd7TkInNWVOPNBMOhK92E2bMB./Iwy0s2SbBsHbUgZW.9Ba', 'ADMIN', 'admin@gmail.com'),
(NEWID(), 'user1', '$2a$10$t36EQyvd7TkInNWVOPNBMOhK92E2bMB./Iwy0s2SbBsHbUgZW.9Ba', 'USER', 'user1@gmail.com'),
(NEWID(), 'user2', '$2a$10$t36EQyvd7TkInNWVOPNBMOhK92E2bMB./Iwy0s2SbBsHbUgZW.9Ba', 'USER', 'user2@gmail.com');


INSERT INTO Country (ID, COUNTRY_NAME)
VALUES
(NEWID(), N'Vietnam'),
(NEWID(), N'USA'),
(NEWID(), N'China'),
(NEWID(), N'India'),
(NEWID(), N'Korea'),
(NEWID(), N'Japan');


INSERT INTO Genre (NAME)
VALUES
(N'Action'),
(N'Drama'),
(N'Comedy'),
(N'Horror'),
(N'Romance');
