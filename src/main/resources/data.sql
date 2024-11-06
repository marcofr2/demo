-- Insert a User
INSERT INTO user (name, surname) VALUES ('John', 'Doe');
INSERT INTO user (id, name, surname) VALUES (2, 'Jane', 'Smith');


-- Insert an NFC Tag linked to the User
INSERT INTO nfctag (uid, user_id) VALUES ('12345ABC', 1);
INSERT INTO nfctag (uid, user_id) VALUES ('123ABC', 2);

-- Insert Projects
INSERT INTO project (id, name, description) VALUES (1, 'Project Alpha', 'Project Alpha is a project management tool for team collaboration, task tracking, and deadline management. It features real-time updates, workflows, and analytics, helping teams stay aligned and productive.');
INSERT INTO project (id, name, description) VALUES (2, 'Project Beta', 'Project Beta is a platform for data analysis and reporting, enabling users to process and visualize data for actionable insights. It offers automated syncing, dashboards, and sharing options.');

INSERT INTO user_project (user_id, project_id) VALUES (1, 1);
INSERT INTO user_project (user_id, project_id) VALUES (2, 1);
INSERT INTO user_project (user_id, project_id) VALUES (1, 2);
