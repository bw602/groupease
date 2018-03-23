CREATE TABLE UserProfile (
    ID BIGSERIAL PRIMARY KEY,
    AuthID TEXT NOT NULL UNIQUE,
    Name TEXT NOT NULL,
    NickName TEXT NULL,
    Email TEXT NOT NULL,
    PhotoURL TEXT NULL,
    LastUpdate TIMESTAMP NOT NULL
);

CREATE TABLE Channel (
  ID BIGSERIAL PRIMARY KEY,
  Name TEXT NOT NULL UNIQUE,
  Description TEXT NULL,
  LastUpdatedOn TIMESTAMP NOT NULL
);

CREATE TABLE Member (
    ID BIGSERIAL PRIMARY KEY,
    UserID BIGSERIAL NOT NULL REFERENCES UserProfile,
    ChannelID BIGSERIAL NOT NULL REFERENCES Channel,
    IsOwner BOOLEAN NOT NULL DEFAULT FALSE,
    -- Per channel profile fields to go here later
    LastUpdate TIMESTAMP NOT NULL
);

CREATE TABLE ChannelGroup (
    ID BIGSERIAL PRIMARY KEY,
    ChannelID BIGSERIAL NOT NULL REFERENCES Channel ON DELETE CASCADE,
    Name TEXT NOT NULL,
    Description TEXT,
    IsFull BOOLEAN NOT NULL DEFAULT FALSE,
    LastUpdate TIMESTAMP NOT NULL
);

CREATE TABLE GroupMember (
    GroupID BIGSERIAL NOT NULL REFERENCES ChannelGroup ON DELETE CASCADE,
    MemberID BIGSERIAL NOT NULL REFERENCES Member ON DELETE CASCADE,
    LastUpdate TIMESTAMP NOT NULL,
    PRIMARY KEY (GroupID, MemberID)
);

CREATE TABLE ChannelInvitation (
    ID BIGSERIAL PRIMARY KEY,
    ChannelID BIGSERIAL NOT NULL REFERENCES Channel ON DELETE CASCADE,
    SenderID BIGSERIAL NOT NULL REFERENCES Member ON DELETE CASCADE,
    RecipientID BIGSERIAL NOT NULL REFERENCES UserProfile ON DELETE CASCADE,
    Comments TEXT,
    LastUpdate TIMESTAMP NOT NULL
);

CREATE TABLE ChannelJoinRequest (
    ID BIGSERIAL PRIMARY KEY,
    ChannelID BIGSERIAL NOT NULL REFERENCES Channel ON DELETE CASCADE,
    UserID BIGSERIAL NOT NULL REFERENCES UserProfile ON DELETE CASCADE,
    Comments TEXT,
    LastUpdate TIMESTAMP NOT NULL
);

CREATE TABLE GroupInvitation (
    ID BIGSERIAL PRIMARY KEY,
    GroupID BIGSERIAL NOT NULL REFERENCES ChannelGroup ON DELETE CASCADE,
    RecipientID BIGSERIAL NOT NULL REFERENCES Member ON DELETE CASCADE,
    Comments TEXT,
    LastUpdate TIMESTAMP NOT NULL
);

CREATE TABLE GroupJoinRequest (
    ID BIGSERIAL PRIMARY KEY,
    GroupID BIGSERIAL NOT NULL REFERENCES ChannelGroup ON DELETE CASCADE,
    SenderID BIGSERIAL NOT NULL REFERENCES Member ON DELETE CASCADE,
    Comments TEXT,
    LastUpdate TIMESTAMP NOT NULL
);

INSERT INTO Channel (
  name,
  description,
  lastUpdatedOn
) VALUES (
  'CS6675-Spring-2018',
  'Advanced Internet Applications - Spring 2018',
  CURRENT_TIMESTAMP
);

INSERT INTO Channel (
  name,
  description,
  lastUpdatedOn
) VALUES (
  'CS8803-BDS-Fall-2017',
  'Big Data Systems and Analytics - Fall 2017',
  CURRENT_TIMESTAMP
);

INSERT INTO UserProfile (
    AuthID, Name, NickName, PhotoURL, Email, LastUpdate
) VALUES (
    'phony-provider-1234567890', 'George P. Burdell', 'gpb01',
    'http://example.com/avatars/gatech/gpb01.png', 'gpburdell@gatech.edu', CURRENT_timestamp
) ;

INSERT INTO Member (
    ChannelID, UserID, IsOwner, LastUpdate
) VALUES (
    (SELECT id FROM Channel WHERE Name='CS6675-Spring-2018'),
    (SELECT ID FROM UserProfile WHERE NickName='gpb01'),
    TRUE, current_timestamp
);

INSERT INTO Member (
    ChannelID, UserID, IsOwner, LastUpdate
) VALUES (
    (SELECT id FROM Channel WHERE Name='CS8803-BDS-Fall-2017'),
    (SELECT ID FROM UserProfile WHERE NickName='gpb01'),
    TRUE, current_timestamp
);

INSERT INTO ChannelGroup (
    ChannelID, Name, Description, LastUpdate
) VALUES (
    (SELECT id FROM Channel WHERE Name='CS6675-Spring-2018'),
    'GroupEase', 'Create groups with ease', current_timestamp
);

INSERT INTO GroupMember (
    GroupID, MemberID, LastUpdate
) VALUES (
    (SELECT ID FROM ChannelGroup WHERE Name='GroupEase'),
    (SELECT Member.ID FROM Member
        INNER JOIN UserProfile ON Member.UserID = UserProfile.ID
        INNER JOIN Channel ON Member.ChannelID = Channel.ID
        WHERE UserProfile.NickName='gpb01' AND Channel.Name='CS6675-Spring-2018'),
    current_timestamp
);
