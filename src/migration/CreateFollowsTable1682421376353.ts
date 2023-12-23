import {MigrationInterface, QueryRunner} from 'typeorm';
import {Logger} from '@nestjs/common';

export class CreateFollowsTable1682421376353 implements MigrationInterface {
  private readonly logger: Logger = new Logger(
    CreateFollowsTable1682421376353.name,
  );

  public async up(queryRunner: QueryRunner): Promise<any> {
    this.logger.log('Start migration - CreateFollowsTable1682421376353');
    await queryRunner.query(
      `CREATE TABLE "followers"
       (
           "id"                SERIAL PRIMARY KEY,
           "following_user_id" BIGINT    NOT NULL,
           "followed_user_id"  BIGINT    NOT NULL,
           "notifications"     VARCHAR   NOT NULL,
           "created_at"        TIMESTAMP NOT NULL DEFAULT now(),
           "updated_at"        TIMESTAMP NOT NULL DEFAULT now(),
           CONSTRAINT "following_user_id" FOREIGN KEY (following_user_id) REFERENCES users (id),
           CONSTRAINT "followed_user_id" FOREIGN KEY (followed_user_id) REFERENCES users (id)
       )`,
    );
  }

  public async down(queryRunner: QueryRunner): Promise<any> {
    await queryRunner.query(`DROP TABLE "followers"`);
  }
}
