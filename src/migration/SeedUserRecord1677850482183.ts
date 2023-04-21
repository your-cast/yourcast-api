import {MigrationInterface, QueryRunner} from 'typeorm';
import {UserEntity} from '../user/entity/user.entity';

export class SeedUserRecord1565812987671 implements MigrationInterface {
  public async up(queryRunner: QueryRunner): Promise<any> {
    const userRepo = queryRunner.manager.getRepository(UserEntity);

    const user = userRepo.create({
      username: 'bhaidar',
      password: '@dF%^hGb03W~',
      email: 'bhaidar@gmail.com',
    });

    await userRepo.save(user);
  }

  public async down(queryRunner: QueryRunner): Promise<any> {}
}
