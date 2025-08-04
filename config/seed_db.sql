INSERT INTO resumes(uuid, full_name)  VALUES ('UUID_14', 'David Beckham'),
       ('UUID_15', 'Teddy Sheringam'),
       ('UUID_17', 'Peter Olsen'),
       ('UUID_18', 'Carlos Tevez');



INSERT INTO contacts( resume_uuid, type, value)  VALUES ('UUID_14', 'PHONE', '+7(999)000-00-00'),
                                             ('UUID_14', 'LINKEDIN', 'test@linked_in.com'),
                                             ('UUID_15', 'EMAIL', 'tsher@mail.ru'),
                                             ('UUID_15', 'GITHUB', 'sher@github.com'),
                                             ('UUID_15', 'HOME_PHONE', '+3(321)345-11-22'),
                                             ('UUID_17', 'EMAIL', 'olsen@gmail.com'),
                                             ('UUID_17', 'SKYPE','olsen@skype.com'),
                                             ('UUID_17', 'HOME_PAGE', 'www.peter_olsen.com'),
                                             ('UUID_18', 'SKYPE', 'carlitos@skype.com'),
                                             ('UUID_18', 'PHONE', '+34(555)342-23-45');