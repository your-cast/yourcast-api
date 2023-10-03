import {MigrationInterface, QueryRunner} from 'typeorm';
import {Logger} from '@nestjs/common';

export class CreateUsersTable1682421338891 implements MigrationInterface {
  private readonly logger: Logger = new Logger(CreateUsersTable1682421338891.name);

  public async up(queryRunner: QueryRunner): Promise<any> {
    this.logger.log('Start migration - CreateUsersTable1682421338891');
    await queryRunner.query(
      `CREATE TABLE "users"
       (
           "id"                SERIAL PRIMARY KEY,
           "name"              VARCHAR   NOT NULL,
           "password"          VARCHAR   NOT NULL,
           "email"             VARCHAR   NOT NULL,
           "system_id"         VARCHAR   NOT NULL,
           "email_verified_at" TIMESTAMP          DEFAULT NULL,
           "created_at"        TIMESTAMP NOT NULL DEFAULT now(),
           "updated_at"        TIMESTAMP NOT NULL DEFAULT now(),
           CONSTRAINT "unique_name" UNIQUE ("name"),
           CONSTRAINT "unique_email" UNIQUE ("email"),
           CONSTRAINT "unique_system_id" UNIQUE ("system_id")
       )`,
    );
  }

  public async down(queryRunner: QueryRunner): Promise<any> {
    await queryRunner.query(`DROP TABLE "users"`);
  }
}
