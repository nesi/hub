Run projectdb import:

java -cp /opt/jooq/jOOQ-lib/jooq-3.3.2.jar:/opt/jooq/jOOQ-lib/jooq-meta-3.3.2.jar:/opt/jooq/jOOQ-lib/jooq-codegen-3.3.2.jar:/home/markus/local/lib/java/mysql-connector-java-5.1.26-bin.jar:. org.jooq.util.GenerationTool /projectDb.xml

java -cp /opt/jooq/jOOQ-lib/jooq-3.3.2.jar:/opt/jooq/jOOQ-lib/jooq-meta-3.3.2.jar:/opt/jooq/jOOQ-lib/jooq-codegen-3.3.2.jar:/home/markus/Downloads/postgresql-9.3-1101.jdbc3.jar:. org.jooq.util.GenerationTool /dim_jobs.xml
