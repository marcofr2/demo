-- Insert a User
INSERT INTO user (name, surname) VALUES ('John', 'Doe');

-- Insert an NFC Tag linked to the User
INSERT INTO nfctag (uid, user_id) VALUES ('12345ABC', LAST_INSERT_ID());

