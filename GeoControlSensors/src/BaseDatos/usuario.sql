GRANT INSERT, SELECT, EXECUTE
ON monitorsensores.*
TO 'sensor'@'127.0.0.1'
IDENTIFIED BY 'sensor';


GRANT DELETE
ON monitorsensores.contactos
TO 'sensor'@'127.0.0.1'
IDENTIFIED BY 'sensor';