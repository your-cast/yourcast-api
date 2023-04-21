import {MigrationInterface, QueryRunner} from 'typeorm';
import {Logger} from '@nestjs/common';

export class CreateUsersTable1677850482182 implements MigrationInterface {
  private readonly logger = new Logger(CreateUsersTable1677850482182.name);

  public async up(queryRunner: QueryRunner): Promise<any> {
    this.logger.log('Start migration');
    await queryRunner.query(
      `CREATE TABLE "users"
         (
           "id"        uuid              NOT NULL DEFAULT uuid_generate_v4(),
           "username"  character varying NOT NULL,
           "password"  character varying NOT NULL,
           "email"     character varying NOT NULL,
           "created_at" TIMESTAMP         NOT NULL DEFAULT now(),
           "updated_at" TIMESTAMP         NOT NULL DEFAULT now(),
           CONSTRAINT "unique_username" UNIQUE ("username"),
           CONSTRAINT "primary_key_id" PRIMARY KEY ("id")
         )`,
    );
  }

  public async down(queryRunner: QueryRunner): Promise<any> {
    await queryRunner.query(`DROP TABLE "users"`);
  }
}
