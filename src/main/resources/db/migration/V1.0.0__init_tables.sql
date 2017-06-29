CREATE TABLE sent_emails (
  id VARCHAR(255) NOT NULL,
  sent_date_time DATETIME NOT NULL,
  html_content TEXT NOT NULL,
  title VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
)